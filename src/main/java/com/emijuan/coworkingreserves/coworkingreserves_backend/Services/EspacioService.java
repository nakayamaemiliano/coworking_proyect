package com.emijuan.coworkingreserves.coworkingreserves_backend.Services;

import com.emijuan.coworkingreserves.coworkingreserves_backend.Repositories.EspacioRepository;
import com.emijuan.coworkingreserves.coworkingreserves_backend.models.Espacio;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class EspacioService {

    private final EspacioRepository espacioRepository;

    public EspacioService(EspacioRepository espacioRepository) {
        this.espacioRepository = espacioRepository;
    }

    // 🔹 Crear espacio
    public Espacio crearEspacio(Espacio espacio) {
        return espacioRepository.save(espacio);
    }

    // 🔹 Listar todos los espacios
    public List<Espacio> listarTodos() {
        return espacioRepository.findAll();
    }

    // 🔹 Buscar espacio por ID
    public Espacio buscarPorId(Long id) {
        return espacioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Espacio no encontrado con ID: " + id));
    }

    // 🔹 Actualizar espacio
    public Espacio actualizarEspacio(Long id, Espacio nuevo) {
        Espacio existente = buscarPorId(id);
        existente.setNombre(nuevo.getNombre());
        existente.setDescripcion(nuevo.getDescripcion());
        existente.setCapacidad(nuevo.getCapacidad());
        existente.setUbicacion(nuevo.getUbicacion());
        existente.setImagenUrl(nuevo.getImagenUrl());
        existente.setDisponible(nuevo.getDisponible());
        return espacioRepository.save(existente);
    }

    // 🔹 Eliminar espacio
    public void eliminarEspacio(Long id) {
        if (!espacioRepository.existsById(id)) {
            throw new RuntimeException("No se encontró el espacio con ID: " + id);
        }
        espacioRepository.deleteById(id);
    }

    // 🔹 Listar espacios disponibles (ya lo tenías)
    public List<Espacio> listarDisponibles(LocalDate fecha, LocalTime horaInicio, LocalTime horaFin) {
        if (horaFin.isBefore(horaInicio)) {
            throw new IllegalArgumentException("La hora de fin debe ser mayor a la de inicio");
        }
        return espacioRepository.findDisponibles(fecha, horaInicio, horaFin);
    }
}
