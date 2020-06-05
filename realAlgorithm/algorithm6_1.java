package realAlgorithm;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

class Node implements Comparable<Node>{
    static double max=10000000000000.0;
    String data;//지먕.
    double longitude,latitude;//경도,위도
    List<Node>edge;
    boolean visiting;
    double distance,weight;//가중치, 다익스트라 알고리즘 때 사용하는 것.
    Node predecessor;
    public Node(String data,double longitude,double latitude,double distance,boolean mission){
        this.data=data; this.longitude=longitude; this.latitude=latitude;
        edge= (mission)? new LinkedList<>():null;
        visiting=false; this.distance=distance; weight=max;
        predecessor=null;
    }
    @Override
    public int compareTo(Node n){return (int)(this.weight-n.weight);}
}

class Graph{
    ArrayList<Node> nodes;
    int length;
    public Graph(int length,List<Node> arr){
        this.length=length;
        nodes=(ArrayList<Node>)arr;
    }
    public int getLength(){return length;}
    public void makeInit(int num){
        if(num==0){
            for(Node n1:nodes){
                n1.visiting=false;
                for(Node n2:n1.edge) n2.visiting=false;
            }
        }
        else if(num==1){
            for(Node n1:nodes) n1.weight=Node.max;
        }
    }
    public void insertGraph(int index, String data, double dist){
        Node node=nodes.get(index);
        Node arNode=findNode(data);
        node.edge.add(new Node(arNode.data,arNode.longitude,arNode.latitude,dist,false));
        arNode.edge.add(new Node(node.data,node.longitude,node.latitude,dist,false));
    }
    public void hopsOfTenByBFS(String str){
        Queue<Node> q=new LinkedList<>();
        int num=0,qLength=1,putQ=0;
        q.offer(findNode(str));
        makeVisiting(findNode(str));
        while(num<11){
            for(int i=0;i<qLength;i++){
                Node n=q.poll();
                System.out.println(n.data+" "+n.longitude+" "+n.latitude);
                for(Node n2:n.edge){
                    if(!n2.visiting){
                        makeVisiting(findNode(n2.data));
                        q.offer(findNode(n2.data));
                        putQ++;
                    }
                }
            }
            qLength=putQ;
            putQ=0; num++;
        }
    }
    public void allOfDFS(String str,BufferedWriter bw) throws IOException {
        traversalOfDFS(findNode(str),bw);
        for(Node n:nodes){
            if(!n.visiting) traversalOfDFS(n,bw);
        }
    }
    public void traversalOfDFS(Node arrayNode,BufferedWriter bw) throws IOException {
        makeVisiting(arrayNode);
        //System.out.println(arrayNode.data+"    "+arrayNode.longitude+"    "+arrayNode.latitude);
        bw.write(arrayNode.data+" ");
        bw.write(arrayNode.longitude+" ");
        bw.write(arrayNode.latitude+" ");
        bw.newLine();
        for(Node n:arrayNode.edge){
            if(!n.visiting) traversalOfDFS(findNode(n.data),bw);
        }
    }
    public void makeDijkstra(String data){
        findNode(data).weight=0.0;
        PriorityQueue<Node> minheap = new PriorityQueue<>();
        minheap.offer(findNode(data));
        while(!minheap.isEmpty()){
            Node n1=minheap.poll();
            for(int i=0;i<n1.edge.size();i++){
                Node n2=n1.edge.get(i);
                relax(n1,findNode(n2.data),n2.distance,minheap);
            }
        }
    }
    public void relax(Node n1,Node n2,double distance,PriorityQueue<Node>minheap){
        if(n2.weight>(n1.weight+distance)){
            n2.weight=n1.weight+distance;
            n2.predecessor=n1;
            minheap.remove(n2);//혹시 있을 수도 있으니 삭제.
            minheap.offer(n2);//삽입.(다시 삽입일 수도 있음)
        }
    }
    public void makeVisiting(Node node){//방문한 노드를 visiting 으로 만듦.
        node.visiting=true;
        for(Node n1:nodes){
            for(Node n2:n1.edge){
                if(n2.data.equals(node.data)) n2.visiting=true;
            }
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
    public void printPath(String data,Node n){
        if(n.data.equals(data)) return;
        printPath(data,n.predecessor);
        System.out.println(n.data+" ");
    }
}

public class algorithm6_1 {
    public static Scanner sc;
    public static void main(String[] args) throws IOException {
        sc=new Scanner(System.in);
        ArrayList<Node> nodes=new ArrayList<>();
        callOfAlabama(nodes);
        Graph g=new Graph(nodes.size(),nodes);
        callOfRoadList2(g);
        hopsOfTen(g);
        g.makeInit(0);
        inputDFS(g);
        String str1=sc.nextLine();
        String str2=sc.nextLine();
        g.makeDijkstra(str1);
        System.out.println(str1+" ");
        g.printPath(str1,g.findNode(str2));
        System.out.println(g.findNode(str2).weight);
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
                nodes.add(new Node(token[0],Double.parseDouble(token[1]),Double.parseDouble(token[2]),0.0,true));
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    private static void callOfRoadList2(Graph g){
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
    private static double calDistance(double lat1,double lon1,double lat2,double lon2){
        double theta,dist;
        theta=lon1-lon2;
        dist=Math.sin(deg2rad(lat1))*Math.sin(deg2rad(lat2))+Math.cos(deg2rad(lat1))*Math.cos(deg2rad(lat2))*Math.cos(deg2rad(theta));
        dist=Math.acos(dist); dist=rad2deg(dist);
        dist=dist*60*1.1515; dist=dist*1.609344; dist=dist*1000.0;
        return dist;
    }
    private static double deg2rad(double deg){ return (double)(deg*Math.PI/(double)180);}
    private static double rad2deg(double rad){ return (double)(rad*(double)180/Math.PI);}
    private static void hopsOfTen(Graph g){
        Scanner sc=new Scanner(System.in);
        String str=sc.nextLine();
        g.hopsOfTenByBFS(str);
    }
    public static void inputDFS(Graph g){
        Path fp= Paths.get("DFS.txt");
        try(BufferedWriter bw= Files.newBufferedWriter(fp)){
            Scanner sc=new Scanner(System.in);
            g.allOfDFS(sc.nextLine(),bw);
        }catch(IOException e) {
            e.printStackTrace();
        }
    }
}