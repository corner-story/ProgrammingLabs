#include<stdio.h>
#include<unistd.h>
#include<sys/types.h>
#include<sys/wait.h>

int main(){
    int num = 0;
    int* count = &num;
    pid_t child1, child2;

    if((child1 = fork()) == 0){
        (*count)++;
        printf("i am child1   count=%d\n", *count);
    }else{
        if((child2 = fork()) == 0){
            (*count)++;
            sleep(3);
            printf("i am child2   count=%d\n", *count);
        }else{
            //父进程
            (*count)++;
            sleep(1);
            printf("i am father   count=%d\n", *count);

            waitpid(child1, NULL, 0);
            waitpid(child2, NULL, 0);
        }
    }


}