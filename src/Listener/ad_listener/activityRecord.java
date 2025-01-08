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

public class activityRecord implements ActionListener {
    JPanel jplDisplay;
    JInternalFrame jifShow;//显示志愿广场结果的面板
    JLabel label1[];    //固定文本的label
    JTextField textField2[];     //从数据库获取信息
    final DefaultTableModel JTableModel = new DefaultTableModel(100,12);	//定义表格模板
    final JScrollPane jspDisplay=new JScrollPane();	//滚动面板
    my_db md;
    public activityRecord(JPanel jpl)
    {
        this.jplDisplay=jpl;
    }
    public void actionPerformed(ActionEvent e)
    {
        jplDisplay.removeAll();        //先清空
        allActivities();
        jplDisplay.setVisible(true);
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

            md.searchAllRecords();
            String[] name = {"活动级别","活动时间","活动编号","学号","志愿时长","活动删除","点击删除记录"};
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
                value[2]=md.rs.getString(3);
                value[3]=md.rs.getString(4);
                value[4]=md.rs.getString(5);
                value[5]=md.rs.getString(6);
                value[6]="·";
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
                    if(c==6){
                        String ano = (String) myJTable.getValueAt(r,2);
                        String sno = (String) myJTable.getValueAt(r,3);
                        md.deleteActivityRecord(ano,sno);
                    }
                }
            });
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }
}
