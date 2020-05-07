package realAlgorithm;
import java.util.*;

public class algorithm1_1 {
    private static int N;
    private static int limit;
    private static int rankCount=0;
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        inputNum(sc);
        int []arr=new int[N];
        inputArray(0,arr,sc);
        inputLimit(sc);
        findRank(0,arr);
        System.out.println(rankCount+1);
    }
    private static void inputNum(Scanner sc){ N=sc.nextInt();}
    private static void inputArray(int count,int []arr,Scanner sc){
        if(count<N){
            arr[count]=sc.nextInt();
            inputArray(count+1,arr,sc);
        }
    }
    private static void inputLimit(Scanner sc){ limit=sc.nextInt();}
    private static int findRank(int count,int[]arr){
        if(count<N){
            if(arr[count]<limit) rankCount++;
            return findRank(count+1,arr);
        }
        else return -1;
    }
}
