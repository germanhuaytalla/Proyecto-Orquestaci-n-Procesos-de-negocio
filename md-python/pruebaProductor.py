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
            "contenido":{
                "codigo_cliente":"14266484",
        	    "nombre_cliente":"paco",
        	    "ruc_cliente":"ruc511",
                "lista_items":[
                    {   
                        "codigo":"1000",
                        "descripcion":"desc 1",
                        "cantidad":5,
                        "precio_unitario":41.2,
                    },
                    {
                        "codigo":"1002",
                        "descripcion":"desc 2",
                        "cantidad":3,
                        "precio_unitario":13.7,
                    },
                    {
                        "codigo":"1003",
                        "descripcion":"desc 3",
                        "cantidad":3,
                        "precio_unitario":8.5,
                    },
                     {
                        "codigo":"1004",
                        "descripcion":"desc 4",
                        "cantidad":3,
                        "precio_unitario":20.1,
                    }
                ]
            }
        }
    , ensure_ascii=False)
    conn.send(destination = settings.TOPIC_TO_2, body=message, headers = {'persistent': 'true'})
    # print("Lista de artículos enviado al módulo de Administración de inventario y Reserva...")
    print("Lista de artículos enviado al módulo de Facturacion...")
    time.sleep(2)
    conn.disconnect()

if __name__ == '__main__':
    listaProductos()