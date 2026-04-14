/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package serverrest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author camilli.cristiano
 */
public class MockDataService {
    
    public static List<Map<String, Object>> getArnie() {
        List<Map<String, Object>> arnie = new ArrayList<>();
        
        Map<String, Object> a1 = new HashMap<>();
        a1.put("id", 1);
        a1.put("codice", "AR-001");
        a1.put("stato", "Attiva");
        
        arnie.add(a1);
        return arnie;
    }

    public static List<Map<String, Object>> getUtenti() {
        List<Map<String, Object>> utenti = new ArrayList<>();
        utenti.add(Map.of("id", 1, "username", "apicoltore_rossi", "ruolo", "admin"));
        return utenti;
    }
    
    // Devo aggiungere altri metodi
}
