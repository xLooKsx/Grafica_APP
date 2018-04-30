package com.via.boleto.grafica.activity;
/**
 * Created by lucas.oliveira on 10/03/2018.
 * Changed by Matheus Silva on 09/04/2018.
 * Autentição via web service
 */
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.via.boleto.grafica.dao.BaseLocalDAO;
import com.via.boleto.grafica.util.GraficaUtils;
import com.via.boleto.grafica.R;
import com.via.boleto.grafica.util.SharedPref;
import com.via.boleto.grafica.util.iRetrofit;
import com.via.boleto.grafica.model.AutenticacaoTO;
import com.via.boleto.grafica.model.AutenticarPostTO;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText txtUsuario;
    private EditText txtSenha;
    private Button btnEntrar;
    private ProgressBar progressBar;

    private BaseLocalDAO baseLocalDAO;

    private CheckBox chkBoxLogin;

    private StringBuilder mensagemValidacao = new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        inicializarObjetos();


       txtSenha.getText().toString();

        carregarUsuario();
        btnEntrar.setOnClickListener(this);

    }

    private void carregarUsuario() {
        txtUsuario.setText(new SharedPref().getLogin(LoginActivity.this, "login"));
    }


    private void inicializarObjetos(){
        txtUsuario =(EditText) findViewById(R.id.txt_usuario);
        txtSenha =(EditText) findViewById(R.id.txt_senha);
        btnEntrar =(Button) findViewById(R.id.btn_entrar);
        chkBoxLogin = (CheckBox) findViewById(R.id.chkBoxLembrarLogin);
        progressBar = (ProgressBar) findViewById(R.id.progressBarLogin);
    }

    private void mostrarBarProgress(){

        progressBar.setVisibility(View.VISIBLE);
        btnEntrar.setVisibility(View.GONE);
    }

    private void esconderBarProgress(){

        progressBar.setVisibility(View.GONE);
        btnEntrar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBackPressed() {
        System.exit(0);
    }

    @Override
    public void onClick(View view) {



        mostrarBarProgress();

        if (validarUsuarioSenha()){

            baseLocalDAO = new BaseLocalDAO(LoginActivity.this);

            AutenticarPostTO autenticar = new AutenticarPostTO();
            autenticar.setUsuario(txtUsuario.getText().toString());
            autenticar.setSenha(txtSenha.getText().toString());
            salvarUsuario();

            if (GraficaUtils.verificaConexao(LoginActivity.this)){

                sendNeworkRequest(autenticar);
            }else if (baseLocalDAO.usuarioExiste(txtUsuario.getText().toString(), txtUsuario.getText().toString())){

                Intent it = new Intent(LoginActivity.this, PrincipalActivity.class);

                txtUsuario.setText(new SharedPref().getLogin(LoginActivity.this, "login"));
                txtSenha.setText("");

                esconderBarProgress();

                startActivity(it);
            }else{
                Toast.makeText(LoginActivity.this,  getString(R.string.login_erro)+"\n"+getString(R.string.erro_usuario_senha)+"\n"+getString(R.string.erro_conexao), Toast.LENGTH_LONG).show();
                esconderBarProgress();
            }
        }else{
            Toast.makeText(LoginActivity.this,  getString(R.string.login_erro)+"\n"+mensagemValidacao.toString(), Toast.LENGTH_LONG).show();
            mensagemValidacao.delete(0, mensagemValidacao.length());
            esconderBarProgress();
        }
    }

    private boolean validarUsuarioSenha() {

        if (!GraficaUtils.notNullNotBlank(txtUsuario.getText().toString())){
            mensagemValidacao.append(getString(R.string.erro_campos_brancos_login)+"\n");
        }

        if (!GraficaUtils.notNullNotBlank(txtSenha.getText().toString())){
            mensagemValidacao.append(getString(R.string.erro_campos_brancos_senha));
        }

        return mensagemValidacao.toString().length() == 0;
    }

    private void salvarUsuario() {

        if (chkBoxLogin.isChecked() && GraficaUtils.notNullNotBlank(txtUsuario.getText().toString())){
            new SharedPref().setLogin(LoginActivity.this, "login", txtUsuario.getText().toString());
            Toast.makeText(LoginActivity.this,  getString(R.string.login_salvo), Toast.LENGTH_LONG).show();
        }
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

                    baseLocalDAO.salvarUsuario(new AutenticarPostTO(txtUsuario.getText().toString(), txtSenha.getText().toString()));

                    txtUsuario.setText(new SharedPref().getLogin(LoginActivity.this, "login"));
                    txtSenha.setText("");

                    GraficaUtils.salvarListaProduto(LoginActivity.this);


                    Intent it = new Intent(LoginActivity.this, PrincipalActivity.class);
                    startActivity(it);
                    esconderBarProgress();
                }
                else{
                    Toast.makeText(LoginActivity.this,  getString(R.string.login_erro)+"\n"+getString(R.string.erro_usuario_senha), Toast.LENGTH_LONG).show();
                    esconderBarProgress();
                }
            }

            @Override
            public void onFailure(Call<AutenticacaoTO> call, Throwable t) {
                Toast.makeText(LoginActivity.this,  getString(R.string.login_erro)+"\n"+getString(R.string.erro_conexao), Toast.LENGTH_LONG).show();
                esconderBarProgress();
            }
        });

    }
}
