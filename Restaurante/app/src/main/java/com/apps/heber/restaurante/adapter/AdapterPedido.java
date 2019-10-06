package com.apps.heber.restaurante.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.apps.heber.restaurante.R;
import com.apps.heber.restaurante.modelo.Pedido;

import java.util.List;

public class AdapterPedido extends RecyclerView.Adapter<AdapterPedido.MyViewHolderPedidos> {

    List<Pedido> listaPedidos;
    Context context;

    public AdapterPedido(List<Pedido> listaPedidos, Context context) {
        this.listaPedidos = listaPedidos;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolderPedidos onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemPedido = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_cardapio, viewGroup, false);

        return new MyViewHolderPedidos(itemPedido);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolderPedidos myViewHolderPedidos, int i) {
        myViewHolderPedidos.nomeProduto.setText("VidaCap");
        myViewHolderPedidos.valorProduto.setText("500.000,00");
        myViewHolderPedidos.ingredientesProduto.setText("Sorteio");
    }

    @Override
    public int getItemCount() {
        return 11;
    }

    public class MyViewHolderPedidos extends RecyclerView.ViewHolder{

        TextView nomeProduto, valorProduto, ingredientesProduto;

        public MyViewHolderPedidos(@NonNull View itemView) {
            super(itemView);
            nomeProduto = itemView.findViewById(R.id.textNomeProdutoItemCardapio);
            valorProduto = itemView.findViewById(R.id.textValorItemCardapio);
            ingredientesProduto = itemView.findViewById(R.id.textIngredientesItemCardapio);
        }
    }
}
