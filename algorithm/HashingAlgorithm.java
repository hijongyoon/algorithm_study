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
    Suffix next;
    public Suffix(String data){
        sWord=data;
        count=1;
        next=null;
    }
}

class Prefix{
    String pWord1;
    String pWord2;
    Suffix suf;
    int countSuffix;
    public Prefix(String data1,String data2){
        pWord1=data1;
        pWord2=data2;
        suf=null;
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
    Node next;
    public Node(Prefix data){
        this.data=data;
        next=null;
    }
}

public class HashingAlgorithm {
    public static Scanner sc;
    public static Node[] table;
    public static int nodeCount=0,stringCount=0;
    public static String first,second;
    public static String beforeLast,last;// 문장 마지막 전 단어와 마지막 단어를 저장.
    public static Node address;
    public static Suffix[]suf;
    public static void main(String[] args) {
        table=new Node[4093];
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
        if(table[p1.hash()]==null) {
            table[p1.hash()]=new Node(p1);
            address=table[p1.hash()];
            return;
        }
        else linkNode(p1.hash(),p1);
    }
    private static void linkNode(int hashing,Prefix p) {
        Node node=table[hashing];
        Node sub=null;
        while(node!=null) {
            sub=node;
            node=node.next;
        }
        sub.next=new Node(p);
        address=sub.next;
    }
    private static void makeSuffix(String data){
        if(findSuffix(data)) return;
        if(address.data.suf==null) address.data.suf=new Suffix(data);
        else linkSuffix(address.data,data);
        address.data.countSuffix++;
    }
    private static boolean findPrefix(String data1,String data2) {
        int i;
        Node n;
        for(i=0;i<table.length;i++) {
            n=table[i];
            while(n!=null) {
                if(n!=null&&n.data.pWord1.equals(data1)&&n.data.pWord2.equals(data2)) {
                    address=n;
                    return true;
                }
                n=n.next;
            }
        }
        return false;
    }
    private static boolean findSuffix(String data) {
        Prefix p=address.data;
        if(p.suf==null) return false;
        Suffix sub=p.suf;
        while(sub!=null) {
            if(sub.sWord.equals(data)) {
                sub.count++;
                return true;
            }
            sub=sub.next;
        }
        return false;
    }
    private static void linkSuffix(Prefix p,String data) {
        Suffix sub=p.suf;
        Suffix exSub=null;
        while(sub!=null) {
            exSub=sub;
            sub=sub.next;
        }
        exSub.next=new Suffix(data);
    }
    private static void printLine(){
        suf=new Suffix[100000];
        String choose;
        System.out.print(first+" "+second+" ");
        while(true) {
            findPrefix(first,second);
            choose=calculate();
            if(choose.equals("[end]")||stringCount==1000)break;
            System.out.print(choose+" ");
            if(stringCount!=0&&stringCount%20==0) System.out.println();
            stringCount++;
            first=second;
            second=choose;
        }
    }
    private static String calculate() {
        Suffix sub=address.data.suf;
        while(sub!=null) {
            suf[nodeCount++]=sub;
            sub=sub.next;
        }
        int result=chooseSuffix();
        nodeCount=0;
        return suf[result].sWord;
    }
    private static int chooseSuffix() {
        int result=0,i=0;
        Random rand=new Random();
        for(i=0;i<nodeCount;i++) result+=suf[i].count;
        for(i=0;i<nodeCount;i++) {
            if(rand.nextInt(result)< suf[i].count) break;//여기에다가 =을 넣어줄지는 고민을 좀 해봐야한다.
        }
        return (i>=nodeCount)?  i-1 :  i;
    }
}