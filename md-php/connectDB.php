<?php
  class ConnectDB{

    public function conectar_PostgreSQL( $usuario, $pass, $host, $bd){
         $conn= pg_connect( "host=".$host." ".
                            "password=".$pass." ".
                            "user=".$usuario." ".
                            "dbname=".$bd
                          ) or die( "Error al conectar: ".pg_last_error() );
    
        return $conn;
    }
    
  }

  $obj=new ConnectDB();
  $conn=$obj->conectar_PostgreSQL("postgres","9R0PD4S9","localhost","fisi_tiendasutiles");

?>
