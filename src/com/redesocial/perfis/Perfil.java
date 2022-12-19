package com.redesocial.perfis;

import com.redesocial.post.Post;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Perfil {
    private final String NOME;
    private final String LOGIN;
    private final String SENHA;

    private List posts;
    private List seguidoPor;
    private List seguindo;

    public Perfil(String login, String nome, String senha) {
        this.LOGIN = login;
        this.NOME = nome;
        this.SENHA = senha;
        this.posts = new ArrayList<Post>();
        this.seguidoPor = new ArrayList<>();
        this.seguindo = new ArrayList<>();
    }

    public void fazerPost(Perfil usuarioLogado, Date data, String texto) {
        Post novoPost = new Post(this, data, texto);
        usuarioLogado.adicionaPost(novoPost);
        System.out.println("Publicado!");
        System.out.println();
    }

    public void curtirPost(Post post) {
        post.adicionarCurtida();
        System.out.println("Curtido!");
        System.out.println();
    }

    public void comentarPost(Post post, String comentario, Perfil perfilQueEstaLogado, SimpleDateFormat dataFormatada) {
        post.adicionarComentario(perfilQueEstaLogado, dataFormatada, comentario);
        System.out.println("Comentário feito!!");
        System.out.println();
    }

    public void adicionaUsuario(Perfil perfilVisitado, Perfil perfilQueEstaLogado) {
        if (perfilVisitado != perfilQueEstaLogado && !(perfilQueEstaLogado.getSeguindo().contains(perfilVisitado))) {
            perfilQueEstaLogado.adicionaSeguido(perfilVisitado);
            perfilVisitado.adicionaSeguidor(perfilQueEstaLogado);
            System.out.println(perfilVisitado.getNOME() + " foi adicionado!");
        } else if (perfilVisitado == perfilQueEstaLogado) {
            System.out.println("Você está na sua página, navegue por outras para adicionar amigos.");
        } else {
            System.out.println("Vocês já são amigos.");
        }

        System.out.println();
    }

    public void adicionaPost(Post post) {
        this.posts.add(post);
    }

    public void adicionaSeguidor(Perfil seguidoPor) {
        this.seguidoPor.add(seguidoPor);
    }

    public void adicionaSeguido(Perfil seguindo) {
        this.seguindo.add(seguindo);
    }

    public String getSENHA() {
        return this.SENHA;
    }

    public boolean verificaSenha(String senha) {
        if (senha.equals(this.SENHA)) {
            return true;
        } else {
            return false;
        }
    }

    public String getLOGIN() {
        return this.LOGIN;
    }

    public String getNOME() {
        return NOME;
    }

    public List<Post> getPosts() {
        return this.posts;
    }

    public List<Perfil> getSeguidoPor() {
        return this.seguidoPor;
    }

    public List<Perfil> getSeguindo() {
        return this.seguindo;
    }
}
