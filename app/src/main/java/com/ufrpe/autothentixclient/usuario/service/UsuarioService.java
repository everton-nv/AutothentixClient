package com.ufrpe.autothentixclient.usuario.service;

import com.google.gson.Gson;
import com.ufrpe.autothentixclient.usuario.dominio.Documento;
import com.ufrpe.autothentixclient.usuario.dominio.PessoaFisica;
import com.ufrpe.autothentixclient.usuario.dominio.PessoaJuridica;
import com.ufrpe.autothentixclient.usuario.dominio.Usuario;


public class UsuarioService {

    private static final String URLBASE = "https://app-autothentix.herokuapp.com/";
    private static final String ROTACADASTROPF = URLBASE + "registra/pfisica";
    private static final String ROTACADASTROPJ = URLBASE + "registra/pjurudica";
    private static final String ROTALOGAR = URLBASE + "auth/login";
    private static  final String ROTAGERARDOC = URLBASE + "geradocumento/html";
    private static final String METODOGET = "GET";
    private static final String METODOPOST = "POST";
    private static final int UM = 1;
    private Gson gson = new Gson();
    private String respostaServidor;

    public UsuarioService(){

    }

    private Usuario criarObjUsuario(String email, String senha){
        Usuario usuario = new Usuario();
        usuario.setEmail(email);
        usuario.setSenha(senha);
        return usuario;
    }

    private PessoaFisica criarObjPessoaFisica(String nome, String cpf, String telefone, String sexo, String dataNasc){
        PessoaFisica pessoaFisica = new PessoaFisica();
        pessoaFisica.setName(nome);
        pessoaFisica.setCpf(cpf);
        pessoaFisica.setPhone(telefone);
        pessoaFisica.setSex(sexo);
        pessoaFisica.setBirthdate(dataNasc);
        return pessoaFisica;
    }

    public PessoaJuridica criarObjPessoaJuridica(String razaoSocial, String cnpj, String telefone){
        PessoaJuridica pessoaJuridica = new PessoaJuridica();
        pessoaJuridica.setRazaoSocial(razaoSocial);
        pessoaJuridica.setCnpj(cnpj);
        pessoaJuridica.setTelefone(telefone);
        return pessoaJuridica;
    }

    public Documento criarObjDocumento(String nomeContratante, String cpfContratante , String rgContratante, String nacContratante, String nomeEmpresa ,
                                       String nomeContratado, String cnpjContratado, String nacContratado, String cpfContratado, String profContratado,
                                       String valorNumerico, String valorExtenso, String cidade, String estado, String dataAtual, String nomeDocumento)
    {   Documento documento = new Documento();
        documento.setContratantenome(nomeContratante);
        documento.setContratantecpf(cpfContratante);
        documento.setContratanterg(rgContratante);
        documento.setContratatantenacionalidade(nacContratante);
        documento.setContratadanomeEmpresa(nomeEmpresa);
        documento.setContratadanome(nomeContratado);
        documento.setContratadacnpj(cnpjContratado);
        documento.setContratadanacionalidade(nacContratado);
        documento.setContratadacpf(cpfContratado);
        documento.setContratadaprofissao(profContratado);
        documento.setValor(valorNumerico);
        documento.setValorExtenso(valorExtenso);
        documento.setCidade(cidade);
        documento.setEstado(estado);
        documento.setDatatual(dataAtual);
        documento.setNomedoc(nomeDocumento);
        return  documento;
    }

    private String criarJsonObjeto(Object objeto){
        return gson.toJson(objeto);
    }

   public void inserirCadastroPf(String email, String senha, String nome, String cpf, String telefone, String sexo, String dataNasc, ConexaoServidor conexaoServidor){
        String jsonUser = criarJsonObjeto(criarObjUsuario(email,senha));
        String jsonPf = criarJsonObjeto(criarObjPessoaFisica(nome,cpf,telefone,sexo,dataNasc));
        String novoJson = juntarJsonPf(jsonUser,jsonPf);
        conexaoServidor.execute(novoJson, ROTACADASTROPF, METODOPOST);
   }
   public void inserirCadastroPj(String razaoSocial, String cnpj, String email, String telefone, String senha, ConexaoServidor conexaoServidor){
        String jsonUser = criarJsonObjeto(criarObjUsuario(email, senha));
        String jsonPj = criarJsonObjeto(criarObjPessoaJuridica(razaoSocial, cnpj, telefone));
        String novoJson = juntarJsonPf(jsonUser, jsonPj);
        conexaoServidor.execute(novoJson, ROTACADASTROPJ, METODOPOST);
   }

   public void inserirDocumento(String nomeContratante, String cpfContratante , String rgContratante, String nacContratante, String nomeEmpresa ,
                                String nomeContratado, String cnpjContratado, String nacContratado, String cpfContratado, String profContratado,
                                String valorNumerico, String valorExtenso, String cidade, String estado, String dataAtual, String nomeDocumento, ConexaoServidor conexaoServidor ) {
        String jsonDoc = criarJsonObjeto(criarObjDocumento(nomeContratante , cpfContratante , rgContratante, nacContratante,  nomeEmpresa , nomeContratado,
                         cnpjContratado,nacContratado,cpfContratado,  profContratado, valorNumerico, valorExtenso, cidade, estado, dataAtual, nomeDocumento));
        conexaoServidor.execute(jsonDoc, ROTAGERARDOC, METODOPOST);
   }


    public void logar(String email, String senha, ConexaoServidor conexaoServidor){
        String jsonUser = criarJsonObjeto(criarObjUsuario(email,senha));
        conexaoServidor.execute(jsonUser,ROTALOGAR, METODOPOST);
    }

    public String getRespostaServidor() {
        return respostaServidor;
    }
    public void setRespostaServidor(String respostaServidor) {
        this.respostaServidor = respostaServidor;
    }

    private static String juntarJsonPf(String jsonUser, String jsonPf){
        String jason1 = jsonUser.replace("}",",");
        String jason2 = jsonPf.substring(UM,jsonPf.length());
        return jason1 + jason2;
   }
}