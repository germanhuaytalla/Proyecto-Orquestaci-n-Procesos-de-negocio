const Stomp = require("stompjs");
const logica = require("./logica"); // Importa el modulo donde esta la logica

const client = Stomp.overTCP("localhost", 61616);

client.connect("admin", "admin", function(error) {
  const topicName = "fisi_tiendautiles/mod_ordenes";
  client.subscribe(topicName, function(message) {
    console.log("Mensaje recibido:", message.body);
  });
});