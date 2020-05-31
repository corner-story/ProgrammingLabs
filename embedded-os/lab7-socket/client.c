#include<stdio.h>
#include<stdlib.h>
#include<sys/types.h>
#include<sys/socket.h>
#include<netinet/in.h>
#include<arpa/inet.h>
#include<unistd.h>
#include<signal.h>
#include<string.h>
#include<assert.h>

#define SERVER_ADDR "39.107.83.159"
#define SERVER_PORT 6666

int main(int argc, char *argv[]){

    int sockfd = socket(AF_INET, SOCK_STREAM, 0);
    assert(sockfd != -1);

    struct sockaddr_in serv_addr;
    memset(&serv_addr, 0, sizeof(serv_addr));
    serv_addr.sin_family = AF_INET;
    serv_addr.sin_addr.s_addr = inet_addr(SERVER_ADDR);
    serv_addr.sin_port = htons(SERVER_PORT);
    socklen_t addrlen = sizeof(serv_addr);

    assert(connect(sockfd, (struct sockaddr *)&serv_addr, addrlen) != -1);

    char *data = "there is client!\n";
    write(sockfd, data, strlen(data));
    char buffer[256];
    int n = -1;
    // read server's time info
    if((n = read(sockfd, buffer, 64)) > 0){
        write(1, buffer, n);
    }
    // read server's data
    if((n = read(sockfd, buffer, 64)) > 0){
        write(1, buffer, n);
    }
    close(sockfd);
    return 0;
}