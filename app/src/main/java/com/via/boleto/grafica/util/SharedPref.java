package com.via.boleto.grafica.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by lucas.oliveira on 24/04/2018.
 */

public class SharedPref {

    public static final String PREF_ID="graficaApp";

    public static void setLogin(Context context, String chave, String valor){

        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_ID, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(chave, valor);
        editor.commit();
    }

    public static String getLogin(Context context, String chave){

        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_ID, Context.MODE_PRIVATE);
        return sharedPreferences.getString(chave, "");
    }
}
