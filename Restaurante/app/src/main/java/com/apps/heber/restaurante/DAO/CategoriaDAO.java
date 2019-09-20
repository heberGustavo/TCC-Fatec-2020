package com.apps.heber.restaurante.DAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

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
        return false;
    }

    public boolean atualizarCategoria(Categoria categoria){
        return false;
    }

    public boolean deletarCategoria(Categoria categoria){
        return false;
    }

    public List<Categoria> listarCategoria(){

        List<Categoria> listaCategoria = new ArrayList<>();

        return listaCategoria;
    }
}
