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
    JInternalFrame jifShow;//��ʾ־Ը�㳡��������
    JInternalFrame jifShow2;//��ʾ־Ը���Ϣ�����
    JPanel jback;//�������
    JPanel alter;
    final DefaultTableModel JTableModel = new DefaultTableModel(100,12);	//������ģ��
    final JScrollPane jspDisplay=new JScrollPane();	//�������
    JLabel label1[];    //�̶��ı���label
    JLabel label2[];
    JTextField textField2[];     //�����ݿ��ȡ��Ϣ
    Font labelfont = new Font("����", 1, 24);
    Font labelfont2 = new Font("����",1,24);

    leader_db ldb;
    public alterActivityListener(JPanel jpl,String vno)
    {
        this.mno=vno;
        this.jplDisplay=jpl;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        jplDisplay.removeAll();        //�����
        allActivities();
        jplDisplay.setVisible(true);
    }
    public void allActivities(){
        JTable myJTable;
        jplDisplay.removeAll();
        try{
            //�����������ݿ�����
            ldb = new leader_db();
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

            ldb.searchResponsibleActivities(mno);
            String[] name = {"����","�����","�Ƶ��","��������","��ص�","�����","����ʱ��","�ʱ��","����޸Ļ��Ϣ"};
            JTableModel.setRowCount(0);	//���ģ�����������������
            JTableModel.setColumnCount(0);
            for(int i=0;i<name.length;i++)	//�������
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
                value[8]="��";
                JTableModel.addRow(value);
            }
            ldb.rs.close();
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
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
    public void activityDetails(JInternalFrame showtemp,String vano) throws SQLException, ClassNotFoundException {
        Font fbutton = new Font("����", 1, 24);

        jplDisplay.removeAll();        //�����
        jback = new JPanel();
        alter = new JPanel();
        jback.setBackground(new Color(3, 209, 245));
        alter.setBackground(new Color(3, 209, 245));
        JButton back = new JButton("����");            //���÷��ذ�ť��������һ��
        JButton alter1 = new JButton("�޸�");           //���ñ�����ť������Զ���ӱ�����¼
        back.setFont(fbutton);
        alter.setFont(fbutton);
        jback.add(back);
        jback.setVisible(true);
        alter.add(alter1);
        alter.setVisible(true);

        //������ʾ��������
        UIManager.put("InternalFrame.titleFont", new java.awt.Font("����", 0, 24));
        jifShow2 = new JInternalFrame("���ϸ��Ϣ", true, true, true);
        //����ʽ����
        jifShow2.setLayout(new GridLayout(7, 4, 10, 10));
        String labelname[] = new String[]{"����", "�����", "��������", "������", "�����",
                "�����", "�Ƶ��", "�ʱ��", "��ص�", "�ɲμ�����",
                "�ʱ��", "����ʱ��"};
        //����label
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
        //���񲼾ֲ���
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
                    JOptionPane.showMessageDialog(jifShow2, "�޸�ʧ��","Error", 1);
                else
                    JOptionPane.showMessageDialog(jifShow2, "�޸ĳɹ�","Successful", 1);

            }
        });
    }
}
