<?php 

class Producto{

    private String $cod_producto;
    private String $nombre;
    private float $precio;
    private int $cantidad;

    function __construct(String $cod_producto, String $nombre, float $precio, int $cantidad){
        $this->cod_producto = $cod_producto;
        $this->nombre = $nombre;
        $this->precio = $precio;
        $this->cantidad = $cantidad;
    }

    public function getCod_producto():String {
      return $this->cod_producto;
    }
    public function setCod_producto(String $codigo_producto) {
        $this->codigo_producto = $codigo_producto;
    }
 
    public function getNombre():String {
      return $this->nombre;
    }
    public function setNombre(String $nombre) {
        $this->nombre = $nombre;
    }

    public function getPrecio():float {
      return $this->precio;
    }
    public function setPrecio(float $precio) {
        $this->precio = $precio;
    }

    public function getCantidad():int {
      return $this->cantidad;
    }
    public function setCantidad(String $cantidad) {
        $this->cantidad = $cantidad;
    }



}

?>