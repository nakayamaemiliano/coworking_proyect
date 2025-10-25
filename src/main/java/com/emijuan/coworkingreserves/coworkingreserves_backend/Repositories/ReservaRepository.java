package com.emijuan.coworkingreserves.coworkingreserves_backend.Repositories;

import com.emijuan.coworkingreserves.coworkingreserves_backend.models.Reserva;
import com.emijuan.coworkingreserves.coworkingreserves_backend.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva,Long> {

    List<Reserva> findByUsuario(Usuario usuario);

    @Query("""
        SELECT r FROM Reserva r 
        WHERE r.espacio.id = :espacioId
        AND r.fechaReserva = :fecha
        AND (r.horaInicio < :horaFin AND r.horaFin > :horaInicio)
    """)
    List<Reserva> verificarDisponibilidad(
            @Param("espacioId") Long espacioId,
            @Param("fecha") LocalDate fecha,
            @Param("horaInicio") LocalTime horaInicio,
            @Param("horaFin") LocalTime horaFin
    );

}
