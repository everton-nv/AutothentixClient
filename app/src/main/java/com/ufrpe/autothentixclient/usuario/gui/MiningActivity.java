package com.ufrpe.autothentixclient.usuario.gui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ufrpe.autothentixclient.R;
import com.ufrpe.autothentixclient.infra.GuiUtil;
import com.ufrpe.autothentixclient.usuario.service.ConexaoServidor;

import java.util.Objects;

import static com.ufrpe.autothentixclient.usuario.gui.animation.MyAnimation.getAnimationFadeIn;
import static com.ufrpe.autothentixclient.usuario.gui.animation.MyAnimation.getAnimationFadeOut;

public class MiningActivity extends AppCompatActivity implements AsyncResposta {
    ConexaoServidor conexaoServidor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mining);

        try {
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.screen_name_mining);
        } catch (Exception e) {
            Log.e(getString(R.string.log_screen_mining), e.getMessage());
            GuiUtil.myToastShort(this, getString(R.string.msg_error_open_activity));
            changeActivity(MainActivity.class);
        }
    }

    private void changeActivity(Class screenClass){
        Intent intent = new Intent(this, screenClass);
        startActivity(intent);
        finish();
    }

    private void showLoadLayout() {
        Animation animationFadeOut = getAnimationFadeOut(this);

        ProgressBar progressBar = findViewById(R.id.progressBarMining);
        TextView labelMining = findViewById(R.id.lblMinerando);
        TextView txtBlockchain = findViewById(R.id.txtBlockchain);

        animationFadeOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                txtBlockchain.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        txtBlockchain.startAnimation(animationFadeOut);

        progressBar.setVisibility(View.VISIBLE);
        progressBar.startAnimation(getAnimationFadeIn(this));
        labelMining.setVisibility(View.VISIBLE);
        labelMining.startAnimation(getAnimationFadeIn(this));
    }

    private void hideLoadLayout(){
        Animation animationFadeOut = getAnimationFadeOut(this);

        ProgressBar progressBar = findViewById(R.id.progressBarMining);
        TextView labelMining = findViewById(R.id.lblMinerando);
        TextView txtBlockchain = findViewById(R.id.txtBlockchain);

        animationFadeOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                progressBar.setVisibility(View.GONE);
                labelMining.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        progressBar.startAnimation(animationFadeOut);

        txtBlockchain.setVisibility(View.VISIBLE);
        txtBlockchain.startAnimation(getAnimationFadeIn(this));
    }

    @Override
    public void onBackPressed() {
        changeActivity(LoginActivity.class);
    }

    @Override
    public void processStart() {

    }

    @Override
    public void processFinish(String output) {

    }

    private void connectToServer(){
        conexaoServidor = new ConexaoServidor();
        conexaoServidor.delegate = this;
    }
}
