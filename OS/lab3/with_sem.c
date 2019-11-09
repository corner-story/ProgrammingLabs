#include<stdio.h>
#include<stdlib.h>
#include<unistd.h>

#include<fcntl.h>
#include<sys/stat.h>
#include<semaphore.h>

/*
    信号量. 编译 gcc name.c -pthread
*/

int main(int argc, char* argv[]){
    char message = 'X';
    if(argc > 1){
        message = argv[1][0];
    }

    sem_t* mutex = sem_open("first_sem", O_CREAT, 0666, 1);
    for(int i=0; i<3; i++){
        sem_wait(mutex); //首先尝试获取资源

        printf("%c", message);
        fflush(stdout);
        sleep(rand()%3);
        printf("%c", message);
        fflush(stdout);

        sem_post(mutex); //释放资源
        sleep(2);
    }
    
    sleep(3);
    //关闭mutex
    sem_close(mutex);
    sem_unlink("first_sem");
    return 0;
}