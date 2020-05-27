package algorithm;
import java.util.*;

class Node implements Comparable<Node>{
    static int max=10000;
    String data;
    int weight,distance;
    boolean visiting;
    List<Node>edge;
    Node predecessor;
    public Node(String data,int weight,boolean mission){
        this.data=data; this.weight=weight;
        edge= (mission)? new LinkedList<>():null;
        visiting=false;
        predecessor=null;
        distance=max;
    }
    @Override
    public int compareTo(Node n){ return this.distance-n.distance;}
}

class Graph{
    ArrayList<Node>nodes;
    int length;

    public Graph(int length,List<Node> arr){
        this.length=length;
        nodes=(ArrayList<Node>)arr;
    }
    public int getLength(){return length;}
    public void insertGraph(int index, String data, int weight){
        Node node=nodes.get(index);
        Node arNode=findNode(data);
        node.edge.add(new Node(arNode.data,weight,false));
        arNode.edge.add(new Node(node.data,weight,false));
    }
    public void makeDijkstra(String data){
        findNode(data).distance=0;
        PriorityQueue<Node> minheap = new PriorityQueue<>();
        minheap.offer(findNode(data));
        while(!minheap.isEmpty()){
            Node n1=minheap.poll();
            for(int i=0;i<n1.edge.size();i++){
                    Node n2 = n1.edge.get(i);
                    relax(n1, findNode(n2.data), n2.weight,minheap);
            }
        }
    }
    public void relax(Node n1,Node n2,int weight,PriorityQueue<Node>minheap){
        if(n2.distance>n1.distance+weight){
            n2.distance=n1.distance+weight;
            n2.predecessor=n1;
            minheap.remove(n2);
            minheap.offer(n2);
        }
    }
    public Node findNode(String data){//인접리스트안 배열에서 큐에서 빼낸 노드와 데이터가 같은 배열 요소를 찾음.
        Node node=null;
        for(Node n:nodes){
            if(n.data.equals(data)) {
                node=n; break;
            }
        }
        return node;
    }
    public void printPath(String data,Node n){
        if(n.data.equals(data)) return;
        printPath(data,n.predecessor);
        System.out.print(n.data+" ");
    }
}
public class DijkstraAlgorithm {public static Scanner sc;
    public static Graph g;
    public static void main(String[] args) {
        sc=new Scanner(System.in);
        ArrayList<Node> nodes=new ArrayList<>();
        makeGraph(nodes);
        g.makeDijkstra("s");
        System.out.print("s"+" ");
        g.printPath("s",g.findNode("z"));
    }
    private static void makeGraph(List<Node>nodes){
        for(int i=0;i<5;i++) nodes.add(new Node(sc.nextLine(),0,true));
        makeEdge(nodes);
    }
    private static void makeEdge(List<Node>nodes) {
        g = new Graph(5, nodes);
        g.insertGraph(0, "t", 10);
        g.insertGraph(0, "y", 5);
        g.insertGraph(0, "z", 100);
        g.insertGraph(1, "y", 3);
        g.insertGraph(1, "x", 1);
        g.insertGraph(2, "y", 9);
        g.insertGraph(2, "z", 6);
        g.insertGraph(3, "z", 2);
    }
}
