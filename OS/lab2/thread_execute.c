#include<stdio.h>
#include<pthread.h>
#include<unistd.h>


void* child1(void* count){
    sleep(2);
    int* num = (int*)count;
    (*num)++;
    printf("I am child1  count=%d\n", *num);
}

void* child2(void* count){
    int* num = (int*)count;
    (*num)++;
    printf("I am child2  count=%d\n", *num);
}

int main(){
    pthread_t child1_tid, child2_tid;
    int count = 0;

    pthread_create(&child1_tid, 0, child1, &count);
    pthread_create(&child2_tid, 0, child2, &count);

    sleep(1);
    count++;
    printf("I am father   count=%d\n", count);
    pthread_join(child1_tid, 0);
    pthread_join(child2_tid, 0);
    return 0;
}