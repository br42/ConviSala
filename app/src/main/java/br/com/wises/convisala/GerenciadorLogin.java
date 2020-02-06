package br.com.wises.convisala;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.EditText;

public class GerenciadorLogin {
    private boolean logado = false;
    private int idOrganizacao = 0;
    private SharedPreferences pref = null;
    private SharedPreferences.Editor editor = null;

    public GerenciadorLogin (Context context) {
        logado = false;
        pref = context.getApplicationContext().getSharedPreferences("UserLogin", 0); // 0 - for private mode
        //editor.apply();
        //EditText emailView = findViewById(R.id.login_usuario);
    }

    public boolean entrar (String email, String senha) {
        if (email != null && senha != null && !email.equals("") && !senha.equals("")) {
            editor = pref.edit();
            editor.putString("email", email);
            editor.putString("senha", senha);
            editor.apply();
            logado = true;
            return true;
        } else {
            return false;
        }
    }

    public String getEmail () {
        return pref.getString("email","");
    }

    public String getSenha () {
        return pref.getString("senha","");
    }

    public int getIdOrganizacao () {
        return this.idOrganizacao;
    }

    public void setIdOrganizacao (int id) {
        this.idOrganizacao = id;
    }

    public boolean sair () {
        logado = false;
        return true;
    }

    public boolean isLogado () {
        return logado;
    }

    public boolean isNotLogado () {
        return !logado;
    }
}
