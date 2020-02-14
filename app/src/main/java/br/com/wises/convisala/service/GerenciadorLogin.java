package br.com.wises.convisala.service;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.com.wises.convisala.model.Organizacao;
import br.com.wises.convisala.model.Usuario;

public class GerenciadorLogin {
    private boolean logado;
    private Organizacao organizacao = new Organizacao();
    private Usuario usuario = new Usuario();
    private SharedPreferences pref;
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
            String json;
            try {
                json = new HttpUsuario(email, senha).execute().get();
                usuario = parseUsuario(json);
                organizacao = parseOrganizacaoUsuario(json);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
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


    public Usuario parseUsuario (String rawUsuario) {
        System.out.println("Interpretando: \""+rawUsuario+"\"; ");
        JSONObject usuarioJson;
        Usuario user = new Usuario();

        try {
            usuarioJson = new JSONObject(rawUsuario);

            if (usuarioJson.length() <= 0) {
                return user;
            }

            user.setId(usuarioJson.optInt("id", 0));
            user.setNome(usuarioJson.optString("nome", ""));
            user.setEmail(usuarioJson.optString("email", ""));

            System.out.println("Interpretado com sucesso! ");

        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }

    public Organizacao parseOrganizacaoUsuario (String rawUsuario) {
        System.out.println("Interpretando: \""+rawUsuario+"\"; ");
        JSONObject usuarioJson;
        Organizacao org = new Organizacao();

        try {
            usuarioJson = new JSONObject(rawUsuario);

            if (usuarioJson.length() > 0) {
                JSONObject obj = usuarioJson.optJSONObject("idOrganizacao");
                if (obj != null && obj.length() > 0) {
                    int id; String nome; char tipoOrganizacao;
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
        JSONArray jsonArray;
        List<Organizacao> listaDeOrganizacoes = new ArrayList<>();

        try {
            jsonArray = new JSONArray(rawOrganizacoes);
            listaDeOrganizacoes.clear();
            //listaDeOrganizacoes.add(new Organizacao());

            if (jsonArray.length() > 0) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject obj = null;
                    try {obj = jsonArray.getJSONObject(i);}
                    catch (Exception e) {e.printStackTrace();}

                    if (obj != null && obj.has("id") && obj.has("nome") && obj.has("tipoOrganizacao")) {
                        int id; String nome; char tipoOrganizacao;
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
