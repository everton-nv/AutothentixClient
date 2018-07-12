package com.ufrpe.autothentixclient.usuario.gui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

import com.ufrpe.autothentixclient.R;
import com.ufrpe.autothentixclient.infra.ValidacaoService;
import com.ufrpe.autothentixclient.usuario.service.UsuarioService;

import java.util.ArrayList;
import java.util.Objects;


public class CadastroActivity extends AppCompatActivity {
    private AlertDialog alerta;
    private EditText edtNome,edtCpf,edtDataNasc,edtSexo, edtTelefone,edtEmail,edtSenha,edtRepetirSenha, edtCnpj, edtRazaoSocial;
    private Switch switchTipoCadastro;

    private static final int ZERO = 0;
    private static final int UM = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        try{
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.screen_name_signup);
        } catch (Exception e){
            Log.e(getString(R.string.log_screen_signup), e.getMessage());
            this.returnLoginActivity();
        }

        switchTipoCadastro = findViewById(R.id.switchTipoCadastro);
        edtNome = findViewById(R.id.editTextNome);
        edtCpf = findViewById(R.id.editTextCpf);
        edtDataNasc = findViewById(R.id.editTextDataNasc);
        edtSexo = findViewById(R.id.editTextSexo);
        edtTelefone = findViewById(R.id.editTextTelef);
        edtEmail = findViewById(R.id.editTextEmail);
        edtSenha = findViewById(R.id.editTextSenha);
        edtRepetirSenha = findViewById(R.id.editTextConfirmaSenha);
        edtCnpj = findViewById(R.id.editTextCnpj);
        edtRazaoSocial = findViewById(R.id.editTextRazaoSocial);

        edtSexo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setarGeneroEditText();
            }
        });

        switchTipoCadastro.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean bChecked) {
                if (bChecked) {
                    switchTipoCadastro.setText(R.string.switch_tipo_cadastro_pessoa_juridica);
                    edtCnpj.setVisibility(View.VISIBLE);
                    edtRazaoSocial.setVisibility(View.VISIBLE);

                    edtNome.setVisibility(View.GONE);
                    edtCpf.setVisibility(View.GONE);
                    edtDataNasc.setVisibility(View.GONE);
                    edtSexo.setVisibility(View.GONE);
                } else {
                    switchTipoCadastro.setText(R.string.switch_tipo_cadastro_pessoa_fisica);
                    edtNome.setVisibility(View.VISIBLE);
                    edtCpf.setVisibility(View.VISIBLE);
                    edtDataNasc.setVisibility(View.VISIBLE);
                    edtSexo.setVisibility(View.VISIBLE);

                    edtCnpj.setVisibility(View.GONE);
                    edtRazaoSocial.setVisibility(View.GONE);
                }
            }
        });
    }

    private void returnLoginActivity(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        this.returnLoginActivity();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                this.returnLoginActivity();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onClickCancel(View view){
        this.returnLoginActivity();
    }

    private void setarGeneroEditText() {
        ArrayList<String> itens = new ArrayList<String>();
        itens.add(getString(R.string.sex_fem));
        itens.add(getString(R.string.sex_male));
        itens.add(getString(R.string.sex_other));

        //adapter utilizando um layout customizado (TextView)
        final ArrayAdapter adapter = new ArrayAdapter(this, R.layout.alert_criar_conta_pt1, itens);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.choice_your_genre);
        //define o diálogo como uma lista, passa o adapter.

        builder.setSingleChoiceItems(adapter, 0, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                String genero = adapter.getItem(arg1).toString();

                edtSexo.setText(genero);
                alerta.dismiss();
            }
        });

        alerta = builder.create();
        alerta.show();
    }

    public void verificarTipoCadastro(View view){
        boolean verificador = switchTipoCadastro.isChecked();

        if (verificador){
            validarCadastroPj();
        }else {
            validarCadastroPf();
        }
    }

    public void validarCadastroPf() {
        String nome = edtNome.getText().toString();
        String cpf = edtCpf.getText().toString();
        String email = edtEmail.getText().toString();
        String nasc = edtDataNasc.getText().toString();
        String senha = edtSenha.getText().toString();
        String sexo = edtSexo.getText().toString().substring(ZERO,UM);
        String telefone = edtTelefone.getText().toString();
        String repetirSenha = edtRepetirSenha.getText().toString();

        ValidacaoService validacaoCadastro = new ValidacaoService();
        boolean valid = true;

        if (!validacaoCadastro.isSenhaValida(senha)) {
            edtSenha.requestFocus();
            edtSenha.setError(getString(R.string.msg_senha_fora_padrão));
            valid = false;
        }
        if (!validacaoCadastro.isSenhaIgual(senha, repetirSenha)) {
            edtRepetirSenha.requestFocus();
            edtRepetirSenha.setError(getString(R.string.msg_senha_nao_confere_com_anterior));
            valid = false;
        }
        if (!validacaoCadastro.isDataValida(nasc)) {
            edtDataNasc.requestFocus();
            edtDataNasc.setError(getString(R.string.msg_data_invalida));
            valid = false;
        }
        if (!validacaoCadastro.isEmailValido(email)) {
            edtEmail.requestFocus();
            edtEmail.setError(getString(R.string.msg_email_invalido));
            valid = false;
        }
        if (!validacaoCadastro.isCpfValido(cpf)) {
            edtCpf.requestFocus();
            edtCpf.setError(getString(R.string.msg_cpf_invalido));
            valid = false;
        }
        if (!validacaoCadastro.isTelefoneValido(telefone)) {
            edtTelefone.requestFocus();
            edtTelefone.setError(getString(R.string.msg_telefone_invalido));
            valid = false;
        }

        if (validacaoCadastro.isCampoVazio(nome)) {
            edtNome.requestFocus();
            edtNome.setError(getString(R.string.msg_nome_invalido));
            valid = false;
        }

        if (valid) {
            UsuarioService service = new UsuarioService();
            service.inserirCadastroPf(email, senha, nome, cpf, telefone, sexo, nasc);
        }
    }

    public void validarCadastroPj(){
        String razaoSocial = edtRazaoSocial.getText().toString();
        String cnpj = edtCnpj.getText().toString();
        String email = edtEmail.getText().toString();
        String telefone = edtTelefone.getText().toString();
        String senha = edtSenha.getText().toString();
        String repetirSenha = edtRepetirSenha.getText().toString();

        ValidacaoService validacaoCadastro = new ValidacaoService();
        boolean valid = true;

        if (!validacaoCadastro.isSenhaValida(senha)){
            edtSenha.requestFocus();
            edtSenha.setError(getString(R.string.msg_senha_fora_padrão));
            valid = false;
        }
        if (!validacaoCadastro.isSenhaIgual(senha, repetirSenha)){
            edtRepetirSenha.requestFocus();
            edtRepetirSenha.setError(getString(R.string.msg_senha_nao_confere_com_anterior));
            valid = false;
        }
        if (validacaoCadastro.isCampoVazio(razaoSocial)){
            edtRazaoSocial.requestFocus();
            edtRazaoSocial.setError(getString(R.string.msg_razao_social_invalida));
        }
        if (!validacaoCadastro.isEmailValido(email)){
            edtEmail.requestFocus();
            edtEmail.setError(getString(R.string.msg_email_invalido));
            valid = false;
        }
        if (!validacaoCadastro.isCpfValido(cnpj)){
            edtCnpj.requestFocus();
            edtCnpj.setError(getString(R.string.msg_cnpj_invalido));
            valid = false;
        }
        if (!validacaoCadastro.isTelefoneValido(telefone)){
            edtTelefone.requestFocus();
            edtTelefone.setError(getString(R.string.msg_telefone_invalido));
            valid = false;
        }

    }
}