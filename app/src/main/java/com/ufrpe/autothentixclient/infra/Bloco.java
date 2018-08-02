package com.ufrpe.autothentixclient.infra;

import java.util.Date;

public class Bloco {
    public String hashAtual;
    public String hashAnterior;
    private String data; //our data will be a simple message.
    private long timeStamp; //as number of milliseconds since 1/1/1970.
    private String acao;
    private int index;
    private int nonce;

    //Block Constructor.
    public Bloco(String data, String acao) {
        this.data = data;
        this.hashAnterior = "0";
        this.timeStamp = new Date().getTime();
        this.hashAtual = calcularHash();
        this.nonce = 0;
        this.acao = acao;
        this.index = 0;
    }

    public String calcularHash() {
        return StringUtil.applicarSha256(
                hashAnterior +
                        Long.toString(timeStamp) +
                        data
        );
    }

    public void minerarBloco(int difficulty) {
        String target = new String(new char[difficulty]).replace('\0', '0'); //Create a string with difficulty * "0"
        while(!hashAtual.substring( 0, difficulty).equals(target)) {
            nonce ++;
            hashAtual = calcularHash();
        }
    }

    @Override
    public String toString(){
        return  "Bloco " + Integer.toString(this.index + 1) +
                "\nHash: " + this.hashAtual + "\nHash do Anterior: " + this.hashAnterior +
                "\nDados: " + this.data;
    }

    public int getIndex() {
        return index;
    }

    public String getAcao() {
        return acao;
    }

    public String getData() {
        return data;
    }
}
