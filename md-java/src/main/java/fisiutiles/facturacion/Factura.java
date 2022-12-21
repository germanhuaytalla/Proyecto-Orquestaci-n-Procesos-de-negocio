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
        this.numeroDeFactura = 0;
        this.codigoDeCliente = "no hay informacion";
        this.nombreDeCliente = "no hay informacion";
        this.rucDeCliente = "no hay informacion";
        this.items = new ArrayList<>();
        this.totalIGV = 0.0;
        this.totalFactura = 0.0;
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
        double totalItems = calcularTotalItems();
        this.totalIGV = totalItems * 0.18;
        this.totalFactura = totalItems + this.totalIGV;
    }

    public double getTotalIGV() {
        return totalIGV;
    }

    public double getTotalFactura() {
        return totalFactura;
    }

    @Override
    public String toString() {
        return "Factura{" + "numeroDeFactura=" + numeroDeFactura + ", codigoDeCliente=" + codigoDeCliente + ", nombreDeCliente=" + nombreDeCliente + ", rucDeCliente=" + rucDeCliente + ", items=" + items + ", totalIGV=" + totalIGV + ", totalFactura=" + totalFactura + '}';
    }

    private double calcularTotalItems() {
        double totalItems = 0.0;

        for (Item item : this.items) {
            totalItems += item.getSubTotal();
        }

        return totalItems;
    }
}
