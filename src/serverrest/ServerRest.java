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
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;



/**
 * Server REST per Apicoltura REST
 * 
 * @author camilli.cristiano
 */
public class ServerRest {

    /**
     * Avvia il server REST sulla porta specificata
     * 
     * @param porta la porta su cui avviare il server
     */
    public static void avviaServer(int porta) {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(porta), 0);
            
            // Istanza unica del gestore generico
            GenericRestHandler apiHandler = new GenericRestHandler();
            
            // Registriamo tutti gli endpoint delle tue tabelle sullo stesso Handler
            server.createContext("/api/apiari", apiHandler);
            server.createContext("/api/arnie", apiHandler);
            server.createContext("/api/sensoriarnia", apiHandler);
            server.createContext("/api/sensori", apiHandler);
            server.createContext("/api/rilevazioni", apiHandler);
            server.createContext("/api/tipirilevazione", apiHandler);
            server.createContext("/api/notifiche", apiHandler);
            server.createContext("/api/utenti", apiHandler);
            
            server.createContext("/", ServerRest::gestisciBenvenuto);

            server.setExecutor(null);
            server.start();
            
            System.out.println("Server APERTO su tutte le tabelle sulla porta " + porta);
            
        } catch (IOException e) {
            System.err.println("Errore: " + e.getMessage());
        }
    }
    
    private static void gestisciBenvenuto(HttpExchange exchange) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        
        Map<String, Object> info = new HashMap<>();
        info.put("messaggio", "Benvenuto nel Sistema Gestionale Apicoltura");
        info.put("versione", "2.0.0");
        
        String[] tabelle = {"apiari", "arnie", "sensori", "rilevazioni", "notifiche", "utenti"};
        info.put("risorse_disponibili", tabelle);
        
        String jsonRisposta = gson.toJson(info);
        
        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
        byte[] bytes = jsonRisposta.getBytes();
        exchange.sendResponseHeaders(200, bytes.length);
        exchange.getResponseBody().write(bytes);
        exchange.getResponseBody().close();
    }
}