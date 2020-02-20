package br.com.wises.convisala.ui.reservas;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

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
import br.com.wises.convisala.model.Reserva;
import br.com.wises.convisala.dao.ReservaDAO;
import br.com.wises.convisala.model.Sala;
import br.com.wises.convisala.model.Usuario;
import br.com.wises.convisala.service.HttpListaReservasPorUsuario;
import br.com.wises.convisala.service.HttpService;
import br.com.wises.convisala.service.MetodoHttp;

public class ReservasFragment extends Fragment {

    private final ReservaDAO dao = new ReservaDAO();
    private static Reserva reservaEscolhida = null;
    private AlertDialog.Builder dialogoRemocao = null;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_reservas, container, false);

        class ObjReserva {
            void setReserva(Reserva reserva) {
                ReservasFragment.setReservaEscolhida(reserva);
            }

            Reserva getReserva() {
                return ReservasFragment.getReservaEscolhida();
            }
        }

        final ObjReserva objReserva = new ObjReserva();

        //FloatingActionButton fab = getView().findViewById(R.id.fab);
        FloatingActionButton fab = root.findViewById(R.id.fab);
        if (fab != null) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //        .setAction("Action", null).show();

                startActivity(new Intent(getContext(), CadastrarReservaActivity.class));
                }
            });
        }

        ListView reservas_listview = root.findViewById(R.id.home_lista_reservas);
        final BaseAdapter adapter = new BaseAdapter() {
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
                    convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_reserva, parent, false);
                }
                try {
                    ((TextView) convertView.findViewById(R.id.item_reserva_tema)).setText(reserva.getDescricao());
                    ((TextView) convertView.findViewById(R.id.item_reserva_solicitador)).setText(("por: " + reserva.getNomeOrganizador()));
                    //((TextView) convertView.findViewById(R.id.item_reserva_sala)).setText((reserva.getSala().toString()));
                    //((TextView) convertView.findViewById(R.id.item_reserva_andar)).setText(("(" + reserva.getSala().getAndar() + "º Andar)"));

                    try { ((TextView) convertView.findViewById(R.id.item_reserva_horario_inicio))
                            .setText(("das " + new SimpleDateFormat("hh:mm", Locale.FRANCE).format(reserva.getHoraInicio())));
                    } catch (Exception e) {e.printStackTrace();}
                    try { ((TextView) convertView.findViewById(R.id.item_reserva_horario_fim))
                            .setText(("às " + new SimpleDateFormat("hh:mm", Locale.FRANCE).format(reserva.getHoraInicio())));
                    } catch (Exception e) {e.printStackTrace();}
                    try { ((TextView) convertView.findViewById(R.id.item_reserva_data))
                            .setText(("Dia " + new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE).format(reserva.getHoraInicio())));
                    } catch (Exception e) {e.printStackTrace();}

                    System.out.println(reserva.getDescricao());
                    System.out.println(String.format(getString(R.string.fragment_reservas_solicitador), reserva.getNomeOrganizador()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return convertView;
            }
        };
        reservas_listview.setAdapter(adapter);

        /*dao.adicionarReserva(new Reserva (0, "Frifaire", new Usuario(0, "Claudinei Neto", "a@b.c", ""),
                new Sala(613,42,"","",""),
                new GregorianCalendar(2020, 2, 29).getTime(), new GregorianCalendar(2020, 2, 29).getTime()));*/

        dialogoRemocao = new AlertDialog.Builder(getContext());
        dialogoRemocao.setTitle("Cancelar Reserva");
        dialogoRemocao.setMessage("Deseja cancelar essa reserva?");
        dialogoRemocao.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //nada;
                objReserva.getReserva();
            }
        });
        dialogoRemocao.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    String json = new HttpService("/reserva/cancelar", MetodoHttp.Post,
                        Arrays.asList("authorization","id_reserva"), Arrays.asList("secret", ""+objReserva.getReserva().getId())
                    ).execute().get();
                    System.out.println("Remoção de Reserva: " + json);
                    dao.removerReserva(objReserva.getReserva());
                    adapter.notifyDataSetChanged();
                } catch (Exception e) {e.printStackTrace();}
                //confirmacaoSaida.setFalse();
            }
        });
        dialogoRemocao.create();


        reservas_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        registerForContextMenu(reservas_listview);

        reservas_listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //view.showContextMenu();
                objReserva.setReserva(dao.obterReserva(position));

                return false;
            }
        });

        List<Reserva> reservas = new ArrayList<>();
        try {
            System.out.println("ID do Usuário: " + Aplicativo.gerenciadorLogin.getUsuario().getId() + "; ");
            String jsonSalas = new HttpListaReservasPorUsuario(Aplicativo.gerenciadorLogin.getUsuario().getId()).execute().get();
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

                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'Z[UTC]'", Locale.FRANCE);

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
            adapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
        dao.adicionarLista(reservas);

        return root;

    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        try {
            if (getActivity() != null) {
                getActivity().getMenuInflater().inflate(R.menu.lista_menu, menu);
            } else {
                throw new NullPointerException("O método \"getActivity\" retornou valor nulo.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.fragment_reservas_lista_menu_editar) {
            // Editar;
            startActivity(new Intent (getContext(), InfoReservaActivity.class));
        } else if (item.getItemId() == R.id.fragment_reservas_lista_menu_remover) {
            // Remover;
            dialogoRemocao.show();
        }

        return super.onContextItemSelected(item);
    }

    //@SuppressWarnings("WeakerAccess")

    private static void setReservaEscolhida(Reserva reserva) {
        ReservasFragment.reservaEscolhida = reserva;
    }

    @SuppressWarnings("WeakerAccess")
    public static Reserva getReservaEscolhida() {
        return ReservasFragment.reservaEscolhida;
    }
}