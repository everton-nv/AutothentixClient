package com.ufrpe.autothentixclient.usuario.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.ufrpe.autothentixclient.R;

import java.util.ArrayList;
import java.util.Objects;

public class CreateDocActivity extends AppCompatActivity {
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_doc);
        try {
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.screen_name_create_doc);
        } catch (Exception e) {
            Log.e(getString(R.string.log_screen_signup), e.getMessage());
            this.returnMainActivity();
        }

        startSpinner();
    }

    private void startSpinner (){
        String[] listaDoc = {"Aluguel", "Compra e Venda","Serviços"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, listaDoc);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner = (Spinner)findViewById(R.id.spinnerNovoDoc);
        spinner.setAdapter(adapter);

        //Metodo para quando um elemento do Spinner é selecionado()
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        this.returnMainActivity();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.returnMainActivity();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void returnMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void onClickCancel(View view) {
        this.returnMainActivity();
    }

    public void onClickSpinnerChoice(View view){
        LinearLayout layoutBtnSpinner = findViewById(R.id.layoutBtnSpinner);
        LinearLayout layoutBtnEditText = findViewById(R.id.layoutBtnEditText);
        layoutBtnSpinner.setVisibility(View.GONE);
        spinner.setVisibility(View.GONE);
        layoutBtnEditText.setVisibility(View.VISIBLE);

        ArrayList<EditText> editTexts = new ArrayList<>();
        editTexts.add(new EditText(this));

        for (int i=0; i<editTexts.size(); i++) {
            editTexts.get(i).setHint("Dica");
        }
    }

}
