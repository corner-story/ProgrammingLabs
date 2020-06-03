#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <unistd.h>
#include <signal.h>
#include <string.h>
#include <time.h>
#include <pthread.h>
#include <stdarg.h>
#include <assert.h>

/*
    server->clients message formate:
    [SERVER_INS or sender's name] & [SERVER_INS_xxx] &  [online gay] & [update time] & [message]

*/

#define SERVER_PORT 6666
#define MAX_CLIENT_NUM  32
#define SERVER_INS      "SERVER-INS"
#define SERVER_INS_FMT  "%s&%d&%d&%s&%s"
#define SERVER_INS_MESSAGE      0
#define SERVER_INS_USER_COME    1
#define SERVER_INS_USER_EXIT    2

struct client_t {
    pthread_t thread;
    int sockfd;
    char username[20];
    int name_length;
};

struct client_t clients[MAX_CLIENT_NUM];

int sockfd;
pthread_mutex_t mutex;

void *request_handler(void *sockfd);
void sigint_handler(int num);
char *get_time();
struct client_t *get_client();
void send_all(char buffer[], int length);
void init_client();
void clear_client(struct client_t *client);
void delete_clients();
struct client_t *get_client_from_sockfd(int sockfd);
void send_server_ins(char *fmt, ...);
int online_gay();
void forward_server_ins(int ins_num, char *message);
void forward_message(char *username, char *message);



int main(int argc, char *argv[])
{
    // set sigint handler
    signal(SIGINT, sigint_handler);
    // ignore sigpipe
    signal(SIGPIPE, SIG_IGN);

    // init all clients
    init_client();

    // init mutex
    int init_res = pthread_mutex_init(&mutex, NULL);
    assert(init_res == 0);

    sockfd = socket(AF_INET, SOCK_STREAM, 0);
    assert(sockfd != -1);

    // bind
    struct sockaddr_in serv_addr;
    memset(&serv_addr, 0, sizeof(serv_addr));
    serv_addr.sin_family = AF_INET;
    serv_addr.sin_addr.s_addr = inet_addr("0.0.0.0");
    serv_addr.sin_port = htons(SERVER_PORT);
    assert(bind(sockfd, (struct sockaddr *)&serv_addr, sizeof(serv_addr)) != -1);

    // listen
    assert(listen(sockfd, 20) != -1);

    printf("run tcp server in %s:%d ......\n", "127.0.0.1", SERVER_PORT);
    struct sockaddr_in clnt_addr;
    socklen_t clnt_addr_size = sizeof(clnt_addr);
    while (1){
        int cfd = accept(sockfd, (struct sockaddr *)&clnt_addr, &clnt_addr_size);

        printf("[%s] connect from %s:%d  socket: %d\n", get_time(), inet_ntoa(clnt_addr.sin_addr), clnt_addr.sin_port, cfd);
        
        struct client_t *c = get_client();
        if(c == NULL){
            close(cfd);
            continue;
        }
        c->sockfd = cfd;
        // create a new thread to connect to client
        pthread_t thread;
        int res = pthread_create(&thread, NULL, request_handler, (void*)(&cfd));

        if(res == 0){
            c->thread = thread;
        }else{
            close(cfd);
        }
    }

    close(sockfd);
    return 0;
}


void *request_handler(void* sockfd){
    int fd = *((int*)sockfd);
    printf("current thread: %ld socket: %d\n", pthread_self(), fd);
    struct client_t *client = get_client_from_sockfd(fd);
    char buffer[1024];
    char data[1024];
    int n = -1;
    // read username, user come in chat-room
    if ((n = recv(fd, buffer, 1024, 0)) > 0){
        buffer[n] = '\0';
        strcpy(client->username, buffer);
        client->name_length = n;
        strncpy(data, buffer, n);
        data[n] = '&';
    }

    // send 'user come in chatroom msg'
    forward_server_ins(SERVER_INS_USER_COME, client->username);

    while ((n = recv(fd, buffer, 1024, 0)) > 0){
        buffer[n] = '\0';
        printf("[%s]: %s\n", client->username, buffer);
        forward_message(client->username, buffer);
    }


    // user get out of the chat room
    close(fd);
    clear_client(client);
    // send 'user get out of the chatroom' msg
    forward_server_ins(SERVER_INS_USER_EXIT, client->username);
    pthread_exit(NULL);
}

void sigint_handler(int num){
    close(sockfd);
    delete_clients();
    pthread_mutex_destroy(&mutex);
    exit(0);
}

char *get_time(){
    time_t t = time(NULL);
    char *time = ctime(&t);
    char *res = time;
    while (*time != '\n')
    {
        time++;
    }
    *time = '\0';
    return res;
}

struct client_t* get_client(){
    struct client_t *client = NULL;
    pthread_mutex_lock(&mutex);
    for(int i=0; i<MAX_CLIENT_NUM; i++){
        if(clients[i].sockfd == 0){
            client = &clients[i];
        }
    }
    pthread_mutex_unlock(&mutex);
    return client;
}

void send_all(char buffer[], int length){
    pthread_mutex_lock(&mutex);
    for (int i = 0; i < MAX_CLIENT_NUM; i++){
        if(clients[i].sockfd != 0){
            send(clients[i].sockfd, buffer, length, 0);
        }
    }
    pthread_mutex_unlock(&mutex);
}


void init_client(){
    for (int i = 0; i < MAX_CLIENT_NUM; i++){
        clients[i].sockfd = 0;
        clients[i].thread = 0;
    }   
}

struct client_t *get_client_from_sockfd(int sockfd){
    for (int i = 0; i < MAX_CLIENT_NUM; i++){
        if(clients[i].sockfd == sockfd){
            return &clients[i];
        }
    }
    return NULL;
}

void clear_client(struct client_t *client){
    pthread_mutex_lock(&mutex);
    client->sockfd = 0;
    client->thread = 0;
    pthread_mutex_unlock(&mutex);
}

void delete_clients(){
    for (size_t i = 0; i < MAX_CLIENT_NUM; i++){
        if(clients[i].sockfd != 0){
            close(clients[i].sockfd);
        }
    }
}

int online_gay(){
    int gay = 0;
    pthread_mutex_lock(&mutex);
    for (int i = 0; i < MAX_CLIENT_NUM; i++){
        if (clients[i].sockfd != 0 && clients[i].thread != 0){
            gay++;
        }
    }
    pthread_mutex_unlock(&mutex);
    return gay;
}

// forward one client's message to all clients
void forward_message(char *username, char *message){
    send_server_ins(SERVER_INS_FMT, username, SERVER_INS_MESSAGE, online_gay(), get_time(), message);
}

void forward_server_ins(int ins_num, char *message){
    send_server_ins(SERVER_INS_FMT, SERVER_INS, ins_num, online_gay(), get_time(), message);
}

void send_server_ins(char *fmt, ...){
    char buffer[1024];
    va_list args;
    va_start(args, fmt);
    vsprintf(buffer, fmt, args);
    va_end(args);

    int length = strlen(buffer);
    buffer[length+1] = '\n';
    printf("[%s]: %s\n", "SEND_ALL", buffer);
    send_all(buffer, length);
}