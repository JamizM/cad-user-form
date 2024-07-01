package com.jamiz.domain.repository;

import com.jamiz.domain.Connection.Conexao;
import com.jamiz.domain.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

    public Long excluirUsuario(Long id){
        String sql = "DELETE FROM tb_user WHERE id_user = ?";
        String sqlOrganizarId = "ALTER SEQUENCE tb_user_id_user_seq RESTART WITH 1;";
        try{
            PreparedStatement preparadorArrrumarId = conn.prepareStatement(sqlOrganizarId);
            PreparedStatement preparador = conn.prepareStatement(sql);
            preparador.setLong(1, id);

            int linhasAfetadasDB = preparador.executeUpdate();
            if(linhasAfetadasDB == 0){
                return (long) -1;
            }

            preparador.execute();
            preparadorArrrumarId.execute();
            preparador.close();
            System.out.println("usuario deletado com sucesso");
        } catch (SQLException e){
            System.out.println("Erro: " + e);
        }
        return id;
    }

    public boolean alterarUsuario(String nome, String telefone, Long id){
        String sql = "UPDATE tb_user SET nome = ?, telefone = ? WHERE id_user = ?";
        try{
            PreparedStatement preparador = conn.prepareStatement(sql);
            preparador.setString(1, nome);
            preparador.setString(2, telefone);
            preparador.setLong(3, id);

            ResultSet rs = preparador.executeQuery();

            if (!rs.next()){ //se caso nao tiver dado na tabela
                return false;
            }

            preparador.execute(); //executa consulta sql
            preparador.close();
            System.out.println("usuario atualizado");

        } catch (SQLException e ){
            System.out.println("Erro: " + e);
        }
        return true;
    }

    public List<User> buscarTodos(){
        String sql = "SELECT * FROM tb_user";
        try{
            PreparedStatement preparador = conn.prepareStatement(sql);
            ResultSet rs = preparador.executeQuery();

            if (!rs.next()) {
                return null;
            } else {
                do {
                    String colunaId = rs.getString("id_user");
                    String colunaNome = rs.getString("nome");
                    String colunaTelefone = rs.getString("telefone");
                    System.out.printf("colunaId: %s - colunaNome: %s - colunaTelefone: %s%n", colunaId, colunaNome, colunaTelefone);
                } while(rs.next());
            }
            preparador.close();
        } catch (SQLException e){
            System.out.println("Erro: " + e);
        }
        return null;
    }

    public void apagarTodos(){
        String sql = "DELETE FROM tb_user";
        try{
            PreparedStatement preparador = conn.prepareStatement(sql);

            int linhasAfetadas = preparador.executeUpdate();

            if(linhasAfetadas == 0){
                System.out.println("tabela vazia");
            }
            else{
                System.out.println("Usuarios Deletados");
            }
            preparador.execute();
            preparador.close();
        } catch (SQLException e){
            System.out.println("Erro: " + e);
        }
    }
}
