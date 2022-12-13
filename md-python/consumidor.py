import stomp
import time
import json
import mysql.connector
from productor import enviarMensaje
from settings import settings

class ListenerArticulos(stomp.ConnectionListener):
    def on_error(self, message):
        print('error "%s"' % message.body)
    def on_message(self, message):
        print('lista de articulos recibida con exito "%s' % message.body)
        lista_articulos = message.body
        validarArticulos(lista_articulos)

class ListenerConfirmacion(stomp.ConnectionListener): 
    def __init__(self):
        global confirmacion
        confirmacion = False
        
    def on_error(self, message):
        print('error "%s"' % message.body)

    def on_message(self, message):
        global confirmacion
        confirmacion = True
        print('confirmacion recibida con exito "%s' % message.body)

    def on_disconnected(self):
        print('disconnected')
        

def recibirMensaje():
    hosts = [(settings.MYSQL_HOST, settings.ACTIVEMQ_PORT)]
    conn = stomp.Connection(host_and_ports=hosts)
    conn.set_listener('', ListenerArticulos())
    conn.connect(wait=True, headers = {'client-id': 'clientname'} )
    conn.subscribe(destination='ordenes/lista_articulos', id=123, ack='auto', headers = {'subscription-type': 'MULTICAST','durable-subscription-name':'articulos'})
    time.sleep(int(settings.TIEMPO_ESPERA) + 5)
    # print("DESCONECTANDO...")
    
def validarArticulos(lista):
    lista_articulos = json.loads(lista)
    print("Validando lista de artículos")
    cnn = mysql.connector.connect(host="localhost", user="root", passwd="", database="fisi_tiendasutiles")
    cur = cnn.cursor()
    i = 0
    print(lista_articulos)
    for codigo in lista_articulos:
        print(codigo)
        cur.execute("SELECT cantidad FROM articulos where codigo = {}".format(codigo))
        stock = cur.fetchall()
        stock = stock[0]
        if cur.rowcount == 0:
            mensaje = 'El articulo {} no existe'.format(codigo)
            print(mensaje)
            enviarMensaje('ordenes/error', mensaje)
            break
        else:
            if stock[0] >= int(lista_articulos[codigo]):
                i = i + 1
                if i == len(lista_articulos):
                    print("RESERVANDO ARTICULOS...")
                    reservar(lista_articulos)  
            else:
                mensaje = 'No hay stock suficiente del articulo {}. Solo hay {} unidades'.format(codigo, stock[0])
                print(mensaje)
                enviarMensaje('ordenes/error', mensaje)
                break

def reservar(lista_articulos):
    cnn = mysql.connector.connect(host="localhost", user="root", passwd="", database="fisi_tiendasutiles")
    cur = cnn.cursor()
    for codigo in lista_articulos:
        cur.execute("UPDATE articulos SET cantidad = cantidad - {}  WHERE (codigo = {})".format(lista_articulos[codigo], codigo))
        cnn.commit()
    
    hosts = [(settings.MYSQL_HOST, settings.ACTIVEMQ_PORT)]
    conn = stomp.Connection(host_and_ports=hosts)
    conn.set_listener('', ListenerConfirmacion())
    conn.connect(wait=True, headers = {'client-id': 'clientname'} )
    conn.subscribe(destination='ordenes/confirmacion', id=987, ack='auto',headers = {'subscription-type': 'MULTICAST','durable-subscription-name':'confirmaciones'})

    print("ESPERANDO CONFIRMACION")

    for x in range(int(settings.TIEMPO_ESPERA), 0, -1):
        seconds = x % 60
        minutes = int(x / 60) % 60
        hours = int (x / 3600)
        print(f"{hours:02}:{minutes:02}:{seconds:02}")
        time.sleep(1)
        print(confirmacion)
        if confirmacion == True:
            print("CONFIRMACION DE LA COMPRA")
            mensaje = 'Enviando mensaje al módulo de Facturación'
            print(mensaje)
            enviarMensaje('facturacion', mensaje)
            break
    
    if confirmacion == False:
        print("NO SE REALIZO LA CONFIRMACION DE LA COMPRA")
        for codigo in lista_articulos:
            cur.execute("UPDATE articulos SET cantidad = cantidad + {}  WHERE (codigo = {})".format(lista_articulos[codigo], codigo))
            cnn.commit()

if __name__ == '__main__':
    confirmacion = False
    recibirMensaje()