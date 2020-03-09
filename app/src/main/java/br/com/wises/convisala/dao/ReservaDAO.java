package br.com.wises.convisala.dao;

import java.util.ArrayList;
import java.util.List;

import br.com.wises.convisala.model.Reserva;

@SuppressWarnings("unused")
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

    public void limparListaReservas () {
        reservaLista.clear();
    }

    public void organizarLista () {
        List<Reserva> listaNova = new ArrayList<>();
        List<Boolean> itensUsados = new ArrayList<>();

        for (Reserva i : reservaLista) {
            itensUsados.add(false);
        }

        for (Reserva j : reservaLista) {
            Reserva reserva = null;
            int i = 0;
            for (i = 0; i < reservaLista.size(); i++) {
                Reserva reservaTemp = reservaLista.get(i);
                if (!itensUsados.get(i) && // Se não for repetido,
                        ((reserva == null) || // O item não for nulo e...
                        (reservaTemp.getHoraInicio().getTime() < reserva.getHoraInicio().getTime()))) {
                        // A reserva obtida começar antes, então ela deve vir antes;
                    reserva = reservaTemp;

                }
            }
            // Adicione a reserva com menor tempo à lista:
            if (reserva != null) {
                listaNova.add(reserva);
                itensUsados.set(i, true);
            }
        }

        limparListaReservas();
        adicionarLista(listaNova);
    }

}
