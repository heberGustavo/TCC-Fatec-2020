package com.apps.heber.restaurante.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.apps.heber.restaurante.helper.DbHelper;
import com.apps.heber.restaurante.modelo.Categoria;

import java.util.ArrayList;
import java.util.List;

public class CategoriaDAO {

    private SQLiteDatabase escrever;
    private SQLiteDatabase ler;

    public CategoriaDAO(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        escrever = dbHelper.getWritableDatabase();
        ler = dbHelper.getReadableDatabase();
    }

    public boolean salvarCategoria(Categoria categoria){
        ContentValues values = new ContentValues();
        values.put("nomeCategoria", categoria.getCategoria());

        try {
            escrever.insert(DbHelper.TABELA_CATEGORIA, null, values);
            Log.i("INFO", "Tarefa salva com sucesso!"+values.toString());
        }catch (Exception e){
            Log.i("INFO", "Erro ao salvar tarefa ..: " + e.getMessage());
        }
        return true;
    }

    public boolean atualizarCategoria(Categoria categoria){
        ContentValues values = new ContentValues();
        values.put("nomeCategoria", categoria.getCategoria());

        String[] args = {categoria.getId().toString()};
        try {
            escrever.update(DbHelper.TABELA_CATEGORIA, values, "idCategoria=?", args);
            Log.i("INFO", "Categoria atualizada com sucesso!");
        }catch (Exception e){
            Log.i("INFO", "Erro ao atualizar categoria ..: " + e.getMessage());
            return false;
        }

        return true;
    }

    //Nao esta deletando os cardapios cadastrado em uma tal categoria
    public boolean deletarCategoria(Categoria categoria){
        try {
            String[] args = {categoria.getId().toString()};
            escrever.delete(DbHelper.TABELA_CATEGORIA, "idCategoria=?", args);
            escrever.close();
            Log.i("INFO", "Tarefa deletada com sucesso!");
        }catch (Exception e){
            Log.i("INFO", "Erro ao deletar tarefa ..: " + e.getMessage());
            return false;
        }
        return true;
    }

    public List<Categoria> listarCategoria(){

        List<Categoria> listaCategoria = new ArrayList<>();

        String sql = "SELECT * " +
                "FROM " + DbHelper.TABELA_CATEGORIA +
                " order by nomeCategoria " + ";";

        Cursor cursor = ler.rawQuery(sql, null);

        while (cursor.moveToNext()){
            Categoria categoria = new Categoria();

            Long id = cursor.getLong(cursor.getColumnIndex("idCategoria"));
            String nomeCategoria = cursor.getString(cursor.getColumnIndex("nomeCategoria"));

            categoria.setId(id);
            categoria.setCategoria(nomeCategoria);

            listaCategoria.add(categoria);
        }

        return listaCategoria;
    }
}
