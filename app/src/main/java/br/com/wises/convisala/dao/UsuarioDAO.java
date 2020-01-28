package br.com.wises.convisala.dao;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {
    private final ArrayList<Usuario> usuarios = new ArrayList<Usuario>();

    public UsuarioDAO () {

    }

    public ArrayList<Usuario> getUsuarios() {
        return usuarios;
    }

    public void adicionarUsuario (Usuario usuario) {
        usuarios.add(usuario);
    }

    public void adicionarLista (ArrayList<Usuario> usuarios) {
        usuarios.addAll(usuarios);
    }

    public void removerUsuario (Usuario usuario) {
        usuarios.remove(usuario);
    }

    public Usuario obterUsuario (int posicao) {
        return usuarios.get(posicao);
    }

    public int quantiaDeUsuarios () {
        return usuarios.size();
    }
}
