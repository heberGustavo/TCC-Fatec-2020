package com.apps.heber.restaurante.modelo;

import java.io.Serializable;

public class Mesa implements Serializable {

    private int numeroMesas;

    public int getNumeroMesas() {
        return numeroMesas;
    }

    public void setNumeroMesas(int numeroMesas) {
        this.numeroMesas = numeroMesas;
    }
}
