package br.com.wises.convisala.dao;

import java.util.ArrayList;

import br.com.wises.convisala.model.Reserva;

public class SalaDAO {

    private final ArrayList<Reserva> reservaLista =  new ArrayList<Reserva>();

    public SalaDAO() {

    }

    public ArrayList<Reserva> obterLista() {
        return reservaLista;
    }

    public void adicionarReserva (Reserva reserva) {
        reservaLista.add(reserva);
    }

    public void adicionarLista (ArrayList<Reserva> reservas) {
        reservaLista.addAll(reservas);
    }

    public Reserva obterReserva (int posicao) {
        return reservaLista.get(posicao);
    }

    public int quantiaDeReservas () {
        return reservaLista.size();
    }

}
