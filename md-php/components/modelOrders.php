<?php 

include_once("connectDatabase.php");

class ModelOrders{
  
  public function getProductos($conn){
    // $query_lista = pg_query($conn, "SELECT codigo,nombre,precio,marca FROM carrito");
    // var_dump(pg_fetch_all($query_lista));
    // $lista_productos=pg_fetch_all($query_lista); 
    $query_lista = ['1000' => 5, '1003' => 1];
    $lista_productos=json_encode($query_lista,true);
    
    return $lista_productos;
  }

}

?>