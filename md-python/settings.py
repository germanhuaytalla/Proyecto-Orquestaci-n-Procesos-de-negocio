from dotenv import load_dotenv
import os
from dataclasses import dataclass

@dataclass
class Settings:
    MYSQL_HOST: str
    MYSQL_USER: str
    MYSQL_PASSWORD: str
    MYSQL_DB: str
    ACTIVEMQ_PORT: str
    TIEMPO_ESPERA: int

load_dotenv()

settings = Settings(
    MYSQL_HOST=os.getenv("MYSQL_HOST"),
    MYSQL_USER=os.getenv("MYSQL_USER"),
    MYSQL_PASSWORD=os.getenv("MYSQL_PASSWORD"),
    MYSQL_DB=os.getenv("MYSQL_DB"),
    ACTIVEMQ_PORT=os.getenv("ACTIVEMQ_PORT"),
    TIEMPO_ESPERA=os.getenv("TIEMPO_ESPERA")
)