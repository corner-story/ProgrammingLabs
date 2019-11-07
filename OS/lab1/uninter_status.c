// 查看不可中断状态
#include<unistd.h>
#include<stdio.h>

int main(){

    if(vfork() == 0){
        //子进程
        sleep(120);
        return 0;
    }

    return 0;
}