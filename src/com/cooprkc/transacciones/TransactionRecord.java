package com.cooprkc.transacciones;
import java.time.LocalDateTime;

public class TransactionRecord {
    private final LocalDateTime timestamp;
    private final String tipo;
    private final String numeroCuenta;
    private final double monto;
    private final boolean exitoso;
    private final String mensaje; // información adicional o error

    public TransactionRecord(LocalDateTime timestamp, String tipo, String numeroCuenta, double monto, boolean exitoso, String mensaje) {
        this.timestamp = timestamp;
        this.tipo = tipo;
        this.numeroCuenta = numeroCuenta;
        this.monto = monto;
        this.exitoso = exitoso;
        this.mensaje = mensaje;
    }

    public LocalDateTime getTimestamp() { return timestamp; }
    public String getTipo() { return tipo; }
    public String getNumeroCuenta() { return numeroCuenta; }
    public double getMonto() { return monto; }
    public boolean isExitoso() { return exitoso; }
    public String getMensaje() { return mensaje; }

    @Override
    public String toString() {
        return "[" + timestamp + "] " + tipo + " Cuenta:" + numeroCuenta + " Monto:" + monto + " Exitoso:" + exitoso + " Msg:" + mensaje;
    }
}
