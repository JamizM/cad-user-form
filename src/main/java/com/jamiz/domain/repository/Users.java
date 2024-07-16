package com.jamiz.domain.repository;

import com.jamiz.domain.Connection.Conexao;
import com.jamiz.domain.entity.User;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.security.MessageDigest;

public class Users {

    Connection conn = Conexao.getConection();

    public void inserirUsuario(String nome, Integer telefone, String senha){
        String sql = "INSERT INTO tb_user (nome, telefone, senha) VALUES (?, ?, ?)";
        try{
            PreparedStatement preparador = conn.prepareStatement(sql); //prepara pre-comando para colocar usuario em DB

            //criptografia - java.security
            MessageDigest algoritmoCriptografiaSenha = MessageDigest.getInstance("MD5"); //gera 128 bits
            byte[] criptografaSenha = algoritmoCriptografiaSenha.digest(senha.getBytes("UTF-8")); //pegamos cada byte e criptografamos

            //passando par String usando append() em cada byte
            StringBuilder hexString = new StringBuilder();
            for (byte bytesSenha : criptografaSenha){
                hexString.append(String.format("%02X", 0xFF & bytesSenha));
            }
            String senhaCriptografada = hexString.toString();

            preparador.setString(1, nome);
            preparador.setInt(2, telefone);
            preparador.setString(3, senhaCriptografada);

            preparador.execute();
            preparador.close();
            System.out.println("Usuario cadastrado com sucesso");
        } catch (SQLException e){
            System.out.println("Erro: " + e);
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            System.out.println("Erro criptografia: " + e);
        }
    }

    public Long excluirUsuario(Long id){
        String sql = "DELETE FROM tb_user WHERE id_user = ?";
        try{
            PreparedStatement preparador = conn.prepareStatement(sql);
            preparador.setLong(1, id);

            int linhasAfetadasDB = preparador.executeUpdate();
            if(linhasAfetadasDB == 0){
                return (long) -1;
            }

            preparador.execute();
            preparador.close();
            System.out.println("usuario deletado com sucesso");
        } catch (SQLException e){
            System.out.println("Erro: " + e);
        }
        return id;
    }

    public boolean alterarUsuario(String nome, Integer telefone, Long id){
        String sql = "UPDATE tb_user SET nome = ?, telefone = ? WHERE id_user = ?";
        try{
            PreparedStatement preparador = conn.prepareStatement(sql);
            preparador.setString(1, nome);
            preparador.setInt(2, telefone);
            preparador.setLong(3, id);

            int linhasAfetadas = preparador.executeUpdate();

            if(linhasAfetadas > 0){
                System.out.println("usuario atualizado");
                return true;
            }

            preparador.close();
            System.out.println("usuario atualizado");
        } catch (SQLException e ){
            System.out.println("Erro: " + e);
        }
        //erro capturado, como comando insert nao retornada nada, e usamos o ResultSet rs -> rs.next() para ser o cursor que devolve um objeto;
        //assim apresentando erro
        return false;
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
                    Integer colunaTelefone = rs.getInt("telefone");
                    System.out.printf("Id: %s - Nome: %s - Telefone: %s%n", colunaId, colunaNome, colunaTelefone);
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
