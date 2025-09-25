package com.cooprkc.modelo;


import java.util.ArrayList;
import java.util.List;
import java.util.Collections;


public class Socio {
    private final String nombre;
    private final String cedula;
    private final List<Cuenta> cuentas;

    public Socio(String nombre, String cedula) {
        this.nombre = nombre;
        this.cedula = cedula;
        this.cuentas = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public String getCedula() {
        return cedula;
    }

    /**
     * Devuelve una vista inmodificable de las cuentas para proteger el estado interno.
     */
    public List<Cuenta> getCuentas() {
        return Collections.unmodifiableList(cuentas);
    }

    /**
     * Añade una cuenta al socio (uso controlado). No valida duplicados globales —
     * eso lo debe hacer la cooperativa al abrir cuentas para garantizar unicidad.
     */
    public void addCuenta(Cuenta cuenta) {
        cuentas.add(cuenta);
    }

    public void removeCuenta(Cuenta cuenta) {
        cuentas.remove(cuenta);
    }
}


