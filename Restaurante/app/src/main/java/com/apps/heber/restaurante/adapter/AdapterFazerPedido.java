package com.apps.heber.restaurante.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.apps.heber.restaurante.R;
import com.apps.heber.restaurante.modelo.Mesa;

import java.util.List;

public class AdapterFazerPedido extends RecyclerView.Adapter<AdapterFazerPedido.MyViewHolderFazerPedidos> {

    List<Mesa> listaMesas;
    Context context;

    public AdapterFazerPedido(List<Mesa> listaMesas, Context context) {
        this.listaMesas = listaMesas;
        this.context = context;
    }

    @Override
    public MyViewHolderFazerPedidos onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_mesas_fazer_pedido, viewGroup, false);

        return new MyViewHolderFazerPedidos(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolderFazerPedidos myViewHolderFazerPedidos, int i) {
        myViewHolderFazerPedidos.numeroMesa.setText("Mesa 1");
    }

    @Override
    public int getItemCount() {
        return 4;
    }

    public class MyViewHolderFazerPedidos extends RecyclerView.ViewHolder{

        TextView numeroMesa;

        public MyViewHolderFazerPedidos(View itemView) {
            super(itemView);

            numeroMesa = itemView.findViewById(R.id.textNumeroMesa);
        }
    }
}
