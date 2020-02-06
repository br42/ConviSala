package br.com.wises.convisala;

import android.os.AsyncTask;
import android.util.Base64;

import androidx.annotation.NonNull;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpSalas extends AsyncTask<Void, Void, String> {

    public HttpSalas() {

    }

    @Override
    protected String doInBackground(Void... voids) {
        int responseCode = 0;
        String wsURL = "http://172.30.248.109:8080/ReservaDeSala/rest/sala/salas";
        StringBuilder resposta = new StringBuilder();
        StringBuilder result = new StringBuilder();
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
            con.setRequestProperty("id_organizacao", ""+Aplicativo.gerenciadorLogin.getIdOrganizacao());
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
            //System.out.println(result.toString());

            //result.append(new String (Base64.decode(base64.toString(), 0), "UTF-8"));

            //JSONArray decoder = new JSONArray(resposta.toString());
            //String nomeOrganizacao = decoder.getJSONObject(0).getString("nome");

            //result.append("Nome da Organização: ");
            //result.append(nomeOrganizacao);

            return (resposta.toString());
        } catch (Exception e) {
            //System.out.println("[EXCEPTION!] Responsecode" + ": " + responseCode + "; ");
            e.printStackTrace();
            return ("[EXCEPTION!] Responsecode" + ": " + responseCode + "; ");
        }
    }
}
