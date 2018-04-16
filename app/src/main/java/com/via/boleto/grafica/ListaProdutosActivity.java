package com.via.boleto.grafica;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.via.boleto.grafica.model.ProdutoTO;

import java.util.ArrayList;
import java.util.List;

public class ListaProdutosActivity extends AppCompatActivity {

    private List<ProdutoTO> produtos;
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
        initializeAdapter();
    }

    private void initializeData(){
        produtos = new ArrayList<>();
        produtos.add(new ProdutoTO(1, "papel", 2.6, "não Comestivel"));
        produtos.add(new ProdutoTO(1, "cartolina", 0.6, "não Comestivel"));
        produtos.add(new ProdutoTO(1, "EVA", 4.6, "não Comestivel"));

    }

    private void initializeAdapter(){
        RVAdapter adapter = new RVAdapter(produtos);
        rv.setAdapter(adapter);
    }
}
