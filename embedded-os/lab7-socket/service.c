#include<stdio.h>
#include<sys/types.h>
#include<sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include<unistd.h>
#include<signal.h>
#include<string.h>
#include<assert.h>

int main(int argc, char *argv[]){
    // ignore sigchld
    signal(SIGCHLD, SIG_IGN);

    int sockfd = socket(AF_INET, SOCK_STREAM, 0);
    assert(sockfd != -1);

    struct sockaddr_in serv_addr;
    memset(&serv_addr, 0, sizeof(serv_addr));
    serv_addr.sin_family = AF_INET;
    serv_addr.sin_addr.s_addr = inet_addr("0.0.0.0");
    serv_addr.sin_port = htons(6666);
    assert(bind(sockfd, (struct sockaddr*)&serv_addr, sizeof(serv_addr)) != -1);

    assert(listen(sockfd, 20) != -1);

    struct sockaddr_in clnt_addr;
    socklen_t clnt_addr_size = sizeof(clnt_addr);
    while(1){
        int cfd = accept(sockfd, (struct sockaddr*)&clnt_addr, &clnt_addr_size);
        write(cfd, "fuck you!", 9);
    }
    return 0;
}