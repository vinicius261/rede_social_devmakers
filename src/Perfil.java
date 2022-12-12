import java.util.ArrayList;
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

    public String getSENHA() {
        return this.SENHA;
    }

    public String  getLOGIN() {
        return this.LOGIN;
    }

    public String getNome() {
        return NOME;
    }

    public List<Post> getPosts() {
        return this.posts;
    }

    public void setPosts(Post post) {
        this.posts.add(post);
    }

    public List<Perfil> getSeguidoPor() {
        return this.seguidoPor;
    }

    public void setSeguidoPor(Perfil seguidoPor) {
        this.seguidoPor.add(seguidoPor);
    }

    public List<Perfil> getSeguindo() {
        return this.seguindo;
    }

    public void setSeguindo(Perfil seguindo) {
        this.seguindo.add(seguindo);
    }
}
