package realAlgorithm;
import java.util.*;

class Node {
    int num;
    List<Node>edge;
    boolean visiting;
    int color;
    public Node(int num,boolean mission){
        this.num=num;
        edge= (mission)? new LinkedList<>():null;
        visiting=false;
        color=2;
    }
}

class Graph{
    static int BLACK=0,WHITE=1,NOTHING=2;
    ArrayList<Node> nodes;
    public Graph(List<Node> arr){
        nodes=(ArrayList<Node>)arr;
    }
    public void insertGraph(int index, int num){
        Node node=nodes.get(index);
        Node arNode=nodes.get(num);
        node.edge.add(new Node(arNode.num,false));
        arNode.edge.add(new Node(node.num,false));
    }
    public boolean fillColor(int num){
        Queue<Node> q=new LinkedList<>();
        Node start=nodes.get(num);
        q.offer(start);
        start.color=BLACK;
        while(!q.isEmpty()){
            Node n=q.poll();
            for(Node e:n.edge){
                Node n2=nodes.get(e.num);
                if(n2.color==NOTHING){
                    if(n.color==BLACK) n2.color=WHITE;
                    else if(n.color==WHITE) n2.color=BLACK;
                    q.offer(n2);
                }
                else if(n.color==n2.color)  return false;
            }
        }
        return true;
    }
}

public class FinalExam {
    public static Scanner sc=new Scanner(System.in);
    public static Graph g;
    public static void main(String[] args) {
        ArrayList<Node>nodes=new ArrayList<>();
        makeGraph(nodes);
        if(g.fillColor(0)) System.out.println("Yes");
        else System.out.println("No");
    }
    public static void makeGraph(List<Node>nodes){
        int num1=sc.nextInt();//정점의 갯수.
        int num2=sc.nextInt();//엣지의 갯수.
        for(int i=0;i<num1;i++)
            nodes.add(new Node(i,true));
        g=new Graph(nodes);
        for(int i=0;i<num2;i++)
            g.insertGraph(sc.nextInt(),sc.nextInt());
    }
}