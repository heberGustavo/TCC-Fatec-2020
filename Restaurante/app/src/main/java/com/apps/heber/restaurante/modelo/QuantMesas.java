package com.apps.heber.restaurante.modelo;

import java.io.Serializable;

public class QuantMesas implements Serializable {

    private int idMesa;
    private int numeroMesa;

    public int getIdMesa() {
        return idMesa;
    }

    public void setIdMesa(int idMesa) {
        this.idMesa = idMesa;
    }

    public int getNumeroMesa() {
        return numeroMesa;
    }

    public void setNumeroMesa(int numeroMesa) {
        this.numeroMesa = numeroMesa;
    }

    @Override
    public String toString() {
        return "QuantMesas{" +
                "idMesa=" + idMesa +
                ", numeroMesa=" + numeroMesa +
                '}';
    }

}
