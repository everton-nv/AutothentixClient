package com.ufrpe.autothentixclient.usuario.gui;


import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.ufrpe.autothentixclient.R;


public class LoginActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextView logo = (TextView) findViewById(R.id.txtLogo1);
        TextView subtitle = findViewById(R.id.txtSubtitle);
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/trench100free.ttf");
        logo.setTypeface(font);
        subtitle.setTypeface(font);
    }

    public void cadastrar(View view){
        changeActivity(CadastroActivity.class);
    }

    public void logar(View view){
        changeActivity(MainActivity.class);
    }

    public void changeActivity(Class screenClass){
        Intent intent = new Intent(this, screenClass);
        startActivity(intent);
        finish();
    }


}
