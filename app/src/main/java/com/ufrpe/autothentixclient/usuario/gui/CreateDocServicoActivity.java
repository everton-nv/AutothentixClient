package com.ufrpe.autothentixclient.usuario.gui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

import static com.ufrpe.autothentixclient.usuario.dominio.TagBundleEnum.DOC_NAME_TITLE;
import static com.ufrpe.autothentixclient.usuario.dominio.TagBundleEnum.URL_PREVIEW;

public class CreateDocServicoActivity extends AppCompatActivity implements AsyncResposta {
    private EditText edtNomeContratante, edtNacContratante, edtRgContratante, edtCpfContratante;
    private EditText edtNomeEmpresa, edtCnpjContratado, edtNomeContratado, edtNacContratado, edtProfContratado, edtCpfContratado;
    private EditText edtValorNumerico , edtValorExtenso, edtNomeDoc , edtCidade, edtDataAtual;
    ConexaoServidor conexaoServidor;
    UsuarioService usuarioService = new UsuarioService();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_doc_de_servico);
        findScreenInputs();

        try {
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.screen_name_create_doc);
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

    private void initPreview(){
        Intent intent = new Intent(this, ViewDocActivity.class);
        startActivity(intent);
    }

    private void initPreview(String link){
        String nomeDocumento = edtNomeDoc.getText().toString();
        Intent intent = new Intent(this, ViewDocActivity.class);
        intent.putExtra(URL_PREVIEW.getValue(), link);
        intent.putExtra(DOC_NAME_TITLE.getValue(), nomeDocumento);
        startActivity(intent);
        finish();
    }

    public void  onClickCreateDoc(View view){

        edtNomeContratante.setText("Jadiel");
        edtCpfContratante.setText("11144433388");
        edtRgContratante.setText("235");
        edtNacContratante.setText("Brasileiro");
        edtNomeEmpresa.setText("Jadiel Company");
        edtNomeContratado.setText("Outro");
        edtCnpjContratado.setText("1234567874");
        edtNacContratado.setText("Brasileiro");
        edtProfContratado.setText("babá");
        edtValorNumerico.setText("150");
        edtValorExtenso.setText("um,cinco,zero");
        edtCidade.setText("Hellcife");
        edtDataAtual.setText("25/08/2018");
        edtCpfContratado.setText("99988855423");
        edtNomeDoc.setText("Teste");

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



        ValidacaoService validacaoCadastro = new ValidacaoService();
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
                    validacaoCadastro.dataFormatoBanco(dataAtual), nomeDocumento);

            SharedPreferencesServices sharedPreferencesServices = new SharedPreferencesServices(this);
            String token = sharedPreferencesServices.getTokenPreferences();

            connectToServer();
            usuarioService.inserirDocumento(documento, conexaoServidor, token);
        }
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
        //LinearLayout progressBarLayout = findViewById(R.id.progressBarLayout);
        //progressBarLayout.setVisibility(View.GONE);

        LoadScreen.loadOut(this, (LinearLayout) findViewById(R.id.progressBarLayout));
        SharedPreferencesServices sharedPreferencesServices = new SharedPreferencesServices(this);

        if(output.contains("data") && !output.equals("{\"data\":\"1 Transação adicionada na fila\"}")){
            String token = sharedPreferencesServices.getTokenPreferences();
            connectToServer();
            String novoJsonResposta = output.substring(8,output.length()-1);
            usuarioService.inserirBloco(novoJsonResposta,conexaoServidor,token, "inserir");
        }
        else if(output.equals("{\"data\":\"1 Transação adicionada na fila\"}")) {
            sharedPreferencesServices.needUpdateDocList();
            initPreview(usuarioService.getJSONDOC());
        }

    }

    private void connectToServer(){
        conexaoServidor = new ConexaoServidor();
        conexaoServidor.delegate = this;
    }
}