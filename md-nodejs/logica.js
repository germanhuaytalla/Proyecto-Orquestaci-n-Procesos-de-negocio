
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
    fecha_factura: String,
    fecha_cobro: String,
    estado_registro: String
});

const CuentasPorCobrar = mongoose.model('CuentasPorCobrar', cuentasPorCobrarSchema, 'cuentas_x_cobrar');

module.exports.ejecutarAccion = (mensaje) => {
    // mensaje = mensaje.replace(/\\/g, '');

    // Lógica de la acción a realizar
    // console.log(`Se ha recibido el mensaje: ${mensaje} y se está realizando la acción`);
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
    console.log(mensaje)
    mensaje = mensaje['contenido']
    console.log(mensaje)

    // Hora actual UTC-5

    const fechaActual = new Date();
    const fecha_actual = fechaActual.toLocaleString('es-CO', {
        timeZone: 'Etc/GMT+5',
        day: '2-digit',
        month: '2-digit',
        year: 'numeric',
        hour: '2-digit',
        minute: '2-digit'
    });

    fechaActual.setDate(fechaActual.getDate() + 7);

    const fecha_cobro = fechaActual.toLocaleDateString('es-CO', {
        day: '2-digit',
        month: '2-digit',
        year: 'numeric'
      });

    // Campos para guardar en MONGODB
    contenido = {
        numero_factura: mensaje['numero_factura'],
        codigo_cliente: mensaje['codigo_cliente'],
        nombre_cliente: mensaje['nombre_cliente'],
        ruc_cliente: mensaje['ruc_cliente'],
        lista_items: mensaje['lista_items'],
        total_igv: mensaje['total_igv'],
        total_x_cobrar: mensaje['total_factura'],
        fecha_factura: fecha_actual,
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
        estado: 1,
        contenido: contenido
    }

    console.log("Enviando mensaje al modulo de procesamiento de ordenes.");

    productor.sendMessage(mensajeConfirmacion, "fisi_tiendautiles/mod_ordenes");
}