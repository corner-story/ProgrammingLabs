#include<stdio.h>
#include"primes.h"

void fuck(){
    int num = 0;
    scanf("%d", &num); 
    if(num < 2){
        printf("%d\n", -1);
        return;
    }
    int count = print_primes(num);
    if(count == 0){
        printf("%d\n", -1);
    }
}