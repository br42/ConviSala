package br.com.wises.convisala.model;

import androidx.annotation.Nullable;

@SuppressWarnings({"WeakerAccess", "unused"})
public class Usuario {
    private int id;
    private String nome;
    private String email;
    private String senha;

    public Usuario () {
        this(0, "", "", "");
    }

    public Usuario (int id, String nome, String email, String senha) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        // Checa se ambos os Usuários têm informações idênticas.
        // Caso tenham, a função retorna ^true^. Caso não, ^false^.
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }

        Usuario outro = (Usuario) obj;

        return (this.getNome().equals(outro.getNome()) &&
                this.getEmail().equals(outro.getEmail()) &&
                this.getSenha().equals(outro.getSenha()));
    }

    public boolean validar(Usuario outro) {
        // Checa se ambos os Usuários têm informações idênticas.
        // Caso tenham, a função retorna ^true^. Caso não, ^false^.

        return (outro != null &&
                this.getEmail().equals(outro.getEmail()) &&
                this.getSenha().equals(outro.getSenha()));
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
