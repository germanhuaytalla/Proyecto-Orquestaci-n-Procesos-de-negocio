<?php

use Stomp\Transport\Message;

include_once("connectMD.php");
include_once("modelOrders.php");

class Producer
{

  public function enviarMensaje($protocol, $topic, $mensaje)
  {
    $mensaje = json_encode($mensaje, true);
    var_dump($mensaje);
    $protocol->send($topic, new Message($mensaje));
    if (!$protocol) {
      echo "Mensaje no evniado\n";
      sleep(2);
    } else {
      echo "Mensaje enviado con éxito";
    }
  }
}

$obj=new Producer();
$obj->enviarMensaje($stomp, 'ordenes/lista_articulos', $lista_productos);
