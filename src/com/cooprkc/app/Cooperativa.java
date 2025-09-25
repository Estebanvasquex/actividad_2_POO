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

    /**
     * Verifica si el número de cuenta ya existe en la cooperativa.
     */
    public boolean numeroCuentaExiste(String numeroCuenta) {
        return socios.stream()
                .flatMap(s -> s.getCuentas().stream())
                .anyMatch(c -> c.getNumeroCuenta().equals(numeroCuenta));
    }

    /**
     * Abre una cuenta para un socio, validando que el número de cuenta sea único.
     * Retorna true si la apertura fue exitosa, false si el número ya existe.
     */
    public boolean abrirCuenta(Socio socio, Cuenta cuenta) {
        if (numeroCuentaExiste(cuenta.getNumeroCuenta())) {
            return false;
        }
        socio.addCuenta(cuenta);
        return true;
    }

    /**
     * Ejecuta una transacción, la registra y maneja errores específicos con try-catch.
     *
     * Nota: se registran tanto transacciones exitosas como intentos fallidos para
     * fines de auditoría.
     */
    public void ejecutarTransaccion(Transaccion t) {
        String tipo = t.getTipo();
        String numeroCuenta = "N/A";
        double monto = t.getMonto();
        boolean exitoso = false;
        String mensaje = "";

        // intentamos extraer número de cuenta si la transacción provee método concreto
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

    /**
     * Lista los nombres de todos los socios usando streams (map + forEach).
     */
    public void imprimirNombresSocios() {
        listarSocios().stream()
                .map(Socio::getNombre)
                .forEach(System.out::println);
    }

    /**
     * Filtra y devuelve cuentas con saldo mayor a un umbral.
     */
    public List<Cuenta> cuentasConSaldoMayorQue(double umbral) {
        return listarSocios().stream()
                .flatMap(socio -> socio.getCuentas().stream())
                .filter(c -> c.getSaldo() > umbral)
                .collect(Collectors.toList());
    }

    /**
     * Calcula el total de saldos en la cooperativa usando reduce().
     */
    public double totalSaldos() {
        return listarSocios().stream()
                .flatMap(s -> s.getCuentas().stream())
                .map(c -> c.getSaldo())
                .reduce(0.0, Double::sum);
    }
}

