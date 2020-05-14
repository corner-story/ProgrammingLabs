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
    
    int test2_fd = open("./test2", O_CREAT | O_WRONLY, 0777);
    assert(test2_fd != -1);

    int fifo_fd = open(fifo, O_RDONLY);
    assert(fifo_fd != -1);

    char buffer[128];
    int bytes = 0;
    while ((bytes = read(fifo_fd, buffer, 128)) > 0){
        write(test2_fd, buffer, bytes);
    }
    return 0;
}
