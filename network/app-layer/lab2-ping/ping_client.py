import socket
import time

HOST = "39.107.83.159"
PORT = 6666

client = socket.socket(family=socket.AF_INET, type=socket.SOCK_DGRAM)
num = 1
MAX_NUM = 10

miss = 0

while num <= MAX_NUM:
    data = f"Ping {num} " + time.strftime("%Y-%m-%d %H:%M:%S", time.localtime())
    
    client.sendto(data.encode("utf8"), (HOST, PORT))
    send_time = time.time()
    try:
        client.settimeout(1)
        data, addr = client.recvfrom(1024)
        print(data.decode(), end="")
    except Exception as e:
        print(f"request[{num}] timeout", end="")
        miss += 1
    print(f" ---> rtt: {time.time()-send_time}")
    num += 1

print("\n**********************")
print(f"send: {MAX_NUM}, success: {MAX_NUM-miss}, miss: {miss}")
print(f"miss rate: {miss / MAX_NUM}")