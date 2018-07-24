package com.ufrpe.autothentixclient.usuario.gui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.webkit.WebView;

import com.ufrpe.autothentixclient.R;
import com.ufrpe.autothentixclient.infra.GuiUtil;

import java.util.Objects;

public class ViewDocActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_doc);

        try {
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Nome do Documento");
        } catch (Exception e) {
            Log.e(getString(R.string.log_screen_create_doc_service), e.getMessage());
            GuiUtil.myToastShort(this, getString(R.string.msg_error_open_activity));
            closeActivity();
        }

        WebView myWebView = (WebView) findViewById(R.id.webview);
        myWebView.loadUrl("www.google.com");
    }

    private void closeActivity(){
        finish();
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
}
