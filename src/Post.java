import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Post {
    private final Perfil AUTOR;
    private final Date DATA;
    private final String TEXTO;
    private ArrayList<String> comentarios;
    private int curtidas;

    protected Post(Perfil autor, Date data, String texto) {
        this.AUTOR = autor;
        this.DATA = data;
        this.TEXTO = texto;
        this.comentarios = new ArrayList<String>();
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

    public ArrayList<String> getComentarios(){
        return this.comentarios;
    }

    public void setComentarios(Perfil perfilLogado, SimpleDateFormat dataFormatada, String comentario){
        this.comentarios.add(perfilLogado.getNome() + " - " + dataFormatada.format(RedeSocial.criaData()) + " - " + comentario);
    }
}
