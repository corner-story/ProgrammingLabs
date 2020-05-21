#include<stdio.h>
#include<stdlib.h>
#include<sys/types.h>
#include<sys/ipc.h>
#include<sys/shm.h>
#include<sys/wait.h>
#include<unistd.h>
#include<string.h>
#include<assert.h>

int main(int argc, char *argv[]){
    key_t key = ftok(".", 1);
    printf("shared memory key: %d\n", key);
    assert(key != -1);

    int shmid = shmget(key, 4096, IPC_CREAT | IPC_EXCL | 0600);
    assert( shmid != -1);

    char *shm = shmat(shmid, NULL, 0);

    strcpy(shm, "Hello, shared memory!");
    if(fork() == 0){
        strncpy(shm, "fuck", 4);
        
        shmdt(shm);
        exit(0);
    }

    wait(NULL);
    printf("shared memory: %s\n", shm);

    // delete memory map
    shmdt(shm);
    // delete shared memory id
    shmctl(shmid, IPC_RMID, NULL);
    return 0;
}