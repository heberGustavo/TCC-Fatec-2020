package com.apps.heber.restaurante.modelo;

import java.io.Serializable;
import java.util.Objects;

public class CategoriaNovo implements Serializable{

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

    @Override
    public String toString() {
        return categoria;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoriaNovo that = (CategoriaNovo) o;
        return idCategoria == that.idCategoria &&
                Objects.equals(categoria, that.categoria);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCategoria, categoria);
    }
}
