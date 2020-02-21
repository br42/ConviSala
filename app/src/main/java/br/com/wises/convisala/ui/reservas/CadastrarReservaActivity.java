package br.com.wises.convisala.ui.reservas;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import java.util.GregorianCalendar;

import br.com.wises.convisala.Aplicativo;
import br.com.wises.convisala.R;
import br.com.wises.convisala.service.HttpCadastrarReserva;

@SuppressWarnings("CharsetObjectCanBeUsed")
public class CadastrarReservaActivity extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_reserva);

        Button cadastrar = findViewById(R.id.activity_cadastrar_reserva_enviar);
        Button dataInicioView = findViewById(R.id.activity_cadastrar_reserva_data_inicio);
        Button horaInicioView = findViewById(R.id.activity_cadastrar_reserva_hora_inicio);
        Button dataFimView = findViewById(R.id.activity_cadastrar_reserva_data_fim);
        Button horaFimView = findViewById(R.id.activity_cadastrar_reserva_hora_fim);

        dataInicioView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder construtor = new AlertDialog.Builder(CadastrarReservaActivity.this);
                construtor.setView(R.layout.picker_date);
                construtor.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            throw new UnsupportedOperationException("Botão OK Clicado!");
                        } catch (Exception e) {e.printStackTrace();}
                    }
                });
                construtor.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            throw new UnsupportedOperationException("Botão Cancelar Clicado!");
                        } catch (Exception e) {e.printStackTrace();}
                    }
                });

                AlertDialog dialogo = construtor.create();
                dialogo.show();
            }
        });

        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText descricaoView = findViewById(R.id.activity_cadastrar_reserva_descricao);
                EditText salaView = findViewById(R.id.activity_cadastrar_reserva_sala);

                String descricao = descricaoView.getText().toString();
                int salaId = 0;
                try {salaId = Integer.parseInt(salaView.getText().toString());} catch (Exception e) {e.printStackTrace();}
                long horaInicio = GregorianCalendar.getInstance().getTimeInMillis(); //horaInicioView.getDate();
                long horaFim = GregorianCalendar.getInstance().getTimeInMillis() + 60000; //horaFimView.getDate();

                JSONObject object = new JSONObject();
                try {
                    object.put("descricao", descricao);
                    object.put("id_sala", salaId);
                    object.put("id_usuario", Aplicativo.gerenciadorLogin.getUsuario().getId());
                    object.put("data_hora_inicio", horaInicio);
                    object.put("data_hora_fim", horaFim);
                } catch (Exception e) {e.printStackTrace();}

                String base64;
                try {
                    base64 = Base64.encodeToString(object.toString().getBytes("UTF-8"), Base64.NO_WRAP);
                    System.out.println("Cadastrando Reserva: " + (new HttpCadastrarReserva(base64).execute().get()));
                    System.out.println("Reserva em Base64: " + base64);
                    finish();
                } catch (Exception e) {e.printStackTrace();}

            }
        });

    }
}
