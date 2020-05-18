import socket
import sys

# HOST = socket.gethostbyname(socket.gethostname())
HOST = "0.0.0.0"
PORT = 8888

if len(sys.argv) > 1:
    PORT = int(sys.argv[1])

with socket.create_server((HOST, PORT)) as server:
    print(f"run tcp server in {HOST}:{PORT}!\n")
    while True:
        conn, addr = server.accept()
        print(f"[TCPServer]: a accept from {addr[0]}:{addr[1]}")
        conn.send("there is tcp socket server!\n".encode("utf8"))
        data = conn.recv(1024).decode()   
        print(data)
        conn.close()