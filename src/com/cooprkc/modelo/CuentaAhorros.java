package com.cooprkc.modelo;

public class CuentaAhorros extends Cuenta {
    private final double interes;

    public CuentaAhorros(String numeroCuenta, double saldoInicial, double interes) {
        super(numeroCuenta, saldoInicial);
        this.interes = interes;
    }

    public double getInteres() {
        return interes;
    }

    public void aplicarInteres() {
        double incremento = getSaldo() * (interes / 100.0);
        if (incremento > 0) {
            depositar(incremento);
        }
    }
}


