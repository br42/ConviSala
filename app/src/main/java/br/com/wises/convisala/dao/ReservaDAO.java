package br.com.wises.convisala.dao;

import java.util.ArrayList;
import java.util.List;

import br.com.wises.convisala.model.Reserva;

public class ReservaDAO {

    private final List<Reserva> reservaLista =  new ArrayList<>();

    public ReservaDAO () {

    }

    public List<Reserva> obterLista() {
        return reservaLista;
    }

    public void adicionarReserva (Reserva reserva) {
        reservaLista.add(reserva);
    }

    public void adicionarLista (List<Reserva> reservas) {
        reservaLista.addAll(reservas);
    }

    public Reserva obterReserva (int posicao) {
        return reservaLista.get(posicao);
    }

    public int quantiaDeReservas () {
        return reservaLista.size();
    }

    public void removerReserva (int posicao) {
        reservaLista.remove(posicao);
    }

    public void removerReserva (Reserva reserva) {
        reservaLista.remove(reserva);
    }

}
