package com.redesocial.post;

import com.redesocial.perfis.Perfil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Post {
    private final Perfil AUTOR;
    private final Date DATA;
    private final String TEXTO;
    private List<String> comentarios;
    private int curtidas;

    public Post(Perfil autor, Date data, String texto) {
        this.AUTOR = autor;
        this.DATA = data;
        this.TEXTO = texto;
        this.comentarios = new ArrayList<String>();
    }

    public void adicionarCurtida() {
        this.curtidas++;
    }

    public void adicionarComentario(Perfil perfilLogado, SimpleDateFormat formatacaoDaData, String comentario) {
        Date data = new Date(System.currentTimeMillis());
        this.comentarios.add(perfilLogado.getNOME() + " - " + formatacaoDaData.format(data) + " - " + comentario);
    }

    public Date getDATA() {
        return DATA;
    }

    public String getTEXTO() {
        return TEXTO;
    }

    public int getCurtidas() {
        return curtidas;
    }

    public List<String> getComentarios() {
        return this.comentarios;
    }

}
