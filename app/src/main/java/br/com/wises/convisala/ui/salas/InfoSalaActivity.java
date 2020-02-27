package br.com.wises.convisala.ui.salas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import br.com.wises.convisala.Aplicativo;
import br.com.wises.convisala.R;
import br.com.wises.convisala.dao.ReservaDAO;
import br.com.wises.convisala.model.Reserva;
import br.com.wises.convisala.model.Sala;
import br.com.wises.convisala.model.Usuario;
import br.com.wises.convisala.service.HttpService;
import br.com.wises.convisala.service.MetodoHttp;

public class InfoSalaActivity extends AppCompatActivity {

    private final ReservaDAO dao = new ReservaDAO();
    private final BaseAdapter adapter = new BaseAdapter() {
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
            return dao.obterReserva(posicao).getId();
        }

        @Override
        public View getView(int posicao, View convertView, ViewGroup parent) {
            Reserva reserva = getItem(posicao);
            if (convertView == null) {
                convertView = LayoutInflater.from(InfoSalaActivity.this).inflate(R.layout.item_reserva, parent, false);
            }
            try {
                ((TextView) convertView.findViewById(R.id.item_reserva_tema)).setText(reserva.getDescricao());
                ((TextView) convertView.findViewById(R.id.item_reserva_solicitador)).setText(("por: " + reserva.getNomeOrganizador()));
                //((TextView) convertView.findViewById(R.id.item_reserva_sala)).setText((reserva.getSala().toString()));
                //((TextView) convertView.findViewById(R.id.item_reserva_andar)).setText(("(" + reserva.getSala().getAndar() + "º Andar)"));

                try { ((TextView) convertView.findViewById(R.id.item_reserva_horario_inicio))
                        .setText(("das " + new SimpleDateFormat("HH:mm", Locale.FRANCE).format(reserva.getHoraInicio())));
                } catch (Exception e) {e.printStackTrace();}
                try { ((TextView) convertView.findViewById(R.id.item_reserva_horario_fim))
                        .setText(("às " + new SimpleDateFormat("HH:mm", Locale.FRANCE).format(reserva.getHoraFim())));
                } catch (Exception e) {e.printStackTrace();}
                try { ((TextView) convertView.findViewById(R.id.item_reserva_data_inicio))
                        .setText(("Dia " + new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE).format(reserva.getHoraInicio())));
                } catch (Exception e) {e.printStackTrace();}
                try { ((TextView) convertView.findViewById(R.id.item_reserva_data_fim))
                        .setText(("ao dia " + new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE).format(reserva.getHoraFim())));
                } catch (Exception e) {e.printStackTrace();}

                System.out.println(reserva.getDescricao());
                System.out.println(String.format(getString(R.string.fragment_reservas_solicitador), reserva.getNomeOrganizador()));
            } catch (Exception e) {
                System.err.println("Falha ao gerar uma View no BaseAdapter da Activity InfoSalaActivity; ");
                e.printStackTrace();
            }
            return convertView;
        }
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_sala);

        Sala salaEscolhida = SalasFragment.getSalaEscolhida();

        if (salaEscolhida != null) {
            ((TextView) findViewById(R.id.activity_info_sala_nome)).setText(String.format(getString(R.string.activity_info_sala_nome), salaEscolhida.getNome()));
            ((TextView) findViewById(R.id.activity_info_sala_localizacao)).setText(String.format(getString(R.string.activity_info_sala_localizacao), salaEscolhida.getLocalizacao()));
        }

        ListView listaReservas = findViewById(R.id.activity_info_sala_lista_reservas);
        listaReservas.setAdapter(adapter);

        List<Reserva> reservas = new ArrayList<>();
        try {
            System.out.println("ID do Usuário: " + Aplicativo.gerenciadorLogin.getUsuario().getId() + "; ");
            //String jsonSalas = new HttpListaReservasPorUsuario(Aplicativo.gerenciadorLogin.getUsuario().getId()).execute().get();
            String jsonSalas = new HttpService("reserva/byIdSala/", MetodoHttp.Get,
                    Arrays.asList("authorization","id_sala"), Arrays.asList("secret",""+salaEscolhida.getId()) ).execute().get();
            System.out.println("Interpretando Salas: " + jsonSalas + "; ");
            JSONArray listaJson = new JSONArray(jsonSalas);
            JSONObject obj;
            for (int i = 0; i < listaJson.length(); i++) {
                obj = listaJson.getJSONObject(i);
                Reserva reserva = new Reserva();

                reserva.setId(obj.optInt("id",0));
                reserva.setDescricao(obj.optString("descricao",""));
                reserva.setNomeOrganizador(obj.optString("nomeOrganizador",""));

                reserva.setUsuario(new Usuario());
                reserva.getUsuario().setId(obj.optInt("idUsuario", 0));

                reserva.setSala(new Sala());
                reserva.getSala().setId(obj.optInt("idSala", 0));

                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z[UTC]'", Locale.FRANCE);

                try {
                    Date data;
                    String dataString = obj.optString("dataHoraInicio", "");
                    data = format.parse(dataString);
                    reserva.setHoraInicio(data);
                } catch (Exception e) {e.printStackTrace();}

                try {
                    Date data;
                    String dataString = obj.optString("dataHoraFim", "");
                    data = format.parse(dataString);
                    reserva.setHoraFim(data);
                } catch (Exception e) {e.printStackTrace();}

                try {
                    Date data;
                    String dataString = obj.optString("dataCriacao", "");
                    data = format.parse(dataString);
                    reserva.setDataCriacao(data);
                } catch (Exception e) {e.printStackTrace();}

                try {
                    Date data;
                    String dataString = obj.optString("DataAlteracao", "");
                    data = format.parse(dataString);
                    reserva.setDataAlteracao(data);
                } catch (Exception e) {e.printStackTrace();}

                reservas.add(reserva);
                try {
                    System.out.println("Reservas: Reserva Nº" + (i + 1) + " interpretada com sucesso! " +
                            reserva.getDescricao() + " por " + reserva.getNomeOrganizador() + "; ");
                } catch (Exception e) {e.printStackTrace();}
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        dao.limparListaReservas();
        dao.adicionarLista(reservas);
        adapter.notifyDataSetChanged();
    }

}
