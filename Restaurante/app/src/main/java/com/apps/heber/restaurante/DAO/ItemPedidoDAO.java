package com.apps.heber.restaurante.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.apps.heber.restaurante.helper.DbHelper;
import com.apps.heber.restaurante.modelo.ItemPedido;

import java.util.ArrayList;
import java.util.List;

public class ItemPedidoDAO {

    /*
    private SQLiteDatabase escrever;
    private SQLiteDatabase ler;

    public ItemPedidoDAO(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        escrever = dbHelper.getWritableDatabase();
        ler = dbHelper.getReadableDatabase();
    }

    public boolean salvar(ItemPedido itemPedido){
        ContentValues values = new ContentValues();
        values.put("nomeProduto", itemPedido.getNomeProduto());
        values.put("ingredientes", itemPedido.getIngredientes());
        values.put("quantidade", itemPedido.getQuantidade());
        values.put("valorUnitario", itemPedido.getValorUnitario());
        values.put("valorTotal", itemPedido.getValorTotal());
        values.put("observacao", itemPedido.getObservacao());
        values.put("fkIdProduto", itemPedido.getFkIdCategoria());
        values.put("posicaoMesa", itemPedido.getPosicaoMesa());

        try {
            escrever.insert(DbHelper.TABELA_ITEM_DO_PEDIDO, null, values);
            escrever.close();
        }catch (Exception e){
            Log.i("INFO", "Item de Pedido não inseridos na tabela: "+ e.getMessage());
        }

        return true;
    }

    public boolean atualizar(ItemPedido itemPedido){
        ContentValues values = new ContentValues();
        values.put("quantidade", itemPedido.getQuantidade());
        values.put("valorTotal", itemPedido.getValorTotal());
        values.put("observacao", itemPedido.getObservacao());

        try {
            String[] args = {itemPedido.getIdItemPedido().toString()};
            escrever.update(DbHelper.TABELA_ITEM_DO_PEDIDO, values, "idItemPedido=?", args);
            escrever.close();
        }catch (Exception e){
            Log.i("INFO", "Erro ao atualiza dados da tabela Cardapio: "+e.getMessage());
            return false;
        }
        return true;
    }

    public boolean deletar(ItemPedido itemPedido){
        try {
            String[] args = {itemPedido.getIdItemPedido().toString()};
            escrever.delete(DbHelper.TABELA_ITEM_DO_PEDIDO, "idItemPedido=?", args);
            escrever.close(); //Fecha a conexao
            //Log.i("INFO", "Sucesso na remocao do pedido");
        }catch (Exception e){
            //Log.i("INFO", "Erro na remocao do pedido");
            return false;
        }
        return true;
    }

    public boolean deletar(int numeroMesa){
        try {
            String[] args = {String.valueOf(numeroMesa)};
            escrever.delete(DbHelper.TABELA_ITEM_DO_PEDIDO, "posicaoMesa=?", args);
            escrever.close(); //Fecha a conexao
            Log.i("INFO", "Sucesso na remocao do pedido");
        }catch (Exception e){
            Log.i("INFO", "Erro na remocao do pedido: "+e.getMessage());
            return false;
        }
        return true;
    }

    public List<ItemPedido> listar(int pMesa){
        List<ItemPedido> listaItemPedidos = new ArrayList<>();

        String sql = "SELECT * FROM " + DbHelper.TABELA_ITEM_DO_PEDIDO +
                " WHERE posicaoMesa = " + pMesa + ";";

        Cursor cursor = ler.rawQuery(sql, null);

        while (cursor.moveToNext()){
            ItemPedido itemPedido = new ItemPedido();

            Long idItemProduto = cursor.getLong(cursor.getColumnIndex("idItemPedido"));
            String nomeProduto = cursor.getString(cursor.getColumnIndex("nomeProduto"));
            String ingredientes = cursor.getString(cursor.getColumnIndex("ingredientes"));
            int quantidade = cursor.getInt(cursor.getColumnIndex("quantidade"));
            double valorUnitario = cursor.getDouble(cursor.getColumnIndex("valorUnitario"));
            double valorTotal = cursor.getDouble(cursor.getColumnIndex("valorTotal"));
            String observacao = cursor.getString(cursor.getColumnIndex("observacao"));
            Long fkIdProduto = cursor.getLong(cursor.getColumnIndex("fkIdProduto"));
            int posicaoMesa = cursor.getInt(cursor.getColumnIndex("posicaoMesa"));

            itemPedido.setIdItemPedido(idItemProduto);
            itemPedido.setNomeProduto(nomeProduto);
            itemPedido.setIngredientes(ingredientes);
            itemPedido.setQuantidade(quantidade);
            itemPedido.setValorUnitario(valorUnitario);
            itemPedido.setValorTotal(valorTotal);
            itemPedido.setObservacao(observacao);
            itemPedido.setFkIdCategoria(fkIdProduto);
            itemPedido.setPosicaoMesa(posicaoMesa);

            listaItemPedidos.add(itemPedido);

            ler.close();
        }
        return listaItemPedidos;
    }

    public double listarGastoMesa(int pMesa){

        double soma = 0;

        String sql = "SELECT * " +
                "FROM " + DbHelper.TABELA_ITEM_DO_PEDIDO +
                " WHERE posicaoMesa = " + pMesa + ";";

        Cursor cursor = ler.rawQuery(sql, null);

        while(cursor.moveToNext()){
            soma += cursor.getDouble(cursor.getColumnIndex("valorTotal"));
        }
        cursor.close();
        ler.close();

        return soma;
    }

     */
}
