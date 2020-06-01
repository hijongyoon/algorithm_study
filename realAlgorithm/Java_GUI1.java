package realAlgorithm;
//알고리즘과는 관계없는 GUI 프로그래밍.
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
public class Java_GUI1 extends JFrame{
    public static Container c;
    public static JCheckBox[]check;
    public static JTextField[]text;
    public static int []number= {-1,-1,-1,-1,-1,-1,-1};
    public static int indexOfNumber=0;
    public Java_GUI1() {
        setTitle("Money Changer with CheckBox");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        c=getContentPane();
        c.setBackground(Color.pink);

        c.setLayout(null);
        JLabel l1=new JLabel("금액");
        l1.setBounds(50, 40, 30, 30);
        JTextField t1=new JTextField();
        t1.setBounds(90, 40, 130, 25);
        JButton b1=new JButton("계산");
        b1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int number=Integer.parseInt(t1.getText());
                calculate(number);
            }
        });
        b1.setBounds(230, 40, 80, 25);
        c.add(l1);c.add(t1); c.add(b1);
        check=new JCheckBox[7];
        for(int i=0;i<7;i++) check[i]=new JCheckBox();
        String[]words= {"오만원","만원 ","천원","500원","100원","50원","10원","1원"};
        makeContents(words);

        setSize(350,600);
        setVisible(true);
    }
    public static void makeContents(String[]words) {
        int xLabel=60,yLabel=75;
        int xText=110,yText=75;
        int xCheck=230,yCheck=75;
        text=new JTextField[8];
        for(int i=0;i<7;i++) {
            JLabel j1=new JLabel(words[i]);
            j1.setBounds(xLabel, yLabel, 40, 30);
            text[i]=new JTextField();
            text[i].setBounds(xText, yText, 100, 25);
            check[i].setBounds(xCheck, yCheck,18,18);
            check[i].setBackground(Color.pink);
            isCheck(check[i],i);
            c.add(j1);c.add(text[i]);c.add(check[i]);
            yLabel+=25; yText+=25; yCheck+=25;
        }
        JLabel j1=new JLabel(words[7]);
        j1.setBounds(xLabel, yLabel, 40, 30);
        text[7]=new JTextField();
        text[7].setBounds(xText, yText, 100, 25);
        c.add(j1);c.add(text[7]);
    }
    public static void isCheck(JCheckBox check,int num) {
        check.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange()==ItemEvent.SELECTED) number[num]=num;
            }
        });
    }
    public static void calculate(int num) {
        int data;
        for(int i=0;i<7;i++) {
            if(number[i]==0) num=preCalculate(num,50000,i);//5만원 체크한 경우
            else if(number[i]==1) num=preCalculate(num,10000,i);//1만원 체크한 경우
            else if(number[i]==2)num=preCalculate(num,1000,i);//천원 체크한 경우
            else if(number[i]==3)num=preCalculate(num,500,i);//5백원 체크한 경우
            else if(number[i]==4)num=preCalculate(num,100,i);//백원 체크한 경우
            else if(number[i]==5)num=preCalculate(num,50,i);//50원 체크한 경우
            else if(number[i]==6)num=preCalculate(num,10,i);//10원 체크한 경우
            else {
                text[i].setText("0");
                text[i].setHorizontalAlignment(JTextField.CENTER);
            }
        }
        if(num!=0) text[7].setText(String.valueOf(num));
        else if(num==0) text[7].setText("0");
        text[7].setHorizontalAlignment(JTextField.CENTER);
    }
    public static int preCalculate(int num1,int num2,int i) {
        text[i].setText(String.valueOf(num1/num2));
        text[i].setHorizontalAlignment(JTextField.CENTER);
        return num1-num2*(num1/num2);
    }
    public static void main(String[] args) {
        new Java_GUI1();
    }
}
