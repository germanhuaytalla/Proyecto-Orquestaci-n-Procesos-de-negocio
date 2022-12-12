<?php
require __DIR__ . '/../vendor/autoload.php';

use Stomp\Client;
use Stomp\Network\Connection;
use Stomp\StatefulStomp;

class ConnectMD
{
  
  public function connect()
  {
    $connection = new Connection('tcp://localhost:61613');
    $connection->setReadTimeout(5);
    $stomp = new StatefulStomp(new Client($connection));
    return $stomp;
  }
}

$obj=new ConnectMD();
$stomp=$obj->connect();

?>