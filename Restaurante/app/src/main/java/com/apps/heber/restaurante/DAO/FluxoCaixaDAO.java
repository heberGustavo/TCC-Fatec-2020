package com.apps.heber.restaurante.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.apps.heber.restaurante.helper.DbHelper;

import java.io.Serializable;

public class FluxoCaixaDAO {

    private SQLiteDatabase ler;
    private SQLiteDatabase escrever;

    public FluxoCaixaDAO(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        ler = dbHelper.getReadableDatabase();
        escrever = dbHelper.getWritableDatabase();
    }

    public boolean salvar(double soma){
        ContentValues values = new ContentValues();
        values.put("somaEntrada", soma);

        try {
            escrever.insert(DbHelper.TABELA_FLUXO_CAIXA, null, values);
            escrever.close();
        }catch (Exception e){
            Log.v("INFO", "Erro: "+e.getMessage());
        }

        return true;
    }

    public double valorReceita(){

        double receita = 0;

        String sql = "SELECT * " +
                "FROM " + DbHelper.TABELA_FLUXO_CAIXA + ";";

        Cursor cursor = ler.rawQuery(sql, null);

        while (cursor.moveToNext()){
            receita += cursor.getDouble(cursor.getColumnIndex("somaEntrada"));
        }
        cursor.close();

        ler.close();
        return receita;
    }

}
