package br.com.wises.convisala;

import android.app.Application;

import br.com.wises.convisala.service.GerenciadorLogin;

public class Aplicativo extends Application {
    public static GerenciadorLogin gerenciadorLogin = null;
    public static final String baseUrl = "";

    @Override
    public void onCreate() {
        super.onCreate();
        gerenciadorLogin = new GerenciadorLogin(this);
    }
}
