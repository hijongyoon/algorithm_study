package realAlgorithm;

import java.util.Scanner;

public class algorithm1_2 {
    private static int N;
    private static int rankCount=0;
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        inputNum(sc);
        int []arr=new int[N];
        int []rankArray=new int[N];
        inputArray(sc,0,arr);
        findRank(0,arr,rankArray);
        for(int e:rankArray) System.out.print(e+" ");
    }
    private static void inputNum(Scanner sc){ N=sc.nextInt(); }
    private static void inputArray(Scanner sc,int count,int []arr){
        if(count<N){
            arr[count]=sc.nextInt();
            inputArray(sc,count+1,arr);
        }
    }
    private static void findRank(int count,int[]arr,int[]rankArr){
        if(count<N){
            rankArr[count]=findArrayRang(0,arr,arr[count]);//여기서 count 값은 무조건 0이어야만 함. 그래야 각 원소마다 배열을 순회 가능.
            rankCount=0;
            findRank(count+1,arr,rankArr);
        }
    }
    private static int findArrayRang(int count,int[]arr,int limit){
        if(count<N){
            if(arr[count]<limit) rankCount++;
            return findArrayRang(count+1,arr,limit);
        }
        else return rankCount+1;
    }
}
