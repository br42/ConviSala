package br.com.wises.convisala;

import android.app.Application;

public class Aplicativo extends Application {
    public static boolean logado = false;

    @Override
    public void onCreate() {
        super.onCreate();
        Aplicativo.logado = false;
    }
}
