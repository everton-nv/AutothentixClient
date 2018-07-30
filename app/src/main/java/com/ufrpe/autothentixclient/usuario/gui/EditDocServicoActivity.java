package com.ufrpe.autothentixclient.usuario.gui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.ufrpe.autothentixclient.R;
import com.ufrpe.autothentixclient.infra.GuiUtil;
import com.ufrpe.autothentixclient.usuario.service.ConexaoServidor;
import com.ufrpe.autothentixclient.usuario.service.UsuarioService;

import java.util.Objects;

import static com.ufrpe.autothentixclient.usuario.dominio.TagBundleEnum.DOC_NAME_TITLE;
import static com.ufrpe.autothentixclient.usuario.dominio.TagBundleEnum.URL_PREVIEW;

public class EditDocServicoActivity extends AppCompatActivity implements AsyncResposta {

    private EditText edtNomeContratante, edtNacContratante, edtRgContratante, edtCpfContratante;
    private EditText edtNomeEmpresa, edtCnpjContratado, edtNomeContratado, edtNacContratado, edtProfContratado, edtCpfContratado;
    private EditText edtValorNumerico , edtValorExtenso, edtNomeDoc , edtCidade, edtEstado, edtDataAtual;
    ConexaoServidor conexaoServidor;
    UsuarioService usuarioService = new UsuarioService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_doc_de_servico);
        findScreenInputs();

        try {
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.screen_name_edit_doc);
        } catch (Exception e) {
            Log.e(getString(R.string.log_screen_create_doc_service), e.getMessage());
            GuiUtil.myToastShort(this, getString(R.string.msg_error_open_activity));
            closeActivity();
        }
    }

    private void findScreenInputs() {
        edtNomeContratante = findViewById(R.id.editTextContratanteNome);
        edtCpfContratante = findViewById(R.id.editTextContratanteCPF);
        edtRgContratante = findViewById(R.id.editTextContratanteRG);
        edtNacContratante = findViewById(R.id.editTextContratanteNacionalidade);
        edtNomeEmpresa = findViewById(R.id.editTextContratadaNomeEmpresa);
        edtNomeContratado = findViewById(R.id.editTextContratadaNome);
        edtCnpjContratado = findViewById(R.id.editTextContratadaCNPJ);
        edtNacContratado = findViewById(R.id.editTextContratadaNacionalidade);
        edtProfContratado = findViewById(R.id.editTextContratadaProfissao);
        edtValorNumerico = findViewById(R.id.editTextValor);
        edtValorExtenso = findViewById(R.id.editTextValorExtenso);
        edtCidade = findViewById(R.id.editTextCidade);
        edtEstado = findViewById(R.id.editTextEstado);
        edtDataAtual = findViewById(R.id.editTextDataAtual);
        edtCpfContratado = findViewById(R.id.editTextContratadaCPF);
        edtNomeDoc = findViewById(R.id.editTextDocNome);
    }

    private void closeActivity(){
        finish();
    }

    public void onClickCancel(View view) {
        closeActivity();
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

    private void initPreview(String link){
        String nomeDocumento = edtNomeDoc.getText().toString();
        Intent intent = new Intent(this, ViewDocActivity.class);
        intent.putExtra(URL_PREVIEW.getValue(), link);
        intent.putExtra(DOC_NAME_TITLE.getValue(), nomeDocumento);
        startActivity(intent);
    }

    private void closeKeyboard(){
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            Objects.requireNonNull(imm).hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            getCurrentFocus().clearFocus();
        }

    }

    @Override
    public void processStart() {
        closeKeyboard();
        LoadScreen.loadOn(this, (LinearLayout) findViewById(R.id.progressBarLayout));
    }

    @Override
    public void processFinish(String output) {
        LoadScreen.loadOut(this, (LinearLayout) findViewById(R.id.progressBarLayout));
    }

    private void connectToServer(){
        conexaoServidor = new ConexaoServidor();
        conexaoServidor.delegate = this;
    }
}
