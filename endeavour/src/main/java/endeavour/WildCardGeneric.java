package endeavour;

import java.util.List;

public class WildCardGeneric {
    private static void add(List<? extends Number> list){
        /* modification of an unknown type collection is not possible, will throw exception
        list.add(4);
        list.add(8);
        System.out.println(list.get(0));
        */
    }
}