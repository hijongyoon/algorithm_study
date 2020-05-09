package realAlgorithm;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

class Node<E extends String>{
    protected E name;
    protected E company_name;
    protected E addresss;
    protected E zip;
    protected E phone;
    protected E email;
    protected Node<E>left;
    protected Node<E>right;
    protected Node<E>parent;

    public Node(E name,E company_name,E address,E zip, E phone, E email){
        this.name=name; this.company_name=company_name; this.addresss=address; this.zip=zip; this.phone=phone; this.email=email;
        left=null; right=null; parent=null;
    }
}

class BinaryTree<E extends String>{
    protected Node<E>root;
    protected int size;
    public BinaryTree(){root=null; size=0;}
    protected  BinaryTree(Node<E>root){this.root=root;}
    public void inorderTreeWalk(Node<E>x){//inorder 순으로 순회한다면 왼쪽->가운데->오른쪽 순으로 방문한다.
        if(x!=null){
            inorderTreeWalk(x.left);
            System.out.println(x.name);
            System.out.println("   "+"Company: "+x.company_name);
            System.out.println("   "+"Address: "+x.addresss);
            System.out.println("   "+"Zipcode: "+x.zip);
            System.out.println("   "+"Phones: "+x.phone);
            System.out.println("   "+"Email: "+x.email);
            inorderTreeWalk(x.right);
        }
    }
    public void saveTree(BufferedWriter bw, Node<E>x) throws IOException {
        if(x!=null) {
            saveTree(bw,x.left);
            bw.write(x.name+",");
            bw.write(x.company_name+",");
            bw.write(x.addresss+",");
            bw.write(x.zip+",");
            bw.write(x.phone+",");
            bw.write(x.email+",");
            bw.newLine();
            saveTree(bw,x.right);
        }
    }
    public Node<E> treeSearch(E word){
        Node<E> x=root;
        while(x!=null&&!word.equals(x.name)){
            if(word.compareTo(x.name)<0) x=x.left;
            else x=x.right;
        }
        return x;
    }
    public void treeTrace(E word) {
        Node<E> x=root;
        while(x!=null&&!word.equals(x.name)){
            System.out.println(x.name);
            if(word.compareTo(x.name)<0) x=x.left;
            else x=x.right;
        }
        System.out.println(x.name);
    }
    public Node<E> treeMinimum(Node<E> x){
        if(x==null) x=root;
        while(x.left!=null) x=x.left;
        return x;
    }
    public void treeInsert(Node<E>node){
        Node<E> y=null; Node<E> x=root;
        while(x!=null){//x가 null이 될 때 까지 반복하고 y는 그 뒤를 따라온다.
            y=x;
            if(node.name.compareTo(x.name)<0) x=x.left;//이 if 문은 x가 한칸 아래로 전진하는 것이다.
            else x=x.right;
        }
        node.parent=y;
        if(y==null) root=node;//원래 tree가 empty tree.
        else if(node.name.compareTo(y.name)<0) y.left=node;//새로운 노드를 왼쪽 자식에 삽입.
        else y.right=node;//새로운 노드를 오른쪽 자식에 삽입.
        size++;
    }
    public Node<E> treeSuccessor(Node<E>x){
        if(x==null) x=root;
        if(x.right!=null) return treeMinimum(x.right);
        Node<E>y=x.parent;
        while(y!=null&&x==y.right){
            x=y; y=y.parent;
        }
        return y;
    }
    public Node<E> treeDelete(Node<E>node){//삭제할 노드를 검색해서 찾은 후에 이 메소드를 호출 해야함.
        Node<E> y; Node<E> x;
        if(node.left==null||node.right==null) y=node;//자식 노드가 0개이거나 1개인 경우.
        else y=treeSuccessor(node);//자식노드가 2개인 경우.
        if(y.left!=null) x=y.left;//이 if~else 문은 x가 y의 왼쪽 자식일수도 오른쪽 자식일수도 null 일수도 있음. 이 경우는 case 1,2,3 전부 해당.
        else x=y.right;//case 3인 경우에도 z의 successor 는 오른쪽이나 왼쪽의 자식 중 둘 중에 하나 또는 하나도 안가질 수 있기 때문이다.
        if(x!=null) x.parent=y.parent;//y를 삭제할 것이므로 x의 부모는 y의 부모가 됨.
        if(y.parent==null) root=x;//y의 부모가 null 이라면 즉 y가 root 노드.(이 경우는 무조건 자식노드가 0게이너가 1개인 상황)
        else if (y==y.parent.left) y.parent.left=x;//y가 y의 부모가 왼쪽 자식이면 x도 y의 부모의 왼쪽자식이 된다.
        else y.parent.right=x;//y가 y의 부모의 오른쪽 자식이라면 x도 y의 부모의 오른쪽 자식이 된다.
        if(y!=node) {//z의 successor 인 y의 데이터를 z에 옮김.
            node.name=y.name; node.company_name=y.company_name;
            node.addresss=y.addresss; node.zip=y.zip; node.phone=y.phone;
            node.email=y.email;
        }
        size-=1;
        return y;
    }
}

public class algorithm4 {
    private static BinaryTree tree;
    public static void main(String[] args) {
        tree=new BinaryTree();
        Scanner sc=new Scanner(System.in);
        String str;
        while(true) {
            System.out.print("$ ");
            str=sc.nextLine();
            String[] str1=str.split(" ");
            if(str1[0].equals("read")) readFile(str1[1]);
            else if(str1[0].equals("list")) tree.inorderTreeWalk(tree.root);
            else if(str1[0].equals("find")) findInfo(str1[1]);
            else if(str1[0].equals("trace")) tree.treeTrace(str1[1]);
            else if(str1[0].equals("delete")) tree.treeDelete(tree.treeSearch(str1[1]));
            else if(str1[0].equals("save")) saveFile(str1[1]);
            else if(str1[0].equals("exit")) break;
        }
    }
    private static void readFile(String data) {
        Path fp=Paths.get(data);
        String str;
        try(BufferedReader br=Files.newBufferedReader(fp)){
            str=br.readLine();
            while(true) {
                str=br.readLine();
                if(str==null) break;
                String[] array=str.split(",",2);//array[0] 이 이름
                str=array[1];
                str=str.replace(", "," ");
                String[]array2=str.split(",");
                array2[0]=array2[0].replace("\"","");
                array2[1]=array2[1].replace("\"","");
                tree.treeInsert(new Node(array[0],array2[0],array2[1],array2[2],array2[3],array2[4]));
            }
        }catch(IOException e) {
            e.printStackTrace();
        }
    }
    private static void findInfo(String data) {
        Node<String>x=tree.treeSearch(data);
        System.out.println(x.name);
        System.out.println("   "+"Company: "+x.company_name);
        System.out.println("   "+"Address: "+x.addresss);
        System.out.println("   "+"Zipcode: "+x.zip);
        System.out.println("   "+"Phones: "+x.phone);
        System.out.println("   "+"Email: "+x.email);
    }
    private static void saveFile(String data) {
        Path fp= Paths.get(data);
        try(BufferedWriter bw= Files.newBufferedWriter(fp)){
            tree.saveTree(bw,tree.root);
        }catch(IOException e) {
            e.printStackTrace();
        }
    }
}
