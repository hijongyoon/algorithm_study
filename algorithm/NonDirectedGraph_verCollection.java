package algorithm;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

class Node{
    String data;//지먕.
    double longitude,latitude;//경도,위도
    List<Node>edge;
    boolean visiting;
    double distance;//가중치.
    public Node(String data,double longitude,double latitude,boolean mission){
        this.data=data; this.longitude=longitude; this.latitude=latitude;
        edge= (mission)? new LinkedList<>():null;
        visiting=false; distance=0.0;
    }
}

class Graph{
    //Node[]nodes;
    ArrayList<Node> nodes;
    int length;
    public Graph(int length,List<Node> arr){
        this.length=length;
        nodes=(ArrayList<Node>)arr;
    }
    public int getLength(){return length;}
    public void insertGraph(int index, String data, double dist){
        Node node=nodes.get(index);
        Node arNode=findNode(data);
        node.edge.add(new Node(arNode.data,arNode.longitude,arNode.latitude,false));
        node.edge.get(node.edge.size()-1).distance=dist;
        arNode.edge.add(new Node(node.data,node.longitude,node.latitude,false));
        arNode.edge.get(arNode.edge.size()-1).distance=dist;
    }
    public void hopsOfTenByBFS(Node arrayNode,int num){
        if(num<10&&!arrayNode.visiting){
            makeVisiting(arrayNode);
            System.out.println(num+" "+arrayNode.data+"    "+arrayNode.longitude+"    "+arrayNode.latitude);
            for(Node n:arrayNode.edge)
                hopsOfTenByBFS(findNode(n.data),num+1);
        }
    }
    public void allOfDFS(String str){
        traversalOfDFS(findNode(str));
        for(Node n:nodes){
            if(!n.visiting) traversalOfDFS(n);
        }
    }
    public void traversalOfDFS(Node arrayNode){
        makeVisiting(arrayNode);
        System.out.println(arrayNode.data+"    "+arrayNode.longitude+"    "+arrayNode.latitude);
        for(Node n:arrayNode.edge){
            if(!n.visiting) traversalOfDFS(findNode(n.data));
        }
    }
    public void makeVisiting(Node node){//방문한 노드를 visiting 으로 만듦.
        Node node2; node.visiting=true;
        for(Node n1:nodes){
            node2=n1;
            for(Node n2:node2.edge){
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
}

public class NonDirectedGraph_verCollection {
    public static void main(String[] args) throws IOException {
        ArrayList<Node> nodes=new ArrayList<>();
        callOfAlabama(nodes);
        Graph g=new Graph(nodes.size(),nodes);
        callOfRoadList2(g,nodes);
        hopsOfTen(g);
        //hopsOfAll(g);
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
                nodes.add(new Node(token[0],Double.parseDouble(token[1]),Double.parseDouble(token[2]),true));
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    private static void callOfRoadList2(Graph g,List<Node>nodes){
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
    private static double deg2rad(double deg){//주어진 도(degree) 값을 라디언으로 변환.
        return (double)(deg*Math.PI/(double)180);
    }
    private static double rad2deg(double rad){//주어진 라디언(radian) 값을 도(degree)로 변환.
        return (double)(rad*(double)180/Math.PI);
    }
    private static void hopsOfTen(Graph g){
        Scanner sc=new Scanner(System.in);
        String str=sc.nextLine();
        g.hopsOfTenByBFS(g.findNode(str),0);
    }
    private static void hopsOfAll(Graph g){
        Scanner sc=new Scanner(System.in);
        String str=sc.nextLine();
        g.allOfDFS(str);
    }
}