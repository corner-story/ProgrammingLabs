import socket
import threading
import sys
import os

HOST = "0.0.0.0"
PORT = 8888
BUFFER_SIZE = 1024



def request_handler(conn, addr):
    try:
        print(f"\n{threading.current_thread().getName()} receive request {addr} ----> {conn.getsockname()}")
        http_message = conn.recv(BUFFER_SIZE).decode()
        
        filename = http_message.split(" ")
        if len(filename) < 2:
            conn.close()
            return
            
        filename = filename[1][1:]
        if filename == "":
            filename = "index.html"
        print(f"request file: {filename}")

        response = "HTTP/1.1 200 OK\r\n"
        file = None
        if os.access(filename, os.F_OK) and os.access(filename, os.R_OK):
            with open(filename, mode="rb") as fd:
                file = fd.read()
        else:
            response = "HTTP/1.1 404 NotFound\r\n"
            with open("404.html", "rb") as fd:
                file = fd.read()
        
        response += "Content-Type: text/html\r\n"
        response += "\r\n"

        conn.send(response.encode("utf8"))
        conn.send(file)
    except Exception as e:
        print(f"{threading.current_thread().getName()} error: {e}")
    finally:
        conn.close()


def main():
    global PORT

    if len(sys.argv) > 1:
        PORT = int(sys.argv[1])

    server = socket.socket(family=socket.AF_INET, type=socket.SOCK_STREAM);
    server.bind((HOST, PORT))
    server.listen(5)

    print(f"run tcp server in port[{PORT}]....\n")
    try:
        while True:
            conn, addr = server.accept()
            threading.Thread(target=request_handler, args=(conn, addr)).start()
    except Exception as e:
        print(f"{threading.current_thread().getName()} error: {e}")
    finally:
        server.close()


if __name__ == "__main__":
    main()
