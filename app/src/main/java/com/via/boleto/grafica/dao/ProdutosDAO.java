package com.via.boleto.grafica.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.via.boleto.grafica.model.ProdutoTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lucas.oliveira on 24/04/2018.
 */

public class ProdutosDAO extends SQLiteOpenHelper{

    public static final String NOME_BANCO="grafica_app.sqlite";
    public static final int VERSAO_BANCO=1;

    public ProdutosDAO(Context context) {
        super(context, NOME_BANCO, null, VERSAO_BANCO);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("create table if not exists produto (" +
                                "produtoId integer primary key autoincrement, " +
                                "nome text," +
                                "valorVenda real," +
                                "tipoProduto text );");
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int versaoAntiga, int versaoNova) {

    }

    public void apagarConteudoTabela(String nomeTabela){

        SQLiteDatabase sqLiteDatabase = null;
        StringBuilder mensagem = new StringBuilder();

        try {
            sqLiteDatabase = getWritableDatabase();
            //sqLiteDatabase.delete("produto", null, null);
            sqLiteDatabase.execSQL("delete from "+ nomeTabela);
        }catch (SQLException e){
            mensagem.append("Erro ao apagar o conteudo de produto: "+"\n"+e.toString());
        }finally {
            if (sqLiteDatabase != null){
                sqLiteDatabase.close();
            }

        }

    }

    public String salvarProduto(ProdutoTO produtoTO){

        SQLiteDatabase sqLiteDatabase = null;
        StringBuilder mensagem = new StringBuilder();

        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("nome", produtoTO.getNome());
            contentValues.put("valorVenda", produtoTO.getValorVenda());
            contentValues.put("tipoProduto", produtoTO.getTipoProduto());

            sqLiteDatabase = getWritableDatabase();
            sqLiteDatabase.insert("produto", "", contentValues);

        }catch (SQLException e){
            mensagem.append("Erro ao salvar o produto: "+"\n"+e.toString());
        }finally {
            if (sqLiteDatabase != null){
                sqLiteDatabase.close();
            }

        }

        return mensagem.toString();
    }


    public List<ProdutoTO> getProdutos(){

        List<ProdutoTO> produtos = new ArrayList<>();
        StringBuilder mensagem = new StringBuilder();
        SQLiteDatabase sqLiteDatabase = null;
        ProdutoTO produtoTO  = null;

        try {

            sqLiteDatabase = getWritableDatabase();
            Cursor cursor = sqLiteDatabase.query("produto", null, null, null, null, null, null, null);


            if (cursor.moveToFirst()){
                do {
                    produtoTO = new ProdutoTO();

                    produtoTO.setProdutoId(cursor.getInt(cursor.getColumnIndex("produtoId")));
                    produtoTO.setNome(cursor.getString(cursor.getColumnIndex("nome")));
                    produtoTO.setValorVenda(cursor.getDouble(cursor.getColumnIndex("valorVenda")));
                    produtoTO.setTipoProduto(cursor.getString(cursor.getColumnIndex("tipoProduto")));

                    produtos.add(produtoTO);
                }while (cursor.moveToNext());
            }
        }catch (SQLException e){
            mensagem.append("Erro ao buscar os produtos: "+"\n"+e.toString());
        }finally {
            if (sqLiteDatabase != null){
                sqLiteDatabase.close();
            }
        }
        return produtos;
    }
}
