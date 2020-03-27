package com.apps.heber.restaurante.modelo;

import java.io.Serializable;
import java.util.Objects;

public class Categoria implements Serializable {

    private Long id;
    private String categoria;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        Categoria categoria1 = (Categoria) o;
        return id.equals(categoria1.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, categoria);
    }
}
