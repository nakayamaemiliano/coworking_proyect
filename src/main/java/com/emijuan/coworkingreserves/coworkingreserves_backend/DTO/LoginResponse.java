package com.emijuan.coworkingreserves.coworkingreserves_backend.DTO;

public class LoginResponse {
    private String token;
    private String email;
    private String rol;

    public LoginResponse(String token, String email, String rol) {
        this.token = token;
        this.email = email;
        this.rol = rol;
    }

    // getters
    public String getToken() { return token; }
    public String getEmail() { return email; }
    public String getRol() { return rol; }
}
