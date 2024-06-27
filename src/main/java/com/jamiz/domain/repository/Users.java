package com.jamiz.domain.repository;

import com.jamiz.domain.Connection.Conexao;
import com.jamiz.domain.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Users {

    Connection conn = Conexao.getConection();

    public void inserirUsuario(String nome, String telefone, String senha){
        String sql = "INSERT INTO tb_user (nome, telefone, senha) VALUES (?, ?, ?)";
        try{
            PreparedStatement preparador = conn.prepareStatement(sql); //prepara pre-comando para colocar usuario em DB
            preparador.setString(1, nome);
            preparador.setString(2, telefone);
            preparador.setString(3, senha);
            preparador.execute();
            preparador.close();
            System.out.println("Usuario cadastrado com sucesso");
        } catch (SQLException e){
            System.out.println("Erro: " + e);
        }
    }

    public void excluirUsuario(Long id){
        String sql = "DELETE FROM tb_user WHERE id_user = ?";
        try{
            PreparedStatement preparador = conn.prepareStatement(sql);
            preparador.setLong(1, id);
            preparador.execute();
            preparador.close();
            System.out.println("usuario deletado com sucesso");

        } catch (SQLException e){
            System.out.println("Erro: " + e);
        }
    }

    public void alterarUsuario(String nome, String telefone, Long id){
        String sql = "UPDATE tb_user SET nome = ?, telefone = ? WHERE id = ?";
        try{
            PreparedStatement preparador = conn.prepareStatement(sql);
            preparador.setString(1, nome); //substitui a primeira interrogação do comando sql (nome = ?)
            //pela linha do user.getNome();
            preparador.setString(2, telefone); //substitui a segunda itnerrogacao
            preparador.setLong(3, id);
            preparador.execute(); //executa consulta sql
            preparador.close();
            System.out.println("usuario atualzado");

        } catch (SQLException e ){
            System.out.println("Erro: " + e);
        }
    }

    public List<User> buscarTodos(){
        //usado List<User> para poder agrupar todos os elementos e assim ter uma consulta mais rapida
        String sql = "SELECT * FROM tb_user";

        List<User> lista = new ArrayList<User>();
        try{
            PreparedStatement preparador = conn.prepareStatement(sql);
            ResultSet rs = preparador.executeQuery();

            //sem retornar senha, por boa pratica
            while(rs.next()){
                User user = new User();
                user.setId(rs.getLong("id_user"));
                user.setNome(rs.getString("nome"));
                user.setTelefone(rs.getString("telefone"));

                lista.add(user); //retorna lista de usuario
            }
            if (!rs.next()) {
                System.out.println("Não existem dados no banco para serem exibidos");
            }
            preparador.close();
        } catch (SQLException e){
            System.out.println("Erro: " + e);
        }
        return lista;
    }

    public void apagarTodos(){
        String sql = "DELETE FROM tb_user";
        try{
            PreparedStatement preparador = conn.prepareStatement(sql);
            preparador.execute();
            if(!preparador.execute() == false){
                System.out.println("tabela vazia");
            }
            else{
                System.out.println("dados concluidos com sucesso");
            }
            preparador.close();
        } catch (SQLException e){
            System.out.println("Erro: " + e);
        }
    }
}
