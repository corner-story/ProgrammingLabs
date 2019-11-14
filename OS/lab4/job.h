#ifndef SCHDULE_JOB_H
#define SCHDULE_JOB_H


#include <stdio.h>
#include <string.h>
#include <stdlib.h>

#define true 1
#define false 0
#define bool int
#define MAXJOB 20

//最大作业数量
// int MAXJOB=50;

//作业的数据结构
typedef struct node
{
	int number;//作业号        
	int reach_time;//作业抵达时间
	int need_time;//作业的执行时间
	int privilege;//作业优先权
	float excellent;//响应比
	int start_time;//作业开始时间
	int wait_time;//等待时间
	int visited;//作业是否被访问过
	bool isreached;//作业是否抵达
}job;

job jobs[MAXJOB];//作业序列

int quantity;//作业数量

//初始化作业序列
void initial_jobs()
{
	int i;
	for(i=0;i<MAXJOB;i++)
	{
		jobs[i].number=0;
		jobs[i].reach_time=0;
		jobs[i].privilege=0;
		jobs[i].excellent=0;
		jobs[i].start_time=0;
		jobs[i].wait_time=0;
		jobs[i].visited=0;
		jobs[i].isreached=false;
	}
	quantity=0;
}


//重置全部作业信息
void reset_jinfo() 
{ 
	int i; 
	for(i=0;i<MAXJOB;i++)
	{ 
		jobs[i].start_time=0; 
		jobs[i].wait_time=0; 
		jobs[i].visited=0; 
	}
}





//读取作业数据
void readJobdata()
{
	FILE *fp;
	char fname[20];
	int i;
    //输入测试文件文件名
	printf("please input job data file name\n");
	scanf("%s",fname);
	if((fp=fopen(fname,"r"))==NULL)
	{
		printf("error, open file failed, please check filename:\n");
	}
	else
	{
		//依次读取作业信息
		while(!feof(fp))
		{
	if(fscanf(fp,"%d %d %d %d",&jobs[quantity].number,&jobs[quantity].reach_time,&jobs[quantity].need_time,&jobs[quantity].privilege)==4)
			quantity++;
		}
		//打印作业信息
		printf("output the origin job data\n");
		printf("---------------------------------------------------------------------\n");
		printf("\tjobID\treachtime\tneedtime\tprivilege\n");
		for(i=0;i<quantity;i++)
		{
	printf("\t%-8d\t%-8d\t%-8d\t%-8d\n",jobs[i].number,jobs[i].reach_time,jobs[i].need_time,jobs[i].privilege);
		}
	}
}


#endif