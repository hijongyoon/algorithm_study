package algorithm;

public class FibonacciUsingDP {
    public static int []f={-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
    public static void main(String[] args) {
        System.out.println(fib(9));
    }
    private static int fib(int n){
        f[1]=f[2]=1;
        for(int i=3;i<=n;i++) f[i]=f[i-2]+f[i-1];
        return f[n];
    }
}
