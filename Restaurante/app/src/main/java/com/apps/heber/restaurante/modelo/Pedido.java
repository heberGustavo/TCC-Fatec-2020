package com.apps.heber.restaurante.modelo;

import android.support.annotation.NonNull;

import java.io.Serializable;

public class Pedido implements Serializable {

    private Long idItemPedido;
    private String nomeProduto;
    private Long fkIdCategoria; //Categoria
    private String ingredientes;
    private int quantidade;
    private double valorUnitario;
    private double valorTotal;
    private String observacao;
    private int posicaoMesa;

    public Long getIdItemPedido() {
        return idItemPedido;
    }

    public void setIdItemPedido(Long idItemPedido) {
        this.idItemPedido = idItemPedido;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public Long getFkIdCategoria() {
        return fkIdCategoria;
    }

    public void setFkIdCategoria(Long fkIdCategoria) {
        this.fkIdCategoria = fkIdCategoria;
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

    public int getPosicaoMesa() {
        return posicaoMesa;
    }

    public void setPosicaoMesa(int posicaoMesa) {
        this.posicaoMesa = posicaoMesa;
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "idItemPedido=" + idItemPedido +
                ", nomeProduto='" + nomeProduto + '\'' +
                ", fkIdCategoria=" + fkIdCategoria +
                ", ingredientes='" + ingredientes + '\'' +
                ", quantidade=" + quantidade +
                ", valorUnitario=" + valorUnitario +
                ", valorTotal=" + valorTotal +
                ", observacao='" + observacao + '\'' +
                ", posicaoMesa=" + posicaoMesa +
                '}';
    }
}
