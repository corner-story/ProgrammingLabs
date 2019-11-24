# ProgrammingLabs
programming labs in se courses



## Compiler: a little compiler!
what it supports:  
1. define valiable and assignment
2. if-else
3. for, while and break-continue
4. function
5. a little bytecode vm like python

a little example:
```c
/*
    a little program language
*/
int main(){
    string name := "cxk";  //声明语句
    int age := 20;
    double score := 23.5E+2;

    printf("name: ");
    printf(name);
    printf("age: ");
    printf(age);
    printf("score: ");
    printf(score);


    //判断语句
    if(age <= 22){
        printf("you are young!");
    }else{
        printf("you are old!");
    }


    //循环语句
    printf("******************************");
    printf("使用for循环计算 10*(1-100) 的值:");
    int sum1 := 0;
    for(int i:=0; i<10; i:= i+1){
        for(int j:=1; j<=100; j := j+1){
            sum1 := sum1 + j;
        }
    }
    printf(sum1);


    //while循环
    printf("******************************");
    printf("使用while循环计算值小于50的斐波那契数列:");
    int first := 0;
    int second := first + 1;

    while(second <= 50){
        printf(second);
        int temp := second;
        second := temp + first;
        first := temp;
    }

    printf("test function:");
    int a := add(1, 2);
    int b := square(0, 2);
    printf(square(a, b));

}

int add(int a, int b){
    return a+b;
}

//平方
int square(int a, int b){
    return add(a*a, b*b);
}

``` 

how to use  
1. cd *ProgrammingLabs/compiler/CompilerAPIVersion/*
2. java -jar compiler.jar filepath






## Compiler Display: visualize the execute of compiler!

1. just open the CompilerDisplay/  file with IDEA and run it.
2. open http://127.0.0.1:8080

![](https://ftp.bmp.ovh/imgs/2019/11/7a4f61ee286dbd5e.png)
