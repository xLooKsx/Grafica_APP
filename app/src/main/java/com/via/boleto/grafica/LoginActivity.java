package com.via.boleto.grafica;
/**
 * Changed by Matheus Silva on 09/04/2018.
 * Autentição via web service
 */
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.via.boleto.grafica.model.AutenticacaoTO;
import com.via.boleto.grafica.model.AutenticarPostTO;
import com.via.boleto.grafica.model.ProdutoTO;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
    public void onBackPressed() {
        System.exit(0);
    }

    @Override
    public void onClick(View view) {

        usuario = txtSenha.getText().toString();
        senha = txtUsuario.getText().toString();

        AutenticarPostTO autenticar = new AutenticarPostTO();
        autenticar.setUsuario(usuario);
        autenticar.setSenha(senha);

        //sendNeworkRequest(autenticar);

        Intent it = new Intent(LoginActivity.this, PrincipalActivity.class);
        startActivity(it);
    }

    public void sendNeworkRequest(AutenticarPostTO autenticar){
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://sggfit.azurewebsites.net/api/")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();

        iRetrofit service = retrofit.create(iRetrofit.class);
        Call<AutenticacaoTO> call = service.getAutenticacao(autenticar);

        call.enqueue(new Callback<AutenticacaoTO>() {
            @Override
            public void onResponse(Call<AutenticacaoTO> call, Response<AutenticacaoTO> response) {
                AutenticacaoTO autenticado = response.body();
                if (autenticado.isexiste()){
                    Intent it = new Intent(LoginActivity.this, PrincipalActivity.class);
                    startActivity(it);
                }
                else{
                    Toast.makeText(LoginActivity.this,  getString(R.string.login_erro)+"\n"+getString(R.string.erro_campos_brancos), Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<AutenticacaoTO> call, Throwable t) {
                Toast.makeText(LoginActivity.this,  getString(R.string.login_erro)+"\n"+getString(R.string.erro_campos_brancos), Toast.LENGTH_LONG).show();
            }
        });

    }
}
