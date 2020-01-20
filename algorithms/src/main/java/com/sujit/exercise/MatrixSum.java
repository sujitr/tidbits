package com.sujit.exercise;


import java.util.concurrent.*;

/**
 * PROBLEM - Write a program to compute the sum of all digits
 * in a 2D matrix with threads running in parallel.
 *
 * the basic idea is to break a given 2D matrix into sub matrices,
 * then feeding them to separate threads for summation.
 *
 * how you feed them to threads that's one case to ponder.
 * The breaking of the matrix into separate individual ones and
 * corresponding feeding to threads should be made programmable.
 *
 * if we could come up with a recursive approach for dividing a matrix,
 * then fork-join could be used here.
 */
public class MatrixSum {
    public static void main(String[] args){
        int[][] data = {{12,45,57,24,87,55},{92,16,37,81,95,47},{82,8,44,3,66,73},{41,67,71,84,58,47}};
        displayMatrix(data);

        int length = data[0].length;
        int width = data.length;

        int w_half = width/2;
        int l_half = length/2;

        System.out.println("Mid point in the given matrix is :"+l_half+","+w_half);

        System.out.println("----------------------------");
        // assuming an even matrix at all times
        // first part
        for(int i = 0; i < w_half; i++){
            for(int j = 0; j < l_half; j++){
                System.out.print(data[i][j]+"\t");
            }
            System.out.print("\n");
        }
        System.out.println("----------------------------");
        // second part
        for(int i = w_half; i < data.length; i++){
            for(int j = 0; j < l_half; j++){
                System.out.print(data[i][j]+"\t");
            }
            System.out.print("\n");
        }
        System.out.println("----------------------------");
        // third part
        for(int i = w_half; i < data.length; i++){
            for(int j = l_half; j < data[0].length; j++){
                System.out.print(data[i][j]+"\t");
            }
            System.out.print("\n");
        }
        System.out.println("----------------------------");
        // third part
        for(int i =0 ; i < w_half; i++){
            for(int j = l_half; j < data[0].length; j++){
                System.out.print(data[i][j]+"\t");
            }
            System.out.print("\n");
        }
        System.out.println("----------------------------");
        System.out.println("Normal Sum Is : "+ getAllNormalSum(data));
        System.out.println("----------------------------");
        System.out.println("Threaded Sum is :"+getThreadedSum(data, 4));

    }

    private static int getThreadedSum(int[][] data, int numberOfThreads) {
        int finalResult = 0;

        int length = data[0].length;
        int width = data.length;

        int w_half = width/2;
        int l_half = length/2;
        ExecutorService service = null;
        Future<Integer>[] results = new Future[numberOfThreads];
        try{
            service = Executors.newFixedThreadPool(numberOfThreads);
            /*
            There has to be a mathematical way to do this without hard-coding
             */
            results[0] = service.submit(new SumUp(data,0,0,w_half, l_half));
            results[1] = service.submit(new SumUp(data,w_half,0,width, l_half));
            results[2] =  service.submit(new SumUp(data,w_half,l_half,width, length));
            results[3] =  service.submit(new SumUp(data,0,l_half,w_half, length));

            for (int i = 0; i < numberOfThreads; i++){
                finalResult += results[i].get(10, TimeUnit.SECONDS);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        } finally {
            if (service!=null) {
                service.shutdown();
            }
        }
        return finalResult;
    }

    public static void displayMatrix(int[][] matrix){
        for ( int i = 0 ; i < matrix.length ; i++ )
        {
            for ( int j = 0 ; j < matrix[0].length ; j++ )
                System.out.print(matrix[i][j]+"\t");
            System.out.print("\n");
        }
    }

    public static int getAllNormalSum(int[][] matrix){
        int result = 0;
        for(int i = 0; i < matrix.length;i++){
            for(int j = 0; j < matrix[0].length; j++){
                result += matrix[i][j];
            }
        }
        return result;
    }
}

class SumUp implements Callable<Integer> {

    private int[][] data;
    private int xStart;
    private int yStart;
    private int xEnd;
    private int yEnd;

    public SumUp(int[][] data, int xStart, int yStart, int xEnd, int yEnd) {
        this.data = data;
        this.xStart = xStart;
        this.yStart = yStart;
        this.xEnd = xEnd;
        this.yEnd = yEnd;
    }

    @Override
    public Integer call() throws Exception {
        int result = 0;
        for(int i = xStart; i < xEnd;i++){
            for(int j = yStart; j < yEnd; j++){
                result += data[i][j];
            }
        }
        return result;
    }
}

