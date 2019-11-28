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

public class AdapterPrincipal extends RecyclerView.Adapter<AdapterPrincipal.MyViewHolderPrincipal> {

    Context context;
    List<QuantMesas> listaMesas;

    public AdapterPrincipal(Context context, List<QuantMesas> listaMesas) {
        this.context = context;
        this.listaMesas = listaMesas;
    }

    @NonNull
    @Override
    public MyViewHolderPrincipal onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_view_item, viewGroup, false);

        return new MyViewHolderPrincipal(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolderPrincipal myViewHolderPrincipal, int i) {
        QuantMesas quantMesas = listaMesas.get(i);

        myViewHolderPrincipal.numeroMesa.setText(String.valueOf(quantMesas.getIdMesa()));
    }

    @Override
    public int getItemCount() {
        return listaMesas.size();
    }

    public class MyViewHolderPrincipal extends RecyclerView.ViewHolder{

        TextView numeroMesa;

        public MyViewHolderPrincipal(@NonNull View itemView) {
            super(itemView);
            numeroMesa = itemView.findViewById(R.id.numeracaoMesas);
        }
    }
}
