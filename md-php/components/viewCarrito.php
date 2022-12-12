<?php
include_once("connectDB.php");


if (isset($_POST['actualizar_carrito'])) {
  $actualizar_id = $_POST['p_codigo'];
  $cantidad = $_POST['p_cantidad'];

  pg_query($conn, "UPDATE carrito SET cantidad='$cantidad' WHERE codigo='$actualizar_id'");
  echo "<script>
  alert('Cantidad actualizada');
  window.location='viewCarrito.php';
  </script>";
}

if (isset($_GET['delete'])) {
  $delete_id = $_GET['delete'];

  pg_query($conn, "DELETE FROM carrito WHERE codigo='$delete_id'");
}

if (isset($_POST['enviar'])) {

  $select_productos = pg_query($conn, "SELECT * FROM carrito");
  if (pg_fetch_assoc($select_productos) > 0) {
    pg_query($conn, "DELETE FROM carrito");
    echo "<script>
    alert('Lista enviado');
    window.location='viewOrders.php';
    </script>";
  }else{
    echo "<script>
    alert('No hay elementos en el carrito');
    window.location='viewCarrito.php';
    </script>";
  }
}


?>


<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Carrito</title>

  <script src="https://cdn.tailwindcss.com"></script>
  <script src="https://kit.fontawesome.com/42778bfa5e.js" crossorigin="anonymous"></script>
</head>

<body>
  <section class="relative w-full">
    <h1 class="absolute top-64 left-[25%] right-[25%] text-center text-6xl font-semibold bg-white text-[#0123E7] border-2 border-[#0123E7] py-4 px-2">FISI | Carrito compras</h1>
    <img class="absolute top-5 left-[40%] 2xl:left-[45%]" src="https://i.postimg.cc/4dxctWXr/cropped-logo-fisi-3.webp" alt="">
    <img class="w-full relative object-cover h-[400px] opacity-80 -z-10" src="https://i.postimg.cc/rm2gBTFk/utiles-escolares-1024x576.jpg" alt="">
  </section>
  <section class="max-w-[1000px] mx-auto mb-20 ">
    <h1 class="text-4xl font-bold text-center py-10">Lista de productos</h1>
    <div class="container mx-auto flex gap-4">
      <div class="max-w-[700px] mx-auto grid grid-cols-1 md:grid-cols-1 lg:grid-cols-2 gap-2 lg:gap-4 place-content-center">
        <?php
        $total = 0;

        $select_products = pg_query($conn, "SELECT * FROM carrito");
        if (pg_num_rows($select_products)) {
          while ($row = pg_fetch_assoc($select_products)) {
        ?>
            <form class="relative w-fit flex flex-col gap-4 border-2 border-[#0123E7] rounded-lg pt-6 pb-2 px-4" action="viewCarrito.php" method="POST">
              <div class="absolute left-1 top-1 p-2 bg-red-500 rounded-lg">
                <a class="fas fa-times p-2" href="<?php echo "viewCarrito.php"; ?>?delete=<?php echo $row['codigo']; ?>" onclick="return confirm('¿Borrar producto?')">
                </a>
              </div>
              <div class="text-center"><?php echo $row['codigo'] ?></div>
              <div class=""><span class="font-semibold">Nombre:</span> <?php echo $row['nombre'] ?></div>
              <div class=""><span class="font-semibold">Marca: </span><?php echo $row['marca'] ?></div>
              <div class="text-center font-semibold ">s/. <?php echo $row['precio'] ?></div>

              <input type="hidden" name="p_codigo" value="<?php echo $row['codigo'] ?>">
              <input type="hidden" name="p_nombre" value="<?php echo $row['nombre'] ?>">
              <input type="hidden" name="p_precio" value="<?php echo $row['precio'] ?>">
              <input type="hidden" name="p_marca" value="<?php echo $row['marca'] ?>">

              <div class="flex flex-row  gap-4">
                <input class="w-full border border-[#0123E7] px-2 rounded-lg" type="number" min="1" name="p_cantidad" value="<?php echo $row['cantidad'];  ?>">
                <input class="w-full py-2 px-2 bg-[#847e7e] hover:bg-[#0123E7] transition duration-100 text-white rounded-lg" type="submit" name="actualizar_carrito" value="Actualizar carrito">
              </div>
              <div class="text-center py-2 font-semibold">Sub total: s/.<span class="font-normal"><?php echo $sub_total = ($row['cantidad'] * $row["precio"]) ?></span></div>
            </form>
        <?php
            $total += $sub_total;
          }
        } else {
          echo "<p class='text-center col-span-2'>No hay productos añadidos aqui</p>";
        }
        ?>
      </div>
      <div class="max-w-[300px] h-full flex flex-col gap-4 bg-[#0123E7] text-center rounded-lg py-4 px-4">
        <p class="text-white font-bold text-3xl">Total generado: <span cla>s/.<?php echo $total; ?></span></p>
        <form action="viewCarrito.php" method="POST">
          <input type="submit" class="w-20 h-10 flex items-center justify-center text-center mx-auto border hover:border-white text-[#0123E7] hover:text-white bg-white hover:bg-[#0123E7] hover:scale-[0.9] transition duration-100 rounded-lg" value="Enviar" name="enviar">
        </form>
      </div>
    </div>
    <div class="w-full flex items-center justify-center border border-[#0123E7] py-10 my-10">
      <a class="bg-[#0123E7] rounded-lg text-white px-4 py-2" href="./viewOrders.php">
        Regresar
      </a>
    </div>
  </section>
</body>

</html>