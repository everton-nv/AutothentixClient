package com.ufrpe.autothentixclient.usuario.gui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Switch;

import com.ufrpe.autothentixclient.R;
import com.ufrpe.autothentixclient.infra.GuiUtil;
import com.ufrpe.autothentixclient.infra.SharedPreferencesServices;
import com.ufrpe.autothentixclient.infra.ValidacaoService;
import com.ufrpe.autothentixclient.usuario.dominio.PessoaFisica;
import com.ufrpe.autothentixclient.usuario.dominio.PessoaJuridica;
import com.ufrpe.autothentixclient.usuario.dominio.Usuario;
import com.ufrpe.autothentixclient.usuario.service.ConexaoServidor;
import com.ufrpe.autothentixclient.usuario.service.UsuarioService;

import java.io.IOException;
import java.util.Objects;


public class CadastroActivity extends AppCompatActivity implements AsyncResposta{
    private AlertDialog alerta;
    private EditText edtNome, edtCpf, edtDataNasc, edtSexo, edtTelefone, edtEmail, edtSenha, edtRepetirSenha, edtCnpj;
    private TextInputLayout layoutTextNome, layoutTextCpf, layoutTextCnpj, layoutTextSexo, layoutTextDataNasc;
    private Switch switchTipoCadastro;

    private static final int ZERO = 0;
    private static final int UM = 1;
    ConexaoServidor conexaoServidor = new ConexaoServidor();
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        conexaoServidor.delegate = this;

        try {
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.screen_name_signup);
        } catch (Exception e) {
            Log.e(getString(R.string.log_screen_signup), e.getMessage());
            GuiUtil.myToastShort(this, getString(R.string.msg_error_open_activity));
            changeActivity(LoginActivity.class);
        }

        findScreenInputs();
        findScreenLayouts();

        edtSexo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    setarGeneroEditText();
                }
            }
        });

        switchTipoCadastro.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean bChecked) {
                if (bChecked) {
                    showLegalPersonLayout();
                } else {
                    showNaturalPersonLayout();
                }
            }
        });
    }

    private void showNaturalPersonLayout() {
        switchTipoCadastro.setText(R.string.switch_tipo_cadastro_pessoa_fisica);
        edtNome.setVisibility(View.VISIBLE);
        edtCpf.setVisibility(View.VISIBLE);
        edtDataNasc.setVisibility(View.VISIBLE);
        edtSexo.setVisibility(View.VISIBLE);

        layoutTextNome.setVisibility(View.VISIBLE);
        layoutTextCpf.setVisibility(View.VISIBLE);
        layoutTextDataNasc.setVisibility(View.VISIBLE);
        layoutTextSexo.setVisibility(View.VISIBLE);

        edtCnpj.setVisibility(View.GONE);

        layoutTextCnpj.setVisibility(View.GONE);
    }

    private void showLegalPersonLayout() {
        switchTipoCadastro.setText(R.string.switch_tipo_cadastro_pessoa_juridica);
        edtCnpj.setVisibility(View.VISIBLE);

        layoutTextCnpj.setVisibility(View.VISIBLE);

        edtNome.setVisibility(View.GONE);
        edtCpf.setVisibility(View.GONE);
        edtDataNasc.setVisibility(View.GONE);
        edtSexo.setVisibility(View.GONE);

        layoutTextNome.setVisibility(View.GONE);
        layoutTextCpf.setVisibility(View.GONE);
        layoutTextDataNasc.setVisibility(View.GONE);
        layoutTextSexo.setVisibility(View.GONE);
    }

    private void findScreenLayouts() {
        layoutTextNome = findViewById(R.id.layoutTextNome);
        layoutTextCpf = findViewById(R.id.layoutTextCpf);
        layoutTextCnpj = findViewById(R.id.layoutTextCnpj);
        layoutTextSexo = findViewById(R.id.layoutTextSexo);
        layoutTextDataNasc = findViewById(R.id.layoutTextDataNasc);
    }

    private void findScreenInputs() {
        progressBar = findViewById(R.id.login_progress);
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
    }

    private void changeActivity(Class screenClass){
        Intent intent = new Intent(this, screenClass);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        changeActivity(LoginActivity.class);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        changeActivity(LoginActivity.class);
        switch (item.getItemId()) {
            case android.R.id.home:
                changeActivity(LoginActivity.class);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onClickCancel(View view) {
        changeActivity(LoginActivity.class);
    }

    private void setarGeneroEditText() {
        //adapter utilizando um layout customizado (TextView)
        final ArrayAdapter adapter = new ArrayAdapter<>(this, R.layout.alert_criar_conta_pt1, getResources().getStringArray(R.array.gender));

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle(R.string.choice_your_genre);
        //define o diálogo como uma lista, passa o adapter.

        builder.setSingleChoiceItems(adapter, 0, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                String genero = Objects.requireNonNull(adapter.getItem(arg1)).toString();
                edtSexo.setText(genero);
                if(edtSexo.hasFocus()){
                    edtDataNasc.requestFocus();
                }
                alerta.dismiss();
            }
        });

        alerta = builder.create();
        alerta.show();
    }

    public void verificarTipoCadastro(View view) throws IOException {
        boolean verificador = switchTipoCadastro.isChecked();

        if (verificador) {
            validarCadastroPj();
        } else {
            validarCadastroPf();
        }
    }

    public void validarCadastroPf() throws IOException {
        String nome = edtNome.getText().toString();
        String cpf = edtCpf.getText().toString();
        String email = edtEmail.getText().toString();
        String nasc = edtDataNasc.getText().toString();
        String senha = edtSenha.getText().toString();
        String sexo = edtSexo.getText().toString();
        String telefone = edtTelefone.getText().toString();
        String repetirSenha = edtRepetirSenha.getText().toString();

        ValidacaoService validacaoCadastro = new ValidacaoService();
        boolean valid = true;

        if (validacaoCadastro.isCampoVazio(sexo)) {
            setarGeneroEditText();
            valid = false;
        }
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
        if (!validacaoCadastro.isTelefoneValido(telefone)) {
            edtTelefone.requestFocus();
            edtTelefone.setError(getString(R.string.msg_telefone_invalido));
            valid = false;
        }
        if (!validacaoCadastro.isEmailValido(email)) {
            edtEmail.requestFocus();
            edtEmail.setError(getString(R.string.msg_email_invalido));
            valid = false;
        }
        if (!validacaoCadastro.isDataValida(nasc)) {
            edtDataNasc.requestFocus();
            edtDataNasc.setError(getString(R.string.msg_data_invalida));
            valid = false;
        }
        if (!validacaoCadastro.isCpfValido(cpf)) {
            edtCpf.requestFocus();
            edtCpf.setError(getString(R.string.msg_cpf_invalido));
            valid = false;
        }
        if (validacaoCadastro.isCampoVazio(nome)) {
            edtNome.requestFocus();
            edtNome.setError(getString(R.string.msg_nome_invalido));
            valid = false;
        }
        if (valid) {
            Usuario usuario = new Usuario(email,senha);
            PessoaFisica pessoaFisica = new PessoaFisica(nome, cpf, telefone, sexo.substring(ZERO, UM), validacaoCadastro.dataFormatoBanco(nasc));
            UsuarioService service = new UsuarioService();
            service.inserirCadastroPf(usuario, pessoaFisica, conexaoServidor);
        }
    }

    public void validarCadastroPj() {
        String cnpj = edtCnpj.getText().toString();
        String email = edtEmail.getText().toString();
        String telefone = edtTelefone.getText().toString();
        String senha = edtSenha.getText().toString();
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
        if (!validacaoCadastro.isTelefoneValido(telefone)) {
            edtTelefone.requestFocus();
            edtTelefone.setError(getString(R.string.msg_telefone_invalido));
            valid = false;
        }
        if (!validacaoCadastro.isEmailValido(email)) {
            edtEmail.requestFocus();
            edtEmail.setError(getString(R.string.msg_email_invalido));
            valid = false;
        }
        if (!validacaoCadastro.isCpfValido(cnpj)) {
            edtCnpj.requestFocus();
            edtCnpj.setError(getString(R.string.msg_cnpj_invalido));
            valid = false;
        }
        if (valid) {
            Usuario usuario = new Usuario(email, senha);
            PessoaJuridica pessoaJuridica = new PessoaJuridica(cnpj, telefone);
            UsuarioService service = new UsuarioService();
            service.inserirCadastroPj(usuario, pessoaJuridica, conexaoServidor);
        }
    }

    @Override
    public  void processStart(){
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void processFinish(String output) {
        progressBar.setVisibility(View.GONE);
        if(output == null || output.contains("error")){
            String message = (output==null) ? "Não foi possível efetuar o cadastro." : output;
            GuiUtil.myToast(this, message);
            changeActivity(CadastroActivity.class);
        }
        else{
            SharedPreferencesServices sharedPreferencesServices = new SharedPreferencesServices(getApplicationContext());
            sharedPreferencesServices.setTokenPreferences(output);
            GuiUtil.myToast(getApplicationContext(), "Cadastrado com sucesso.");
            changeActivity(LoginActivity.class);
        }
    }
}