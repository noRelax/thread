package cn.itcast.norelax;

import java.util.ArrayList;

class InsertData {
    private ArrayList<Integer> arrayList = new ArrayList<Integer>();
     
    public synchronized void insert(Thread thread){
        for(int i=0;i<5;i++){
            System.out.println(thread.getName()+"在插入数据"+i);
            arrayList.add(i);
        }
    }
}