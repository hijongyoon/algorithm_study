package algorithm;
import java.util.*;

class Stick{
    int length,price;
    public Stick(int length,int price){
        this.length=length;
        this.price=price;
    }
}

public class StickProblem {//배낭 문제와 다르게 똑같은 길이로 여러개를 짜를 수 있음.
    public static Scanner sc=new Scanner(System.in);
    public static int numOfStick;
    public static int [][] m;
    public static List<Stick>list=new LinkedList<>();
    public static void main(String[] args) {
        init();
        System.out.println(whatStick());
        for(int i=0;i<numOfStick+1;i++){
            for(int j=0;j<numOfStick+1;j++) System.out.print(m[i][j]+" ");
            System.out.println();
        }
    }
    private static void init(){
        numOfStick=sc.nextInt();
        m=new int[numOfStick+1][numOfStick+1];
        for(int i=0;i<numOfStick;i++){
            Stick s=new Stick(i+1,sc.nextInt());
            list.add(s);
        }
        for(int i=0;i<numOfStick+1;i++) m[i][0]=0;
        for(int i=0;i<numOfStick+1;i++) m[0][i]=0;
    }
    private static int whatStick(){
        for(int i=1;i<numOfStick+1;i++){
            Stick s=list.get(i-1);
            for(int l=1;l<numOfStick+1;l++){
                if(s.length>l) m[i][l]=m[i-1][l];
                else m[i][l]=mostValue(m[i-1][l],l,i,s.price);
            }
        }
        return m[numOfStick][numOfStick];
    }
    private static int mostValue(int num1,int num2,int length,int price){
        //num1은 m[i-1][l]에 해당하는 수, num2는 l, length 는 i, price 는 현재 행 번호를 가진 막대기의 가격.
        int max=num1;
        for(int i=0;i<num2/length;i++){//이 비교는 현재 막대기를 포함시켰을 때와 안포함 시켰을 때를 비교하는 것을 의미.
            //num2/length 는 현재 막대기가 몇개 들어가는지 헤아리기 위함.
            int compare=price*(i+1)+m[length][num2-length*(i+1)];//현재 막대기가 1개부터 num2/length 개 까지 들어갔을 때를 비교.
            if(max<compare) max=compare;                         // 현재 막대기가 1개 있을 때와 현재 막대기가 1개 없을 경우 중 가장 큰 것을 더한 것을 비교.
        }
        return max;
    }
}