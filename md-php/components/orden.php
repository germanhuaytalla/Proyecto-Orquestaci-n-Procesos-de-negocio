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
          'codigo' => '1000',
          'nombre' => 'TÉMPERA X 7 FABER CASTELL',
          'precio' => 8.4,
          'cantidad'=> 800,
          'marca'=>'FABER CASTELL'
      ),
      array(
          'codigo' => '1001',
          'nombre' => 'PLUMÓN N° 45 X 20  FABER-CASTEL',
          'precio' => 18.4,
          'cantidad'=> 100,
          'marca'=>'FABER CASTELL'
      ),
      array(
          'codigo' => '1002',
          'nombre' => 'BOLÍGRAFO PILOT BL-G1-7 PLATA',
          'precio' => 4.7,
          'cantidad'=> 88,
          'marca'=>'PILOT'
      ),
      array(
          'codigo' => '1003',
          'nombre' => 'TIJERA 7 1/2 PULGADAS',
          'precio' => 11.6,
          'cantidad'=> 100,
          'marca'=>'WEX',
      ),
    );

      return $this->lista_productos;
  }
}


?>