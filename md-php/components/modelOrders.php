<?php

include_once("connectDatabase.php");

class ModelOrders
{

  public function getProductos($conn)
  {
    $query_lista = pg_query($conn, "SELECT * FROM orden");
    $lista_productos = pg_fetch_all($query_lista);
    // var_dump(pg_fetch_all($query_lista))
    return $lista_productos;
  }
}
