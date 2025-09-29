package com.cooprkc.app;
import com.cooprkc.modelo.Cuenta;
import com.cooprkc.transacciones.Deposito;
import com.cooprkc.transacciones.Retiro;
import com.cooprkc.modelo.Socio;
import com.cooprkc.transacciones.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Cooperativa {
    private final List<Socio> socios;
    private final List<TransactionRecord> transacciones; // log de auditoría

    public Cooperativa() {
        this.socios = new ArrayList<>();
        this.transacciones = new ArrayList<>();
    }

    public void registrarSocio(Socio socio) {
        Objects.requireNonNull(socio, "El socio no debe ser nulo");
        socios.add(socio);
    }

    public List<Socio> listarSocios() {
        return new ArrayList<>(socios);
    }

    public boolean numeroCuentaExiste(String numeroCuenta) {
        return socios.stream()
                .flatMap(s -> s.getCuentas().stream())
                .anyMatch(c -> c.getNumeroCuenta().equals(numeroCuenta));
    }

    public boolean abrirCuenta(Socio socio, Cuenta cuenta) {
        if (numeroCuentaExiste(cuenta.getNumeroCuenta())) {
            return false;
        }
        socio.addCuenta(cuenta);
        return true;
    }

    public void ejecutarTransaccion(Transaccion t) {
        String tipo = t.getTipo();
        String numeroCuenta = "N/A";
        double monto = t.getMonto();
        boolean exitoso = false;
        String mensaje = "";

        try {
            if (t instanceof Deposito) numeroCuenta = ((Deposito) t).getNumeroCuentaDestino();
            else if (t instanceof Retiro) numeroCuenta = ((Retiro) t).getNumeroCuentaOrigen();
        } catch (Exception e) {
            // no crítico — dejamos el número como N/A
        }

        try {
            t.ejecutar();
            exitoso = true;
            mensaje = "Transacción ejecutada con éxito.";
        } catch (SaldoInsuficienteException ex) {
            exitoso = false;
            mensaje = ex.getMessage();
        } catch (Exception ex) {
            exitoso = false;
            mensaje = "Error ejecutando transacción: " + ex.getMessage();
        }

        // registrar el resultado en el log de auditoría
        TransactionRecord record = new TransactionRecord(LocalDateTime.now(), tipo, numeroCuenta, monto, exitoso, mensaje);
        transacciones.add(record);

        // informar por consola (puede cambiarse por logging corporativo)
        System.out.println(record.toString());
    }

    public List<TransactionRecord> getTransacciones() {
        return new ArrayList<>(transacciones);
    }

    // ----- Consultas funcionales solicitadas en el enunciado -----

    public void imprimirNombresSocios() {
        listarSocios().stream()
                .map(Socio::getNombre)
                .forEach(System.out::println);
    }

    public List<Cuenta> cuentasConSaldoMayorQue(double umbral) {
        return listarSocios().stream()
                .flatMap(socio -> socio.getCuentas().stream())
                .filter(c -> c.getSaldo() > umbral)
                .collect(Collectors.toList());
    }

    public double totalSaldos() {
        return listarSocios().stream()
                .flatMap(s -> s.getCuentas().stream())
                .map(c -> c.getSaldo())
                .reduce(0.0, Double::sum);
    }
}

