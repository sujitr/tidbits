package com.sujit.exercise.RemoveKDigits;

import java.util.LinkedList;

public class Smart {
    private String num;

    public Smart(String num) {
        this.num = num;
    }

    /**
     * Method to remove K digits
     * @param k number of digits to be removed
     * @return Updated string representing new number after removing K digits
     */
    public String removeKdigits(int k) {
        int start = 0;
        int index_zero;
        while(true) {
            index_zero=num.indexOf('0', start);
            if(index_zero==-1||k<index_zero-start)
                break;
            else {
                k-=index_zero-start;
            }
            start = index_zero;
            while (start<num.length()&&num.charAt(start)=='0') {
                start++;
            }
        }
        if(start==num.length())
            return "0";
        if(k==0) {
            return num.substring(start);
        }
        if(index_zero==-1)
            index_zero = num.length();
        LinkedList<Character> chrs = new LinkedList<>();
        chrs.add(num.charAt(start));
        int deletedC = 0;
        int i = start+1;
        for (; deletedC<k&&i<index_zero; i++) {
            Character ref = chrs.peekLast();
            char c = num.charAt(i);
            while(!chrs.isEmpty()&&ref>c&&deletedC<k) {
                deletedC++;
                chrs.pollLast();
                ref = chrs.peekLast();
            }
            chrs.add(c);
        }

        while (deletedC<k) {
            deletedC++;
            chrs.pollLast();
        }

        StringBuilder stringBuilder = new StringBuilder();
        while(!chrs.isEmpty()) {
            stringBuilder.append(chrs.pollFirst());
        }
        stringBuilder.append(num.substring(i));
        if(stringBuilder.length()==0)
            stringBuilder.append(0);
        return stringBuilder.toString();
    }
}
