package singletontest;

class Test{
    public static void main(String [] args){
        DemoSingleton ds = DemoSingleton.getInstance();
        System.out.println(ds.hashCode());
    }
}