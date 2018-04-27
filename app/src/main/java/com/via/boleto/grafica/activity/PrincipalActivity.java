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

import com.via.boleto.grafica.fragment.CartaoCreditoFragment;
import com.via.boleto.grafica.fragment.CartaoDebitoFragment;
import com.via.boleto.grafica.fragment.ConfigEmailFragment;
import com.via.boleto.grafica.fragment.DinheiroFragment;
import com.via.boleto.grafica.R;
import com.via.boleto.grafica.util.GraficaUtils;

import java.util.Random;

/**
 * Created by lucas.oliveira on 16/04/2018.
 */
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

        GraficaUtils.mostrarStatusDaddos(PrincipalActivity.this);


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

            GraficaUtils.mostrarStatusDaddos(PrincipalActivity.this);

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

        GraficaUtils.mostrarStatusDaddos(PrincipalActivity.this);

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
}
