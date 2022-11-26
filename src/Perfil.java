import java.util.ArrayList;

public class Perfil {
    String nome;
    String login = "novo";
    String senha;
    ArrayList<Post> posts = new ArrayList<>();
    ArrayList<Perfil> seguidoPor = new ArrayList();
    ArrayList<Perfil> segue = new ArrayList();

    public Perfil(String login, String nome, String senha) {

        this.login = login;
        this.nome = nome;
        this.senha = senha;

    }
}
