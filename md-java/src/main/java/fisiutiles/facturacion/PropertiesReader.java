package fisiutiles.facturacion;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public final class PropertiesReader {

    private static final String PROPERTIES_FILE_NAME = "config.properties";
    private static final String URL = "./src/main/java/resource/config.properties";

    public Properties getProperties() {
        var properties = new Properties();
        
        try (var inputStream = new FileInputStream(URL)) {
            if (inputStream != null) {
                properties.load(inputStream);
            }
        } catch (IOException e) {
            System.out.println("Archivo de propiedades '" + PROPERTIES_FILE_NAME + "' no encontrado.");
        }
        
        return properties;
    }
}
