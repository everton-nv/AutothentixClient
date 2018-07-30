package com.ufrpe.autothentixclient.usuario.dominio;

public class Documento {

    public Documento(String nomeContratante, String cpfContratante , String rgContratante, String nacContratante, String nomeEmpresa ,
                     String nomeContratado, String cnpjContratado, String nacContratado, String cpfContratado, String profContratado,
                     String valorNumerico, String valorExtenso, String cidade, String dataAtual, String nomeDocumento){

        setContratantenome(nomeContratante);
        setContratantecpf(cpfContratante);
        setContratanterg(rgContratante);
        setContratantenacionalidade(nacContratante);
        setContratadanomeEmpresa(nomeEmpresa);
        setContratadanome(nomeContratado);
        setContratadacnpj(cnpjContratado);
        setContratadanacionalidade(nacContratado);
        setContratadacpf(cpfContratado);
        setContratadaprofissao(profContratado);
        setValor(valorNumerico);
        setValorExtenso(valorExtenso);
        setCidade(cidade);
        setDatatual(dataAtual);
        setNomedoc(nomeDocumento);
    }
    public Documento(String nomeContratante, String cpfContratante , String rgContratante, String nacContratante, String nomeEmpresa ,
                     String nomeContratado, String cnpjContratado, String nacContratado, String cpfContratado, String profContratado,
                     String valorNumerico, String valorExtenso, String cidade, String dataAtual, String nomeDocumento, String id){

        setContratantenome(nomeContratante);
        setContratantecpf(cpfContratante);
        setContratanterg(rgContratante);
        setContratantenacionalidade(nacContratante);
        setContratadanomeEmpresa(nomeEmpresa);
        setContratadanome(nomeContratado);
        setContratadacnpj(cnpjContratado);
        setContratadanacionalidade(nacContratado);
        setContratadacpf(cpfContratado);
        setContratadaprofissao(profContratado);
        setValor(valorNumerico);
        setValorExtenso(valorExtenso);
        setCidade(cidade);
        setDatatual(dataAtual);
        setNomedoc(nomeDocumento);
        setId(id);
    }

    public Documento(){}

    private String id;
    private String nomedoc;
    private String contratantenome;
    private String contratantenacionalidade;
    private String contratanterg;
    private String contratantecpf;
    private String contratadanomeEmpresa;
    private String contratadacnpj;
    private String contratadanome;
    private String contratadanacionalidade;
    private String contratadaprofissao;
    private String contratadacpf;
    private String valor;
    private String valorExtenso;
    private String cidade;
    private String datatual;

    public String getNomedoc() {
        return nomedoc;
    }

    public void setNomedoc(String nomedoc) {
        this.nomedoc = nomedoc;
    }

    public String getContratantenome() {
        return contratantenome;
    }

    public void setContratantenome(String contratantenome) {
        this.contratantenome = contratantenome;
    }

    public String getContratantenacionalidade() {
        return contratantenacionalidade;
    }

    public void setContratantenacionalidade(String contratantenacionalidade) {
        this.contratantenacionalidade = contratantenacionalidade;
    }

    public String getContratanterg() {
        return contratanterg;
    }

    public void setContratanterg(String contratanterg) {
        this.contratanterg = contratanterg;
    }

    public String getContratantecpf() {
        return contratantecpf;
    }

    public void setContratantecpf(String contratantecpf) {
        this.contratantecpf = contratantecpf;
    }

    public String getContratadanomeEmpresa() {
        return contratadanomeEmpresa;
    }

    public void setContratadanomeEmpresa(String contratadanomeEmpresa) {
        this.contratadanomeEmpresa = contratadanomeEmpresa;
    }

    public String getContratadacnpj() {
        return contratadacnpj;
    }

    public void setContratadacnpj(String contratadacnpj) {
        this.contratadacnpj = contratadacnpj;
    }

    public String getContratadanome() {
        return contratadanome;
    }

    public void setContratadanome(String contratadanome) {
        this.contratadanome = contratadanome;
    }

    public String getContratadanacionalidade() {
        return contratadanacionalidade;
    }

    public void setContratadanacionalidade(String contratadanacionalidade) {
        this.contratadanacionalidade = contratadanacionalidade;
    }

    public String getContratadaprofissao() {
        return contratadaprofissao;
    }

    public void setContratadaprofissao(String contratadaprofissao) {
        this.contratadaprofissao = contratadaprofissao;
    }

    public String getContratadacpf() {
        return contratadacpf;
    }

    public void setContratadacpf(String contratadacpf) {
        this.contratadacpf = contratadacpf;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getValorExtenso() {
        return valorExtenso;
    }

    public void setValorExtenso(String valorExtenso) {
        this.valorExtenso = valorExtenso;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getDatatual() {
        return datatual;
    }

    public void setDatatual(String datatual) {
        this.datatual = datatual;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
