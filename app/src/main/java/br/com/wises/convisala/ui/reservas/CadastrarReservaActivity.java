package br.com.wises.convisala.ui.reservas;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import br.com.wises.convisala.Aplicativo;
import br.com.wises.convisala.R;
import br.com.wises.convisala.dao.SalaDAO;
import br.com.wises.convisala.model.Organizacao;
import br.com.wises.convisala.model.Sala;
import br.com.wises.convisala.service.HttpCadastrarReserva;
import br.com.wises.convisala.service.HttpSalas;

@SuppressWarnings("CharsetObjectCanBeUsed")
public class CadastrarReservaActivity extends AppCompatActivity {

    private final SalaDAO salaDao = new SalaDAO();
    private long dataInicial;
    private long horaInicial;
    private long dataFinal;
    private long horaFinal;

    private final BaseAdapter adapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return salaDao.quantiaDeSalas();
        }

        @Override
        public Sala getItem(int posicao) {
            return salaDao.obterSala(posicao);
        }

        @Override
        public long getItemId(int posicao) {
            return salaDao.obterSala(posicao).getId();
        }

        @Override
        public View getView(int posicao, View convertView, ViewGroup parent) {
            Sala sala = getItem(posicao);
            if (convertView == null) {
                convertView = LayoutInflater.from(CadastrarReservaActivity.this).inflate(R.layout.item_sala, parent, false);
            }
            try {
                ((TextView) convertView.findViewById(R.id.item_sala_nome)).setText(sala.getNome());
                ((TextView) convertView.findViewById(R.id.item_sala_local)).setText(sala.getLocalizacao());
                ((TextView) convertView.findViewById(R.id.item_sala_andar))
                        .setText((sala.getOrganizacao() != null) ? (sala.getOrganizacao().getNome()) : (""));
                ((TextView) convertView.findViewById(R.id.item_sala_bairro)).setText(("" + sala.getQuantidadePessoasSentadas() + " cadeiras"));
                ((TextView) convertView.findViewById(R.id.item_sala_cidade)).setText(("(" + sala.getArea() + "m²)"));
            } catch (Exception e) {
                System.err.println("Falha ao gerar uma view no BaseAdapter da Activity CadastrarReservaActivity; ");
                e.printStackTrace();
            }
            return convertView;
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_reserva);

        Button cadastrar = findViewById(R.id.activity_cadastrar_reserva_enviar);
        Button dataInicioView = findViewById(R.id.activity_cadastrar_reserva_data_inicio);
        final Button horaInicioView = findViewById(R.id.activity_cadastrar_reserva_hora_inicio);
        final Button dataFimView = findViewById(R.id.activity_cadastrar_reserva_data_fim);
        final Button horaFimView = findViewById(R.id.activity_cadastrar_reserva_hora_fim);

        //################################################################################################

        class GerenciadorDatas {

            public long getDataInicial() {
                return dataInicial;
            }

            public void setDataInicial(long data) {
                dataInicial = data;
            }

            public long getHoraInicial() {
                return horaInicial;
            }

            public void setHoraInicial(long hora) {
                horaInicial = hora;
            }

            public long getDataFinal() {
                return dataFinal;
            }

            public void setDataFinal(long data) {
                dataFinal = data;
            }

            public long getHoraFinal() {
                return horaFinal;
            }

            public void setHoraFinal(long hora) {
                horaFinal = hora;
            }
        }

        final GerenciadorDatas gerenciadorDatas = new GerenciadorDatas();

        //################################################################################################

        final AlertDialog.Builder construtor = new AlertDialog.Builder(CadastrarReservaActivity.this);

        // Data Inicial da Reserva;

        final AlertDialog dialogoDataInicio = construtor.create();
        dialogoDataInicio.setView(getLayoutInflater().inflate(R.layout.picker_date, null));
        //dialogoDataInicio.setContentView(R.layout.picker_date);
        dialogoDataInicio.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    //throw new UnsupportedOperationException("Botão OK Clicado!");
                    CalendarView calendario = dialogoDataInicio.findViewById(R.id.picker_date_calendar_view);
                    gerenciadorDatas.setDataInicial(calendario.getDate());
                } catch (Exception e) {e.printStackTrace();}
            }
        });
        dialogoDataInicio.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                        /*try {
                            throw new UnsupportedOperationException("Botão Cancelar Clicado!");
                        } catch (Exception e) {e.printStackTrace();}*/
            }
        });

        // Hora Inicial da Reserva;

        //construtor.setView();
        final AlertDialog dialogoHoraInicio = construtor.create();
        dialogoHoraInicio.setView(getLayoutInflater().inflate(R.layout.picker_time, null));
        //dialogoHoraInicio.setContentView(R.layout.picker_date);
        dialogoHoraInicio.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    //throw new UnsupportedOperationException("Botão OK Clicado!");
                    TimePicker relogio = dialogoHoraInicio.findViewById(R.id.picker_time_clock);
                    if (relogio != null) {
                        gerenciadorDatas.setHoraInicial(new SimpleDateFormat("h:m", Locale.FRANCE)
                            .parse(""+relogio.getCurrentHour()+":"+relogio.getCurrentMinute()).getTime());
                    }
                } catch (Exception e) {e.printStackTrace();}
            }
        });
        dialogoHoraInicio.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                        /*try {
                            throw new UnsupportedOperationException("Botão Cancelar Clicado!");
                        } catch (Exception e) {e.printStackTrace();}*/
            }
        });

        // Data Final da Reserva;

        final AlertDialog dialogoDataFim = construtor.create();
        dialogoDataFim.setView(getLayoutInflater().inflate(R.layout.picker_date, null));
        //dialogoDataFim.setContentView(R.layout.picker_date);
        dialogoDataFim.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    //throw new UnsupportedOperationException("Botão OK Clicado!");
                    CalendarView calendario = dialogoDataFim.findViewById(R.id.picker_date_calendar_view);
                    gerenciadorDatas.setDataFinal(calendario.getDate());
                } catch (Exception e) {e.printStackTrace();}
            }
        });
        dialogoDataFim.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                        /*try {
                            throw new UnsupportedOperationException("Botão Cancelar Clicado!");
                        } catch (Exception e) {e.printStackTrace();}*/
            }
        });

        // Hora Final da Reserva;

        //construtor.setView();
        final AlertDialog dialogoHoraFim = construtor.create();
        dialogoHoraFim.setView(getLayoutInflater().inflate(R.layout.picker_time, null));
        //dialogoHoraFim.setContentView(R.layout.picker_date);
        dialogoHoraFim.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    //throw new UnsupportedOperationException("Botão OK Clicado!");
                    TimePicker relogio = dialogoHoraFim.findViewById(R.id.picker_time_clock);
                    if (relogio != null) {
                        gerenciadorDatas.setHoraFinal(new SimpleDateFormat("h:m", Locale.FRANCE)
                                .parse(""+relogio.getCurrentHour()+":"+relogio.getCurrentMinute()).getTime());
                    }
                } catch (Exception e) {e.printStackTrace();}
            }
        });
        dialogoHoraFim.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                        /*try {
                            throw new UnsupportedOperationException("Botão Cancelar Clicado!");
                        } catch (Exception e) {e.printStackTrace();}*/
            }
        });

        //################################################################################################

        dataInicioView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogoDataInicio.show();
            }
        });

        //################################################################################################

        horaInicioView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogoHoraInicio.show();
            }
        });

        //################################################################################################

        dataFimView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogoDataFim.show();
            }
        });

        //################################################################################################

        horaFimView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogoHoraFim.show();
            }
        });

        //################################################################################################

        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText descricaoView = findViewById(R.id.activity_cadastrar_reserva_descricao);
                Spinner salaView = findViewById(R.id.activity_cadastrar_reserva_sala);

                String descricao = descricaoView.getText().toString();
                int salaId = 0;
                try {salaId = (int) salaView.getSelectedItemId();} catch (Exception e) {e.printStackTrace();}
                //long horaInicio = GregorianCalendar.getInstance().getTimeInMillis(); //horaInicioView.getDate();
                //long horaFim = GregorianCalendar.getInstance().getTimeInMillis() + 60000; //horaFimView.getDate();
                long horaInicio = dataInicial + horaInicial;
                long horaFim = dataFinal + horaFinal;

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

        //################################################################################################

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
        } catch (Exception e) {
            e.printStackTrace();
        }
        salaDao.adicionarLista(salas);

        Spinner listaSalas = findViewById(R.id.activity_cadastrar_reserva_sala);
        listaSalas.setAdapter(adapter);
    }
}
