package br.com.wises.convisala;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private final Booleano confirmacaoSaida = new Booleano (false);
    private AlertDialog.Builder dialogoSaida = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //getSupportActionBar().hide();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        if (fab != null) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                }
            });
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        dialogoSaida = new AlertDialog.Builder(this);
        dialogoSaida.setTitle("Fazer Logout");
        dialogoSaida.setMessage("Deseja sair?");
        dialogoSaida.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                confirmacaoSaida.setTrue();
                Aplicativo.logado = false;
                finish();
            }
        });
        dialogoSaida.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                confirmacaoSaida.setFalse();
            }
        });
        dialogoSaida.create();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        Aplicativo.logado = false;
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        confirmacaoSaida.setFalse();
        if (Aplicativo.logado) {
            dialogoSaida.show();
        } else {
            confirmacaoSaida.setTrue();
            finish();
        }
    }
}

class Booleano {
    private boolean valor = false;

    public Booleano () {
        this.valor = false;
    }

    public Booleano (boolean valor) {
        this.valor = valor;
    }

    public void setTrue() {
        this.valor = true;
    }

    public void setFalse() {
        this.valor = false;
    }

    public void setValor(boolean valor) {
        this.valor = valor;
    }

    public boolean getValor() {
        return valor;
    }

    public boolean isTrue() {
        return valor;
    }

    public boolean isFalse() {
        return !valor;
    }

}
