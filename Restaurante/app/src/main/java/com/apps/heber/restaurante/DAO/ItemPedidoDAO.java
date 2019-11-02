package com.apps.heber.restaurante.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.apps.heber.restaurante.helper.DbHelper;
import com.apps.heber.restaurante.modelo.Pedido;

import java.util.ArrayList;
import java.util.List;

public class ItemPedidoDAO {

    private SQLiteDatabase escrever;
    private SQLiteDatabase ler;

    public ItemPedidoDAO(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        escrever = dbHelper.getWritableDatabase();
        ler = dbHelper.getReadableDatabase();
    }

    public boolean salvar(Pedido pedido){
        ContentValues values = new ContentValues();
        values.put("nomeProduto", pedido.getNomeProduto());
        values.put("ingredientes", pedido.getIngredientes());
        values.put("quantidade", pedido.getQuantidade());
        values.put("valorUnitario", pedido.getValorUnitario());
        values.put("valorTotal", pedido.getValorTotal());
        values.put("observacao", pedido.getObservacao());
        values.put("fkIdProduto", pedido.getFkIdCategoria());

        //Log.i("INFO", "vvvBD: "+pedido);

        try {
            escrever.insert(DbHelper.TABELA_ITEM_DO_PEDIDO, null, values);
            escrever.close();
        }catch (Exception e){
            Log.i("INFO", "Item de Pedido não inseridos na tabela: "+ e.getMessage());
        }

        return true;
    }

    public boolean atualizar(Pedido pedido){
        ContentValues values = new ContentValues();
        values.put("quantidade", pedido.getQuantidade());
        values.put("valorTotal", pedido.getValorTotal());
        values.put("observacao", pedido.getObservacao());

        try {
            String[] args = {pedido.getIdItemPedido().toString()};
            escrever.update(DbHelper.TABELA_ITEM_DO_PEDIDO, values, "idItemPedido=?", args);
            escrever.close();
        }catch (Exception e){
            Log.i("INFO", "Erro ao atualiza dados da tabela Cardapio: "+e.getMessage());
            return false;
        }
        return true;
    }

    //Erro ao excluir tarefa
    public boolean deletar(Pedido pedido){
        try {
            String[] args = {pedido.getIdItemPedido().toString()};
            escrever.delete(DbHelper.TABELA_ITEM_DO_PEDIDO, "idItemPedido=?", args);
            escrever.close(); //Fecha a conexao
            //Log.i("INFO", "Sucesso na remocao do pedido");
        }catch (Exception e){
            //Log.i("INFO", "Erro na remocao do pedido");
            return false;
        }
        return true;
    }

    public List<Pedido> listar(){
        List<Pedido> listaPedidos = new ArrayList<>();

        String sql = "SELECT * FROM " + DbHelper.TABELA_ITEM_DO_PEDIDO + ";";

        Cursor cursor = ler.rawQuery(sql, null);

        while (cursor.moveToNext()){
            Pedido pedido = new Pedido();

            Long idItemProduto = cursor.getLong(cursor.getColumnIndex("idItemPedido"));
            String nomeProduto = cursor.getString(cursor.getColumnIndex("nomeProduto"));
            String ingredientes = cursor.getString(cursor.getColumnIndex("ingredientes"));
            int quantidade = cursor.getInt(cursor.getColumnIndex("quantidade"));
            double valorUnitario = cursor.getDouble(cursor.getColumnIndex("valorUnitario"));
            double valorTotal = cursor.getDouble(cursor.getColumnIndex("valorTotal"));
            String observacao = cursor.getString(cursor.getColumnIndex("observacao"));
            Long fkIdProduto = cursor.getLong(cursor.getColumnIndex("fkIdProduto"));

            pedido.setIdItemPedido(idItemProduto);
            pedido.setNomeProduto(nomeProduto);
            pedido.setIngredientes(ingredientes);
            pedido.setQuantidade(quantidade);
            pedido.setValorUnitario(valorUnitario);
            pedido.setValorTotal(valorTotal);
            pedido.setObservacao(observacao);
            pedido.setFkIdCategoria(fkIdProduto);

            listaPedidos.add(pedido);

            ler.close();
        }
        return listaPedidos;
    }
}
