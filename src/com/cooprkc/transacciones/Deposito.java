package com.cooprkc.transacciones;

import com.cooprkc.modelo.Cuenta;

public class Deposito implements Transaccion {
    private final Cuenta cuenta;
    private final double monto;

    public Deposito(Cuenta cuenta, double monto) {
        this.cuenta = cuenta;
        this.monto = monto;
    }

    @Override
    public void ejecutar() {
        // depósitos no lanzan SaldoInsuficienteException
        cuenta.depositar(monto);
    }

    @Override
    public double getMonto() {
        return monto;
    }

    @Override
    public String getTipo() {
        return "DEPOSITO";
    }

    public String getNumeroCuentaDestino() {
        return cuenta.getNumeroCuenta();
    }
}

