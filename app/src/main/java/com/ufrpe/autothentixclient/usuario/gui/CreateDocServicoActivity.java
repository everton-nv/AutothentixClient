package com.ufrpe.autothentixclient.usuario.gui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ufrpe.autothentixclient.R;
import com.ufrpe.autothentixclient.infra.GuiUtil;
import com.ufrpe.autothentixclient.infra.ValidacaoService;
import com.ufrpe.autothentixclient.usuario.service.ConexaoServidor;
import com.ufrpe.autothentixclient.usuario.service.UsuarioService;

import java.util.Objects;

public class CreateDocServicoActivity extends AppCompatActivity implements AsyncResposta {

    private EditText edtNomeContratante, edtNacContratante, edtRgContratante, edtCpfContratante;
    private EditText edtNomeEmpresa, edtCnpjContratado, edtNomeContratado, edtNacContratado, edtProfContratado, edtCpfContratado;
    private EditText edtValorNumerico , edtValorExtenso, edtNomeDoc , edtCidade, edtEstado, edtDataAtual;
    private Button criar;
    ConexaoServidor conexaoServidor = new ConexaoServidor();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        conexaoServidor.delegate = this;
        setContentView(R.layout.activity_create_doc_de_servico);
        edtNomeContratante = findViewById(R.id.editTextContratanteNome);
        edtCpfContratante = findViewById(R.id.editTextContratanteCPF);
        edtRgContratante = findViewById(R.id.editTextContratanteRG);
        edtNacContratante =findViewById(R.id.editTextContratanteNacionalidade);
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
        edtNomeDoc = findViewById(R.id.editTextNome);
        criar = findViewById(R.id.btnRegister);

        try {
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.screen_name_create_doc);
        } catch (Exception e) {
            Log.e(getString(R.string.log_screen_create_doc_service), e.getMessage());
            GuiUtil.myToastShort(this, getString(R.string.msg_error_open_activity));
            closeActivity();
        }
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


    private void initPreview(){
        Intent intent = new Intent(this, ViewDocActivity.class);
        startActivity(intent);
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
        String estado = edtEstado.getText().toString();
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

        if (validacaoCadastro.isCampoVazio(estado)) {
            edtEstado.requestFocus();
            edtEstado.setError(getString(R.string.msg_estado_invalido));
            valid = false;
        }

        if (!validacaoCadastro.isDataDoc(dataAtual)) {
            edtDataAtual.requestFocus();
            edtDataAtual.setError(getString(R.string.msg_data_invalida));
            valid = false;
        }

        if (valid){
            UsuarioService usuarioService = new UsuarioService();
            usuarioService.inserirDocumento(nomeContratante , cpfContratante , rgContratante, nacContratante,  nomeEmpresa , nomeContratado,
                                            cnpjContratado,nacContratado,cpfContratado,  profContratado, valorNumerico, valorExtenso, cidade, estado, dataAtual, nomeDocumento, conexaoServidor );
        }

    }

    @Override
    public void processFinish(String output) {
        initPreview();

    }

    @Override
    public void processStart() {

    }
}