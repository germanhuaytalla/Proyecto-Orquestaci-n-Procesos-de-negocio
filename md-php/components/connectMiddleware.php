<?php 

use Stomp\Client;
use Stomp\Network\Connection;
use Stomp\StatefulStomp;
use Stomp\Transport\Message;

class ConnectMiddleware
{
  
  public function connect()
  {
    $connection = new Connection('tcp://localhost:61613');
    $connection->setReadTimeout(30);
    $stomp = new StatefulStomp(new Client($connection));
    return $stomp;
  }
}
