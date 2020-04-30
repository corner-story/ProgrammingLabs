#include<stdio.h>
#include<stdlib.h>
#include<sys/types.h>
#include<sys/stat.h>
#include<unistd.h>
#include<signal.h>
#include<syslog.h>

void to_be_daemon(){
    pid_t pid;
    if((pid = fork()) < 0){
        printf("can't fork!\n");
        exit(1);
    }else if(pid > 0){
        exit(0);
    }

    if(setsid() < 0){
        printf("setsid error\n");
        exit(1);
    }
    umask(0);
    chdir("/");

    for(int i = 0; i < getdtablesize(); i++){
        close(i);
    }
}

void write_log(const char *proc, char *message){
    openlog(proc, LOG_PID|LOG_CONS, LOG_USER);
    syslog(LOG_INFO, "%s", message);
    closelog();
}