package algorithm;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

class Suffix{
    String sWord;
    int count;
    public Suffix(String data){
        sWord=data;
        count=1;
    }
}

class Prefix{
    String pWord1;
    String pWord2;
    List<Suffix>suf;
    int countSuffix;
    public Prefix(String data1,String data2){
        pWord1=data1;
        pWord2=data2;
        suf=new ArrayList<>();
        countSuffix=0;
    }
    public int hash(){
        int h=0;
        char p;
        int i=0;
        for(p=pWord1.charAt(i);p!='ㅣ';p=isChar(++i,pWord1))  h=31*h+p;
        if(h<0) h=h*-1;
        i=0;
        for(p= pWord2.charAt(i);p!='ㅣ';p=isChar(++i,pWord2)) h=31*h+p;
        if(h<0) h=h*-1;
        return h%4093;
    }
    private char isChar(int i,String s){
        if(i==s.length()) return 'ㅣ';
        else return s.charAt(i);
    }
}

class Node{
    Prefix data;
    public Node(Prefix data){
        this.data=data;
    }
}

public class HashingAlgorithm_ver_CollectionFrameWork {
    public static Scanner sc;
    public static List<ArrayList<Node>> table;
    public static int nodeCount=0,stringCount=0;
    public static String first,second;
    public static String beforeLast,last;// 문장 마지막 전 단어와 마지막 단어를 저장.
    public static Node address;
    public static List<Suffix>suf;
    public static void main(String[] args){
        table=new ArrayList<>(4093);
        for(int i=0;i<4093;i++) table.add(new ArrayList<>());
        sc=new Scanner(System.in);
        readLine();
        printLine();
    }
    private static void readLine(){
        Path fp= Paths.get("hashing.txt");
        String str;
        String post="";
        try(BufferedReader br= Files.newBufferedReader(fp)){
            while(true) {
                str=br.readLine();
                if(str==null) break;
                if(str.equals("")) continue;
                str=post.concat(str);// 마지막 단어를 다음줄 문장에 연결
                String []array=str.split(" ");
                if(first==null&&second==null) {
                    first=array[0];
                    second=array[1];
                }
                make(array);
                post=array[array.length-1].concat(" ");
            }
        }catch(IOException e) {
            e.printStackTrace();
        }
    }
    private static void make(String []array){
        int i;
        if(beforeLast!=null&&last!=null) {
            makePrefix(beforeLast,last);
            makeSuffix(array[1]);
        }
        for(i=0;i<array.length-1;i++){
            makePrefix(array[i],array[i+1]);
            if(i!=array.length-2) makeSuffix(array[i+2]);
            if(i>=array.length-2) {
                beforeLast=array[i];
                last=array[i+1];
            }
        }
    }
    private static void makePrefix(String data1,String data2){
        if(findPrefix(data1,data2)) return ;
        Prefix p1=new Prefix(data1,data2);
        if(table.get(p1.hash()).isEmpty()) {
            ArrayList<Node>node=table.get(p1.hash());
            node.add(new Node(p1));
            table.set(p1.hash(),node);
            address=node.get(node.size()-1);
            return;
        }
        else linkNode(p1.hash(),p1);
    }
    private static void linkNode(int hashing,Prefix p) {
        ArrayList<Node>node=table.get(hashing);
        node.add(new Node(p));
        address=node.get(node.size()-1);
    }
    private static void makeSuffix(String data){
        if(findSuffix(data)) return;
        address.data.suf.add(new Suffix(data));
    }
    private static boolean findPrefix(String data1,String data2) {
        Node n;
        for (ArrayList<Node> node : table) {
            for (Node value : node) {
                n = value;
                if (n.data.pWord1.equals(data1) && n.data.pWord2.equals(data2)) {
                    address = n;
                    return true;
                }
            }
        }
        return false;
    }
    private static boolean findSuffix(String data) {
        Prefix p=address.data;
        if(p.suf.size()==0) return false;
        for (Suffix sub : p.suf) {
            if (sub.sWord.equals(data)) {
                sub.count++;
                return true;
            }
        }
        return false;
    }
    private static void printLine(){
        suf=new ArrayList<>();
        String choose;
        System.out.print(first+" "+second+" ");
        while(true) {
            findPrefix(first,second);
            choose=calculate();
            if(choose.equals("[end]")||stringCount==1000) break;
            System.out.print(choose+" ");
            if(stringCount!=0&&stringCount%20==0) System.out.println();
            stringCount++;
            first=second;
            second=choose;
        }
    }
    private static String calculate() {
        suf=address.data.suf;
        nodeCount=address.data.suf.size()-1;
        int result=chooseSuffix();
        nodeCount=0;
        return suf.get(result).sWord;
    }
    private static int chooseSuffix() {
        int result=0,i=0;
        Random rand=new Random();
        for(i=0;i<=nodeCount;i++) result+=suf.get(i).count;//result+=suf[i].count;
        for(i=0;i<=nodeCount;i++) {
            if(rand.nextInt(result)< suf.get(i).count) break;//여기에다가 =을 넣어줄지는 고민을 좀 해봐야한다.
        }
        return (i>nodeCount)?  i-1 :  i;
    }
}