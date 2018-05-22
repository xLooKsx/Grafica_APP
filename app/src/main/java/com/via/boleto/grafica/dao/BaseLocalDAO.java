package com.via.boleto.grafica.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.via.boleto.grafica.model.AutenticarPostTO;
import com.via.boleto.grafica.model.ProdutoTO;

import java.util.ArrayList;
import java.util.List;

public class BaseLocalDAO extends SQLiteOpenHelper {

    public static final String NOME_BANCO="grafica_app.sqlite";
    public static final int VERSAO_BANCO=1;

    public BaseLocalDAO(Context context) {
        super(context, NOME_BANCO, null, VERSAO_BANCO);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("create table if not exists usuario (" +
                "login text primary key, " +
                "senha text);");


        sqLiteDatabase.execSQL("create table if not exists produto (" +
                "produtoId integer primary key autoincrement, " +
                "nome text," +
                "valorVenda real," +
                "tipoProduto text );");

        sqLiteDatabase.execSQL("create table if not exists valorDinheiro (" +
                "valorDinheiro integer primary key autoincrement, " +
                "valor text);");

        sqLiteDatabase.execSQL("create table if not exists valorCredito (" +
                "valorCredito integer primary key autoincrement, " +
                "valor text);");

        sqLiteDatabase.execSQL("create table if not exists valorDebito (" +
                "valorDebito integer primary key autoincrement, " +
                "valor text);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void apagarConteudoTabela(String nomeTabela){

        SQLiteDatabase sqLiteDatabase = null;
        StringBuilder mensagem = new StringBuilder();

        try {
            sqLiteDatabase = getWritableDatabase();
            //sqLiteDatabase.delete("produto", null, null);
            sqLiteDatabase.execSQL("delete from "+ nomeTabela);

        }catch (SQLException e){
            Log.e("apagar base dados", "apagar base dados "+e.toString());
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
            Log.e("Produto Salvo", "Produto Salvo: "+e.toString());
        }finally {
            if (sqLiteDatabase != null){
                sqLiteDatabase.close();
            }

        }

        return mensagem.toString();
    }

    public List<ProdutoTO> getProdutos(){

        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        List<ProdutoTO> produtos = new ArrayList<>();

        try{

            Cursor cursor = sqLiteDatabase.query("produto", null, null, null, null, null, null, null);

            if (cursor.moveToFirst()){

                do{
                    ProdutoTO produtoTO = new ProdutoTO();

                    produtoTO.setProdutoId(cursor.getInt(cursor.getColumnIndex("produtoId")));
                    produtoTO.setNome(cursor.getString(cursor.getColumnIndex("nome")));
                    produtoTO.setValorVenda(cursor.getDouble(cursor.getColumnIndex("valorVenda")));
                    produtoTO.setTipoProduto(cursor.getString(cursor.getColumnIndex("tipoProduto")));

                    produtos.add(produtoTO);
                }while (cursor.moveToNext());
            }

        }catch (SQLException e){
            Log.e("Lista Produto", "Lista Produto: "+e.toString());
        }

        return produtos;
    }


    public String salvarVlrDinheiro(String valor){

        SQLiteDatabase sqLiteDatabase = null;
        StringBuilder mensagem = new StringBuilder();

        try {


            ContentValues contentValues = new ContentValues();
            contentValues.put("valor", valor);

            sqLiteDatabase = getWritableDatabase();
            sqLiteDatabase.insert("valorDinheiro", "", contentValues);



        }catch (SQLException e){
            mensagem.append("Erro ao salvar o valorDinheiro: "+"\n"+e.toString());
        }finally {
            if (sqLiteDatabase != null){
                sqLiteDatabase.close();
            }

        }

        return mensagem.toString();
    }

    public String getValorDinheiro(String tabela){

        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String valor = "vazio";

        try{

            Cursor cursor = sqLiteDatabase.query("valorDinheiro", null, null, null, null, null, null, null);

            if (cursor.moveToFirst()){

                do{

                    valor = cursor.getString(cursor.getColumnIndex("valor"));

                }while (cursor.moveToNext());
            }

        }catch (SQLException e){
        }

        return valor;
    }

    public String salvarVlrCredito(String valor){

        SQLiteDatabase sqLiteDatabase = null;
        StringBuilder mensagem = new StringBuilder();

        try {


            ContentValues contentValues = new ContentValues();
            contentValues.put("valor", valor);

            sqLiteDatabase = getWritableDatabase();
            sqLiteDatabase.insert("valorCredito", "", contentValues);



        }catch (SQLException e){
            mensagem.append("Erro ao salvar o valorCredito: "+"\n"+e.toString());
        }finally {
            if (sqLiteDatabase != null){
                sqLiteDatabase.close();
            }

        }

        return mensagem.toString();
    }

    public String getValorCredito(String tabela){

        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String valor = "vazio";

        try{

            Cursor cursor = sqLiteDatabase.query("valorCredito", null, null, null, null, null, null, null);

            if (cursor.moveToFirst()){

                do{

                    valor = cursor.getString(cursor.getColumnIndex("valor"));

                }while (cursor.moveToNext());
            }

        }catch (SQLException e){
        }

        return valor;
    }

    public String salvarVlrDebito(String valor){

        SQLiteDatabase sqLiteDatabase = null;
        StringBuilder mensagem = new StringBuilder();

        try {


            ContentValues contentValues = new ContentValues();
            contentValues.put("valor", valor);

            sqLiteDatabase = getWritableDatabase();
            sqLiteDatabase.insert("valorDebito", "", contentValues);



        }catch (SQLException e){
            mensagem.append("Erro ao salvar o valorDebito: "+"\n"+e.toString());
        }finally {
            if (sqLiteDatabase != null){
                sqLiteDatabase.close();
            }

        }

        return mensagem.toString();
    }

    public String getValorDebito(String tabela){

        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String valor = "vazio";

        try{

            Cursor cursor = sqLiteDatabase.query("valorDebito", null, null, null, null, null, null, null);

            if (cursor.moveToFirst()){

                do{

                    valor = cursor.getString(cursor.getColumnIndex("valor"));

                }while (cursor.moveToNext());
            }

        }catch (SQLException e){
        }

        return valor;
    }

    public String salvarUsuario(AutenticarPostTO usuario){

        SQLiteDatabase sqLiteDatabase = null;
        StringBuilder mensagem = new StringBuilder();

        try {
            if (!usuarioExiste(usuario)){

                ContentValues contentValues = new ContentValues();
                contentValues.put("login", usuario.getUsuario());
                contentValues.put("senha", usuario.getSenha());

                sqLiteDatabase = getWritableDatabase();
                sqLiteDatabase.insert("usuario", "", contentValues);
            }


        }catch (SQLException e){
            mensagem.append("Erro ao salvar o usuario: "+"\n"+e.toString());
        }finally {
            if (sqLiteDatabase != null){
                sqLiteDatabase.close();
            }

        }

        return mensagem.toString();
    }

    public boolean usuarioExiste(String usuario, String senha) {

        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT *  FROM usuario WHERE login = '"+usuario+"' AND senha = '"+senha+"'");

        Cursor cursor = sqLiteDatabase.rawQuery(sql.toString(), null);
        cursor.moveToFirst();

        return (cursor.getCount() > 0);
    }

    private boolean usuarioExiste(AutenticarPostTO usuario) {

        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM usuario WHERE login = '"+usuario.getUsuario()+"'");

        Cursor cursor = sqLiteDatabase.rawQuery(sql.toString(), null);
        cursor.moveToFirst();

        return (cursor.getCount() > 0);
    }
}
