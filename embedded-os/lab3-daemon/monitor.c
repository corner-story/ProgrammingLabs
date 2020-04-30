#include<stdio.h>
#include<unistd.h>
#include"daemon.h"

int check_proc();
void exec_proc();

const char *test_path = "/home/lambdafate/projects/embedded-os/lab3-daemon/";
const char *proc = "daemon-monitor";

int main(int argc, char *argv[]){
    to_be_daemon();
    write_log(proc, "daemon-monitor begin running!\n");
    while(1){
        sleep(5);
        if(check_proc() == -1){
            write_log(proc, "test process no running? don't worry, i will run it!\n");
            exec_proc();
        }
    }
    return 0;
}


// check "./test" process status
int check_proc(){
    const char *cmd = "ps -aux | grep ./test | grep -v grep";
    char buffer[32];

    FILE *fp = NULL;
    if((fp = popen(cmd, "r")) == NULL){
        return -1;
    }
    int res = 1;
    if(fgets(buffer, sizeof(buffer), fp) == NULL){
        res = -1;
    }
    pclose(fp);
    return res;
}

// using fork-exec to execute test
void exec_proc(){
    if(fork() == 0){
        write_log(proc, "exec_proc() -> fork success!\n");
        // change work dir
        chdir(test_path);
        // child process
        if(execl("./test", "./test", NULL) == -1){
            write_log(proc, "exec_proc() -> execl error!\n");           
        }
    }
}