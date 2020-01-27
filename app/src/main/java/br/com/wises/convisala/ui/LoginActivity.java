package br.com.wises.convisala.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import br.com.wises.convisala.MainActivity;
import br.com.wises.convisala.R;
import br.com.wises.convisala.dao.Usuario;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button login = findViewById(R.id.login_logar);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText nomeOuEmail = findViewById(R.id.login_usuario);
                EditText senha = findViewById(R.id.login_senha);

                Usuario usuario = new Usuario(nomeOuEmail.getText().toString(),
                        nomeOuEmail.getText().toString(),
                        senha.getText().toString());

                Usuario referencia = new Usuario("Clovis", "clovis@wises.com.br", "wisesys");

                if (usuario.validar(referencia)) {
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                }
            }
        });

        Button convidado = findViewById(R.id.login_convidado);
        convidado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }
        });
    }
}
