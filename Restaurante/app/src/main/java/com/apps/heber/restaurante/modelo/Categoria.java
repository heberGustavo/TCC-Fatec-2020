package com.apps.heber.restaurante.modelo;

import android.support.annotation.NonNull;

import java.io.Serializable;

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

    @NonNull
    @Override
    public String toString() {
        return getCategoria();
    }
}
