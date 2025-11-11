package com.emijuan.coworkingreserves.coworkingreserves_backend.DTO;

public class LoginResponse {
    private String token;
    private String email;
    private String rol;
    private Long id; // <-- agregamos el id

    public LoginResponse(String token, String email, String rol, Long id) {
        this.token = token;
        this.email = email;
        this.rol = rol;
        this.id = id;
    }

    // Getters y setters
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
}
