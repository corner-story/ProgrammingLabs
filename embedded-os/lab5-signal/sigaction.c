#include<stdio.h>
#include<sys/types.h>
#include<unistd.h>
#include<signal.h>

volatile int quit = 0;

void sigint_handler(int num){
    quit = 1;
    printf("I have get SIGINT.\n");
}

void sigquit_handler(int num){
    printf("I have get SIGQUIT.\n");
}

int main(int argc, char *argv[]){
    // set sigint handler
    struct sigaction sigint_act;
    sigint_act.sa_handler = sigint_handler;
    sigemptyset(&sigint_act.sa_mask);
    sigint_act.sa_flags = 0;
    sigaction(SIGINT, &sigint_act, NULL);

    // set sigquit handler
    struct sigaction sigquit_act;
    sigquit_act.sa_handler = sigquit_handler;
    sigemptyset(&sigquit_act.sa_mask);
    sigquit_act.sa_flags = 0;
    sigaction(SIGQUIT, &sigquit_act, NULL);
    
    printf("Test: send current process SIGINT and SIGQUIT.\n");
    kill(getpid(), SIGINT);
    kill(getpid(), SIGQUIT);

    printf("block sigint....\n");
    sigset_t set, prev_set;
    sigemptyset(&set);
    sigaddset(&set, SIGINT);
    sigprocmask(SIG_SETMASK, &set, &prev_set);
    
    quit = 0;
    kill(getpid(), SIGINT);             // sigint will be deal until UNBLOCK SIGINT. 
    if(sigismember(&set, SIGINT) == 1 && quit == 0){
        printf("The SIGINT signal has been ignored\n");
    }
    
    printf("unblock sigint....\n");
    sigprocmask(SIG_SETMASK, &prev_set, NULL);

    return 0;
}