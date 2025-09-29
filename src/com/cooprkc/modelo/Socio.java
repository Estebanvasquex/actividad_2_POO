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

    public List<Cuenta> getCuentas() {
        return Collections.unmodifiableList(cuentas);
    }

    public void addCuenta(Cuenta cuenta) {
        cuentas.add(cuenta);
    }

    public void removeCuenta(Cuenta cuenta) {
        cuentas.remove(cuenta);
    }
}


