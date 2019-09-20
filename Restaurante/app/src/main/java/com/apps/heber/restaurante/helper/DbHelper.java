package com.apps.heber.restaurante.helper;

import android.content.Context;
import android.content.RestrictionEntry;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbHelper extends SQLiteOpenHelper {

    public static String NOME_DB = "db_app_restaurante";
    public static int VERSION = 1;

    public static String TABELA_CATEGORIA = "tb_categoria";
    public static String TABELA_PRODUTO = "tb_produtos";
    public static String TABELA_PEDIDO = "tb_pedido";
    public static String TABELA_ITEM_DO_PEDIDO = "tb_item_do_pedido";

    public DbHelper(Context context) {
        super(context, NOME_DB, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sqlCategoria = "CREATE TABLE IF NOT EXISTS " + TABELA_CATEGORIA +
                "(idCategoria INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nomeCategoria varchar NOT NULL)";
        try {
            db.execSQL(sqlCategoria);
            Log.i("INFO", "Tabela categoria criada com sucesso!" );
        }catch (Exception e){
            Log.i("INFO", "Erro na criação da tabela categoria ..: " + e.getMessage() );
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
