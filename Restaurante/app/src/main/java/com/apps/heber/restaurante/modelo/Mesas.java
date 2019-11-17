package com.apps.heber.restaurante.modelo;

public class Mesas {

    private Long idMesa;
    private String nomeCliente;

    public Long getIdMesa() {
        return idMesa;
    }

    public void setIdMesa(Long idMesa) {
        this.idMesa = idMesa;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    @Override
    public String toString() {
        return "Mesas{" +
                "idMesa=" + idMesa +
                ", nomeCliente='" + nomeCliente + '\'' +
                '}';
    }
}
