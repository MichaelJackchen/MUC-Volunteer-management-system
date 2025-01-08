package Listener.vl_listener;

import connect_database.volunteer_db;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

//志愿活动button监听
public class ActivityListener implements ActionListener {
    String vno =new String();
    JPanel jplDisplay;
    JInternalFrame jifShow;//显示志愿广场结果的面板
    JInternalFrame jifShow2;//显示志愿活动信息的面板
    JPanel jplButton;//选择志愿类别的面板
    JPanel jback;//返回面板
    JPanel japply;//报名面板

    JButton jbQuit=new JButton("退出");
    JButton jbSure=new JButton("确定");			//设置按钮
    final JComboBox vtBox=new JComboBox();		//设置下拉菜单
    final DefaultTableModel JTableModel = new DefaultTableModel(100,12);	//定义表格模板
    final JScrollPane jspDisplay=new JScrollPane();	//滚动面板

    JLabel label1[];    //固定文本的label
    JLabel label2[];    //从数据库获取信息的label
    Font labelfont = new Font("宋体", 1, 24);
    Font labelfont2 = new Font("楷体",1,24);
    public ActivityListener(JPanel jpl,String vno)
    {
        this.vno=vno;
        this.jplDisplay=jpl;
    }
    public void activityDetails(JInternalFrame showtemp,String vano) throws SQLException, ClassNotFoundException {
        Font fbutton=new Font("宋体", 1, 24);

        jplDisplay.removeAll();		//先清空
        jback=new JPanel();
        japply=new JPanel();
        jback.setBackground(new Color(3, 209, 245));
        japply.setBackground(new Color(3, 209, 245));
        JButton back=new JButton("返回");			//设置返回按钮，返回上一级
        JButton apply=new JButton("报名");           //设置报名按钮，点击自动添加报名记录
        back.setFont(fbutton);
        apply.setFont(fbutton);
        jback.add(back);
        jback.setVisible(true);
        japply.add(apply);
        japply.setVisible(true);


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
        volunteer_db vt = new volunteer_db();
        try {

            vt.searchVolunteerActivity(vano);
            vt.rs.next();
            for(int i=0;i<label1.length;i++) {
                label1[i] = new JLabel(labelname[i]);
                label2[i] = new JLabel(vt.rs.getString(i+1));
                label1[i].setFont(labelfont);
                label2[i].setFont(labelfont2);
//            label2[i].setForeground(Color.CYAN);
                jifShow2.add(label1[i]);
                jifShow2.add(label2[i]);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        int knum = Integer.parseInt(label2[9].getText());
//        JLabel vname = new JLabel("NAME:");
//        JLabel vlph = new JLabel("PHONE:");
//        JTextField vntxt = new JTextField();
//        jifShow.add(vname);
//        jifShow.add(vntxt);
//        jifShow.add(vlph);
        jifShow2.setBackground(new Color(3, 209, 245));
        jifShow2.add(jback);
        //网格布局补空
        jifShow2.add(new JLabel());
        jifShow2.add(new JLabel());

        jifShow2.add(japply);
        jifShow2.setVisible(true);
        jplDisplay.add(jifShow2);
//        jplDisplay.remove(jifShow);
//        添加监听器
        //返回上一级
        back.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                jplDisplay.removeAll();
                jplDisplay.add(showtemp);
            }
        });
        apply.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame perform = new JFrame("提示");
                perform.setSize(400, 200);
                perform.setLocationRelativeTo(null);
                JLabel ok = new JLabel("报名成功！");
                JLabel no1 = new JLabel("报名失败!您已申请参加该活动！");
                JLabel no2 = new JLabel("报名失败!该活动报名时间已过！");
                JLabel no3 = new JLabel("报名失败!该活动报名人数已满！");
                ok.setFont(labelfont);
                no1.setFont(labelfont);
                no2.setFont(labelfont);
                no3.setFont(labelfont);
                String aedate = label2[10].getText();       //活动时间
                String asdate = label2[11].getText();
                //解析日期
                Date nowdate = new Date();
                SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
                String snowdate = ft.format(nowdate);
                boolean timeright = false;
                try {
                    Date d = ft.parse(snowdate);
                    Date d1 = ft.parse(asdate);
                    Date d2 = ft.parse(aedate);

                    if(d1.before(d) && d.before(d2))
                        timeright = true;
                    else
                        timeright = false;
                } catch (ParseException ex) {
                    ex.printStackTrace();
                }
                //查询报名人数
                int num=0;
                try{
                    vt.searchVolunteersNum(vano);
                    vt.rs.next();
                    num = vt.rs.getInt(1);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

//                System.out.println(asdate+aedate+snowdate);
                //数据库操作

                try {
                    vt.searchVolunteerTranscibe(vno,vano);

                    int vaStatus = 0;
                    if(vt.rs.next()) {
                        vaStatus = vt.rs.getInt(1);
                    }

                    //正在申请,参加,或已经记录参加结束
                    if(vaStatus==1 || vaStatus==2 ||vaStatus==3){
                        perform.add(no1);
                    }
                    //判断当前时间是否在报名时间-活动时间之内
                    else if(!timeright)
                    {
                        perform.add(no2);
                    }
                    //判断报名人数
                    else if(num>=knum)
                    {
                        perform.add(no3);
                    }
                    //向活动记录表中添加申请记录
                    else
                    {
                        vt.executeUpdate("call Activities_insert('"+vano+"','"+vno+"',1)");
                        perform.add(ok);
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

//              perform.add(ok);
                perform.setVisible(true);
            }
        });
    }
    public void allActivities(){
        Font fbutton=new Font("宋体", 1, 24);

        jplDisplay.removeAll();
        //用于选择志愿种类的面板
        jplButton=new JPanel();
        jplButton.setBackground(new Color(250,250,240));
        String[] vtName={"全校活动","本院活动"};
        vtBox.setFont(fbutton);                    //下拉菜单设置字体
        vtBox.setModel(new DefaultComboBoxModel(vtName));      //刷新下拉菜单的数据

        jbSure.setFont(fbutton);

        jbQuit.setFont(fbutton);

        JLabel advise=new JLabel("请选择");
        advise.setFont(new Font("华文行楷", 1, 32));
        advise.setForeground(Color.BLACK);
        jplButton.setLayout(new GridLayout(12,1));		//设置志愿类别面板的布局，放在右侧
        //在类别面板上加入选择、下拉菜单、确定、退出
        for(int i=0;i<12;i++)
        {
            switch(i)
            {
                case 0:jplButton.add(advise,BorderLayout.CENTER);break;
                case 1:jplButton.add( vtBox,BorderLayout.CENTER);break;
                case 10:jplButton.add(jbSure,BorderLayout.CENTER);break;
                case 11:jplButton.add(jbQuit,BorderLayout.CENTER);break;
                default:jplButton.add(new JLabel());
            }
        }
        jplButton.setVisible(true);
        String[] infoname = {"活动编号","活动名称","所属部门","负责人","活动类型",
                "活动级别","活动频率","活动时长","活动地点","可参加人数",
                "活动时间","报名时间"};
        //用于显示结果的面板
        UIManager.put("InternalFrame.titleFont", new java.awt.Font("宋体", 0, 24));
        jifShow=new JInternalFrame("志愿活动",true,true,true);

        final JScrollPane jspDisplay=new JScrollPane();	//滚动面板
        jspDisplay.setBackground(Color.WHITE);
        jspDisplay.setVisible(true);

        JTable myFirstTable=new JTable(JTableModel);		//定义表格
        myFirstTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        myFirstTable.setFillsViewportHeight(true);
        myFirstTable.setRowHeight(40);

        jspDisplay.add(myFirstTable);		//添加到显示面板上
        jspDisplay.setViewportView(myFirstTable);
        jifShow.add(jplButton,BorderLayout.EAST);
        jifShow.add(jspDisplay,BorderLayout.CENTER);
        jifShow.setVisible(true);
        jplDisplay.add(jifShow);
        //给table加上鼠标事件监听，点击触发活动的详细信息
        myFirstTable.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e){
                int r= myFirstTable.getSelectedRow();
                int c= myFirstTable.getSelectedColumn();
                String row = (String) myFirstTable.getValueAt(r,c);
                if(row==null)
                    return;
                if(c==8){
                    String vano = (String) myFirstTable.getValueAt(r,0);
                    try {
                        activityDetails(jifShow,vano);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    } catch (ClassNotFoundException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
    }
    public void actionPerformed(ActionEvent e)
    {
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
                    volunteer_db vdb = new volunteer_db();
                    if(vtType.equals("全校活动")){
                        vdb.searchVolunteerType("校级");
                    }
                    else {
                        vdb.searchMyxyActivities(vno);

                    }
                    ResultSetMetaData rsmd=vdb.rs.getMetaData();	//创建结果集对象
                    String[] name = {"活动编号","活动名称","活动频率","所属部门","活动地点","活动人数","报名时间","活动时间","点击查看活动详细"};
                    JTableModel.setRowCount(0);	//表格模板的行数和列数清零
                    JTableModel.setColumnCount(0);
                    //设置表单头的字体
                    UIManager.put("TableHeader.font", new java.awt.Font("宋体", 0,18));
                    for(int i=0;i<name.length;i++)	//添加列名
                    {
                        JTableModel.addColumn(name[i]);
                    }
                    vdb.rs.beforeFirst();
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
                    vdb.rs.close();
                    JTableModel.setRowCount(JTableModel.getRowCount()+10);
                    JTableModel.setColumnCount(JTableModel.getColumnCount()+10);
                    myJTable=new JTable(JTableModel);	//用表格模板初始化表格myJTable
//                    myJTable.setEnabled(true);		//设置表格能被编辑
                    myJTable.setRowHeight(54);		//设置行高
                    myJTable.setRowMargin(100);
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
