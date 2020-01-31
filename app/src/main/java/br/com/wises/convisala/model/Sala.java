package br.com.wises.convisala.model;

import androidx.annotation.NonNull;

public class Sala {
    private int numero = 0;
    private int andar = 0;
    private String nome = "";
    private String pavimento = "";
    private String local = "";

    public Sala (int numero, int andar, String nome, String pavimento, String local) {
        this.numero = numero;
        this.andar = andar;
        this.nome = nome;
        this.pavimento = pavimento;
        this.local = local;
    }

    public int getNumero() {
        return numero;
    }

    public int getAndar() {
        return andar;
    }

    public String getNome() {
        return nome;
    }

    public String getPavimento() {
        return pavimento;
    }

    public String getLocal() {
        return local;
    }

    @NonNull @Override
    public String toString() {
        if (this.getNome() != null && !this.getNome().equals("")) {
            return this.getNome();
        }

        if (this.getNumero() != 0) {
            return "Sala " + this.getNumero();
        }

        return "";
    }
}