package com.sujit.exercise;

import java.util.stream.Stream;

/**
 * Double version of Singleton class. There has to be
 * two instances in total. With all even calls to the
 * class, one instance will be returned while for all
 * odd numbered calls, other instance need to be returned.
 */
public class DoubleTon {

    private DoubleTon(){}

    private static DoubleTon[] instances = {
            new DoubleTon(),
            new DoubleTon()
    };

    private static int callCount;

    /**
     * This method needs to be synchronized as the callCount
     * needs to be protected from multiple access to avoid
     * inconsistent values.
     */
    public static synchronized DoubleTon getInstance(){
        return instances[++callCount % 2];
    }

    public static void main(String[] args){
        DoubleTon d1 = DoubleTon.getInstance();
        DoubleTon d2 = DoubleTon.getInstance();
        for(int i = 1; i <= 10; i++){
            if(i%2 != 0){
                System.out.println(d1 == DoubleTon.getInstance());
            }else{
                System.out.println(d2 == DoubleTon.getInstance());
            }
        }
    }
}
