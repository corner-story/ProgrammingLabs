import socket
from random import randint

HOST = "0.0.0.0"
PORT = 6666

server = socket.socket(family=socket.AF_INET, type=socket.SOCK_DGRAM)
server.bind((HOST, PORT))

while True:
    maybe = randint(1,10)

    data, addr = server.recvfrom(1024)
    if maybe <= 3:
        continue
    server.sendto(data, addr)