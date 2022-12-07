<?php 

class Orden{

    private String $id_cliente;
    private $lista_productos;
    private float $monto_total;

    function __construct(){
      $this->lista_productos=null;
    }

    
    public function getId_cliente():String {
      return $this->id_cliente;
    }

    public function setId_cliente(String $id_cliente) {
        $this->id_cliente = $id_cliente;
    }
        
    public function getMonto_total():float {
      return $this->monto_total;
    }

    public function setMonto_total(float $monto_total) {
        $this->monto_total = $monto_total;
    }


    public function ListaProductos():array{
      $this->lista_productos=array(array(
          'codigo' => '7613035724518',
          'nombre' => 'Paneton Donofrio bolsa',
          'imagen' => '',
          'precio' => 25.50,
          'cantidad'=> 5
      ),
      array(
          'codigo' => '7751271021579',
          'nombre' => 'Leche Gloria 400 gr',
          'imagen' => '',
          'precio' => 4.20,
          'cantidad'=> 5
      ),
      array(
          'codigo' => '7750151012447',
          'nombre' => 'Queso Cheddar Laive 170 gr',
          'imagen' => '',
          'precio' => 5.30,
          'cantidad'=> 5
      ),
      array(
          'codigo' => '7750151005548',
          'nombre' => 'Yougurt Gloria Fresa 340 ml',
          'imagen' => '',
          'precio' => 12.50,
          'cantidad'=> 5
      ),
      array(
          'codigo' => '7750151003902',
          'nombre' => 'Mermelada Fanny 1k',
          'imagen' => '',
          'precio' => 61.50,
          'cantidad'=> 5
      ),
      array(
          'codigo' => '7750243069946',
          'nombre' => 'Detergente Bolivar 3.8 kg',
          'imagen' => '',
          'precio' => 35.80,
          'cantidad'=> 5
      ));

      return $this->lista_productos;
  }
}


?>