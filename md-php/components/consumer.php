<?php

require __DIR__ . '/../vendor/autoload.php';
include_once("connectDatabase.php");
include_once("connectMiddleware.php");


class Consumer{
  

  function recibirMensaje($topic, $stomp,$view)
  {
    $stomp->subscribe(
      $topic,
      null,
      'auto',
      array(
        "message-id"=>"id","priority"=>9
      )
    );
  
    //Listener
    while(true){
      $mensaje = $stomp->read();
      if ($mensaje !=null) {
        echo "<script>
        window.location='".$view.".php';
        </script>";
        break;
      }else{
        echo "No reception";
        break;
      }
    }
    $stomp->unsubscribe();
  }
}


