<?php 

use Stomp\Client;
use Stomp\Network\Connection;
use Stomp\StatefulStomp;
use Stomp\Transport\Message;

include_once("config.php");
class ConnectMiddleware
{
  
  public function connect()
  {
    $connection = new Connection(constant('URL'));
    $connection->setReadTimeout(30);
    $stomp = new StatefulStomp(new Client($connection));
    return $stomp;
  }
}
