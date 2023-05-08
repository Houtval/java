package com.example;

import java.util.Scanner;
class ProcessData {
    //进程号
    public String id;
    // 到达时间
    public float arriveTime;
    // 服务时间
    public float serviceTime;
    // 完成时间
    public float finishTime;
    // 周转时间
    public float turnTime;
    // 带权周转时间
    public double powerTime;

    // 作业的构造方法中传来的初值为到达时间和服务时间
    public ProcessData(String id,float arriveTime, float serviceTime) {
        this.id=id;
        this.arriveTime = arriveTime;
        this.serviceTime = serviceTime;
    }
}
public class App {
    //输入函数
    public static Scanner input = new Scanner(System.in);
    // 平均周转时间
    public static double avgTotalTime;
    // 平均带权周转时间
    public static double avgPowerTime;

    public static void main(String[] args) {
       // 输入数据
            System.out.print("输入进程个数:");
            int n=input.nextInt();
            ProcessData[] processData = new ProcessData[n];
            for (int i=0;i<processData.length;i++)
            {
                System.out.printf("第%d个进程\n",i+1);
                System.out.printf("请输入进程号:");
                String id=input.next();
                System.out.print("输入到达时间:");
                
                    float arriveTime=input.nextFloat();
                   
                    System.out.print("输入服务时间:");
                   
                        float serviceTime=input.nextFloat();
                        processData[i]=new ProcessData(id,arriveTime,serviceTime);
                    
            }
            input.close();
            // 调用先来先服务算法
            FCFS(processData);
        
    }

    // 先来先服务算法实现
    private static void FCFS(ProcessData[] processData) {
        float preFinished = 0; // 前一个作业的完成时间即为下一个作业的开始时间
        avgTotalTime = 0;    // 平均周转时间
        avgPowerTime = 0;  // 平均带权周转时间

        // 初始化完成时间、周转时间、带权周转时间的初值为0
        for (ProcessData processDatum : processData) {
            processDatum.finishTime = 0; // 设置初始完成时间为0
            processDatum.turnTime = 0; // 设置初始周转时间为0
            processDatum.powerTime = 0; // 设置初始带权周转时间为0
        }

        // 如果第一个作业的到达时间不等于前一个作业的完成时间，就将前一个作业的完成时间定义为当前作业的到达时间
        if (processData[0].arriveTime != preFinished) {
            preFinished = processData[0].arriveTime;
        }

        for (ProcessData processDatum : processData) {
            // 作业的完成时间为上一个作业的完成时间加当前作业的服务时间
            processDatum.finishTime = preFinished + processDatum.serviceTime;
            preFinished = processDatum.finishTime;
            // 周转时间 = 完成时间 - 到达时间
            processDatum.turnTime = processDatum.finishTime - processDatum.arriveTime;
            // 带权周转时间 = 作业的周转时间 / 系统提供服务的时间
            processDatum.powerTime = (double) processDatum.turnTime / (double) processDatum.serviceTime;
        }

        System.out.println("先来先服务（FCFS）算法：");

        // 打印进程的信息
        Display(processData);
    }



    private static void Display(ProcessData[] processData) {
        System.out.println("进程号\t"+"到达时间\t" + "服务时间\t" + "完成时间\t" + "周转时间\t" + "带权周转时间\t");
        for (ProcessData processDatum : processData) {
            System.out.printf("%-7s %-15.2f %-15.2f %-15.2f %-15.2f %-15.2f\n",processDatum.id,processDatum.arriveTime,processDatum.serviceTime,processDatum.finishTime,processDatum.turnTime,processDatum.powerTime);
            avgTotalTime += processDatum.turnTime; // 求总周转时间，此时avgTotalTime中存储的值为总周转时间
            avgPowerTime += processDatum.powerTime; // 求总带权周转时间，此时avgPowerTime中存储的值为总带权周转时间
        }

        avgTotalTime = avgTotalTime / processData.length; // 平均周转时间
        avgPowerTime = avgPowerTime / processData.length; // 平均带权周转时间

        System.out.println("平均周转时间：" + avgTotalTime);
        System.out.println("平均带权周转时间：" + avgPowerTime);
    }
}

