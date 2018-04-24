package com.via.boleto.grafica.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.via.boleto.grafica.model.AutenticarPostTO;

/**
 * Created by lucas.oliveira on 24/04/2018.
 */

public class UsuarioDAO extends SQLiteOpenHelper {

    public static final String NOME_BANCO="grafica_app.sqlite";
    public static final int VERSAO_BANCO=1;

    public UsuarioDAO(Context context) {
        super(context, NOME_BANCO, null, VERSAO_BANCO);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("create table if not exists usuario (" +
                "usuario text primary key, " +
                "senha text);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int versaoAntiga, int versaoNova) {

    }

    public String salvarUsuario(AutenticarPostTO usuario){

        SQLiteDatabase sqLiteDatabase = null;
        StringBuilder mensagem = new StringBuilder();

        try {
            if (!usuarioExiste(usuario)){

                ContentValues contentValues = new ContentValues();
                contentValues.put("usuario", usuario.getUsuario());
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
        sql.append("SELECT usuario,senha  FROM usuario WHERE usuario = '"+usuario+"' AND senha = '"+senha+"'");

        Cursor cursor = sqLiteDatabase.rawQuery(sql.toString(), null);
        cursor.moveToFirst();

        //Cursor cursor = getReadableDatabase().rawQuery(sql.toString(), null);

        return (cursor.getCount() > 0);
    }

    private boolean usuarioExiste(AutenticarPostTO usuario) {

        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM usuario WHERE usuario = '"+usuario.getUsuario()+"'");

        Cursor cursor = sqLiteDatabase.rawQuery(sql.toString(), null);
        cursor.moveToFirst();

        return (cursor.getCount() > 0);
    }
}
