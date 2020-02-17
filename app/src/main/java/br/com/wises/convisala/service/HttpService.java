package br.com.wises.convisala.service;

import android.os.AsyncTask;

import androidx.annotation.NonNull;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import br.com.wises.convisala.Aplicativo;

public class HttpService extends AsyncTask<Void, Void, String> {
    @NonNull private String url;
    @NonNull private MetodoHttp metodo;
    @NonNull private List<String> headers = new ArrayList<>();
    @NonNull private List<String> valores = new ArrayList<>();

    public HttpService(@NonNull String url, @NonNull MetodoHttp metodo, @NonNull List<String> headers, @NonNull List<String> valores) {
        this.url = url;
        this.metodo = metodo;
        this.headers.addAll(headers);
        this.valores.addAll(valores);
    }

    @Override
    protected String doInBackground(Void... voids) {
        int responseCode = 0;
        String wsURL = Aplicativo.baseUrl + url;
        StringBuilder resposta = new StringBuilder();
        URL obj;
        try {
            obj = new URL(wsURL);
        } catch (Exception e) {
            return "[EXCEPTION!] URL inválida;";
        }
        HttpURLConnection con;
        try {
            con = (HttpURLConnection) obj.openConnection();
        } catch (Exception e) {
            return "[EXCEPTION!] URL inválida, impossível abrir conexão;";
        }
        try {

            con.setRequestMethod(metodo.getMetodo());
            for (int i = 0; i < Math.min(headers.size(), valores.size()); i++) {
                con.setRequestProperty(headers.get(i), valores.get(i));
            }
            con.setConnectTimeout(2000);
            con.connect();

            responseCode = con.getResponseCode();
            //System.out.println("Responsecode" + ": " + "" + responseCode + "; ");

            BufferedReader rd = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {
                resposta.append(line);
            }
            rd.close();

            return (resposta.toString());

        } catch (Exception e) {
            //System.out.println("[EXCEPTION!] Responsecode" + ": " + responseCode + "; ");
            e.printStackTrace();
            return ("[EXCEPTION!] Responsecode" + ": " + responseCode + "; ");
        }
    }
}
