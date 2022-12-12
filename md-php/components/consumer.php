<?php

  use Stomp\Transport\Message;

  include_once("connectDB.php");
  include_once("connect.php");

  
  function recibirMensaje($topic,$stomp){
    $stomp->subscribe($topic,null,'auto',array('client-id'=>'clientname','subscription-type'=>'MULTICAST','durable-subscription-name'=>'articulos'));
    $mensaje=$stomp->read();
    if($mensaje!=null){
      echo "Mensaje recibido: ".$mensaje->body;
      $stomp->ack($mensaje);  // mark the message as received in the queue
    }else{
      echo "Fallo al recibir un mensaje\n";
     
    }
    $stomp->unsubscribe();
  }
  
  recibirMensaje('/ordenes/lista_articulos',$stomp);

?>