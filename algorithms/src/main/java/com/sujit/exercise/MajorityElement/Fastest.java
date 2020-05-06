package com.sujit.exercise.MajorityElement;

public class Fastest {
    private int[] data;

    public Fastest(int[] data) {
        this.data = data;
    }

    public int getMajorityElement(){
        if(data==null || data.length == 0){
            return 0;
        }
        return fetchMajor(0);
    }

    private int fetchMajor(int start){
        int count = 1;
        int num = data[start];
        for(int i = start+1; i < data.length; i++){
            if(num == data[i]){
                count++;
            }else{
                count--;
            }
            if(count==0) return fetchMajor(i+1);
        }
        return num;
    }
}
