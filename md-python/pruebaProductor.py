import stomp
import time
import json
from settings import settings

def listaProductos():
    hosts = [(settings.MYSQL_HOST, settings.ACTIVEMQ_PORT)]
    conn = stomp.Connection(host_and_ports=hosts)
    conn.connect(wait=True)
    message = json.dumps(
        {   
            "estado":1,
            "mensaje":{
                "codigoDeCliente":"14266484",
        	    "nombreDeCliente":"paco",
        	    "rucDeCliente":"ruc511",
                "items":[
                    {   
                        "codigo":"1000",
                        "descripcion":"desc 1",
                        "cantidad":5,
                        "precioUnitario":41.2,
                    },
                    {
                        "codigo":"1002",
                        "descripcion":"desc 2",
                        "cantidad":1000,
                        "precioUnitario":13.7,
                    },
                    {
                        "codigo":"1003",
                        "descripcion":"desc 3",
                        "cantidad":3,
                        "precioUnitario":8.5,
                    }
                ]
            }
        }
    )
    conn.send(destination = settings.TOPIC_FROM, body=message)
    print("Lista de artículos enviado al módulo de Administración de inventario y Reserva...")
    time.sleep(2)
    conn.disconnect()

if __name__ == '__main__':
    listaProductos()