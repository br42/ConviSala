package br.com.wises.convisala;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import br.com.wises.convisala.service.AutenticacaoLogin;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //####################################################################
        // Código da Gambiarra:
        // (Permite ignorar a exception android.os.NetworkOnMainThreadException);
        // {

        /*if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }*/

        // }
        //####################################################################

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText emailView = findViewById(R.id.login_usuario);
        String emailAnterior = Aplicativo.gerenciadorLogin.getEmail();
        String senhaAnterior = Aplicativo.gerenciadorLogin.getSenha();
        if (emailAnterior != null && !emailAnterior.equals("") && senhaAnterior != null && !senhaAnterior.equals("")) {
            String status = "";
            try {
                status = (new AutenticacaoLogin(emailAnterior, senhaAnterior)).execute().get();
                //makeAuthRequest("clovis@wises.com.br", "123");
            } catch (Exception e) {
                e.printStackTrace();
            }

            System.out.println(status);

            //if (usuario.validar(referencia)) {

            int idOrganizacao = Aplicativo.gerenciadorLogin.parseOrganizacaoUsuario(status).getId();

            //if (status.equals("Login efetuado com sucesso!") && Aplicativo.gerenciadorLogin.entrar(emailAnterior, senhaAnterior)) {
            if (idOrganizacao != 0 && Aplicativo.gerenciadorLogin.entrar(emailAnterior, senhaAnterior)) {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            } else {
                emailView.setText(emailAnterior);
            }
        }

        Button login = findViewById(R.id.login_logar);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText emailView = findViewById(R.id.login_usuario);
                EditText senhaView = findViewById(R.id.login_senha);

                String email = emailView.getText().toString();

                //Usuario referencia = new Usuario("Clovis", "clovis@wises.com.br", "wisesys");

                /*String dominio = "";
                if (email.contains("@") && email.indexOf(".") > email.indexOf("@")) {
                    dominio = email.split("@")[1];
                }*/

                String status = "";
                try {
                    status = (new AutenticacaoLogin(email, senhaView.getText().toString())).execute().get();
                    //makeAuthRequest("clovis@wises.com.br", "123");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                System.out.println(status);

                //if (usuario.validar(referencia)) {
                if (status.equals("Login efetuado com sucesso!") && Aplicativo.gerenciadorLogin.entrar(email, senhaView.getText().toString())) {
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                }
            }
        });

        Button criarConta = findViewById(R.id.login_cadastrar);
        criarConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
            }
        });

        Button convidado = findViewById(R.id.login_convidado);
        convidado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }
        });

//        if (false) {
//            String nome = "abc", senha = "def", email = "ghi";
//
//            JSONObject usuarioJson = new JSONObject();
//
//            try {
//                usuarioJson.put("email", email);
//                usuarioJson.put("senha", senha);
//                usuarioJson.put("nome", nome);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            String usuarioBase64 = "";
//            try {
//                usuarioBase64 = Base64.encodeToString(usuarioJson.toString().getBytes("UTF-8"), Base64.NO_WRAP);
//            } catch (Exception e) {e.printStackTrace();}
//
//            System.out.println("Objeto JSON em Base64: "+usuarioBase64);
//
//            try {
//                System.out.println("Objeto JSON Decodificado: " + new String(Base64.decode(usuarioBase64,0), "UTF-8"));
//            } catch (Exception e) {e.printStackTrace();}
//        }

    }
}

