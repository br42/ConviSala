package br.com.wises.convisala.dao;

import java.util.ArrayList;

import br.com.wises.convisala.model.Usuario;

@SuppressWarnings({"WeakerAccess", "unused"})
public class UsuarioDAO {
    private final ArrayList<Usuario> usuarios = new ArrayList<>();

    public UsuarioDAO () {

    }

    public ArrayList<Usuario> getUsuarios() {
        return usuarios;
    }

    public ArrayList<Usuario> obterLista() {
        return usuarios;
    }

    public void adicionarUsuario (Usuario usuario) {
        usuarios.add(usuario);
    }

    public void adicionarLista (ArrayList<Usuario> usuarios) {
        this.usuarios.addAll(usuarios);
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

    public void limparLista () {
        usuarios.clear();
    }
}
