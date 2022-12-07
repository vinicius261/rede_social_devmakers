import java.util.ArrayList;

public class Perfil {
    private final String NOME;
    private final String LOGIN;
    private final String SENHA;

    private ArrayList<Post> posts;
    private ArrayList<Perfil> seguidoPor;
    private ArrayList<Perfil> seguindo;

    public Perfil(String login, String nome, String senha) {
        this.LOGIN = login;
        this.NOME = nome;
        this.SENHA = senha;
        this.posts = new ArrayList<>();
        this.seguidoPor = new ArrayList<>();
        this.seguindo = new ArrayList<>();
    }

    public String getSENHA() {
        return this.SENHA;
    }

    public String  getLOGIN() {
        return this.LOGIN;
    }

    public String getNome() {
        return NOME;
    }

    public ArrayList<Post> getPosts() {
        return posts;
    }

    public void setPosts(Post post) {
        this.posts.add(post);
    }

    public ArrayList<Perfil> getSeguidoPor() {
        return seguidoPor;
    }

    public void setSeguidoPor(Perfil seguidoPor) {
        this.seguidoPor.add(seguidoPor);
    }

    public ArrayList<Perfil> getSeguindo() {
        return seguindo;
    }

    public void setSeguindo(Perfil seguindo) {
        this.seguindo.add(seguindo);
    }
}
