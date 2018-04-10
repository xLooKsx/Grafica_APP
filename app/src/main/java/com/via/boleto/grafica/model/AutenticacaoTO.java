package com.via.boleto.grafica.model;
/**
 * Created by Matheus Silva on 09/04/2018.
 */
public class AutenticacaoTO {

    private boolean existe;
    private String id;

    public boolean isexiste() {
        return existe;
    }

    public void setexiste(boolean existe) {
        this.existe = existe;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
