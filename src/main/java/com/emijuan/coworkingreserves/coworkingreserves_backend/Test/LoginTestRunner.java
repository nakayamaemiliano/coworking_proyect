package com.emijuan.coworkingreserves.coworkingreserves_backend.Test;

import com.emijuan.coworkingreserves.coworkingreserves_backend.Repositories.UsuarioRepository;
import com.emijuan.coworkingreserves.coworkingreserves_backend.models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

public class LoginTestRunner  implements CommandLineRunner {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        String email = "juan@gmail.com";
        String passwordEnClaro = "654321";

        System.out.println("🔹 Test de login para: " + email);

        // 1️⃣ Buscar usuario en BD
        Usuario usuario = usuarioRepository.findByEmail(email).orElse(null);

        if (usuario == null) {
            System.out.println("❌ Usuario no encontrado");
            return;
        }

        System.out.println("Usuario encontrado en BD: " + usuario.getEmail());
        System.out.println("Contraseña en BD: " + usuario.getPassword());

        // 2️⃣ Verificar password con BCrypt
        boolean matches = passwordEncoder.matches(passwordEnClaro, usuario.getPassword());

        if (matches) {
            System.out.println("✅ Contraseña correcta, login exitoso");
        } else {
            System.out.println("❌ Contraseña incorrecta, login falla");
        }
    }

}
