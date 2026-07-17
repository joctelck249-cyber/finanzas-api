package com.mjlc.finanzasapi.repositorio;

import com.mjlc.finanzasapi.modelo.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface MovimientoRepository extends JpaRepository<Movimiento, Integer> {

    List<Movimiento> findByAnioOrderByFechaDesc(Integer anio);

    @Query("SELECT COALESCE(SUM(CASE WHEN m.tipo = 'Ingreso' THEN m.monto ELSE -m.monto END), 0) " +
           "FROM Movimiento m WHERE m.anio = :anio")
    BigDecimal sumaMovimientos(@Param("anio") Integer anio);

    @Query("SELECT COALESCE(SUM(CASE WHEN m.tipo = 'Ingreso' THEN m.monto ELSE 0 END), 0) " +
           "FROM Movimiento m WHERE m.anio = :anio AND m.mes = :mes")
    BigDecimal sumaIngresosMes(@Param("anio") Integer anio, @Param("mes") Integer mes);

    @Query("SELECT COALESCE(SUM(CASE WHEN m.tipo = 'Egreso' THEN m.monto ELSE 0 END), 0) " +
           "FROM Movimiento m WHERE m.anio = :anio AND m.mes = :mes")
    BigDecimal sumaEgresosMes(@Param("anio") Integer anio, @Param("mes") Integer mes);
}