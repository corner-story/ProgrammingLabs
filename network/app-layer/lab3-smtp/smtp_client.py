import socket
import sys


if len(sys.argv) != 3:
    print("please input your name and auth code!")
    sys.exit()

username, auth_code = sys.argv[1], sys.argv[2]

email = input("input email addr: ")

msg = """TO: lambdafate.163.com
FROM: 2350622075@qq.com
Subject: use telnet and smtp send email!

Hello, I'am <h2>Client</h2>!
i'am testing smtp!
see you!"""

endmsg = "\r\n.\r\n"

# Choose a mail server (e.g. Google mail server) and call it mailserver 
HOST = "smtp.qq.com"
PORT = 25


steps = [
    {"assert": "220", "operate": "HELO qq.com\r\n"},
    {"assert": "250", "operate": "auth login\r\n"},
    {"assert": "334", "operate": username + "\r\n"},
    {"assert": "334", "operate": auth_code + "\r\n"},
    {"assert": "235", "operate": f"MAIL FROM: <2350622075@qq.com>\r\n"},
    {"assert": "250", "operate": f"RCPT TO: <{email}>\r\n"},
    {"assert": "250", "operate": "DATA\r\n"},
    {"assert": "354", "operate": msg + endmsg},
    {"assert": "250", "operate": "quit\r\n"}
]

client = socket.socket(family=socket.AF_INET, type=socket.SOCK_STREAM)
client.connect((HOST, PORT))

for step in steps:
    data = client.recv(1024).decode()
    print(f"Server: {data}")
    if data[:3] != step["assert"]:
        print(f"error: get error code!")
        sys.exit(1)
    print("Client: {}".format(step["operate"]))
    client.send(step["operate"].encode("utf8"))

