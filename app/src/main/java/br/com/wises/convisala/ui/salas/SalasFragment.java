package br.com.wises.convisala.ui.salas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import br.com.wises.convisala.R;

public class SalasFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_salas, container, false);
        final TextView textView = root.findViewById(R.id.text_gallery);
        textView.setText("Uau!");
        return root;
    }
}