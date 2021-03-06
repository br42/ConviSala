package br.com.wises.convisala.ui.salas;

import android.content.Intent;
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
import java.util.List;

import br.com.wises.convisala.service.HttpSalas;
import br.com.wises.convisala.R;
import br.com.wises.convisala.dao.SalaDAO;
import br.com.wises.convisala.model.Organizacao;
import br.com.wises.convisala.model.Sala;

@SuppressWarnings("WeakerAccess")
public class SalasFragment extends Fragment {

    private final SalaDAO dao = new SalaDAO();
    private static Sala salaEscolhida = null;
    private final BaseAdapter adapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return dao.quantiaDeSalas();
        }

        @Override
        public Sala getItem(int posicao) {
            return dao.obterSala(posicao);
        }

        @Override
        public long getItemId(int posicao) {
            return dao.obterSala(posicao).getId();
        }

        @Override
        public View getView(int posicao, View convertView, ViewGroup parent) {
            Sala sala = getItem(posicao);
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_sala, parent, false);
            }
            try {
                ((TextView) convertView.findViewById(R.id.item_sala_nome)).setText(sala.getNome());
                ((TextView) convertView.findViewById(R.id.item_sala_local)).setText(sala.getLocalizacao());
                ((TextView) convertView.findViewById(R.id.item_sala_andar))
                        .setText((sala.getOrganizacao() != null) ? (sala.getOrganizacao().getNome()) : (""));
                ((TextView) convertView.findViewById(R.id.item_sala_bairro)).setText(("" + sala.getQuantidadePessoasSentadas() + " cadeiras"));
                ((TextView) convertView.findViewById(R.id.item_sala_cidade)).setText(("(" + sala.getArea() + "m²)"));
            } catch (Exception e) {
                System.err.println("Falha ao gerar uma view no BaseAdapter da Activity SalasFragment; ");
                e.printStackTrace();
            }
            return convertView;
        }
    };

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_salas, container, false);
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
                    Snackbar.make(view, "Não é possível criar salas sem permissão. \n" +
                            "Contate o administrador para mais informações. ", Snackbar.LENGTH_LONG)
                            .setAction("Ação", null).show();
                }
            });
        }

        ListView home_listview = root.findViewById(R.id.salas_lista_salas);

        home_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                setSalaEscolhida(dao.obterSala(position));
                startActivity(new Intent(getContext(), InfoSalaActivity.class));
            }
        });

        //dao.adicionarSala(new Sala(613,42,"","",""));
        home_listview.setAdapter(adapter);

        List<Sala> salas = new ArrayList<>();
        try {
            String jsonSalas = new HttpSalas().execute().get();
            System.out.println("Interpretando Salas: " + jsonSalas + "; ");
            JSONArray listaJson = new JSONArray(jsonSalas);
            JSONObject obj;
            for (int i = 0; i < listaJson.length(); i++) {
                obj = listaJson.getJSONObject(i);
                Sala sala = new Sala();

                sala.setNome(obj.optString("nome",""));
                sala.setLocalizacao(obj.optString("localizacao",""));
                sala.setId(obj.optInt("id",0));
                sala.setMultimidia(obj.optBoolean("possuiMultimidia",false));
                sala.setArCondicionado(obj.optBoolean("possuiArcon",false));
                sala.setQuantidadePessoasSentadas(obj.optInt("quantidadePessoasSentadas",0));
                sala.setArea(obj.optDouble("areaDaSala",0));
                sala.setLatitude(obj.optDouble("latitude",0));
                sala.setLongitude(obj.optDouble("longitude",0));

                JSONObject organizacao = obj.optJSONObject("idOrganizacao");
                if (organizacao != null && organizacao.length() > 0) {
                    sala.setOrganizacao(new Organizacao(
                            organizacao.optInt("id",0),
                            organizacao.optString("nome",""),
                            organizacao.optString("tipoOrganizacao","\0").charAt(0)));
                }

                salas.add(sala);
                try {
                    System.out.println("Salas: Sala Nº" + (i+1) + " interpretada com sucesso! " +
                        sala.getNome() + " em " + sala.getLocalizacao() + "; ");
                } catch (Exception e) {e.printStackTrace();}
            }
            adapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
        dao.adicionarLista(salas);

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();

        adapter.notifyDataSetChanged();
    }

    private void setSalaEscolhida(Sala sala) {
        SalasFragment.salaEscolhida = sala;
    }

    public static Sala getSalaEscolhida() {
        return salaEscolhida;
    }

}