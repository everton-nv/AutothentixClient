package com.ufrpe.autothentixclient.infra;

import java.util.Date;

public class Bloco {
    public String prevHash;
    private String data; //our data will be a simple message.
    private long timeStamp; //as number of milliseconds since 1/1/1970.
    private String acao;
    private int index;
    private int nonce;
    public String hash;

    //Block Constructor.
    public Bloco(String data, String acao) {
        this.data = data;
        this.prevHash = "0";
        this.timeStamp = new Date().getTime();
        this.nonce = 0;
        this.acao = acao;
        this.index = 0;
        this.hash = calcularHash();
    }

    public String calcularHash() {
        return StringUtil.applicarSha256(
                prevHash +
                        Long.toString(timeStamp) +
                        data + nonce
        );
    }

    public void minerarBloco(int difficulty) {
        if(this.hash.equals("0")){
            this.hash = calcularHash();
        }

        String target = new String(new char[difficulty]).replace('\0', '0'); //Create a string with difficulty * "0"
        while(!hash.substring( 0, difficulty).equals(target)) {
            nonce ++;
            hash = calcularHash();
        }
    }

    @Override
    public String toString(){
        return  "Bloco " + Integer.toString(this.index + 1) +
                "\nAção: " + this.acao + "\nDados: " + this.data +
                "\nHash: " + this.hash + "\nHash do Anterior: " + this.prevHash + "\n\n" ;
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
