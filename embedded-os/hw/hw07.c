#include<stdio.h>
#include<sys/types.h>
#include<sys/wait.h>
#include<unistd.h>
#include<assert.h>

int main(int argc, char *argv[]){
    int fds[2];
    assert(pipe(fds) != -1);

    pid_t pid = fork();
    assert(pid != -1);
    if(pid == 0){
        close(fds[0]);
        assert(dup2(fds[1], 1) != -1);
        assert(execlp("ps", "ps", "-ef", NULL) != -1);
    }
    close(fds[1]);
    assert(dup2(fds[0], 0) != -1);
    assert(execlp("grep", "grep", "nfs", NULL) != -1);
    return 0;
}