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

public class AdapterCardapioCategoriaFazerPedidoActivity extends RecyclerView.Adapter<AdapterCardapioCategoriaFazerPedidoActivity.MyViewHolderCardapioCategoriaFazerPedidoActivity> {

    List<Categoria> listaCategorias;
    Context context;

    public AdapterCardapioCategoriaFazerPedidoActivity(List<Categoria> listaCategorias, Context context) {
        this.listaCategorias = listaCategorias;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolderCardapioCategoriaFazerPedidoActivity onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View item = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_cardapio_categoria, viewGroup, false);

        return new MyViewHolderCardapioCategoriaFazerPedidoActivity(item);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolderCardapioCategoriaFazerPedidoActivity myViewHolderCardapioCategoriaFazerPedidoActivity, int i) {
        Categoria categoria = listaCategorias.get(i);
        myViewHolderCardapioCategoriaFazerPedidoActivity.nomeCategoria.setText(categoria.getCategoria());
    }

    @Override
    public int getItemCount() {
        return listaCategorias.size();
    }

    public class MyViewHolderCardapioCategoriaFazerPedidoActivity extends RecyclerView.ViewHolder{

        TextView nomeCategoria;

        public MyViewHolderCardapioCategoriaFazerPedidoActivity(@NonNull View itemView) {
            super(itemView);
            nomeCategoria = itemView.findViewById(R.id.textNomeCategoriaCardapioCategoria);
        }
    }
}
