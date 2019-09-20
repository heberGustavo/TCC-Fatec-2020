package com.apps.heber.restaurante.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
            Log.i("INFO", "Tarefa salva com sucesso!");
        }catch (Exception e){
            Log.i("INFO", "Erro ao salvar tarefa ..: " + e.getMessage());
        }
        return true;
    }

    public boolean atualizarCategoria(Categoria categoria){
        return false;
    }

    public boolean deletarCategoria(Categoria categoria){
        return false;
    }

    public List<Categoria> listarCategoria(){

        List<Categoria> listaCategoria = new ArrayList<>();

        String sql = "SELECT * FROM " + DbHelper.TABELA_CATEGORIA + ";";
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
