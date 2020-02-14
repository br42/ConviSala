package br.com.wises.convisala;

import android.app.Application;

import br.com.wises.convisala.service.GerenciadorLogin;

public class Aplicativo extends Application {
    public static GerenciadorLogin gerenciadorLogin = null;
    public static final String baseUrl = "http://172.30.248.109:8080/ReservaDeSala/rest";

    @Override
    public void onCreate() {
        super.onCreate();
        gerenciadorLogin = new GerenciadorLogin(this);
    }
}
