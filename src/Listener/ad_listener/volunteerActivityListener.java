package Listener.ad_listener;

import connect_database.my_db;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

public class volunteerActivityListener implements ActionListener {
    JPanel jplDisplay;
    JInternalFrame jifShow;//显示志愿广场结果的面板
    JInternalFrame jifShow2;//显示志愿活动信息的面板
    JPanel jback;//返回面板
    JPanel japply;//报名面板

    final DefaultTableModel JTableModel = new DefaultTableModel(100,12);	//定义表格模板
    final JScrollPane jspDisplay=new JScrollPane();	//滚动面板

    JLabel label1[];    //固定文本的label
    JTextField textField2[];     //从数据库获取信息
    Font labelfont = new Font("宋体", 1, 24);
    Font labelfont2 = new Font("楷体",1,24);

    my_db md;
    public volunteerActivityListener(JPanel jpl)
    {
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
        JButton apply=new JButton("修改");           //设置报名按钮，点击自动添加报名记录
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
        String tablename[] = new String[]{"Vano","Vaname","Vasec","Vamno","Vatype",
                "Valev","Vafre","Vadura","Vapl","Vasnum",
                "Vatime","Vregtime"};
        //创建label
        label1=new JLabel[labelname.length];
        textField2=new JTextField[labelname.length];
        try {
            md.searchVolunteerActivity(vano);
            md.rs.next();
            for(int i=0;i<label1.length;i++) {
                label1[i] = new JLabel(labelname[i]);
                textField2[i] = new JTextField();
                textField2[i].setText(md.rs.getString(i + 1));
                label1[i].setFont(labelfont);
                textField2[i].setFont(labelfont2);
                jifShow2.add(label1[i]);
                jifShow2.add(textField2[i]);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        jifShow2.setBackground(new Color(3, 209, 245));
        jifShow2.add(jback);
        //网格布局补空
        jifShow2.add(new JLabel());
        jifShow2.add(new JLabel());

        jifShow2.add(japply);
        jifShow2.setVisible(true);
        jplDisplay.add(jifShow2);
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
                String alterList[] =new String[textField2.length];
                for(int i=0;i<textField2.length;i++){
                    alterList[i] = textField2[i].getText();
                }
                if(md.alterActivity(vano,alterList,tablename)==0)
                    JOptionPane.showMessageDialog(jifShow2, "修改失败","Error", 1);
                else
                    JOptionPane.showMessageDialog(jifShow2, "修改成功","Successful", 1);

            }
        });
    }
    public void allActivities(){
        JTable myJTable;
        jplDisplay.removeAll();
        try{
            //负责人与数据库连接
            md = new my_db();
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

            md.searchAllActivities();
            String[] name = {"活动编号","活动名称","活动频率","所属部门","活动地点","活动人数","报名时间","活动时间","点击查看活动详情"};
            JTableModel.setRowCount(0);	//表格模板的行数和列数清零
            JTableModel.setColumnCount(0);
            for(int i=0;i<name.length;i++)	//添加列名
            {
                JTableModel.addColumn(name[i]);
            }
            md.rs.beforeFirst();
            while(md.rs.next())
            {
                String[] value=new String[name.length];
                value[0]=md.rs.getString(1);
                value[1]=md.rs.getString(2);
                value[2]=md.rs.getString(7);
                value[3]=md.rs.getString(3);
                value[4]=md.rs.getString(9);
                value[5]=md.rs.getString(10);
                value[6]=md.rs.getString(12);
                value[7]=md.rs.getString(11);
                value[8]="·";
                JTableModel.addRow(value);
            }
            md.rs.close();
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
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }
    public void actionPerformed(ActionEvent e)
    {
        jplDisplay.removeAll();        //先清空
        allActivities();
        jplDisplay.setVisible(true);
    }
}

