import socket

request = "GET / HTTP/1.1\r\n"
request += "\r\n"

client = socket.socket(family=socket.AF_INET, type=socket.SOCK_STREAM)
client.connect(("www.baidu.com", 80))

client.send(request.encode("utf8"))
data = client.recv(1024)

print(data)