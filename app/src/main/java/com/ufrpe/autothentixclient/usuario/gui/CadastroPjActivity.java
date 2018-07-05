package com.ufrpe.autothentixclient.usuario.gui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ufrpe.autothentixclient.R;
import com.ufrpe.autothentixclient.usuario.dominio.PessoaJuridica;
import com.ufrpe.autothentixclient.usuario.dominio.Usuario;

public class CadastroPjActivity extends AppCompatActivity {

    private Usuario usuario = new Usuario();
    private PessoaJuridica pessoaJuridica= new PessoaJuridica();
    private EditText cnpj;
    private EditText telefone;
    private EditText email;
    private EditText senha;
    private EditText repetSenha;
    private Button cadastrar;
    private EditText razãoSocial;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_pj);

        cnpj = findViewById(R.id.editTextCnpj);
        telefone = findViewById(R.id.editTextTelefone);
        email = findViewById(R.id.editTextEmailpj);
        senha = findViewById(R.id.editTextConfirmaSenhapj);
        repetSenha = findViewById(R.id.editTextConfirmaSenhapj);
        cadastrar = findViewById(R.id.buttonCadastrarpj);
        razãoSocial = findViewById(R.id.editTextRazaoSocial);

        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                validarEmail(email.getText().toString());
                validarTele(telefone.getText().toString());
                validarCnpj(cnpj.getText().toString());
                validarSenha(senha.getText().toString());
                validarConfSenha(repetSenha.getText().toString(), senha.getText().toString());
                validarNome(razãoSocial.getText().toString());

                //validação.validarConfSenha(senha.getText().toString(), this);

            }
        });
    }

    private void validarEmail(String Email) {

        if (Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
           usuario.setEmail(Email);

        } else {
            Toast.makeText(CadastroPjActivity.this, "Verifique o campo do email!!", Toast.LENGTH_SHORT).show();
        }
    }

    private void validarCnpj(String cnpj) {

        if (cnpj.length() == 14 ) {
            pessoaJuridica.setCnpj(cnpj);

        } else {
            Toast.makeText(CadastroPjActivity.this, "Verifique o campo do CNPJ!!", Toast.LENGTH_SHORT).show();
        }
    }

    private void validarTele(String telefone) {

        if (telefone.length() == 9) {
            pessoaJuridica.setTelefone(telefone);

        } else {
            Toast.makeText(CadastroPjActivity.this, "Verifique o campo do telefone!!", Toast.LENGTH_SHORT).show();
        }
    }

    private void validarSenha(String senha) {

        if (senha.length() == 8 ) {
            usuario.setSenha(senha);

        } else {
            Toast.makeText(CadastroPjActivity.this, "Verifique o campo do senha!!", Toast.LENGTH_SHORT).show();
        }
    }

    private void validarConfSenha(String confSenha, String senha) {

        if (!confSenha.isEmpty()) {

        } else {
            Toast.makeText(CadastroPjActivity.this, "Verifique o campo do confirmar senha!!", Toast.LENGTH_SHORT).show();
        }
    }

    private void validarNome(String nome) {

        if (!nome.isEmpty()) {
            pessoaJuridica.setRazaoSocial(nome);

        } else {
            Toast.makeText(CadastroPjActivity.this, "Verifique o campo do Razão Social!!", Toast.LENGTH_SHORT).show();
        }
    }
}
