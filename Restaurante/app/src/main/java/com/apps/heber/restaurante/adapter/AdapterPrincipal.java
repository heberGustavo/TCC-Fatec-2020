package com.apps.heber.restaurante.adapter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.apps.heber.restaurante.R;
import com.apps.heber.restaurante.modelo.QuantMesa;

import java.util.List;

import static com.apps.heber.restaurante.R.drawable.ic_restaurant_menu_gray_24dp;

public class AdapterPrincipal extends RecyclerView.Adapter<AdapterPrincipal.MyViewHolderPrincipal> {

    Context context;
    List<QuantMesa> listaMesas;

    public AdapterPrincipal(Context context, List<QuantMesa> listaMesas) {
        this.context = context;
        this.listaMesas = listaMesas;
    }

    @NonNull
    @Override
    public MyViewHolderPrincipal onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.item_mesas_fazer_pedido, viewGroup, false);

        return new MyViewHolderPrincipal(itemView);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull MyViewHolderPrincipal myViewHolderPrincipal, int i) {
        QuantMesa mesa = listaMesas.get(i);

        myViewHolderPrincipal.iconeMesa.setImageResource(ic_restaurant_menu_gray_24dp);
        myViewHolderPrincipal.numeroMesa.setText("Mesa " + mesa.getId());
        myViewHolderPrincipal.statusMesa.setBackground(context.getDrawable(R.drawable.ic_room_service_laranja_24dp));
    }

    @Override
    public int getItemCount() {
        return listaMesas.size();
    }

    public class MyViewHolderPrincipal extends RecyclerView.ViewHolder{

        ImageView iconeMesa;
        TextView numeroMesa;
        TextView statusMesa;

        public MyViewHolderPrincipal(@NonNull View itemView) {
            super(itemView);
            numeroMesa = itemView.findViewById(R.id.textNumeroMesa);
            iconeMesa = itemView.findViewById(R.id.iconeMesa);
            statusMesa = itemView.findViewById(R.id.textStatus);
        }
    }
}
