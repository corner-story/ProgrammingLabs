#include<stdio.h>
#include<stdlib.h>

#define VM_PAGE 7           /*虚拟页面数*/
#define PM_PAGE 4           /*内存块(物理块)数为4*/
#define TOTAL_INSERT 18     //所有的指令个数

#define bool int
#define true 1
#define false 0

//每条指令信息
typedef struct{
    int  num;      //指令号
    int  vpage;     //该指令所在虚拟地址
    int  offset;    //页内偏移
    int  inflow;
}instr_item;
//指令数组
instr_item instr_array[TOTAL_INSERT];
//指令流数据结构
struct instr_flow{
    instr_item *instr;
    struct instr_flow *next;
};
//指令流头数据结构
struct instr_flow_head{
    int num;
    struct instr_flow *next;
};
struct instr_flow_head iflow_head;

//页表
typedef struct
{
	int vmn;    //虚拟页号
	int pmn;    //物理块
	int exist;  //虚页对应的物理块是否在内存中
	int time;
}vpage_item;
vpage_item page_table[VM_PAGE];

//位图, NULL 或者 与 该物理块对应的虚页
vpage_item* ppage_bitmap[PM_PAGE];

//每条指令对应的虚拟页面
int vpage_arr[TOTAL_INSERT] = { 1,2,3,4,2,6,2,1,2,3,7,6,3,2,1,2,3,6 };


void init_data() //数据初始化
{
	for (int i = 0; i<VM_PAGE; i++)
	{
		page_table[i].vmn = i + 1;  //虚页号
		page_table[i].pmn = -1;    //实页号
		page_table[i].exist = 0;
		page_table[i].time = -1;

	}
	for (int i = 0; i<PM_PAGE; i++) /*最初4个物理块为空*/
	{
		ppage_bitmap[i] = NULL;
	}
}

void FIFO();
void LRU();
void OPT();

int main()
{
	int a;
	
	do
	{
		printf("\n请输入需要选择的页面置换算法：1.FIFO\t2.LRU\t3.OPT\t输入0结束\n");
		scanf("%d", &a);
		switch (a)
		{
		case 1:
			init_data();
			FIFO();
			break;
		case 2:
			init_data();
			LRU();
			break;
		case 3:
			init_data();
			OPT();
			break;
		}
	} while (a != 0);

	return 0;
}


/*FIFO页面置换算法*/
void FIFO()
{
	/*
		time属性代表, 页面被装载到内存的时间, 淘汰时会淘汰time值最小的(最先装载到内存)
	*/
	int k = 0;
	int i;
	int sum = 0;
	int missing_page_count = 0;
	int current_time = 0;
	bool isleft = true;   /*当前物理块中是否有剩余*/
	while (sum < TOTAL_INSERT)
	{
		if (page_table[vpage_arr[sum] - 1].exist == 0)
		{
			missing_page_count++;
			if (k < 4)
			{
				if (ppage_bitmap[k] == NULL) /*找到一个空闲物理块*/
				{
					ppage_bitmap[k] = &page_table[vpage_arr[sum] - 1];
					ppage_bitmap[k]->exist = 1;
					ppage_bitmap[k]->pmn = k;
					ppage_bitmap[k]->time = current_time;
					k++;
				}
			}
			else
			{
                //在这种情况下, 所有的物理块都被占用
                //需要选择一个淘汰页面
				int temp = ppage_bitmap[0]->time;	/*记录物理块中作业最早到达时间*/
				int j = 0;                          /*记录应当被替换的物理块号*/
				for (i = 0; i < PM_PAGE; i++)
				{
					if (ppage_bitmap[i]->time < temp)
					{
						temp = ppage_bitmap[i]->time;
						j = i;
					}
				}
                //更改 与淘汰页面对应的虚页 的信息
				ppage_bitmap[j]->exist = 0;

                //调入新的物理页, 并和新的虚拟页对应
                //do something that get the new pm to memory 
				ppage_bitmap[j] = &page_table[vpage_arr[sum] - 1];      /*更新页表项*/
				ppage_bitmap[j]->exist = 1;
				ppage_bitmap[j]->pmn = j;
				ppage_bitmap[j]->time = current_time;
			}
		}
		current_time++;
		sum++;
	}
	printf("FIFO算法缺页次数为:%d\t缺页率为:%f\t置换次数为:%d\t置换率为:%f", missing_page_count, missing_page_count / (float)TOTAL_INSERT, missing_page_count - 4, (missing_page_count - 4) / (float)TOTAL_INSERT);
}


//最近最少使用页面置换算法
void LRU(){
	/*
		time属性值代表, 程序最后一次访问该物理页的时间, 淘汰时淘汰time值最小的
	*/
	int current_time = 0;
	int pmindex = 0;  //当前空闲物理块index
	int sum = 0;	//指令
	int missing_page_count = 0;	//缺页数
	
	while(sum < TOTAL_INSERT){

		//查看是否缺页
		//缺页时装载
		//不缺页时更改该物理块的最后访问时间
		if(page_table[vpage_arr[sum] - 1].exist == 0){
			missing_page_count++;

			//查看是否有空闲物理块
			if(pmindex < PM_PAGE){
				//更新位图
				ppage_bitmap[pmindex] = &page_table[vpage_arr[sum] - 1];
				ppage_bitmap[pmindex]->exist = 1;
				ppage_bitmap[pmindex]->time = current_time;
				ppage_bitmap[pmindex]->pmn = pmindex;
				ppage_bitmap[pmindex]->vmn = vpage_arr[sum];

				pmindex++;
			}else{
				//没有空闲物理块, 找到最近最少访问的物理块替换掉
				int replace_index = 0;
				int last_time = ppage_bitmap[replace_index]->time;  //物理块的最后访问时间

				for(int i=0; i<PM_PAGE; i++){
					if(ppage_bitmap[i]->time < last_time){
						replace_index = i;
						last_time = ppage_bitmap[i]->time;
					}
				}
				//取消要淘汰的物理块和虚拟页面的对应关系
				ppage_bitmap[replace_index]->exist = 0;

				//装载新的物理块并和该虚拟页对应
				ppage_bitmap[replace_index] = &page_table[vpage_arr[sum] - 1];
				ppage_bitmap[replace_index]->exist = 1;
				ppage_bitmap[replace_index]->time = current_time;
				ppage_bitmap[replace_index]->pmn = replace_index;
				ppage_bitmap[replace_index]->vmn = vpage_arr[sum];
			}

		}else{
			//物理块存在, 直接更新最后访问时间
			page_table[vpage_arr[sum]-1].time = current_time;
		}

		sum++;
		current_time++;
	}
	printf("LRU算法缺页次数为:%d\t缺页率为:%f\t置换次数为:%d\t置换率为:%f", missing_page_count, missing_page_count / (float)TOTAL_INSERT, missing_page_count - 4, (missing_page_count - 4) / (float)TOTAL_INSERT);


}


//最优页面置换算法
void OPT(){
	/*
		time属性值
	*/
	int current_time = 0;
	int pmindex = 0;  //当前空闲物理块index
	int sum = 0;	//指令
	int missing_page_count = 0;	//缺页数
	
	while(sum < TOTAL_INSERT){

		//查看是否缺页
		//缺页时装载
		//不缺页时更改该物理块的最后访问时间
		if(page_table[vpage_arr[sum] - 1].exist == 0){
			missing_page_count++;

			//查看是否有空闲物理块
			if(pmindex < PM_PAGE){
				//更新位图
				ppage_bitmap[pmindex] = &page_table[vpage_arr[sum] - 1];
				ppage_bitmap[pmindex]->exist = 1;
				ppage_bitmap[pmindex]->time = 0;
				ppage_bitmap[pmindex]->pmn = pmindex;
				ppage_bitmap[pmindex]->vmn = vpage_arr[sum];

				pmindex++;
			}else{
				//没有空闲物理块
				
				//查找后续指令
				int ins = sum + 1;
				int found = 0;
				while(ins < TOTAL_INSERT && found < (PM_PAGE-1)){
					if(page_table[vpage_arr[ins] - 1].exist == 0){
						ins++;
						continue;
					}
					page_table[vpage_arr[ins] - 1].time = ins - sum;
					found++;
					ins++;
				}
				int replace_index = 0;
				for(int i=0; i<PM_PAGE; i++){
					if(ppage_bitmap[i]->time == 0){
						replace_index = i;
						break;
					}
				}

				//取消要淘汰的物理块和虚拟页面的对应关系
				ppage_bitmap[replace_index]->exist = 0;

				//装载新的物理块并和该虚拟页对应
				ppage_bitmap[replace_index] = &page_table[vpage_arr[sum] - 1];
				ppage_bitmap[replace_index]->exist = 1;
				ppage_bitmap[replace_index]->time = current_time;
				ppage_bitmap[replace_index]->pmn = replace_index;
				ppage_bitmap[replace_index]->vmn = vpage_arr[sum];
			}

		}else{
			page_table[vpage_arr[sum]-1].time = 0;
		}

		sum++;
		current_time++;
	}
	printf("LRU算法缺页次数为:%d\t缺页率为:%f\t置换次数为:%d\t置换率为:%f", missing_page_count, missing_page_count / (float)TOTAL_INSERT, missing_page_count - 4, (missing_page_count - 4) / (float)TOTAL_INSERT);


}

