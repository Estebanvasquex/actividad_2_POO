package com.cooprkc.modelo;

public class Cuenta {
    private final String numeroCuenta;
    private double saldo;

    public Cuenta(String numeroCuenta, double saldoInicial) {
        this.numeroCuenta = numeroCuenta;
        this.saldo = saldoInicial;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public double getSaldo() {
        return saldo;
    }

    public void depositar(double monto) {
        if (monto <= 0) throw new IllegalArgumentException("El monto a depositar debe ser positivo.");
        this.saldo += monto;
    }

    public boolean retirar(double monto) {
        if (monto <= 0) throw new IllegalArgumentException("El monto a retirar debe ser positivo.");
        if (saldo >= monto) {
            this.saldo -= monto;
            return true;
        }
        return false;
    }
}


