package com.cooprkc.app;

import com.cooprkc.modelo.CuentaAhorros;
import com.cooprkc.modelo.Socio;
import com.cooprkc.transacciones.Deposito;
import com.cooprkc.transacciones.Retiro;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CooperativaTest {

    @Test
    void testTransaccionExitosa() {
        Cooperativa coop = new Cooperativa();
        Socio socio = new Socio("Ana", "111");
        CuentaAhorros cuenta = new CuentaAhorros("100", 1000, 2);
        coop.registrarSocio(socio);
        coop.abrirCuenta(socio, cuenta);

        coop.ejecutarTransaccion(new Deposito(cuenta, 500));
        assertEquals(1500, cuenta.getSaldo(), 0.01);


        assertTrue(coop.getTransacciones().get(0).isExitoso());
    }

    @Test
    void testTransaccionFallidaPorSaldoInsuficiente() {
        Cooperativa coop = new Cooperativa();
        Socio socio = new Socio("Luis", "222");
        CuentaAhorros cuenta = new CuentaAhorros("200", 100, 2);
        coop.registrarSocio(socio);
        coop.abrirCuenta(socio, cuenta);

        coop.ejecutarTransaccion(new Retiro(cuenta, 500));


        assertFalse(coop.getTransacciones().get(0).isExitoso());
        assertTrue(coop.getTransacciones().get(0).getMensaje().contains("Saldo insuficiente"));
    }
}