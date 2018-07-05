package com.ufrpe.autothentixclient.usuario.gui;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ufrpe.autothentixclient.R;
//import com.ufrpe.autothentixclient.infra.Validação;
import com.ufrpe.autothentixclient.usuario.dominio.PessoaFisica;
import com.ufrpe.autothentixclient.usuario.dominio.Usuario;

import java.util.ArrayList;
import java.util.Calendar;

public class CadastroPfActivity extends AppCompatActivity {

    private Usuario usuario = new Usuario();
  //  private Validação validação;
    private PessoaFisica pessoaFisica = new PessoaFisica();
    private AlertDialog alerta;
    private TextView displayDate;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    private static final String TAG = "Cadastro";
    private EditText nome;
    private EditText cpf;
    private EditText dataNasc;
    private EditText sexo;
    private EditText telefone;
    private EditText email;
    private EditText senha;
    private EditText repetirSenha;
    private Button entrar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_pf);

        nome = findViewById(R.id.editTextNome);
        cpf = findViewById(R.id.editTextCpf);
        dataNasc = findViewById(R.id.editTextDataNasc);
        sexo = findViewById(R.id.editTextSexo);
        telefone = findViewById(R.id.editTextTelef);
        email = findViewById(R.id.editTextEmail);
        senha = findViewById(R.id.editTextSenha);
        repetirSenha = findViewById(R.id.editTextConfirmaSenha);
        entrar = findViewById(R.id.buttonCadastrar);

        sexo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setarGeneroEditText();
            }
        });

        dataNasc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setarDataNascEditText();
            }
        });

        entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*validarEmail(email.getText().toString());
                validarTele(telefone.getText().toString());*/
                validarCpf(cpf.getText().toString());
                validarSenha(senha.getText().toString());
                validarConfSenha(repetirSenha.getText().toString(), senha.getText().toString());
                validarNome(nome.getText().toString());
                //validação.validarConfSenha(senha.getText().toString(), this);

            }
        });

    }

    private void setarGeneroEditText() {
        ArrayList<String> itens = new ArrayList<String>();
        itens.add("Feminino");
        itens.add("Masculino");

        //adapter utilizando um layout customizado (TextView)
        final ArrayAdapter adapter = new ArrayAdapter(this, R.layout.alert_criar_conta_pt1, itens);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Escolha seu gênero:");
        //define o diálogo como uma lista, passa o adapter.

        builder.setSingleChoiceItems(adapter, 0, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {

                String genero = adapter.getItem(arg1).toString();

                sexo.setText(genero);
                String sexo = genero;
                pessoaFisica.setSexo(genero);
                alerta.dismiss();
            }
        });

        alerta = builder.create();
        alerta.show();
    }

    public void setarDataNascEditText() {
        displayDate = (TextView) findViewById(R.id.editTextDataNasc);

        displayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar calendar = Calendar.getInstance();
                int ano = calendar.get(Calendar.YEAR);
                int mes = calendar.get(Calendar.MONTH);
                int dia = calendar.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog dialog = new DatePickerDialog(CadastroPfActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, dateSetListener, ano, mes, dia);

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();


            }

        });

        dateSetListener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                Log.d(TAG, "dataSet: date: dd/mm/yyyy: " + dayOfMonth + "/" + month + "/" + year);
                String date = dayOfMonth + "/" + (month + 1) + "/" + year;
                dataNasc.setText(date);
                String data = date;
                pessoaFisica.setDataNasc(data);
            }
        };
    }


    private void validarCpf(String cpf) {

        if (cpf.length() == 11 ) {
            pessoaFisica.setCpf(cpf);

        } else {
            Toast.makeText(CadastroPfActivity.this, "Verifique o campo do cpf!!", Toast.LENGTH_SHORT).show();
        }
    }

    private void validarSenha(String senha) {

        if (senha.length() == 8 ) {
            usuario.setSenha(senha);

        } else {
            Toast.makeText(CadastroPfActivity.this, "Verifique o campo do senha!!", Toast.LENGTH_SHORT).show();
        }
    }

    private void validarConfSenha(String confSenha, String senha) {

        if (!confSenha.isEmpty()) {

        } else {
            Toast.makeText(CadastroPfActivity.this, "Verifique o campo do confirmar senha!!", Toast.LENGTH_SHORT).show();
        }
    }

    private void validarNome(String nome) {

        if (!nome.isEmpty()) {
            pessoaFisica.setNome(nome);

        } else {
            Toast.makeText(CadastroPfActivity.this, "Verifique o campo do Nome!!", Toast.LENGTH_SHORT).show();
        }
    }
}


