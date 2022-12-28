<?php
include_once("config.php");
include_once("producer.php");
include_once("modelOrders.php");
include_once("connectMiddleware.php");


$mensaje = [
  'estado' => 1,
  "contenido" => [
    "num_factura" => "2020",
    "codigo_cliente" => "11111",
    "nombre_cliente" => "Pepito",
    "ruc_cliente" => "asdas2",
    "lista_items" =>
    [
      [
        "codigo" => "no hay informacion", "descripcion" => "1", "cantidad" => 5, "precioUnitario" => 12.3, "subTotal" => 61.5,

      ],
      [
        "codigo" => "no hay informacion", "descripcion" => "2", "cantidad" => 4, "precioUnitario" => 8.7, "subTotal" => 34.8,

      ],
      [
        "codigo" => "no hay informacion", "descripcion" => "3", "cantidad" => 2, "precioUnitario" => 11.6, "subTotal" => 23.2

      ]
    ],
    "total_igv" => "100",
    "total_x_cobrar" => "1000",
    "fecha_cobro" => "2022-12-27 T 19:58:00.797Z",
    "estado_registro" => "pendiente"
  ]
];

// $msj=json_encode($mensaje,true);

//Enviar mensaje al proceso de Inventario de productos
$conn_md = new ConnectMiddleware();
$stomp = $conn_md->connect();
$producer = new Producer();
$msj = json_encode($mensaje, true);

$producer->enviarMensaje($stomp, 'test', $msj);

if (!$producer) {
  sleep(3);
  echo "<script>
      alert('El mensaje no se envió correctamente');
      </script>";
} else {
  sleep(3);
  echo "<script>
  alert('El mensaje se envió correctamente');
  </script>";
}


?>
<!-- <?php echo gettype($msj); ?>
<?php echo $msj; ?>
<form method="POST" action="reporte.php">
  <input type="hidden" name="mensaje" value=<?= $msj ?>>
  <input type="submit" name="enviar" value="enviar mensaje">
</form>
 -->