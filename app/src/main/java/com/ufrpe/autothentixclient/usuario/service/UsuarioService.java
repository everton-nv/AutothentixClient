package com.ufrpe.autothentixclient.usuario.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ufrpe.autothentixclient.usuario.dominio.Documento;
import com.ufrpe.autothentixclient.usuario.dominio.PessoaFisica;
import com.ufrpe.autothentixclient.usuario.dominio.PessoaJuridica;
import com.ufrpe.autothentixclient.usuario.dominio.Usuario;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UsuarioService {

    private static final String URLBASE = "https://app-autothentix.herokuapp.com/";
    private static final String ROTACADASTROPF = URLBASE + "registra/pfisica";
    private static final String ROTACADASTROPJ = URLBASE + "registra/pjuridica";
    private static final String ROTALOGAR = URLBASE + "auth/login";
    private static final String ROTAGERARDOC = URLBASE + "geradocumento/salva";
    private static final String ROTAGERARHTMLDOC = URLBASE + "geradocumento/html";
    private static final String ROTAGERARLISTADOC = URLBASE + "geradocumento";
    private static final String ROTAATUALIZARDOC = URLBASE + "geradocumento/";
    private static final String ROTADELETARDOC = URLBASE + "geradocumento/";
    private static final String METODOGET = "GET";
    private static final String METODOPOST = "POST";
    private static final String METODOPUT = "PUT";
    private static final String METODODELETAR = "DELETE";
    private String JSONDOC;
    private static final int UM = 1;
    private static final int OITO = 8;
    private Gson gson = new Gson();
    private String respostaServidor;


    public UsuarioService(){}

    public String getJSONDOC() {
        return JSONDOC;
    }

    private void setJSONDOC(String JSONDOC) {
        this.JSONDOC = JSONDOC;
    }

    String getRotalogar(){
        return ROTALOGAR;
    }

    String getRotagerardoc(){
        return ROTAGERARDOC;
    }

    String getRotacadastropf(){
        return ROTACADASTROPF;
    }

    String getRotacadastropj(){
        return ROTACADASTROPJ;
    }

    String getRotagerarhtmldoc(){
        return ROTAGERARHTMLDOC;
    }

    String getRotagerarlistadoc(){return ROTAGERARLISTADOC;}

    String getMetodoput(){return METODOPUT;}

    String getMetododeletar(){ return METODODELETAR;}


    public String criarJsonObjeto(Object objeto){
        return gson.toJson(objeto);
    }

   public void inserirCadastroPf(Usuario usuario, PessoaFisica pessoaFisica, ConexaoServidor conexaoServidor){
        String jsonUser = criarJsonObjeto(usuario);
        String jsonPf = criarJsonObjeto(pessoaFisica);
        String novoJson = juntarJsonPf(jsonUser,jsonPf);
        conexaoServidor.execute(novoJson, ROTACADASTROPF, METODOPOST);
   }
   public void inserirCadastroPj(Usuario usuario, PessoaJuridica pessoaJuridica, ConexaoServidor conexaoServidor){
        String jsonUser = criarJsonObjeto(usuario);
        String jsonPj = criarJsonObjeto(pessoaJuridica);
        String novoJson = juntarJsonPf(jsonUser, jsonPj);
        conexaoServidor.execute(novoJson, ROTACADASTROPJ, METODOPOST);
   }

   public void inserirDocumento(Documento documento, ConexaoServidor conexaoServidor, String token ) {
        String jsonDoc = criarJsonObjeto(documento);
        setJSONDOC(jsonDoc);
        conexaoServidor.execute(jsonDoc, ROTAGERARDOC, METODOPOST, token);
   }

   public void atualizarDocumento(Documento documento, ConexaoServidor conexaoServidor, String token){
       String novaRotaAtt = ROTAATUALIZARDOC + documento.getId();
       String jsonDocAtt = criarJsonObjeto(documento);
       conexaoServidor.execute(jsonDocAtt,novaRotaAtt,METODOPUT,token);
   }

    public void gerarHtmlDoc(String json, ConexaoServidor conexaoServidor, String token ) {
        conexaoServidor.execute(json, ROTAGERARHTMLDOC, METODOPOST, token);
    }

    public void logar(Usuario usuario, ConexaoServidor conexaoServidor){
        String jsonUser = criarJsonObjeto(usuario);
        conexaoServidor.execute(jsonUser,ROTALOGAR, METODOPOST);
    }

    public void deletarDocumento(String docId,ConexaoServidor conexaoServidor, String token){
        String novaRotaAtt = ROTADELETARDOC + docId;
        conexaoServidor.execute(novaRotaAtt, METODODELETAR, token);
    }

    public String getRespostaServidor() {
        return respostaServidor;
    }

    public void setRespostaServidor(String respostaServidor) {
        this.respostaServidor = respostaServidor;
    }

    private static String juntarJsonPf(String jsonUser, String jsonPf){
        String json1 = jsonUser.replace("}",",");
        String json2 = jsonPf.substring(UM,jsonPf.length());
        return json1 + json2;
   }

   public String limpandoJson(String json){
       Map<String,Object> jsonNodes = gson.fromJson(json, Map.class);
       String resultado = jsonNodes.get("token").toString();
       return resultado;
   }

   public void listarDocs(ConexaoServidor conexaoServidor, String token){
       conexaoServidor.execute(ROTAGERARLISTADOC,METODOGET,token);
   }

   public List docServerJsontoObject(String json){
       String novoJson = json.substring(OITO,json.length()-UM);
       ArrayList<Documento> listaDocs = gson.fromJson(novoJson, new TypeToken<List<Documento>>(){}.getType());
       return listaDocs;
   }

   public Documento docAppJsontoObject(String json){
        Documento documento = gson.fromJson(json, new TypeToken<Documento>(){}.getType());
       return documento;
   }
}