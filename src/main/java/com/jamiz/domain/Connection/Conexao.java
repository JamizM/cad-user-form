package com.jamiz.domain.Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Conexao {

    private static Connection conn = null;
    private static PreparedStatement preparedStatement;
    private static ResultSet resultSet;

    public static Connection getConection(){
        String url = "jdbc:postgresql://localhost:5432/postgres";
        String name = "postgres";
        String password = "Jhg27@";

        try{
            conn = DriverManager.getConnection(url, name, password);
            System.out.println("Database Connected");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
        return conn;
    }

    public static void fecharConexao(Connection con){
        if(con != null){
            try{
                System.out.println("Conexao Fechada");
                con.close();
            } catch (Exception e){
                System.out.println("Erro: " + e.getMessage());
            }
        }
    }

    public static void fecharConexao(Connection con, PreparedStatement preparador) {
        if (preparador != null) {
            try {
                preparador.close();
            } catch (SQLException e) {
                System.err.println("Erro: " + e);

            } finally {
                fecharConexao(con);
            }
        }
    }

    public static void fecharConexao(Connection con, PreparedStatement preparador, ResultSet resultado) {
        if (resultado != null) {
            try {
                resultado.close();
            } catch (SQLException e) {
                System.err.println("Erro: " + e);

            } finally {
                fecharConexao(con, preparador);
            }
        }
    }
}
