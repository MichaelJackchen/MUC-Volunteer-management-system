package window;

import Listener.mn_listener.addActivityListener;
import Listener.mn_listener.alterActivityListener;
import Listener.mn_listener.mnActivityListener;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class manager extends JFrame {
    String mno = new String();
    JFrame logintemp;       //login��frame
    JFrame mn_fr = new JFrame("manager");
    final int MIN_WIDTH=1200;
    final int MIN_HIGHT=800;
    Point point=new Point(0,0);		//���ڵĵ�ǰ����
    JMenuBar myMenubar;
    JMenu menuFile,menuEdit;
    JMenuItem menuitemExit, menuitemAbout;

    Icon iconTitle;
    JButton jbnButtons[];
    JPanel jplTitle,jplDisplay,jplButton;


    Font f12 = new Font("Times New Roman", 0, 24);
    Font f121 = new Font("Times New Roma28n", 1,24);
    Font fbutton = new Font("����", 1, 24);
    String[] name={"�Ҹ���Ļ","���־Ը�","�޸�־Ը���Ϣ"};
    public manager(String manumber,JFrame jl)
    {
        this.mno = manumber;
        this.initComponents();
        this.logintemp = jl;
    }
    private void initComponents()
    {
        mn_fr.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        //��Ӳ˵���
        menuFile=new JMenu("File");
        menuFile.setFont(f121);

        menuitemExit=new JMenuItem("Exit");	//�Ӳ˵���
        menuitemExit.setFont(f12);
        menuFile.add(menuitemExit);			//file�����exit

        menuEdit=new JMenu("Help");
        menuEdit.setFont(f121);

        menuitemAbout=new JMenuItem("About...");
        menuitemAbout.setFont(f12);
        menuEdit.add(menuitemAbout);		//help�����about
        //ԭ�������ӵ��м������
        myMenubar=new JMenuBar();
        myMenubar.add(menuFile);
        myMenubar.add(menuEdit);
        mn_fr.setJMenuBar(myMenubar);

        //���������ڵ�λ��

        mn_fr.setMinimumSize(new Dimension(MIN_WIDTH,MIN_HIGHT));
        mn_fr.setSize(1200,900);
        mn_fr.setTitle("manager");
        mn_fr.setLocationRelativeTo(null);//����Ļ���м���ʾ



        //��Ӱ�ť���
        jbnButtons=new JButton[3];
        for(int i=0;i<jbnButtons.length;i++)
        {
            // set the style of each Jbutton label
            jbnButtons[i]=new JButton(name[i]);
            jbnButtons[i].setFont(fbutton);
            jbnButtons[i].setForeground(Color.BLACK);
        }
        jplButton=new JPanel();		//create the panel to store Button
        jplButton.setBackground(new Color(250,250,240));
        jplButton.setLayout(new GridLayout(9,1));
        for(int i=0;i<8;i++)
        {
            if((i%3)==0)
                jplButton.add(new JLabel());
            else
                jplButton.add(jbnButtons[i/3],BorderLayout.CENTER);
        }
        mn_fr.add(jplButton,BorderLayout.WEST);

        //�����ʾ���

        jplDisplay=new JPanel();
        jplDisplay.setLayout(new BorderLayout());
        jplDisplay.setBackground(new Color(230,230,230));
        mn_fr.add(jplDisplay,BorderLayout.CENTER);
        mn_fr.pack();
        mn_fr.setVisible(true);
        //Ϊ�����Ӽ�����

        jbnButtons[0].addActionListener(new mnActivityListener(jplDisplay,mno));
        jbnButtons[1].addActionListener(new addActivityListener(jplDisplay,mno));
        jbnButtons[2].addActionListener(new alterActivityListener(jplDisplay,mno));
        menuitemExit.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                mn_fr.dispose();
                logintemp.setVisible(true);
            }
        });

    }
}

