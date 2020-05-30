#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <sys/ipc.h>
#include <sys/shm.h>
#include <sys/sem.h>
#include <string.h>
#include <assert.h>

union semun {
    int val;               /* Value for SETVAL */
    struct semid_ds *buf;  /* Buffer for IPC_STAT, IPC_SET */
    unsigned short *array; /* Array for GETALL, SETALL */
    struct seminfo *__buf; /* Buffer for IPC_INFO
                                           (Linux-specific) */
};

static void semaphore_p(int sem_id);
static void semaphore_v(int sem_id);

int main(int arc, char *argv[])
{
    key_t key = ftok(".", 1);
    assert(key != -1);

    int shmid = shmget(key, 4096, IPC_CREAT | IPC_EXCL | 0600);
    assert(shmid != -1);

    char *shm = shmat(shmid, NULL, 0);

    int semid = semget(key, 1, IPC_CREAT | IPC_EXCL | 0600);
    assert(semid != -1);

    union semun sem_union;
    sem_union.val = 1;
    assert(semctl(semid, 0, SETVAL, sem_union) != -1);

    pid_t pid = fork();
    assert(pid != -1);

    if (pid == 0)
    {
        sleep(1);
        semaphore_p(semid);
        printf("\nread process[%d] lock....\n", getpid());
        printf("read data from shared memory:\n");
        printf("%s", shm);
        printf("read process[%d] unlock....\n", getpid());
        semaphore_v(semid);
        exit(0);
    }
    char buffer[64];
    char *work = shm;
    semaphore_p(semid);
    printf("write process[%d] lock....\n", getpid());
    printf("please input your data:\n");
    while (1)
    {
        char *temp = fgets(buffer, 64, stdin);
        int flag = strcmp(temp, "end\n");
        while (*temp)
        {
            *work++ = *temp++;
        }
        if (flag == 0)
        {
            break;
        }
    }
    *work = '\0';
    printf("write process[%d] unlock....\n", getpid());
    semaphore_v(semid);

    wait(NULL);
    union semun sem_union2;
    semctl(semid, 0, IPC_RMID, sem_union2);
    // delete memory map
    shmdt(shm);
    // delete shared memory id
    shmctl(shmid, IPC_RMID, NULL);
    return 0;
}

static void semaphore_p(int sem_id)
{
    struct sembuf sem_b;
    sem_b.sem_num = 0;
    sem_b.sem_op = -1; //P()
    sem_b.sem_flg = SEM_UNDO;
    assert(semop(sem_id, &sem_b, 1) != -1);
}

static void semaphore_v(int sem_id)
{
    struct sembuf sem_b;
    sem_b.sem_num = 0;
    sem_b.sem_op = 1; //V()
    sem_b.sem_flg = SEM_UNDO;
    assert(semop(sem_id, &sem_b, 1) != -1);
}
