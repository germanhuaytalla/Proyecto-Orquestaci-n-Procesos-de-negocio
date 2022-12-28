package fisiutiles.facturacion;

public class ItemCalculado {
    
    private String codigo;
    private String descripcion;
    private int cantidad;
    private double precio_unitario;
    private double sub_total;

    public ItemCalculado() {
        this.codigo = "no hay informacion";
        this.descripcion = "no hay informacion";
        this.cantidad = 0;
        this.precio_unitario = 0.0;
        this.sub_total = 0.0;
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
        this.sub_total = cantidad * this.precio_unitario;
    }

    public double getPrecio_unitario() {
        return precio_unitario;
    }

    public void setPrecio_unitario(double precioUnitario) {
        this.precio_unitario = precioUnitario;
        this.sub_total = precioUnitario * this.cantidad;
    }

    public double getSub_total() {
        return sub_total;
    }

    @Override
    public String toString() {
        return "ItemCalculado [codigo=" + codigo + ", descripcion=" + descripcion + ", cantidad=" + cantidad
                + ", precioUnitario=" + precio_unitario + ", subTotal=" + sub_total + "]";
    }
}
