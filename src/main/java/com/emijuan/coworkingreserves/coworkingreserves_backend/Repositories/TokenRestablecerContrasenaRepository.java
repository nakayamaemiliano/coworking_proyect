package com.emijuan.coworkingreserves.coworkingreserves_backend.Repositories;

import com.emijuan.coworkingreserves.coworkingreserves_backend.models.TokenRestablecerContrasena;
import com.emijuan.coworkingreserves.coworkingreserves_backend.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface TokenRestablecerContrasenaRepository extends JpaRepository<TokenRestablecerContrasena,Long> {
    Optional<TokenRestablecerContrasena> findByCodigoVerificacion(String codigoVerificacion);

    void deleteAllByUsuario(Usuario usuario);
}
