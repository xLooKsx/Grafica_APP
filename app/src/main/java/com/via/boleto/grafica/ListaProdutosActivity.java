package com.via.boleto.grafica;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.via.boleto.grafica.model.ProdutoTO;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListaProdutosActivity extends AppCompatActivity {

//    private List<ProdutoTO> produtos;
    private RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_produtos);

        rv=(RecyclerView)findViewById(R.id.rv);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);

        initializeData();
//        initializeAdapter();
    }

    private void initializeData(){

        iRetrofit retrofit = iRetrofit.retrofit.create(iRetrofit.class);
        final Call<List<ProdutoTO>> call = retrofit.getProduto();

        call.enqueue(new Callback<List<ProdutoTO>>() {
            @Override
            public void onResponse(Call<List<ProdutoTO>> call, Response<List<ProdutoTO>> response) {
                int code = response.code();
                if (code == 200){
                    List<ProdutoTO> listaProdutos = response.body();
                    RVAdapter adapter = new RVAdapter(listaProdutos);
                    rv.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<ProdutoTO>> call, Throwable t) {

            }
        });
    }
}
