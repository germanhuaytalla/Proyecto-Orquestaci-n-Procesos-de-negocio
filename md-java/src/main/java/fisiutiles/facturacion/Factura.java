package fisiutiles.facturacion;

import java.util.ArrayList;

public class Factura {

    private int numero_factura;
    private String codigo_cliente;
    private String nombre_cliente;
    private String ruc_cliente;
    private ArrayList<ItemCalculado> lista_items;
    private double total_igv;
    private double total_factura;

    public Factura() {
        this.numero_factura = 0;
        this.codigo_cliente = "no hay informacion";
        this.nombre_cliente = "no hay informacion";
        this.ruc_cliente = "no hay informacion";
        this.lista_items = new ArrayList<>();
        this.total_igv = 0.0;
        this.total_factura = 0.0;
    }

    public int getNumero_factura() {
        return numero_factura;
    }

    public void setNumero_factura(int numeroDeFactura) {
        this.numero_factura = numeroDeFactura;
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

    public String getRuc_de_cliente() {
        return ruc_cliente;
    }

    public void setRuc_de_cliente(String rucDeCliente) {
        this.ruc_cliente = rucDeCliente;
    }

    public ArrayList<ItemCalculado> getLista_items() {
        return lista_items;
    }

    public void setLista_items(ArrayList<ItemCalculado> items) {
        this.lista_items = items;
        double totalItems = calcularTotalItems();
        this.total_igv = totalItems * 0.18;
        this.total_factura = totalItems + this.total_igv;
    }

    public double getTotal_igv() {
        return total_igv;
    }

    public double getTotal_factura() {
        return total_factura;
    }

    @Override
    public String toString() {
        return "Factura{" + "numeroDeFactura=" + numero_factura + ", codigoDeCliente=" + codigo_cliente + ", nombreDeCliente=" + nombre_cliente + ", rucDeCliente=" + ruc_cliente + ", items=" + lista_items + ", totalIGV=" + total_igv + ", totalFactura=" + total_factura + '}';
    }

    private double calcularTotalItems() {
        double totalItems = 0.0;

        for (ItemCalculado item : this.lista_items) {
            totalItems += item.getSub_total();
        }

        return totalItems;
    }
}
