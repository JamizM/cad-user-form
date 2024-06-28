package com.jamiz.domain;

import com.jamiz.domain.Connection.Conexao;
import com.jamiz.domain.repository.Users;

import java.sql.Connection;
import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Users users = new Users();
        Connection conexao = Conexao.getConection();
        boolean formAberto = true;

        while(formAberto){
            if(conexao != null){
                System.out.println("\nDigite os numeros abaixo para escolher o que fazer\n");
                System.out.println("1 - inserir usuario \n 2 - Excluir Usuario\n 3 - alterar usuario \n 4 - ver todos os usuarios\n 5 - apagar todos os usuarios\n 6 - Sair e Fechar Conexao");
                int escolha = sc.nextInt();

                if(escolha < 0 || escolha > 5){
                    System.out.println("Opções invalidas, Digite opção válida");
                }

                switch (escolha){
                    case 1:
                        System.out.println("escreva seu nome, telefone e senha");
                        System.out.println("nome: ");
                        String nomeDB = sc.next();
                        System.out.println("telefone: ");
                        String telefoneDB = sc.next();
                        System.out.println("senha: ");
                        String senhaDB = sc.next();
                        users.inserirUsuario(nomeDB, telefoneDB, senhaDB);
                        break;

                    case 2:
                        System.out.println("escreva o ID para ser removido");
                        Long idDB = sc.nextLong();
                        users.excluirUsuario(idDB);
                        break;

                    case 3:
                        System.out.println("Escreva seu nome e telefone e id para serem trocados");
                        String nomeTrocadoDB = sc.nextLine();
                        String telefoneTrocadoDB = sc.nextLine();
                        Long idDBLocalizacao = sc.nextLong();
                        users.alterarUsuario(nomeTrocadoDB, telefoneTrocadoDB, idDBLocalizacao);
                        break;

                    case 4:
                        users.buscarTodos();
                        break;

                    case 5:
                        users.apagarTodos();
                        System.out.println("usuarios apagados do DB");
                        break;


                    case 6:
                        formAberto = true;
                        Conexao.fecharConexao(conexao);
                }

            }
        }
    }
}
