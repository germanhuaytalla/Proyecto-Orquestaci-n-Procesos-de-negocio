package fisiutiles.facturacion;

public class Item {

    private String descripcion;
    private int cantidad;
    private double precioUnitario;
    private double subTotal;

    public Item() {
        this.descripcion = "no hay informacion";
        this.cantidad = 0;
        this.precioUnitario = 0.0;
        this.subTotal = 0.0;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
        this.subTotal = this.cantidad * this.precioUnitario;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
        this.subTotal = this.precioUnitario * this.cantidad;
    }

    public double getSubTotal() {
        return subTotal;
    }

    @Override
    public String toString() {
        return "Item{" + "descripcion=" + descripcion + ", cantidad=" + cantidad + ", precioUnitario=" + precioUnitario + ", subTotal=" + subTotal + '}';
    }
}
