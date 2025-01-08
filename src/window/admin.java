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
	JFrame logintemp;       //login的frame
	JFrame ad_fr = new JFrame("admin");
	final int MIN_WIDTH=1200;
	final int MIN_HIGHT=800;
	Point point=new Point(0,0);		//窗口的当前坐标
	JMenuBar myMenubar;
	JMenu menuFile,menuEdit;
	JMenuItem menuitemExit, menuitemAbout;

	Icon iconTitle;
	JButton jbnButtons[];
	JPanel jplTitle,jplDisplay,jplButton;

	Font f12 = new Font("Times New Roman", 0, 24);
	Font f121 = new Font("Times New Roma28n", 1,24);
	Font fbutton = new Font("宋体", 1, 24);
	String[] name={"志愿活动","志愿者","负责人","活动记录"};
	/** 创建主窗口*/
	public admin(JFrame jl)
	{
		this.initComponents();
		this.logintemp = jl;
	}
	private void initComponents() {
		ad_fr.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		//添加菜单栏
		menuFile=new JMenu("File");
		menuFile.setFont(f121);

		menuitemExit=new JMenuItem("Exit");	//子菜单项
		menuitemExit.setFont(f12);
		menuFile.add(menuitemExit);			//file下添加exit

		menuEdit=new JMenu("Help");
		menuEdit.setFont(f121);

		menuitemAbout=new JMenuItem("About...");
		menuitemAbout.setFont(f12);
		menuEdit.add(menuitemAbout);		//help下添加about
		//原子组件添加到中间层容器
		myMenubar=new JMenuBar();
		myMenubar.add(menuFile);
		myMenubar.add(menuEdit);

		ad_fr.setJMenuBar(myMenubar);
		//设置主窗口的位置

		ad_fr.setMinimumSize(new Dimension(MIN_WIDTH,MIN_HIGHT));
		ad_fr.setSize(1200,900);
		ad_fr.setTitle("admin");
		ad_fr.setLocationRelativeTo(null);//在屏幕正中间显示
		//添加按钮面板
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
