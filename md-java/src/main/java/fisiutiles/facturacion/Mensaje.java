package fisiutiles.facturacion;

import org.json.JSONObject;

public class Mensaje {
 
    private int estado;
    private String contenido;

    public Mensaje() {
    }

    public Mensaje(int estado, String contenido) {
        this.estado = estado;
        this.contenido = contenido;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    @Override
    public String toString() {
        return "Mensaje [estado=" + estado + ", contenido=" + contenido + "]";
    }
}
