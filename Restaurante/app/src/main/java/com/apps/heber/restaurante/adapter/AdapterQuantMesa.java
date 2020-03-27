package com.apps.heber.restaurante.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.apps.heber.restaurante.modelo.QuantMesa;
import com.apps.heber.restaurante.R;

import java.util.List;

import static com.apps.heber.restaurante.R.drawable.ic_restaurant_menu_gray_24dp;

public class AdapterQuantMesa extends RecyclerView.Adapter<AdapterQuantMesa.QuantMesaViewHolder> {

    private Context mContext;
    private List<QuantMesa> mListaMesa;

    public AdapterQuantMesa(Context context, List<QuantMesa> listaMesa){
        mContext = context;
        mListaMesa = listaMesa;
    }

    @Override
    public QuantMesaViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(mContext)
                .inflate(R.layout.item_mesas_fazer_pedido, viewGroup, false);

        return new QuantMesaViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(QuantMesaViewHolder quantMesaViewHolder, int i) {
        QuantMesa mesa = mListaMesa.get(i);

        // --> Pegar pelo Id da mesa
        quantMesaViewHolder.iconeMesa.setImageResource(ic_restaurant_menu_gray_24dp);
        quantMesaViewHolder.numeroMesa.setText("Mesa " + (i+1));
    }

    @Override
    public int getItemCount() {
        return mListaMesa.size();
    }

    public class QuantMesaViewHolder extends RecyclerView.ViewHolder{

        ImageView iconeMesa;
        TextView numeroMesa;

        public QuantMesaViewHolder(View itemView) {
            super(itemView);
            numeroMesa = itemView.findViewById(R.id.textNumeroMesa);
            iconeMesa = itemView.findViewById(R.id.iconeMesa);
        }
    }

}
