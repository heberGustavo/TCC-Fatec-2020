package com.apps.heber.restaurante.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.apps.heber.restaurante.R;
import com.apps.heber.restaurante.modelo.Categoria;
import com.apps.heber.restaurante.modelo.FluxoCaixa;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AdapterFluxoDeCaixa extends RecyclerView.Adapter<AdapterFluxoDeCaixa.MyViewHolderFluxoDeCaixa> {

    List<FluxoCaixa> listaFluxoDeCaixa;
    Context context;

    public AdapterFluxoDeCaixa(List<FluxoCaixa> listaFluxoDeCaixa, Context context) {
        this.listaFluxoDeCaixa = listaFluxoDeCaixa;
        this.context = context;
    }

    @Override
    public MyViewHolderFluxoDeCaixa onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_cardapio, viewGroup, false);

        return new MyViewHolderFluxoDeCaixa(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolderFluxoDeCaixa myViewHolderFluxoDeCaixa, int i) {

        FluxoCaixa fluxoCaixa = listaFluxoDeCaixa.get(i);
        myViewHolderFluxoDeCaixa.tipo.setText(fluxoCaixa.getTipo());

        try {
            SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date dateSistema = formato.parse(fluxoCaixa.getDataFluxo());
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String dataFormatada = dateFormat.format(dateSistema);

            myViewHolderFluxoDeCaixa.data.setText(dataFormatada);

        } catch (ParseException e) {
            e.printStackTrace();
            Log.v("INFO", "zzzData formatada: " + e.toString() +" / "+ e.getMessage());
        }


        //Se for receita
        if(fluxoCaixa.getReceita() > 0){
            myViewHolderFluxoDeCaixa.valor.setText(String.valueOf(fluxoCaixa.getReceita()));
            myViewHolderFluxoDeCaixa.valor.setTextColor(context.getResources().getColor(R.color.colorGreen));
        }
        //Se for despesa
        else{
            myViewHolderFluxoDeCaixa.valor.setText(String.valueOf(fluxoCaixa.getDespesa()));
            myViewHolderFluxoDeCaixa.valor.setTextColor(context.getResources().getColor(R.color.colorRed));
        }
    }

    @Override
    public int getItemCount() {
        return listaFluxoDeCaixa.size();
    }

    public class MyViewHolderFluxoDeCaixa extends RecyclerView.ViewHolder{

        TextView data;
        TextView valor;
        TextView tipo;

        public MyViewHolderFluxoDeCaixa(@NonNull View itemView) {
            super(itemView);

            data = itemView.findViewById(R.id.textNomeProdutoItemCardapio);
            valor = itemView.findViewById(R.id.textValorItemCardapio);
            tipo = itemView.findViewById(R.id.textIngredientesItemCardapio);
        }
    }
}
