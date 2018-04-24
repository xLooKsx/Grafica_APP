package com.via.boleto.grafica.util;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by LucasOliveira on 10/03/2018.
 */

public class GraficaUtils {

    public static boolean notNullNotBlank(String texto){

        return (texto.trim().length() !=0 && texto != null);
    }

    public static boolean verificaConexao(Context context) {

        ConnectivityManager conectivtyManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (conectivtyManager.getActiveNetworkInfo() != null
                && conectivtyManager.getActiveNetworkInfo().isAvailable()
                && conectivtyManager.getActiveNetworkInfo().isConnected()) {
            return true;
        }
        return false;
    }
}
