package com.ufrpe.autothentixclient.usuario.service;


import android.os.AsyncTask;

import com.ufrpe.autothentixclient.usuario.gui.AsyncResposta;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;


public class ConexaoServidor extends AsyncTask<String, String, String> {
    private static final int ZERO = 0;
    private static final int UM = 1;
    private static final int DOIS = 2;
    public AsyncResposta delegate = null;
    private UsuarioService usuarioService = new UsuarioService();



    @Override
    protected void onPreExecute(){
        delegate.processStart();

    }

    @Override
    protected void onCancelled(){
        delegate.processCancelled();

    }

    @Override
    protected String doInBackground(String... strings) {
        String jsonResposta = null;
        if(Objects.equals(strings[UM], usuarioService.getRotalogar())){
            jsonResposta = conectarUser(strings);
        }

        if(Objects.equals(strings[UM], usuarioService.getRotacadastropf())){
            jsonResposta = conectarUser(strings);
        }

        if(Objects.equals(strings[UM], usuarioService.getRotacadastropj())){
            jsonResposta = conectarUser(strings);
        }

        if (Objects.equals(strings[UM], usuarioService.getRotagerardoc())){
            jsonResposta = inserirDocServer(strings);
        }

        if (Objects.equals(strings[UM], usuarioService.getRotagerarhtmldoc())){
            jsonResposta = getHtmlDoc(strings);
        }

        return jsonResposta;
    }

    @Override
    protected void onPostExecute(String jsonResposta){
        delegate.processFinish(jsonResposta);
    }

    private String conectarUser(String... strings){
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

            BufferedReader reader = new BufferedReader( new InputStreamReader( conexao.getInputStream()));
            StringBuilder sbHtml = new StringBuilder();
            String linha;
            while( ( linha = reader.readLine() ) != null )
            {
                sbHtml.append (linha);
            }
            jsonResposta = sbHtml.toString();

        }catch(Exception e){
            e.printStackTrace();
        }
        usuarioService.setRespostaServidor(jsonResposta);

        return jsonResposta;
    }

    private String inserirDocServer(String... strings){
        String jsonResposta = null;

        try{
            URL url = new URL(strings[UM]);
            HttpURLConnection conexao = (HttpURLConnection) url.openConnection();

            conexao.setRequestMethod(strings[DOIS]);
            conexao.addRequestProperty("Content-type", "application/json");
            conexao.setRequestProperty("authorization",strings[3]);

            conexao.setDoOutput(true);
            conexao.setDoInput(true);

            PrintStream printStream = new PrintStream(conexao.getOutputStream());
            printStream.println(strings[ZERO]);

            conexao.connect();

            BufferedReader reader = new BufferedReader( new InputStreamReader( conexao.getInputStream()));
            StringBuilder sbHtml = new StringBuilder();
            String linha;
            while( ( linha = reader.readLine() ) != null )
            {
                sbHtml.append (linha);
            }
            jsonResposta = sbHtml.toString();

        }catch(Exception e){
            e.printStackTrace();
        }
        usuarioService.setRespostaServidor(jsonResposta);

        return jsonResposta;
    }

    private String getHtmlDoc(String... strings){
        String jsonResposta = null;
        try{
            URL url = new URL(strings[UM]);
            HttpURLConnection conexao = (HttpURLConnection) url.openConnection();

            conexao.setRequestMethod(strings[DOIS]);
            conexao.addRequestProperty("Content-type", "application/json");
            conexao.setRequestProperty("authorization",strings[3]);

            conexao.setDoOutput(true);
            conexao.setDoInput(true);

            PrintStream printStream = new PrintStream(conexao.getOutputStream());
            printStream.println(strings[ZERO]);

            conexao.connect();

            BufferedReader reader = new BufferedReader( new InputStreamReader( conexao.getInputStream()));
            StringBuilder sbHtml = new StringBuilder();
            String linha;
            while( ( linha = reader.readLine() ) != null )
            {
                sbHtml.append (linha);
            }
            jsonResposta = sbHtml.toString();

        }catch(Exception e){
            e.printStackTrace();
        }
        usuarioService.setRespostaServidor(jsonResposta);

        return jsonResposta;
    }
}