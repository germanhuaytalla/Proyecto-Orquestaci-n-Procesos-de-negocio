import stomp
import time
from settings import settings

def enviarMensaje(topic="", msg=""):
    hosts = [(settings.ACTIVEMQ_HOST, settings.ACTIVEMQ_PORT)]
    conn = stomp.Connection(host_and_ports=hosts)
    conn.connect()
    conn.send(body=msg, destination=topic)
    print("Mensaje eviado a ", topic)
    time.sleep(2)
    conn.disconnect()