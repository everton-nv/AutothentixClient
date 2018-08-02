package com.ufrpe.autothentixclient.infra;

import java.util.ArrayList;

public class BlockChain {
    public static ArrayList<Bloco> blockchain = new ArrayList<Bloco>();
    public static int difficulty = 5;

    public boolean isChainValid() {
        Bloco currentBlock;
        Bloco previousBlock;
        String hashTarget = new String(new char[difficulty]).replace('\0', '0');

        //loop through blockchain to check hashes:
        for(int i=1; i < blockchain.size(); i++) {
            currentBlock = blockchain.get(i);
            previousBlock = blockchain.get(i-1);
            //compare registered hash and calculated hash:
            if(!currentBlock.hashAtual.equals(currentBlock.calcularHash()) ){
                return false;
            }
            //compare previous hash and registered previous hash
            if(!previousBlock.hashAtual.equals(currentBlock.hashAnterior) ) {
                return false;
            }
            //check if hash is solved
            if(!currentBlock.hashAtual.substring( 0, difficulty).equals(hashTarget)) {
                return false;
            }
        }
        return true;
    }

    public void addBloco(Bloco bloco){
        bloco.minerarBloco(difficulty);
        blockchain.add(bloco);
    }

    public ArrayList<Bloco> getBlockchain() {
        return blockchain;
    }

    @Override
    public String toString(){
        StringBuilder blockChainStr = new StringBuilder();
        for(Bloco bloco : this.getBlockchain()){
            blockChainStr.append(bloco.toString());
        }
        return blockChainStr.toString();
    }

    public static void setBlockchain(ArrayList<Bloco> blockchain) {
        BlockChain.blockchain = blockchain;
    }
}
