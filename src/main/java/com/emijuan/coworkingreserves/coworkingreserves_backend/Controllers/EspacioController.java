package com.emijuan.coworkingreserves.coworkingreserves_backend.Controllers;

import com.emijuan.coworkingreserves.coworkingreserves_backend.Services.EspacioService;
import com.emijuan.coworkingreserves.coworkingreserves_backend.models.Espacio;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api/espacios")
public class EspacioController {
    private final EspacioService espacioService;

    public EspacioController(EspacioService espacioService) {
        this.espacioService = espacioService;
    }

    // 🟢 Crear espacio
    @PostMapping
    public ResponseEntity<Espacio> crearEspacio(@RequestBody Espacio espacio) {
        return ResponseEntity.ok(espacioService.crearEspacio(espacio));
    }

    // 🟣 Listar todos los espacios
    @GetMapping
    public ResponseEntity<List<Espacio>> listarTodos() {
        return ResponseEntity.ok(espacioService.listarTodos());
    }

    // 🔵 Buscar espacio por ID
    @GetMapping("/{id}")
    public ResponseEntity<Espacio> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(espacioService.buscarPorId(id));
    }

    // 🟠 Actualizar espacio
    @PutMapping("/{id}")
    public ResponseEntity<Espacio> actualizarEspacio(
            @PathVariable Long id,
            @RequestBody Espacio espacio) {
        return ResponseEntity.ok(espacioService.actualizarEspacio(id, espacio));
    }

    // 🔴 Eliminar espacio
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEspacio(@PathVariable Long id) {
        espacioService.eliminarEspacio(id);
        return ResponseEntity.noContent().build();
    }

    // 🟢 Listar espacios disponibles
    @GetMapping("/disponibles")
    public ResponseEntity<List<Espacio>> listarDisponibles(
            @RequestParam LocalDate fecha,
            @RequestParam LocalTime horaInicio,
            @RequestParam LocalTime horaFin) {
        return ResponseEntity.ok(espacioService.listarDisponibles(fecha, horaInicio, horaFin));
    }
}
