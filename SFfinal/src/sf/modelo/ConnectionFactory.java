/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sf.modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    private static ConnectionFactory  instance = new ConnectionFactory();

    public static final String URL = "jdbc:mysql://localhost:3306/empresa";
    public static final String USER = "root";
    public static final String PASSWORD = "";
    public static final String DRIVER_CLASS = "org.gjt.mm.mysql.Driver";

    private ConnectionFactory() {
        try {
            Class.forName(DRIVER_CLASS);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();

        }
    }
    
    

    private Connection createConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);

        } catch (SQLException e) {
            System.out.println("Erro: Erro na conexão com o banco");

        }
        return connection;
    }

     
    public static Connection getConnection(){
    return instance.createConnection();
    
    }
}
