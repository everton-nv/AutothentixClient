package com.ufrpe.autothentixclient.usuario.service;

import com.google.gson.Gson;
import com.ufrpe.autothentixclient.usuario.dominio.PessoaFisica;
import com.ufrpe.autothentixclient.usuario.dominio.PessoaJuridica;
import com.ufrpe.autothentixclient.usuario.dominio.Usuario;


public class UsuarioService {

    private static final String URLBASE = "https://app-autothentix.herokuapp.com/";
    private static final String ROTACADASTROPF = URLBASE + "registra/Pfisica";
    private static final String ROTACADASTROPJ = URLBASE + "registra/Pjuridica";
    private static final String ROTALOGAR = URLBASE + "auth/login";

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

    private String criarJsonObjeto(Object objeto){
        return gson.toJson(objeto);
    }

   public void inserirCadastroPf(String email, String senha, String nome, String cpf, String telefone, String sexo, String dataNasc, ConexaoServidor conexaoServidor){
        String jsonUser = criarJsonObjeto(criarObjUsuario(email,senha));
        String jsonPf = criarJsonObjeto(criarObjPessoaFisica(nome,cpf,telefone,sexo,dataNasc));
        String novoJson = juntarJsonPf(jsonUser,jsonPf);
        conexaoServidor.execute(novoJson, ROTACADASTROPF);
   }
   public void inserirCadastroPj(String razaoSocial, String cnpj, String email, String telefone, String senha, ConexaoServidor conexaoServidor){
        String jsonUser = criarJsonObjeto(criarObjUsuario(email, senha));
        String jsonPj = criarJsonObjeto(criarObjPessoaJuridica(razaoSocial, cnpj, telefone));
        String novoJson = juntarJsonPf(jsonUser, jsonPj);
        conexaoServidor.execute(novoJson, ROTACADASTROPJ);
   }

    public void logar(String email, String senha, ConexaoServidor conexaoServidor){
        String jsonUser = criarJsonObjeto(criarObjUsuario(email,senha));
        conexaoServidor.execute(jsonUser,ROTALOGAR);
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