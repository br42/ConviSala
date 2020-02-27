package br.com.wises.convisala.service;

import androidx.annotation.NonNull;

@SuppressWarnings("unused")
public enum MetodoHttp {
    Get("GET"),
    Post("POST"),
    Put("PUT"),
    Delete("DELETE"),
    Patch("PATCH");

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
        return valor;
        //super.toString();
    }
}
