package com.via.boleto.grafica.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.via.boleto.grafica.R;
import com.via.boleto.grafica.activity.ListaProdutosActivity;
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
public class DinheiroFragment extends Fragment {

    private View view;
    private static String teste;

    public DinheiroFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_dinheiro, container, false);
        ((PrincipalActivity)getActivity()).setActionBarTitle(getString(R.string.tela_principal_vendas_por_dinheiro_label));
        // Inflate the layout for this fragment




        if (GraficaUtils.verificaConexao(getActivity())){
            GraficaUtils.mostrarStatusDaddos(getActivity());
            new BaseLocalDAO(getContext()).apagarConteudoTabela("valorDinheiro");
            sendRequestDinheito();
            TextView textView = (TextView)view.findViewById(R.id.txt_vlrdinheiro);

        }else{
            TextView textView = (TextView)view.findViewById(R.id.txt_vlrdinheiro);
            textView.setText("R$ " + new BaseLocalDAO(getContext()).getValorDinheiro("valorDinheiro"));
        }


        return view;
    }

    public void sendRequestDinheito( ){

        iRetrofit retrofit = iRetrofit.retrofit.create(iRetrofit.class);
        final Call<Object> call = retrofit.getDinheiro();

        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                int code = response.code();
                if (code == 200){

                    String valorDinheiro = response.body().toString().substring(7, 12);
                    new SharedPref().setLogin(getActivity(), "vlrDinheiro", valorDinheiro);
                    TextView textView = (TextView)view.findViewById(R.id.txt_vlrdinheiro);
                    textView.setText("R$ " + new SharedPref().getLogin(getActivity(), "vlrDinheiro"));
                    new BaseLocalDAO(getContext()).salvarVlrDinheiro(new SharedPref().getLogin(getActivity(), "vlrDinheiro"));
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {


            }
        });
    }

}
