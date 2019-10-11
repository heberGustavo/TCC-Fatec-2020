package com.apps.heber.restaurante.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

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
            escrever.close();
        }catch (Exception e){
            Log.i("INFO", "Produto não inseridos na tabela: "+ e.getMessage());
        }

        return true;
    }

    public boolean atualizar(Cardapio cardapio){
        ContentValues values = new ContentValues();
        values.put("nomeProduto", cardapio.getNomeProduto());
        values.put("descricao", cardapio.getIngredientes());
        values.put("preco", cardapio.getValor());
        values.put("idCategoria", cardapio.getIdCategoria());

        try {
            String[] args = {cardapio.getIdCardapio().toString()};
            escrever.update(DbHelper.TABELA_PRODUTO, values, "idCardapio=?", args);
            escrever.close();
        }catch (Exception e){
            Log.i("INFO", "Erro ao excluir dados da tabela Cardapio");
            return false;
        }

        return true;
    }

    //Erro ao excluir tarefa
    public boolean deletar(Cardapio cardapio){
        try {
            String[] args = {cardapio.getIdCardapio().toString()};
            escrever.delete(DbHelper.TABELA_PRODUTO, "idProduto=?", args);
            escrever.close(); //Fecha a conexao
            Log.i("INFO", "Sucesso na remocao do cardapio");
        }catch (Exception e){
            Log.i("INFO", "Erro na remocao do cardapio");
            return false;
        }
        return true;
    }

    public List<Cardapio> listar(Long posicao){
        List<Cardapio> listaCardapio = new ArrayList<>();
        //Log.i("INFO", "Posicao BD 2: "+posicao);
        String sql = "SELECT * FROM " + DbHelper.TABELA_PRODUTO +
                " WHERE idCategoria = " + posicao +
                " order by nomeProduto;";

        Cursor cursor = ler.rawQuery(sql, null);

        while (cursor.moveToNext()){
                Cardapio cardapio = new Cardapio();

                Long idProduto = cursor.getLong(cursor.getColumnIndex("idProduto"));
                String nomeProduto = cursor.getString(cursor.getColumnIndex("nomeProduto"));
                String descricao = cursor.getString(cursor.getColumnIndex("descricao"));
                double preco = cursor.getDouble(cursor.getColumnIndex("preco"));
                Long idCategoria = cursor.getLong(cursor.getColumnIndex("idCategoria"));

                cardapio.setIdCardapio(idProduto);
                cardapio.setNomeProduto(nomeProduto);
                cardapio.setIngredientes(descricao);
                cardapio.setValor(preco);
                cardapio.setIdCategoria(idCategoria);

                listaCardapio.add(cardapio);
                //Log.i("INFO", "Lista BD: "+cardapio.getIdCategoria());
                ler.close();

        }
        return listaCardapio;
    }
}
