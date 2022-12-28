package fisiutiles.facturacion;

import com.google.gson.JsonObject;

public class Mensaje {
 
    private int estado;
    private JsonObject contenido;

    public Mensaje() {
    }

    public Mensaje(int estado, JsonObject contenido) {
        this.estado = estado;
        this.contenido = contenido;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public JsonObject getContenido() {
        return contenido;
    }

    public void setContenido(JsonObject contenido) {
        this.contenido = contenido;
    }

    @Override
    public String toString() {
        return "Mensaje [estado=" + estado + ", contenido=" + contenido + "]";
    }
}
