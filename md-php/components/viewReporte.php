<?php
include_once("config.php");
include_once("consumer.php");
include_once("modelOrders.php");
include_once("connectMiddleware.php");


if (isset($_POST['aceptar'])) {
  $aceptar = $_POST['aceptar'];

  //Recibir mensaje de cuentas por cobrar
  $conn_md = new ConnectMiddleware();
  $stomp = $conn_md->connect();
  $consumer = new Consumer();
  $consumer->recibirMensajeCuentasPorCobrar(constant('TOPIC_FROM'), $stomp);

}

if (isset($_POST['cancelar'])) {

  echo "<script>
  alert('El mensaje se ha cancelado');
  window.location='viewCarrito.php';
  </script>";
}


?>

<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Document</title>

  <script src="https://cdn.tailwindcss.com"></script>
  <script src="https://kit.fontawesome.com/42778bfa5e.js" crossorigin="anonymous"></script>
</head>

<body>
  <section class="relative w-full">
    <form action="" method="POST" class="absolute top-64 left-[25%] right-[25%] text-center text-3xl font-semibold  bg-white text-[#0123E7] border-2 border-[#0123E7] py-8 px-2">
      <h2>¿Generar Factura de Venta?</h2>
      <div class="flex justify-center items-center gap-4 my-6">
        <input class="bg-[#0123E7] px-4 py-2 text-base text-white rounded-lg hover:scale-[0.9]" type="submit" name="aceptar" value="Aceptar">
        <input class="bg-[#0123E7] px-4 py-2 text-base text-white rounded-lg hover:scale-[0.9]" type="submit" name="cancelar" value="Cancelar">
      </div>
    </form>
    <img class="w-full h-[100vh] relative object-cover opacity-80 -z-10" src="https://i.postimg.cc/rm2gBTFk/utiles-escolares-1024x576.jpg" alt="">
  </section>


</body>

</html>