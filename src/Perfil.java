import java.util.ArrayList;

public class Perfil {
    private final String NOME;
    private final String LOGIN;
    private final String SENHA;

    private ArrayList<Post> posts = new ArrayList<>();
    private ArrayList<Perfil> seguidoPor = new ArrayList<>();
    private ArrayList<Perfil> segue = new ArrayList<>();

    public Perfil(String login, String nome, String senha) {
        this.LOGIN = login;
        this.NOME = nome;
        this.SENHA = senha;
    }

    public boolean getSENHA(String senha) {
        return senha.equals(this.SENHA);
    }

    public boolean getLOGIN(String login) {
        return login.equalsIgnoreCase(this.LOGIN);
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

    public ArrayList<Perfil> getSegue() {
        return segue;
    }

    public void setSegue(Perfil segue) {
        this.segue.add(segue);
    }
}
