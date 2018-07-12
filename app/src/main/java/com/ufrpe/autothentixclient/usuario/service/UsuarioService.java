package com.ufrpe.autothentixclient.usuario.service;

import com.google.gson.Gson;
import com.ufrpe.autothentixclient.usuario.dominio.PessoaFisica;
import com.ufrpe.autothentixclient.usuario.dominio.PessoaJuridica;
import com.ufrpe.autothentixclient.usuario.dominio.Usuario;


public class UsuarioService {
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
        pessoaFisica.setNome(nome);
        pessoaFisica.setCpf(cpf);
        pessoaFisica.setTelefone(telefone);
        pessoaFisica.setSexo(sexo);
        pessoaFisica.setDataNasc(dataNasc);
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

   public String inserirCadastroPf(String email, String senha, String nome, String cpf, String telefone, String sexo, String dataNasc){
        ConexaoServidor conexaoServidor = new ConexaoServidor();
        String jsonUser = criarJsonUsuario(criarObjUsuario(email,senha));
        String jsonPf = criarJsonPessoaFisica(criarObjPessoaFisica(nome,cpf,telefone,sexo,dataNasc));
        String novoJson = juntarJsonPf(jsonUser,jsonPf);
        return conexaoServidor.inserirPessoaFisica(novoJson);
   }

   static String juntarJsonPf(String jsonUser, String jsonPf){
        String jason1 = jsonUser.replace("}",",");
        String jason2 = jsonPf.substring(UM,jsonPf.length());
        return jason1 + jason2;
   }
}
