package br.edu.univas;

import java.util.List;
import java.util.Scanner;

public class StartApp {

    public static void main(String[] args) {
        String url = "files/biblioteca.csv";
        iniciarBiblioteca(url);
    }
    public static void iniciarBiblioteca(String url) {
        Scanner scanner = new Scanner(System.in);
        Biblioteca biblioteca = new Biblioteca();
        boolean start = true;
        do {
            System.out.println("""
                    Seja bem-vindo a biblioteca, escolha uma das funcionalidades:\s
                    1 - Cadastrar Livro\s
                    2 - Remover Livro\s
                    3 - Buscar Livro\s
                    4 - Gerar Relatório\s
                    0 - Sair""");
            int escolha = scanner.nextInt();
            switch (escolha) {
                default -> System.out.println("Escolha incorreta!");
                case 0 -> {
                    System.out.println("Bye bye");
                    start = false;
                }
                case 1 -> cadastrarLivro(url, scanner, biblioteca);
                case 2 -> excluirLivro(url, scanner, biblioteca);
                case 3 -> buscarLivro(url, scanner, biblioteca);
                case 4 -> gerarRelatorio(url, biblioteca);
            }
        } while(start);
        scanner.close();
    }
    public static void cadastrarLivro(String url, Scanner scanner, Biblioteca biblioteca) {
        scanner.nextLine();
        String[] conteudoLivro = new String[3];
        System.out.print("Por favor, digite o nome do livro: ");
        conteudoLivro[0] = scanner.nextLine();
        System.out.print("Por favor, digite o número de páginas: ");
        int numeroPaginas = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Por favor, digite o nome do autor: ");
        conteudoLivro[1] = scanner.nextLine();
        System.out.print("Por favor, digite a área de interesse: ");
        conteudoLivro[2] = scanner.nextLine();
        biblioteca.CadastrarBiblioteca(conteudoLivro[0].trim(),String.valueOf(numeroPaginas).trim(),conteudoLivro[1].trim(),conteudoLivro[2].trim(), url);
        System.out.println(conteudoLivro[0] + " - Livro Cadastrado");
    }

    public static void buscarLivro(String url, Scanner scanner, Biblioteca biblioteca) {
        if(Biblioteca.ArquivoExiste(url)){
            List<String> existentes = biblioteca.LinhaExistentes(url);
            scanner.nextLine();
            System.out.print("""
                Qual será a forma de busca para o livro?\s
                1- Nome do Livro\s
                2- Nome do Autor\s
                3- Área de Interesse\s
                Escolha:\s""");
            int escolha = scanner.nextInt();
            if(escolha == 1) buscaPorNomeLivro(scanner, existentes);
            else if(escolha == 2) buscaPorNomeAutor(scanner, existentes);
            else if(escolha == 3) buscaPorAreaInteresse(scanner, existentes);
            else System.out.println("Escolha Incorreta!");
        } else
            System.out.println("Biblioteca não cadastrada! \n");
    }

    public static void buscaPorNomeLivro(Scanner scanner, List<String> existentes) {
        scanner.nextLine();
        System.out.print("Qual o nome do livro? ");
        String nomeDoLivro = scanner.nextLine();
        boolean encontrado = false;
            for (int i = 1; i < existentes.size(); i++) {
                if (existentes.get(i).split(",")[0].trim().equals(nomeDoLivro.trim())) {
                    System.out.println(existentes.get(i));
                    encontrado = true;
                }
            }
            if(!encontrado) System.out.println("Livro não encontrado!");
        }
    public static void buscaPorNomeAutor(Scanner scanner, List<String> existentes) {
        scanner.nextLine();
        System.out.print("Qual o nome do autor? ");
        String nomeDoAutor = scanner.nextLine();
        boolean encontrado = false;
            for (int i = 1; i < existentes.size(); i++) {
                if (existentes.get(i).split(",")[2].trim().equals(nomeDoAutor.trim())) {
                    System.out.println(existentes.get(i));
                    encontrado = true;
                }
            }
        if(!encontrado) System.out.println("Livro com o nome do autor não encontrado!");
    }
    public static void buscaPorAreaInteresse(Scanner scanner, List<String> existentes) {
        scanner.nextLine();
        System.out.print("Qual à area de interesse? ");
        String areaDeInteresse = scanner.nextLine();
        boolean encontrado = false;
        for (int i = 1; i < existentes.size(); i++) {
            if (existentes.get(i).split(",")[3].trim().equals(areaDeInteresse.trim())) {
                System.out.println(existentes.get(i));
                encontrado = true;
            }
        }
        if(!encontrado) System.out.println("Livro com o área de interesse não encontrado!");
    }


    public static void excluirLivro(String url, Scanner scanner, Biblioteca biblioteca) {
        if(Biblioteca.ArquivoExiste(url)) {
            List<String> existentes = biblioteca.LinhaExistentes(url);
            String[] livro = new String[4];
            String[] estruturaLivro = {
                    "Nome do Livro",
                    "Número de Páginas",
                    "Nome do Autor",
                    "Área de Interesse"
            };
            scanner.nextLine();
            for (int i = 0; i < livro.length; i++) {
                System.out.print("Por favor, digite o(a) " + estruturaLivro[i] + ": ");
                livro[i] = scanner.nextLine();
                if (livro[i].trim().equalsIgnoreCase(estruturaLivro[i].trim())) {
                    excluirLivro(url, scanner, biblioteca);
                }
            }
            boolean encontrado = false;
            for (int i = 1; i < existentes.size(); i++) {
                if (existentes.get(i).split(",")[0].trim().equals(livro[0].trim()) && existentes.get(i).split(",")[1].trim().equals(livro[1].trim()) && existentes.get(i).split(",")[2].trim().equals(livro[2].trim()) && existentes.get(i).split(",")[3].trim().equals(livro[3].trim())) {
                    existentes.remove(existentes.get(i));
                    biblioteca.removeLivroBiblioteca(existentes, url);
                    encontrado = true;
                }
            }
            if(!encontrado) System.out.println("Livro não encontrado! \n");
        } else
            System.out.println("Biblioteca não cadastrada! \n");
    }

    public static void gerarRelatorio(String url, Biblioteca biblioteca) {
        if (Biblioteca.ArquivoExiste(url)) {
            List<String> todasAsLinhas = biblioteca.LinhaExistentes(url);
            for (String unicaLinha : todasAsLinhas) {
                System.out.println(unicaLinha);
            }
        } else
            System.out.println("Biblioteca não cadastrada! \n");
    }
}