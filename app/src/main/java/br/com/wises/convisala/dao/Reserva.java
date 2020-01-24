package br.com.wises.convisala.dao;

public class Reserva {
    private String temaDaReserva = "";
    private String solicitadorDaReserva = "";
    private Sala salaReservada = null;
    private long dataDaReserva = 0;
    private long horaInicio = 0;
    private long horaFim = 0;

    public Reserva () {

    }

    public Reserva (String temaDaReserva, String solicitadorDaReserva, Sala salaReservada, long dataDaReserva, long horaInicio, long horaFim) {
        this.temaDaReserva = temaDaReserva;
        this.solicitadorDaReserva = solicitadorDaReserva;
        this.salaReservada = salaReservada;
        this.dataDaReserva = dataDaReserva;
        this.horaInicio = horaInicio;
        this.horaFim = horaFim;
    }

    public String getTema() {
        return temaDaReserva;
    }

    public String getSolicitador() {
        return solicitadorDaReserva;
    }

    public Sala getSala() {
        return salaReservada;
    }

    public long getData() {
        return dataDaReserva;
    }

    public long getHoraInicio() {
        return horaInicio;
    }

    public long getHoraFim() {
        return horaFim;
    }
}
