package fisiutiles.facturacion;

public class ItemCalculado {
    
    private String codigo;
    private String descripcion;
    private int cantidad;
    private double precioUnitario;
    private double subTotal;

    public ItemCalculado() {
        this.codigo = "no hay informacion";
        this.descripcion = "no hay informacion";
        this.cantidad = 0;
        this.precioUnitario = 0.0;
        this.subTotal = 0.0;
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
        this.subTotal = cantidad * this.precioUnitario;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
        this.subTotal = precioUnitario * this.cantidad;
    }

    public double getSubTotal() {
        return subTotal;
    }
}
