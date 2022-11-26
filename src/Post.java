import java.util.ArrayList;

public class Post {
    Perfil autor;
    String data;
    String hora;
    String texto;
    ArrayList<String> comentarios = new ArrayList<>();
    int curtidas;

    public Post(Perfil autor, String data, String hora, String texto) {
        this.autor = autor;
        this.data = data;
        this.hora = hora;
        this.texto = texto;
        autor.posts.add(this);
    }
}
