package com.via.boleto.grafica.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.via.boleto.grafica.R;
import com.via.boleto.grafica.activity.PrincipalActivity;
import com.via.boleto.grafica.dao.BaseLocalDAO;
import com.via.boleto.grafica.util.GraficaUtils;
import com.via.boleto.grafica.util.SharedPref;
import com.via.boleto.grafica.util.iRetrofit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by lucas.oliveira on 16/04/2018.
 * A simple {@link Fragment} subclass.
 */
public class CartaoDebitoFragment extends Fragment {


    private View view;
    public CartaoDebitoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_cartao_debito, container, false);
        ((PrincipalActivity)getActivity()).setActionBarTitle("Vendas por Débito");
        // Inflate the layout for this fragment

        if (GraficaUtils.verificaConexao(getActivity())){
            GraficaUtils.mostrarStatusDaddos(getActivity());
            new BaseLocalDAO(getContext()).apagarConteudoTabela("valorDebito");
            sendRequestDebito();
            TextView textView = (TextView)view.findViewById(R.id.txt_vlrCartaoDebito);

        }else{
            TextView textView = (TextView)view.findViewById(R.id.txt_vlrCartaoDebito);
            textView.setText("R$ " + new BaseLocalDAO(getContext()).getValorDebito("valorDebito"));
        }

        return view;
    }

    public void sendRequestDebito(){

        iRetrofit retrofit = iRetrofit.retrofit.create(iRetrofit.class);
        final Call<Object> call = retrofit.getDebito();

        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                int code = response.code();
                if (code == 200){

                    String valorDebito = response.body().toString().substring(7, 12);
                    new SharedPref().setLogin(getActivity(), "valorDebito", valorDebito);
                    TextView textView = (TextView)view.findViewById(R.id.txt_vlrCartaoDebito);
                    textView.setText("R$ " + new SharedPref().getLogin(getActivity(), "valorDebito"));
                    new BaseLocalDAO(getContext()).salvarVlrDebito(new SharedPref().getLogin(getActivity(), "valorDebito"));
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {


            }
        });
    }

}
