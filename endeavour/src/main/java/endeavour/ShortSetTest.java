package endeavour;

import java.util.Set;
import java.util.HashSet;

public class ShortSetTest {
    public static void main(String[] args){
        Set shortSet = new HashSet();
        for(short i = 0; i < 100; i++){
            shortSet.add(i);
            shortSet.remove(i-1);
        }
        System.out.println(shortSet);
    }
}
