<?php

require __DIR__ . '/../vendor/autoload.php';
include_once("connectDatabase.php");
include_once("connectMiddleware.php");

class Consumer
{

  function recibirMensajeConfirmacion($topic, $stomp, $view)
  {
    $stomp->subscribe(
      $topic,
      null,
      'auto'
    );

    //Listener
    while (true) {
      $mensaje = $stomp->read();
      
      if ($mensaje != null) {
        $mensaje2 = json_decode($mensaje->body, true);
        // print_r($mensaje2);
        if($mensaje2['estado'] == 1){
          echo "<script>
          window.location='" . $view . ".php';
          </script>";
        } else if($mensaje2['estado'] == 0) {
          echo "<script>
          window.location='viewError.php';
          </script>";
        }
        break;
      } else {
        echo "No reception";
        break;
      }
    }
    $stomp->unsubscribe();
  }

  function recibirMensajeCuentasPorCobrar($topic, $stomp)
  {
    $stomp->subscribe(
      $topic,
      null,
      'auto'
    );

    // Listener
    while (true) {
      $mensaje = $stomp->read();
      if ($mensaje != null) {
        $array = json_decode($mensaje->body,true);
        return $array;
      } else {
        echo "No reception";
        break;
      }
    }
    $stomp->unsubscribe();
  }
}
