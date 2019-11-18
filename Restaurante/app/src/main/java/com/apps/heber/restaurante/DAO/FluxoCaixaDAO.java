package com.apps.heber.restaurante.DAO;

import java.io.Serializable;

public class FluxoCaixaDAO implements Serializable {

    private int id;
    private double somaEntrada;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getSomaEntrada() {
        return somaEntrada;
    }

    public void setSomaEntrada(double somaEntrada) {
        this.somaEntrada = somaEntrada;
    }

    @Override
    public String toString() {
        return "FluxoCaixaDAO{" +
                "id=" + id +
                ", somaEntrada=" + somaEntrada +
                '}';
    }
}
