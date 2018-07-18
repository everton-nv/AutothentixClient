package com.ufrpe.autothentixclient.usuario.service;


import android.os.AsyncTask;

import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;


public class ConexaoServidor extends AsyncTask<String, String, String> {
    private static final int ZERO = 0;
    private static final int UM = 1;

    @Override
    protected String doInBackground(String... strings) {
        String jsonResposta = null;
        try{
        URL url = new URL(strings[UM]);
        HttpURLConnection conexao = (HttpURLConnection) url.openConnection();

        conexao.setRequestMethod("POST");
        conexao.addRequestProperty("Content-type", "application/json");

        conexao.setDoOutput(true);

        PrintStream printStream = new PrintStream(conexao.getOutputStream());
        printStream.println(strings[ZERO]);

        conexao.connect();

        jsonResposta = new Scanner(conexao.getInputStream()).next();
        }catch(Exception e){
            e.printStackTrace();
        }
        return jsonResposta;
    }
}