package com.ufrpe.autothentixclient.usuario.service;


import android.os.AsyncTask;

import com.ufrpe.autothentixclient.usuario.gui.AsyncResposta;

import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;


public class ConexaoServidor extends AsyncTask<String, String, String> {
    private static final int ZERO = 0;
    private static final int UM = 1;
    private static final int DOIS = 2;
    public AsyncResposta delegate = null;

    @Override
    protected void onPreExecute(){
        delegate.processStart();
    }

    @Override
    protected String doInBackground(String... strings) {
        if(strings[DOIS] == "POST"){
            String jsonResposta = null;
            try{
                URL url = new URL(strings[UM]);
                HttpURLConnection conexao = (HttpURLConnection) url.openConnection();

                conexao.setRequestMethod(strings[DOIS]);
                conexao.addRequestProperty("Content-type", "application/json");

                conexao.setDoOutput(true);
                conexao.setDoInput(true);

                PrintStream printStream = new PrintStream(conexao.getOutputStream());
                printStream.println(strings[ZERO]);

                conexao.connect();

                jsonResposta = new Scanner(conexao.getInputStream()).next();

            }catch(Exception e){
                e.printStackTrace();
            }
            UsuarioService usuarioService = new UsuarioService();
            usuarioService.setRespostaServidor(jsonResposta);

            return jsonResposta;
        }else{
            String jsonResposta = null;
            try{
                URL url = new URL(strings[UM]);
                HttpURLConnection conexao = (HttpURLConnection) url.openConnection();

                conexao.setRequestMethod(strings[DOIS]);
                conexao.addRequestProperty("Content-type", "application/json");

                conexao.setDoOutput(true);
                conexao.setDoInput(true);

                conexao.connect();

                jsonResposta = new Scanner(conexao.getInputStream()).next();

            }catch(Exception e){
                e.printStackTrace();
            }
            UsuarioService usuarioService = new UsuarioService();
            usuarioService.setRespostaServidor(jsonResposta);

            return jsonResposta;
        }

    }

    @Override
    protected void onPostExecute(String jsonResposta){
        delegate.processFinish(jsonResposta);

    }
}