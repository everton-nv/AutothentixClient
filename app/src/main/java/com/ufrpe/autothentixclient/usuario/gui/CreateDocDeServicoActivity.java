package com.ufrpe.autothentixclient.usuario.gui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.ufrpe.autothentixclient.R;
import com.ufrpe.autothentixclient.infra.GuiUtil;

import java.util.Objects;

public class CreateDocDeServicoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_doc_de_servico);

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

    public void onClickCreateDoc(View view) {
        initPreview();
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
}
