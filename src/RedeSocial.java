
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class RedeSocial {
    // Olá Alan! Existe um método que adiciona três perfis assim que você cadastra o primeiro usuário.
// Assim pode usar as funções extras de busca de usuário, seguir, comentar e curtir com menos trabalho.
//Esses usuários começam com a letra "c", use ela para fazer as buscas. Valeu!
    static ArrayList<Perfil> usuarios = new ArrayList<>();
    static Perfil perfilQueEstaLogado;
    static SimpleDateFormat dataFormatada = new SimpleDateFormat("dd/MM/yyyy 'às' HH:mm");
    public static Scanner sc = new Scanner(System.in);

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
        int opcao = Validacoes.respostaLogin();

        if (usuarios.size() == 1 && opcao == 1) {
            criaPerfisAutomaticamente();
        }

        if (opcao == 1) {
            login();
        } else if (opcao == 2) {
            novoUsuario();
        } else {
            System.out.println("Programa encerrado, até logo!");
        }
    }

    public static void menuDoUsuario(Perfil usuarioLogado) {
        System.out.println("================================================================");
        System.out.println("Bem vindo, " + usuarioLogado.nome + "!");
        System.out.println();

        System.out.println("O que deseja fazer?\n\n1 - Fazer postagem 2 - Ir para sua timeline\n3 - Procurar usuários  4 - Ver seu perfil \n0 - Sair");
        System.out.println("================================================================");

        int opcao = Validacoes.respostaMenuUsuario();

        if (opcao == 1) {
            fazerPostagem(usuarioLogado);
        } else if (opcao == 2) {
            criaTimeline(usuarioLogado);
        } else if (opcao == 3) {
            pesquisaUsuarios();
        } else if (opcao == 4) {
            exibePerfil(usuarioLogado);
        } else {
            menuInicial();
        }
    }

    public static void criaTimeline(Perfil usuarioLogado) {
        System.out.println("================================================================");
        System.out.println("Suas publicações:");
        System.out.println();

        if (usuarioLogado.posts.isEmpty()) {
            System.out.println("Faça sua primeira publicação.");
            System.out.println();
            menuDoUsuario(usuarioLogado);
        } else {
            for (Post post : usuarioLogado.posts) {
                System.out.println(dataFormatada.format(post.data) + " - " + "'" + post.texto + "'");
            }
            System.out.println();
            System.out.println("================================================================");
            System.out.println();
            menuDoUsuario(usuarioLogado);
        }
    }

    public static void fazerPostagem(Perfil usuarioLogado) {
        Date data = criaData();

        System.out.println("No que esta pensando? ");
        String texto = sc.nextLine();

        new Post(usuarioLogado, data, texto);
        System.out.println("Publicado!");
        System.out.println();

        menuDoUsuario(usuarioLogado);
    }

    public static void menuDePerfis(Perfil perfilVisitado) {
        System.out.println("============================================");
        System.out.println("Para ver em detalhes desse perfil escolha uma opção: ");
        System.out.println("1 - Postagens   2 - Seguidores\n3 - Seguidos    4 - Adicionar esse perfil\n5 - Voltar ao menu do usuário  6 - Voltar para o perfil de " + perfilVisitado.nome);
        System.out.println("============================================");
        int opcao = Validacoes.respostaMenu();

        if (opcao == 1) {
            postagens(perfilVisitado);
        } else if (opcao == 2) {
            seguridores(perfilVisitado);
        } else if (opcao == 3) {
            seguidos(perfilVisitado);
        } else if (opcao == 4) {
            adicionaUsuario(perfilVisitado);
        } else if (opcao == 5) {
            menuDoUsuario(perfilQueEstaLogado);
        } else {
            exibePerfil(perfilVisitado);
        }
    }

    public static void exibePerfil(Perfil perfilVisitado) {
        System.out.println("============================================");
        System.out.println("Perfil de " + perfilVisitado.nome);
        System.out.println();

        System.out.println("Postagens de " + perfilVisitado.nome);
        System.out.println();

        if (perfilVisitado.posts.size() > 0) {
            for (Post post : perfilVisitado.posts) {
                System.out.println("        " + dataFormatada.format(post.data) +" - " + post.texto);
                System.out.println();
            }
        } else {
            System.out.println("        O usuário ainda não tem postagens.");
            System.out.println();
        }
        System.out.println("Seguidores de " + perfilVisitado.nome);
        System.out.println();
        if (perfilVisitado.seguidoPor.size() > 0) {
            for (Perfil seguidor : perfilVisitado.seguidoPor) {
                System.out.println("        " + seguidor.nome);
                System.out.println();
            }
        } else {
            System.out.println("        O usuário ainda não tem seguidores.");
            System.out.println();
        }

        System.out.println(perfilVisitado.nome + " segue:");
        System.out.println();

        if (perfilVisitado.segue.size() > 0) {
            for (Perfil segue : perfilVisitado.segue) {
                System.out.println("        " + segue.nome);
                System.out.println();
            }
        } else {
            System.out.println("        O usuário ainda não segue ninguém.");
            System.out.println();
        }

        System.out.println("============================================");
        menuDePerfis(perfilVisitado);
    }

    public static void login() {
        System.out.println("Insira seu login: ");
        String login = sc.nextLine();
        if (usuarios.isEmpty()) {
            System.out.println("Ainda não existem usuários cadastrados.");
            System.out.println();
            menuInicial();
        } else {
            try {
                boolean usuarioEncontrado = false;
                for (int i = 0; i < usuarios.size(); i++) {
                    if (usuarios.get(i).login.equalsIgnoreCase(login)) {
                        System.out.println("Olá " + usuarios.get(i).nome + " insira sua senha: ");
                        String senha = sc.nextLine();
                        if (usuarios.get(i).senha.equals(senha)) {
                            System.out.println("Login efetuado com sucesso.");
                            System.out.println();
                            perfilQueEstaLogado = usuarios.get(i);
                            menuDoUsuario(usuarios.get(i));
                            usuarioEncontrado = true;
                            break;
                        } else {
                            System.out.println("Senha incorreta, tente novamente");
                            senha = sc.nextLine();
                            if (usuarios.get(i).senha.equals(senha)) {
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
            } catch (UserNotFoundException | InvalidPasswordException e) {
                System.out.println(e.getMessage());
                System.out.println();
                menuInicial();
            }
        }
    }

    public static void novoUsuario() {
        System.out.println("Insira seu email: ");
        String login = Validacoes.recebeEmail();
        System.out.println("Insira seu nome: ");
        String nome = Validacoes.recebeNome();
        System.out.println("Insira uma senha com  6 ou mais caracteres: ");
        String senha = Validacoes.criaSenha();
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

    public static void postagens(Perfil perfilVisitado) {
        System.out.println("============================================");
        System.out.println("Postagens de " + perfilVisitado.nome);
        System.out.println();

        for (Post post : perfilVisitado.posts) {
            System.out.println(dataFormatada.format(post.data));
            System.out.println(post.texto);
            System.out.println(post.curtidas + " curtidas ");
            if (post.comentarios.isEmpty()) {
                System.out.println("Seja o primeiro a comentar");
            } else {
                System.out.println("Cometários: ");
                for (String comentarios : post.comentarios) {
                    System.out.println(comentarios);
                }
            }
            System.out.println();
            System.out.println("1 - Curtir  2 - Comentar 3 - Ver próxima  4 - Voltar para o menu");
            System.out.println();
            System.out.println("============================================");
            int acao = Validacoes.recebeAcaoPostagem();
            if (acao == 1) {
                curtirPost(post);
                exibePerfil(perfilVisitado);
                menuDePerfis(perfilVisitado);
            } else if (acao == 2) {
                System.out.println("Digite seu comentário: ");
                String comentario = sc.nextLine();
                comentarPost(post, comentario);
                menuDePerfis(perfilVisitado);
            } else if (acao == 4) {
                menuDoUsuario(perfilVisitado);
                break;
            }
        }
        if (perfilVisitado.posts.isEmpty()) {
            System.out.println(perfilVisitado.nome + " ainda não tem publicações.");
        } else {
            System.out.println("Não há mais postagens.");
        }
        System.out.println("============================================");
        menuDePerfis(perfilVisitado);
    }

    public static void seguridores(Perfil perfilVisitado) {
        System.out.println("============================================");
        if (perfilVisitado.seguidoPor.isEmpty()) {
            System.out.println("O usuário ainda não tem seguidores.");
            System.out.println();
        } else {
            System.out.println("Seguidores de " + perfilVisitado.nome);
            System.out.println();
            for (Perfil seguidor : perfilVisitado.seguidoPor) {
                System.out.println(seguidor.nome);
                System.out.println();
            }
        }
        System.out.println("============================================");
        menuDePerfis(perfilVisitado);
    }

    public static void seguidos(Perfil perfilVisitado) {
        System.out.println("============================================");
        if (perfilVisitado.seguidoPor.isEmpty()) {
            System.out.println("O usuário ainda não segue ninguém.");
        } else {
            System.out.println(perfilVisitado.nome + " segue:");
            System.out.println();
            for (Perfil segue : perfilVisitado.segue) {
                System.out.println(segue.nome);
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
            if (usuarios.get(i).nome.toUpperCase().startsWith(busca.toUpperCase())) {
                System.out.println((i + 1) + " - " + usuarios.get(i).nome);
                usuarioEncontrado = true;
            }
        }
        if (usuarioEncontrado) {
            System.out.println();
            System.out.println("Digite o número do usuário que deseja ver o perfil.");
            int posicao = Validacoes.recebePosicao(usuarios);
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
        if (perfilVisitado != perfilQueEstaLogado && !(perfilQueEstaLogado.segue.contains(perfilVisitado))) {
            perfilQueEstaLogado.segue.add(perfilVisitado);
            perfilVisitado.seguidoPor.add(perfilQueEstaLogado);
            System.out.println(perfilVisitado.nome + " foi adicionado!");
        } else if (perfilVisitado == perfilQueEstaLogado) {
            System.out.println("Você está na sua página, navegue por outras para adicionar amigos.");
        } else {
            System.out.println("Vocês já são amigos.");
        }

        System.out.println();
        exibePerfil(perfilVisitado);
    }

    public static void curtirPost(Post post) {
        post.curtidas += 1;
        System.out.println("Curtido!");
        System.out.println();
    }

    public static void comentarPost(Post post, String comentario) {
        post.comentarios.add(perfilQueEstaLogado.nome + " - " + dataFormatada.format(post.data) + " - " + comentario);
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