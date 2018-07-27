package com.ufrpe.autothentixclient.usuario.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.ufrpe.autothentixclient.R;
import com.ufrpe.autothentixclient.infra.SharedPreferencesServices;

import static com.ufrpe.autothentixclient.infra.GuiUtil.myToastShort;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initActivity(CreateDocServicoActivity.class);
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //startPatientFragment();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

//    private void startPatientFragment(){
//        DocumentoFragment mFragment = (DocumentoFragment) getSupportFragmentManager().findFragmentByTag("lista de consultas");
//        if(mFragment == null) {
//            mFragment = new DocumentoFragment();
//            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//            ft.replace(R.id.rl_fragment_container, mFragment, "Documents List");
//            ft.commit();
//        }
//    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()){
            case R.id.action_settings:
                myToastShort(this, "Settings Click ok");
                break;
            case R.id.action_logout:
                SharedPreferencesServices sharedPreferencesServices = new SharedPreferencesServices(this);
                sharedPreferencesServices.clearPreferences();
                changeActivity(LoginActivity.class);
                myToastShort(this, getString(R.string.msg_success_logoff));
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        switch (item.getItemId()){
            case R.id.nav_camera:
                myToastShort(this, "Camera Click ok");
                break;
            case R.id.nav_gallery:
                myToastShort(this, "Galeria Click ok");
                break;
            case R.id.nav_slideshow:
                myToastShort(this, "Slide Show Click ok");
                break;
            case R.id.nav_manage:
                myToastShort(this, "Gerenciamento Click ok");
                break;
            case R.id.nav_share:
                myToastShort(this, "Compartilhar Click ok");
                break;
            case R.id.nav_send:
                myToastShort(this, "Enviar Click ok");
                break;
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void changeActivity(Class screenClass){
        initActivity(screenClass);
        finish();
    }

    public void initActivity(Class screenClass){
        Intent intent = new Intent(this, screenClass);
        startActivity(intent);
    }
}
