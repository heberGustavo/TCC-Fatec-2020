package com.apps.heber.restaurante.modelo;

import java.io.Serializable;

public class PedidoNovo implements Serializable {

    private int idItemPedido;
    private String nomeProduto;
    private String ingredientes;
    private int quantidade;
    private double valorUnitario;
    private double valorTotal;
    private String observacao;
    private String nomeCategoria;
    private int idMesa;

    public PedidoNovo() {
    }

    public PedidoNovo(int idItemPedido, String nomeProduto, String ingredientes, int quantidade, double valorUnitario, double valorTotal, String observacao, String nomeCategoria, int idMesa) {
        this.idItemPedido = idItemPedido;
        this.nomeProduto = nomeProduto;
        this.ingredientes = ingredientes;
        this.quantidade = quantidade;
        this.valorUnitario = valorUnitario;
        this.valorTotal = valorTotal;
        this.observacao = observacao;
        this.nomeCategoria = nomeCategoria;
        this.idMesa = idMesa;
    }

    @Override
    public String toString() {
        return "PedidoNovo{" +
                "idItemPedido=" + idItemPedido +
                ", nomeProduto='" + nomeProduto + '\'' +
                ", ingredientes='" + ingredientes + '\'' +
                ", quantidade=" + quantidade +
                ", valorUnitario=" + valorUnitario +
                ", valorTotal=" + valorTotal +
                ", observacao='" + observacao + '\'' +
                ", nomeCategoria='" + nomeCategoria + '\'' +
                ", idMesa=" + idMesa +
                '}';
    }

    public int getIdItemPedido() {
        return idItemPedido;
    }

    public void setIdItemPedido(int idItemPedido) {
        this.idItemPedido = idItemPedido;
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

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
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
