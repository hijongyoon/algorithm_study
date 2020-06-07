package algorithm;

public class TheShortestWayUsingDP {
    public static int max=Integer.MAX_VALUE;
    public static int [][]m={{max,max,max,max,max},
                             {max,6,7,12,5},
                             {max,5,3,11,18},
                             {max,7,17,3,3},
                             {max,8,10,12,9}};
    public static int [][]L=new int[5][5];
    public static String[][]P={{"","","","",""},
                               {"","","","",""},
                               {"","","","",""},
                               {"","","","",""},
                               {"","","","",""}};
    public static void main(String[] args) {
        makeL();
        System.out.println(mat());
        printPath(); //역순.
        printPath(4,4); //정순.
    }
    private static void makeL(){
        for(int i=0;i<m.length;i++){
            System.arraycopy(m[i], 0, L[i], 0, m.length);
        }
    }
    private static int mat(){
        for(int i=1;i<m.length;i++){
            for(int j=1;j<m.length;j++){
                if(i==1&&j==1) {
                    L[i][j]=m[1][1];
                    P[i][j]="-";
                }
                else {
                    if(L[i-1][j]<L[i][j-1]){
                        L[i][j]=m[i][j]+L[i-1][j];
                        P[i][j]="↑";
                    }
                    else{
                        L[i][j]=m[i][j]+L[i][j-1];
                        P[i][j]="←";
                    }
                }
            }
        }
        return L[m.length-1][m.length-1];
    }
    private static void printPath(){
        int i=m.length-1,j=m.length-1;
        while(!P[i][j].equals("-")){
            System.out.println(i+" "+j);
            if(P[i][j].equals("←")) j=j-1;
            else i=i-1;
        }
        System.out.println(i+" "+j);
    }
    private static void printPath(int i,int j){
        if(P[i][j].equals("-")) {
            System.out.println(i+" "+j);
            return;
        }
        if(P[i][j].equals("←")) printPath(i,j-1);
        else printPath(i-1,j);
        System.out.println(i+" "+j);
    }
}
