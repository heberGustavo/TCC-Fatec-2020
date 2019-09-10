package com.apps.heber.restaurante.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.apps.heber.restaurante.R;
import com.apps.heber.restaurante.modelo.Categoria;

import java.util.List;

public class AdapterCardapioCategoria extends RecyclerView.Adapter<AdapterCardapioCategoria.MyViewHolderCardapioCategoria> {

    List<Categoria> listaCategorias;
    Context context;

    public AdapterCardapioCategoria(List<Categoria> listaCategorias, Context context) {
        this.listaCategorias = listaCategorias;
        this.context = context;
    }

    @Override
    public MyViewHolderCardapioCategoria onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_cardapio_categoria, viewGroup,false);

        return new MyViewHolderCardapioCategoria(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolderCardapioCategoria myViewHolderCardapioCategoria, int i) {
        myViewHolderCardapioCategoria.nomeCategoria.setText("Lanches");
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    public class MyViewHolderCardapioCategoria extends RecyclerView.ViewHolder{

        TextView nomeCategoria;

        public MyViewHolderCardapioCategoria(View itemView) {
            super(itemView);

            nomeCategoria = itemView.findViewById(R.id.textNomeCategoriaCardapioCategoria);
        }
    }
}
