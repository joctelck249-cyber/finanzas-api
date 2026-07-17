package com.mjlc.finanzasapi.dto;

import java.math.BigDecimal;

public class ResumenMensualResponse {
    private BigDecimal ingresos;
    private BigDecimal egresos;
    private BigDecimal balance;

    public ResumenMensualResponse(BigDecimal ingresos, BigDecimal egresos) {
        this.ingresos = ingresos;
        this.egresos = egresos;
        this.balance = ingresos.subtract(egresos);
    }

    public BigDecimal getIngresos() { return ingresos; }
    public BigDecimal getEgresos() { return egresos; }
    public BigDecimal getBalance() { return balance; }
}