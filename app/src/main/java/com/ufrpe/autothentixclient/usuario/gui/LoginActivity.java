package com.ufrpe.autothentixclient.usuario.gui;


import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.ufrpe.autothentixclient.R;
import com.ufrpe.autothentixclient.infra.GuiUtil;
import com.ufrpe.autothentixclient.infra.ValidacaoService;
import com.ufrpe.autothentixclient.usuario.service.UsuarioService;

import java.io.IOException;


public class LoginActivity extends AppCompatActivity {
    private EditText edtEmail, edtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edtEmail = findViewById(R.id.edtLogin);
        edtPassword = findViewById(R.id.edtSenha);
        TextView logo = (TextView) findViewById(R.id.txtLogo1);
        TextView subtitle = findViewById(R.id.txtSubtitle);
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/trench100free.ttf");
        logo.setTypeface(font);
        subtitle.setTypeface(font);
    }

    public void cadastrar(View view){
        changeActivity(CadastroActivity.class);
    }

    public void logar(View view) throws IOException {
        String email = edtEmail.getText().toString();
        String password = edtPassword.getText().toString();

        ValidacaoService validacaoCadastro = new ValidacaoService();
        boolean valid = true;

        if (validacaoCadastro.isCampoVazio(password)) {
            edtPassword.requestFocus();
            edtPassword.setError(getString(R.string.msg_login_senha_incorreto));
            valid = false;
        }
        if (validacaoCadastro.isCampoVazio(email)) {
            edtEmail.requestFocus();
            edtEmail.setError(getString(R.string.msg_login_senha_incorreto));
            valid = false;
        }
        if(valid){
            UsuarioService usuarioService = new UsuarioService();
            onResponse(usuarioService.logar(email, password));
        }

    }

    public void changeActivity(Class screenClass){
        Intent intent = new Intent(this, screenClass);
        startActivity(intent);
        finish();
    }

    public void onResponse(String token){
        if(token != null){
            changeActivity(MainActivity.class);
        }
        else{
            GuiUtil.myToast(getApplicationContext(), "Usuario não encontrado.");
        }
    }


}
