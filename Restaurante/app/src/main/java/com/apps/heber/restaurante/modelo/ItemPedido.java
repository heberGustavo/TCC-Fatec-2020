package com.apps.heber.restaurante.modelo;

import android.support.annotation.NonNull;

import java.io.Serializable;

public class ItemPedido implements Serializable {

    private int idItemCardapio;
    private String nomeProduto;
    private String ingrediente;
    private int quantidade;
    private double valorUnitario;
    private double valorTotal;
    private String observacoes;
    private int idCategoria;
    private String nomeCategoria;
    private int idMesa;

    public ItemPedido() {
    }

    public ItemPedido(int idItemCardapio, String nomeProduto, String ingrediente, int quantidade, double valorUnitario, double valorTotal, String observacoes, String nomeCategoria, int idMesa, int idCategoria) {
        this.idItemCardapio = idItemCardapio;
        this.nomeProduto = nomeProduto;
        this.ingrediente = ingrediente;
        this.quantidade = quantidade;
        this.valorUnitario = valorUnitario;
        this.valorTotal = valorTotal;
        this.observacoes = observacoes;
        this.nomeCategoria = nomeCategoria;
        this.idMesa = idMesa;
        this.idCategoria = idCategoria;
    }

    @Override
    public String toString() {
        return "ItemPedido{" +
                "idItemCardapio=" + idItemCardapio +
                ", nomeProduto='" + nomeProduto + '\'' +
                ", ingrediente='" + ingrediente + '\'' +
                ", quantidade=" + quantidade +
                ", valorUnitario=" + valorUnitario +
                ", valorTotal=" + valorTotal +
                ", observacoes='" + observacoes + '\'' +
                ", idCategoria=" + idCategoria +
                ", nomeCategoria='" + nomeCategoria + '\'' +
                ", idMesa=" + idMesa +
                '}';
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public int getIdItemCardapio() {
        return idItemCardapio;
    }

    public void setIdItemCardapio(int idItemCardapio) {
        this.idItemCardapio = idItemCardapio;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public String getIngrediente() {
        return ingrediente;
    }

    public void setIngrediente(String ingrediente) {
        this.ingrediente = ingrediente;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public double getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(double valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public String getNomeCategoria() {
        return nomeCategoria;
    }

    public void setNomeCategoria(String nomeCategoria) {
        this.nomeCategoria = nomeCategoria;
    }

    public int getIdMesa() {
        return idMesa;
    }

    public void setIdMesa(int idMesa) {
        this.idMesa = idMesa;
    }
}
