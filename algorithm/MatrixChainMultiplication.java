package algorithm;

public class MatrixChainMultiplication {
    public static int []p={5,2,3,4,6,7,8};
    public static int[][]m={{-1,-1,-1,-1,-1,-1},
                            {-1,-1,-1,-1,-1,-1},
                            {-1,-1,-1,-1,-1,-1},
                            {-1,-1,-1,-1,-1,-1},
                            {-1,-1,-1,-1,-1,-1},
                            {-1,-1,-1,-1,-1,-1}};
    public static void main(String[] args) {
        System.out.println(matrixChain(6));
    }
    private static int matrixChain(int n){
        for(int i=0;i<n;i++) m[i][i]=0;
        for(int r=0;r<n-1;r++){//대각선의 총 갯수는 n-1개.
            for(int i=0;i<n-r-1;i++){//각 대각선이 가지는 값의 갯수.
                int j=i+r+1;
                m[i][j]=m[i+1][j]+p[i]*p[i+1]*p[j+1];
                for(int k=i+1;k<=j-1;k++){
                    if(m[i][j]>m[i][k]+m[k+1][j]+p[i]*p[k+1]*p[j+1])
                        m[i][j] = m[i][k] + m[k + 1][j] + p[i] * p[k+1] * p[j + 1];
                }
            }
        }
        return m[0][n-1];
    }
}
