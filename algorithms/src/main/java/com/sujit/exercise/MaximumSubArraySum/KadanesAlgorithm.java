package com.sujit.exercise.MaximumSubArraySum;

public class KadanesAlgorithm {

    int[] data;

    public KadanesAlgorithm(int[] data) {
        this.data = data;
    }

    public int getMaxSubArraySum() {

        int maxSoFar = Integer.MIN_VALUE;
        int maxEndingHere = 0;

        for(int i = 0; i < data.length; i++){
            maxEndingHere += data[i];

            if(maxSoFar < maxEndingHere){
                maxSoFar = maxEndingHere;
            }

            if(maxEndingHere < 0){
                maxEndingHere = 0;
            }
        }
        return maxSoFar;
    }
}
