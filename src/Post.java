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

    public List<String> getComentarios(){
        return this.comentarios;
    }

    public void setComentarios(Perfil perfilLogado, SimpleDateFormat dataFormatada, String comentario){
        this.comentarios.add(perfilLogado.getNome() + " - " + dataFormatada.format(RedeSocial.criaData()) + " - " + comentario);
    }
}
