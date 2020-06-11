package algorithm;
import java.util.*;

class InfinityStone{
    int number,value,weight;
    public InfinityStone(int number,int weight,int value){
        this.number=number;
        this.value=value;
        this.weight=weight;
    }
}

public class KnapsackProblem {
    private static int numOfStone=4;//보석의 갯수.
    private static int  [][]m=new int[numOfStone+1][numOfStone+2];//일단 갯수 중심으로 배열을 만듦.
    private static int height=numOfStone;//numOfStone 이 세로.
    private static int width=numOfStone+1;//numOfStone+1 이 가로.
    private static List<InfinityStone>gauntlet=new LinkedList<>();
    private static int result=0;
    public static void main(String[] args) {
        init();
        result=knapSack();
        System.out.println(result);
        numOfJam(numOfStone,m[height][width],result);
        for(int i=result;i>0;i--) System.out.print(gauntlet.get(i-1).number+"번 보석 ");//인덱스와 보석의 번호가 다를 수도 있으므로 이렇게 함.
    }
    private static void init(){
        for(int i=0;i<numOfStone;i++){
            InfinityStone jam=new InfinityStone(i+1,i+2,i+3);
            gauntlet.add(jam);
        }
        for(int i=0;i<=width;i++) m[0][i]=0;
        for(int i=0;i<=height;i++) m[i][0]=0;
    }
    private static int knapSack(){
        for(int i=1;i<=height;i++){
            InfinityStone jam=gauntlet.get(i-1);
            for(int w=1;w<=width;w++){//내가 무게를 1,2,3,4 이렇게 해서 가능. 각각 제멋대로라면 무게 배열을 만들어야함.
                if(jam.weight>w) m[i][w]=m[i-1][w];
                else m[i][w]=Math.max(m[i-1][w],jam.value+m[i-1][w-jam.weight]);
            }
        }
        return m[height][width];
    }
    private static void numOfJam(int i,int num,int num2){
        if(num==num2) numOfJam(i-1,m[i-1][width],num2);
        else result=i+1;
    }
}
