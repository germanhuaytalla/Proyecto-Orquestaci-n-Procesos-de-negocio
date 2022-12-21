import stomp
import time
import json
import mysql.connector
from productor import enviarMensaje
from settings import settings

class ListenerArticulos(stomp.ConnectionListener):
    def __init__(self, conn):
        self.conn = conn

    def on_error(self, message):
        print('error 1 "%s"' % message.body)

    def on_message(self, message):
        print('lista de articulos recibida con exito "%s' % message.body)
        print(message.body)
        conn.disconnect()
        validarArticulos(message.body)
        
    
class ListenerConfirmacion(stomp.ConnectionListener): 
    def __init__(self, conn):
        print("listener confirmacion")
        self.conn = conn
        
    def on_error(self, message):
        print('error 2"%s"' % message.body)

    def on_message(self, message):
        global confirmacion
        datos = json.loads(message.body)
        confirmacion = int(datos['estado'])
        print('confirmacion recibida con exito "%s"' % message.body)
        conn.disconnect()

def validarArticulos(mensaje):
    datos = json.loads(mensaje)
    items = datos['mensaje']['items']
    print("Validando lista de artículos")
    cnn = mysql.connector.connect(host=settings.MYSQL_HOST, user=settings.MYSQL_USER, passwd=settings.MYSQL_PASSWORD, database=settings.MYSQL_DB)
    cur = cnn.cursor()
    i = 0
    for item in items:
        # print(item['codigo'])
        cur.execute("SELECT cantidad FROM articulos where codigo = {}".format(item['codigo']))
        stock = cur.fetchall()
        if cur.rowcount == 0:
            mensaje = '{"estado": 0, "mensaje": "El articulo ' + item['codigo'] + ' no existe"}'
            print(mensaje)
            # Se envía mensaje de error al módulo de órdenes
            enviarMensaje(settings.TOPIC_TO_1, mensaje)
            break
        else:
            stock = int(stock[0][0])
            if stock >= int(item['cantidad']):
                i = i + 1
                if i == len(items):
                    print("RESERVANDO ARTICULOS...")
                    reservar(items, mensaje)  
            else:
                mensaje = '{"estado": 0, "mensaje": "No hay stock suficiente del articulo ' + item['codigo'] + '. Solo hay ' + str(stock) + ' unidades"}'
                print(mensaje)
                enviarMensaje(settings.TOPIC_TO_1, mensaje)
                break

def reservar(items, message):
    cnn = mysql.connector.connect(host=settings.MYSQL_HOST, user=settings.MYSQL_USER, passwd=settings.MYSQL_PASSWORD, database=settings.MYSQL_DB)
    cur = cnn.cursor()
    for item in items:
        cur.execute("UPDATE articulos SET cantidad = cantidad - {}  WHERE (codigo = {})".format(item['cantidad'], item['codigo']))
        cnn.commit()
    
    # Solicitando confirmación del cliente en el módulo de procesamiento de órdenes
    print("ESPERANDO CONFIRMACION")
    mensaje = '{"estado": 1, "mensaje": "solicitando confirmacion"}'
    enviarMensaje(settings.TOPIC_TO_1, mensaje)

    # Escuchando mensaje de confirmación
    hosts = [(settings.ACTIVEMQ_HOST, settings.ACTIVEMQ_PORT)]
    conn = stomp.Connection(host_and_ports=hosts)
    conn.set_listener('', ListenerConfirmacion(conn))
    conn.connect(wait=True, headers = {'client-id': 'fisi_utiles'} )
    conn.subscribe(destination=settings.TOPIC_FROM, id=987, ack='auto',headers = {'subscription-type': 'ANYCAST','durable-subscription-name':'confirmacion'})

    for x in range(int(settings.TIEMPO_ESPERA), 0, -1):
        seconds = x % 60
        minutes = int(x / 60) % 60
        hours = int (x / 3600)
        print(f"{hours:02}:{minutes:02}:{seconds:02}")
        time.sleep(1)
        print(confirmacion)
        if confirmacion == 1:
            # Proceso terminado satisfactoriamente y enviando mensaje al módulo de facturación
            print("CONFIRMACION DE LA COMPRA")
            mensaje = message
            print(mensaje)
            enviarMensaje(settings.TOPIC_TO_2, mensaje)
            break

    if confirmacion == 0:
        print("NO SE REALIZO LA CONFIRMACION DE LA COMPRA")
        for item in items:
            cur.execute("UPDATE articulos SET cantidad = cantidad + {}  WHERE (codigo = {})".format(item['cantidad'], item['codigo']))
            cnn.commit()

    conn.disconnect()


confirmacion = 0        
hosts = [(settings.ACTIVEMQ_HOST, settings.ACTIVEMQ_PORT)]
conn = stomp.Connection(host_and_ports=hosts)
conn.set_listener('', ListenerArticulos(conn))
conn.connect(wait=True, headers = {'client-id': 'fisi_utiles'} )
conn.subscribe(destination=settings.TOPIC_FROM, id=123, ack='auto', headers = {'subscription-type': 'ANYCAST','durable-subscription-name':'inventario'})
print("Esperando mensajes...")
while 1:
    time.sleep(10)
    break;