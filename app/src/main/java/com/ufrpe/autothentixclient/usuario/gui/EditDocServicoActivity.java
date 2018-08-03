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
import com.ufrpe.autothentixclient.infra.BlockChain;
import com.ufrpe.autothentixclient.infra.Bloco;
import com.ufrpe.autothentixclient.infra.GuiUtil;
import com.ufrpe.autothentixclient.infra.SharedPreferencesServices;
import com.ufrpe.autothentixclient.infra.ValidacaoService;
import com.ufrpe.autothentixclient.usuario.dominio.Documento;
import com.ufrpe.autothentixclient.usuario.service.ConexaoServidor;
import com.ufrpe.autothentixclient.usuario.service.UsuarioService;

import java.util.ArrayList;
import java.util.Objects;

import static com.ufrpe.autothentixclient.usuario.dominio.TagBundleEnum.DOC_ID;
import static com.ufrpe.autothentixclient.usuario.dominio.TagBundleEnum.DOC_JSON;
import static com.ufrpe.autothentixclient.usuario.dominio.TagBundleEnum.DOC_NAME_TITLE;
import static com.ufrpe.autothentixclient.usuario.dominio.TagBundleEnum.URL_PREVIEW;

public class EditDocServicoActivity extends AppCompatActivity implements AsyncResposta {

    private EditText edtNomeContratante, edtNacContratante, edtRgContratante, edtCpfContratante;
    private EditText edtNomeEmpresa, edtCnpjContratado, edtNomeContratado, edtNacContratado, edtProfContratado, edtCpfContratado;
    private EditText edtValorNumerico , edtValorExtenso, edtNomeDoc , edtCidade, edtEstado, edtDataAtual;
    ConexaoServidor conexaoServidor;
    UsuarioService usuarioService = new UsuarioService();
    ValidacaoService validacaoCadastro = new ValidacaoService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_doc_de_servico);
        findScreenInputs();
        setFieldsDoc();

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

    private void setFieldsDoc(){
        try {
            String docJson = getIntent().getStringExtra(DOC_JSON.getValue());
             Documento documento = (Documento) usuarioService.docAppJsontoObject(docJson);

            edtNomeContratante.setText(documento.getContratantenome());
            edtCpfContratante.setText(documento.getContratantecpf());
            edtRgContratante.setText(documento.getContratanterg());
            edtNacContratante.setText(documento.getContratantenacionalidade());
            edtNomeEmpresa.setText(documento.getContratadanomeEmpresa());
            edtNomeContratado.setText(documento.getContratadanome());
            edtCnpjContratado.setText(documento.getContratadacnpj());
            edtNacContratado.setText(documento.getContratadanacionalidade());
            edtProfContratado.setText(documento.getContratadaprofissao());
            edtValorNumerico.setText(documento.getValor());
            edtValorExtenso.setText(documento.getValorExtenso());
            edtCidade.setText(documento.getCidade());
            edtDataAtual.setText(validacaoCadastro.dataExibicao(documento.getDatatual()));
            edtCpfContratado.setText(documento.getContratadacpf());
            edtNomeDoc.setText(documento.getNomedoc());

        }catch (Exception e){
            GuiUtil.myToast(this, e);
            closeActivity();
        }
    }

    @Override
    public void processStart() {
        closeKeyboard();
        LoadScreen.loadOn(this, (LinearLayout) findViewById(R.id.progressBarLayout));
    }

    @Override
    public void processFinish(String output) {
        SharedPreferencesServices sharedPreferencesServices = new SharedPreferencesServices(this);
        LoadScreen.loadOut(this, (LinearLayout) findViewById(R.id.progressBarLayout));
        if(output.equals("{\"data\":[1]}")){
            connectToServer();
            String token = sharedPreferencesServices.getTokenPreferences();
            String jsonDocAtt = usuarioService.getJSONDOC();
            usuarioService.inserirBloco(jsonDocAtt,conexaoServidor,token,"atualizar");
        }else if(output.equals("{\"data\":\"1 Transação adicionada na fila\"}")){
            sharedPreferencesServices.needUpdateDocList();
            closeActivity();
        }
    }

    private void connectToServer(){
        conexaoServidor = new ConexaoServidor();
        conexaoServidor.delegate = this;
    }

    public void  onClickCreateDoc(View view){

        String nomeContratante = edtNomeContratante.getText().toString();
        String cpfContratante = edtCpfContratante.getText().toString();
        String rgContratante = edtRgContratante.getText().toString();
        String nacContratante = edtNacContratante.getText().toString();
        String nomeEmpresa = edtNomeEmpresa.getText().toString();
        String nomeContratado = edtNomeContratado.getText().toString();
        String cnpjContratado = edtCnpjContratado.getText().toString();
        String nacContratado = edtNacContratado.getText().toString();
        String cpfContratado = edtCpfContratado.getText().toString();
        String profContratado = edtProfContratado.getText().toString();
        String valorNumerico = edtValorNumerico.getText().toString();
        String valorExtenso = edtValorExtenso.getText().toString();
        String cidade = edtCidade.getText().toString();
        String dataAtual = edtDataAtual.getText().toString();
        String nomeDocumento = edtNomeDoc.getText().toString();
        String docId = getIntent().getStringExtra(DOC_ID.getValue());

        boolean valid = true;

        if (validacaoCadastro.isCampoVazio(nomeContratante)) {
            edtNomeContratante.requestFocus();
            edtNomeContratante.setError(getString(R.string.msg_nome_invalido));
            valid = false;
        }
        if (!validacaoCadastro.isCpfValido(cpfContratante)) {
            edtCpfContratante.requestFocus();
            edtCpfContratante.setError(getString(R.string.msg_cpf_invalido));
            valid = false;
        }

        if (validacaoCadastro.isCampoVazio(rgContratante)) {
            edtRgContratante.requestFocus();
            edtRgContratante.setError(getString(R.string.msg_rg_invalido));
            valid = false;
        }

        if (validacaoCadastro.isCampoVazio(nacContratante)) {
            edtNacContratante.requestFocus();
            edtNacContratante.setError(getString(R.string.msg_nacionalidade_invalido));
            valid = false;
        }

        if (validacaoCadastro.isCampoVazio(nomeEmpresa)) {
            edtNomeEmpresa.requestFocus();
            edtNomeEmpresa.setError(getString(R.string.msg_nome_invalido));
            valid = false;
        }

        if (validacaoCadastro.isCampoVazio(nomeContratado)) {
            edtNomeContratado.requestFocus();
            edtNomeContratado.setError(getString(R.string.msg_nome_invalido));
            valid = false;
        }

        if (validacaoCadastro.isCampoVazio(nomeDocumento)) {
            edtNomeDoc.requestFocus();
            edtNomeDoc.setError(getString(R.string.msg_nome_invalido));
            valid = false;
        }

        if (!validacaoCadastro.isCnpjValido (cnpjContratado)) {
            edtCnpjContratado.requestFocus();
            edtCnpjContratado.setError(getString(R.string.msg_cnpj_invalido));
            valid = false;
        }

        if (validacaoCadastro.isCampoVazio(nacContratado)) {
            edtNacContratado.requestFocus();
            edtNacContratado.setError(getString(R.string.msg_nacionalidade_invalido));
            valid = false;
        }

        if (!validacaoCadastro.isCpfValido(cpfContratado)) {
            edtCpfContratado.requestFocus();
            edtCpfContratado.setError(getString(R.string.msg_cpf_invalido));
            valid = false;
        }

        if (validacaoCadastro.isCampoVazio(profContratado)) {
            edtProfContratado.requestFocus();
            edtProfContratado.setError(getString(R.string.msg_profissão_invalido));
            valid = false;
        }

        if (validacaoCadastro.isCampoVazio(valorExtenso)) {
            edtValorExtenso.requestFocus();
            edtValorExtenso.setError(getString(R.string.msg_valor_invalido));
            valid = false;
        }

        if (validacaoCadastro.isCampoVazio(valorNumerico)) {
            edtValorNumerico.requestFocus();
            edtValorNumerico.setError(getString(R.string.msg_valor_invalido));
            valid = false;
        }

        if (validacaoCadastro.isCampoVazio(cidade)) {
            edtCidade.requestFocus();
            edtCidade.setError(getString(R.string.msg_cidade_invalido));
            valid = false;
        }

        if (!validacaoCadastro.isDataDoc(dataAtual)) {
            edtDataAtual.requestFocus();
            edtDataAtual.setError(getString(R.string.msg_data_invalida));
            valid = false;
        }

        if (valid){
            Documento documento = new Documento(nomeContratante, cpfContratante, rgContratante,
                    nacContratante, nomeEmpresa, nomeContratado, cnpjContratado, nacContratado,
                    cpfContratado, profContratado, valorNumerico, valorExtenso, cidade,
                    validacaoCadastro.dataFormatoBanco(dataAtual), nomeDocumento, docId);

            SharedPreferencesServices sharedPreferencesServices = new SharedPreferencesServices(this);
            String token = sharedPreferencesServices.getTokenPreferences();

            connectToServer();
            usuarioService.atualizarDocumento(documento, conexaoServidor, token);
        }
    }
}