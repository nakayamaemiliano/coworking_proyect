package com.emijuan.coworkingreserves.coworkingreserves_backend.DTO;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservaResponse {
    private Long id;
    private Long usuarioId;
    private Long espacioId;
    private LocalDate fechaReserva;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private String estado;

    public ReservaResponse(Long id, Long usuarioId, Long espacioId,
                           LocalDate fechaReserva, LocalTime horaInicio,
                           LocalTime horaFin, String estado) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.espacioId = espacioId;
        this.fechaReserva = fechaReserva;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.estado = estado;
    }

    // Getters
    public Long getId() { return id; }
    public Long getUsuarioId() { return usuarioId; }
    public Long getEspacioId() { return espacioId; }
    public LocalDate getFechaReserva() { return fechaReserva; }
    public LocalTime getHoraInicio() { return horaInicio; }
    public LocalTime getHoraFin() { return horaFin; }
    public String getEstado() { return estado; }
}
