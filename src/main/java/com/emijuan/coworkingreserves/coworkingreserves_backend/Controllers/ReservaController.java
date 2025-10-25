package com.emijuan.coworkingreserves.coworkingreserves_backend.Controllers;

import com.emijuan.coworkingreserves.coworkingreserves_backend.DTO.ReservaRequest;
import com.emijuan.coworkingreserves.coworkingreserves_backend.DTO.ReservaResponse;
import com.emijuan.coworkingreserves.coworkingreserves_backend.Services.ReservaService;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservas")
public class ReservaController {
    private final ReservaService reservaService;

    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    // 🔹 Crear una reserva
    @PostMapping("/{usuarioId}")
    @Transactional
    public ResponseEntity<ReservaResponse> crearReserva(
            @PathVariable Long usuarioId,
            @RequestBody ReservaRequest request
    ) {
        ReservaResponse response = reservaService.crear(usuarioId, request);
        return ResponseEntity.ok(response);
    }

    // 🔹 Listar todas las reservas (solo para admin)
    @GetMapping
    public ResponseEntity<List<ReservaResponse>> listarTodas() {
        return ResponseEntity.ok(reservaService.listarTodas());
    }

    // 🔹 Listar reservas de un usuario específico
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<ReservaResponse>> listarPorUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(
                reservaService.listarTodas()
                        .stream()
                        .filter(r -> r.getUsuarioId().equals(usuarioId))
                        .toList()
        );
    }

    // 🔹 Actualizar una reserva existente
    @PutMapping("/{reservaId}")
    public ResponseEntity<ReservaResponse> actualizarReserva(
            @PathVariable Long reservaId,
            @RequestBody ReservaRequest request
    ) {
        ReservaResponse actualizada = reservaService.actualizar(reservaId, request);
        return ResponseEntity.ok(actualizada);
    }

    // 🔹 Eliminar una reserva
    @DeleteMapping("/{reservaId}")
    public ResponseEntity<Void> eliminarReserva(@PathVariable Long reservaId) {
        reservaService.eliminar(reservaId);
        return ResponseEntity.noContent().build();
    }
}
