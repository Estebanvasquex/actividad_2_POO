package com.cooprkc.transacciones;

public interface Transaccion {
    void ejecutar() throws SaldoInsuficienteException;
    double getMonto();
    String getTipo();
}