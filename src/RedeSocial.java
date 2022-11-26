
import java.util.ArrayList;
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
        int opcao = respostaLogin();

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

    private static void menuDoUsuario(Perfil usuarioLogado) {
        System.out.println("================================================================");
        System.out.println("Bem vindo, " + usuarioLogado.nome + "!");
        System.out.println();

        System.out.println("O que deseja fazer?\n\n1 - Fazer postagem 2 - Ir para sua timeline\n3 - Procurar usuários  4 - Ver seu perfil \n0 - Sair");
        System.out.println("================================================================");

        int opcao = respostaMenuUsuario();

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

       /* System.out.println("Suas postagens :");
        for (Post post : usuarioLogado.posts) {
            System.out.println(post.data + " - " + post.hora);
            System.out.println(post.texto);
            System.out.println();
        }

        System.out.println("Seus seguidores: ");
        for (Perfil seguidor : usuarioLogado.seguidoPor) {
            System.out.println(seguidor.nome);
            System.out.println();
        }*/
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
            for (Post post : usuario.posts) {
                System.out.println(post.data + " às " + post.hora + " - " + "'" + post.texto + "'");
            }
            System.out.println();
            System.out.println("================================================================");
            System.out.println();
            menuDoUsuario(usuario);
        }
    }

    private static void fazerPostagem(Perfil autor) {
        System.out.println("Vamos registrar a data da postagem: ");
        String data = dataPostagem();

        System.out.println("Agora a hora da postagem: ");
        String hora = horaPostagem();

        System.out.println("No que esta pensando? ");
        String texto = sc.nextLine();

        new Post(autor, data, hora, texto);
        System.out.println("Publicado!");
        System.out.println();

        menuDoUsuario(perfilQueEstaLogado);
    }

    private static void menuDePerfis(Perfil usuario) {
        System.out.println("============================================");
        System.out.println("Para ver em detalhes desse perfil escolha uma opção: ");
        System.out.println("1 - Postagens   2 - Seguidores\n3 - Seguidos    4 - Adicionar esse perfil\n5 - Voltar ao menu do usuário  6 - Voltar para o perfil de " + usuario.nome);
        System.out.println("============================================");
        int opcao = respostaMenu();

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

    private static void exibePerfil(Perfil usuario) {
        System.out.println("============================================");
        System.out.println("Perfil de " + usuario.nome);
        System.out.println();

        System.out.println("Postagens de " + usuario.nome);
        System.out.println();

        if (usuario.posts.size() > 0) {
            for (Post post : usuario.posts) {
                System.out.println("        " + post.data + " - " + post.hora + " - " + post.texto);
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

    private static void login() {
        System.out.println("Insira seu login: ");
        String login = sc.nextLine();
        if (usuarios.isEmpty()) {
            System.out.println("Ainda não existem usuários cadastrados.");
            System.out.println();
            menuInicial();
        } else {
            try {
                for (int i = 0; i < usuarios.size(); i++) {
                    boolean usuarioEncontrado = false;
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
                        if (usuarioEncontrado == false) {
                            throw new UserNotFoundException("Usuário não encontrado.");
                        }
                    }
                }
            } catch (UserNotFoundException | InvalidPasswordException e) {
                System.out.println(e.getMessage());
                System.out.println();
                menuInicial();
            }
        }
    }

    private static void novoUsuario() {
        System.out.println("Insira seu email: ");
        String login = recebeEmail();
        System.out.println("Insira seu nome: ");
        String nome = recebeNome();
        System.out.println("Insira uma senha com  6 ou mais caracteres: ");
        String senha = criaSenha();
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



    private static void postagens(Perfil usuario) {
        System.out.println("============================================");
        System.out.println("Postagens de " + usuario.nome);
        System.out.println();

        for (Post post : usuario.posts) {
            System.out.println(post.data + " - " + post.hora);
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
            int acao = recebeAcaoPostagem();
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

    private static void seguridores(Perfil usuario) {
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
    private static void seguidos(Perfil usuario) {
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

    private static void pesquisaUsuários(Perfil usuarioLogado) {
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
            int posicao = recebePosicao(usuarioLogado);
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

    private static void adicionaUsuario(Perfil usuario) {
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

    private static void criaPerfisAutomaticamente() {
        String[] emails = {"caio@gmail.com", "celia@gmail.com", "cassio@gmail.com"};
        String[] nomes = {"Caio Santos", "Celia Garcia", "Cassio Ramos"};
        String[] senhas = {"123456", "123456", "123456"};

        for (int i = 0; i < 3; i++) {
            Perfil novo = new Perfil(emails[i], nomes[i], senhas[i]);
            usuarios.add(novo);
        }
    }

    private static void curtirPost(Post post) {
        post.curtidas += 1;
        System.out.println("Curtido!");
        System.out.println();
    }

    private static void comentarPost(Post post, String comentario, Perfil autorDoComentario) {
        post.comentarios.add(autorDoComentario.nome + " - " + comentario);
    }

    public static String recebeNome() {
        String nome = sc.nextLine();

        if (!(nome.matches("^([a-zA-Z]+\\s*)+$"))) {
            System.out.println("Digite apenas letras.");
            nome = sc.nextLine();
            if (!(nome.matches("^([a-zA-Z]+\\s*)+$"))) {
                System.out.println("Nome inválido.");
                System.out.println();
                RedeSocial.menuInicial();
            }
        }
        return nome;
    }

    public static String recebeEmail() {
        String email = sc.nextLine();
        String[] verificaEmail = email.split("@");

        if (verificaEmail.length < 2) {
            System.out.println("Email inválido, tente mais uma vez.");
            email = sc.nextLine();
            if (verificaEmail.length < 2) {
                System.out.println("Email inválido.");
                System.out.println();
                RedeSocial.menuInicial();
            }
        }

        for (Perfil usuario : RedeSocial.usuarios) {
            if (usuario.login.equalsIgnoreCase(email)) {
                System.out.println("Login já cadastrado.");
                System.out.println();
                RedeSocial.menuInicial();
                break;
            }
        }

        System.out.println("Confirme seu email: ");
        String confirmacao = sc.nextLine();
        if (!confirmacao.equalsIgnoreCase(email)) {
            System.out.println("Os emails digitados estão diferentes, tente mais uma vez.");
            email = sc.nextLine();
            if (!confirmacao.equalsIgnoreCase(email)) {
                System.out.println("Os emails digitados estão diferentes.");
                System.out.println();
                RedeSocial.menuInicial();
            }
        }
        return email;
    }

    public static String criaSenha() {
        String senha = sc.nextLine();
        if (senha.length() < 6 || senha.isEmpty()) {
            System.out.println("A senha deve conter pelo menos 6 caracteres, tente novamente.");
            senha = sc.nextLine();
            if (senha.length() < 6 || senha.isEmpty()) {
                senha = "ERRO";
            }
        }
        if ((!senha.equals("ERRO"))) {
            System.out.println("Confirme a senha: ");
            String confirmacao = sc.nextLine();
            System.out.println();
            if (!confirmacao.equals(senha)) {
                System.out.println("Senhas diferentes, confirme novamente.");
                confirmacao = sc.nextLine();
                if (!confirmacao.equals(senha)) {
                    senha = "ERRO2";
                }
            }
        }
        return senha;
    }

    public static int respostaLogin() {
        int numero = 0;

        try {
            numero = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Digite um número do menu.");
            System.out.println();
            numero = respostaLogin();
        }

        while (numero < 0 || numero > 2) {
            System.out.println("Digite 1 para Fazer login, 2 para Cadastrar ou 0 para Sair");
            System.out.println();
            numero = respostaLogin();
        }
        return numero;
    }

    public static int respostaMenuUsuario() {
        int numero = 0;

        try {
            numero = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Digite um número do menu.");
            System.out.println();
            numero = respostaMenuUsuario();
        }

        while (numero < 0 || numero > 4) {
            System.out.println("Digite 1 para Postar, 2 para Timeline, 3 para buscar usuários, 4 para ver seu perfil ou 0 para Sair");
            System.out.println();
            numero = respostaMenuUsuario();
        }
        return numero;
    }

    public static int respostaMenu() {
        int numero = 0;

        try {
            numero = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Digite apenas números");
            numero = respostaLogin();
        }

        while (numero < 1 || numero > 6) {
            System.out.println("Digite uma opção válida.");
            numero = respostaLogin();
        }
        return numero;
    }

    public static String dataPostagem() {
        System.out.println("Insira o ano: ");
        int ano = recebeAno();

        System.out.println("Insira o mês: ");
        int mes = recebeMes();

        System.out.println("Insira o dia: ");
        int dia = recebeDia(mes);


        return dia + "/" + mes + "/" + ano;
    }

    public static String horaPostagem() {
        System.out.println("Insira as horas: ");
        int hora = recebeHora();

        System.out.println("Insira os minutos: ");
        int minutos = recebeMinutos();

        if (hora < 10 && minutos < 10) {
            return "0" + hora + ":" + "0" + minutos;
        } else if (hora < 10) {
            return "0" + hora + ":" + minutos;
        } else if (minutos < 10) {
            return hora + ":" + "0" + minutos;
        } else {
            return hora + ":" + minutos;
        }
    }

    public static int recebeDia(int mes) {
        int dia = 0;

        try {
            dia = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Digite apenas números.");
            dia = recebeDia(mes);
        }

        if (mes == 1 || mes == 3 || mes == 5 || mes == 7 || mes == 8 || mes == 10 || mes == 12) {
            while (mes < 1 || mes > 31) {
                System.out.println("Digite um dia válido.");
                dia = recebeDia(mes);
            }
        } else if (mes == 2) {
            while (mes < 1 || mes > 29) {
                System.out.println("Digite um dia válido.");
                dia = recebeDia(mes);
            }
        } else {
            while (dia < 1 || dia > 30) {
                System.out.println("Digite um dia válido.");
                dia = recebeDia(mes);
            }
        }
        return dia;
    }

    public static int recebeMes() {
        int mes = 0;

        try {
            mes = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Digite apenas números.");
            mes = recebeMes();
        }

        while (mes < 1 || mes > 12) {
            System.out.println("Digite um mês válido.");
            mes = recebeMes();
        }
        return mes;
    }

    public static int recebeAno() {
        int ano = 0;

        try {
            ano = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Digite apenas números.");
            ano = recebeAno();
        }

        while (ano < 2022) {
            System.out.println("Digite o ano atual.");
            ano = recebeAno();
        }
        return ano;
    }

    public static int recebeHora() {
        int hora = 0;

        try {
            hora = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Digite apenas números.");
            hora = recebeDia(hora);
        }

        while (hora < 0 || hora > 23) {
            System.out.println("Digite uma hora válida.");
            hora = recebeDia(hora);
        }
        return hora;
    }

    public static int recebeMinutos() {
        int minutos = 0;

        try {
            minutos = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Digite apenas números.");
            minutos = recebeMinutos();
        }

        while (minutos < 1 || minutos > 59) {
            System.out.println("Digite minutos válidos.");
            minutos = recebeMinutos();
        }
        return minutos;
    }

    public static int recebePosicao(Perfil tamanhoArray) {
        int posicao = 0;

        try {
            posicao = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Digite apenas números.");
            posicao = recebePosicao(tamanhoArray);
        }

        while (posicao < tamanhoArray.segue.size()) {
            System.out.println("Digite um número válido.");
            recebePosicao(tamanhoArray);
        }
        return posicao - 1;

    }

    public static int recebeAcaoPostagem() {
        int acao = 0;

        try {
            acao = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Digite apenas números.");
            acao = recebeAcaoPostagem();
        }

        while (acao < 1 || acao > 4) {
            System.out.println("Digite um número válido.");
            recebeAcaoPostagem();
        }
        return acao;

    }
}
