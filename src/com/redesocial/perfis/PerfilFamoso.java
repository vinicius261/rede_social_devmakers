package com.redesocial.perfis;

public class PerfilFamoso extends Perfil {
    private String famosoPor;

    public PerfilFamoso(String LOGIN, String nome, String SENHA, String famosoPor) {
        super(LOGIN, nome , SENHA);
        this.famosoPor = famosoPor;
    }

    public String getFamosoPor() {
        return famosoPor;
    }

    public void setFamosoPor(String famosoPor) {
        this.famosoPor = famosoPor;
    }
}
