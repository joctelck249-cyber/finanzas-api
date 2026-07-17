package com.mjlc.finanzasapi.modelo;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "configuracion_anual")
public class ConfiguracionAnual {

    @Id
    private Integer anio;

    @Column(name = "saldo_inicial")
    private BigDecimal saldoInicial;

    public Integer getAnio() { return anio; }
    public void setAnio(Integer anio) { this.anio = anio; }

    public BigDecimal getSaldoInicial() { return saldoInicial; }
    public void setSaldoInicial(BigDecimal saldoInicial) { this.saldoInicial = saldoInicial; }
}