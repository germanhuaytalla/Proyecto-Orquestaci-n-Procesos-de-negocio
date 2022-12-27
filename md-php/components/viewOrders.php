<?php
include_once("connectDatabase.php");

if (isset($_POST['agregar_carrito'])) {
  $codigo = $_POST['p_codigo'];
  $nombre = $_POST['p_nombre'];
  $cantidad = $_POST['p_cantidad'];
  $precio = $_POST['p_precio'];

  $verificar_carrito = pg_query($conn, "SELECT * FROM orden WHERE codigo='$codigo'");

  if (pg_num_rows($verificar_carrito) > 0) {
    echo "<script>
    alert('El producto ya fue agregado al carrito anteriormente');
    window.location='viewOrders.php';
    </script>";
  } else {
    $insert__products = pg_query($conn, "INSERT INTO orden(codigo,descripcion,precioUnitario,cantidad) VALUES('$codigo','$nombre','$precio','$cantidad')");
    echo "<script>
    window.location='viewOrders.php';
    </script>";
  }
}

if (isset($_POST['consulta_carrito'])) {
  echo "<script>
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
    <h1 class="absolute top-64 left-[25%] right-[25%] text-center text-3xl lg:text-6xl font-semibold bg-white text-[#0123E7] border-2 border-[#0123E7] py-4 px-2">FISI | TIENDA DE ÚTILES</h1>
    <img class="absolute top-5 left-[20%] md:left-[40%] 2xl:left-[45%]" src="https://i.postimg.cc/4dxctWXr/cropped-logo-fisi-3.webp" alt="">
    <img class="w-full object-cover h-[400px]" src="https://i.postimg.cc/rm2gBTFk/utiles-escolares-1024x576.jpg" alt="">
  </section>
  <section class="relative container mx-auto mb-20">
    <h1 class="text-4xl font-bold text-center py-10">Lista de productos</h1>
    <div class="container mx-auto grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-8 lg:gap-12">
      <?php
      $select_products = pg_query($conn, "SELECT * FROM articulos");

      if (pg_num_rows($select_products)) {
        while ($row = pg_fetch_assoc($select_products)) {
      ?>
          <form class="mx-auto relative w-[90%] flex flex-col gap-4 border-2 border-[#0123E7] rounded-lg py-2 px-5" action="" method="POST">
            <img src="<?php echo $row['imagen'] ?>" alt="libro1">
            <div class="text-center"><?php echo $row['codigo'] ?></div>
            <div class="absolute left-0 top-0 flex items-center justify-center w-16 h-10 bg-[#0123E7] rounded-lg text-white">s/. <?php echo $row['precioUnitario'] ?></div>
            <div class=""><span class="font-semibold">Nombre:</span> <?php echo $row['descripcion'] ?></div>


            <input type="hidden" name="p_codigo" value="<?php echo $row['codigo'] ?>">
            <input class="border border-[#0123E7] px-2 rounded-lg" type="number" min="1" name="p_cantidad" value="1">
            <input type="hidden" name="p_nombre" value="<?php echo $row['descripcion'] ?>">
            <input type="hidden" name="p_precio" value="<?php echo $row['precioUnitario'] ?>">
            <input class="flex items-center justify-center mx-auto w-32 py-2 px-4 bg-[#847e7e] hover:bg-[#0123E7] transition duration-100 text-white rounded-lg" type="submit" name="agregar_carrito" value="Agregar a carrito">
          </form>
      <?php
        }
      } else {
        echo "<p class='empty'>No hay productos añadidos aqui</p>";
      }
      ?>
    </div>

    <form class="" action="viewCarrito.php" method="POST">
      <button name="consulta_carrito" class="fixed flex gap-2 right-10 bottom-10 bg-[#0123E7] text-white px-5 py-8 rounded-full  hover:scale-[0.9]">
        <?php
        $select_products = pg_query($conn, "SELECT * FROM orden");
        ?>
        <i class="fas fa-shopping-cart text-2xl"></i>
        <p class="text-2xl">(<?php echo pg_num_rows($select_products); ?>)</p>
      </button>
    </form>

  </section>
</body>

</html>