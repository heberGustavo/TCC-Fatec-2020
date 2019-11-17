package com.apps.heber.restaurante.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.apps.heber.restaurante.helper.DbHelper;
import com.apps.heber.restaurante.modelo.QuantMesas;

import java.util.ArrayList;
import java.util.List;

public class QuantMesasDAO {

    private SQLiteDatabase escrever;
    private SQLiteDatabase ler;

    public QuantMesasDAO(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        escrever = dbHelper.getWritableDatabase();
        ler = dbHelper.getReadableDatabase();
    }

    public boolean salvarQuantMesas(QuantMesas quantMesas){
        ContentValues values = new ContentValues();
        values.put("numeroMesa", quantMesas.getNumeroMesa());

        Log.v("INFO", "Mesa BD: " + quantMesas.toString());

        try {
            escrever.insert(DbHelper.TABELA_MESA, null, values);
            Log.i("INFO", "Mesa salva com sucesso..: "+quantMesas.toString());
        }catch (Exception e){
            Log.i("INFO", "Erro ao salvar mesa ..: " + e.getMessage());
        }
        return true;
    }

    public boolean atualizarQuantMesa(QuantMesas quantMesas){
        ContentValues values = new ContentValues();
        values.put("numeroMesa", quantMesas.getNumeroMesa());

        String[] args = {String.valueOf(quantMesas.getIdMesa())};
        try {
            escrever.update(DbHelper.TABELA_MESA, values, "idMesa=?", args);
            escrever.close();
            Log.i("INFO", "Mesa atualizada com sucesso!");
        }catch (Exception e){
            Log.i("INFO", "Erro ao atualizar mesa ..: " + e.getMessage());
            return false;
        }

        return true;
    }

    public boolean deletarQuantMesa(QuantMesas quantMesas){
        try {
            String[] args = {String.valueOf(quantMesas.getIdMesa())};
            escrever.delete(DbHelper.TABELA_MESA, "idMesa=?", args);
            escrever.close();
            Log.i("INFO", "Mesa deletada com sucesso!");
        }catch (Exception e){
            Log.i("INFO", "Erro ao deletar mesa ..: " + e.getMessage());
            return false;
        }
        return true;
    }

    public List<QuantMesas> listarQuantMesa(){

        List<QuantMesas> listQuantMesas = new ArrayList<>();

        String sql = "SELECT *  " +
                "FROM " + DbHelper.TABELA_MESA + ";";

        Cursor cursor = ler.rawQuery(sql, null);

        while (cursor.moveToNext()){

            QuantMesas quantMesas = new QuantMesas();

            int id = cursor.getInt(cursor.getColumnIndex("idMesa"));
            int numeroMesa = cursor.getInt(cursor.getColumnIndex("numeroMesa"));

            quantMesas.setIdMesa(id);
            quantMesas.setNumeroMesa(numeroMesa);

            listQuantMesas.add(quantMesas);
            //Log.i("Info", "vvvLista de categorias: " + categoria.toString());
        }

        return listQuantMesas;
    }
}
