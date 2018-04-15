package com.via.boleto.grafica;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.TextView;

import com.via.boleto.grafica.model.ProdutoTO;

import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PrincipalActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FragmentTransaction fragmentTransaction;
    private NavigationView navigationView;
    private int idMenu = R.id.nav_dinheiro;

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

        iRetrofit retrofit = iRetrofit.retrofit.create(iRetrofit.class);
        final Call<List<ProdutoTO>> call = retrofit.getProduto();

        call.enqueue(new Callback<List<ProdutoTO>>() {
            @Override
            public void onResponse(Call<List<ProdutoTO>> call, Response<List<ProdutoTO>> response) {
                int code = response.code();
                if (code == 200){
                    List<ProdutoTO> listaProdutos = response.body();
                    for (ProdutoTO produto : listaProdutos){
                        Log.d("Nome do produto:", produto.getNome());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<ProdutoTO>> call, Throwable t) {

            }
        });

    }

    public void setActionBarTitle(String title){

        getSupportActionBar().setTitle(title);
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
        int novoValor;

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_atualizar) {

            switch (idMenu){

                case R.id.nav_dinheiro:
                    txtViewNovoValor = (TextView)findViewById(R.id.txt_vlrdinheiro);
                    novoValor = r.nextInt(800 - 10) + 65;
                    txtViewNovoValor.setText("R$ "+String.valueOf(novoValor)+",00");
                    break;

                case R.id.nav_cartao_debito:
                    txtViewNovoValor = (TextView)findViewById(R.id.txt_vlrCartaoDebito);
                    novoValor = r.nextInt(400 - 10) + 65;
                    txtViewNovoValor.setText("R$ "+String.valueOf(novoValor)+",00");
                    break;

                case R.id.nav_cartao_credito:
                    txtViewNovoValor = (TextView)findViewById(R.id.txt_vlrCartaoCredito);
                    novoValor = r.nextInt(328 - 10) + 65;
                    txtViewNovoValor.setText("R$ "+String.valueOf(novoValor)+",00");
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
                fragmentTransaction.commit();
                break;

            case R.id.nav_cartao_debito:
                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame_principal, new CartaoDebitoFragment());
                fragmentTransaction.commit();
                break;

            case R.id.nav_cartao_credito:
                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame_principal, new CartaoCreditoFragment());
                fragmentTransaction.commit();
                break;

            case R.id.nav_config_email:
                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame_principal, new ConfigEmailFragment());
                fragmentTransaction.commit();
                break;

            case R.id.nav_sair:
                Intent intentLogin = new Intent(PrincipalActivity.this, LoginActivity.class);
                finish();
                startActivity(intentLogin);
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
}
