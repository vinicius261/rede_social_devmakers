public class PerfilFamoso extends Perfil{
    String famosoPor;

    public PerfilFamoso(String login, String nome, String senha, String famosoPor) {
        super(login, nome + " *Perfil Verificado*", senha);
        this.famosoPor = famosoPor;
    }
}
