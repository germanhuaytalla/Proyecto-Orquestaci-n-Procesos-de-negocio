<?php 
include_once("connectMiddleware.php");
include_once("producer.php");

$conn_md = new ConnectMiddleware();
$stomp = $conn_md->connect();
$producer = new Producer();
$producer->enviarMensaje($stomp, 'ordenes/consulta','consultar reserva');


?>