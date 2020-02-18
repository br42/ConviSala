package br.com.wises.convisala.ui.reservas;

import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.Date;

import br.com.wises.convisala.Aplicativo;
import br.com.wises.convisala.R;
import br.com.wises.convisala.service.HttpCadastrarReserva;

public class CadastrarReservaActivity extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_reserva);

        Button cadastrar = findViewById(R.id.activity_cadastrar_reserva_enviar);

        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText descricaoView = findViewById(R.id.activity_cadastrar_reserva_descricao);
                EditText salaView = findViewById(R.id.activity_cadastrar_reserva_sala);
                CalendarView horaInicioView = findViewById(R.id.activity_cadastrar_reserva_hora_inicio);
                CalendarView horaFimView = findViewById(R.id.activity_cadastrar_reserva_hora_fim);

                String descricao = descricaoView.getText().toString();
                Integer salaId = 0;
                try {salaId = Integer.parseInt(salaView.getText().toString());} catch (Exception e) {e.printStackTrace();}
                long horaInicio = horaInicioView.getDate();
                long horaFim = horaFimView.getDate();


                JSONObject object = new JSONObject();
                try {
                    object.put("descricao", descricao);
                    object.put("id_sala", salaId);
                    object.put("id_usuario", Aplicativo.gerenciadorLogin.getUsuario().getId());
                    object.put("data_hora_inicio", horaInicio);
                    object.put("data_hora_fim", horaFim);
                } catch (Exception e) {e.printStackTrace();}

                String base64 = "";
                try {
                    base64 = Base64.encodeToString(object.toString().getBytes("UTF-8"), Base64.NO_WRAP);
                    System.out.println("Cadastrando Reserva: " + (new HttpCadastrarReserva(base64).execute().get()));
                    finish();
                } catch (Exception e) {e.printStackTrace();}

            }
        });

    }
}
