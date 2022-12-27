<?php

include_once("modelOrders.php");
require __DIR__ . '/../vendor/autoload.php';
use Stomp\Transport\Message;

class Producer
{

  public function enviarMensaje($protocol, $topic, $mensaje)
  {
    // var_dump($mensaje);
    $protocol->send($topic, new Message($mensaje));

    return $protocol;
  }
}