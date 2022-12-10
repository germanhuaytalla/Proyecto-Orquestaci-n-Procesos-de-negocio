<?php 
  define("host","localhost");
  define("dbname","fisi_tiendasutiles");
  define("user","root");
  define("password","9R0PD4S9");
  
  $conn= pg_connect(" user=".constant("user")." password=".constant("password")."host=".constant("host")." dbname=".constant("dbname")."");
  echo $conn;
  
?>  