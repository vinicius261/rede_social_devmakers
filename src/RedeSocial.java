
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class RedeSocial {
    // Olá Alan! Existe um método que adiciona três perfis assim que você cadastra o primeiro usuário.
// Assim pode usar as funções extras de busca de usuário, seguir, comentar e curtir com menos trabalho.
//Esses usuários começam com a letra "c", use ela para fazer as buscas. Valeu!
    static ArrayList<Perfil> usuarios = new ArrayList();
    static Perfil perfilQueEstaLogado;
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
            pesquisaUsuários(usuarioLogado);
        } else if (opcao == 4) {
            exibePerfil(usuarioLogado);
        } else {
            menuInicial();
        }
    }

    public static void criaTimeline(Perfil usuario) {
        System.out.println("================================================================");
        System.out.println("Suas publicações:");
        System.out.println();

        if (usuario.posts.isEmpty()) {
            System.out.println("Faça sua primeira publicação.");
            System.out.println();
            menuDoUsuario(usuario);
        } else {
            SimpleDateFormat formatter= new SimpleDateFormat("dd/MM/yyyy 'às' HH:mm");
            for (Post post : usuario.posts) {
                System.out.println(formatter.format(post.data) + " - " + "'" + post.texto + "'");
            }
            System.out.println();
            System.out.println("================================================================");
            System.out.println();
            menuDoUsuario(usuario);
        }
    }

    public static void fazerPostagem(Perfil autor) {
        Date data = Validacoes.criaData();

        System.out.println("No que esta pensando? ");
        String texto = sc.nextLine();

        new Post(autor, data, texto);
        System.out.println("Publicado!");
        System.out.println();

        menuDoUsuario(perfilQueEstaLogado);
    }

    public static void menuDePerfis(Perfil usuario) {
        System.out.println("============================================");
        System.out.println("Para ver em detalhes desse perfil escolha uma opção: ");
        System.out.println("1 - Postagens   2 - Seguidores\n3 - Seguidos    4 - Adicionar esse perfil\n5 - Voltar ao menu do usuário  6 - Voltar para o perfil de " + usuario.nome);
        System.out.println("============================================");
        int opcao = Validacoes.respostaMenu();

        if (opcao == 1) {
            postagens(usuario);
        } else if (opcao == 2) {
            seguridores(usuario);
        } else if (opcao == 3) {
            seguidos(usuario);
        } else if (opcao == 4) {
            adicionaUsuario(usuario);
        } else if (opcao == 5) {
            menuDoUsuario(perfilQueEstaLogado);
        } else {
            exibePerfil(usuario);
        }
    }

    public static void exibePerfil(Perfil usuario) {
        System.out.println("============================================");
        System.out.println("Perfil de " + usuario.nome);
        System.out.println();

        System.out.println("Postagens de " + usuario.nome);
        System.out.println();

        if (usuario.posts.size() > 0) {
            SimpleDateFormat formatter= new SimpleDateFormat("dd/MM/yyyy 'às' HH:mm");
            for (Post post : usuario.posts) {
                System.out.println("        " + formatter.format(post.data) +" - " + post.texto);
                System.out.println();
            }
        } else {
            System.out.println("        O usuário ainda não tem postagens.");
            System.out.println();
        }
        System.out.println("Seguidores de " + usuario.nome);
        System.out.println();
        if (usuario.seguidoPor.size() > 0) {
            for (Perfil seguidor : usuario.seguidoPor) {
                System.out.println("        " + seguidor.nome);
                System.out.println();
            }
        } else {
            System.out.println("        O usuário ainda não tem seguidores.");
            System.out.println();
        }

        System.out.println(usuario.nome + " segue:");
        System.out.println();

        if (usuario.segue.size() > 0) {
            for (Perfil segue : usuario.segue) {
                System.out.println("        " + segue.nome);
                System.out.println();
            }
        } else {
            System.out.println("        O usuário ainda não segue ninguém.");
            System.out.println();
        }

        System.out.println("============================================");
        menuDePerfis(usuario);
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


    public static void postagens(Perfil usuario) {
        System.out.println("============================================");
        System.out.println("Postagens de " + usuario.nome);
        System.out.println();

        for (Post post : usuario.posts) {
            SimpleDateFormat formatter= new SimpleDateFormat("dd/MM/yyyy 'às' HH:mm");
            System.out.println(formatter.format(post.data));
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
                exibePerfil(usuario);
                menuDePerfis(usuario);
            } else if (acao == 2) {
                System.out.println("Digite seu comentário: ");
                String comentario = sc.nextLine();
                comentarPost(post, comentario, usuario);
                menuDePerfis(usuario);
            } else if (acao == 4) {
                menuDoUsuario(usuario);
                break;
            } else {
            }
        }
        if (usuario.posts.isEmpty()) {
            System.out.println(usuario.nome + " ainda não tem publicações.");
            System.out.println("============================================");
            menuDePerfis(usuario);
        } else {
            System.out.println("Não há mais postagens.");
            System.out.println("============================================");
            menuDePerfis(usuario);
        }
    }

    public static void seguridores(Perfil usuario) {
        System.out.println("============================================");
        if (usuario.seguidoPor.isEmpty()) {
            System.out.println("O usuário ainda não tem seguidores.");
            System.out.println();
        } else {
            System.out.println("Seguidores de " + usuario.nome);
            System.out.println();
            for (Perfil seguidor : usuario.seguidoPor) {
                System.out.println(seguidor.nome);
                System.out.println();
            }
        }
        System.out.println("============================================");
        menuDePerfis(usuario);
    }

    public static void seguidos(Perfil usuario) {
        System.out.println("============================================");
        if (usuario.seguidoPor.isEmpty()) {
            System.out.println("O usuário ainda não segue ninguém.");
        } else {
            System.out.println(usuario.nome + " segue:");
            System.out.println();
            for (Perfil segue : usuario.segue) {
                System.out.println(segue.nome);
                System.out.println();
            }
        }
        System.out.println("============================================");
        menuDePerfis(usuario);
    }

    public static void pesquisaUsuários(Perfil usuarioLogado) {
        System.out.println("Digite um nome ou um começo de nome: ");
        String busca = sc.nextLine();
        boolean usuarioEncontrado = false;
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).nome.toUpperCase().startsWith(busca.toUpperCase())) {
                System.out.println((i + 1) + " - " + usuarios.get(i).nome);
                usuarioEncontrado = true;
            }
        }
        if (usuarioEncontrado == true) {
            System.out.println();
            System.out.println("Digite o número do usuário que deseja ver o perfil.");
            int posicao = Validacoes.recebePosicao(usuarioLogado);
            for (int i = 0; i < usuarios.size(); i++) {
                if (i == posicao) {
                    exibePerfil(usuarios.get(i));
                }
            }
        } else {
            System.out.println("Não existem usuários que correspondem a busca.");
            System.out.println();
            menuDoUsuario(usuarioLogado);
        }
    }

    public static void adicionaUsuario(Perfil usuario) {
        if (usuario != perfilQueEstaLogado && !(perfilQueEstaLogado.segue.contains(usuario))) {
            perfilQueEstaLogado.segue.add(usuario);
            usuario.seguidoPor.add(perfilQueEstaLogado);
            System.out.println(usuario.nome + " foi adicionado!");
        } else if (usuario == perfilQueEstaLogado) {
            System.out.println("Você está na sua página, navegue por outras para adicionar amigos.");
        } else {
            System.out.println("Vocês já são amigos.");
        }

        System.out.println();
        exibePerfil(usuario);
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

    public static void curtirPost(Post post) {
        post.curtidas += 1;
        System.out.println("Curtido!");
        System.out.println();
    }

    public static void comentarPost(Post post, String comentario, Perfil autorDoComentario) {
        post.comentarios.add(autorDoComentario.nome + " - " + comentario);
    }
}