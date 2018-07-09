package com.ufrpe.autothentixclient.usuario.gui;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


import com.ufrpe.autothentixclient.R;


public class LoginActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void cadastrar(View view){
        Intent it = new Intent(LoginActivity.this, CadastroActivity.class);
        startActivity(it);
        finish();
    }
 }
