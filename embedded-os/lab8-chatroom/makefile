require:
	sudo apt-get install libncurses5-dev libncursesw5-dev
server: server.c
	gcc server.c -o server -pthread
client: client.c
	gcc client.c -o client -pthread -lncursesw

.PHONY: build
build:
	make require && make server && make client