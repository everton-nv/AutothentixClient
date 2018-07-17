package com.ufrpe.autothentixclient.infra;

import android.text.TextUtils;
import android.util.Patterns;
import java.text.SimpleDateFormat;
import java.util.Date;


public class ValidacaoService {
    private static final String DATA_COMUM_CB   = "dd/MM/yyyy";
    private static final String DATA_COMUM_SB = "yyyyMMdd";

    private static final int TAMCPF = 11;
    private static final int TAMCNPJ = 14;
    private static final int TAMTEL = 11;
    private static final int ZERO = 0;
    private static final int DOIS = 2;
    private static final int QUATRO = 4;
    private static final int TAMANHO_DATA_CB = 10;
    private static final int TAMANHO_DATA_SB = 8;

    public boolean isCampoVazio(String valor) {

        return (TextUtils.isEmpty(valor) || valor.trim().isEmpty());
    }

    public boolean isEmailValido(String email) {

        return (!(isCampoVazio(email)) || Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    public boolean isTelefoneValido(String telefone) {

        return (!(isCampoVazio(telefone)) || telefone.length() == TAMTEL);
    }

    public boolean isCpfValido(String cpf) {

        return (!(isCampoVazio(cpf)) || cpf.length() == TAMCPF);
    }

    public boolean isSenhaValida(String senha) {

        if (isCampoVazio(senha)) {
            return false;
        } else {
            String rex = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,12})";
            boolean teste = (senha.matches(rex));
            return teste;
        }
    }

    public boolean isSenhaIgual(String senha, String confirmaSenha) {
        return senha.equals(confirmaSenha);
    }

    public boolean isCnpjValido(String cnpj) {

        return (!(isCampoVazio(cnpj)) || cnpj.length() == TAMCNPJ);
    }

    static boolean dataMenorOuIgualQueAtual(String data) {
        SimpleDateFormat dataFormatada = new SimpleDateFormat(DATA_COMUM_CB);
        dataFormatada.setLenient(false);
        //Testa no formato dd/MM/yyyy
        try {
            Date dataAtual = new Date();
            Date dataCliente = dataFormatada.parse(data);

            if (dataAtual.compareTo(dataCliente) >= ZERO) {
                return true;
            }
        } catch (Exception e) {
        }
        return false;
    }

    static boolean dataExiste(String data){
        SimpleDateFormat dataFormatada = new SimpleDateFormat (DATA_COMUM_CB);
        dataFormatada.setLenient (false);

        //Testa no formato dd/MM/yyyy
        try {
            dataFormatada.parse(data);
            return true;
        } catch (Exception e) {
        }

        dataFormatada = new SimpleDateFormat (DATA_COMUM_SB);
        dataFormatada.setLenient (false);

        //Testa no formato yyyyMMdd
        try {
            dataFormatada.parse(data);
            return true;
        } catch (Exception e) {
        }

        return false;
    }

    public boolean isDataValida(String data){

        return(dataExiste(data) && dataMenorOuIgualQueAtual(data) && (data.length() == TAMANHO_DATA_CB) || data.length() == TAMANHO_DATA_SB);
    }

    public String dataFormatoBanco(String data){
        String dataFormatada;
        if (data.contains("/")){
            String dataReversa = new StringBuilder(data).reverse().toString();
            dataFormatada = dataReversa.replace("/","-");

        }else{
            dataFormatada = data.substring(QUATRO,TAMANHO_DATA_SB) + "-" + data.substring(DOIS,QUATRO) + "-" + data.substring(ZERO,DOIS);
        }
        return dataFormatada;

    }
}