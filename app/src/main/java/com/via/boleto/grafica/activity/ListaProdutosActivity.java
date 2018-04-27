package com.via.boleto.grafica.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.via.boleto.grafica.R;
import com.via.boleto.grafica.dao.BaseLocalDAO;
import com.via.boleto.grafica.util.GraficaUtils;
import com.via.boleto.grafica.util.RVAdapter;
import com.via.boleto.grafica.util.iRetrofit;
import com.via.boleto.grafica.model.ProdutoTO;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListaProdutosActivity extends AppCompatActivity {

    private RecyclerView rv;
    private ProgressBar progressBar;
    private BaseLocalDAO baseLocalDAO;
    private List<ProdutoTO> listaProdutos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_produtos);

        iniciarObjetos();

        baseLocalDAO = new BaseLocalDAO(ListaProdutosActivity.this);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);

        rv.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);


        if (GraficaUtils.verificaConexao(ListaProdutosActivity.this)){
             GraficaUtils.mostrarStatusDaddos(ListaProdutosActivity.this);
             initializeData();
            progressBar.setVisibility(View.GONE);
            rv.setVisibility(View.VISIBLE);
         }else{
             mostrarListaOffline();
             GraficaUtils.mostrarStatusDaddos(ListaProdutosActivity.this);
            progressBar.setVisibility(View.GONE);
            rv.setVisibility(View.VISIBLE);
         }
    }

    private void iniciarObjetos(){

        rv=(RecyclerView)findViewById(R.id.rv);
        progressBar = (ProgressBar) findViewById(R.id.progressBarListaProduto);
    }

    private void mostrarListaOffline() {

        RVAdapter adapter = new RVAdapter(baseLocalDAO.getProdutos());
        rv.setAdapter(adapter);
    }

    private void salvarProdutos(List<ProdutoTO> listaProdutos) {

        for(ProdutoTO produtoDaVez: listaProdutos){

            baseLocalDAO.salvarProduto(produtoDaVez);
        }
    }


    private void initializeData(){

        baseLocalDAO.apagarConteudoTabela("produto");
        iRetrofit retrofit = iRetrofit.retrofit.create(iRetrofit.class);
        final Call<List<ProdutoTO>> call = retrofit.getProduto();

        call.enqueue(new Callback<List<ProdutoTO>>() {
            @Override
            public void onResponse(Call<List<ProdutoTO>> call, Response<List<ProdutoTO>> response) {
                int code = response.code();
                if (code == 200){
                    listaProdutos = response.body();
                    RVAdapter adapter = new RVAdapter(listaProdutos);
                    rv.setAdapter(adapter);
                    salvarProdutos(listaProdutos);
                }
            }

            @Override
            public void onFailure(Call<List<ProdutoTO>> call, Throwable t) {

                Toast.makeText(ListaProdutosActivity.this,  getString(R.string.lista_produtos_erro)+"\n"+getString(R.string.erro_conexao), Toast.LENGTH_LONG).show();
            }
        });
    }
}
