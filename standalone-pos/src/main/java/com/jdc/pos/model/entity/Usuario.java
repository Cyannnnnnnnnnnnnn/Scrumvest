package com.jdc.pos.model.entity;

import lombok.Data;

@Data
public class Usuario {
    private String nombre;
    private String rol;
    private Account cuenta;

    public Usuario() {
    }

    public Usuario(Account cuenta) {
        this.nombre = cuenta.getName();
        this.rol = cuenta.getRol().name();
        this.cuenta = cuenta;
    }

    public Account getAccount() {
        return cuenta;
    }
}
