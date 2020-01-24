package br.com.wises.convisala.dao;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ReservaDAO {

    private final ArrayList<Reserva> reservaLista =  new ArrayList<Reserva>();

    public ReservaDAO () {

    }

    public ArrayList<Reserva> obterReservas() {
        return reservaLista;
    }

    public void adicionarReserva (Reserva reserva) {
        reservaLista.add(reserva);
    }

    public void adicionarLista (ArrayList<Reserva> reservas) {
        reservaLista.addAll(reservas);
    }

    public Reserva obterItem (int indice) {
        return reservaLista.get(indice);
    }

    public int quantiaDeReservas () {
        return reservaLista.size();
    }

}
