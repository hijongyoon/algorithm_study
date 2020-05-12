package algorithm;
import jdk.dynalink.NoSuchDynamicMethodException;

import javax.naming.NoPermissionException;
import java.security.spec.RSAOtherPrimeInfo;
import java.util.*;
interface Color{
     int BLACK=0;
      int RED=1;
}
class rbNode<E extends Number> implements Color{//color 가 black 을 0으로 표시하고 red 를 1로 표시하겠다.
    protected E data;
    protected int color;
    protected rbNode<E>left;
    protected rbNode<E>right;
    protected rbNode<E>parent;
    public rbNode(E data){
        this.data=data; color=BLACK;
        left=null; right=null; parent=null;
    }
    public String toString(){ return data.toString(); }
}

class RedBlackTree<E extends Number> implements Color{
    protected rbNode<E> root;
    public RedBlackTree() { root = null; }
    protected RedBlackTree(rbNode<E> root) { this.root = root; }
    public RedBlackTree(E data, RedBlackTree<E> leftTree, RedBlackTree<E> rightTree) {//왼쪽 부트리랑 오른쪽 부트리를 하나의 root 노드에 결합.
        root = new rbNode<>(data);
        if (leftTree != null) root.left = leftTree.root;
        else root.left = null;
        if (rightTree != null) root.right = rightTree.root;
        else root.right = null;
    }
    public RedBlackTree<E> getLeftSubtree() {//root 노드 기준으로 왼쪽 subtree의 root 노드를 반환.
        if (root != null && root.left != null) return new RedBlackTree<>(root.left);
        else return null;
    }
    public RedBlackTree<E> getRightSubtree() {//root 노드 기준으로 오른쪽 subtree의 root 노드를 반환.
        if (root != null && root.right != null) return new RedBlackTree<>(root.right);
        else return null;
    }
    public rbNode<E> getRoot() { return root; }
    public void inorderTreeWalk(rbNode<E> x) {//inorder 순으로 순회한다면 왼쪽->가운데->오른쪽 순으로 방문한다.
        if (x != null) {
            inorderTreeWalk(x.left);
            System.out.print(x + " ");
            inorderTreeWalk(x.right);
        }
    }
    public void preorderTreeWalk(rbNode<E> x) {//preorder 순으로 순회한다면 가운데->왼쪽->오른쪽 순으로 방문한다.
        if (x != null) {
            System.out.print(x + " ");
            preorderTreeWalk(x.left);
            preorderTreeWalk(x.right);
        }
    }
    public void postorderTreeWalk(rbNode<E> x) {//postorder 순으로 순회한다면 왼쪽->오른쪽->가운데 순으로 방문한다.
        if (x != null) {
            postorderTreeWalk(x.left);
            postorderTreeWalk(x.right);
            System.out.print(x + " ");
        }
    }
    public void levelOrderTreeWalk() {//레벨 순으로 왼쪽에서 오른쪽으로 방문한다.
        Queue<rbNode<E>> que = new LinkedList<>();
        que.offer(root);
        rbNode<E> node;
        while (!que.isEmpty()) {//큐가 빌때 까지 진행.
            node = que.poll();
            if (node.color == BLACK) System.out.print("black-" + node + " ");
            else if (node.color == RED) System.out.print("Red-" + node + " ");
            //System.out.print(node+" ");
            if (node.left != null) que.offer(node.left);
            if (node.right != null)
                que.offer(node.right);//여기서는 else if 문을 사용해선 안된다. 두 개의 자식이 있을 경우엔 else if 문은 하나의 자식만 넣어준다.
        }
        System.out.println();
    }
    public rbNode<E> treeSearch(E data) {
        rbNode<E> x = root;
        while (x != null && data.intValue() != x.data.intValue()) {
            if (data.intValue() < x.data.intValue()) x = x.left;
            else x = x.right;
        }
        return x;
    }
    public rbNode<E> treeMinimum(rbNode<E> x) {
        if (x == null) x = root;
        while (x.left != null) x = x.left;
        return x;
    }
    public rbNode<E> treeMaximum(rbNode<E> x) {
        if (x == null) x = root;
        while (x.right != null) x = x.right;
        return x;
    }
    public void leftRotate(rbNode<E> x) {
        rbNode<E> y = x.right;
        x.right = y.left;//y의 왼쪽 자식을 x의 오른쪽 자식으로 연결.
        if (y.left != null) y.left.parent = x;//y의 오른쪽 자식의 부모를 x로 바꿈.
        y.parent = x.parent;//y의 부모가 x의 부모로 됨.
        if (x.parent == null) root = y;//x의 부모가 null 이라면 즉 x가 루트라면 y가 루트가 됨.
        else if (x == x.parent.left) x.parent.left = y;
        else x.parent.right = y;
        y.left = x;
        x.parent = y;
    }
    public void rightRotate(rbNode<E> x) {//leftRotate 와 대칭적.
        rbNode<E> y = x.left;
        x.left = y.right;
        if (y.right != null) y.right.parent = x;
        y.parent = x.parent;
        if (x.parent == null) root = y;
        else if (x == x.parent.right) x.parent.right = y;
        else x.parent.left = y;
        y.right = x;
        x.parent = y;
    }
    public void treeInsert(rbNode<E> node) {
        rbNode<E> y = null;
        rbNode<E> x = root;
        while (x != null) {//x가 null이 될 때 까지 반복하고 y는 그 뒤를 따라온다.
            y = x;
            if (node.data.intValue() < x.data.intValue()) x = x.left;//이 if 문은 x가 한칸 아래로 전진하는 것이다.
            else x = x.right;
        }
        node.parent = y;
        if (y == null) root = node;//원래 tree가 empty tree.
        else if (node.data.intValue() < y.data.intValue()) y.left = node;//새로운 노드를 왼쪽 자식에 삽입.
        else y.right = node;//새로운 노드를 오른쪽 자식에 삽입.
        node.left = null;
        node.right = null;
        node.color = RED;
        rbInsertFixup(node);
    }
    public void rbInsertFixup(rbNode<E> z) {
        rbNode<E> y = null;
        while (z.parent != null && z.parent.color == RED) {//z의 부모가 red 라면.
            if (z.parent.parent != null && z.parent == z.parent.parent.left) {//z의 부모가 z의 할아버지의 왼쪽 자식인 경우. case1,2,3 해당.
                y = z.parent.parent.right;//y는 삼촌노드.
                if (y != null && y.color == RED) {//삼촌이 red 인 경우. 즉 할아버지는 black 이라는 의미.
                    z.parent.color = BLACK;
                    y.color = BLACK;
                    z.parent.parent.color = RED;//삼촌과 부모를 black 으로 할아버지를 red 로 바꿈.
                    z = z.parent.parent;//할아버지 노드를 z로 바꿈.
                }//-> Case 1
                else {
                    if (z == z.parent.right) {//->Case 2
                        z = z.parent;
                        leftRotate(z);
                    }
                    z.parent.color = BLACK;//->Case 3
                    z.parent.parent.color = RED;
                    rightRotate(z.parent.parent);
                }
            } else {//case 4,5,6 해당. 완벽히 대칭적.
                if (z.parent.parent != null) y = z.parent.parent.left;//y는 삼촌노드.
                if (y != null && y.color == RED && z.parent.parent != null) {//삼촌이 red 인 경우. 즉 할아버지는 black 이라는 의미.
                    z.parent.color = BLACK;
                    y.color = BLACK;
                    z.parent.parent.color = RED;//삼촌과 부모를 black 으로 할아버지를 red 로 바꿈.
                    z = z.parent.parent;//할아버지 노드를 z로 바꿈.
                } //-> Case 4
                else if (z.parent.parent != null) {
                    if (z == z.parent.left) {//-> Case 5
                        z = z.parent;
                        rightRotate(z);
                    }
                    z.parent.color = BLACK;//-> Case 6
                    z.parent.parent.color = RED;
                    leftRotate(z.parent.parent);
                }
            }
        }
        root.color = BLACK;//루트는 무조건 black 이다.
    }
    public rbNode<E> treeSuccessor(rbNode<E> x) {
        if (x == null) x = root;
        if (x.right != null) return treeMinimum(x.right);
        rbNode<E> y = x.parent;
        while (y != null && x == y.right) {
            x = y;
            y = y.parent;
        }
        return y;
    }
    public rbNode<E> treeDelete(rbNode<E> node) {//삭제할 노드를 검색해서 찾은 후에 이 메소드를 호출 해야함.
        rbNode<E> y;
        rbNode<E> x;
        if (node.left == null || node.right == null) y = node;//자식 노드가 0개이거나 1개인 경우.
        else y = treeSuccessor(node);//자식노드가 2개인 경우.
        if (y.left != null) x = y.left;//이 if~else 문은 x가 y의 왼쪽 자식일수도 오른쪽 자식일수도 null 일수도 있음. 이 경우는 case 1,2,3 전부 해당.
        else x = y.right;//case 3인 경우에도 z의 successor 는 오른쪽이나 왼쪽의 자식 중 둘 중에 하나 또는 하나도 안가질 수 있기 때문이다.
        if (x != null) x.parent = y.parent;//y를 삭제할 것이므로 x의 부모는 y의 부모가 됨.
        if (y.parent == null) root = x;//y의 부모가 null 이라면 즉 y가 root 노드.(이 경우는 무조건 자식노드가 0게이너가 1개인 상황)
        else if (y == y.parent.left) y.parent.left = x;//y가 y의 부모가 왼쪽 자식이면 x도 y의 부모의 왼쪽자식이 된다.
        else y.parent.right = x;//y가 y의 부모의 오른쪽 자식이라면 x도 y의 부모의 오른쪽 자식이 된다.
        if (y != node) node.data = y.data;//z의 successor 인 y의 데이터를 z에 옮김.
        if (y.color == 0) rbDeleteFixup(x);//삭제될 노드가 red 이면 문제가 없지만 black 일 경우에는 문제가 생긴다. 왜면 bh가 바뀔 수 도 있기 때문.
        return y;
    }
    public void rbDeleteFixup(rbNode<E> x) {
        rbNode<E> w=null;
        while (x!=null&&x != root && x.color == BLACK) {
            if (x.parent!=null&&x == x.parent.left) {//case 1,2,3,4 해당.
                w = x.parent.right;//w는 x의 형제노드. w는 절대 null 이 될 수 없다. bh 조건 때문이다.
                if (w!=null&&w.color == RED) {//case 1- 형제노드가 red 인 경우.
                    w.color = BLACK;
                    x.parent.color = RED;
                    leftRotate(x.parent);
                    w = x.parent.right;
                }
                if (w!=null&&w.color==BLACK&&w.left.color == BLACK && w.right.color == BLACK) {//case 2- w 는 black, w의 자식들도 black 인 경우.
                    w.color = RED;
                    x = x.parent;//만약 case1 에서 넘어 왔다면 x의 부모는 red 이므로 while 문을 빠져 나가서 x가 black 이 되어서 종료.
                }
                else if(w!=null&&w.color==BLACK&&w.left.color==RED&&w.right.color==BLACK){//case 3- w는 black, w의 왼자식은 red, 오른자식은 black.
                    w.left.color=BLACK; w.color=RED;
                    rightRotate(w); w=x.parent.right;
                }
                if(w!=null&&w.color==BLACK&&w.right.color==RED){//case 4- w는 black. w의 오른쪽 자식이 red. case3 에서는 필연적으로 case 4로 넘어 올 수 밖에 없음.
                    w.color=x.parent.color; x.parent.color=BLACK;
                    w.right.color=BLACK; leftRotate(x.parent);
                    x=root;
                }
            }
            else if(x.parent!=null) {//case 5,6,7,8에 해당. 완벽히 대칭적.
                w = x.parent.left;//w는 x의 형제노드.
                if (w!=null&&w.color == RED) {
                    w.color = BLACK;
                    x.parent.color = RED;
                    rightRotate(x.parent);
                    w = x.parent.left;
                }
                if (w!=null&&w.color==BLACK&&w.right.color == BLACK && w.left.color == BLACK) {
                    w.color = RED;
                    x = x.parent;
                }
                else if(w!=null&&w.left.color==BLACK&&w.right.color==RED){
                    w.right.color=BLACK; w.color=RED;
                    leftRotate(w); w=x.parent.left;
                }
                if(w!=null&&w.color==BLACK&&w.left.color==RED){
                    w.color=x.parent.color; x.parent.color=BLACK;
                    w.left.color=BLACK; rightRotate(x.parent);
                    x=root;
                }
            }
        }
        if(x!=null) x.color=BLACK;
    }
}

public class TreeAlgorithm2 {
    public static void main(String[] args) {
        RedBlackTree<Integer> tree = new RedBlackTree<>();
        //int[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int[]arr={20,15,3,12,5,11,6,40,25,18};
        //int[]arr={10,9,8,7,6,5,4,3,2,1};
        for (int e : arr) tree.treeInsert(new rbNode<>(e));
        tree.levelOrderTreeWalk();
        tree.treeDelete(tree.treeSearch(40));
        tree.levelOrderTreeWalk();
    }
}
