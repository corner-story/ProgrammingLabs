// 查看运行态
#include<stdio.h>

int main(){
    int i=0, j=0, k=0;
    int length = 100000;
    for(i=0; i<length; i++){
        for(j=0; j<length; j++){
            k++;
            k--;
        }
    }
    return 0;
}