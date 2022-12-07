import stomp
import time
import json
from settings import settings

def listaProductos():
    hosts = [(settings.MYSQL_HOST, settings.ACTIVEMQ_PORT)]
    conn = stomp.Connection(host_and_ports=hosts)
    conn.connect(wait=True)
    message = json.dumps({'1000':25, '1002':1})
    conn.send(body=message, destination='ordenes/lista_articulos')
    print("Lista de artículos enviado al módulo de Administración de inventario y Reserva...")
    time.sleep(2)
    conn.disconnect()

if __name__ == '__main__':
    listaProductos()