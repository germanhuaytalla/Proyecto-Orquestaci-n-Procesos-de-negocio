import stomp
import time
from settings import settings

def enviarMensaje(topic="", msg=""):
    hosts = [(settings.MYSQL_HOST, settings.ACTIVEMQ_PORT)]
    conn = stomp.Connection(host_and_ports=hosts)
    conn.connect(wait=True,headers = {'client-id': 'clientname'} )
    conn.send(body=msg, destination=topic)
    time.sleep(2)
    conn.disconnect()