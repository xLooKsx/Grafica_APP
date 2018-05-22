package com.via.boleto.grafica.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.via.boleto.grafica.fragment.CartaoCreditoFragment;
import com.via.boleto.grafica.fragment.CartaoDebitoFragment;
import com.via.boleto.grafica.fragment.ConfigEmailFragment;
import com.via.boleto.grafica.fragment.DinheiroFragment;
import com.via.boleto.grafica.R;
import com.via.boleto.grafica.model.FaturamentoTO;
import com.via.boleto.grafica.model.ProdutoTO;
import com.via.boleto.grafica.util.GraficaUtils;
import com.via.boleto.grafica.util.RVAdapter;
import com.via.boleto.grafica.util.SharedPref;
import com.via.boleto.grafica.util.iRetrofit;

import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by lucas.oliveira on 16/04/2018.
 */
public class PrincipalActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FragmentTransaction fragmentTransaction;
    private NavigationView navigationView;
    private int idMenu = R.id.nav_dinheiro;

    private static String valorDinheiro="vazio";
    private static String valorCredito="vazio";
    private static String valorDebito="vazio";

    public PrincipalActivity() {
        sendRequestDinheito();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);




        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_principal, new DinheiroFragment());
        fragmentTransaction.commit();

        navigationView.setCheckedItem(R.id.nav_dinheiro);

        GraficaUtils.mostrarStatusDaddos(PrincipalActivity.this);
    }


    public void setActionBarTitle(String title){

        getSupportActionBar().setTitle(title);
        sendRequestDinheito();
        sendRequestCredito();
        sendRequestDebito();

        new SharedPref().setLogin(PrincipalActivity.this, "vlrDinheiro", valorDinheiro);
        new SharedPref().setLogin(PrincipalActivity.this, "vlrCredito", valorCredito);
        new SharedPref().setLogin(PrincipalActivity.this, "vlrDebito", valorDebito);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Random r = new Random();
        TextView txtViewNovoValor;

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_atualizar) {

            GraficaUtils.mostrarStatusDaddos(PrincipalActivity.this);




            switch (idMenu){

                case R.id.nav_dinheiro:

                    txtViewNovoValor = (TextView)findViewById(R.id.txt_vlrdinheiro);
                    txtViewNovoValor.setText("R$ "+valorDinheiro);
                    break;

                case R.id.nav_cartao_debito:
                    txtViewNovoValor = (TextView)findViewById(R.id.txt_vlrCartaoDebito);
                    txtViewNovoValor.setText("R$ "+valorDebito);
                    break;

                case R.id.nav_cartao_credito:
                    txtViewNovoValor = (TextView)findViewById(R.id.txt_vlrCartaoCredito);
                    txtViewNovoValor.setText("R$ "+valorCredito);
                    break;
            }

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();



        switch (id){

            case R.id.nav_dinheiro:
                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame_principal, new DinheiroFragment());
                GraficaUtils.mostrarStatusDaddos(PrincipalActivity.this);
                fragmentTransaction.commit();
                break;

            case R.id.nav_cartao_debito:
                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame_principal, new CartaoDebitoFragment());
                GraficaUtils.mostrarStatusDaddos(PrincipalActivity.this);
                fragmentTransaction.commit();
                break;

            case R.id.nav_cartao_credito:
                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame_principal, new CartaoCreditoFragment());
                GraficaUtils.mostrarStatusDaddos(PrincipalActivity.this);
                fragmentTransaction.commit();
                break;

            case R.id.nav_logout:
                finish();
                break;

            case R.id.nav_sair:
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                finish();

                startActivity(intent);
                break;

            case R.id.nav_lista_produtos:
                Intent intentListProduto = new Intent(PrincipalActivity.this, ListaProdutosActivity.class);
                startActivity(intentListProduto);
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        idMenu = id;
        return true;
    }

    public void sendRequestDinheito(){

        iRetrofit retrofit = iRetrofit.retrofit.create(iRetrofit.class);
        final Call<Object> call = retrofit.getDinheiro();

        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                int code = response.code();
                if (code == 200){

                    valorDinheiro = response.body().toString().substring(7, 12);
                    new SharedPref().setLogin(PrincipalActivity.this, "vlrDinheiro", valorDinheiro);
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {


            }
        });
    }

    public void sendRequestCredito(){

        iRetrofit retrofit = iRetrofit.retrofit.create(iRetrofit.class);
        final Call<Object> call = retrofit.getCredito();

        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                int code = response.code();
                if (code == 200){

                    valorCredito = response.body().toString().substring(7, 12);
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {


            }
        });
    }

    public void sendRequestDebito(){

        iRetrofit retrofit = iRetrofit.retrofit.create(iRetrofit.class);
        final Call<Object> call = retrofit.getDebito();

        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                int code = response.code();
                if (code == 200){

                    valorDebito = response.body().toString().substring(7, 12);
                    new SharedPref().setLogin(PrincipalActivity.this, "vlrDinheiro", valorDinheiro);
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {


            }
        });
    }
}
