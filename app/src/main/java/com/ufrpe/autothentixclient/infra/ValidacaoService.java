package com.ufrpe.autothentixclient.infra;

import android.text.TextUtils;
import android.util.Patterns;


public class ValidacaoService {

    public boolean isCampoVazio(String valor) {

        return (TextUtils.isEmpty(valor) || valor.trim().isEmpty());
    }

    public boolean isEmail(String email) {

        return (!(isCampoVazio(email)) || Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    public boolean isTelefone(String telefone) {

        return (!(isCampoVazio(telefone)) || telefone.length() == 11);
    }

    public boolean isCpf(String cpf) {

        return (!(isCampoVazio(cpf)) || cpf.length() == 11);
    }

    public boolean isSenhaValida(String senha) {

        if (isCampoVazio(senha)) {
            return false;
        } else {
            String rex = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z].{6,12}))";
            return (senha.matches(rex));
        }
    }

    public boolean isSenhaIgual(String senha, String confirmaSenha) {
        return senha.equals(confirmaSenha);
    }

    public boolean isCnpj(String cnpj) {

        return (!(isCampoVazio(cnpj)) || cnpj.length() == 14);
    }
}