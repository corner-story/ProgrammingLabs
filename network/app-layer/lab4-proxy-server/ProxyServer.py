import socket
import sys

tips = {
    200: "OK",
    404: "NotFound"
}

cache = {

}

def make_response(conn, status=200, filename=None):
    response = f"HTTP/1.1 {status} {tips[status]}\r\n"
    response += "Content-Type: text/html\r\n"
    response += "\r\n"

    conn.send(response.encode("utf8"))
    if filename == None:
        conn.close()
        return
    with open(filename, "rb") as fd:
        conn.send(fd.read())
    conn.close()
    return

def make_request(url, type="GET"):
    info = url.split("/")
    path = info[1] if len(info) > 1 else ""
    request = f"{type} /{path} HTTP/1.1\r\n"
    request += "\r\n"
    client = socket.socket(family=socket.AF_INET, type=socket.SOCK_STREAM)
    data = None
    try:
        client.connect((info[0], 80))
        client.send(request.encode("utf8"))
        data = b''
        while len(buffer := client.recv(1024)) != 0:
            data += buffer
    except Exception as e:
        print(f"make-request error: {e}")
    finally:
        client.close()
        return data


HOST = "0.0.0.0"
PORT = 8888

cache_num = 0 

server = socket.socket(family=socket.AF_INET, type=socket.SOCK_STREAM)
server.bind((HOST, 8888))
server.listen(5)

print(f"run proxy server in port[{PORT}]....")
while True:
    conn, addr = server.accept()
    data = conn.recv(1024).decode()

    if len(data) == 0:
        conn.close()
        continue

    url = data.split(" ")[1][1:].split("//")
    url = url[len(url)-1]
    print(f"connection from {addr} ---> {url}")
    
    # return 404
    if len(url) == 0:
        make_response(conn, 404, "404.html")
        continue
    
    if cache.get(url, None) != None:
        print("cache hit....")
        make_response(conn, 200, cache.get(url))
        continue
    
    # cache miss
    print("cache miss....")
    data = make_request(url)
    if data == None:
        make_response(conn, 404, "404.html")
        continue
    conn.send(data)
    conn.close()

    # save cache
    cache_data = data.decode().split("\r\n\r\n")[1]
    with open(f"cache_{cache_num}", "wb") as fd:
        fd.write(cache_data.encode("utf8"))
    cache[url] = f"cache_{cache_num}"
    cache_num += 1


