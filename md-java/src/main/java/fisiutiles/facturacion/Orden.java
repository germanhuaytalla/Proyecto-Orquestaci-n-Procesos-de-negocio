package fisiutiles.facturacion;

import java.util.ArrayList;

public class Orden {
    
    private String codigoDeCliente;
    private String nombreDeCliente;
    private String rucDeCliente;
    private ArrayList<Item> items;

    public Orden() {
    }

    public Orden(String codigoDeCliente, String nombreDeCliente, String rucDeCliente, ArrayList<Item> items) {
        this.codigoDeCliente = codigoDeCliente;
        this.nombreDeCliente = nombreDeCliente;
        this.rucDeCliente = rucDeCliente;
        this.items = items;
    }

    public String getCodigoDeCliente() {
        return codigoDeCliente;
    }

    public void setCodigoDeCliente(String codigoDeCliente) {
        this.codigoDeCliente = codigoDeCliente;
    }

    public String getNombreDeCliente() {
        return nombreDeCliente;
    }

    public void setNombreDeCliente(String nombreDeCliente) {
        this.nombreDeCliente = nombreDeCliente;
    }

    public String getRucDeCliente() {
        return rucDeCliente;
    }

    public void setRucDeCliente(String rucDeCliente) {
        this.rucDeCliente = rucDeCliente;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }
}
