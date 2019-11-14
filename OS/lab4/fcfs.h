#ifndef SCHDULE_FCHS_H
#define SCHDULE_FCHS_H

#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include "job.h"
/*
    先来先服务(FCFS)调度算法
	比较简单的调度算法, 易于实现
*/

//查找最早到达作业，若全部到达返回-1.
//返回-1代表没有可执行作业, FCFS算法模拟结束
int findjob_fcfs(job jobs[],int count)
{
	int rearlyloc=-1;
	int rearlyjob=-1;
	for(int i=0;i<count;i++)
	{
		if(rearlyloc==-1){
			if(jobs[i].visited==0){
			rearlyloc=i;
			rearlyjob=jobs[i].reach_time;
			}
		}
		else if(rearlyjob>jobs[i].reach_time&&jobs[i].visited==0)
		{
			rearlyjob=jobs[i].reach_time;
			rearlyloc=i;
		}
	}
	return rearlyloc;
}



//FCFS
void FCFS() 
{ 
	int i; 
	int current_time=0;
	int loc;
	int total_waitime=0;
	int total_roundtime=0;
	//获取最近到达的作业
	loc=findjob_fcfs(jobs,quantity);
	//输出作业流
	printf("\n\nFCFS算法作业流\n");
	printf("------------------------------------------------------------------------\n"); 
	printf("\tjobID\treachtime\tstarttime\twaittime\troundtime\n");
	current_time=jobs[loc].reach_time; 
	//每次循环找出最先到达的作业并打印相关信息
	for(i=0;i<quantity;i++)
	{ 
		if(jobs[loc].reach_time>current_time)
		{
			jobs[loc].start_time=jobs[loc].reach_time;
			current_time=jobs[loc].reach_time;
		}
		else
		{
			jobs[loc].start_time=current_time;
		}
		jobs[loc].wait_time=current_time-jobs[loc].reach_time; 
	printf("\t%-8d\t%-8d\t%-8d\t%-8d\t%-8d\n",loc+1,jobs[loc].reach_time,jobs[loc].start_time,jobs[loc].wait_time,
			jobs[loc].wait_time+jobs[loc].need_time);
		jobs[loc].visited=1;
		current_time+=jobs[loc].need_time;
		total_waitime+=jobs[loc].wait_time; 
		total_roundtime=total_roundtime+jobs[loc].wait_time+jobs[loc].need_time;
		//获取剩余作业中最近到达作业
		loc=findjob_fcfs(jobs,quantity);
	} 
	printf("总等待时间:%-8d 总周转时间:%-8d\n",total_waitime,total_roundtime); 
	printf("平均等待时间: %4.2f 平均周转时间: %4.2f\n",(float)total_waitime/(quantity),(float)total_roundtime/(quantity)); 

}



#endif