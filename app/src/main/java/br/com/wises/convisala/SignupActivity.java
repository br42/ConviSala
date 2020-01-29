package br.com.wises.convisala;

import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import br.com.wises.convisala.dao.Usuario;

public class SignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        Button login = findViewById(R.id.signup_cadastrar);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText nome = findViewById(R.id.signup_usuario);
                EditText email = findViewById(R.id.signup_email);
                EditText senha = findViewById(R.id.signup_senha);

                Usuario usuario = new Usuario(nome.getText().toString(),
                        email.getText().toString(),
                        senha.getText().toString());

                //Usuario referencia = new Usuario("Clovis", "clovis@wises.com.br", "wisesys");

                String status = "";
                try {
                    status = (new AutenticacaoSignup(nome.getText().toString(), email.getText().toString(), senha.getText().toString()))
                            .execute().get();
                    //makeAuthRequest("clovis@wises.com.br", "123");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                System.out.println(status);

                //if (usuario.validar(referencia)) {
                if (status.equals("Login efetuado com sucesso!")) {
                    Aplicativo.logado = true;
                    startActivity(new Intent(SignupActivity.this, MainActivity.class));
                }
            }
        });

        if (true) {
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
    }
}
