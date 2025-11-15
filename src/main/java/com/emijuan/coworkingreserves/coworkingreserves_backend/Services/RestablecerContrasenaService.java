package com.emijuan.coworkingreserves.coworkingreserves_backend.Services;

import com.emijuan.coworkingreserves.coworkingreserves_backend.Repositories.TokenRestablecerContrasenaRepository;
import com.emijuan.coworkingreserves.coworkingreserves_backend.Repositories.UsuarioRepository;
import com.emijuan.coworkingreserves.coworkingreserves_backend.models.TokenRestablecerContrasena;
import com.emijuan.coworkingreserves.coworkingreserves_backend.models.Usuario;
import jakarta.transaction.Transactional;
import org.springframework.mail.MailException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class RestablecerContrasenaService {

    private final UsuarioRepository usuarioRepository;
    private final TokenRestablecerContrasenaRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    public RestablecerContrasenaService(
            UsuarioRepository usuarioRepository,
            TokenRestablecerContrasenaRepository tokenRepository,
            PasswordEncoder passwordEncoder,
            EmailService emailService
    ) {
        this.usuarioRepository = usuarioRepository;
        this.tokenRepository = tokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    @Transactional
    public String generarToken(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("No existe un usuario con ese email."));

        tokenRepository.deleteAllByUsuario(usuario);

        TokenRestablecerContrasena token = new TokenRestablecerContrasena();
        token.setUsuario(usuario);
        token.setCodigoVerificacion(UUID.randomUUID().toString());
        token.setFechaExpiracion(LocalDateTime.now().plusMinutes(15));
        token.setUsado(false);
        token.setFechaCreacion(LocalDateTime.now());
        tokenRepository.save(token);

        try {
            String asunto = "Recuperación de contraseña - Coworking Reserves";
            String cuerpo = """
                    Hola %s,
                    
                    Tu código de verificación para restablecer tu contraseña es:
                    
                    %s
                    
                    Este código expirará en 15 minutos.
                    """.formatted(usuario.getNombre(), token.getCodigoVerificacion());

            emailService.enviarCorreo(usuario.getEmail(), asunto, cuerpo);
        } catch (MailException e) {
            System.err.println("⚠️ No se pudo enviar el correo: " + e.getMessage());
        }

        System.out.println("📧 Código de verificación para " + email + ": " + token.getCodigoVerificacion());
        return "📩 Se generó el token de recuperación. Revisa tu correo.";
    }

    @Transactional
    public String restablecerContrasena(String codigo, String nuevaContrasena) {
        Optional<TokenRestablecerContrasena> tokenOpt = tokenRepository.findByCodigoVerificacion(codigo);

        if (tokenOpt.isEmpty()) throw new RuntimeException("Código inválido.");

        TokenRestablecerContrasena token = tokenOpt.get();

        if (token.isUsado()) throw new RuntimeException("El token ya fue utilizado.");
        if (token.getFechaExpiracion().isBefore(LocalDateTime.now())) throw new RuntimeException("El token ha expirado.");

        Usuario usuario = token.getUsuario();
        usuario.setPassword(passwordEncoder.encode(nuevaContrasena));
        usuarioRepository.save(usuario);

        token.setUsado(true);
        tokenRepository.save(token);

        return "Contraseña actualizada correctamente ✅";
    }
}
