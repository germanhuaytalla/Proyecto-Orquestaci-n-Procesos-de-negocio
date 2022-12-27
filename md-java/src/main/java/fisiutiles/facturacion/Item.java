package fisiutiles.facturacion;

public class Item {
    
    private String codigo;
    private String descripcion;
    private int cantidad;
    private double precioUnitario;

    public Item() {
        this.codigo = "no hay informacion";
        this.descripcion = "no hay informacion";
        this.cantidad = 0;
        this.precioUnitario = 0.0;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
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
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    @Override
    public String toString() {
        return "Item{" + "codigo=" + codigo + ", descripcion=" + descripcion + ", cantidad=" + cantidad + ", precioUnitario=" + precioUnitario + '}';
    }
}
