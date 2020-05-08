package realAlgorithm;

import java.util.Scanner;

public class algorithm2_1 {
    private static int N;
    private static int K;
    private static int [][]maze;
    private static final int PATHWAY_COLOR=0;
    private static final int PATH_COLOR=3;
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        inputN(sc);
        inputMazeAndK(sc);
        System.out.println(findMazePath(0,0,0));
    }
    private static void inputN(Scanner sc){ N=sc.nextInt();}
    private static void inputMazeAndK(Scanner sc){
        maze=new int[N][N];
        for(int i=0;i<N;i++)
            for(int j=0;j<N;j++) maze[i][j]=sc.nextInt();
        K=sc.nextInt();
    }
    private static int findMazePath(int x,int y,int len){
        if(len>K||x<0||y<0||x>=N||y>=N||maze[x][y]!=PATHWAY_COLOR) return 0;
        else if((x==N-1&&y==N-1)) return 1;
        else{
            maze[x][y]=PATH_COLOR;
            int result=findMazePath(x-1,y,len+1)+findMazePath(x,y+1,len+1)+findMazePath(x+1,y,len+1)+findMazePath(x,y-1,len+1);
            maze[x][y]=PATHWAY_COLOR;
            return result;
        }
    }
}
