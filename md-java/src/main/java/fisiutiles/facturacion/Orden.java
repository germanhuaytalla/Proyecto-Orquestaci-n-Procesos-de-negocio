package fisiutiles.facturacion;

import java.util.ArrayList;

public class Orden {
    
    private String codigo_cliente;
    private String nombre_cliente;
    private String ruc_cliente;
    private ArrayList<Item> lista_items;

    public Orden() {
    }

    public Orden(String codigoDeCliente, String nombreDeCliente, String rucDeCliente, ArrayList<Item> items) {
        this.codigo_cliente = codigoDeCliente;
        this.nombre_cliente = nombreDeCliente;
        this.ruc_cliente = rucDeCliente;
        this.lista_items = items;
    }

    public String getCodigo_cliente() {
        return codigo_cliente;
    }

    public void setCodigo_cliente(String codigoDeCliente) {
        this.codigo_cliente = codigoDeCliente;
    }

    public String getNombre_cliente() {
        return nombre_cliente;
    }

    public void setNombre_cliente(String nombreDeCliente) {
        this.nombre_cliente = nombreDeCliente;
    }

    public String getRuc_cliente() {
        return ruc_cliente;
    }

    public void setRuc_cliente(String rucDeCliente) {
        this.ruc_cliente = rucDeCliente;
    }

    public ArrayList<Item> getLista_items() {
        return lista_items;
    }

    public void setLista_items(ArrayList<Item> items) {
        this.lista_items = items;
    }

    @Override
    public String toString() {
        return "Orden [codigoDeCliente=" + codigo_cliente + ", nombreDeCliente=" + nombre_cliente + ", rucDeCliente="
                + ruc_cliente + ", items=" + lista_items + "]";
    }
}
