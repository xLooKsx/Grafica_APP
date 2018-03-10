package com.via.boleto.grafica;

/**
 * Created by LucasOliveira on 10/03/2018.
 */

public class GraficaUtils {

    public static boolean notNullNotBlank(String texto){

        return (texto.trim().length() !=0 && texto != null);
    }
}
