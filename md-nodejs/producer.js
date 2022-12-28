// const Stomp = require("stomp-client");
// const stompClient = new Stomp("127.0.0.1", 61616);

// topicName = 'fisi_tiendautiles/mod_cuentas_x_cobrar'

// console.log("Enviando Mensaje ...")
// const message = {
//     estado: 1,
//     mensaje: `Su pedido se ha registrado con exito!.\n La fecha del pago esta programada para el dia `
// }
// const data = [
//     { topic: topicName, messages: JSON.stringify(message) }
// ];
// console.log(data);

// stompClient.send(data, function (err, resp) {
//     console.log(resp);
// });
// stompClient.on('error', function (err) {
//     console.log(err);
// });

const Stomp = require("stompjs");

const sendMessage = (message, destination) => {
    const client = Stomp.overTCP("localhost", 61616);

    client.connect("admin", "admin", function (error) {
        message = JSON.stringify(message)
        const messageBuffer = Buffer.from(message);
        const messageString = messageBuffer.toString('utf8');
        console.log(messageString)
        client.send(destination, {}, messageString);
        client.disconnect();
    });
}

module.exports = {
    sendMessage
}
