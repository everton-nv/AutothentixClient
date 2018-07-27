package com.ufrpe.autothentixclient.infra;

import java.util.Date;

public class Bloco {
    public String hashAtual;
    public String hashAnterior;
    private String data; //our data will be a simple message.
    private long timeStamp; //as number of milliseconds since 1/1/1970.
    private int nonce;

    //Block Constructor.
    public Bloco(String data,String previousHash ) {
        this.data = data;
        this.hashAnterior = previousHash;
        this.timeStamp = new Date().getTime();
        this.hashAtual = calcularHash();
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

}
