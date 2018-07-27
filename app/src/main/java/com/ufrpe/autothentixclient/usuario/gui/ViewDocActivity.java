package com.ufrpe.autothentixclient.usuario.gui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.webkit.WebView;

import com.ufrpe.autothentixclient.R;
import com.ufrpe.autothentixclient.infra.GuiUtil;
import com.ufrpe.autothentixclient.infra.SharedPreferencesServices;
import com.ufrpe.autothentixclient.usuario.service.ConexaoServidor;
import com.ufrpe.autothentixclient.usuario.service.UsuarioService;

import java.util.Objects;

import static com.ufrpe.autothentixclient.usuario.dominio.TagBundleEnum.URL_PREVIEW;

public class ViewDocActivity extends AppCompatActivity implements AsyncResposta {

    ConexaoServidor conexaoServidor = new ConexaoServidor();
    UsuarioService usuarioService = new UsuarioService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        conexaoServidor.delegate = this;
        setContentView(R.layout.activity_view_doc);

        try {
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Nome do Documento");
        } catch (Exception e) {
            Log.e(getString(R.string.log_screen_create_doc_service), e.getMessage());
            GuiUtil.myToastShort(this, getString(R.string.msg_error_open_activity));
            closeActivity();
        }

        initVlisualizarDoc();
    }

    private void closeActivity(){
        finish();
    }

    @Override
    public void onBackPressed() {
        closeActivity();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.closeActivity();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initVlisualizarDoc(){
        try {
            Bundle bundle = getIntent().getExtras();
            String jason = bundle.getString(URL_PREVIEW.getValue());

            SharedPreferencesServices sharedPreferencesServices = new SharedPreferencesServices(this);
            String token = sharedPreferencesServices.getTokenPreferences();

            usuarioService.gerarHtmlDoc(jason,conexaoServidor,token);

        } catch (Exception e){
            Log.e("ViewDocActivity",e.getMessage());
            GuiUtil.myToast(this,"Ocorreu um erro ao conectar com o servidor, tente novamente.");
        }
    }

    @Override
    public void processFinish(String output) {
        WebView myWebView = findViewById(R.id.webview);
        myWebView.getSettings().setUseWideViewPort(true);
        myWebView.loadData(output,"text/html","UTF-8");

    }

    @Override
    public void processStart() {

    }
}
