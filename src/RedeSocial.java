
import excecoes.InvalidPasswordException;
import excecoes.UserNotFoundException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

class RedeSocial {
    static List<Perfil> usuarios = new ArrayList<>();
    static Perfil perfilQueEstaLogado;
    static SimpleDateFormat dataFormatada = new SimpleDateFormat("dd/MM/yyyy 'às' HH:mm");
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Seja bem vindo a Rede Social!");
        System.out.println();

        menuInicial();

        sc.close();
    }

    public static void menuInicial() {
        System.out.println("================================================================");
        System.out.println("Já tem uma conta?\n\n1 - Sim (Faça login)\n2 - Não (Cadastrar)\n\n0 - Sair (Encerrar programa)");
        System.out.println("================================================================");
        int opcao = EntradasEValidacoes.respostaLogin();

        if (usuarios.size() >= 1 && opcao == 1) {
            criaPerfisAutomaticamente();
        }

        if (opcao == 1) {
            try {
                fazerLogin();
            } catch (UserNotFoundException | InvalidPasswordException e) {
                System.out.println(e.getMessage());
                System.out.println();
                menuInicial();
            }
        } else if (opcao == 2) {
            cadastrarUsuario();
        } else {
            System.out.println("Programa encerrado, até logo!");
        }
    }

    public static void menuDoUsuario(Perfil usuarioLogado) {
        System.out.println("================================================================");
        System.out.println("Bem vindo, " + usuarioLogado.getNome() + "!");
        System.out.println();

        System.out.println("O que deseja fazer?\n\n1 - Fazer postagem  2 - Ir para sua timeline" +
                "\n3 - Procurar usuários  4 - Ver seu perfil \n0 - Sair\n\n\n" +
                "Se você é famoso digite '5' para fazer a verificação de seu perfil");
        System.out.println("================================================================");

        int opcao = EntradasEValidacoes.respostaMenuUsuario();

        if (opcao == 1) {
            fazerPostagem(usuarioLogado);
        } else if (opcao == 2) {
            exibeTimeline(usuarioLogado);
        } else if (opcao == 3) {
            pesquisaUsuarios();
        } else if (opcao == 4) {
            exibePerfil(usuarioLogado);
        } else if (opcao == 5) {
            criaPerfilFamoso(usuarioLogado);
        } else {
            menuInicial();
        }
    }

    public static void exibeTimeline(Perfil usuarioLogado) {
        System.out.println("================================================================");
        System.out.println("Suas publicações:");
        System.out.println();

        if (usuarioLogado.getPosts().isEmpty()) {
            System.out.println("Faça sua primeira publicação.");
        } else {
            for (Post post : usuarioLogado.getPosts()) {
                System.out.println(dataFormatada.format(post.getDATA()) + " - " + "Curtidas: " +
                        post.getCurtidas() + " Comentários: " + post.getComentarios().size() + "\n'" + post.getTEXTO() + "'");
                System.out.println();
            }
            System.out.println();
            System.out.println("================================================================");
        }
        System.out.println();
        menuDoUsuario(usuarioLogado);
    }

    public static void fazerPostagem(Perfil usuarioLogado) {
        Date data = criaData();

        System.out.println("No que esta pensando? ");
        String texto = sc.nextLine();

        Post novoPost = new Post(usuarioLogado, data, texto);
        usuarioLogado.setPosts(novoPost);
        System.out.println("Publicado!");
        System.out.println();

        menuDoUsuario(usuarioLogado);
    }

    public static void menuDePerfis(Perfil perfilVisitado) {
        System.out.println("============================================");
        System.out.println("Para ver em detalhes desse perfil escolha uma opção: ");
        System.out.println("1 - Postagens   2 - Seguidores\n3 - Seguidos    4 - Adicionar esse perfil\n5 - Voltar ao menu do usuário  6 - Voltar para o perfil de " + perfilVisitado.getNome());
        System.out.println("============================================");
        int opcao = EntradasEValidacoes.respostaMenu();

        if (opcao == 1) {
            exibePostagens(perfilVisitado);
        } else if (opcao == 2) {
            exibeSeguridores(perfilVisitado);
        } else if (opcao == 3) {
            exibeSeguindo(perfilVisitado);
        } else if (opcao == 4) {
            adicionaUsuario(perfilVisitado);
        } else if (opcao == 5) {
            menuDoUsuario(perfilQueEstaLogado);
        } else {
            menuDoUsuario(perfilVisitado);
        }
    }

    public static void exibePerfil(Perfil perfilVisitado) {
        System.out.println("============================================");
        if (perfilVisitado instanceof PerfilFamoso){
            System.out.println("Perfil de " + perfilVisitado.getNome() + " *Perfil Verificado*");
        }else {
            System.out.println("Perfil de " + perfilVisitado.getNome());
        }
        System.out.println();

        if (perfilVisitado instanceof PerfilFamoso){
            System.out.println("Esse perfil é famoso por: " + ((PerfilFamoso) perfilVisitado).getFamosoPor());
            System.out.println();
        }

        System.out.println("Postagens de " + perfilVisitado.getNome());
        System.out.println();
        if (perfilVisitado.getPosts().size() > 0) {
            for (Post post : perfilVisitado.getPosts()) {
                System.out.println("        " + dataFormatada.format(post.getDATA()) + " - " + post.getTEXTO());
                System.out.println();
            }
        } else {
            System.out.println("        O usuário ainda não tem postagens.");
            System.out.println();
        }
        System.out.println("Seguidores de " + perfilVisitado.getNome());
        System.out.println();
        if (perfilVisitado.getSeguidoPor().size() > 0) {
            for (Perfil seguidor : perfilVisitado.getSeguidoPor()) {
                System.out.print("        " + seguidor.getNome() + " / ");
            }
        } else {
            System.out.println("        O usuário ainda não tem seguidores.");
        }
        System.out.println();

        System.out.println(perfilVisitado.getNome() + " segue:");
        System.out.println();

        if (perfilVisitado.getSeguindo().size() > 0) {
            for (Perfil segue : perfilVisitado.getSeguindo()) {
                System.out.print("        " + segue.getNome() + " / ");
            }
        } else {
            System.out.println("        O usuário ainda não segue ninguém.");
        }
        System.out.println();

        System.out.println("============================================");
        menuDePerfis(perfilVisitado);
    }

    public static void fazerLogin() {
        System.out.println("Insira seu login: ");
        String login = sc.nextLine();
        if (usuarios.isEmpty()) {
            System.out.println("Ainda não existem usuários cadastrados.");
            System.out.println();
            menuInicial();
        } else {
            boolean usuarioEncontrado = false;
            for (int i = 0; i < usuarios.size(); i++) {
                if (usuarios.get(i).getLOGIN().equalsIgnoreCase(login)) {
                    System.out.println("Olá " + usuarios.get(i).getNome() + " insira sua senha: ");
                    String senha = sc.nextLine();
                    if (usuarios.get(i).getSENHA().equals(senha)) {
                        System.out.println("Login efetuado com sucesso.");
                        System.out.println();
                        perfilQueEstaLogado = usuarios.get(i);
                        menuDoUsuario(usuarios.get(i));
                        usuarioEncontrado = true;
                        break;
                    } else {
                        System.out.println("Senha incorreta, tente novamente");
                        senha = sc.nextLine();
                        if (usuarios.get(i).getSENHA().equals(senha)) {
                            System.out.println("Login efetuado com sucesso.");
                            perfilQueEstaLogado = usuarios.get(i);
                            menuDoUsuario(usuarios.get(i));
                        } else {
                            throw new InvalidPasswordException("Senha incorreta.");
                        }
                    }
                }
            }
            if (usuarioEncontrado == false) {
                throw new UserNotFoundException("Usuário não encontrado.");
            }
        }
    }

    public static void cadastrarUsuario() {
        System.out.println("Insira seu email: ");
        String login = EntradasEValidacoes.recebeEmail();
        System.out.println("Insira seu nome: ");
        String nome = EntradasEValidacoes.recebeNome();
        System.out.println("Insira uma senha com  6 ou mais caracteres: ");
        String senha = EntradasEValidacoes.criaSenha();
        if (senha.equals("ERRO")) {
            System.out.println("A senha digitada não é válida.");
            RedeSocial.menuInicial();
        } else if (senha.equals("ERRO2")) {
            System.out.println("As senhas são diferentes, usuário não cadastrado.");
            System.out.println();
            RedeSocial.menuInicial();
        } else {
            System.out.println();
            System.out.println("Cadastro realizado com sucesso!");

            Perfil novo = new Perfil(login, nome, senha);
            RedeSocial.usuarios.add(novo);
            System.out.println();
            menuInicial();
        }
    }

    public static void exibePostagens(Perfil perfilVisitado) {
      exibePostagens(perfilVisitado,0);
    }

    public static void exibePostagens(Perfil perfilVisitado, int indexPostagem){
        System.out.println("============================================");
        System.out.println("Postagens de " + perfilVisitado.getNome());
        System.out.println();

        for (int i = indexPostagem; i < perfilVisitado.getPosts().size(); i++ ) {
            Post post = perfilVisitado.getPosts().get(i);
            System.out.println(dataFormatada.format(post.getDATA()));
            System.out.println(post.getTEXTO());
            System.out.println(post.getCurtidas() + " curtidas ");
            if (post.getComentarios().isEmpty()) {
                System.out.println("Seja o primeiro a comentar");
            } else {
                System.out.println("Cometários: ");
                for (String comentarios : post.getComentarios()) {
                    System.out.println(comentarios);
                }
            }
            System.out.println();
            System.out.println("1 - Curtir  2 - Comentar 3 - Ver próxima  4 - Voltar para o menu");
            System.out.println();
            System.out.println("============================================");
            int acao = EntradasEValidacoes.recebeAcaoPostagem();
            if (acao == 1) {
                curtirPost(post);
                exibePostagens(perfilVisitado, i);
                menuDePerfis(perfilVisitado);
            } else if (acao == 2) {
                System.out.println("Digite seu comentário: ");
                String comentario = sc.nextLine();
                comentarPost(post, comentario);
                exibePostagens(perfilVisitado, i);
            } else if (acao == 4) {
                menuDoUsuario(perfilVisitado);
                break;
            }
        }
        if (perfilVisitado.getPosts().isEmpty()) {
            System.out.println(perfilVisitado.getNome() + " ainda não tem publicações.");
        } else {
            System.out.println("Não há mais postagens.");
        }
        System.out.println("============================================");
        menuDePerfis(perfilVisitado);
    }

    public static void exibeSeguridores(Perfil perfilVisitado) {
        System.out.println("============================================");
        if (perfilVisitado.getSeguidoPor().isEmpty()) {
            System.out.println("O usuário ainda não tem seguidores.");
            System.out.println();
        } else {
            System.out.println("Seguidores de " + perfilVisitado.getNome());
            System.out.println();
            for (Perfil seguidor : perfilVisitado.getSeguidoPor()) {
                System.out.println(seguidor.getNome());
                System.out.println();
            }
        }
        System.out.println("============================================");
        menuDePerfis(perfilVisitado);
    }

    public static void exibeSeguindo(Perfil perfilVisitado) {
        System.out.println("============================================");
        if (perfilVisitado.getSeguidoPor().isEmpty()) {
            System.out.println("O usuário ainda não segue ninguém.");
        } else {
            System.out.println(perfilVisitado.getNome() + " segue:");
            System.out.println();
            for (Perfil segue : perfilVisitado.getSeguindo()) {
                System.out.println(segue.getNome());
                System.out.println();
            }
        }
        System.out.println("============================================");
        menuDePerfis(perfilVisitado);
    }

    public static void pesquisaUsuarios() {
        System.out.println("Digite um nome ou um começo de nome: ");
        String busca = sc.nextLine();
        boolean usuarioEncontrado = false;
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getNome().toUpperCase().startsWith(busca.toUpperCase())) {
                System.out.println((i + 1) + " - " + usuarios.get(i).getNome());
                usuarioEncontrado = true;
            }
        }
        if (usuarioEncontrado) {
            System.out.println();
            System.out.println("Digite o número do usuário que deseja ver o perfil.");
            int posicao = EntradasEValidacoes.recebePosicao(usuarios);
            for (int i = 0; i < usuarios.size(); i++) {
                if (i == posicao) {
                    exibePerfil(usuarios.get(i));
                }
            }
        } else {
            System.out.println("Não existem usuários que correspondem a busca.");
            System.out.println();
            menuDoUsuario(perfilQueEstaLogado);
        }
    }

    public static void adicionaUsuario(Perfil perfilVisitado) {
        if (perfilVisitado != perfilQueEstaLogado && !(perfilQueEstaLogado.getSeguindo().contains(perfilVisitado))) {
            perfilQueEstaLogado.setSeguindo(perfilVisitado);
            perfilVisitado.setSeguidoPor(perfilQueEstaLogado);
            System.out.println(perfilVisitado.getNome() + " foi adicionado!");
        } else if (perfilVisitado == perfilQueEstaLogado) {
            System.out.println("Você está na sua página, navegue por outras para adicionar amigos.");
        } else {
            System.out.println("Vocês já são amigos.");
        }

        System.out.println();
        exibePerfil(perfilVisitado);
    }

    public static void curtirPost(Post post) {
        post.setCurtidas();
        System.out.println("Curtido!");
        System.out.println();
    }

    public static void comentarPost(Post post, String comentario) {
        post.setComentarios(perfilQueEstaLogado, dataFormatada, comentario);
        System.out.println("Comentário feito!!");
        System.out.println();
    }

    private static void criaPerfilFamoso(Perfil perfil) {
        System.out.println("Escreva brevemente o que te torna famoso.");
        String famosoPor = sc.nextLine();

        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getLOGIN().equals(perfil.getLOGIN())) {
                PerfilFamoso novo = new PerfilFamoso(perfil.getLOGIN(), perfil.getNome(), perfil.getSENHA(), famosoPor);
                usuarios.remove(i);
                usuarios.add(novo);
                System.out.println("Parabéns agora sue perfil está em destaque!");
                menuDoUsuario(novo);
            }else{
                menuDoUsuario(perfilQueEstaLogado);
            }
        }
    }

    public static Date criaData() {
        return new Date(System.currentTimeMillis());
    }

    public static void criaPerfisAutomaticamente() {
        String[] emails = {"caio@gmail.com", "celia@gmail.com", "cassio@gmail.com"};
        String[] nomes = {"Caio Santos", "Celia Garcia", "Cassio Ramos"};
        String[] senhas = {"123456", "123456", "123456"};

        for (int i = 0; i < 3; i++) {
            Perfil novo = new Perfil(emails[i], nomes[i], senhas[i]);
            usuarios.add(novo);
        }
    }
}