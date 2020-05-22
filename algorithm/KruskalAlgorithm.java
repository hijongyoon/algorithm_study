package algorithm;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

class Node{
    String data;
    double longitude,latitude;
    double weight;
    int num;
    List<Node>edge;
    Node parent;
    int treeSize;
    public Node(int num,String data,double longitude,double latitude,double weight,boolean mission){
        this.num=num;
        this.data=data; this.weight=weight;
        this.longitude=longitude; this.latitude=latitude;
        edge= (mission)? new LinkedList<>():null;
        parent=this;
        treeSize=1;
    }
}

class Edge implements Comparable<Edge>{
    Node n1;
    Node n2;
    double weight;
    public Edge(Node n1,Node n2,double weight){
        this.n1=n1; this.n2=n2; this.weight=weight;
    }
    @Override
    public int compareTo(Edge e){
        return (int)(this.weight-e.weight);
    }
}

class Graph{
    ArrayList<Node>nodes;
    int length;
    ArrayList<Edge>edges;
    List<String>MST;

    public Graph(int length,List<Node> arr){
        this.length=length;
        nodes=(ArrayList<Node>)arr;
        edges=new ArrayList<>();
        MST=new ArrayList<>();
    }
    public int getLength(){return length;}
    public void insertGraph(int index, String data, double weight){
        Node node=nodes.get(index);
        Node arNode=findNode(data);
        node.edge.add(new Node(arNode.num,arNode.data,arNode.longitude,arNode.latitude,weight,false));
        arNode.edge.add(new Node(node.num,node.data,node.longitude,node.latitude,weight,false));
        edges.add(new Edge(node,arNode,weight));
    }
    public void MST_Kruskal(){
        Collections.sort(edges);
        int chance=0,i=0;
        while(true){
            if(chance==length-1) break;
            Edge e=edges.get(i);
            if(findSet(e.n1)!=findSet(e.n2)) {
                chance++;
                MST.add(e.n1.data);
                MST.add(e.n2.data);
                weightedUnion(e.n1, e.n2);
            }
            i++;
        }
    }
    public Node findSet(Node n){
        while (n!=n.parent){
            n.parent=n.parent.parent;
            n=n.parent;
        }
        return n.parent;
    }
    private void weightedUnion(Node n1,Node n2){
        Node x=findSet(n1);
        Node y=findSet(n2);
        if(x.treeSize>y.treeSize) {
            y.parent=x;
            x.treeSize+=y.treeSize;
        }
        else{
            x.parent=y;
            y.treeSize+=x.treeSize;
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
    public int findIndex(String data){//해당 단어 배열의 인덱스 찾기.
        Node node=null;
        for(Node n: nodes) {
            if (n.data.equals(data)) {
                node=n;break;
            }
        }
        return nodes.indexOf(node);
    }
}

public class KruskalAlgorithm {
    public static Graph g;
    public static Graph MST;// 원래 그래프를 보존해야할 경우를 생각해 MST 를 따로 만듦.
    public static void main(String[] args) throws IOException {
        ArrayList<Node> nodes=new ArrayList<>();
        ArrayList<Node> nodesOfMST=new ArrayList<>();
        callOfAlabama(nodes);
        g=new Graph(nodes.size(),nodes);
        callOfRoadList2();
        g.MST_Kruskal();
        makeMST(nodesOfMST,nodes);
        printMST();
    }
    private static void callOfAlabama(List<Node>nodes){
        String[] token; int i=0;
        Path fp1= Paths.get("alabama.txt");
        try(BufferedReader br= Files.newBufferedReader(fp1)){
            String str;
            while(true){
                str=br.readLine();
                if(str==null) break;
                token=str.split("\t",3);
                nodes.add(new Node(i,token[0],Double.parseDouble(token[1]),Double.parseDouble(token[2]),0,true));
                i++;
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    private static void callOfRoadList2(){
        Path fp2=Paths.get("roadList2.txt"); String[]token;
        try(BufferedReader br=Files.newBufferedReader(fp2)){
            String str;
            while(true){
                str=br.readLine();
                if(str==null) break;
                token=str.split("\t",2);
                int index=g.findIndex(token[0]);
                Node node1=g.findNode(token[0]),node2=g.findNode(token[1]);
                g.insertGraph(index,token[1],calDistance(node1.latitude,node1.longitude,node2.latitude,node2.longitude));
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    public static void makeMST(List<Node>nodesOfMST,List<Node>nodesOfGraph){
        for(Node n:nodesOfGraph)
            nodesOfMST.add(new Node(n.num,n.data,n.longitude,n.latitude,n.weight,true));
        MST=new Graph(g.length,nodesOfMST);
        makeEdgeOfMST();
    }
    private static void makeEdgeOfMST(){
        String data1,data2;
        for(int i=0;i<g.MST.size();i+=2){
            data1=g.MST.get(i); data2=g.MST.get(i+1);
            MST.insertGraph(MST.findIndex(data1),data2,findEdge(data1,data2));
        }
    }
    private static double findEdge(String data,String data2){
        Node n=g.findNode(data);
        for(Node e:n.edge) {
            if (e.data.equals(data2)) {
                n=e;
                break;
            }
        }
        return n.weight;
    }
    private static double calDistance(double lat1,double lon1,double lat2,double lon2){
        double theta,dist;
        theta=lon1-lon2;
        dist=Math.sin(deg2rad(lat1))*Math.sin(deg2rad(lat2))+Math.cos(deg2rad(lat1))*Math.cos(deg2rad(lat2))*Math.cos(deg2rad(theta));
        dist=Math.acos(dist); dist=rad2deg(dist);
        dist=dist*60*1.1515; dist=dist*1.609344; dist=dist*1000.0;
        return dist;
    }
    private static double deg2rad(double deg){//주어진 도(degree) 값을 라디언으로 변환.
        return (double)(deg*Math.PI/(double)180);
    }
    private static double rad2deg(double rad){//주어진 라디언(radian) 값을 도(degree)로 변환.
        return (double)(rad*(double)180/Math.PI);
    }
    private static void printMST() throws IOException {
        Path fp=Paths.get("mst.txt");
        try(BufferedWriter bw=Files.newBufferedWriter(fp)){
            for(Node n:MST.nodes){
                bw.write(n.num+" "); bw.write(n.longitude+" ");
                bw.write(n.latitude+" "); bw.write(n.edge.size()+" ");
                for(Node e:n.edge) bw.write(e.num+" ");
                bw.newLine();
            }
        }
    }
}