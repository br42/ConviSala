package br.com.wises.convisala;

import android.app.Application;

public class Aplicativo extends Application {
    public static GerenciadorLogin gerenciadorLogin = null;

    @Override
    public void onCreate() {
        super.onCreate();
        gerenciadorLogin = new GerenciadorLogin(this);
    }
}
