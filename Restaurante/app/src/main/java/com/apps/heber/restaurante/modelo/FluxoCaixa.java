package com.apps.heber.restaurante.modelo;

import java.io.Serializable;
import java.util.Date;

public class FluxoCaixa implements Serializable {

    private int id;
    private String tipo;
    private String dataFluxo;
    private double receita;
    private double despesa;

    public FluxoCaixa() {
    }

    public FluxoCaixa(int id, String tipo, String dataFluxo, double receita, double despesa) {
        this.id = id;
        this.tipo = tipo;
        this.dataFluxo = dataFluxo;
        this.receita = receita;
        this.despesa = despesa;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDataFluxo() {
        return dataFluxo;
    }

    public void setDataFluxo(String dataFluxo) {
        this.dataFluxo = dataFluxo;
    }

    public double getReceita() {
        return receita;
    }

    public void setReceita(double receita) {
        this.receita = receita;
    }

    public double getDespesa() {
        return despesa;
    }

    public void setDespesa(double despesa) {
        this.despesa = despesa;
    }
}
