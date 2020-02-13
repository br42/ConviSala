package br.com.wises.convisala.model;

public class Organizacao {
    private int id;
    private String nome;
    private char tipoOrganizacao;
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
