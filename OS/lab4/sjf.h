#ifndef SCHDULE_SJF_H
#define SCHDULE_SJF_H

#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include "job.h"
/*
    短作业优先(SJF)调度算法
*/


//短作业优先作业调度
void SJF()
{
    int i; 
	int current_time=0;
	int loc;
	int total_waitime=0;
	int total_roundtime=0;

    loc = findminjob2(jobs, quantity, current_time);
    if(loc != -1){
        current_time = jobs[loc].reach_time;
    }
    //输出作业流
	printf("\n\nSJF算法作业流\n");
	printf("------------------------------------------------------------------------\n"); 
	printf("\tjobID\treachtime\tstarttime\twaittime\troundtime\n");

    while(loc != -1){
        //该作业存在等待时间
        if(current_time > jobs[loc].reach_time){
            jobs[loc].start_time = current_time;
            
        }else{
            current_time = jobs[loc].reach_time;
            jobs[loc].start_time = jobs[loc].reach_time;
        }
      
        //计算等待时间
        jobs[loc].wait_time = jobs[loc].start_time - jobs[loc].reach_time;
        total_waitime += jobs[loc].wait_time;
        total_roundtime = total_roundtime + jobs[loc].wait_time + jobs[loc].need_time;     

        //进程执行完毕
        jobs[loc].isreached = true;
        jobs[loc].visited = true;
        current_time = jobs[loc].start_time + jobs[loc].need_time;
     
        //输出该进程执行信息
        printf("\t%-8d\t%-8d\t%-8d\t%-8d\t%-8d\n",loc+1,jobs[loc].reach_time,jobs[loc].start_time,jobs[loc].wait_time,
        jobs[loc].wait_time+jobs[loc].need_time);

        //执行下一个进程
        loc = findminjob2(jobs, quantity, current_time);
    }

    printf("总等待时间:%-8d 总周转时间:%-8d\n",total_waitime,total_roundtime); 
	printf("平均等待时间: %4.2f 平均周转时间: %4.2f\n",(float)total_waitime/(quantity),(float)total_roundtime/(quantity)); 

}


#endif  


