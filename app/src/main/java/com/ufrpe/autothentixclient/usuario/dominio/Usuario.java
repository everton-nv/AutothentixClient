package com.ufrpe.autothentixclient.usuario.dominio;

public class Usuario {

    private String email;
    private String senha;
    private long id;

    public String getEmail(){

        return this.email;
    }

    public void setEmail(String novoEmail){

        this.email = novoEmail;
    }

    public String getSenha(){
        return this.senha;
    }

    public void setSenha(String novaSenha){

        this.senha = novaSenha;
    }

    public long getId(){

        return this.id;
    }

    public void setId(long novoId){

        this.id = novoId;
    }
}
