#include<stdio.h>
#include<stdlib.h>
#include<sys/types.h>
#include<sys/socket.h>
#include<netinet/in.h>
#include<arpa/inet.h>
#include<unistd.h>
#include<signal.h>
#include<string.h>
#include<time.h>
#include<assert.h>

#define SERVER_PORT     6666

int sockfd;

void request_handler(int fd);
void sigint_handler(int num);
char *get_time();

int main(int argc, char *argv[]){
    // ignore sigchld
    signal(SIGCHLD, SIG_IGN);
    signal(SIGINT, sigint_handler);

    sockfd = socket(AF_INET, SOCK_STREAM, 0);
    assert(sockfd != -1);

    struct sockaddr_in serv_addr;
    memset(&serv_addr, 0, sizeof(serv_addr));
    serv_addr.sin_family = AF_INET;
    serv_addr.sin_addr.s_addr = inet_addr("0.0.0.0");
    serv_addr.sin_port = htons(SERVER_PORT);
    assert(bind(sockfd, (struct sockaddr*)&serv_addr, sizeof(serv_addr)) != -1);

    assert(listen(sockfd, 20) != -1);

    printf("run tcp server in %s:%d ......\n", "127.0.0.1", SERVER_PORT);
    struct sockaddr_in clnt_addr;
    socklen_t clnt_addr_size = sizeof(clnt_addr);
    while(1){
        int cfd = accept(sockfd, (struct sockaddr*)&clnt_addr, &clnt_addr_size);

        printf("[%s] connect from %s:%d\n", get_time(), inet_ntoa(clnt_addr.sin_addr), clnt_addr.sin_port);
        pid_t pid = fork();
        if(pid == 0){
            signal(SIGCHLD, SIG_DFL);
            signal(SIGINT, SIG_DFL);
            request_handler(cfd);
            exit(0);
        }
    } 

    close(sockfd);
    return 0;
}

void request_handler(int fd){
    char buffer[64];
    int n = -1;
    char *time = get_time();
    write(fd, time, strlen(time));
    write(fd, "\n", 1);
    while((n = read(fd, buffer, 64)) > 0){
        write(fd, buffer, n);
    }
    close(fd);
}

void sigint_handler(int num){
    close(sockfd);
    exit(0);
}

char *get_time(){
    time_t t = time(NULL);
    char *time = ctime(&t);
    char *res = time;
    while(*time != '\n'){
        time++;
    }
    *time = '\0';
    return res;
}