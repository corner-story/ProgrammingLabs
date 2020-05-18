import socket
import sys

HOST = "127.0.0.1"
PORT = 8888

if len(sys.argv) > 1:
    HOST = sys.argv[1]
if len(sys.argv) > 2:
    PORT = int(sys.argv[2])

with socket.create_connection((HOST, PORT)) as client:
    client.send(bytes("Hello, fuck!\n", encoding="utf8"))
    print(client.recv(1024))
    