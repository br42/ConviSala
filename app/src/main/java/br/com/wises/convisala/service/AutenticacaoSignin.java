package br.com.wises.convisala.service;

import android.os.AsyncTask;
import android.util.Base64;
import androidx.annotation.NonNull;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import br.com.wises.convisala.Aplicativo;

@SuppressWarnings({"unused", "CanBeFinal"})
public class AutenticacaoSignin extends AsyncTask<Void, Void, String> {
    @NonNull private String nome;
    @NonNull private String email;
    @NonNull private String password;
    //@NonNull private String dominio;

    public AutenticacaoSignin(@NonNull String nome, @NonNull String email, @NonNull String password, @NonNull String dominio) {
        this.nome = nome;
        this.email = email;
        this.password = password;
        //this.dominio = dominio;
    }

    @Override
    protected String doInBackground(Void... voids) {
        int responseCode = 0;
        String wsURL = Aplicativo.baseUrl + "usuario/cadastro";
        StringBuilder result = new StringBuilder();
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
            JSONObject usuarioJson = new JSONObject();
            usuarioJson.put("dominio", email);
            usuarioJson.put("email", email);
            usuarioJson.put("senha", password);
            usuarioJson.put("nome", nome);

            @SuppressWarnings("CharsetObjectCanBeUsed")
            String usuarioBase64 = Base64.encodeToString(usuarioJson.toString().getBytes("UTF-8"), Base64.NO_WRAP);

            //System.out.println("Objeto JSON em Base64: "+usuarioBase64);
            //System.out.println("Objeto JSON Decodificado: " + new String(Base64.decode(usuarioBase64,0), "UTF-8"));

            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("authorization", "secret");
            con.setRequestProperty("novoUsuario", usuarioBase64);
            con.setDoOutput(false);
            con.setConnectTimeout(2000);
            con.connect();

            responseCode = con.getResponseCode();
            //System.out.println("Responsecode" + ": " + "" + responseCode + "; ");

            BufferedReader rd = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            rd.close();
            //System.out.println(result.toString());

            return (result.toString());
        } catch (Exception e) {
            //System.out.println("[EXCEPTION!] Responsecode" + ": " + responseCode + "; ");
            e.printStackTrace();
            return ("[EXCEPTION!] Responsecode" + ": " + responseCode + "; ");
        }
    }
}
