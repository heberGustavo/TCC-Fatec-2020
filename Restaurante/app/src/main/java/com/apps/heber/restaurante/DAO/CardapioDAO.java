package com.apps.heber.restaurante.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.apps.heber.restaurante.helper.DbHelper;
import com.apps.heber.restaurante.modelo.Cardapio;

import java.util.ArrayList;
import java.util.List;

public class CardapioDAO {

    private SQLiteDatabase escrever;
    private SQLiteDatabase ler;

    public CardapioDAO(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        escrever = dbHelper.getWritableDatabase();
        ler = dbHelper.getReadableDatabase();
    }

    public boolean salvar(Cardapio cardapio){
        ContentValues values = new ContentValues();
        values.put("nomeProduto", cardapio.getNomeProduto());
        values.put("descricao", cardapio.getIngredientes());
        values.put("preco", cardapio.getValor());
        values.put("idCategoria", cardapio.getIdCategoria());

        try {
            escrever.insert(DbHelper.TABELA_PRODUTO, null, values);
            Log.i("INFO", "Produto inseridos na tabela: " + values.toString());
        }catch (Exception e){
            Log.i("INFO", "Produto não inseridos na tabela: "+ e.getMessage());
        }

        return true;
    }

    public boolean atualizar(){
        return false;
    }

    public boolean deletar(){
        return false;
    }

    public List<Cardapio> listar(){
        List<Cardapio> listaCardapio = new ArrayList<>();

        String sql = "SELECT * FROM " + DbHelper.TABELA_PRODUTO + ";";

        Cursor cursor = ler.rawQuery(sql, null);

        while (cursor.moveToNext()){
            Cardapio cardapio = new Cardapio();

            Long idProduto = cursor.getLong(cursor.getColumnIndex("idProduto"));
            String nomeProduto = cursor.getString(cursor.getColumnIndex("nomeProduto"));
            String descricao = cursor.getString(cursor.getColumnIndex("descricao"));
            double preco = cursor.getDouble(cursor.getColumnIndex("preco"));
            Long idCategoria = cursor.getLong(cursor.getColumnIndex("idCategoria"));

            cardapio.setIdCardapio(idProduto);
            cardapio.setValor(preco);
            cardapio.setNomeProduto(nomeProduto);
            cardapio.setIngredientes(descricao);
            cardapio.setIdCategoria(idCategoria);

            listaCardapio.add(cardapio);
        }

        return listaCardapio;
    }
}
