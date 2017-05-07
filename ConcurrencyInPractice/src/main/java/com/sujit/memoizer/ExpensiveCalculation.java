public class ExpensiveCalculation implements Computable<String, BigInteger> {
    public Bignteger compute(String arg){
        // after lot of computation
        return new BigInteger(arg);
    }
}