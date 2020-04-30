#include<stdio.h>
#include<unistd.h>
#include<fcntl.h>
#include"daemon.h"

const char *proc = "daemon-test";

int main(int argc, char *argv[]){
    to_be_daemon();
    write_log(proc, "daemon-test begin running!\n");
    while(1){
        sleep(5);
        int fd = open("/home/lambdafate/trush.txt", O_CREAT | O_WRONLY | O_APPEND, 0777);
        if(fd == -1){
            write_log(proc, "fd == -1\n");
        }else{
            write(fd, "hello\n", 6);
        }
        close(fd);
    }
    return 0;
}


