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
    JInternalFrame jifShow;//��ʾ־Ը�㳡��������
    JInternalFrame jifShow2;//��ʾ־Ը���Ϣ�����
    JPanel jback;//�������
    JPanel japply;//�������

    final DefaultTableModel JTableModel = new DefaultTableModel(100,12);	//������ģ��
    final JScrollPane jspDisplay=new JScrollPane();	//�������

    JLabel label1[];    //�̶��ı���label
    JTextField textField2[];     //�����ݿ��ȡ��Ϣ
    Font labelfont = new Font("����", 1, 24);
    Font labelfont2 = new Font("����",1,24);

    my_db md;
    public volunteerActivityListener(JPanel jpl)
    {
        this.jplDisplay=jpl;
    }
    public void activityDetails(JInternalFrame showtemp,String vano) throws SQLException, ClassNotFoundException {
        Font fbutton=new Font("����", 1, 24);

        jplDisplay.removeAll();		//�����
        jback=new JPanel();
        japply=new JPanel();
        jback.setBackground(new Color(3, 209, 245));
        japply.setBackground(new Color(3, 209, 245));
        JButton back=new JButton("����");			//���÷��ذ�ť��������һ��
        JButton apply=new JButton("�޸�");           //���ñ�����ť������Զ���ӱ�����¼
        back.setFont(fbutton);
        apply.setFont(fbutton);
        jback.add(back);
        jback.setVisible(true);
        japply.add(apply);
        japply.setVisible(true);


        //������ʾ��������
        UIManager.put("InternalFrame.titleFont", new java.awt.Font("����", 0, 24));
        jifShow2 = new JInternalFrame("���ϸ��Ϣ", true, true, true);
        //����ʽ����
        jifShow2.setLayout(new GridLayout(7,4,10,10));
        String labelname[] = new String[]{"����","�����","��������","������","�����",
                "�����","�Ƶ��","�ʱ��","��ص�","�ɲμ�����",
                "�ʱ��","����ʱ��"};
        String tablename[] = new String[]{"Vano","Vaname","Vasec","Vamno","Vatype",
                "Valev","Vafre","Vadura","Vapl","Vasnum",
                "Vatime","Vregtime"};
        //����label
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
        //���񲼾ֲ���
        jifShow2.add(new JLabel());
        jifShow2.add(new JLabel());

        jifShow2.add(japply);
        jifShow2.setVisible(true);
        jplDisplay.add(jifShow2);
//        ��Ӽ�����
        //������һ��
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
                    JOptionPane.showMessageDialog(jifShow2, "�޸�ʧ��","Error", 1);
                else
                    JOptionPane.showMessageDialog(jifShow2, "�޸ĳɹ�","Successful", 1);

            }
        });
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

            md.searchAllActivities();
            String[] name = {"����","�����","�Ƶ��","��������","��ص�","�����","����ʱ��","�ʱ��","����鿴�����"};
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
                value[2]=md.rs.getString(7);
                value[3]=md.rs.getString(3);
                value[4]=md.rs.getString(9);
                value[5]=md.rs.getString(10);
                value[6]=md.rs.getString(12);
                value[7]=md.rs.getString(11);
                value[8]="��";
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
        jplDisplay.removeAll();        //�����
        allActivities();
        jplDisplay.setVisible(true);
    }
}

