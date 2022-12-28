const Stomp = require("stompjs");

const client = Stomp.overTCP("localhost", 61616);

client.connect("admin", "admin", function (error) {
  const topicName = "fisi_tiendautiles/mod_cuentas_x_cobrar";
  const message = {
    estado: 1,
    contenido: {
      numero_factura: '999999',
      codigo_cliente: '11111',
      nombre_cliente: 'Pepito',
      ruc_cliente: 'asdas2',
      lista_items: 'lista de items',
      total_igv: '100',
      total_factura: '1000',
    }
  };

  client.send(topicName, {}, JSON.stringify(message));
  client.disconnect();
});