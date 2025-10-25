package com.emijuan.coworkingreserves.coworkingreserves_backend.Repositories;

import com.emijuan.coworkingreserves.coworkingreserves_backend.models.Espacio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface EspacioRepository extends JpaRepository<Espacio,Long> {
    @Query("""
        SELECT e FROM Espacio e
        WHERE e.disponible = true
        AND NOT EXISTS (
            SELECT r FROM Reserva r
            WHERE r.espacio = e
              AND r.fechaReserva = :fecha
              AND (r.horaInicio < :horaFin AND r.horaFin > :horaInicio)
        )
    """)
    List<Espacio> findDisponibles(
            @Param("fecha") LocalDate fecha,
            @Param("horaInicio") LocalTime horaInicio,
            @Param("horaFin") LocalTime horaFin
    );
}
