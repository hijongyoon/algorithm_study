package realAlgorithm;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

// 자바 GUI final Project.

class FlickeringImage implements Runnable{
    long delay;
    JLabel j1,j2,j3;
    JPanel page2;
    public FlickeringImage(long delay,JPanel j) {
        this.delay=delay;
        j1=new JLabel();
        j2=new JLabel();
        j3=new JLabel();
        page2=j;
    }
    public void run() {
        int n1=1,n2=0,n3=0;
        while(true) {
            if(n1==1) {
                j1.setIcon(RockScissorsPaper.images[3]);
                j1.setBounds(20, 20, RockScissorsPaper.images[3].getIconWidth(), RockScissorsPaper.images[3].getIconHeight());
                page2.add(j1);
                j2.setIcon(RockScissorsPaper.images[1]);
                j2.setBounds(600, 20, RockScissorsPaper.images[1].getIconWidth(), RockScissorsPaper.images[1].getIconHeight());
                page2.add(j2);
                j3.setIcon(RockScissorsPaper.images[2]);
                j3.setBounds(1150, 20, RockScissorsPaper.images[2].getIconWidth(), RockScissorsPaper.images[2].getIconHeight());
                page2.add(j3);
                n1=0;n2=1;
            }
            else if(n2==1) {
                j1.setIcon(RockScissorsPaper.images[0]);
                j1.setBounds(20, 20, RockScissorsPaper.images[0].getIconWidth(), RockScissorsPaper.images[0].getIconHeight());
                page2.add(j1);
                j2.setIcon(RockScissorsPaper.images[4]);
                j2.setBounds(600, 20, RockScissorsPaper.images[4].getIconWidth(), RockScissorsPaper.images[4].getIconHeight());
                page2.add(j2);
                n2=0;n3=1;
            }
            else if(n3==1) {
                j2.setIcon(RockScissorsPaper.images[1]);
                j2.setBounds(600, 20, RockScissorsPaper.images[1].getIconWidth(), RockScissorsPaper.images[1].getIconHeight());
                page2.add(j2);
                j3.setIcon(RockScissorsPaper.images[5]);
                j3.setBounds(1150, 20, RockScissorsPaper.images[5].getIconWidth(), RockScissorsPaper.images[5].getIconHeight());
                page2.add(j3);
                n3=0;n1=1;
            }
            try {
                Thread.sleep(delay);
            }catch(InterruptedException e) {
                return;
            }
        }
    }
}

public class RockScissorsPaper extends JFrame{
    private static Container c;
    public static ImageIcon []images=new ImageIcon[6];
    public static JPanel page1=new JPanel() {
        Image background=new ImageIcon("games/background.jpg").getImage();
        public void paint(Graphics g) {
            g.drawImage(background, 0, 0, null);
        }
    };
    public static JPanel page2=new JPanel();
    public static JButton startButton=new JButton("하나빼기 게임 한판 하실래요?");
    public RockScissorsPaper() {
        setDefault();
        makePage1();
        makePage2();
        makeImage();
        startButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {};
            public void mouseExited(MouseEvent e) {};
            public void mousePressed(MouseEvent e) {
                page1.setVisible(false);
                page2.setVisible(true);
                FlickeringImage f1=new FlickeringImage(200,page2);
                Thread th=new Thread(f1);
                th.start();
            };
        });
    }
    public void setDefault() {
        setTitle("빙빙고 하나빼기");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        c=getContentPane();
        c.setLayout(null);
        setSize(1366,768);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    public void makePage1() {
        startButton.setBounds(500, 300, 300, 100);
        startButton.setFont(new Font("고딕",Font.HANGING_BASELINE,18));
        page1.setBounds(0,0,1366,768);
        page1.setLayout(null);
        add(page1);
        page1.add(startButton);
        page2.setVisible(true);
    }
    public void makePage2() {
        page2.setBounds(0,0,1366,768);
        page2.setLayout(null);
        page2.setBackground(Color.WHITE);
        add(page2);
        page2.setVisible(false);
    }
    public void makeImage() {
        images[0]=new ImageIcon("games/rock.png");
        images[1]=new ImageIcon("games/scissors.png");
        images[2]=new ImageIcon("games/paper.png");
        images[3]=new ImageIcon("games/red rock.png");
        images[4]=new ImageIcon("games/red scissors.png");
        images[5]=new ImageIcon("games/red paper.png");
    }
    public static void main(String[] args) {
        new RockScissorsPaper();
    }
}