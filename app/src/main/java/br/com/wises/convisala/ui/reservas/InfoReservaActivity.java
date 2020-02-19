package br.com.wises.convisala.ui.reservas;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import br.com.wises.convisala.R;
import br.com.wises.convisala.model.Reserva;

public class InfoReservaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_info_reserva);
        Reserva reservaEscolhida = ReservasFragment.getReservaEscolhida();

        if (reservaEscolhida != null) {
            ((TextView) findViewById(R.id.activity_info_reserva_descricao)).setText(String.format(getString(R.string.activity_info_reserva_descricao), reservaEscolhida.getDescricao()));
            ((TextView) findViewById(R.id.activity_info_reserva_organizador)).setText(String.format(getString(R.string.activity_info_reserva_organizador), reservaEscolhida.getNomeOrganizador()));
        }

    }

}
