package br.com.wises.convisala.ui.reservas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import br.com.wises.convisala.R;
import br.com.wises.convisala.model.Organizacao;
import br.com.wises.convisala.model.Reserva;
import br.com.wises.convisala.dao.ReservaDAO;
import br.com.wises.convisala.model.Sala;
import br.com.wises.convisala.model.Usuario;
import br.com.wises.convisala.service.HttpListaReservasPorUsuario;
import br.com.wises.convisala.service.HttpSalas;

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

        /*dao.adicionarReserva(new Reserva (0, "Frifaire", new Usuario(0, "Claudinei Neto", "a@b.c", ""),
                new Sala(613,42,"","",""),
                new GregorianCalendar(2020, 2, 29).getTime(), new GregorianCalendar(2020, 2, 29).getTime()));*/

        ListView home_listview = root.findViewById(R.id.home_lista_reservas);

        home_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

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
                ((TextView) convertView.findViewById(R.id.item_reserva_solicitador)).setText(String.format(getString(R.string.fragment_reservas_solicitador), reserva.getNomeOrganizador()));
                ((TextView) convertView.findViewById(R.id.item_reserva_tema)).setText(reserva.getDescricao());
                ((TextView) convertView.findViewById(R.id.item_reserva_sala)).setText((reserva.getSala().toString()));
                ((TextView) convertView.findViewById(R.id.item_reserva_andar)).setText(("("+ reserva.getSala().getAndar()+"º Andar)"));
                ((TextView) convertView.findViewById(R.id.item_reserva_horario_inicio)).setText(("das "+reserva.getHoraInicio()));
                ((TextView) convertView.findViewById(R.id.item_reserva_horario_fim)).setText(("às "+reserva.getHoraFim()));
                //((TextView) convertView.findViewById(R.id.item_reserva_data)).setText(("Dia "+reserva.getData()));
                return convertView;
            }
        };
        home_listview.setAdapter(adapter);


        List<Reserva> reservas = new ArrayList<>();
        try {
            String jsonSalas = new HttpListaReservasPorUsuario(0).execute().get();
            System.out.println("Interpretando Salas: " + jsonSalas + "; ");
            JSONArray listaJson = new JSONArray(jsonSalas);
            JSONObject obj;
            for (int i = 0; i < listaJson.length(); i++) {
                obj = listaJson.getJSONObject(i);
                Reserva reserva = new Reserva();

                /*reserva.setNome(obj.optString("nome",""));
                reserva.setLocalizacao(obj.optString("localizacao",""));
                reserva.setId(obj.optInt("id",0));
                reserva.setMultimidia(obj.optBoolean("possuiMultimidia",false));
                reserva.setArCondicionado(obj.optBoolean("possuiArcon",false));
                reserva.setQuantidadePessoasSentadas(obj.optInt("quantidadePessoasSentadas",0));
                reserva.setArea(obj.optDouble("areaDaSala",0));
                reserva.setLatitude(obj.optDouble("latitude",0));
                reserva.setLongitude(obj.optDouble("longitude",0));

                JSONObject organizacao = obj.optJSONObject("idOrganizacao");
                if (organizacao != null && organizacao.length() > 0) {
                    reserva.setOrganizacao(new Organizacao(
                            organizacao.optInt("id",0),
                            organizacao.optString("nome",""),
                            organizacao.optString("tipoOrganizacao","\0").charAt(0)));
                }*/

                reservas.add(reserva);
                try {
                    System.out.println("Reservas: Reserva Nº" + (i + 1) + " interpretada com sucesso! " +
                            reserva.getDescricao() + " por " + reserva.getNomeOrganizador() + "; ");
                } catch (Exception e) {e.printStackTrace();}
            }
            adapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
        dao.adicionarLista(reservas);

        return root;
    }
    
}