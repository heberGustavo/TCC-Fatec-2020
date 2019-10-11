package com.apps.heber.restaurante.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.apps.heber.restaurante.R;
import com.apps.heber.restaurante.modelo.Cardapio;

import java.util.List;

public class AdapterCardapio extends RecyclerView.Adapter<AdapterCardapio.MyViewHolderCardapio> {

    List<Cardapio> listaCardapios;
    Context context;

    public AdapterCardapio(List<Cardapio> listaCardapios, Context context) {
        this.listaCardapios = listaCardapios;
        this.context = context;
    }

    @Override
    public MyViewHolderCardapio onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_cardapio, viewGroup, false);

        return new MyViewHolderCardapio(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolderCardapio myViewHolderCardapio, int i) {
        Cardapio cardapio = listaCardapios.get(i);

        myViewHolderCardapio.nomeProduto.setText(cardapio.getNomeProduto());
        myViewHolderCardapio.valor.setText(String.valueOf(cardapio.getValor()));
        myViewHolderCardapio.ingredientes.setText(cardapio.getIngredientes());
    }

    @Override
    public int getItemCount() {
        return listaCardapios.size();
    }

    public class MyViewHolderCardapio extends RecyclerView.ViewHolder{

        TextView nomeProduto, valor, ingredientes;

        public MyViewHolderCardapio(@NonNull View itemView) {
            super(itemView);

            nomeProduto = itemView.findViewById(R.id.textNomeProdutoItemCardapio);
            valor = itemView.findViewById(R.id.textValorItemCardapio);
            ingredientes = itemView.findViewById(R.id.textIngredientesItemCardapio);
        }
    }
}
