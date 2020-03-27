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

public class AdapterCategoria extends RecyclerView.Adapter<AdapterCategoria.MyViewHolderCategoria> {

    List<Categoria> listaCategorias;
    Context context;

    public AdapterCategoria(List<Categoria> listaCategorias, Context context) {
        this.listaCategorias = listaCategorias;
        this.context = context;
    }

    @Override
    public MyViewHolderCategoria onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_categoria, viewGroup, false);

        return new MyViewHolderCategoria(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolderCategoria myViewHolderCategoria, int i) {

        Categoria categoria = listaCategorias.get(i);
        myViewHolderCategoria.nomeCategoria.setText(categoria.getCategoria());
    }

    @Override
    public int getItemCount() {
        return listaCategorias.size();
    }

    public class MyViewHolderCategoria extends RecyclerView.ViewHolder{

        TextView nomeCategoria;

        public MyViewHolderCategoria(@NonNull View itemView) {
            super(itemView);

            nomeCategoria = itemView.findViewById(R.id.textNomeCategoriaItemCategoria);
        }
    }
}
