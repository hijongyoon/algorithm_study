package realAlgorithm;
//알고리즘과는 관계없는 GUI 프로그래밍.
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

class CalcDialog extends JDialog{
    public CalcDialog(JFrame frame,String title) {
        super(frame,title);
        setLayout(null);
        JLabel j1=new JLabel("두 수를 더합니다.");
        j1.setBounds(60, 10, 100, 30);
        JTextField t1=new JTextField();
        t1.setBounds(55, 35, 105, 25);
        JTextField t2=new JTextField();
        t2.setBounds(55, 65, 105, 25);
        JButton b1=new JButton("Add");
        b1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int num1=Integer.parseInt(t1.getText());
                int num2=Integer.parseInt(t2.getText());
                Java_GUI2.j1.setText(String.valueOf(num1+num2));
                t1.setText("");
                t2.setText("");
                setVisible(false);
            }
        });
        b1.setBounds(60, 95, 90, 30);
        add(j1);add(t1);add(t2);add(b1);
        setSize(230,230);
    }
}

public class Java_GUI2 extends JFrame{
    public static JLabel j1;
    public Java_GUI2() {
        setTitle("다이얼로그 만들기");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container c=getContentPane();
        c.setLayout(new FlowLayout());
        JButton b1=new JButton("calculate");
        CalcDialog dialog=new CalcDialog(this,"Calculation Dialog");
        j1=new JLabel("계산 결과 출력");
        j1.setOpaque(true);//JLabel 배경색 설정
        j1.setBackground(Color.pink);
        b1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dialog.setVisible(true);//다이얼 로그 출력
            }
        });
        c.add(b1);c.add(j1);
        setSize(350,300);
        setVisible(true);
    }
    public static void main(String[] args) {
        new Java_GUI2();
    }
}