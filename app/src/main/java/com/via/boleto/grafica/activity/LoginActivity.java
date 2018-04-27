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

    private BaseLocalDAO baseLocalDAO;

    private CheckBox chkBoxLogin;

    private String usuario="";
    private String senha="";

    private StringBuilder mensagemValidacao = new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        inicializarObjetos();
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
    }

    @Override
    public void onBackPressed() {
        System.exit(0);
    }

    @Override
    public void onClick(View view) {

        usuario = txtUsuario.getText().toString();
        senha  = txtSenha.getText().toString();

        if (validarUsuarioSenha()){

            baseLocalDAO = new BaseLocalDAO(LoginActivity.this);

            AutenticarPostTO autenticar = new AutenticarPostTO();
            autenticar.setUsuario(usuario);
            autenticar.setSenha(senha);
            salvarUsuario();

            if (GraficaUtils.verificaConexao(LoginActivity.this)){

                sendNeworkRequest(autenticar);
            }else if (baseLocalDAO.usuarioExiste(usuario, senha)){

                Intent it = new Intent(LoginActivity.this, PrincipalActivity.class);

                txtUsuario.setText(new SharedPref().getLogin(LoginActivity.this, "login"));
                txtSenha.setText("");

                startActivity(it);
            }else{
                Toast.makeText(LoginActivity.this,  getString(R.string.login_erro)+"\n"+getString(R.string.erro_usuario_senha)+"\n"+getString(R.string.erro_conexao), Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(LoginActivity.this,  getString(R.string.login_erro)+"\n"+mensagemValidacao.toString(), Toast.LENGTH_LONG).show();
            mensagemValidacao.delete(0, mensagemValidacao.length());
        }
    }

    private boolean validarUsuarioSenha() {

        if (!GraficaUtils.notNullNotBlank(usuario)){
            mensagemValidacao.append(getString(R.string.erro_campos_brancos_login)+"\n");
        }

        if (!GraficaUtils.notNullNotBlank(senha)){
            mensagemValidacao.append(getString(R.string.erro_campos_brancos_senha));
        }

        return mensagemValidacao.toString().length() == 0;
    }

    private void salvarUsuario() {

        if (chkBoxLogin.isChecked() && GraficaUtils.notNullNotBlank(usuario)){
            new SharedPref().setLogin(LoginActivity.this, "login", usuario);
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

                    baseLocalDAO.salvarUsuario(new AutenticarPostTO(usuario, senha));

                    txtUsuario.setText(new SharedPref().getLogin(LoginActivity.this, "login"));
                    txtSenha.setText("");

                    GraficaUtils.salvarListaProduto(LoginActivity.this);

                    Intent it = new Intent(LoginActivity.this, PrincipalActivity.class);
                    startActivity(it);
                }
                else{
                    Toast.makeText(LoginActivity.this,  getString(R.string.login_erro)+"\n"+getString(R.string.erro_usuario_senha), Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<AutenticacaoTO> call, Throwable t) {
                Toast.makeText(LoginActivity.this,  getString(R.string.login_erro)+"\n"+getString(R.string.erro_conexao), Toast.LENGTH_LONG).show();
            }
        });

    }
}
