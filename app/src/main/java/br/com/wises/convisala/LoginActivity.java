package br.com.wises.convisala;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import br.com.wises.convisala.model.Usuario;

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

        SharedPreferences pref = getApplicationContext().getSharedPreferences("Prefers", 0); // 0 - for private mode
        EditText emailView = findViewById(R.id.login_usuario);

        if (pref.contains("email")) {
            System.out.println("Puxando: " + pref.getString("email", "[nullkkkkkk]"));
            String emailAnterior = pref.getString("email", "");
            emailView.setText(emailAnterior);
            System.out.println("Puxado: " + emailAnterior);
        }

        Button login = findViewById(R.id.login_logar);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref = getApplicationContext().getSharedPreferences("Prefers", 0); // 0 - for private mode
                SharedPreferences.Editor editor = pref.edit();


                EditText emailView = findViewById(R.id.login_usuario);
                EditText senhaView = findViewById(R.id.login_senha);


                Usuario usuario = new Usuario(emailView.getText().toString(),
                        emailView.getText().toString(),
                        senhaView.getText().toString());

                String email = emailView.getText().toString();

                if (email != null && !email.equals("")) {
                    editor.putString("email",email);
                    editor.commit();
                    System.out.println("Salvo: " + pref.getString("email", "[nullkkkkkk]"));
                }

                //Usuario referencia = new Usuario("Clovis", "clovis@wises.com.br", "wisesys");

                String status = "";
                try {
                    status = (new AutenticacaoLogin(email, senhaView.getText().toString())).execute().get();
                    //makeAuthRequest("clovis@wises.com.br", "123");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                System.out.println(status);

                //if (usuario.validar(referencia)) {
                if (status.equals("Login efetuado com sucesso!")) {
                    Aplicativo.logado = true;
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

