#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include "job.h"
#include "fcfs.h"   //FCFS
#include "sjf.h"    //SJF

int main(int argc, char* argv[]){

    initial_jobs(); 
	readJobdata();

    //先来先服务调度 
	FCFS();
    reset_jinfo();

    //短作业优先调度
    SJF();
    reset_jinfo();
    return 0;
}