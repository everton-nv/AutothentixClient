package com.ufrpe.autothentixclient.usuario.gui;


import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.ufrpe.autothentixclient.R;
import com.ufrpe.autothentixclient.infra.GuiUtil;
import com.ufrpe.autothentixclient.infra.SharedPreferencesServices;
import com.ufrpe.autothentixclient.infra.ValidacaoService;
import com.ufrpe.autothentixclient.usuario.dominio.Usuario;
import com.ufrpe.autothentixclient.usuario.service.ConexaoServidor;
import com.ufrpe.autothentixclient.usuario.service.UsuarioService;

import org.json.JSONException;

import java.io.IOException;

import static com.ufrpe.autothentixclient.infra.SharedPreferencesConstante.DEFAULT_LOGIN_PREFERENCES;
import static com.ufrpe.autothentixclient.infra.SharedPreferencesConstante.DEFAULT_PASSWORD_PREFERENCES;


public class LoginActivity extends AppCompatActivity implements AsyncResposta {
    private EditText edtEmail, edtPassword;
    ConexaoServidor conexaoServidor = new ConexaoServidor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        conexaoServidor.delegate = this;
        setContentView(R.layout.activity_login);

        try {
            autoLogin();
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("loginStackTrace", e.getMessage());
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("loginJSON", e.getMessage());
        }
        setCustomFontLogo();
    }

    private  void setCustomFontLogo(){
        TextView logo = findViewById(R.id.txtLogo1);
        TextView subtitle = findViewById(R.id.txtSubtitle);
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/trench100free.ttf");
        logo.setTypeface(font);
        subtitle.setTypeface(font);
    }

    private void findScreenInputs(){
        edtEmail = findViewById(R.id.edtLogin);
        edtPassword = findViewById(R.id.edtSenha);
    }

    private void showLoginLayout() {
        LinearLayout linearLayout = findViewById(R.id.layoutProgressBar);
        linearLayout.setVisibility(View.GONE);
        ScrollView scrollView = findViewById(R.id.scrollViewLogin);
        scrollView.setVisibility(View.VISIBLE);
    }

    public void cadastrar(View view){
        changeActivity(CadastroActivity.class);
    }

    private void logar() throws IOException, JSONException {
        findScreenInputs();
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
            Usuario usuario = new Usuario(email, password);
            UsuarioService usuarioService = new UsuarioService();
            usuarioService.logar(usuario, conexaoServidor);
        }
    }

    public void logar(View view) throws IOException, JSONException {
        logar();
    }

    private void autoLogin() throws IOException, JSONException {
        SharedPreferencesServices sharedPreferencesServices = new SharedPreferencesServices(this);
        String login = sharedPreferencesServices.getLoginPreferences();
        String password = sharedPreferencesServices.getPasswordPreferences();

        Boolean isAutologged;
        Bundle bundle = getIntent().getExtras();

        try{
            isAutologged = bundle.getBoolean("autoLogin");
        } catch(Exception e){
            isAutologged = false;
        }

        if(login.equals(DEFAULT_LOGIN_PREFERENCES) && password.equals(DEFAULT_PASSWORD_PREFERENCES)){
            showLoginLayout();
        } else if(!isAutologged) {
            findScreenInputs();
            edtEmail.setText(login);
            edtPassword.setText(password);
            logar();
        } else{
            findScreenInputs();
            edtEmail.setText(login);
            edtPassword.setText(password);
            showLoginLayout();
        }
    }

    private void changeActivity(Class screenClass){
        Intent intent = new Intent(this, screenClass);
        startActivity(intent);
        finish();
    }

    @Override
    public  void processStart(){

    }

    @Override
    public void processFinish(String output) {
        if(output == null){
            GuiUtil.myToast(getApplicationContext(), getString(R.string.msg_erro_login_or_password_wrong));
            showLoginLayout();
            Intent intent = new Intent(this, LoginActivity.class);
            intent.putExtra("autoLogin", true);
            startActivity(intent);
            finish();
            //changeActivity(LoginActivity.class);
        }
        else{
            UsuarioService usuarioService = new UsuarioService();
            SharedPreferencesServices sharedPreferencesServices = new SharedPreferencesServices(getApplicationContext());
            sharedPreferencesServices.setTokenPreferences(usuarioService.limpandoJson(output));
            sharedPreferencesServices.setLoginPreferences(edtEmail.getText().toString());
            sharedPreferencesServices.setPasswordPreferences(edtPassword.getText().toString());
            changeActivity(MainActivity.class);
        }
    }
}