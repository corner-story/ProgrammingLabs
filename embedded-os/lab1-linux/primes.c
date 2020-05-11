#include<stdio.h>

int check_prime(int num){
    if(num < 2){
        return 0;
    }
    for (int i = num-1; i > 1; i--){
        if(num % i == 0){
            return 0;
        }
    }
    return 1;
}


unsigned int print_primes(int num){
    if(num < 2){
        return 0;
    }
    unsigned int count = 0;
    while(num > 1){
        if(check_prime(num)){
            printf("%d ", num);
            count++;
        }
        num--;
    }
    if(count){
        printf("\n");
    }
    return count;
}
