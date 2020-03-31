package com.apps.heber.restaurante.modelo;

public class QuantMesa {

    private int id;
    private int numero;

    public QuantMesa() {
    }

    public QuantMesa(int id, int numero) {
        this.id = id;
        this.numero = numero;
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
}