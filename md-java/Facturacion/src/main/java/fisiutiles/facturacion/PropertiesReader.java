package fisiutiles.facturacion;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class PropertiesReader {

    private static final String PROPERTIES_FILE_NAME = "config.properties";
    private String URL = "./src/main/java/resource/config.properties";

    public Properties getProperties() {
        Properties properties = new Properties();
        
        try (FileInputStream inputStream = new FileInputStream(URL)) {
            if (inputStream != null) {
                properties.load(inputStream);
            }
        } catch (IOException e) {
            System.out.println("Archivo de propiedades '" + PROPERTIES_FILE_NAME + "' no encontrado.");
        }
        
        return properties;
    }
}
