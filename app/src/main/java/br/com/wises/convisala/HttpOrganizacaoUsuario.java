package br.com.wises.convisala;

import android.os.AsyncTask;
import android.util.Base64;

import androidx.annotation.NonNull;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpOrganizacaoUsuario extends AsyncTask<Void, Void, String> {
    @NonNull private String email = "";
    @NonNull private String password = "";

    public HttpOrganizacaoUsuario(@NonNull String email, @NonNull String password) {
        this.email = email;
        this.password = password;
    }

    @Override
    protected String doInBackground(Void... voids) {
        int responseCode = 0;
        String wsURL = "http://172.30.248.109:8080/ReservaDeSala/rest/usuario/loginV2/";
        StringBuilder resposta = new StringBuilder();
        URL obj = null;
        try {
            obj = new URL(wsURL);
        } catch (Exception e) {
            return "[EXCEPTION!] URL inválida;";
        }
        HttpURLConnection con = null;
        try {
            con = (HttpURLConnection) obj.openConnection();
        } catch (Exception e) {
            return "[EXCEPTION!] URL inválida, impossível abrir conexão;";
        }
        try {
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("authorization", "secret");
            con.setRequestProperty("email", email);
            con.setRequestProperty("password", password);
            con.setConnectTimeout(2000);
            con.connect();

            responseCode = con.getResponseCode();
            //System.out.println("Responsecode" + ": " + "" + responseCode + "; ");

            BufferedReader rd = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line = "";
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
