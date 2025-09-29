package com.cooprkc.app;

import com.cooprkc.modelo.CuentaAhorros;
import com.cooprkc.transacciones.Deposito;
import com.cooprkc.transacciones.Retiro;
import com.cooprkc.modelo.Socio;

import com.cooprkc.transacciones.Transaccion;

import java.util.List;

public class CoopRKCApp {
    public static void main(String[] args) {
        Cooperativa cooperativa = new Cooperativa();

        // Crear socios
        Socio socio1 = new Socio("Juan Perez", "12345678");
        Socio socio2 = new Socio("Maria Lopez", "87654321");
        cooperativa.registrarSocio(socio1);
        cooperativa.registrarSocio(socio2);

        // Abrir cuentas de ahorro (la cooperativa valida duplicados)
        CuentaAhorros cuenta1 = new CuentaAhorros("001", 100000, 0.05);
        CuentaAhorros cuenta2 = new CuentaAhorros("002", 200000, 0.03);
        CuentaAhorros cuenta3 = new CuentaAhorros("003", 300000, 0.03);
        boolean ok1 = cooperativa.abrirCuenta(socio1, cuenta1);
        boolean ok2 = cooperativa.abrirCuenta(socio2, cuenta2);
        if (!ok1 || !ok2) System.out.println("Error: no se pudieron abrir todas las cuentas por duplicado.");

        // Registrar más socios y cuentas para demostrar streams
        Socio socio3 = new Socio("Luis Rodriguez", "55555555");
        cooperativa.registrarSocio(socio3);
        cooperativa.abrirCuenta(socio3, new CuentaAhorros("003", 600000, 2.5));

        // Realizar transacciones (siempre pasar por cooperativa para registro y manejo de errores)
        Transaccion deposito = new Deposito(cuenta1, -699999);
        cooperativa.ejecutarTransaccion(deposito);

        Transaccion retiro = new Retiro(cuenta3, 1200000);
        cooperativa.ejecutarTransaccion(retiro); // si hay error, será capturado e informado

        // Intento de retiro que fallará para demostrar manejo de errores
        Transaccion retiroFallido = new Retiro(cuenta2, 5000000);
        cooperativa.ejecutarTransaccion(retiroFallido);

        // Aplicar interés a cuentas de ahorro explícitamente
        cuenta1.aplicarInteres();
        cuenta2.aplicarInteres();

        // Salidas solicitadas usando programación funcional
        System.out.println("--- Socios registrados ---");
        cooperativa.imprimirNombresSocios(); // map + forEach

        System.out.println("--- Cuentas con saldo mayor a 500000 ---");
        List<com.cooprkc.modelo.Cuenta> cuentasFiltradas = cooperativa.cuentasConSaldoMayorQue(500000);
        cuentasFiltradas.forEach(c -> System.out.println(c.getNumeroCuenta() + " -> " + c.getSaldo()));

        System.out.println("--- Total saldo cooperativa (reduce) ---");
        double total = cooperativa.totalSaldos();
        System.out.println("Total saldo cooperativa: " + total);

        System.out.println("--- Log de transacciones (auditoría) ---");
        cooperativa.getTransacciones().forEach(System.out::println);
    }
}
