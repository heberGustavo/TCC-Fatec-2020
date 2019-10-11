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

public class AdapterCardapioFazerPedido extends RecyclerView.Adapter<AdapterCardapioFazerPedido.MyViewHolderCardapioFazerPedido> {

    List<Cardapio> listaCardapios;
    Context context;

    public AdapterCardapioFazerPedido(List<Cardapio> listaCardapios, Context context) {
        this.listaCardapios = listaCardapios;
        this.context = context;
    }

    @Override
    public MyViewHolderCardapioFazerPedido onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_cardapio, viewGroup, false);

        return new MyViewHolderCardapioFazerPedido(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolderCardapioFazerPedido myViewHolderCardapioFazerPedido, int i) {
        Cardapio cardapio = listaCardapios.get(i);

        myViewHolderCardapioFazerPedido.nomeProduto.setText(cardapio.getNomeProduto());
        myViewHolderCardapioFazerPedido.ingredientes.setText(cardapio.getIngredientes());
        myViewHolderCardapioFazerPedido.valor.setText(String.valueOf(cardapio.getValor()));
    }

    @Override
    public int getItemCount() {
        return listaCardapios.size();
    }

    public class MyViewHolderCardapioFazerPedido extends RecyclerView.ViewHolder{

        TextView nomeProduto, ingredientes, valor;

         public MyViewHolderCardapioFazerPedido(@NonNull View itemView) {
             super(itemView);
             nomeProduto = itemView.findViewById(R.id.textNomeProdutoItemCardapio);
             valor = itemView.findViewById(R.id.textValorItemCardapio);
             ingredientes = itemView.findViewById(R.id.textIngredientesItemCardapio);
         }
     }
}
