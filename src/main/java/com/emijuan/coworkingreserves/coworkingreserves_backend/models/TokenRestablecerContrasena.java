package com.emijuan.coworkingreserves.coworkingreserves_backend.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "token_restablecer_contrasena")
public class TokenRestablecerContrasena {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    private String codigoVerificacion;
    private LocalDateTime fechaExpiracion;
    private boolean usado = false;
    private LocalDateTime fechaCreacion = LocalDateTime.now();

    // --- Constructores ---
    public TokenRestablecerContrasena() {}

    public TokenRestablecerContrasena(Usuario usuario, String codigoVerificacion, LocalDateTime fechaExpiracion) {
        this.usuario = usuario;
        this.codigoVerificacion = codigoVerificacion;
        this.fechaExpiracion = fechaExpiracion;
        this.usado = false;
        this.fechaCreacion = LocalDateTime.now();
    }

    // --- Getters y Setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public String getCodigoVerificacion() { return codigoVerificacion; }
    public void setCodigoVerificacion(String codigoVerificacion) { this.codigoVerificacion = codigoVerificacion; }

    public LocalDateTime getFechaExpiracion() { return fechaExpiracion; }
    public void setFechaExpiracion(LocalDateTime fechaExpiracion) { this.fechaExpiracion = fechaExpiracion; }

    public boolean isUsado() { return usado; }
    public void setUsado(boolean usado) { this.usado = usado; }

    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }


}
