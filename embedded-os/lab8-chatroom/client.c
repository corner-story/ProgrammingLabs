#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <unistd.h>
#include <signal.h>
#include <string.h>
#include <pthread.h>
#include <ncurses.h>
#include <locale.h>
#include <assert.h>

#define SERVER_ADDR "39.107.83.159"
#define SERVER_PORT 6666

int sockfd;
int next_writed_row = 0;
WINDOW *win;
char username[12];
int name_length;

void *read_user_input(void *sockfd);
void *recv_from_server(void *sockfd);
void sigint_handler(int num);
void refresh_input_box();
void write_display_win(char buffer[]);
void init_client(int sockfd);
void write_and_refresh(char buffer[]);

int main(int argc, char *argv[]){
    printf("please input your name(length between 1~12): ");
    scanf("%s", username);
    name_length = strlen(username);
    if(name_length > 12){
        printf("username length error!\n");
        exit(0);
    }

    // set sigint handler
    signal(SIGINT, sigint_handler);

    setlocale(LC_ALL, "");
    win = initscr(); /* 初始化屏幕 */
    curs_set(TRUE);
    clear();
    refresh_input_box();

    // create a socket  
    sockfd = socket(AF_INET, SOCK_STREAM, 0);
    assert(sockfd != -1);

    struct sockaddr_in addr;
    socklen_t addrlen = sizeof(addr);
    addr.sin_family = AF_INET;
    addr.sin_addr.s_addr = inet_addr(SERVER_ADDR);
    addr.sin_port = htons(SERVER_PORT);

    int res = connect(sockfd, (struct sockaddr*)&addr, addrlen);
    assert(res != -1);

    init_client(sockfd);

    pthread_t p1, p2;
    assert(pthread_create(&p1, NULL, read_user_input, &sockfd) == 0);
    assert(pthread_create(&p2, NULL, recv_from_server, &sockfd) == 0);

    pthread_join(p1, NULL);
    pthread_join(p2, NULL);

    return 0;
}

void init_client(int sockfd){
    if(send(sockfd, username, name_length, 0) == -1){
        printf("init your name error!\n");
        exit(0);
    }
}

void *read_user_input(void *sockfd){
    int fd = *((int*)sockfd);
    char buffer[1024];
    while(1){
        getstr(buffer);
        send(fd, buffer, strlen(buffer), 0);
        // write_and_refresh(buffer);
    }
}

void *recv_from_server(void *sockfd){
    int fd = *((int *)sockfd);
    char buffer[1024];
    int n = -1;
    while((n = recv(fd, buffer, 1024, 0)) > 0){
        buffer[n] = '\0';
        write_and_refresh(buffer);
    }
}

void write_and_refresh(char buffer[]){
    write_display_win(buffer);
    refresh_input_box();
}

void sigint_handler(int num){
    close(sockfd);
    endwin();
    exit(0);
}

void refresh_input_box(){
    int row, clo;
    getmaxyx(stdscr, row, clo);
    int line_row = row * 4 / 5;
    for (int i = line_row; i < row; i++){
        move(i, 0);
        clrtoeol();
    }
    move(line_row, 0);
    for (int i = 0; i < clo; i++){
        printw("*");
    }
    refresh();
}

void write_display_win(char buffer[]){
    int row, clo;
    getmaxyx(stdscr, row, clo);
    int line_row = row * 4 / 5;
    int length = strlen(buffer);
    int last_row = next_writed_row + length / clo + (length % clo == 0 ? 0 : 1);
    if (last_row >= line_row){
        clear();
        next_writed_row = 0;
    }
    // write data
    move(next_writed_row, 0);
    char *begin = buffer;
    char *username = strsep(&begin, "&");
    char *time = strsep(&begin, "&");
    char *data = begin;
    printw("[%s] [%s] say:\n%s", username, time, data);
    // mvprintw(next_writed_row, 0, buffer);
    refresh();
    // update next_writed_row
    getyx(win, row, clo);
    next_writed_row = (clo == 0 ? row : (row + 1));
}