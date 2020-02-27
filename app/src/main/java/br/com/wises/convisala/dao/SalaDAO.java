package br.com.wises.convisala.dao;

import java.util.ArrayList;
import java.util.List;

import br.com.wises.convisala.model.Sala;

@SuppressWarnings("unused")
public class SalaDAO {

    private final ArrayList<Sala> salaLista =  new ArrayList<>();

    public SalaDAO() {

    }

    public ArrayList<Sala> obterLista() {
        return salaLista;
    }

    public void adicionarSala (Sala sala) {
        salaLista.add(sala);
    }

    public void adicionarLista (List<Sala> salas) {
        salaLista.addAll(salas);
    }

    public Sala obterSala (int posicao) {
        return salaLista.get(posicao);
    }

    public int quantiaDeSalas () {
        return salaLista.size();
    }

    public void removerSala (Sala sala) {salaLista.remove(sala);}

    public void removerSala (int posicao) {salaLista.remove(posicao);}

    public void limparLista () {salaLista.clear();}

}
