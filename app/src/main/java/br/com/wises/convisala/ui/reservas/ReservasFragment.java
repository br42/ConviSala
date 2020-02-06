package br.com.wises.convisala.ui.reservas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import br.com.wises.convisala.R;
import br.com.wises.convisala.model.Reserva;
import br.com.wises.convisala.dao.ReservaDAO;
import br.com.wises.convisala.model.Sala;

public class ReservasFragment extends Fragment {

    private final ReservaDAO dao = new ReservaDAO();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_reservas, container, false);
        //final TextView textView = root.findViewById(R.id.text_home);
        //homeViewModel.getText().observe(this, new Observer<String>() {
        //    @Override
        //    public void onChanged(@Nullable String s) {
        //        textView.setText(s);
        //    }
        //});

        //FloatingActionButton fab = getView().findViewById(R.id.fab);
        FloatingActionButton fab = root.findViewById(R.id.fab);
        if (fab != null) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });
        }

        dao.adicionarReserva(new Reserva ("Frifaire","Claudinei Neto",
                new Sala(613,42,"","","")
                ,0,0,0));

        ListView home_listview = root.findViewById(R.id.home_lista_reservas);
        BaseAdapter adapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return dao.quantiaDeReservas();
            }

            @Override
            public Reserva getItem(int posicao) {
                return dao.obterReserva(posicao);
            }

            @Override
            public long getItemId(int posicao) {
                return 0;
            }

            @Override
            public View getView(int posicao, View convertView, ViewGroup parent) {
                Reserva reserva = getItem(posicao);
                if (convertView == null) {
                    convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_reserva, parent, false);
                }
                ((TextView) convertView.findViewById(R.id.item_reserva_solicitador)).setText("por "+reserva.getSolicitador());
                ((TextView) convertView.findViewById(R.id.item_reserva_tema)).setText(reserva.getTema());
                ((TextView) convertView.findViewById(R.id.item_reserva_sala)).setText((reserva.getSala().toString()));
                ((TextView) convertView.findViewById(R.id.item_reserva_andar)).setText(("("+ reserva.getSala().getAndar()+"º Andar)"));
                ((TextView) convertView.findViewById(R.id.item_reserva_horario_inicio)).setText(("das "+reserva.getHoraInicio()));
                ((TextView) convertView.findViewById(R.id.item_reserva_horario_fim)).setText(("às "+reserva.getHoraFim()));
                ((TextView) convertView.findViewById(R.id.item_reserva_data)).setText(("Dia "+reserva.getData()));
                return convertView;
            }
        };
        home_listview.setAdapter(adapter);

        return root;
    }
    
}