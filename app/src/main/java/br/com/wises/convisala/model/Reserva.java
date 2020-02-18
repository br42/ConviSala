package br.com.wises.convisala.model;

import java.util.Date;

public class Reserva {
    private int id = 0;
    private Sala salaReservada = null;
    private Usuario usuario = null;
    private String nomeOrganizador = "";
    private Date horaInicio = null;
    private Date horaFim = null;
    private String descricao = "";
    private Date dataCriacao = null;
    private Date dataAlteracao = null;

    public Reserva () {

    }

    public Reserva (int id) {
        this.id = id;
    }

    public Reserva (int id, String descricao, Usuario usuario, Sala salaReservada, Date horaInicio, Date horaFim) {
        this.descricao = descricao;
        this.usuario = usuario;
        this.salaReservada = salaReservada;
        this.horaInicio = horaInicio;
        this.horaFim = horaFim;
    }

    public String getDescricao() {
        return descricao;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Sala getSala() {
        return salaReservada;
    }

    public Date getHoraInicio() {
        return horaInicio;
    }

    public Date getHoraFim() {
        return horaFim;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSala(Sala sala) {
        this.salaReservada = sala;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getNomeOrganizador() {
        return nomeOrganizador;
    }

    public void setNomeOrganizador(String nomeOrganizador) {
        this.nomeOrganizador = nomeOrganizador;
    }

    public void setHoraInicio(Date horaInicio) {
        this.horaInicio = horaInicio;
    }

    public void setHoraFim(Date horaFim) {
        this.horaFim = horaFim;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
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

}
