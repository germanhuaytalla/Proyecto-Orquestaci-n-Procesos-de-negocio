
const mongoose = require('mongoose');
const dbConnection = require('./repository/connection');

const productor = require('./producer')

const host = '127.0.0.1';
const port = '27017';
const name = 'fisi_tiendautiles';

const cuentasPorCobrarSchema = new mongoose.Schema({
    // Define la estructura del documento en la colección "cuentas_x_cobrar"
    numero_factura: Number,
    codigo_cliente: String,
    nombre_cliente: String,
    ruc_cliente: String,
    lista_items: Array,
    total_igv: Number,
    total_x_cobrar: Number,
    fecha_cobro: Date,
    estado_registro: String
});

const CuentasPorCobrar = mongoose.model('CuentasPorCobrar', cuentasPorCobrarSchema, 'cuentas_x_cobrar');

module.exports.ejecutarAccion = (mensaje) => {
    // mensaje = mensaje.replace(/\\/g, '');

    // Lógica de la acción a realizar
    console.log(`Se ha recibido el mensaje: ${mensaje} y se está realizando la acción`);
    dbConnection.connect({ host, port, name })
        .then(() => {
            console.log('Conexión a la base de datos establecida con éxito.');
            cuentasPorCobrar(mensaje);
            setTimeout(function () {
                mongoose.connection.close();      
            }, 2000);

        })
        .catch(error => console.error('Error al conectarse a la base de datos:',
            error));
}

function cuentasPorCobrar(mensaje) {

    mensaje = JSON.parse(mensaje)
    
    mensaje = JSON.parse(mensaje['contenido'])
    console.log(mensaje)
 
    // Hora actual UTC-5
    const currentTime = Date.now();
    const timeInUtc5 = new Date(currentTime - 5*60*60*1000);
    const timeInIsoFormat = timeInUtc5.toISOString();

    // Sumar una semana a la fecha actual
    const fecha_cobro = new Date(timeInUtc5.setDate(timeInUtc5.getDate() + 7));

    // Campos para guardar en MONGODB
    contenido = {
        numero_factura: mensaje['numeroDeFactura'],
        codigo_cliente: mensaje['codigoDeCliente'],
        nombre_cliente: mensaje['nombreDeCliente'],
        ruc_cliente: mensaje['rucDeCliente'],
        lista_items: mensaje['items'],
        total_igv: mensaje['totalIGV'],
        total_x_cobrar: mensaje['totalFactura'],
        fecha_cobro: fecha_cobro,
        estado_registro: 'pendiente'
    }

    const cuentaPorCobrar = new CuentasPorCobrar(contenido);

    cuentaPorCobrar.save((error) => {
        if (error) {
            console.error(error);
        } else {
            console.log('Orden registrada con éxito.');
        }
    });

    // Mensaje de confirmacion para el modulo de procesamiento de ordenes
    const mensajeConfirmacion = {
        estado:1,
        contenido: contenido
    }

    console.log("Enviando mensaje al modulo de procesamiento de ordenes.");

    productor.sendMessage(mensajeConfirmacion, "fisi_tiendautiles/mod_ordenes");
}