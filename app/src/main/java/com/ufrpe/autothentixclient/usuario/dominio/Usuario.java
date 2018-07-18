package com.ufrpe.autothentixclient.usuario.dominio;

public class Usuario {

    private String email;
    private String password;

    public String getEmail(){

        return this.email;
    }

    public void setEmail(String novoEmail){

        this.email = novoEmail;
    }

    public String getSenha(){
        return this.password;
    }

    public void setSenha(String novaSenha){

        this.password = novaSenha;
    }
}