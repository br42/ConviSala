package br.com.wises.convisala.model;

import java.util.Calendar;

public class Organizacao {
    private int id = 0;
    private String nome = "";
    private char tipoOrganizacao = 0;
    private String dominio = "";
    private long dataCriacao = 0;
    private long dataAlteracao = 0;


    public Organizacao () {
        this(0, "", (char)0);
    }

    public Organizacao (int id, String nome, char tipoOrganizacao) {
        this.id = id;
        this.nome = nome;
        this.tipoOrganizacao = tipoOrganizacao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public char getTipoOrganizacao() {
        return tipoOrganizacao;
    }

    public void setTipoOrganizacao(char tipoOrganizacao) {
        this.tipoOrganizacao = tipoOrganizacao;
    }
}
