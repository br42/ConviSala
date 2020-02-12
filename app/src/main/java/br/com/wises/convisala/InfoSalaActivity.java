package br.com.wises.convisala;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import br.com.wises.convisala.model.Sala;
import br.com.wises.convisala.ui.salas.SalasFragment;

public class InfoSalaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_sala);

        Sala salaEscolhida = SalasFragment.getSalaEscolhida();

        if (salaEscolhida != null) {
            ((TextView) findViewById(R.id.activity_info_sala_nome)).setText("Sala: " + salaEscolhida.getNome());
            ((TextView) findViewById(R.id.activity_info_sala_localizacao)).setText("Localização: " + salaEscolhida.getLocalizacao());
        }
    }
}
