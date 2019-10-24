package com.apps.heber.restaurante.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbHelper extends SQLiteOpenHelper {

    public static String NOME_DB = "db_app_restaurante";
    public static int VERSION = 8;

    public static String TABELA_CATEGORIA = "tb_categoria";
    public static String TABELA_PRODUTO = "tb_produtos";
    public static String TABELA_PEDIDO = "tb_pedido";
    public static String TABELA_ITEM_DO_PEDIDO = "tb_item_do_pedido";

    public DbHelper(Context context) {
        super(context, NOME_DB, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //TABELA CATEGORIA
        String sqlCategoria = "CREATE TABLE IF NOT EXISTS " + TABELA_CATEGORIA +
                "(idCategoria INTEGER  PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "nomeCategoria varchar NOT NULL)";
        try {
            db.execSQL(sqlCategoria);
            Log.i("INFO", "Tabela categoria criada com sucesso!" );
        }catch (Exception e){
            Log.i("INFO", "Erro na criação da tabela categoria ..: " + e.getMessage() );
        }

        //TABELA PRODUTO
        String sqlProduto = "CREATE TABLE IF NOT EXISTS " + TABELA_PRODUTO +
                "(idProduto INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "nomeProduto varchar NOT NULL," +
                "descricao varchar NOT NULL," +
                "preco double NOT NULL," +
                "idCategoria INTEGER," +
                "FOREIGN KEY (idCategoria) REFERENCES " + TABELA_CATEGORIA + " (idCategoria) " +
                ")";

        try {
            db.execSQL(sqlProduto);
            Log.i("INFO", "Tabela produto criada com sucesso!" );
        }catch (Exception e){
            Log.i("INFO", "Erro na criação da tabela produto ..: " + e.getMessage() );
        }

        //TABELA iTEM DO PEDIDO
        String sqlItemPedido = "CREATE TABLE IF NOT EXISTS " + TABELA_ITEM_DO_PEDIDO +
                "(idItemPedido INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "nomeProduto varchar NOT NULL," +
                "ingredientes varchar NOT NULL," +
                "quantidade INTEGER NOT NULL," +
                "valorUnitario double NOT NULL," +
                "valorTotal double NOT NULL," +
                "observacao varchar," +
                "fkIdProduto INTEGER," +
                "FOREIGN KEY (fkidProduto) REFERENCES " + TABELA_PRODUTO + " (idProduto) " +
                ")";

        try {
            db.execSQL(sqlItemPedido);
            Log.i("INFO", "Tabela produto criada com sucesso!" );
        }catch (Exception e){
            Log.i("INFO", "Erro na criação da tabela produto ..: " + e.getMessage() );
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        try {
            String sqlCategoria = "DROP TABLE IF EXISTS " + TABELA_CATEGORIA + ";";
            String sqlProduto = "DROP TABLE IF EXISTS " + TABELA_PRODUTO + ";";
            String sqlItemPedido = "DROP TABLE IF EXISTS " + TABELA_ITEM_DO_PEDIDO + ";";

            db.execSQL(sqlCategoria);
            db.execSQL(sqlProduto);
            db.execSQL(sqlItemPedido);
            onCreate(db);
        }catch (Exception e){
            Log.i("INFO", "Erro no upgrade da tabela");
        }

    }

}
