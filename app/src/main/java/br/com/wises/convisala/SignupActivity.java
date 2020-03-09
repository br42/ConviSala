package br.com.wises.convisala;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import br.com.wises.convisala.model.Organizacao;
import br.com.wises.convisala.service.HttpListaOrganizacoes;
import br.com.wises.convisala.service.HttpService;
import br.com.wises.convisala.service.MetodoHttp;

@SuppressWarnings("unused")
public class SignupActivity extends AppCompatActivity {

    private List<Organizacao> listaDeOrganizacoes = new ArrayList<>();
    BaseAdapter adapter = null;
    private int organizacao = 0;
    private AlertDialog.Builder dialogo = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        Button signup = findViewById(R.id.signup_cadastrar);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText nomeView = findViewById(R.id.signup_usuario);
                EditText emailView = findViewById(R.id.signup_email);
                EditText senhaView = findViewById(R.id.signup_senha);

                //Spinner spinnerFiliais = findViewById(R.id.signup_spinner);
                //Usuario referencia = new Usuario("Clovis", "clovis@wises.com.br", "wisesys");

                //String nome = emailView.getText().toString();
                String email = emailView.getText().toString();
                //String senha = emailView.getText().toString();
                String dominio = "";
                String resultado = "";

                if (nomeView.getText().toString().equals("")){
                    Snackbar.make(v, "É necessário digitar um nome de usuário!", Snackbar.LENGTH_LONG).show();
                } else if (email.equals("")){
                    Snackbar.make(v, "É necessário digitar o email!", Snackbar.LENGTH_LONG).show();
                } else if (senhaView.getText().toString().equals("")){
                    Snackbar.make(v, "É necessário digitar uma senha!", Snackbar.LENGTH_LONG).show();
                } else if (organizacao == 0){
                    Snackbar.make(v, "É necessário escolher uma organização!", Snackbar.LENGTH_LONG).show();
                } else {
                    System.out.println("Tamanho da listaDeOrganizacoes: " + listaDeOrganizacoes.size());
                    for (int i = 0; i < listaDeOrganizacoes.size(); i++) {
                        System.out.println("Itens: " + listaDeOrganizacoes.get(i));
                    }


                    /*String dominio = "";
                    String email = emailView.getText().toString();
                    if (email.contains("@") && email.indexOf(".") > email.indexOf("@")) {
                        dominio = email.split("@")[1];
                    }*/

                    String status = "";
                    try {

                        JSONObject usuarioJson = new JSONObject();
                        usuarioJson.put("nome", nomeView.getText().toString());
                        usuarioJson.put("email", email);
                        usuarioJson.put("senha", senhaView.getText().toString());
                        usuarioJson.put("idOrganizacao", organizacao);

                        @SuppressWarnings("CharsetObjectCanBeUsed")
                        String usuarioBase64 = Base64.encodeToString(usuarioJson.toString().getBytes("UTF-8"), Base64.NO_WRAP);

                        status = new HttpService("usuario/cadastro/", MetodoHttp.Post,
                            Arrays.asList("Content-Type","authorization", "novoUsuario"),
                            Arrays.asList("application/json","secret", usuarioBase64))
                        .execute().get();

                        /*status = (new AutenticacaoSignin(nomeView.getText().toString(), emailView.getText().toString(),
                                senhaView.getText().toString(), dominio))
                                .execute().get();*/
                        //makeAuthRequest("clovis@wises.com.br", "123");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    System.out.println(status);

                    finish();
                }
            }

            //if (usuario.validar(referencia)) {
            /*if (status.equals("Login efetuado com sucesso!")) {
                Aplicativo.logado = true;
                startActivity(new Intent(SigninActivity.this, MainActivity.class));
            }*/
        });

        final EditText entradaEmail = findViewById(R.id.signup_email);

        entradaEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String emailDigitado = entradaEmail.getText().toString();
                    String dominio = "";
                    String resultado = "";

                    if (emailDigitado.contains("@") /*&& emailDigitado.indexOf(".") > emailDigitado.indexOf("@")*/) {
                        if ((emailDigitado.length() > 1) && (emailDigitado.indexOf("@") < (emailDigitado.length() - 1))) {
                            dominio = emailDigitado.split("@")[1];
                        }
                        System.out.println("Domínio Inserido: " + dominio);
                        try {
                            resultado = (new HttpListaOrganizacoes(dominio)).execute().get();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        System.out.println(resultado);
                        listaDeOrganizacoes = Aplicativo.gerenciadorLogin.parseOrganizacoesArray(resultado);
                        adapter.notifyDataSetChanged();

                        if (organizacao == 0) {

                            //findViewById(R.id.signup_container_spinner).setVisibility(View.VISIBLE);
                            dialogo.show();
                            //spinnerAberto = true;

                        }
                    }

                    System.out.println("Tamanho da listaDeOrganizacoes: " + listaDeOrganizacoes.size());
                    for (int i = 0; i < listaDeOrganizacoes.size(); i++) {
                        System.out.println("Itens: " + listaDeOrganizacoes.get(i));
                    }

                }
            }
        });

        //Spinner signup_spinner = findViewById(R.id.signup_spinner);

        adapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return listaDeOrganizacoes.size();
            }

            @Override
            public Organizacao getItem(int posicao) {
                return listaDeOrganizacoes.get(posicao);
            }

            @Override
            public long getItemId(int posicao) {
                return listaDeOrganizacoes.get(posicao).getId();
            }

            @SuppressLint("SetTextI18n")
            @Override
            public View getView(int posicao, View convertView, ViewGroup parent) {
                Organizacao organizacao = getItem(posicao);
                if (organizacao == null || (organizacao.getNome().equals("") && organizacao.getId() == 0 && organizacao.getTipoOrganizacao() == 0)) {
                    if (convertView == null) {
                        convertView = LayoutInflater.from(SignupActivity.this).inflate(R.layout.item_organizacao, parent, false);
                        //convertView.setVisibility(View.INVISIBLE);
                    }
                    return convertView;
                } else {
                    if (convertView == null) {
                        convertView = LayoutInflater.from(SignupActivity.this).inflate(R.layout.item_organizacao, parent, false);
                    }
                    ((TextView) convertView.findViewById(R.id.item_organizacao_nome)).setText(organizacao.getNome());
                    ((TextView) convertView.findViewById(R.id.item_organizacao_tipo))
                            .setText("(Tipo: " + ((organizacao.getTipoOrganizacao() == 'M') ? "Matriz" :
                                    ((organizacao.getTipoOrganizacao() == 'F') ? "Filial" : "Desconhecido")) + ")");
                    return convertView;
                }
            }
        };

        dialogo = new AlertDialog.Builder(this);
        dialogo.setTitle("Selecione a organização");
        dialogo.setAdapter(adapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    organizacao = listaDeOrganizacoes.get(which).getId();
                } catch (Exception e) {e.printStackTrace();}
            }
        });

        dialogo.create();

    }
}

    /*Button signup = findViewById(R.id.signup_cadastrar);
    signup.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
                EditText nomeView = findViewById(R.id.signup_usuario);
                EditText emailView = findViewById(R.id.signup_email);
                EditText senhaView = findViewById(R.id.signup_senha);

                //Spinner spinnerFiliais = findViewById(R.id.signup_spinner);
                //Usuario referencia = new Usuario("Clovis", "clovis@wises.com.br", "wisesys");

                String emailDigitado = emailView.getText().toString();
                String dominio = "";
                String resultado = "";

                        /*if (emailDigitado.contains("@") && emailDigitado.indexOf(".") > emailDigitado.indexOf("@")) {
                            if ((emailDigitado.length() > 1) && (emailDigitado.indexOf("@") < (emailDigitado.length() - 1))) {
                                dominio = emailDigitado.split("@")[1];
                            }
                            System.out.println("Domínio Inserido: " + dominio);

                            if (organizacao == 0) {
                                try {
                                    resultado = (new HttpListaOrganizacoes(dominio)).execute().get();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                System.out.println(resultado);
                                listaDeOrganizacoes = Aplicativo.gerenciadorLogin.parseOrganizacoesArray(resultado);
                                adapter.notifyDataSetChanged();

                                //findViewById(R.id.signup_container_spinner).setVisibility(View.VISIBLE);
                                dialogo.show();

                            } else*//* {
                System.out.println("Tamanho da listaDeOrganizacoes: " + listaDeOrganizacoes.size());
                for (int i = 0; i < listaDeOrganizacoes.size(); i++) {
                System.out.println("Itens: " + listaDeOrganizacoes.get(i));
                }


                                /*String dominio = "";
                                String email = emailView.getText().toString();
                                if (email.contains("@") && email.indexOf(".") > email.indexOf("@")) {
                                    dominio = email.split("@")[1];
                                }*//*

                String status = "";
                try {
                status = (new AutenticacaoSignin(nomeView.getText().toString(), emailView.getText().toString(),
                senhaView.getText().toString(), dominio))
                .execute().get();
                //makeAuthRequest("clovis@wises.com.br", "123");
                } catch (Exception e) {
                e.printStackTrace();
                }

                System.out.println(status);

                finish();
            }
        }
        //}

        //if (usuario.validar(referencia)) {
            /*if (status.equals("Login efetuado com sucesso!")) {
                Aplicativo.logado = true;
                startActivity(new Intent(SigninActivity.this, MainActivity.class));
            }*//*
        });*/

        /*dialogo.setSingleChoiceItems(adapter, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                organizacao = which;
            }
        });
        dialogo.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });*/

        //signup_spinner.setAdapter(adapter);

        //((Spinner)findViewById(R.id.signup_spinner)).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        //    @Override
        //    public void onItemSelected(AdapterView<?> parent, View view, int posicao, long id) {
        //        if (spinnerAberto) {
        //            organizacao = listaDeOrganizacoes.get(posicao).getId();
        //            findViewById(R.id.signup_container_spinner).setVisibility(View.GONE);
        //            spinnerAberto = false;
        //        }
        //        System.out.println("Organização: " + organizacao);
        //    }
        //
        //    @Override
        //    public void onNothingSelected(AdapterView<?> parent) {
        //        organizacao = 0;
        //        findViewById(R.id.signup_container_spinner).setVisibility(View.GONE);
        //        spinnerAberto = false;
        //    }
        //});

    //################

    /*private void inutilizado () {

        entradaEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String emailDigitado = entradaEmail.getText().toString();
                String dominio = "";

                if (emailDigitado.contains("@") && emailDigitado.indexOf(".") > emailDigitado.indexOf("@")) {
                    if ((emailDigitado.length() > 1) && (emailDigitado.indexOf("@") < (emailDigitado.length() - 1))) {
                        dominio = emailDigitado.split("@")[1];
                    }
                    System.out.println("Domínio Inserido: " + dominio);
                }
            }
        });

        if (false) {
            String nome = "abc", senha = "def", email = "ghi";

            JSONObject usuarioJson = new JSONObject();

            try {
                usuarioJson.put("email", email);
                usuarioJson.put("senha", senha);
                usuarioJson.put("nome", nome);
            } catch (Exception e) {
                e.printStackTrace();
            }

            String usuarioBase64 = "";
            try {
                usuarioBase64 = Base64.encodeToString(usuarioJson.toString().getBytes("UTF-8"), Base64.NO_WRAP);
            } catch (Exception e) {e.printStackTrace();}

            System.out.println("Objeto JSON em Base64: "+usuarioBase64);

            try {
                System.out.println("Objeto JSON Decodificado: " + new String(Base64.decode(usuarioBase64,0), "UTF-8"));
            } catch (Exception e) {e.printStackTrace();}
        }
    }*/
