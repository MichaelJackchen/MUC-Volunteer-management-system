package Listener.mn_listener;

import connect_database.leader_db;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.regex.Pattern;

public class mnActivityListener implements ActionListener {
    String mno =new String();
    JPanel jplDisplay;
    JInternalFrame jifShow;//显示志愿广场结果的面板
    JInternalFrame jifShow2;//显示志愿活动信息的面板
    JInternalFrame jifshow3;//显示活动记录
    JPanel jback;//返回面板
    JPanel japply;
    final DefaultTableModel JTableModel = new DefaultTableModel(100,12);	//定义表格模板
    final JScrollPane jspDisplay=new JScrollPane();	//滚动面板

    JLabel label1[];    //固定文本的label
    JLabel label2[];    //从数据库获取信息的label
    Font labelfont = new Font("宋体", 1, 24);
    Font labelfont2 = new Font("楷体",1,24);

    leader_db ldb;
    public mnActivityListener(JPanel jpl,String vno)
    {
        this.mno=vno;
        this.jplDisplay=jpl;
    }
    public void activityDetails(JInternalFrame showtemp,String vano) throws SQLException, ClassNotFoundException {
        Font fbutton = new Font("宋体", 1, 24);

        jplDisplay.removeAll();        //先清空
        jback = new JPanel();
        japply=new JPanel();
        jback.setBackground(new Color(3, 209, 245));
        japply.setBackground(new Color(3, 209, 245));
        JButton back = new JButton("返回");            //设置返回按钮，返回上一级
        JButton apply = new JButton("查看报名情况");           //设置报名按钮，点击自动添加报名记录
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
        jifShow2.setLayout(new GridLayout(7, 4, 10, 10));
        String labelname[] = new String[]{"活动编号", "活动名称", "所属部门", "负责人", "活动类型",
                "活动级别", "活动频率", "活动时长", "活动地点", "可参加人数",
                "活动时间", "报名时间"};
        int knum;//可参与人数
        //创建label
        label1 = new JLabel[labelname.length];
        label2 = new JLabel[labelname.length];
        try {

            ldb.searchResponsibleActivity(vano,mno);
            ldb.rs.next();
            for (int i = 0; i < label1.length; i++) {
                label1[i] = new JLabel(labelname[i]);
                label2[i] = new JLabel(ldb.rs.getString(i + 1));

                label1[i].setFont(labelfont);
                label2[i].setFont(labelfont2);
//            label2[i].setForeground(Color.CYAN);
                jifShow2.add(label1[i]);
                jifShow2.add(label2[i]);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        knum = Integer.parseInt(label2[9].getText());
        jifShow2.setBackground(new Color(3, 209, 245));
        jifShow2.add(jback);
        //网格布局补空
        jifShow2.add(new JLabel());
        jifShow2.add(new JLabel());
        jifShow2.add(japply);

        jifShow2.setVisible(true);
        jplDisplay.add(jifShow2);
        back.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jplDisplay.removeAll();
                allActivities();
            }
        });
        int finalKnum = knum;
        apply.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jplDisplay.removeAll();
                JPanel sp = new JPanel();
                sp.setBackground(new Color(250,250,240));
                JButton jbQuit=new JButton("返回上一级");
                JButton jbSure=new JButton("审批通过");
                JButton jbFinish=new JButton("审批结束");
                jbQuit.setFont(fbutton);
                jbSure.setFont(fbutton);
                jbFinish.setFont(fbutton);
                final JTextField pvno=new JTextField(12);
                JLabel advise=new JLabel("请输入志愿者学号");
                advise.setFont(new Font("华文行楷", 1, 32));
                advise.setForeground(Color.BLACK);
                sp.setLayout(new GridLayout(10,1));
                for(int i=0;i<10;i++)
                {
                    switch(i)
                    {
                        case 0:sp.add(advise,BorderLayout.CENTER);break;
                        case 1:sp.add(pvno,BorderLayout.CENTER);break;
                        case 7:sp.add(jbSure,BorderLayout.CENTER);break;
                        case 8:sp.add(jbFinish,BorderLayout.CENTER);break;
                        case 9:sp.add(jbQuit,BorderLayout.CENTER);break;
                        default:sp.add(new JLabel());
                    }
                }
                sp.setVisible(true);
                JTable myJTable;
                UIManager.put("InternalFrame.titleFont", new java.awt.Font("宋体", 0, 24));
                jifshow3 =  new JInternalFrame("活动详细信息", true, true, true);
                final JScrollPane jspDisplay=new JScrollPane();	//滚动面板
                jspDisplay.setBackground(Color.WHITE);
                jspDisplay.setVisible(true);

                JTable mySecondTable=new JTable(JTableModel);
                mySecondTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
                mySecondTable.setFillsViewportHeight(true);
                mySecondTable.setRowHeight(40);
                String[] name = {"志愿者学号","姓名","性别","年级","专业","活动状态"};
                JTableModel.setRowCount(0);	//表格模板的行数和列数清零
                JTableModel.setColumnCount(0);
                for(int i=0;i<name.length;i++)	//添加列名
                {
                    JTableModel.addColumn(name[i]);
                }
                ldb.searchAppliedVl(vano);
                try {
                    leader_db ldb2 = new leader_db();
                    while(ldb.rs.next()){
                        String[] value=new String[name.length];
                        String vno = ldb.rs.getString(4);
                        int aset = ldb.rs.getInt(6);
                        ldb2.searchVolunteers(vno);
                        ldb2.rs.next();
                        value[0]=vno;
                        value[1]=ldb2.rs.getString(2);
                        value[2]=ldb2.rs.getString(3);
                        value[3]=ldb2.rs.getString(7);
                        value[4]=ldb2.rs.getString(8);
                        if(aset==1)
                            value[5]="正在申请";
                        else if(aset==2)
                            value[5]="正在参加";
                        else
                            value[5]="已经结束";
                        JTableModel.addRow(value);

                    }
                } catch (SQLException | ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
                JTableModel.setRowCount(JTableModel.getRowCount()+10);
                JTableModel.setColumnCount(JTableModel.getColumnCount()+10);
                myJTable=new JTable(JTableModel);	//用表格模板初始化表格myJTable
                myJTable.setRowHeight(54);		//设置行高
                myJTable.setFillsViewportHeight(true);
                myJTable.setFont(new Font("Times New Roman",0,18));
                myJTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);//关闭自动调节列宽
                myJTable.setCellSelectionEnabled(true);			//允许选取单元格
                myJTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);	//允许多选
                myJTable.setFont(new Font("宋体", Font.PLAIN, 12));
                jspDisplay.add(myJTable);
                jspDisplay.setViewportView(myJTable);
                jifshow3.add(jspDisplay,BorderLayout.CENTER);
                jifshow3.add(sp,BorderLayout.WEST);
                jifshow3.setVisible(true);
                jplDisplay.add(jifshow3);
                //返回上一级监听
                jbQuit.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        jplDisplay.removeAll();
                        jplDisplay.add(jifShow2);
                    }
                });
                //审批结束按钮
                jbFinish.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        //空值响应
                        String vno = pvno.getText();
                        Pattern pattern=Pattern.compile("^[\\040]*");//其中‘\040是空格的八进制转义字符’
                        if(pattern.matcher(vno).matches()==true)//产品名称提示框
                        {
                            JOptionPane.showMessageDialog(jifshow3, "请输入志愿者编号","提示", 1);
                            return;
                        }
                        ldb.searchActivityStatus(vno,vano);
                        try {
                            //状态只有从正在参加->已经结束
                            if(ldb.rs.next() && ldb.rs.getInt(1)==2) {
                                ldb.alterActivityStatus(vno, vano, 3);
                                ldb.searchVolunteers(vno);
                                if(ldb.rs.next()){
                                    JOptionPane.showMessageDialog(jifshow3, "审批成功","Successful", 1);
                                }
                                else {
                                    JOptionPane.showMessageDialog(jifshow3, "审批失败","Error", 1);
                                }
                            }
                            else{
                                JOptionPane.showMessageDialog(jifshow3, "审批失败","Error", 1);
                            }
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                    }
                });
                //审批按钮
                jbSure.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        //空值响应
                        String vno = pvno.getText();
                        Pattern pattern=Pattern.compile("^[\\040]*");//其中‘\040是空格的八进制转义字符’
                        if(pattern.matcher(vno).matches()==true)//产品名称提示框
                        {
                            JOptionPane.showMessageDialog(jifshow3, "请输入志愿者编号","提示", 1);
                            return;
                        }
                        //先确定是否还有名额
                        ldb.searchVolunteersNum(vano);
                        try {
                            ldb.rs.next();
                            int num = ldb.rs.getInt(1);
                            ldb.searchActivityStatus(vno,vano);
                            //状态只有从正在申请->正在参加
                            if(ldb.rs.next() && ldb.rs.getInt(1)==1){
                                if(num< finalKnum){
                                    ldb.alterActivityStatus(vno,vano,2);
                                    ldb.searchVolunteers(vno);
                                    if(ldb.rs.next()){
                                        JOptionPane.showMessageDialog(jifshow3, "审批成功","Successful", 1);
                                    }
                                    else {
                                        JOptionPane.showMessageDialog(jifshow3, "审批失败","Error", 1);
                                    }
                                }
                                else
                                    JOptionPane.showMessageDialog(jifshow3, "当前活动人数已满","Error", 1);
                            }
                            else{
                                JOptionPane.showMessageDialog(jifshow3, "审批失败","Error", 1);
                            }


                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }

                    }
                });
            }
        });
    }
    public void allActivities(){
        JTable myJTable;
        jplDisplay.removeAll();
        try{
            //负责人与数据库连接
            ldb = new leader_db();
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

            ldb.searchResponsibleActivities(mno);
            String[] name = {"活动编号","活动名称","活动频率","所属部门","活动地点","活动人数","报名时间","活动时间","点击查看活动详情"};
            JTableModel.setRowCount(0);	//表格模板的行数和列数清零
            JTableModel.setColumnCount(0);
            for(int i=0;i<name.length;i++)	//添加列名
            {
                JTableModel.addColumn(name[i]);
            }
            ldb.rs.beforeFirst();
            while(ldb.rs.next())
            {
                String[] value=new String[name.length];
                value[0]=ldb.rs.getString(1);
                value[1]=ldb.rs.getString(2);
                value[2]=ldb.rs.getString(7);
                value[3]=ldb.rs.getString(3);
                value[4]=ldb.rs.getString(9);
                value[5]=ldb.rs.getString(10);
                value[6]=ldb.rs.getString(12);
                value[7]=ldb.rs.getString(11);
                value[8]="·";
                JTableModel.addRow(value);
            }
            ldb.rs.close();
            JTableModel.setRowCount(JTableModel.getRowCount()+10);
            JTableModel.setColumnCount(JTableModel.getColumnCount()+10);
            myJTable=new JTable(JTableModel);	//用表格模板初始化表格myJTable
            myJTable.setRowHeight(54);		//设置行高
            myJTable.setFillsViewportHeight(true);
            myJTable.setFont(new Font("Times New Roman",0,18));
            myJTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);//关闭自动调节列宽
            myJTable.setCellSelectionEnabled(true);			//允许选取单元格
            myJTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);	//允许多选
            myJTable.setFont(new Font("宋体", Font.PLAIN, 12));
            jspDisplay.add(myJTable);		//添加到显示面板上
            jspDisplay.setViewportView(myJTable);
            jifShow.add(jspDisplay,BorderLayout.CENTER);
            jifShow.setVisible(true);
            jplDisplay.add(jifShow);
            //给table加上鼠标事件监听，点击触发活动的详细信息
            myJTable.addMouseListener(new MouseAdapter(){
                public void mouseClicked(MouseEvent e){
                    int r= myJTable.getSelectedRow();
                    int c= myJTable.getSelectedColumn();
                    String row = (String) myFirstTable.getValueAt(r,c);
                    if(row==null)
                        return;
                    if(c==8){
                        String vano = (String) myJTable.getValueAt(r,0);
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
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        jplDisplay.removeAll();        //先清空
        allActivities();
        jplDisplay.setVisible(true);
    }
}
