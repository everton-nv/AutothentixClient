package com.ufrpe.autothentixclient.usuario.gui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.ufrpe.autothentixclient.R;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    private Button cadastrar;
    private AlertDialog alerta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        cadastrar = findViewById(R.id.btnCadastrar);
        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    ArrayList<String> itens = new ArrayList<String>();
                    itens.add("Pessoa Física");
                    itens.add("Pessoa Jurídica");

                    //adapter utilizando um layout customizado (TextView)
                    final ArrayAdapter adapter = new ArrayAdapter(LoginActivity.this, R.layout.alert_criar_conta_pt1, itens);

                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                    builder.setTitle("Escolha uma das opções:");
                    //define o diálogo como uma lista, passa o adapter.

                    builder.setSingleChoiceItems(adapter, 0, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {

                            String pessoa = adapter.getItem(arg1).toString();
                            String p = pessoa;
                            if (p == "Pessoa Física"){

                                Intent abrirPerfil = new Intent(LoginActivity.this, CadastroActivity.class);
                                startActivity(abrirPerfil);

                            } else{

                                Intent abrirPerfil = new Intent(LoginActivity.this, CadastroActivity.class);
                                startActivity(abrirPerfil);
                            }

                            alerta.dismiss();
                        }
                    });

                    alerta = builder.create();
                    alerta.show();
                }

            });

    }
}
