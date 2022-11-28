import java.util.ArrayList;
import java.util.Date;

public class Post {
    Perfil autor;
    Date data;
    String texto;
    ArrayList<String> comentarios = new ArrayList<>();
    int curtidas;

    public Post(Perfil autor, Date data, String texto) {
        this.autor = autor;
        this.data = data;
        this.texto = texto;
        autor.posts.add(this);
    }
}
