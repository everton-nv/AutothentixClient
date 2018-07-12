package com.ufrpe.autothentixclient.usuario.service;


import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class ConexaoServidor {

    private static final String CADASTROPF = "https://app-autothentix.herokuapp.com/registra/Pfisica";
    private static final String CADASTROPJ = "https://app-autothentix.herokuapp.com/registra/Pjuridica";
    private static final String LOGAR =     "https://app-autothentix.herokuapp.com/autenticacao/login";



    public String inserirPessoaFisica(String json){
        String jsonResposta = null;
        try {
            URL url = new URL(CADASTROPF);
            HttpURLConnection conexao = (HttpURLConnection) url.openConnection();

            conexao.setRequestMethod("POST");
            conexao.addRequestProperty("Content-type","application/json");
            conexao.setDoOutput(true);

            PrintStream printStream = new PrintStream(conexao.getOutputStream());
            printStream.println(json);

            conexao.connect();

            jsonResposta = new Scanner(conexao.getInputStream()).next();

        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
        return jsonResposta;
    }

}


