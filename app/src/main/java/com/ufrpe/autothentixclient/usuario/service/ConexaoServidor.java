package com.ufrpe.autothentixclient.usuario.service;


import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ConexaoServidor extends AsyncTask<String, String, String> {

    /*public String inserirPessoaFisica(String json){
        String jsonResposta = null;
        try {
            URL url = new URL(CADASTROPF);
            HttpURLConnection conexao = (HttpURLConnection) url.openConnection();

            conexao.setRequestMethod("POST");
            conexao.addRequestProperty("Content-type","application/json");
            conexao.setRequestProperty("Accept", "application/json");
            conexao.setDoOutput(true);

            PrintStream printStream = new PrintStream(conexao.getOutputStream());
            printStream.println(json);

            conexao.connect();

            jsonResposta = new Scanner(conexao.getInputStream()).next();

        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
        return jsonResposta;
    }*/

    /*public String inserirPessoaFisica(String json) throws IOException {
        String jsonResposta = null;

        URL url = new URL(CADASTROPF);
        HttpURLConnection conexao = (HttpURLConnection) url.openConnection();

        conexao.setRequestMethod("POST");
        conexao.addRequestProperty("Content-type","application/json");
        conexao.setRequestProperty("Accept", "application/json");

        conexao.setDoOutput(true);

        PrintStream printStream = new PrintStream(conexao.getOutputStream());
        printStream.println(json);

        conexao.connect();

        jsonResposta = new Scanner(conexao.getInputStream()).next();

        return jsonResposta;
    }*/
    /*
    public String inserirPessoaFisica(String json) throws IOException {

        OkHttpClient client = new OkHttpClient();

        String url = CADASTROPF;

        Request.Builder builder = new Request.Builder();

        builder.url(url);

        MediaType mediaType =
                MediaType.parse("application/json; charset=utf-8");

        RequestBody body = RequestBody.create(mediaType, json);
        builder.post(body);

        Request request = builder.build();

        Response response = client.newCall(request).execute();

        return response.body().string();

    }*/

    /*public String logarUsuario(String json) throws IOException{
        String jsonResposta = null;

        URL url = new URL(LOGAR);
        HttpURLConnection conexao = (HttpURLConnection) url.openConnection();

        conexao.setRequestMethod("POST");
        conexao.addRequestProperty("Content-type", "application/json");
        //conexao.setRequestProperty("Accept", "application/json");

        conexao.setDoOutput(true);

        try{
            PrintStream printStream = new PrintStream(conexao.getOutputStream());
            printStream.println(json);
        }catch(Exception ex){
            Log.getStackTraceString(ex);
        }


        conexao.connect();

        jsonResposta = new Scanner(conexao.getInputStream()).next();

        return jsonResposta;
    }*/

    @Override
    protected String doInBackground(String... strings) {
        String jsonResposta = null;
        try{
        URL url = new URL(strings[1]);
        HttpURLConnection conexao = (HttpURLConnection) url.openConnection();

        conexao.setRequestMethod("POST");
        conexao.addRequestProperty("Content-type", "application/json");
        //conexao.setRequestProperty("Accept", "application/json");

        conexao.setDoOutput(true);

        PrintStream printStream = new PrintStream(conexao.getOutputStream());
        printStream.println(strings[0]);

        conexao.connect();

        jsonResposta = new Scanner(conexao.getInputStream()).next();
        }catch(Exception e){
            e.printStackTrace();;
        }
        return jsonResposta;
    }
    /*
    @Override
    protected Object doInBackground(Object[] objects) {
        return null;
    }*/
    /*

    public String logarUsuario(String json) throws IOException, JSONException {

        OkHttpClient client = new OkHttpClient();

        String url = LOGAR;

        Request.Builder builder = new Request.Builder();

        builder.url(url);

        MediaType mediaType =
                MediaType.parse("application/json; charset=utf-8");
        JSONObject jsonObject = new JSONObject(json);

        RequestBody body = RequestBody.create(mediaType, String.valueOf(jsonObject));
        builder.post(body);

        Request request = builder.build();

        Response response = client.newCall(request).execute();

        return response.body().string();

    }*/

}


