package com.ufrpe.autothentixclient.usuario.dominio;

public class PessoaJuridica {
    private String cnpj;
    private String razaoSocial;
    private String nomeFantasia;
    private String telefone;
    private String dataInic;
    private String codNatJuridica;

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getDataInic() {
        return dataInic;
    }

    public void setDataInic(String dataInic) {
        this.dataInic = dataInic;
    }

    public String getCodNatJuridica() {
        return codNatJuridica;
    }

    public void setCodNatJuridica(String codNatJuridica) {
        this.codNatJuridica = codNatJuridica;
    }
}