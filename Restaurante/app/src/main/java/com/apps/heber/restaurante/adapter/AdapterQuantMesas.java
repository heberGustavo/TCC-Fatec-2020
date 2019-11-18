package com.apps.heber.restaurante.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.apps.heber.restaurante.R;
import com.apps.heber.restaurante.modelo.QuantMesas;

import java.util.List;

public class AdapterQuantMesas extends RecyclerView.Adapter<AdapterQuantMesas.MyViewHolderFazerPedidos> {

    List<QuantMesas> listaMesas;
    Context context;

    public AdapterQuantMesas(List<QuantMesas> listaMesas, Context context) {
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
        QuantMesas quantMesas = listaMesas.get(i);

        myViewHolderFazerPedidos.numeroMesa.setText("Mesa " +quantMesas.getIdMesa());
    }

    @Override
    public int getItemCount() {
        return listaMesas.size();
    }

    public class MyViewHolderFazerPedidos extends RecyclerView.ViewHolder{

        TextView numeroMesa;

        public MyViewHolderFazerPedidos(View itemView) {
            super(itemView);

            numeroMesa = itemView.findViewById(R.id.textNumeroMesa);
        }
    }
}
