import socket
import sys

HOST = socket.gethostname()
PORT = 8888

if len(sys.argv) > 1:
    PORT = int(sys.argv[1])

with socket.create_server((HOST, PORT)) as server:
    print(f"run tcp server in {HOST}:{PORT}!\n")
    while True:
        conn, addr = server.accept()
        print(f"a accept from {addr[0]}:{addr[1]}")
        conn.send(bytes("there is socket server!\n", encoding="utf8"))
        data = conn.recv(1024)    
        print(data)
        conn.close()