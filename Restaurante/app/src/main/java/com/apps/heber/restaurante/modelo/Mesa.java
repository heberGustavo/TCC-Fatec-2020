package com.apps.heber.restaurante.modelo;

import java.io.Serializable;

public class Mesa implements Serializable {

    private String numeroMesas;

    public String getNumeroMesas() {
        return numeroMesas;
    }

    public void setNumeroMesas(String numeroMesas) {
        this.numeroMesas = numeroMesas;
    }
}
