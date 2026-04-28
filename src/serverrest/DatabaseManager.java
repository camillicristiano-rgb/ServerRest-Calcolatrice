/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package serverrest;

import java.sql.Connection; // L'UNICO necessario
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author camilli.cristiano
 */
public class DatabaseManager {
    // Parametri per il database 'apicoltura'
    private static final String URL = "jdbc:mysql://localhost:3306/apicoltura?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = ""; 

    public static Connection getConnection() throws SQLException {
        // Carica esplicitamente il driver (opzionale ma consigliato per evitare errori)
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver MySQL non trovato nel progetto!", e);
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
