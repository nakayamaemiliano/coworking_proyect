package com.emijuan.coworkingreserves.coworkingreserves_backend.DTO;

public class ResetPasswordRequest {
    private String codigo;
    private String nuevaContrasena;

    public ResetPasswordRequest() {}

    public ResetPasswordRequest(String codigo, String nuevaContrasena) {
        this.codigo = codigo;
        this.nuevaContrasena = nuevaContrasena;
    }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getNuevaContrasena() { return nuevaContrasena; }
    public void setNuevaContrasena(String nuevaContrasena) { this.nuevaContrasena = nuevaContrasena; }
}
