package com.via.boleto.grafica.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.via.boleto.grafica.R;
import com.via.boleto.grafica.activity.PrincipalActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class CartaoDebitoFragment extends Fragment {


    public CartaoDebitoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((PrincipalActivity)getActivity()).setActionBarTitle("Vendas por Débito");
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cartao_debito, container, false);
    }

}