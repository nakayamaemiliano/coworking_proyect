package com.emijuan.coworkingreserves.coworkingreserves_backend.Controllers;

import com.emijuan.coworkingreserves.coworkingreserves_backend.Services.RestablecerContrasenaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.emijuan.coworkingreserves.coworkingreserves_backend.DTO.EmailRequest;
import com.emijuan.coworkingreserves.coworkingreserves_backend.DTO.ResetPasswordRequest;

@RestController
@RequestMapping("/api/auth")
public class RestablecerContrasenaController {
    private final RestablecerContrasenaService restablecerService;

    public RestablecerContrasenaController(RestablecerContrasenaService restablecerService) {
        this.restablecerService = restablecerService;
    }

    // 🔹 Endpoint para solicitar token de recuperación
    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody EmailRequest request) {
        System.out.println("📩 Email recibido: " + request.getEmail()); //
        try {
            String mensaje = restablecerService.generarToken(request.getEmail());
            return ResponseEntity.ok(mensaje);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    // 🔹 Endpoint para restablecer la contraseña
    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordRequest request) {
        try {
            String mensaje = restablecerService.restablecerContrasena(request.getCodigo(), request.getNuevaContrasena());
            return ResponseEntity.ok(mensaje);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
