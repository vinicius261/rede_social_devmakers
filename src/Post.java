import java.util.ArrayList;
import java.util.Date;

public class Post {
    private final Perfil AUTOR;
    private final Date DATA;
    private final String TEXTO;
    ArrayList<String> comentarios = new ArrayList<>();
    private int curtidas;

    public Post(Perfil autor, Date data, String texto) {
        this.AUTOR = autor;
        this.DATA = data;
        this.TEXTO = texto;
        autor.setPosts(this);
    }

    public Perfil getAUTOR() {
        return AUTOR;
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

    public void setCurtidas() {
        this.curtidas++;
    }
}
