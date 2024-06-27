package com.jamiz.domain.controller;

import com.jamiz.domain.Connection.Conexao;
import com.jamiz.domain.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Users {

    Connection conn = Conexao.getConection();

    public void inserirUsuario(User user){
        String sql = "INSERT INTO tb_user (nome, telefone) VALUES (?, ?)";
        try{
            PreparedStatement preparador = conn.prepareStatement(sql); //prepara pre-comando para colocar usuario em DB
            preparador.setString(1, user.getNome());
            preparador.setInt(2, user.getTelefone());
            preparador.execute();
            preparador.close();
            System.out.println("Usuario cadastrado com sucesso");
        } catch (SQLException e){
            System.out.println("Erro: " + e);
        }
    }

    public void excluirUsuario(User user){
        String sql = "DELETE FROM tb_user WHERE id_user = ?";
        try{
            PreparedStatement preparador = conn.prepareStatement(sql);
            preparador.setLong(1, user.getId());

        } catch (SQLException e){
            System.out.println("Erro: " + e);
        }
    }

    public void alterarUsuario(User user){

    }

    public void buscarTodos(){

    }

    public void apagarTodos(){

    }


}
