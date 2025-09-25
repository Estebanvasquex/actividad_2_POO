package com.cooprkc.transacciones;

import com.cooprkc.modelo.Cuenta;

public class Retiro implements Transaccion {
    private final Cuenta cuenta;
    private final double monto;

    public Retiro(Cuenta cuenta, double monto) {
        this.cuenta = cuenta;
        this.monto = monto;
    }

    @Override
    public void ejecutar() throws SaldoInsuficienteException {
        boolean ok = cuenta.retirar(monto);
        if (!ok) {
            throw new SaldoInsuficienteException("Saldo insuficiente en cuenta " + cuenta.getNumeroCuenta() + " para retirar " + monto);
        }
    }

    @Override
    public double getMonto() {
        return monto;
    }

    @Override
    public String getTipo() {
        return "RETIRO";
    }

    public String getNumeroCuentaOrigen() {
        return cuenta.getNumeroCuenta();
    }
}

