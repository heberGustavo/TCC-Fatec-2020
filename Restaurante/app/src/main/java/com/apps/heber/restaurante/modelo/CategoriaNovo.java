package com.apps.heber.restaurante.modelo;

import java.io.Serializable;
import java.util.Objects;

public class CategoriaNovo{

    private int idCategoria;
    private String categoria;

    public CategoriaNovo() {
    }

    public CategoriaNovo(int idCategoria, String categoria) {
        this.idCategoria = idCategoria;
        this.categoria = categoria;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

}
