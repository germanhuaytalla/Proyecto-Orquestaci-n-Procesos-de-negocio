package fisiutiles.facturacion;

public class Facturacion {

    public static void main(String[] args) {
        var consumidor = new Consumidor();
        var hilo = new Thread(consumidor);
        hilo.start();
    }
}
