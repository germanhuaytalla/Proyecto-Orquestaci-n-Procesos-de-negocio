<?php

  include_once("config.php");
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
  $conn=$obj->conectar_PostgreSQL(constant("POSTGRESQL_USER"),constant("POSTGRESQL_PASSWORD"),constant("POSTGRESQL_HOST"),constant("POSTGRESQL_DB"));

?>
