import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Validacoes {

    static Scanner sc = new Scanner(System.in);
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
            verificaEmail = email.split("@");
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

    public static Date criaData() {
        SimpleDateFormat formatter= new SimpleDateFormat("dd/MM/yyyy 'às' HH:mm");
        Date data = new Date(System.currentTimeMillis());
        return data;
    }
}


