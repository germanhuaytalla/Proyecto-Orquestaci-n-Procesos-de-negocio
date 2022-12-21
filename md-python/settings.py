from dotenv import load_dotenv
import os
from dataclasses import dataclass

@dataclass
class Settings:
    MYSQL_HOST: str
    MYSQL_USER: str
    MYSQL_PASSWORD: str
    MYSQL_DB: str
    ACTIVEMQ_HOST: str
    ACTIVEMQ_PORT: str
    TOPIC_FROM: str
    TOPIC_TO_1: str
    TOPIC_TO_2: str
    TIEMPO_ESPERA: int

load_dotenv()

settings = Settings(
    MYSQL_HOST=os.getenv("MYSQL_HOST"),
    MYSQL_USER=os.getenv("MYSQL_USER"),
    MYSQL_PASSWORD=os.getenv("MYSQL_PASSWORD"),
    MYSQL_DB=os.getenv("MYSQL_DB"),
    ACTIVEMQ_HOST=os.getenv("ACTIVEMQ_HOST"),
    ACTIVEMQ_PORT=os.getenv("ACTIVEMQ_PORT"),
    TOPIC_FROM=os.getenv("TOPIC_FROM"),
    TOPIC_TO_1=os.getenv("TOPIC_TO_1"),
    TOPIC_TO_2=os.getenv("TOPIC_TO_2"),
    TIEMPO_ESPERA=os.getenv("TIEMPO_ESPERA")
)