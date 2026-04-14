/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */

package serverrest;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;



/**
 * Server REST per la calcolatrice
 * 
 * @author delfo
 */
public class ServerRest {

    /**
     * Avvia il server REST sulla porta specificata
     * 
     * @param porta la porta su cui avviare il server
     */
    public static void avviaServer(int porta) {
        try {
            // Crea il server sulla porta specificata
            HttpServer server = HttpServer.create(new InetSocketAddress(porta), 0);
            
            // Registra gli handler per gli endpoint
            ApiarioHandler apiHandler = new ApiarioHandler();
        
            server.createContext("/api/users", apiHandler);
            server.createContext("/api/arnie", apiHandler);
            server.createContext("/api/notifiche", apiHandler);
            server.createContext("/api/tipirilevazione", apiHandler);
            server.createContext("/api/rilevazioni", apiHandler);
            server.createContext("/api/sensori", apiHandler);
            server.createContext("/api/apiari", apiHandler);

            server.createContext("/", ServerRest::gestisciBenvenuto);

            server.setExecutor(null);
            server.start();

            System.out.println("Server Apiario avviato sulla porta: " + porta);
            System.out.println("Esempio: http://localhost:" + porta + "/api/arnie");
        
        } catch (IOException e) {
            System.err.println("Errore nell'avvio del server: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Gestisce l'endpoint di benvenuto che fornisce informazioni sull'API
     * 
     * @param exchange l'oggetto HttpExchange per gestire la richiesta/risposta
     * @throws IOException in caso di errori durante la comunicazione
     */
    private static void gestisciBenvenuto(HttpExchange exchange) throws IOException {
        // Istanza GSON per la risposta
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        // Mappa principale delle informazioni
        Map<String, Object> info = new LinkedHashMap<>(); // LinkedHashMap mantiene l'ordine di inserimento
        info.put("messaggio", "Benvenuto nel Sistema Gestionale Apiari REST API");
        info.put("stato_sistema", "In fase di sviluppo (Mock Data Mode)");
        info.put("versione", "2.0.0");
        info.put("tecnologia", "Java + Sun HttpServer + GSON");

        // Mappa degli Endpoints disponibili
        Map<String, String> endpoints = new LinkedHashMap<>();
        endpoints.put("utenti", "/api/users");
        endpoints.put("apiari", "/api/apiari");
        endpoints.put("arnie", "/api/arnie");
        endpoints.put("sensori", "/api/sensori");
        endpoints.put("sensori_arnia", "/api/sensoriarnia");
        endpoints.put("tipi_rilevazione", "/api/tipirilevazione");
        endpoints.put("rilevazioni", "/api/rilevazioni");
        endpoints.put("notifiche", "/api/notifiche");

        info.put("endpoints_disponibili", endpoints);

        // Informazioni aggiuntive sulla struttura dati
        Map<String, String> note = new HashMap<>();
        note.put("formato", "Tutte le risposte sono in formato JSON");
        note.put("database", "Non ancora collegato - Dati restituiti da classi Mock");
        info.put("note_sviluppo", note);

        // Conversione in JSON e invio
        String jsonRisposta = gson.toJson(info);

        // Configurazione Header e invio risposta
        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
        exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*"); // Utile per test da browser/frontend

        byte[] bytes = jsonRisposta.getBytes(StandardCharsets.UTF_8);
        exchange.sendResponseHeaders(200, bytes.length);

        try (OutputStream os = exchange.getResponseBody()) {
            os.write(bytes);
        }
    }
}