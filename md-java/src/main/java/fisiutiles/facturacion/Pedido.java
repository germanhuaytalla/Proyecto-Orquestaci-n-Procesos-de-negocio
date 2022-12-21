package fisiutiles.facturacion;

import java.util.ArrayList;

public class Pedido {
    
    private String codigoDeCliente;
    private String nombreDeCliente;
    private String rucDeCliente;
    private ArrayList<Item> items;

    public Pedido() {
    }

    public Pedido(String codigoDeCliente, String nombreDeCliente, String rucDeCliente, ArrayList<Item> items) {
        this.codigoDeCliente = codigoDeCliente;
        this.nombreDeCliente = nombreDeCliente;
        this.rucDeCliente = rucDeCliente;
        this.items = items;
    }
}
