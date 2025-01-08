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
    JInternalFrame jifShow;//��ʾ־Ը�㳡��������
    JLabel label1[];    //�̶��ı���label
    JTextField textField2[];     //�����ݿ��ȡ��Ϣ
    final DefaultTableModel JTableModel = new DefaultTableModel(100,12);	//������ģ��
    final JScrollPane jspDisplay=new JScrollPane();	//�������
    my_db md;
    public activityRecord(JPanel jpl)
    {
        this.jplDisplay=jpl;
    }
    public void actionPerformed(ActionEvent e)
    {
        jplDisplay.removeAll();        //�����
        allActivities();
        jplDisplay.setVisible(true);
    }
    public void allActivities(){
        JTable myJTable;
        jplDisplay.removeAll();
        try{
            //�����������ݿ�����
            md = new my_db();
            //������ʾ��������
            UIManager.put("InternalFrame.titleFont", new java.awt.Font("����", 0, 24));
            jifShow=new JInternalFrame("־Ը�",true,true,true);

            final JScrollPane jspDisplay=new JScrollPane();	//�������
            jspDisplay.setBackground(Color.WHITE);
            jspDisplay.setVisible(true);

            JTable myFirstTable=new JTable(JTableModel);		//������
            myFirstTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            myFirstTable.setFillsViewportHeight(true);
            myFirstTable.setRowHeight(40);

            md.searchAllRecords();
            String[] name = {"�����","�ʱ��","����","ѧ��","־Ըʱ��","�ɾ��","���ɾ����¼"};
            JTableModel.setRowCount(0);	//���ģ�����������������
            JTableModel.setColumnCount(0);
            for(int i=0;i<name.length;i++)	//�������
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
                value[6]="��";
                JTableModel.addRow(value);
            }
            md.rs.close();
            JTableModel.setRowCount(JTableModel.getRowCount()+10);
            JTableModel.setColumnCount(JTableModel.getColumnCount()+10);
            myJTable=new JTable(JTableModel);	//�ñ��ģ���ʼ�����myJTable
            myJTable.setRowHeight(54);		//�����и�
            myJTable.setFillsViewportHeight(true);
            myJTable.setFont(new Font("Times New Roman",0,18));
            myJTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);//�ر��Զ������п�
            myJTable.setCellSelectionEnabled(true);			//����ѡȡ��Ԫ��
            myJTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);	//�����ѡ
            myJTable.setFont(new Font("����", Font.PLAIN, 12));
            jspDisplay.add(myJTable);		//��ӵ���ʾ�����
            jspDisplay.setViewportView(myJTable);
            jifShow.add(jspDisplay,BorderLayout.CENTER);
            jifShow.setVisible(true);
            jplDisplay.add(jifShow);
            //��table��������¼�������������������ϸ��Ϣ
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
