<?php 

include_once("connectDB.php");

class ModelOrders{
  
  public function getProductos($conn){
    $query = pg_query($conn, "SELECT * FROM articulos ");
    // $lista_productos=pg_fetch_all($query); 
    $lista_productos = ['1000' => 5, '1003' => 1];
    // var_dump($lista_productos);

    return $lista_productos;
  }

}

$model=new ModelOrders();
$lista_productos=$model->getProductos($conn);

?>