package algorithm;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

class Node<E extends String>{
    protected E word;
    protected E word_class;
    protected E meaning;
    protected Node<E>left;
    protected Node<E>right;
    protected Node<E>parent;

    public Node(E word,E word_class,E meaning){
        this.word=word; this.word_class=word_class; this.meaning=meaning;
        left=null; right=null; parent=null;
    }
    public String toString(){
        return word.toString();
    }
}

class BinaryTree<E extends String>{
    protected Node<E>root;
    protected int size;
    public BinaryTree(){root=null; size=0;}
    protected  BinaryTree(Node<E>root){this.root=root;}
    public BinaryTree(E word,E word_class,E meaing, BinaryTree<E>leftTree,BinaryTree<E>rightTree){//왼쪽 부트리랑 오른쪽 부트리를 하나의 root 노드에 결합.
        root=new Node<>(word,word_class,meaing);
        if(leftTree!=null) root.left=leftTree.root;
        else root.left=null;
        if(rightTree!=null) root.right=rightTree.root;
        else root.right=null;
    }
    public BinaryTree<E> getLeftSubtree(){//root 노드 기준으로 왼쪽 subtree의 root 노드를 반환.
        if(root!=null&&root.left!=null) return new BinaryTree<>(root.left);
        else return null;
    }
    public BinaryTree<E> getRightSubtree(){//root 노드 기준으로 오른쪽 subtree의 root 노드를 반환.
        if(root!=null&&root.right!=null) return new BinaryTree<>(root.right);
        else return null;
    }
    public void inorderTreeWalk(Node<E>x){//inorder 순으로 순회한다면 왼쪽->가운데->오른쪽 순으로 방문한다.
        if(x==null)x=root;
        if(x!=null){
            inorderTreeWalk(x.left);
            System.out.print(x+" ");
            inorderTreeWalk(x.right);
        }
    }
    public void preorderTreeWalk(Node<E>x){//preorder 순으로 순회한다면 가운데->왼쪽->오른쪽 순으로 방문한다.
        if(x!=null){
            System.out.print(x+" ");
            preorderTreeWalk(x.left);
            preorderTreeWalk(x.right);
        }
    }
    public void postorderTreeWalk(Node<E>x){//postorder 순으로 순회한다면 왼쪽->오른쪽->가운데 순으로 방문한다.
        if(x!=null){
            postorderTreeWalk(x.left);
            postorderTreeWalk(x.right);
            System.out.print(x+" ");
        }
    }
    public void levelOrderTreeWalk(){//레벨 순으로 왼쪽에서 오른쪽으로 방문한다.
        Queue<Node<E>> que=new LinkedList<>();
        que.offer(root); Node<E>node;
        while(!que.isEmpty()){//큐가 빌때 까지 진행.
            node= que.poll();
            System.out.print(node+" ");
            if(node.left!=null) que.offer(node.left);
            if(node.right!=null) que.offer(node.right);//여기서는 else if 문을 사용해선 안된다. 두 개의 자식이 있을 경우엔 else if 문은 하나의 자식만 넣어준다.
        }
        System.out.println();
    }
    public Node<E> treeSearch(E word){
        Node<E> x=root;
        while(x!=null&&!word.equals(x.word)){
            if(word.compareTo(x.word)<0) x=x.left;
            else x=x.right;
        }
        return x;
    }
    public Node<E> treeMinimum(Node<E> x){
        if(x==null) x=root;
        while(x.left!=null) x=x.left;
        return x;
    }
    public Node<E> treeMaximum(Node<E> x){
        if(x==null) x=root;
        while(x.right!=null) x=x.right;
        return x;
    }
    public void treeInsert(Node<E>node){
        Node<E> y=null; Node<E> x=root;
        while(x!=null){//x가 null이 될 때 까지 반복하고 y는 그 뒤를 따라온다.
            y=x;
            if(node.word.compareTo(x.word)<0) x=x.left;//이 if 문은 x가 한칸 아래로 전진하는 것이다.
            else x=x.right;
        }
        node.parent=y;
        if(y==null) root=node;//원래 tree가 empty tree.
        else if(node.word.compareTo(y.word)<0) y.left=node;//새로운 노드를 왼쪽 자식에 삽입.
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
            node.word=y.word; node.word_class=y.word_class; node.meaning=y.meaning;
        }
        size--;
        return y;
    }
    public void sizeReturn(){
        System.out.println(size);
    }
}

public class TreeAlgorithm {
    public static void main(String[] args) {
        String str; String[]token1; String[]token2; String[]token3; Node<String>node;
        BinaryTree<String>tree=new BinaryTree<>();
        try(BufferedReader br=new BufferedReader(new FileReader("shuffled_dict.txt"))){
            while(true){
                str=br.readLine();
                if(str==null) break;
                str=str.concat(" ");
                token1=str.split(" ",2); token2=token1[1].split("\\)",2);
                token3=token2[0].split("\\(",2); token2=token2[1].split(" ",2);
                token3[1]=token3[1].replace(".","");
                node=new Node<>(token1[0],token3[1],token2[1]);
                tree.treeInsert(node);
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        Scanner sc=new Scanner(System.in);
        while(true){
            System.out.print("$ ");
            str=sc.nextLine();
            token1=str.split(" ",2);
            if(token1[0].equals("size")) tree.sizeReturn();
            else if(token1[0].equals("find")) find(tree,token1[1]);
            else if(token1[0].equals("add")) add(tree,sc);
            else if(token1[0].equals("delete")) delete(tree,token1[1]);
            else if(token1[0].equals("deleteAll")) deleteAll(tree,token1[1]);
            else if(token1[0].equals("exit")) break;
        }
    }
    public static void find(BinaryTree<String>tree,String str){
        Node<String>node=tree.treeSearch(str);
        if(node==null) {
            System.out.println("Not found");
            return;
        }
        System.out.println("word: "+node.word);
        System.out.println("class: "+node.word_class);
        System.out.println("meaning: "+node.meaning);
    }
    public static void add(BinaryTree<String>tree,Scanner sc){
        String word,word_class,meaning;
        System.out.print("word: "); word=sc.nextLine();
        System.out.print("class: "); word_class=sc.nextLine();
        System.out.print("meaning: "); meaning=sc.nextLine();
        tree.treeInsert(new Node<>(word,word_class,meaning));
    }
    public static void delete(BinaryTree<String>tree,String str){
        Node<String>node=tree.treeSearch(str);
        if(node==null){
            System.out.println("Not found");
            return ;
        }
        node=tree.treeDelete(node); node=null;
        System.out.println("Deleted successfully");
    }
    public static void deleteAll(BinaryTree<String>tree,String str){
        String buffer; int size=0;
        try(BufferedReader br=new BufferedReader(new FileReader(str))){
            while(true){
                buffer=br.readLine();
                if(buffer==null) break;
                Node<String>node=tree.treeSearch(buffer);
                tree.treeDelete(node); size++;
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        System.out.println(size+" words were deleted successfully");
    }
}
