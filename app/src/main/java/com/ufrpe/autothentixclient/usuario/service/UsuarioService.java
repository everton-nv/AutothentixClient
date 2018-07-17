package com.ufrpe.autothentixclient.usuario.service;

import com.google.gson.Gson;
import com.ufrpe.autothentixclient.usuario.dominio.PessoaFisica;
import com.ufrpe.autothentixclient.usuario.dominio.PessoaJuridica;
import com.ufrpe.autothentixclient.usuario.dominio.Usuario;

import org.json.JSONException;

import java.io.IOException;


public class UsuarioService {

    private static final String URLBASE = "https://app-autothentix.herokuapp.com/";
    private static final String CADASTROPF = URLBASE + "registra/Pfisica";
    private static final String CADASTROPJ = URLBASE + "registra/Pjuridica";
    private static final String LOGAR = URLBASE + "auth/login";

    private static final int UM = 1;
    Gson gson = new Gson();

    public UsuarioService(){}

    public Usuario criarObjUsuario(String email, String senha){
        Usuario usuario = new Usuario();
        usuario.setEmail(email);
        usuario.setSenha(senha);
        return usuario;
    }

    public PessoaFisica criarObjPessoaFisica(String nome, String cpf, String telefone, String sexo, String dataNasc){
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

   public  String criarJsonUsuario(Usuario usuario){
         String jsonUsuario = gson.toJson(usuario);
         return jsonUsuario;
   }

   public String criarJsonPessoaFisica(PessoaFisica pessoaFisica){
       String jsonPessoaFisica = gson.toJson(pessoaFisica);
       return jsonPessoaFisica;
   }

   public String criarJsonPessoaJuridica(PessoaJuridica pessoaJuridica){
       String jsonPessoaJuridica = gson.toJson(pessoaJuridica);
       return jsonPessoaJuridica;
   }
    /*
   public String inserirCadastroPf(String email, String senha, String nome, String cpf, String telefone, String sexo, String dataNasc) throws IOException {
        ConexaoServidor conexaoServidor = new ConexaoServidor();
        String jsonUser = criarJsonUsuario(criarObjUsuario(email,senha));
        String jsonPf = criarJsonPessoaFisica(criarObjPessoaFisica(nome,cpf,telefone,sexo,dataNasc));
        String novoJson = juntarJsonPf(jsonUser,jsonPf);
        return conexaoServidor.inserirPessoaFisica(novoJson);
   }*/
    public String logar(String email, String senha) throws IOException, JSONException {
        //ConexaoServidor conexaoServidor = new ConexaoServidor();
        String jsonUser = criarJsonUsuario(criarObjUsuario(email,senha));
       return String.valueOf(new ConexaoServidor().execute(jsonUser,LOGAR));
        //return conexaoServidor.logarUsuario(jsonUser);
    }


    static String juntarJsonPf(String jsonUser, String jsonPf){
        String jason1 = jsonUser.replace("}",",");
        String jason2 = jsonPf.substring(UM,jsonPf.length());
        return jason1 + jason2;
   }
}
