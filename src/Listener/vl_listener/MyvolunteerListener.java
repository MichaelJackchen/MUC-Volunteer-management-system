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

//�ҵ�־Ը�button����
public class MyvolunteerListener implements ActionListener {
    String vno = new String();
    JPanel jplDisplay;
    JInternalFrame jifShow;//��ʾ��������
    JInternalFrame jifShow2;//��ʾ־Ը���Ϣ�����
    JPanel jplButton;//״̬�������
    JPanel jback;//�������
    JButton jbQuit=new JButton("�˳�");
    JButton jbSure=new JButton("ȷ��");			//���ð�ť
    final JComboBox vtBox=new JComboBox();		//���������˵�
    final DefaultTableModel JTableModel = new DefaultTableModel(100,12);	//������ģ��
    final JScrollPane jspDisplay=new JScrollPane();	//�������

    JLabel label1[];    //�̶��ı���label
    JLabel label2[];    //�����ݿ��ȡ��Ϣ��label
    Font labelfont = new Font("����", 1, 24);
    Font labelfont2 = new Font("����",1,24);

    volunteer_db vdb;
    public MyvolunteerListener(JPanel jpl, String vno)
    {
        this.vno=vno;
        this.jplDisplay=jpl;
    }
    //��ʾ��������ϸ��Ϣ
    public void activityDetails(JInternalFrame showtemp,String vano){
        Font fbutton=new Font("����", 1, 24);

        jplDisplay.removeAll();		//�����
        jback=new JPanel();
        jback.setBackground(new Color(3, 209, 245));
        JButton back=new JButton("����");			//���÷��ذ�ť��������һ��
        back.setFont(fbutton);
        jback.add(back);
        jback.setVisible(true);


        //������ʾ��������
        UIManager.put("InternalFrame.titleFont", new java.awt.Font("����", 0, 24));
        jifShow2 = new JInternalFrame("���ϸ��Ϣ", true, true, true);
        //����ʽ����
        jifShow2.setLayout(new GridLayout(7,4,10,10));
        String labelname[] = new String[]{"����","�����","��������","������","�����",
                "�����","�Ƶ��","�ʱ��","��ص�","�ɲμ�����",
                "�ʱ��","����ʱ��"};
        //����label
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
        Font fbutton = new Font("����", 1, 24);

        jplDisplay.removeAll();        //�����
        //����ѡ��״̬��������
        jplButton = new JPanel();
        jplButton.setBackground(new Color(250, 250, 240));
        String[] vtName = {"��������", "���ڲμ�", "�Ѿ�����"};
        vtBox.setFont(fbutton);                    //�����˵���������
        vtBox.setModel(new DefaultComboBoxModel(vtName));      //ˢ�������˵�������

        jbSure.setFont(fbutton);
        jbQuit.setFont(fbutton);

        JLabel advise = new JLabel("��ѡ��");
        advise.setFont(new Font("�����п�", 1, 32));
        advise.setForeground(Color.BLACK);
        jplButton.setLayout(new GridLayout(12, 1));        //����־Ը������Ĳ��֣������Ҳ�
        //���������ϼ���ѡ�������˵���ȷ�����˳�
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
        String[] infoname = {"����", "�����", "��������", "������", "�����",
                "�����", "�Ƶ��", "�ʱ��", "��ص�", "�ɲμ�����",
                "�ʱ��", "����ʱ��"};

        //������ʾ��������
        UIManager.put("InternalFrame.titleFont", new java.awt.Font("����", 0, 24));
        jifShow = new JInternalFrame("־Ը�", true, true, true);

        final JScrollPane jspDisplay = new JScrollPane();    //�������
        jspDisplay.setBackground(Color.WHITE);
        jspDisplay.setVisible(true);

        JTable myFirstTable = new JTable(JTableModel);        //������
        myFirstTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        myFirstTable.setFillsViewportHeight(true);
        myFirstTable.setRowHeight(40);
        jspDisplay.add(myFirstTable);        //��ӵ���ʾ�����
        jspDisplay.setViewportView(myFirstTable);
        jifShow.add(jplButton, BorderLayout.EAST);
        jifShow.add(jspDisplay, BorderLayout.CENTER);
        jifShow.setVisible(true);
        jplDisplay.add(jifShow);
        //��table��������¼�������������������ϸ��Ϣ
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
        jplDisplay.removeAll();        //�����
        allActivities();
        jplDisplay.setVisible(true);
        //��Ӽ�����
        jbQuit.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                jifShow.dispose();
            }
        });
        //��ѯ����־Ը�����ʾ
        jbSure.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                JTable myJTable;
                String vtType=vtBox.getSelectedItem().toString();
                try{
                    //־Ը�������ݿ�����
                    vdb = new volunteer_db();
                    //�ڻ��¼��鵽����
                    if(vtType.equals("��������")){
                        vdb.searchActivitiesStatus(1,vno);
                    }
                    else if(vtType.equals("���ڲμ�")) {
                        vdb.searchActivitiesStatus(2,vno);
                    }
                    else{
                        vdb.searchActivitiesStatus(3,vno);
                    }
                    //���ñ�������Ϣ
                    String[] name = {"����","�����","�Ƶ��","��������","��ص�","�����","����ʱ��","�ʱ��","����鿴���ϸ"};


                    JTableModel.setRowCount(0);	//���ģ�����������������
                    JTableModel.setColumnCount(0);
                    //���ñ�ͷ������
                    UIManager.put("TableHeader.font", new java.awt.Font("����", 0,18));
                    for(int i=0;i<name.length;i++)	//�������
                    {
                        JTableModel.addColumn(name[i]);
                    }
                    //ͨ���鵽�Ļ����ٵ�־Ը����в������Ϣ
                    ArrayList<String> vno = new ArrayList<String> ();
                    while(vdb.rs.next()){
                        vno.add(vdb.rs.getString(1));
                    }
                    for(int i=0;i<vno.size();i++){
                        vdb.searchVolunteerNo(vno.get(i));
                        while(vdb.rs.next())		//�õ����е�����ֵ
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
                            value[8]="��";
                            JTableModel.addRow(value);
                        }
                    }

                    vdb.rs.close();
                    JTableModel.setRowCount(JTableModel.getRowCount()+10);
                    JTableModel.setColumnCount(JTableModel.getColumnCount()+10);
                    myJTable=new JTable(JTableModel);	//�ñ��ģ���ʼ�����myJTable
                    myJTable.setRowHeight(54);		//�����и�
                    myJTable.setFillsViewportHeight(true);
                    myJTable.setFont(new Font("Times New Roman",0,18));
                    myJTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);//�ر��Զ������п�
                    myJTable.setCellSelectionEnabled(true);			//����ѡȡ��Ԫ��
                    myJTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);	//�����ѡ
                    jspDisplay.add(myJTable);		//��ӵ���ʾ�����
                    jspDisplay.setViewportView(myJTable);
                } catch (ClassNotFoundException e1) {
                    JOptionPane.showMessageDialog(jspDisplay, "���ݿ������쳣","����",0);
                }catch(SQLException wrong){
                    JOptionPane.showMessageDialog(jspDisplay, "���ݿ�����쳣","����",0);
                }
            }
        });
    }
}
