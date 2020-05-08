package realAlgorithm;

import java.util.Scanner;

public class algorithm2_2 {
    private static int N;
    private static int [][]maze;
    private static final int PATHWAY_COLOR=0;
    private static final int PATH_COLOR=3;
    private static final int NORTH=4;
    private static final int EAST=5;
    private static final int SOUTH=6;
    private static final int WEST=7;
    private static int offset=EAST;
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        inputN(sc);
        inputMaze(sc);
        findMazePath(0,0);
        if(maze[N-1][N-1]==PATH_COLOR) System.out.println("Yes");
        else System.out.println("No");
        printMaze();
    }
    private static void inputN(Scanner sc){N=sc.nextInt();}
    private static void inputMaze(Scanner sc){
        maze=new int[N][N];
        for(int i=0;i<N;i++)
            for(int j=0;j<N;j++) maze[i][j]=sc.nextInt();
    }
    private static void printMaze(){
        for(int i=0;i<N;i++){
            for(int j=0;j<N;j++) System.out.print(maze[i][j]+" ");
            System.out.println();
        }
    }
    private static boolean findMazePath(int x,int y){//x1,y1은 전  주소, x2,y2는 현주소.
        if(x<0||y<0||x>=N||y>=N||maze[x][y]!=PATHWAY_COLOR) return false;
        else if(x==N-1&&y==N-1) {
            maze[x][y]=PATH_COLOR;
            return true;
        }
        else {
            maze[x][y] = PATH_COLOR;
            if(offset==EAST&&!findMazePath(x,y+1)){
                offset=NORTH; findMazePath(x-1,y);
                offset=SOUTH; findMazePath(x+1,y);
            }
            if(offset==SOUTH&&!findMazePath(x+1,y)){
                offset=EAST; findMazePath(x,y+1);
                offset=WEST; findMazePath(x,y-1);
            }
            if(offset==NORTH&&!findMazePath(x-1,y)){
                offset=EAST; findMazePath(x,y+1);
                offset=WEST; findMazePath(x,y-1);
            }
        }
        return true;
    }
}
