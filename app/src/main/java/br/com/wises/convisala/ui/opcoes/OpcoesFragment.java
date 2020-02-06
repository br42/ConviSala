package br.com.wises.convisala.ui.opcoes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import br.com.wises.convisala.R;

public class OpcoesFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_opcoes, container, false);
        final TextView textView = root.findViewById(R.id.text_tools);
        return root;
    }
}