#include<stdio.h>
#include<sys/types.h>
#include<sys/stat.h>
#include<unistd.h>
#include<fcntl.h>
#include<assert.h>

int main(int argc, char *argv[]){
    const char *fifo = "/home/lambdafate/fifo";
    if(access(fifo, F_OK) == -1){
        assert(mkfifo(fifo, 0777) != -1);
    }    
    
    int test1_fd = open("./test1", O_RDONLY);
    assert(test1_fd != -1);

    int fifo_fd = open(fifo, O_WRONLY);
    assert(fifo_fd);

    char buffer[128];
    int bytes = 0;
    while((bytes = read(test1_fd, buffer, 128)) > 0){
        write(fifo_fd, buffer, bytes);
    }
    return 0;
}