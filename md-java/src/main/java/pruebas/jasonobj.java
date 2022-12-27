package pruebas;

import com.google.gson.Gson;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

public class jasonobj {
    
    public static void main(String[] args) {
        JSONObject obj = new JSONObject();
        
        obj.put("estado", 1);
        
        JSONObject cont = new JSONObject();
        
        cont.put("codigoDeCliente", "14266484");
        cont.put("nombreDeCliente", "paco");
        cont.put("rucDeCliente", "ruc511");
        
        
        ArrayList<Integer> lista = new ArrayList<>();
        lista.add(5);
        lista.add(3);
        
        String cadena = new Gson().toJson(lista);
        System.out.println(cadena);
        
        JSONArray arr = new JSONArray(cadena);
        
        
        cont.put("items", arr);
        
        String c = JSONObject.valueToString(cont);
        
        
        obj.put("contenido", c);
        

        /*// Basic strings
        myObject.put("name", "Carlos");
        myObject.put("last_name", "Carlos");
        
        // Primitive values
        myObject.put("age", new Integer(21));
        myObject.put("bank_account_balance", new Double(20.2));
        myObject.put("is_developer", new Boolean(true));
        
        // Key with array
        double[] myList = {1.9, 2.9, 3.4, 3.5};
        myObject.put("number_list", myList);
        
        // Object inside object
        JSONObject subdata = new JSONObject();
        myObject.put("extra_data", subdata);*/

        // Generate JSON String
        System.out.print(obj);
    }
}
