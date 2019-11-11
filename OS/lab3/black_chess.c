#include<stdio.h>
#include<stdlib.h>
#include<unistd.h>

#include<fcntl.h>
#include<sys/stat.h>
#include<semaphore.h>

/*
    设置两个信号量: red_mutex: 红子是否可走   black_mutex: 黑子是否可走

*/

int main(int argc, char* argv[]){

    sem_t* red_mutex = sem_open("red_chess", O_CREAT, 0666, 0);
    sem_t* black_mutex = sem_open("black_chess", O_CREAT, 0666, 1);

    for(int i=1; i<=20; i++){
        sem_wait(black_mutex);

        if(i != 20){
            printf("black chess move, next red should move!\n");
        }else{
            printf("black chess win!\n");
        }
        // fflush(stdout);
        sleep(1);

        sem_post(red_mutex);

    }
    
    sleep(3);
    sem_close(red_mutex);
    sem_close(black_mutex);
    sem_unlink("red_chess");
    sem_unlink("black_chess");

    return 0;
}