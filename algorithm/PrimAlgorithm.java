package algorithm;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

class Node implements Comparable<Node>{
    String data;
    int num;
    double longitude,latitude,weight,key;
    Node predecessor;//부모노드.
    List<Node>edge;
    List<Node>successor;//자식노드 집합.
    public Node(int num,String data,double longitude,double latitude,double weight,boolean mission){
        this.data=data; this.weight=weight;
        this.longitude=longitude; this.latitude=latitude;
        edge= (mission)? new LinkedList<>():null;
        predecessor= null;
        key=Double.MAX_VALUE;
        successor=(mission)? new LinkedList<>():null;
        this.num=num;
    }
    @Override
    public int compareTo(Node n){
        return (int)(this.key-n.key);
    }
}

class Graph{
    ArrayList<Node>nodes;
    int length;
    List<String>MST;

    public Graph(int length,List<Node> arr){
        this.length=length;
        nodes=(ArrayList<Node>)arr;
        MST=new ArrayList<>();
    }
    public int getLength(){return length;}
    public void insertGraph(int index, String data, double weight){
        Node node=nodes.get(index);
        Node arNode=findNode(data);
        node.edge.add(new Node(arNode.num,arNode.data,arNode.longitude,arNode.latitude,weight,false));
        arNode.edge.add(new Node(node.num,node.data,node.longitude,node.latitude,weight,false));
    }
    public void MST_Prim(String data){
        Node start=findNode(data);
        start.key=0.0;
        PriorityQueue<Node> minheap = new PriorityQueue<>();
        for(Node n:nodes) minheap.offer(n);
        while(!minheap.isEmpty()){
            Node n1=minheap.poll();
            for(int i=0;i<n1.edge.size();i++){
                Node n2=n1.edge.get(i);
                if(minheap.contains(findNode(n2.data))&&n2.weight<n2.key) relax(minheap,findNode(n2.data),n2.weight,n1);
            }
        }
    }
    private void relax(PriorityQueue<Node>minheap,Node n,double weight,Node subN){
        n.predecessor=subN;
        n.key=weight;
        minheap.remove(n);
        minheap.offer(n);
        subN.successor.add(n);
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

public class PrimAlgorithm {
    public static Graph g;
    public static void main(String[] args) throws IOException {
        ArrayList<Node> nodes=new ArrayList<>();
        callOfAlabama(nodes);
        g=new Graph(nodes.size(),nodes);
        callOfRoadList2();
        g.MST_Prim("River Square Plaza Shopping Center");
        printMST();
    }
    private static void callOfAlabama(List<Node>nodes){
        String[] token;int i=0;
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
    private static void printMST() throws IOException {
        Path fp=Paths.get("empty.txt");
        try(BufferedWriter bw=Files.newBufferedWriter(fp)){
            for(Node n:g.nodes){
                bw.write(n.num+" "); bw.write(n.longitude+" ");
                bw.write(n.latitude+" ");
                if(n.successor==null) System.out.println(n.data);
                for(Node e:n.successor){
                    if(e.predecessor==n) bw.write(e.num+" ");
                }
                if(n.predecessor!=null) bw.write(n.predecessor.num+" ");
                bw.newLine();
            }
        }
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
}
