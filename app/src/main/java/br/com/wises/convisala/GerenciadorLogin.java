package br.com.wises.convisala;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.com.wises.convisala.model.Organizacao;

public class GerenciadorLogin {
    private boolean logado = false;
    private Organizacao organizacao = new Organizacao();
    private SharedPreferences pref = null;
    private SharedPreferences.Editor editor = null;

    public GerenciadorLogin (Context context) {
        logado = false;
        pref = context.getApplicationContext().getSharedPreferences("UserLogin", 0); // 0 - for private mode
        //editor.apply();
        //EditText emailView = findViewById(R.id.login_usuario);
    }

    public boolean entrar (String email, String senha) {
        if (pref != null && email != null && senha != null && !email.equals("") && !senha.equals("")) {
            editor = pref.edit();
            editor.putString("email", email);
            editor.putString("senha", senha);
            editor.apply();
            logado = true;
            String json = "";
            try {
                json = new HttpOrganizacaoUsuario(email, senha).execute().get();
                organizacao = parseOrganizacoesUsuario(json);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        } else {
            return false;
        }
    }

    /*public boolean entrar (String email, String senha, Organizacao org) {
        if (pref != null && email != null && senha != null && !email.equals("") && !senha.equals("")) {
            editor = pref.edit();
            editor.putString("email", email);
            editor.putString("senha", senha);
            editor.putString("org_id", ""+org.getId());
            editor.putString("org_nome", org.getNome());
            editor.putString("org_tipo", ""+org.getTipoOrganizacao());
            editor.apply();
            logado = true;
            return true;
        } else {
            return false;
        }
    }*/

    public String getEmail () {
        return pref.getString("email","");
    }

    public String getSenha () {
        return pref.getString("senha","");
    }

    public String getDominio () {
        String email = getEmail();
        if (email.contains("@") && email.indexOf(".") > email.indexOf("@")) {
            return email.split("@")[1];
        }
        return "";
    }

    public Organizacao getOrganizacao () {
        return this.organizacao;
    }

    public void setOrganizacao (Organizacao org) {
        this.organizacao = org;
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

    public Organizacao parseOrganizacoesUsuario (String rawUsuario) {
        System.out.println("Interpretando: \""+rawUsuario+"\"; ");;
        JSONObject jsonObject = null;
        Organizacao org = new Organizacao();

        try {
            jsonObject = new JSONObject(rawUsuario);

            if (jsonObject.length() > 0) {
                JSONObject obj = jsonObject.optJSONObject("idOrganizacao");
                if (obj != null && obj.length() > 0) {
                    int id = 0; String nome = ""; char tipoOrganizacao = 0;
                    id = obj.optInt("id", 0);
                    nome = obj.optString("nome", "");
                    tipoOrganizacao = obj.optString("tipoOrganizacao", "\0").charAt(0);
                    org = new Organizacao(id, nome, tipoOrganizacao);

                    System.out.println("Interpretado com sucesso! ");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return org;
    }

    public List<Organizacao> parseOrganizacoesArray (String rawOrganizacoes) {
        System.out.println("Interpretando: \""+rawOrganizacoes+"\"; ");
        JSONArray jsonArray = null;
        List<Organizacao> listaDeOrganizacoes = new ArrayList<>();

        try {
            jsonArray = new JSONArray(rawOrganizacoes);

            if (listaDeOrganizacoes == null) {
                listaDeOrganizacoes = new ArrayList<>();
            }
            listaDeOrganizacoes.clear();
            //listaDeOrganizacoes.add(new Organizacao());

            if (jsonArray.length() > 0) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject obj = null;
                    try {obj = jsonArray.getJSONObject(i);}
                    catch (Exception e) {e.printStackTrace();}

                    if (obj != null && obj.has("id") && obj.has("nome") && obj.has("tipoOrganizacao")) {
                        int id = 0; String nome = ""; char tipoOrganizacao = 0;
                        id = obj.optInt("id", 0);
                        nome = obj.optString("nome", "");
                        tipoOrganizacao = obj.optString("tipoOrganizacao", "\0").charAt(0);
                        Organizacao novaOrganizacao = new Organizacao();
                        novaOrganizacao.setId(id);
                        novaOrganizacao.setNome(nome);
                        novaOrganizacao.setTipoOrganizacao(tipoOrganizacao);

                        System.out.println("Interpretado com sucesso! ");
                        listaDeOrganizacoes.add(novaOrganizacao);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaDeOrganizacoes;
    }
}
