package com.jamiz.domain.Connection;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class Conexao {

    private static Connection conn = null;
    private static PreparedStatement preparedStatement;
    private static ResultSet resultSet;

    public static Connection getConection(){

        Properties props = new Properties(); //importar classe Properties para carregar o arquivo config.properties
        try(FileInputStream fis = new FileInputStream("src/main/resources/config.properties")){ //usar FileInputStream para ler o dado do arquivo
            props.load(fis);
        } catch (IOException e){
            System.out.println("erro " + e);
            return null;
        }
        String url = props.getProperty("db.url");
        String name = props.getProperty("db.user");
        String password = props.getProperty("db.password");

        //conexao do DB
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
