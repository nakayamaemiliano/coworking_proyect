package com.emijuan.coworkingreserves.coworkingreserves_backend.Services;

import com.emijuan.coworkingreserves.coworkingreserves_backend.DTO.ReservaRequest;
import com.emijuan.coworkingreserves.coworkingreserves_backend.DTO.ReservaResponse;
import com.emijuan.coworkingreserves.coworkingreserves_backend.Repositories.EspacioRepository;
import com.emijuan.coworkingreserves.coworkingreserves_backend.Repositories.ReservaRepository;
import com.emijuan.coworkingreserves.coworkingreserves_backend.Repositories.UsuarioRepository;
import com.emijuan.coworkingreserves.coworkingreserves_backend.models.Espacio;
import com.emijuan.coworkingreserves.coworkingreserves_backend.models.EstadoReserva;
import com.emijuan.coworkingreserves.coworkingreserves_backend.models.Reserva;
import com.emijuan.coworkingreserves.coworkingreserves_backend.models.Usuario;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservaService {
    private final ReservaRepository reservaRepo;
    private final UsuarioRepository usuarioRepo;
    private final EspacioRepository espacioRepo;

    public ReservaService(ReservaRepository reservaRepo, UsuarioRepository usuarioRepo, EspacioRepository espacioRepo) {
        this.reservaRepo = reservaRepo;
        this.usuarioRepo = usuarioRepo;
        this.espacioRepo = espacioRepo;
    }

    // 🟢 Mapeo de entidad -> DTO
    private ReservaResponse map(Reserva r) {
        return new ReservaResponse(
                r.getId(),
                r.getUsuario().getId(),
                r.getEspacio().getId(),
                r.getFechaReserva(),
                r.getHoraInicio(),
                r.getHoraFin(),
                r.getEstado().name()
        );
    }

    // 🟣 Listar todas las reservas
    public List<ReservaResponse> listarTodas() {
        return reservaRepo.findAll().stream().map(this::map).collect(Collectors.toList());
    }

    // 🟢 Crear nueva reserva
    @Transactional
    public ReservaResponse crear(Long usuarioId, ReservaRequest req) {
        Usuario usuario = usuarioRepo.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no existe"));
        Espacio espacio = espacioRepo.findById(req.getEspacioId())
                .orElseThrow(() -> new RuntimeException("Espacio no existe"));

        if (!espacio.getDisponible()) {
            throw new RuntimeException("El espacio no está disponible actualmente.");
        }

        List<Reserva> ocupadas = reservaRepo.verificarDisponibilidad(
                espacio.getId(),
                req.getFechaReserva(),
                req.getHoraInicio(),
                req.getHoraFin()
        );

        if (!ocupadas.isEmpty()) {
            throw new RuntimeException("⛔ El espacio no está disponible en ese horario.");
        }

        Reserva nueva = new Reserva(usuario, espacio, req.getFechaReserva(), req.getHoraInicio(), req.getHoraFin(), EstadoReserva.PENDIENTE);
        return map(reservaRepo.save(nueva));
    }

    // 🟡 Actualizar una reserva existente
    @Transactional
    public ReservaResponse actualizar(Long reservaId, ReservaRequest req) {
        Reserva reserva = reservaRepo.findById(reservaId)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));

        Espacio espacio = espacioRepo.findById(req.getEspacioId())
                .orElseThrow(() -> new RuntimeException("Espacio no encontrado"));

        // Validar disponibilidad para el nuevo horario
        List<Reserva> ocupadas = reservaRepo.verificarDisponibilidad(
                espacio.getId(),
                req.getFechaReserva(),
                req.getHoraInicio(),
                req.getHoraFin()
        );

        if (!ocupadas.isEmpty() && ocupadas.stream().noneMatch(r -> r.getId().equals(reservaId))) {
            throw new RuntimeException("⛔ El espacio no está disponible en ese nuevo horario.");
        }

        reserva.setEspacio(espacio);
        reserva.setFechaReserva(req.getFechaReserva());
        reserva.setHoraInicio(req.getHoraInicio());
        reserva.setHoraFin(req.getHoraFin());

        Reserva actualizada = reservaRepo.save(reserva);
        return map(actualizada);
    }

    // 🔴 Eliminar una reserva
    @Transactional
    public void eliminar(Long id) {
        Reserva reserva = reservaRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada con ID: " + id));
        reservaRepo.delete(reserva);
    }
}
