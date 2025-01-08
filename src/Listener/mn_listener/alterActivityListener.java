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

public class alterActivityListener implements ActionListener {
    String mno =new String();
    JPanel jplDisplay;
    JInternalFrame jifShow;//显示志愿广场结果的面板
    JInternalFrame jifShow2;//显示志愿活动信息的面板
    JPanel jback;//返回面板
    JPanel alter;
    final DefaultTableModel JTableModel = new DefaultTableModel(100,12);	//定义表格模板
    final JScrollPane jspDisplay=new JScrollPane();	//滚动面板
    JLabel label1[];    //固定文本的label
    JLabel label2[];
    JTextField textField2[];     //从数据库获取信息
    Font labelfont = new Font("宋体", 1, 24);
    Font labelfont2 = new Font("仿宋",1,24);

    leader_db ldb;
    public alterActivityListener(JPanel jpl,String vno)
    {
        this.mno=vno;
        this.jplDisplay=jpl;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        jplDisplay.removeAll();        //先清空
        allActivities();
        jplDisplay.setVisible(true);
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
            String[] name = {"活动编号","活动名称","活动频率","所属部门","活动地点","活动人数","报名时间","活动时间","点击修改活动信息"};
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
    public void activityDetails(JInternalFrame showtemp,String vano) throws SQLException, ClassNotFoundException {
        Font fbutton = new Font("宋体", 1, 24);

        jplDisplay.removeAll();        //先清空
        jback = new JPanel();
        alter = new JPanel();
        jback.setBackground(new Color(3, 209, 245));
        alter.setBackground(new Color(3, 209, 245));
        JButton back = new JButton("返回");            //设置返回按钮，返回上一级
        JButton alter1 = new JButton("修改");           //设置报名按钮，点击自动添加报名记录
        back.setFont(fbutton);
        alter.setFont(fbutton);
        jback.add(back);
        jback.setVisible(true);
        alter.add(alter1);
        alter.setVisible(true);

        //用于显示结果的面板
        UIManager.put("InternalFrame.titleFont", new java.awt.Font("宋体", 0, 24));
        jifShow2 = new JInternalFrame("活动详细信息", true, true, true);
        //网格式布局
        jifShow2.setLayout(new GridLayout(7, 4, 10, 10));
        String labelname[] = new String[]{"活动编号", "活动名称", "所属部门", "负责人", "活动类型",
                "活动级别", "活动频率", "活动时长", "活动地点", "可参加人数",
                "活动时间", "报名时间"};
        //创建label
        label1 = new JLabel[labelname.length];
        label2 = new JLabel[4];
        textField2 = new JTextField[labelname.length-4];
        try {

            ldb.searchResponsibleActivity(vano, mno);
            ldb.rs.next();
            int j=0;
            int k=0;
            for (int i = 0; i < label1.length; i++) {
                label1[i] = new JLabel(labelname[i]);
                label1[i].setFont(labelfont);
                jifShow2.add(label1[i]);
                if(i==0 || i==2 || i==3 || i==5){
                    label2[j] = new JLabel(ldb.rs.getString(i + 1));
                    label2[j].setFont(labelfont2);
                    jifShow2.add(label2[j]);
                    j+=1;
                }
                else {
                    textField2[k] = new JTextField();
                    textField2[k].setText(ldb.rs.getString(i + 1));
                    textField2[k].setFont(labelfont2);
                    jifShow2.add(textField2[k]);
                    k+=1;
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        jifShow2.setBackground(new Color(3, 209, 245));
        jifShow2.add(jback);
        //网格布局补空
        jifShow2.add(new JLabel());
        jifShow2.add(new JLabel());
        jifShow2.add(alter);

        jifShow2.setVisible(true);
        jplDisplay.add(jifShow2);
        back.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jplDisplay.removeAll();
                allActivities();
            }
        });
        alter1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String alterList[] =new String[8];
                for(int i=0;i<textField2.length;i++){
                    alterList[i] = textField2[i].getText();
                }
                if(ldb.alterActivity(vano,alterList)==0)
                    JOptionPane.showMessageDialog(jifShow2, "修改失败","Error", 1);
                else
                    JOptionPane.showMessageDialog(jifShow2, "修改成功","Successful", 1);

            }
        });
    }
}
