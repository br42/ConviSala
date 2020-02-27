package br.com.wises.convisala.model;

import androidx.annotation.NonNull;

import java.util.Date;

@SuppressWarnings({"WeakerAccess", "unused"})
public class Sala {
    private Organizacao organizacao;
    private int id;
    private String nome = "";
    private int quantidadePessoasSentadas;
    private boolean multimidia;
    private boolean arCondicionado;
    private double area;
    private String localizacao = "";
    private double latitude;
    private double longitude;
    private Date dataCriacao;
    private Date dataAlteracao;

    private int numero;
    private int andar;
    private String pavimento = "";

    public Sala () {

    }

    public Sala (int id) {
        this.id = id;
    }

    public Sala (int numero, int andar, String nome, String pavimento, String localizacao) {
        this.numero = numero;
        this.andar = andar;
        this.nome = nome;
        this.pavimento = pavimento;
        this.localizacao = localizacao;
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

    public String getLocalizacao() {
        return localizacao;
    }

    public Organizacao getOrganizacao() {
        return organizacao;
    }

    public void setOrganizacao(Organizacao organizacao) {
        this.organizacao = organizacao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getQuantidadePessoasSentadas() {
        return quantidadePessoasSentadas;
    }

    public void setQuantidadePessoasSentadas(int quantidadePessoasSentadas) {
        this.quantidadePessoasSentadas = quantidadePessoasSentadas;
    }

    public boolean isMultimidia() {
        return multimidia;
    }

    public void setMultimidia(boolean multimidia) {
        this.multimidia = multimidia;
    }

    public boolean isArCondicionado() {
        return arCondicionado;
    }

    public void setArCondicionado(boolean arCondicionado) {
        this.arCondicionado = arCondicionado;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Date getDataAlteracao() {
        return dataAlteracao;
    }

    public void setDataAlteracao(Date dataAlteracao) {
        this.dataAlteracao = dataAlteracao;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public void setAndar(int andar) {
        this.andar = andar;
    }

    public void setPavimento(String pavimento) {
        this.pavimento = pavimento;
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