// const Stomp = require("stomp-client");
// const stompClient = new Stomp("127.0.0.1", 61613);

// stompClient.connect(function (sessionId) {
//   console.log("consumer connected");

//   stompClient.subscribe("/queue/notifications", function (body, headers) {
//     console.log(body);
//     console.log(headers);
//     //stompClient.disconnect();
//   });
//   setTimeout(function(){
//     //stompClient.publish("/queue/notifications", 'oh herrow!');
//      stompClient.disconnect();
//   }, 90000000);

// });

const Stomp = require("stompjs");
const logica = require("./logica"); // Importa el modulo donde esta la logica

const client = Stomp.overTCP("localhost", 61616);

client.connect("admin", "admin", function(error) {
  const topicName = "fisi_tiendautiles/mod_cuentas_x_cobrar";
  client.subscribe(topicName, function(message) {
    console.log("Mensaje recibido:", message.body);
    logica.ejecutarAccion(message.body)
  });
});