package br.com.wises.convisala;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.EditText;

public class GerenciadorLogin {
    private boolean logado = false;
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

    public String obterEmail () {
        return pref.getString("email","");
    }

    public String obterSenha () {
        return pref.getString("senha","");
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
