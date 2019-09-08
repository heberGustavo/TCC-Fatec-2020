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

public class AdapterMonitoramentoPedidos extends RecyclerView.Adapter<AdapterMonitoramentoPedidos.MyViewHolderMonitoramentoPedidos> {

    List<Pedido> listaPedidos;
    Context context;

    public AdapterMonitoramentoPedidos(List<Pedido> listaPedidos, Context context) {
        this.listaPedidos = listaPedidos;
        this.context = context;
    }

    @Override
    public MyViewHolderMonitoramentoPedidos onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_monitorar_pedido, viewGroup, false);

        return new MyViewHolderMonitoramentoPedidos(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolderMonitoramentoPedidos myViewHolderMonitoramentoPedidos, int i) {
        myViewHolderMonitoramentoPedidos.nomeProduto.setText("Calabresa");
        myViewHolderMonitoramentoPedidos.status.setText("Finalizado");
        myViewHolderMonitoramentoPedidos.nomeCliente.setText("Heber Gustavo");
        myViewHolderMonitoramentoPedidos.numeroMesa.setText("Mesa 3");

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class MyViewHolderMonitoramentoPedidos extends RecyclerView.ViewHolder{

        TextView nomeProduto, nomeCliente, status, numeroMesa;

        public MyViewHolderMonitoramentoPedidos(View itemView) {
            super(itemView);

            nomeProduto = itemView.findViewById(R.id.textNomeProdutoMonitorarPedido);
            status = itemView.findViewById(R.id.textStatusMonitorarPedido);
            nomeCliente = itemView.findViewById(R.id.textNomeClienteMonitorarPedido);
            numeroMesa = itemView.findViewById(R.id.textNumeroMesaMonitorarPedido);


        }
    }
}
