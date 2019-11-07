// 查看僵尸状态
#include<stdio.h>
#include<unistd.h>
int main(){
    if(fork() > 0){
        sleep(60);
    }
}