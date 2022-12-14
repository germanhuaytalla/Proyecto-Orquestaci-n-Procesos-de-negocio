package fisiutiles.facturacion;

import java.util.ArrayList;

public class Factura {

    private int numeroDeFactura;
    private String codigoDeCliente;
    private String nombreDeCliente;
    private String rucDeCliente;
    private ArrayList<Item> items;
    private double totalIGV;
    private double totalFactura;
    
    public Factura() {
        
    }

    public Factura(int numeroDeFactura, String codigoDeCliente, String nombreDeCliente, String rucDeCliente, ArrayList<Item> items, double totalIGV, double totalFactura) {
        this.numeroDeFactura = numeroDeFactura;
        this.codigoDeCliente = codigoDeCliente;
        this.nombreDeCliente = nombreDeCliente;
        this.rucDeCliente = rucDeCliente;
        this.items = items;
        this.totalIGV = totalIGV;
        this.totalFactura = totalFactura;
    }

    public int getNumeroDeFactura() {
        return numeroDeFactura;
    }

    public void setNumeroDeFactura(int numeroDeFactura) {
        this.numeroDeFactura = numeroDeFactura;
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

    public double getTotalIGV() {
        return totalIGV;
    }

    public void setTotalIGV(double totalIGV) {
        this.totalIGV = totalIGV;
    }

    public double getTotalFactura() {
        return totalFactura;
    }

    public void setTotalFactura(double totalFactura) {
        this.totalFactura = totalFactura;
    }

    @Override
    public String toString() {
        return "Factura{" + "numeroDeFactura=" + numeroDeFactura + ", codigoDeCliente=" + codigoDeCliente + ", nombreDeCliente=" + nombreDeCliente + ", rucDeCliente=" + rucDeCliente + ", items=" + items + ", totalIGV=" + totalIGV + ", totalFactura=" + totalFactura + '}';
    }
}
