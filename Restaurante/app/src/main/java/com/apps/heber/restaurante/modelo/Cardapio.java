package com.apps.heber.restaurante.modelo;

import java.io.Serializable;

public class Cardapio implements Serializable {

    private Long idCardapio;
    private String nomeProduto;
    private String ingredientes;
    private double valor;
    private int idCategoria;
    private String nomeCategoria;

    public Cardapio() {
    }

    public Cardapio(Long idCardapio, String nomeProduto, String ingredientes, double valor, int idCategoria, String nomeCategoria) {
        this.idCardapio = idCardapio;
        this.nomeProduto = nomeProduto;
        this.ingredientes = ingredientes;
        this.valor = valor;
        this.idCategoria = idCategoria;
        this.nomeCategoria = nomeCategoria;
    }

    public Long getIdCardapio() {
        return idCardapio;
    }

    public void setIdCardapio(Long idCardapio) {
        this.idCardapio = idCardapio;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public String getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(String ingredientes) {
        this.ingredientes = ingredientes;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getNomeCategoria() {
        return nomeCategoria;
    }

    public void setNomeCategoria(String nomeCategoria) {
        this.nomeCategoria = nomeCategoria;
    }

    @Override
    public String toString() {
        return "Cardapio{" +
                "idCardapio=" + idCardapio +
                ", nomeProduto='" + nomeProduto + '\'' +
                ", ingredientes='" + ingredientes + '\'' +
                ", valor=" + valor +
                ", idCategoria=" + idCategoria +
                ", nomeCategoria='" + nomeCategoria + '\'' +
                '}';
    }
}
