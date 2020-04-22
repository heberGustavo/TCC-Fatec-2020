package com.apps.heber.restaurante.modelo;

import java.io.Serializable;

public class QuantMesa implements Serializable {

    private int id;
    private int numero;
    private int status;

    public QuantMesa() {
    }

    public QuantMesa(int id, int numero, int status) {
        this.id = id;
        this.numero = numero;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}