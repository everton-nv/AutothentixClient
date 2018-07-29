package com.ufrpe.autothentixclient.usuario.dominio;


public enum TagBundleEnum {
    URL_PREVIEW("url_preview"), AUTO_LOGIN("auto_login"), DOCS_LIST("documents_list"),
    DOC_NAME_TITLE("doc_name_title");

    private String value;

    TagBundleEnum(String value){ this.value = value;}

    public String getValue(){ return value;}
}
