package com.via.boleto.grafica;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    EditText txtUsuario;
    EditText txtSenha;
    Button btnEntrar;

    String usuario="";
    String senha="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        inicializarObjetos();
        btnEntrar.setOnClickListener(this);

    }



    private void inicializarObjetos(){

        txtUsuario =(EditText) findViewById(R.id.txt_usuario);
        txtSenha =(EditText) findViewById(R.id.txt_senha);
        btnEntrar =(Button) findViewById(R.id.btn_entrar);
    }

    @Override
    public void onClick(View view) {

        usuario = txtSenha.getText().toString();
        senha = txtUsuario.getText().toString();

        if (GraficaUtils.notNullNotBlank(usuario) && GraficaUtils.notNullNotBlank(senha)){

            Intent it = new Intent(LoginActivity.this, MenuActivity.class);
            startActivity(it);

        }else{
            Toast.makeText(LoginActivity.this,  getString(R.string.login_erro)+"\n"+getString(R.string.erro_campos_brancos), Toast.LENGTH_LONG).show();
        }


    }
}
