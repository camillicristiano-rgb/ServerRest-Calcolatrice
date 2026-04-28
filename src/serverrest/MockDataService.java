/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package serverrest;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author camilli.cristiano
 */
public class MockDataService {
    
    // Recupera le Arnie dal DB
    public static List<Map<String, Object>> getArnie() {
        List<Map<String, Object>> lista = new ArrayList<>();
        String sql = "SELECT * FROM arnia";

        // Adesso 'Connection' sarà java.sql.Connection
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Map<String, Object> riga = new HashMap<>();
                riga.put("id", rs.getInt("arn_id"));
                riga.put("data_installazione", rs.getDate("arn_dataInst"));
                riga.put("piena", rs.getBoolean("arn_piena"));
                riga.put("mac_address", rs.getString("arn_MacAddress"));
                riga.put("id_apiario", rs.getInt("api_id"));
                lista.add(riga);
            }
        } catch (SQLException e) {
            System.err.println("Errore getArnie: " + e.getMessage());
        }
        return lista;
    }

    // Recupera gli Utenti dal DB
    public static List<Map<String, Object>> getUtenti() {
        List<Map<String, Object>> lista = new ArrayList<>();
        String sql = "SELECT ute_id, ute_username, ute_admin FROM utente";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Map<String, Object> riga = new HashMap<>();
                riga.put("id", rs.getInt("ute_id"));
                riga.put("username", rs.getString("ute_username"));
                riga.put("is_admin", rs.getBoolean("ute_admin"));
                lista.add(riga);
            }
        } catch (SQLException e) {
            System.err.println("Errore getUtenti: " + e.getMessage());
        }
        return lista;
    }

    // Recupera gli Apiari dal DB
    public static List<Map<String, Object>> getApiari() {
        List<Map<String, Object>> lista = new ArrayList<>();
        String sql = "SELECT * FROM apiario";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Map<String, Object> riga = new HashMap<>();
                riga.put("id", rs.getInt("api_id"));
                riga.put("nome", rs.getString("api_nome"));
                riga.put("luogo", rs.getString("api_luogo"));
                lista.add(riga);
            }
        } catch (SQLException e) {
            System.err.println("Errore getApiari: " + e.getMessage());
        }
        return lista;
    }
}
