package com.redesocial.main;

import com.redesocial.excecoes.InvalidPasswordException;
import com.redesocial.excecoes.UserNotFoundException;
import com.redesocial.perfis.Perfil;
import com.redesocial.post.Post;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class RedeSocial {
    private static List<Perfil> listaDeUsuarios;
    private static Perfil perfilQueEstaLogado;
    private static SimpleDateFormat formatacaoDaData;
    private static Scanner scanner;

    public static void main(String[] args) {
        /*São cadastrados automaticamente 3 usuários com nome iniciando com a letra 'c' para facilitar
        as funções de busca e adicionar usuários. */

        RedeSocial redeSocial = new RedeSocial();

        redeSocial.menuInicial();

        scanner.close();
    }

    public RedeSocial(){
        listaDeUsuarios = new ArrayList<>();
        formatacaoDaData = new SimpleDateFormat("dd/MM/yyyy 'às' HH:mm");
        scanner = new Scanner(System.in);
    }
    public void menuInicial() {
        System.out.println("Seja bem vindo a Rede Social!\n");
        System.out.println("================================================================");
        System.out.println("Já tem uma conta?\n\n1 - Sim (Faça login)\n2 - Não (Cadastrar)\n\n0 - Sair (Encerrar programa)");
        System.out.println("================================================================");

        int opcao = respostaMenuiIncial();

        criaPerfisAutomaticamente(opcao);

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

    protected void menuDoUsuario(Perfil usuarioLogado) {
        System.out.println("================================================================");
        System.out.println("Bem vindo, " + usuarioLogado.getNOME() + "!\n");

        System.out.println("O que deseja fazer?\n\n1 - Fazer postagem  2 - Ir para sua timeline" +
                "\n3 - Procurar usuários  4 - Ver seu perfil \n0 - Sair\n");
        System.out.println("================================================================");

        int opcao = respostaMenuUsuario();

        if (opcao == 1) {
            Date data = criaData();
            System.out.println("No que esta pensando? ");
            String texto = scanner.nextLine();
            Perfil.fazerPost(usuarioLogado, data, texto);
            menuDoUsuario(usuarioLogado);
        } else if (opcao == 2) {
            exibeTimeline(usuarioLogado);
        } else if (opcao == 3) {
            pesquisaUsuarios();
        } else if (opcao == 4) {
            exibePerfil(usuarioLogado);
        } else {
            menuInicial();
        }
    }

    protected void exibeTimeline(Perfil usuarioLogado) {
        System.out.println("================================================================");
        System.out.println("Suas publicações:\n");

        if (usuarioLogado.getPosts().isEmpty()) {
            System.out.println("Faça sua primeira publicação.");
        } else {
            for (Post post : usuarioLogado.getPosts()) {
                System.out.println(formatacaoDaData.format(post.getDATA()) + " - " + "Curtidas: " +
                        post.getCurtidas() + " Comentários: " + post.getComentarios().size() + "\n'" + post.getTEXTO() + "'");
                System.out.println();
            }
            System.out.println();
            System.out.println("================================================================");
        }
        System.out.println();

        menuDoUsuario(usuarioLogado);
    }

    protected void menuDePerfis(Perfil perfilVisitado) {
        System.out.println("============================================");
        System.out.println("Para ver em detalhes desse perfil escolha uma opção: ");
        System.out.println("1 - Postagens   2 - Seguidores\n3 - Seguindo    4 - Adicionar esse perfil\n5" +
                " - Voltar ao menu principal  6 - Voltar para o perfil de " + perfilVisitado.getNOME());
        System.out.println("============================================\n");

        int opcao = respostaMenu();

        if (opcao == 1) {
            exibePostagens(perfilVisitado);
        } else if (opcao == 2) {
            exibeSeguridores(perfilVisitado);
        } else if (opcao == 3) {
            exibeSeguindo(perfilVisitado);
        } else if (opcao == 4) {
            Perfil.adicionaUsuario(perfilVisitado, perfilQueEstaLogado);
            exibePerfil(perfilVisitado);
        } else if (opcao == 5) {
            menuDoUsuario(perfilQueEstaLogado);
        } else {
            menuDoUsuario(perfilVisitado);
        }
    }

    protected void exibePerfil(Perfil perfilVisitado) {
        System.out.println("============================================");
        System.out.println("Perfil de " + perfilVisitado.getNOME());
        System.out.println();

        System.out.println("Postagens de " + perfilVisitado.getNOME());
        System.out.println();
        if (perfilVisitado.getPosts().size() > 0) {
            for (Post post : perfilVisitado.getPosts()) {
                System.out.println("        " + formatacaoDaData.format(post.getDATA()) + " - " + post.getTEXTO());
                System.out.println();
            }
        } else {
            System.out.println("        O usuário ainda não tem postagens.\n");
        }
        System.out.println("Seguidores de " + perfilVisitado.getNOME());
        System.out.println();
        if (perfilVisitado.getSeguidoPor().size() > 0) {
            for (Perfil seguidor : perfilVisitado.getSeguidoPor()) {
                System.out.print("        " + seguidor.getNOME() + " / ");
            }
        } else {
            System.out.println("        O usuário ainda não tem seguidores.");
        }
        System.out.println();

        System.out.println(perfilVisitado.getNOME() + " segue:\n");

        if (perfilVisitado.getSeguindo().size() > 0) {
            for (Perfil segue : perfilVisitado.getSeguindo()) {
                System.out.print("        " + segue.getNOME() + " / ");
            }
        } else {
            System.out.println("        O usuário ainda não segue ninguém.");
        }
        System.out.println();
        System.out.println("============================================");

        menuDePerfis(perfilVisitado);
    }

    protected void fazerLogin() {
        System.out.println("Insira seu login: ");
        String login = scanner.nextLine();
        if (listaDeUsuarios.isEmpty()) {
            System.out.println("Ainda não existem usuários cadastrados.");
            System.out.println();
            menuInicial();
        } else {
            boolean usuarioEncontrado = false;
            for (int i = 0; i < listaDeUsuarios.size(); i++) {
                if (listaDeUsuarios.get(i).getLOGIN().equalsIgnoreCase(login)) {
                    System.out.println("Olá " + listaDeUsuarios.get(i).getNOME() + " insira sua senha: ");
                    String senha = scanner.nextLine();
                    if (listaDeUsuarios.get(i).verificaSenha(senha)) {
                        System.out.println("Login efetuado com sucesso.");
                        System.out.println();
                        perfilQueEstaLogado = listaDeUsuarios.get(i);
                        menuDoUsuario(perfilQueEstaLogado);
                        usuarioEncontrado = true;
                        break;
                    } else {
                        System.out.println("Senha incorreta, tente novamente");
                        senha = scanner.nextLine();
                        if (listaDeUsuarios.get(i).verificaSenha(senha)) {
                            System.out.println("Login efetuado com sucesso.");
                            perfilQueEstaLogado = listaDeUsuarios.get(i);
                            menuDoUsuario(perfilQueEstaLogado);
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

    protected void cadastrarUsuario() {
        System.out.println("Insira seu email: ");
        String login = recebeEmail(listaDeUsuarios);

        System.out.println("Insira seu nome: ");
        String nome = recebeNome();

        System.out.println("Insira uma senha com  6 ou mais caracteres: ");
        String senha = criaSenha();

        if (senha.equals("ERRO")) {
            System.out.println("A senha digitada não é válida.");
            menuInicial();
        } else if (senha.equals("ERRO2")) {
            System.out.println("As senhas são diferentes, usuário não cadastrado.");
            System.out.println();
            menuInicial();
        } else {
            System.out.println();
            System.out.println("Cadastro realizado com sucesso!");

            Perfil novo = new Perfil(login, nome, senha);
            RedeSocial.listaDeUsuarios.add(novo);
            System.out.println();
            menuInicial();
        }
    }

    protected void exibePostagens(Perfil perfilVisitado) {
        exibePostagens(perfilVisitado, 0);
    }

    protected void exibePostagens(Perfil perfilVisitado, int indexPostagem) {
        System.out.println("============================================");
        System.out.println("Postagens de " + perfilVisitado.getNOME());
        System.out.println();

        for (int i = indexPostagem; i < perfilVisitado.getPosts().size(); i++) {
            Post post = perfilVisitado.getPosts().get(i);
            System.out.println(formatacaoDaData.format(post.getDATA()));
            System.out.println(post.getTEXTO());
            if (post.getCurtidas() == 0) {
                System.out.println("Seja o primeiro a curtir");
            } else if (post.getCurtidas() == 1) {
                System.out.println(post.getCurtidas() + " curtida ");
            } else {
                System.out.println(post.getCurtidas() + " curtidas ");
            }
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
            int acao = recebeAcaoPostagem();
            if (acao == 1) {
                Perfil.curtirPost(post);
                exibePostagens(perfilVisitado, i);
                menuDePerfis(perfilVisitado);
            } else if (acao == 2) {
                System.out.println("Digite seu comentário: ");
                String comentario = scanner.nextLine();
                Perfil.comentarPost(post, comentario, perfilQueEstaLogado, formatacaoDaData);
                exibePostagens(perfilVisitado, i);
            } else if (acao == 4) {
                menuDoUsuario(perfilVisitado);
                break;
            }
        }

        if (perfilVisitado.getPosts().isEmpty()) {
            System.out.println(perfilVisitado.getNOME() + " ainda não tem publicações.");
        } else {
            System.out.println("Não há mais postagens.");
        }
        System.out.println("============================================");

        menuDePerfis(perfilVisitado);
    }

    protected void exibeSeguridores(Perfil perfilVisitado) {
        System.out.println("============================================");
        if (perfilVisitado.getSeguidoPor().isEmpty()) {
            System.out.println(perfilVisitado.getNOME() + " ainda não tem seguidores.\n");
        } else {
            System.out.println("Seguidores de " + perfilVisitado.getNOME());
            System.out.println();
            for (Perfil seguidor : perfilVisitado.getSeguidoPor()) {
                System.out.println(seguidor.getNOME());
                System.out.println();
            }
        }
        System.out.println("============================================");

        menuDePerfis(perfilVisitado);
    }

    protected void exibeSeguindo(Perfil perfilVisitado) {
        System.out.println("============================================");
        if (perfilVisitado.getSeguidoPor().isEmpty()) {
            System.out.println(perfilVisitado.getNOME() + " ainda não segue ninguém.");
        } else {
            System.out.println(perfilVisitado.getNOME() + " segue:");
            for (Perfil segue : perfilVisitado.getSeguindo()) {
                System.out.println(segue.getNOME());
                System.out.println();
            }
        }
        System.out.println("============================================");

        menuDePerfis(perfilVisitado);
    }

    protected void pesquisaUsuarios() {
        System.out.println("Digite um nome ou um começo de nome: ");
        String busca = scanner.nextLine();

        boolean usuarioEncontrado = false;

        for (int i = 0; i < listaDeUsuarios.size(); i++) {
            if (listaDeUsuarios.get(i).getNOME().toUpperCase().startsWith(busca.toUpperCase())) {
                System.out.println((i + 1) + " - " + listaDeUsuarios.get(i).getNOME());
                usuarioEncontrado = true;
            }
        }

        if (usuarioEncontrado) {
            System.out.println();
            System.out.println("Digite o número do usuário que deseja ver o perfil.");
            int posicao = recebePosicao(listaDeUsuarios);
            for (int i = 0; i < listaDeUsuarios.size(); i++) {
                if (i == posicao) {
                    exibePerfil(listaDeUsuarios.get(i));
                }
            }
        } else {
            System.out.println("Não existem usuários que correspondem a busca.");
            System.out.println();
            menuDoUsuario(perfilQueEstaLogado);
        }
    }

    public Date criaData() {
        return new Date(System.currentTimeMillis());
    }

    protected void criaPerfisAutomaticamente(int opcao) {
        if (listaDeUsuarios.size() >= 1 && opcao == 1) {
            String[] emails = {"caio@gmail.com", "celia@gmail.com", "cassio@gmail.com"};
            String[] nomes = {"Caio Santos", "Celia Garcia", "Cassio Ramos"};
            String[] senhas = {"123456", "123456", "123456"};

            for (int i = 0; i < 3; i++) {
                Perfil novo = new Perfil(emails[i], nomes[i], senhas[i]);
                listaDeUsuarios.add(novo);
            }
        }

    }

    protected String recebeNome() {
        String nome = scanner.nextLine();

        if (!(nome.matches("^([a-zA-Z]+\\s*)+$"))) {
            System.out.println("Digite apenas letras.");
            nome = scanner.nextLine();
            if (!(nome.matches("^([a-zA-Z]+\\s*)+$"))) {
                System.out.println("Nome inválido.");
                System.out.println();
                menuInicial();
            }
        }
        return nome;
    }

    protected String recebeEmail(List<Perfil> usuarios) {
        String email = scanner.nextLine();
        String[] verificaEmail = email.split("@");

        if (verificaEmail.length < 2) {
            System.out.println("Email inválido, tente mais uma vez.");
            email = scanner.nextLine();
            verificaEmail = email.split("@");
            if (verificaEmail.length < 2) {
                System.out.println("Email inválido.");
                System.out.println();
                menuInicial();
            }
        }

        for (Perfil usuario : usuarios) {
            if (usuario.getLOGIN().equalsIgnoreCase(email)) {
                System.out.println("Login já cadastrado.");
                System.out.println();
                menuInicial();
                break;
            }
        }

        System.out.println("Confirme seu email: ");
        String confirmacao = scanner.nextLine();
        if (!confirmacao.equalsIgnoreCase(email)) {
            System.out.println("Os emails digitados estão diferentes, tente mais uma vez.");
            confirmacao = scanner.nextLine();
            if (!confirmacao.equalsIgnoreCase(email)) {
                System.out.println("Os emails digitados estão diferentes.");
                System.out.println();
                menuInicial();
            }
        }
        return email;
    }

    protected String criaSenha() {
        String senha = scanner.nextLine();
        if (senha.length() < 6) {
            System.out.println("A senha deve conter pelo menos 6 caracteres, tente novamente.");
            senha = scanner.nextLine();
            if (senha.length() < 6) {
                senha = "ERRO";
            }
        }
        if ((!senha.equals("ERRO"))) {
            System.out.println("Confirme a senha: ");
            String confirmacao = scanner.nextLine();
            System.out.println();
            if (!confirmacao.equals(senha)) {
                System.out.println("Senhas diferentes, confirme novamente.");
                confirmacao = scanner.nextLine();
                if (!confirmacao.equals(senha)) {
                    senha = "ERRO2";
                }
            }
        }
        return senha;
    }

    protected int respostaMenuiIncial() {
        int numero = 0;

        try {
            numero = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Digite um número do menu.");
            System.out.println();
            numero = respostaMenuiIncial();
        }

        while (numero < 0 || numero > 2) {
            System.out.println("Digite 1 para Fazer login, 2 para Cadastrar ou 0 para Sair");
            System.out.println();
            numero = respostaMenuiIncial();
        }
        return numero;
    }

    protected int respostaMenuUsuario() {
        int numero = 0;

        try {
            numero = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Digite um número do menu.");
            System.out.println();
            numero = respostaMenuUsuario();
        }

        while (numero < 0 || numero > 5) {
            System.out.println("Digite 1 para Postar, 2 para Timeline, 3 para buscar usuários, 4 para ver seu perfil ou 0 para Sair");
            System.out.println();
            numero = respostaMenuUsuario();
        }
        return numero;
    }

    protected int respostaMenu() {
        int numero = 0;

        try {
            numero = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Digite apenas números");
            numero = respostaMenuiIncial();
        }

        while (numero < 1 || numero > 6) {
            System.out.println("Digite uma opção válida.");
            numero = respostaMenuiIncial();
        }
        return numero;
    }

    protected int recebePosicao(List tamanhoArray) {
        int posicao = 0;

        try {
            posicao = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Digite apenas números.");
            posicao = recebePosicao(tamanhoArray);
        }

        while (posicao > tamanhoArray.size()) {
            System.out.println("Digite um número válido.");
            posicao = recebePosicao(tamanhoArray);
            posicao++;
        }
        return posicao - 1;
    }

    protected int recebeAcaoPostagem() {
        int acao = 0;

        try {
            acao = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Digite apenas números.");
            acao = recebeAcaoPostagem();
        }

        while (acao < 1 || acao > 4) {
            System.out.println("Digite um número válido.");
            acao = recebeAcaoPostagem();
        }
        return acao;
    }
}