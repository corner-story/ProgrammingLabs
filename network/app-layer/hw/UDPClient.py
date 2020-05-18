import socket 
import sys

HOST = "39.107.83.159"
PORT = 8888

if len(sys.argv) > 1:
    HOST = sys.argv[1]
if len(sys.argv) > 2:
    PORT = int(sys.argv[2])

socket = socket.socket(family=socket.AF_INET, type=socket.SOCK_DGRAM)

socket.sendto("hello, i'am udp client!\n".encode("utf8"), (HOST, PORT))

