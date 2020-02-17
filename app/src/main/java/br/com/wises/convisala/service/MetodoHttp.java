package br.com.wises.convisala.service;

import androidx.annotation.NonNull;

public enum MetodoHttp {
    Get("GET"),
    Post("POST"),
    Put("PUT"),
    Delete("DELETE");

    private final String valor;

    MetodoHttp(String metodo) {
        valor = metodo;
    }

    String getMetodo () {
        return valor;
    }

    @NonNull
    @Override
    public String toString() {
        super.toString();
        return valor;
    }
}
