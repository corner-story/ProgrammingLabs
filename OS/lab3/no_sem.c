#include<stdio.h>
#include<stdlib.h>
#include<unistd.h>

int main(int argc, char* argv[]){
    char message = 'X';
    if(argc > 1){
        message = argv[1][0];
    }

    for(int i=0; i<3; i++){
        printf("%c", message);
        fflush(stdout);
        sleep(rand()%3);
        printf("%c", message);
        fflush(stdout);
        sleep(rand()%2);
    }
    sleep(3);
    //printf("over!");

    return 0;
}