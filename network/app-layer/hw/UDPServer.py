import socket
import sys

HOST = "0.0.0.0"
PORT = 8888

if len(sys.argv) > 1:
    PORT = int(sys.argv[1])

socket = socket.socket(family=socket.AF_INET, type=socket.SOCK_DGRAM)
socket.bind((HOST, PORT))

print(f"run udp server in {HOST}:{PORT}!\n")

while True:
    data, addr = socket.recvfrom(1024)
    print(f"[UDPServer]: receive data from {addr[0]}:{addr[1]}: {data.decode()}")