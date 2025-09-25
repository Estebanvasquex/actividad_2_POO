package com.cooprkc.modelo;

public class CuentaAhorros extends Cuenta {
    private final double interes; // porcentaje, p. ej. 5.0 => 5%

    public CuentaAhorros(String numeroCuenta, double saldoInicial, double interes) {
        super(numeroCuenta, saldoInicial);
        this.interes = interes;
    }

    public double getInteres() {
        return interes;
    }

    /**
     * Aplica el interés actual al saldo (se realiza como un depósito del monto ganado).
     */
    public void aplicarInteres() {
        double incremento = getSaldo() * (interes / 100.0);
        if (incremento > 0) {
            depositar(incremento);
        }
    }
}


