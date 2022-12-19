package com.redesocial.perfis;

public class PerfilFamoso extends Perfil {
    private String famosoPor;

    public PerfilFamoso(String LOGIN, String NOME, String SENHA, String famosoPor) {
        super(LOGIN, NOME, SENHA);
        this.famosoPor = famosoPor;
    }

    public String getFamosoPor() {
        return famosoPor;
    }
}
