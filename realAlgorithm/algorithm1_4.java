package realAlgorithm;
import java.util.*;

public class algorithm1_4 {
    private static int N;
    private static int limit;
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        inputNum(sc);
        int[]arr=new int[N];
        inputArray(sc,0,arr);
        inputLimit(sc);
        System.out.println(nearest(0,N-1,arr));
    }
    private static void inputNum(Scanner sc){ N=sc.nextInt();}
    private static void inputArray(Scanner sc,int count,int []arr){
        if(count<N){
            arr[count]=sc.nextInt();
            inputArray(sc,count+1,arr);
        }
    }
    private static void inputLimit(Scanner sc){limit=sc.nextInt();}
    private static int nearest(int start,int end,int[]arr){
        if(start>end) return -1;
        int middle=(start+end)/2;
        if(arr[middle]==limit) return arr[middle];
        else if(arr[middle]<=limit&&middle+1==N) return arr[middle];
        else if(arr[middle]<=limit&&middle+1<=N-1&&arr[middle+1]>limit) return arr[middle];
        else if(middle==0&&arr[middle]>=limit) return arr[middle];
        else if(middle==N-1&&arr[middle]<=limit) return arr[middle];
        else if(arr[middle]>limit) return nearest(start,middle-1,arr);
        else return nearest(middle+1,end,arr);
    }
}
