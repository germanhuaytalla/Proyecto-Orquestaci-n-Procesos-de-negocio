<?php
include_once("connectDatabase.php");
include_once("connectMiddleware.php");
include_once("consumer.php");
include_once("config.php");

ob_start();


// Esperar la el mensaje de "cuentas por cobrar" 
$conn_md = new ConnectMiddleware();
$stomp = $conn_md->connect();
$consumer = new Consumer();
$mensaje=$consumer->recibirMensajeCuentasPorCobrar('fisi_tiendautiles/mod_cuentas_x_cobrar', $stomp);

if ($mensaje != null) {
    print_r($mensaje);

    echo "<script>
    alert('Mensaje recibido');
    </script>";
}


?>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Reporte</title>
    <link rel="shortcut icon" href="">

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">

    <style>
        @import url('fonts/BrixSansRegular.css');
        @import url('fonts/BrixSansBlack.css');

        * {
            box-sizing: border-box;
        }

        body {
            font-size: 12px;
            font-family: sans-serif;
        }

        .container-pdf {
            margin-top: 0.1rem;
            margin-bottom: 2rem;
            margin-left: auto;
            margin-right: auto;
            width: 100%;
        }

        /**Apartado Factura-head y factura-cliente**/
        p,
        label,
        span,
        table {
            font-family: 'BrixSansRegular';
            font-size: 12pt;
        }

        .h2 {
            font-family: 'BrixSansBlack';
            font-size: 16pt;
        }

        .h3 {
            font-family: 'BrixSansBlack';
            font-size: 12pt;
            display: block;
            background: #0a4661;
            color: #FFF;
            text-align: center;
            padding: 3px;
            margin-bottom: 5px;
        }


        #factura_head {
            width: 100%;
            margin-bottom: 10px;
        }

        #factura_head tr .logo_factura {
            width: 25%;
            margin-left: auto;
            margin-right: auto;
        }

        .info_empresa {
            width: 50%;
            text-align: center;
        }

        .info_factura {
            width: 25%;
            border: 1px solid black;
            border-radius: 5px;
        }

        /**Detalles de factura*/
        #factura_cliente {
            border: 1px solid black;
            width: 100%;
        }

        #factura_cliente thead {
            margin: auto;
            text-align: center;
            color: white;
            background-color: #0a4661;
            border: .1rem solid black;
        }

        #factura_cliente tbody tr td {
            padding: 0.5rem 3rem;
        }

        #factura_cliente tbody tr td label,
        span {
            display: inline-block;
        }

        #factura_cliente tbody tr td label {
            font-weight: bold;
        }

        /**Apartado de productos*/
        #factura_productos {
            margin-top: 2rem;
            margin-left: auto;
            margin-right: auto;
            width: 90%;
            border: .1rem solid black;
        }

        #factura_productos .theader tr {
            color: white;
            background-color: #0a4661;
            padding: 1rem;
        }

        #factura_productos .tbody tr td {
            text-align: center;
            border: .1rem solid black;
            padding: 0.1rem 0;
        }
    </style>
</head>

<body>

    <div id="preloader"></div>
    <div class="container-pdf">
        <table id="factura_head">
            <tr>
                <td class="logo_factura">
                    <div>
                        <img src="https://i.postimg.cc/4dxctWXr/cropped-logo-fisi-3.webp" width="160" height="160">
                    </div>
                </td>
                <td class="info_empresa">
                    <div>
                        <span class="h2">FACTURA</span>
                        <p>Fisi Tiendas Ùtiles</p>
                        <p>Email: fisitiendasutiles@gmail.com</p>
                        <p>Estado: <?php echo $mensaje['contenido']['estado_registro']; ?></p>
                    </div>
                </td>
                <td class="info_factura">
                    <div class="round">
                        <span class="h3">Nro.Factura</span>
                        <p><strong>Factura:</strong><?php echo $mensaje['contenido']['num_factura']; ?></p>
                        <p><strong>Fecha y Hora:</strong>
                            <?php echo $mensaje['contenido']['fecha_cobro']; ?>
                        </p>
                        <p><strong>Vendedor:</strong> Sr. Fracisco Perez</p>
                    </div>
                </td>
            </tr>
        </table>
        <table id="factura_cliente">
            <thead>
                <tr>
                    <th colspan="2">Cliente</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td><label>Id: </label><span><?php echo $mensaje['contenido']['codigo_cliente']; ?></span></td>
                    <td><label>Ruc: </label><span><?php echo $mensaje['contenido']['ruc_cliente'] ?></span></td>
                </tr>
                <tr>
                    <td><label>Nombre: </label> <span><?php echo $mensaje['contenido']['nombre_cliente']; ?></span></td>
                    <td></td>
                </tr>
            </tbody>
        </table>


        <table id="factura_productos">
            <thead class="theader">
                <tr>
                    <th>ID</th>
                    <th>Nombre</th>
                    <th>Precio</th>
                    <th>Cantidad</th>
                    <th>Subtotal</th>
                </tr>
            </thead>
            <tbody class="tbody">
                <?php

                foreach ($mensaje['contenido']['lista_items'] as $prod) {
                ?>
                    <tr>
                        <td><?php echo $prod['codigo']; ?></td>
                        <td><?php echo $prod['descripcion']; ?></td>
                        <td>s/.<?php echo $prod['precioUnitario']; ?></td>
                        <td><?php echo $prod['cantidad']; ?></td>
                        <td>s/.<?php echo $prod['subTotal']; ?></td>
                    </tr>
                <?php

                }
                ?>
                <tr>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td>Total</td>
                    <td style="padding: 0.2rem 0; background-color: yellow;">s/.
                        <?php
                        echo $mensaje['contenido']['total_x_cobrar'];
                        ?>
                    </td>
                </tr>
            </tbody>
        </table>
    </div>

</body>

</html>


<?php

$html = ob_get_clean();
// echo $html;

require_once __DIR__ . '/../pdf/dompdf/autoload.inc.php';

use Dompdf\Dompdf;
use Dompdf\Options;

$dompdf = new Dompdf();

$options = new Options();
$options->set(array('isRemoteEnabled' => true));
$dompdf->setOptions($options);

$dompdf->loadHtml(utf8_decode($html), 'utf-8');

$dompdf->setPaper('A4', 'portrait');      //Vertical
// $dompdf->setPaper('A4','landscape');  //Horizontal
$dompdf->render();

$dompdf->stream('productosFisiTiendasutiles.pdf', array("Attachment" => false));


?>