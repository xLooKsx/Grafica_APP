package com.via.boleto.grafica.util;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.via.boleto.grafica.R;
import com.via.boleto.grafica.model.ProdutoTO;

import java.util.List;

/**
 * Created by lucas.oliveira on 16/04/2018.
 */

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.PersonViewHolder> {

    public static class PersonViewHolder extends RecyclerView.ViewHolder {

        public CardView cv;
        public TextView personName;
        public TextView personAge;
        public ImageView personPhoto;
        public TextView valorVenda;

        PersonViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            personName = (TextView)itemView.findViewById(R.id.nomeProduto);
            personAge = (TextView)itemView.findViewById(R.id.descricaoProduto);
            valorVenda = (TextView)itemView.findViewById(R.id.valorVenda);
        }
    }

    public List<ProdutoTO> persons;

    public RVAdapter(List<ProdutoTO> persons) {
        this.persons = persons;
    }

    /* RVAdapter(List<ProdutoTO> persons){
        this.persons = persons;
    }*/

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_produto, viewGroup, false);
        PersonViewHolder pvh = new PersonViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(PersonViewHolder personViewHolder, int i) {
        personViewHolder.personName.setText(persons.get(i).getNome());
        personViewHolder.personAge.setText(persons.get(i).getTipoProduto());
        personViewHolder.valorVenda.setText(persons.get(i).getValorVenda().toString());
    }

    @Override
    public int getItemCount() {
        return persons.size();
    }
}
