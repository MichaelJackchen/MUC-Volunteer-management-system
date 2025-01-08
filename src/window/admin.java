package window;



import Listener.ad_listener.activityRecord;
import Listener.ad_listener.managerListener;
import Listener.ad_listener.volunteerActivityListener;
import Listener.ad_listener.volunteerListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class admin extends JFrame {
	JFrame logintemp;       //login��frame
	JFrame ad_fr = new JFrame("admin");
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
	String[] name={"־Ը�","־Ը��","������","���¼"};
	/** ����������*/
	public admin(JFrame jl)
	{
		this.initComponents();
		this.logintemp = jl;
	}
	private void initComponents() {
		ad_fr.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

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

		ad_fr.setJMenuBar(myMenubar);
		//���������ڵ�λ��

		ad_fr.setMinimumSize(new Dimension(MIN_WIDTH,MIN_HIGHT));
		ad_fr.setSize(1200,900);
		ad_fr.setTitle("admin");
		ad_fr.setLocationRelativeTo(null);//����Ļ���м���ʾ
		//��Ӱ�ť���
		jbnButtons=new JButton[4];
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
		for(int i=0;i<10;i++)
		{
			if((i%3)==0)
				jplButton.add(new JLabel());
			else
				jplButton.add(jbnButtons[i/3],BorderLayout.CENTER);
		}
		jplButton.add(jbnButtons[3]);
		ad_fr.add(jplButton,BorderLayout.WEST);
		jplDisplay=new JPanel();
		jplDisplay.setLayout(new BorderLayout());
		jplDisplay.setBackground(new Color(230,230,230));
		ad_fr.add(jplDisplay,BorderLayout.CENTER);
		ad_fr.pack();
		ad_fr.setVisible(true);
		jbnButtons[0].addActionListener(new volunteerActivityListener(jplDisplay));
		jbnButtons[1].addActionListener(new volunteerListener(jplDisplay));
		jbnButtons[2].addActionListener(new managerListener(jplDisplay));
		jbnButtons[3].addActionListener(new activityRecord(jplDisplay));
		menuitemExit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				ad_fr.dispose();
				logintemp.setVisible(true);
			}
		});
	}

}
