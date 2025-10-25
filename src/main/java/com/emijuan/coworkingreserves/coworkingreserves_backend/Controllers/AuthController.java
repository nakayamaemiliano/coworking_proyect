package com.emijuan.coworkingreserves.coworkingreserves_backend.Controllers;

import com.emijuan.coworkingreserves.coworkingreserves_backend.DTO.LoginRequest;
import com.emijuan.coworkingreserves.coworkingreserves_backend.DTO.LoginResponse;
import com.emijuan.coworkingreserves.coworkingreserves_backend.Repositories.UsuarioRepository;
import com.emijuan.coworkingreserves.coworkingreserves_backend.Security.JwtUtil;
import com.emijuan.coworkingreserves.coworkingreserves_backend.models.Usuario;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthController(UsuarioRepository usuarioRepository,
                          PasswordEncoder passwordEncoder,
                          AuthenticationManager authenticationManager,
                          JwtUtil jwtUtil) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody Usuario usuario) {
        if (usuarioRepository.findByEmail(usuario.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("❌ El email ya está registrado");
        }

        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuarioRepository.save(usuario);
        return ResponseEntity.ok("✅ Usuario registrado exitosamente");
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest request) {
        try {
            // 1️⃣ Autenticar al usuario
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );

            // 2️⃣ Si la autenticación fue exitosa
            if (authentication.isAuthenticated()) {
                var userFromDb = usuarioRepository.findByEmail(request.getEmail())
                        .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

                // 3️⃣ Generar JWT
                String token = jwtUtil.generateToken(
                        userFromDb.getEmail(),
                        userFromDb.getRol().name()
                );

                // 4️⃣ Retornar token + info del usuario
                LoginResponse response = new LoginResponse(token, userFromDb.getEmail(), userFromDb.getRol().name());
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(401).body("❌ Credenciales inválidas");
            }
        } catch (Exception e) {
            return ResponseEntity.status(401).body("❌ Credenciales inválidas");
        }
    }



}
