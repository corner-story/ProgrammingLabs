#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include "job.h"
#include "fcfs.h"   //FCFS
#include "sjf.h"    //SJF
#include "hrrf.h"   //HRRF
#include "hpf.h"    //HPF

int main(int argc, char* argv[]){

    initial_jobs(); 
	readJobdata();

    //先来先服务调度 
	FCFS();
    reset_jinfo();

    //短作业优先调度
    SJF();
    reset_jinfo();

    //高响应比调度
    HRRF();
    reset_jinfo();

    //优先权高者先调度算法
    HPF();
    reset_jinfo();

    return 0;
}