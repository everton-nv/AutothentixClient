package com.ufrpe.autothentixclient.documento;

import com.ufrpe.autothentixclient.usuario.dominio.PessoaFisica;
import com.ufrpe.autothentixclient.usuario.dominio.PessoaJuridica;
import com.ufrpe.autothentixclient.usuario.dominio.Usuario;

public class Sessao {
    private static Sessao instancia = new Sessao();
    private PessoaFisica pessoaFisicaLogada = null;
    private PessoaJuridica pessoaJuridicaLogada = null;
    private Usuario usuarioLogado = null;

    public  static Sessao getInstancia(){

        return instancia;
    }

    public PessoaFisica getPessoaFisicaLogada(){

        return pessoaFisicaLogada;
    }

    public void setPessoaFisicaLogada(PessoaFisica novaPessoaFisica){

        this.pessoaFisicaLogada = novaPessoaFisica;
    }

    public PessoaJuridica getPessoaJuridicaLogada(){

        return pessoaJuridicaLogada;
    }

    public void setPessoaJuridicaLogada(PessoaJuridica novaPessoaJuridica){

        this.pessoaJuridicaLogada = novaPessoaJuridica;
    }

    public Usuario getUsuarioLogado(){

        return usuarioLogado;
    }

    public void setUsuarioLogado(Usuario novoUsuarioLogado){

        this.usuarioLogado = novoUsuarioLogado;
    }

    public void invalidarSessao(){
        pessoaJuridicaLogada = null;
        pessoaFisicaLogada = null;
        usuarioLogado = null;
    }
}
