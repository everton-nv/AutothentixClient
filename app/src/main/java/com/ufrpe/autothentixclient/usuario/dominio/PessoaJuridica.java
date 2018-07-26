package com.ufrpe.autothentixclient.usuario.dominio;

public class PessoaJuridica {

    public PessoaJuridica(String razaoSocial, String cnpj, String telefone){
        setRazaoSocial(razaoSocial);
        setCnpj(cnpj);
        setTelefone(telefone);
    }

    public PessoaJuridica(){}

    private String cnpj;
    private String phone;

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}