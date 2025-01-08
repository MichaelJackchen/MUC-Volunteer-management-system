package Listener.vl_listener;

import connect_database.volunteer_db;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;

//我的志愿活动button监听
public class MyvolunteerListener implements ActionListener {
    String vno = new String();
    JPanel jplDisplay;
    JInternalFrame jifShow;//显示结果的面板
    JInternalFrame jifShow2;//显示志愿活动信息的面板
    JPanel jplButton;//状态类别的面板
    JPanel jback;//返回面板
    JButton jbQuit=new JButton("退出");
    JButton jbSure=new JButton("确定");			//设置按钮
    final JComboBox vtBox=new JComboBox();		//设置下拉菜单
    final DefaultTableModel JTableModel = new DefaultTableModel(100,12);	//定义表格模板
    final JScrollPane jspDisplay=new JScrollPane();	//滚动面板

    JLabel label1[];    //固定文本的label
    JLabel label2[];    //从数据库获取信息的label
    Font labelfont = new Font("宋体", 1, 24);
    Font labelfont2 = new Font("楷体",1,24);

    volunteer_db vdb;
    public MyvolunteerListener(JPanel jpl, String vno)
    {
        this.vno=vno;
        this.jplDisplay=jpl;
    }
    //显示具体活动的详细信息
    public void activityDetails(JInternalFrame showtemp,String vano){
        Font fbutton=new Font("宋体", 1, 24);

        jplDisplay.removeAll();		//先清空
        jback=new JPanel();
        jback.setBackground(new Color(3, 209, 245));
        JButton back=new JButton("返回");			//设置返回按钮，返回上一级
        back.setFont(fbutton);
        jback.add(back);
        jback.setVisible(true);


        //用于显示结果的面板
        UIManager.put("InternalFrame.titleFont", new java.awt.Font("宋体", 0, 24));
        jifShow2 = new JInternalFrame("活动详细信息", true, true, true);
        //网格式布局
        jifShow2.setLayout(new GridLayout(7,4,10,10));
        String labelname[] = new String[]{"活动编号","活动名称","所属部门","负责人","活动类型",
                "活动级别","活动频率","活动时长","活动地点","可参加人数",
                "活动时间","报名时间"};
        //创建label
        label1=new JLabel[labelname.length];
        label2=new JLabel[labelname.length];

        try {
            vdb.searchVolunteerActivity(vano);
            vdb.rs.next();
            for(int i=0;i<label1.length;i++) {
                label1[i] = new JLabel(labelname[i]);
                label2[i] = new JLabel(vdb.rs.getString(i+1));
                label1[i].setFont(labelfont);
                label2[i].setFont(labelfont2);
//            label2[i].setForeground(Color.CYAN);
                jifShow2.add(label1[i]);
                jifShow2.add(label2[i]);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        jifShow2.setBackground(new Color(3, 209, 245));
        jifShow2.add(jback);
        jifShow2.setVisible(true);
        jplDisplay.add(jifShow2);
        back.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                jplDisplay.removeAll();
                jplDisplay.add(showtemp);
            }
        });
    }
    public void allActivities(){
        Font fbutton = new Font("宋体", 1, 24);

        jplDisplay.removeAll();        //先清空
        //用于选择状态种类的面板
        jplButton = new JPanel();
        jplButton.setBackground(new Color(250, 250, 240));
        String[] vtName = {"正在申请", "正在参加", "已经结束"};
        vtBox.setFont(fbutton);                    //下拉菜单设置字体
        vtBox.setModel(new DefaultComboBoxModel(vtName));      //刷新下拉菜单的数据

        jbSure.setFont(fbutton);
        jbQuit.setFont(fbutton);

        JLabel advise = new JLabel("请选择");
        advise.setFont(new Font("华文行楷", 1, 32));
        advise.setForeground(Color.BLACK);
        jplButton.setLayout(new GridLayout(12, 1));        //设置志愿类别面板的布局，放在右侧
        //在类别面板上加入选择、下拉菜单、确定、退出
        for (int i = 0; i < 12; i++) {
            switch (i) {
                case 0:
                    jplButton.add(advise, BorderLayout.CENTER);
                    break;
                case 1:
                    jplButton.add(vtBox, BorderLayout.CENTER);
                    break;
                case 10:
                    jplButton.add(jbSure, BorderLayout.CENTER);
                    break;
                case 11:
                    jplButton.add(jbQuit, BorderLayout.CENTER);
                    break;
                default:
                    jplButton.add(new JLabel());
            }
        }
        jplButton.setVisible(true);
        String[] infoname = {"活动编号", "活动名称", "所属部门", "负责人", "活动类型",
                "活动级别", "活动频率", "活动时长", "活动地点", "可参加人数",
                "活动时间", "报名时间"};

        //用于显示结果的面板
        UIManager.put("InternalFrame.titleFont", new java.awt.Font("宋体", 0, 24));
        jifShow = new JInternalFrame("志愿活动", true, true, true);

        final JScrollPane jspDisplay = new JScrollPane();    //滚动面板
        jspDisplay.setBackground(Color.WHITE);
        jspDisplay.setVisible(true);

        JTable myFirstTable = new JTable(JTableModel);        //定义表格
        myFirstTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        myFirstTable.setFillsViewportHeight(true);
        myFirstTable.setRowHeight(40);
        jspDisplay.add(myFirstTable);        //添加到显示面板上
        jspDisplay.setViewportView(myFirstTable);
        jifShow.add(jplButton, BorderLayout.EAST);
        jifShow.add(jspDisplay, BorderLayout.CENTER);
        jifShow.setVisible(true);
        jplDisplay.add(jifShow);
        //给table加上鼠标事件监听，点击触发活动的详细信息
        myFirstTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int r = myFirstTable.getSelectedRow();
                int c = myFirstTable.getSelectedColumn();
                String row = (String) myFirstTable.getValueAt(r,c);
                if(row==null)
                    return;
                if (c==8) {
                    String vano = (String) myFirstTable.getValueAt(r,0);
                    activityDetails(jifShow,vano);
                }
            }
        });
    }
    public void actionPerformed(ActionEvent e) {
        jplDisplay.removeAll();        //先清空
        allActivities();
        jplDisplay.setVisible(true);
        //添加监听器
        jbQuit.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                jifShow.dispose();
            }
        });
        //查询所有志愿活动并显示
        jbSure.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                JTable myJTable;
                String vtType=vtBox.getSelectedItem().toString();
                try{
                    //志愿者与数据库连接
                    vdb = new volunteer_db();
                    //在活动记录表查到活动编号
                    if(vtType.equals("正在申请")){
                        vdb.searchActivitiesStatus(1,vno);
                    }
                    else if(vtType.equals("正在参加")) {
                        vdb.searchActivitiesStatus(2,vno);
                    }
                    else{
                        vdb.searchActivitiesStatus(3,vno);
                    }
                    //设置表格基本信息
                    String[] name = {"活动编号","活动名称","活动频率","所属部门","活动地点","活动人数","报名时间","活动时间","点击查看活动详细"};


                    JTableModel.setRowCount(0);	//表格模板的行数和列数清零
                    JTableModel.setColumnCount(0);
                    //设置表单头的字体
                    UIManager.put("TableHeader.font", new java.awt.Font("宋体", 0,18));
                    for(int i=0;i<name.length;i++)	//添加列名
                    {
                        JTableModel.addColumn(name[i]);
                    }
                    //通过查到的活动编号再到志愿活动表中查具体信息
                    ArrayList<String> vno = new ArrayList<String> ();
                    while(vdb.rs.next()){
                        vno.add(vdb.rs.getString(1));
                    }
                    for(int i=0;i<vno.size();i++){
                        vdb.searchVolunteerNo(vno.get(i));
                        while(vdb.rs.next())		//得到各行的属性值
                        {
                            String[] value=new String[name.length];
                            value[0]=vdb.rs.getString(1);
                            value[1]=vdb.rs.getString(2);
                            value[2]=vdb.rs.getString(7);
                            value[3]=vdb.rs.getString(3);
                            value[4]=vdb.rs.getString(9);
                            value[5]=vdb.rs.getString(10);
                            value[6]=vdb.rs.getString(12);
                            value[7]=vdb.rs.getString(11);
                            value[8]="·";
                            JTableModel.addRow(value);
                        }
                    }

                    vdb.rs.close();
                    JTableModel.setRowCount(JTableModel.getRowCount()+10);
                    JTableModel.setColumnCount(JTableModel.getColumnCount()+10);
                    myJTable=new JTable(JTableModel);	//用表格模板初始化表格myJTable
                    myJTable.setRowHeight(54);		//设置行高
                    myJTable.setFillsViewportHeight(true);
                    myJTable.setFont(new Font("Times New Roman",0,18));
                    myJTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);//关闭自动调节列宽
                    myJTable.setCellSelectionEnabled(true);			//允许选取单元格
                    myJTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);	//允许多选
                    jspDisplay.add(myJTable);		//添加到显示面板上
                    jspDisplay.setViewportView(myJTable);
                } catch (ClassNotFoundException e1) {
                    JOptionPane.showMessageDialog(jspDisplay, "数据库连接异常","错误",0);
                }catch(SQLException wrong){
                    JOptionPane.showMessageDialog(jspDisplay, "数据库访问异常","错误",0);
                }
            }
        });
    }
}
