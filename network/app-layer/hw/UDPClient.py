import socket 
import sys

HOST = "127.0.0.1"
PORT = 6666

if len(sys.argv) > 1:
    HOST = sys.argv[1]
if len(sys.argv) > 2:
    PORT = int(sys.argv[2])

socket = socket.socket(family=socket.AF_INET, type=socket.SOCK_DGRAM)

socket.sendto(bytes("hello, i'am udp client!\n", encoding="utf8"), (HOST, PORT))

