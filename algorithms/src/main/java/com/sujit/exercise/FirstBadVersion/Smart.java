package com.sujit.exercise.FirstBadVersion;

public class Smart {

    /*
    The isBadVersion API is defined in the interface VersionControl.
            boolean isBadVersion(int version);
    */
    VersionControl versionControl;

    public Smart(VersionControl v) {
        this.versionControl = v;
    }

    public int firstBadVersion(int n) {
        // if there are no bad versions, then return -1
        if(!versionControl.isBadVersion(n)){
            return -1;
        }
        // doing basic binary search
        int min = 1;
        int max = n;
        while(min < max){
            /*
            Pay attention to calculate the mid.
            If you use simple (max+min)/2, you will
            get Time Limit Exceeded error, as
            Integer data type limits may cause issues.
            */
            int mid = min+(max-min)/2;
            if(versionControl.isBadVersion(mid)){
                max = mid;
            }else{
                min = mid+1;
            }
        }
        return min; // the first bad version
    }
}
