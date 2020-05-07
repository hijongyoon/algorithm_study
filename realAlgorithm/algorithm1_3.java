package realAlgorithm;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;

public class algorithm1_3 {
    private static int N;
    private static int sum;
    private static HashSet<String> set;
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        set=new HashSet<>();
        inputNum(sc);
        int []arr=new int[N];
        inputArray(sc,0,arr);
        inputSum(sc);
        findSum_1(0,arr);
        System.out.println(set.size());
        for(String s: set)
            System.out.println(s+" ");
    }
    private static void inputNum(Scanner sc){ N=sc.nextInt();}
    private static void inputArray(Scanner sc,int count,int []arr){
        if(count<N){
            arr[count]=sc.nextInt();
            inputArray(sc,count+1,arr);
        }
    }
    private static void inputSum(Scanner sc){sum=sc.nextInt();}
    private static void findSum_1(int count,int[]arr){
        if(count<N){
            findSum_2(count,0,arr);
            findSum_1(count+1,arr);
        }
    }
    private static void findSum_2(int count1,int count2,int[]arr){
        if(count1==count2) findSum_2(count1,count2+1,arr);
        else if(count2<N){
            findSum_3(count1,count2,0,arr);
            findSum_2(count1,count2+1,arr);
        }
    }
    private static void findSum_3(int count1,int count2,int count3,int[]arr){
        if(count1==count3 || count2==count3) findSum_3(count1,count2,count3+1,arr);
        else if(count3<N){
            int num=arr[count1]+arr[count2]+arr[count3];
            if(num==sum)  checkSum(arr[count1],arr[count2],arr[count3]);
            findSum_3(count1,count2,count3+1,arr);
        }
    }
    private static void checkSum(int num1,int num2,int num3){
        Integer []array={num1,num2,num3};
        Arrays.sort(array);
        String str=array[0]+" "+array[1]+" "+array[2];
        set.add(str);
    }
}
