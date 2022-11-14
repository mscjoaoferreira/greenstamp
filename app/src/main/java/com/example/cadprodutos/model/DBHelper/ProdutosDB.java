package com.example.cadprodutos.model.DBHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.cadprodutos.model.Produtos;

import java.util.ArrayList;

public class ProdutosDB extends SQLiteOpenHelper {

    private static final String DATABASE="dbprodutos";
    private static final int VERSION =1;

    public ProdutosDB(Context context){
        super(context,DATABASE,null,VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String produto = "CREATE TABLE produtos (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, nomeproduto TEXT NOT NULL, descricao TEXT NOT NULL, quantidade INTEGER);";
        db.execSQL(produto);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String produto = "DROP TABLE IF EXISTS produtos";
        db.execSQL(produto);

    }

    public void salvarProdutos(Produtos produto){
        ContentValues values = new ContentValues();

        values.put("nomeproduto", produto.getNomeProduto());
        values.put("descricao",produto.getDescricaoProduto());
        values.put("quantidade",produto.getQtdProduto());

        getWritableDatabase().insert("produtos",null,values);

    }

    //listar
    public ArrayList<Produtos> getLista(){
        String [] columns = {"id","nomeproduto","descricao","quantidade"};
        Cursor cursor = getWritableDatabase().query(
                "produtos",
                columns,
                null,
                null,
                null,
                null,
                null,
                null);

        ArrayList<Produtos> produtos = new ArrayList<Produtos>();

        while (cursor.moveToNext()){
                Produtos produto = new Produtos();
                produto.setId(cursor.getLong(0));
                produto.setNomeProduto(cursor.getString(1));
                produto.setDescricaoProduto(cursor.getString(2));
                produto.setQtdProduto(cursor.getInt(3));

                produtos.add(produto);
        }

        return produtos;
    }

    // alterar produtos

    public void alterarProduto(Produtos produto){
        ContentValues values = new ContentValues();

        values.put("nomeproduto", produto.getNomeProduto());
        values.put("descricao",produto.getDescricaoProduto());
        values.put("quantidade",produto.getQtdProduto());

        String [] args = {produto.getId().toString()};

        getWritableDatabase().update("produtos",values, "id=?",args);
    }

    public void deletarProduto (Produtos produto){
        String [] args = {produto.getId().toString()};

        getWritableDatabase().delete("produtos", "id=?",args);
    }


}
