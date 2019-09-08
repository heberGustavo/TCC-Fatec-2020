package com.apps.heber.restaurante.modelo;

import java.io.Serializable;

public class Pedido implements Serializable {

    private String numeroMesa;

    public String getNumeroMesa() {
        return numeroMesa;
    }

    public void setNumeroMesa(String numeroMesa) {
        this.numeroMesa = numeroMesa;
    }
}
