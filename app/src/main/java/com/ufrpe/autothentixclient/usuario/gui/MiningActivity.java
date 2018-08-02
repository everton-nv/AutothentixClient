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
import com.ufrpe.autothentixclient.infra.BlockChain;
import com.ufrpe.autothentixclient.infra.Bloco;
import com.ufrpe.autothentixclient.infra.GuiUtil;
import com.ufrpe.autothentixclient.infra.SharedPreferencesServices;
import com.ufrpe.autothentixclient.usuario.service.ConexaoServidor;
import com.ufrpe.autothentixclient.usuario.service.UsuarioService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.ufrpe.autothentixclient.usuario.gui.animation.MyAnimation.getAnimationFadeIn;
import static com.ufrpe.autothentixclient.usuario.gui.animation.MyAnimation.getAnimationFadeOut;

public class MiningActivity extends AppCompatActivity implements AsyncResposta {
    ConexaoServidor conexaoServidor;
    UsuarioService usuarioService = new UsuarioService();

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
            finish();
        }
        iniciarMineracao();
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

    private void iniciarMineracao(){
        SharedPreferencesServices sharedPreferencesServices = new SharedPreferencesServices(this);
        String token = sharedPreferencesServices.getTokenPreferences();
        connectToServer();
        usuarioService.getMinerarBlocoServer(conexaoServidor, token);
    }

    @Override
    public void onBackPressed() {
        changeActivity(LoginActivity.class);
    }

    @Override
    public void processStart() {
        showLoadLayout();
    }

    @Override
    public void processFinish(String output) {
        SharedPreferencesServices sharedPreferencesServices = new SharedPreferencesServices(this);
        String token = sharedPreferencesServices.getTokenPreferences();
        if (output.substring(0,9).equals("{\"data\":[")){
            BlockChain blockChain = new BlockChain();
            ArrayList<Bloco> listaBlocos = usuarioService.blockchainServerJsontoObject(output);
            blockChain.setBlockchain(listaBlocos);
            Bloco bloco = usuarioService.blocoJsontoObject(usuarioService.getBLOCOMINERAR());
            blockChain.addBloco(bloco);
            if (blockChain.isChainValid()){
                Bloco bloco1 = blockChain.getBlockchain().get(-1);
                String jsonBlocoMinerado = usuarioService.criarJsonObjeto(bloco1);
                String jsonBlockChain = usuarioService.criarJsonObjeto(blockChain);
                sharedPreferencesServices.setBlockchainPreferences(jsonBlockChain);
                connectToServer();
                usuarioService.inserirBloco(jsonBlocoMinerado, conexaoServidor, token, bloco1.getAcao());
            }else{
                connectToServer();
                usuarioService.getBlockchainServer(conexaoServidor, token);
            }

        }else if ((output.substring(0,8)).equals("{\"data\":")){
        usuarioService.setBLOCOMINERAR(output);
        connectToServer();
        usuarioService.getBlockchainServer(conexaoServidor, token);

        }else if(output.equals("{\"data\": \"ok\"}")){
            hideLoadLayout();
            TextView txtBlockchain = findViewById(R.id.txtBlockchain);
            String jsonBlockChain = sharedPreferencesServices.getBlockchainPreferences();
            BlockChain blockChain = new BlockChain();
            ArrayList<Bloco> listaBlocos = usuarioService.blockchainAppJsontoObject(jsonBlockChain);
            blockChain.setBlockchain(listaBlocos);
            txtBlockchain.setText(blockChain.toString());
        }


    }

    private void connectToServer(){
        conexaoServidor = new ConexaoServidor();
        conexaoServidor.delegate = this;
    }
}
