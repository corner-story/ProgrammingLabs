#ifndef SCHDULE_HRRF_H
#define SCHDULE_HRRF_H

/*
    高响应比(HRRF)调度算法
	R = (wait_time + need_time) / need_time
	R越大越容易获得执行的机会
*/

//查找最早到达作业，若全部到达返回-1.
//返回-1代表没有可执行作业, HRRF算法模拟结束
int findjob_hrrf(job jobs[],int count, int current_time)
{
	int loc=-1;
	int job=-1;
	int r = -1;	//响应比

	//先在当前的就绪进程队列中寻找最高响应比的作业
	if(current_time > 0){
		for(int i=0; i<count;i++){
			if(loc == -1){
				if(jobs[i].visited == 0 && jobs[i].reach_time <= current_time){
					loc = i;
					r = ((current_time-jobs[i].reach_time) + jobs[i].need_time) / jobs[i].need_time;
				}
			}else if(jobs[i].visited ==0 && jobs[i].reach_time <= current_time){
				//计算jobs[i]的响应比
				int temp = ((current_time-jobs[i].reach_time) + jobs[i].need_time) / jobs[i].need_time;
				if(temp > r){
					loc = i;
					r = temp;
				}
			}
		}
	}

	if(loc != -1){
		return loc;
	}

	//在未来即将到达的进程中寻找最高优先级的作业
	int reach_time = -1;
	for(int i=0; i<count; i++){
		if(loc == -1){
			if(jobs[i].visited == 0){
				loc = i;
				job = jobs[i].privilege;
				reach_time = jobs[i].reach_time;
			}
		}else if(jobs[i].visited==0 && jobs[i].reach_time <= reach_time){
			if((jobs[i].reach_time < reach_time) || (jobs[i].privilege > job)){
				loc = i;
				job = jobs[i].privilege;
				reach_time = jobs[i].reach_time;
			}
		}
	}

	return loc;
}



//HRRF
void HRRF() 
{ 
 
	int current_time=0;
	int loc;
	int total_waitime=0;
	int total_roundtime=0;

	
    loc = findjob_hrrf(jobs, quantity, current_time);
    if(loc != -1){
        current_time = jobs[loc].reach_time;
    }
    //输出作业流
	printf("\n\nHRRF算法作业流\n");
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
        loc = findjob_hrrf(jobs, quantity, current_time);
    }

    printf("总等待时间:%-8d 总周转时间:%-8d\n",total_waitime,total_roundtime); 
	printf("平均等待时间: %4.2f 平均周转时间: %4.2f\n",(float)total_waitime/(quantity),(float)total_roundtime/(quantity)); 

}



#endif