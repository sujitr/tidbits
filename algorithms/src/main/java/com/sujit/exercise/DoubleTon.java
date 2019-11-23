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

    public static DoubleTon getInstance(){
        if(++callCount%2==0){
            return instances[0];
        }else{
            return instances[1];
        }
    }

    public static void main(String[] args){
        Stream.generate(DoubleTon::getInstance).limit(10).forEach(System.out::println);
    }
}
