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
                System.out.println("1 - inserir usuario \n 2 - Excluir Usuario\n 3 - alterar usuario \n 4 - ver todos os usuarios\n 5 - apagar todos os usuarios");
                int resp = sc.nextInt();

                if(resp < 0 || resp > 5){
                    System.out.println("Opções invalidas, Digite opção válida");
                }

                switch (resp){
                    case 1: users.inserirUsuario("joao paulo", "11222223333", "12345");
                }

            }
        }
    }
}
