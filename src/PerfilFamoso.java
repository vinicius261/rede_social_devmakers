public class PerfilFamoso extends Perfil {
    private String famosoPor;

    public PerfilFamoso(String login, String nome, String senha, String famosoPor) {
        super(login, nome , senha);
        this.famosoPor = famosoPor;
    }

    public String getFamosoPor() {
        return famosoPor;
    }
}
