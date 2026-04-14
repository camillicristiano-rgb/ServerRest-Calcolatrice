/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package serverrest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author camilli.cristiano
 */
public class ApiarioHandler implements HttpHandler {
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        String path = exchange.getRequestURI().getPath();
        Object risposta = null;

        try {
            if (method.equalsIgnoreCase("GET")) {
                // Logica GET
                switch (path) {
                    case "/api/users": case "/api/utenti":
                        risposta = MockDataService.getUtenti();
                        break;
                    case "/api/arnie":
                        risposta = MockDataService.getArnie();
                        break;
                    default:
                        inviaErrore(exchange, 404, "Risorsa non trovata");
                        return;
                }
                inviaRisposta(exchange, 200, gson.toJson(risposta));

            } else if (method.equalsIgnoreCase("POST")) {
                // Logica POST
                
                // Leggo il corpo della richiesta
                java.io.InputStreamReader isr = new java.io.InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8);
                java.io.BufferedReader br = new java.io.BufferedReader(isr);

                // Converto il JSON ricevuto in una mappa generica
                Map<String, Object> datiRicevuti = gson.fromJson(br, Map.class);

                // Simulo il salvataggio
                Map<String, Object> mockResponse = new HashMap<>();
                mockResponse.put("messaggio", "Inserimento simulato con successo");
                mockResponse.put("dati_salvati", datiRicevuti);
                mockResponse.put("id_generato", (int)(Math.random() * 1000)); // ID finto

                inviaRisposta(exchange, 201, gson.toJson(mockResponse)); // 201 = Created

            } else {
                inviaErrore(exchange, 405, "Metodo non consentito");
            }
        } catch (Exception e) {
            inviaErrore(exchange, 500, "Errore interno: " + e.getMessage());
        }
    }

    private void inviaRisposta(HttpExchange exchange, int codice, String json) throws IOException {
        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
        exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
        byte[] bytes = json.getBytes(StandardCharsets.UTF_8);
        exchange.sendResponseHeaders(codice, bytes.length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(bytes);
        }
    }

    private void inviaErrore(HttpExchange exchange, int codice, String msg) throws IOException {
        Map<String, Object> err = new HashMap<>();
        err.put("errore", msg);
        err.put("status", codice);
        inviaRisposta(exchange, codice, gson.toJson(err));
    }
}
