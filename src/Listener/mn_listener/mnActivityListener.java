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
    JInternalFrame jifShow;//��ʾ־Ը�㳡��������
    JInternalFrame jifShow2;//��ʾ־Ը���Ϣ�����
    JInternalFrame jifshow3;//��ʾ���¼
    JPanel jback;//�������
    JPanel japply;
    final DefaultTableModel JTableModel = new DefaultTableModel(100,12);	//������ģ��
    final JScrollPane jspDisplay=new JScrollPane();	//�������

    JLabel label1[];    //�̶��ı���label
    JLabel label2[];    //�����ݿ��ȡ��Ϣ��label
    Font labelfont = new Font("����", 1, 24);
    Font labelfont2 = new Font("����",1,24);

    leader_db ldb;
    public mnActivityListener(JPanel jpl,String vno)
    {
        this.mno=vno;
        this.jplDisplay=jpl;
    }
    public void activityDetails(JInternalFrame showtemp,String vano) throws SQLException, ClassNotFoundException {
        Font fbutton = new Font("����", 1, 24);

        jplDisplay.removeAll();        //�����
        jback = new JPanel();
        japply=new JPanel();
        jback.setBackground(new Color(3, 209, 245));
        japply.setBackground(new Color(3, 209, 245));
        JButton back = new JButton("����");            //���÷��ذ�ť��������һ��
        JButton apply = new JButton("�鿴�������");           //���ñ�����ť������Զ���ӱ�����¼
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
        jifShow2.setLayout(new GridLayout(7, 4, 10, 10));
        String labelname[] = new String[]{"����", "�����", "��������", "������", "�����",
                "�����", "�Ƶ��", "�ʱ��", "��ص�", "�ɲμ�����",
                "�ʱ��", "����ʱ��"};
        int knum;//�ɲ�������
        //����label
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
        //���񲼾ֲ���
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
                JButton jbQuit=new JButton("������һ��");
                JButton jbSure=new JButton("����ͨ��");
                JButton jbFinish=new JButton("��������");
                jbQuit.setFont(fbutton);
                jbSure.setFont(fbutton);
                jbFinish.setFont(fbutton);
                final JTextField pvno=new JTextField(12);
                JLabel advise=new JLabel("������־Ը��ѧ��");
                advise.setFont(new Font("�����п�", 1, 32));
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
                UIManager.put("InternalFrame.titleFont", new java.awt.Font("����", 0, 24));
                jifshow3 =  new JInternalFrame("���ϸ��Ϣ", true, true, true);
                final JScrollPane jspDisplay=new JScrollPane();	//�������
                jspDisplay.setBackground(Color.WHITE);
                jspDisplay.setVisible(true);

                JTable mySecondTable=new JTable(JTableModel);
                mySecondTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
                mySecondTable.setFillsViewportHeight(true);
                mySecondTable.setRowHeight(40);
                String[] name = {"־Ը��ѧ��","����","�Ա�","�꼶","רҵ","�״̬"};
                JTableModel.setRowCount(0);	//���ģ�����������������
                JTableModel.setColumnCount(0);
                for(int i=0;i<name.length;i++)	//�������
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
                            value[5]="��������";
                        else if(aset==2)
                            value[5]="���ڲμ�";
                        else
                            value[5]="�Ѿ�����";
                        JTableModel.addRow(value);

                    }
                } catch (SQLException | ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
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
                jspDisplay.add(myJTable);
                jspDisplay.setViewportView(myJTable);
                jifshow3.add(jspDisplay,BorderLayout.CENTER);
                jifshow3.add(sp,BorderLayout.WEST);
                jifshow3.setVisible(true);
                jplDisplay.add(jifshow3);
                //������һ������
                jbQuit.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        jplDisplay.removeAll();
                        jplDisplay.add(jifShow2);
                    }
                });
                //����������ť
                jbFinish.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        //��ֵ��Ӧ
                        String vno = pvno.getText();
                        Pattern pattern=Pattern.compile("^[\\040]*");//���С�\040�ǿո�İ˽���ת���ַ���
                        if(pattern.matcher(vno).matches()==true)//��Ʒ������ʾ��
                        {
                            JOptionPane.showMessageDialog(jifshow3, "������־Ը�߱��","��ʾ", 1);
                            return;
                        }
                        ldb.searchActivityStatus(vno,vano);
                        try {
                            //״ֻ̬�д����ڲμ�->�Ѿ�����
                            if(ldb.rs.next() && ldb.rs.getInt(1)==2) {
                                ldb.alterActivityStatus(vno, vano, 3);
                                ldb.searchVolunteers(vno);
                                if(ldb.rs.next()){
                                    JOptionPane.showMessageDialog(jifshow3, "�����ɹ�","Successful", 1);
                                }
                                else {
                                    JOptionPane.showMessageDialog(jifshow3, "����ʧ��","Error", 1);
                                }
                            }
                            else{
                                JOptionPane.showMessageDialog(jifshow3, "����ʧ��","Error", 1);
                            }
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                    }
                });
                //������ť
                jbSure.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        //��ֵ��Ӧ
                        String vno = pvno.getText();
                        Pattern pattern=Pattern.compile("^[\\040]*");//���С�\040�ǿո�İ˽���ת���ַ���
                        if(pattern.matcher(vno).matches()==true)//��Ʒ������ʾ��
                        {
                            JOptionPane.showMessageDialog(jifshow3, "������־Ը�߱��","��ʾ", 1);
                            return;
                        }
                        //��ȷ���Ƿ�������
                        ldb.searchVolunteersNum(vano);
                        try {
                            ldb.rs.next();
                            int num = ldb.rs.getInt(1);
                            ldb.searchActivityStatus(vno,vano);
                            //״ֻ̬�д���������->���ڲμ�
                            if(ldb.rs.next() && ldb.rs.getInt(1)==1){
                                if(num< finalKnum){
                                    ldb.alterActivityStatus(vno,vano,2);
                                    ldb.searchVolunteers(vno);
                                    if(ldb.rs.next()){
                                        JOptionPane.showMessageDialog(jifshow3, "�����ɹ�","Successful", 1);
                                    }
                                    else {
                                        JOptionPane.showMessageDialog(jifshow3, "����ʧ��","Error", 1);
                                    }
                                }
                                else
                                    JOptionPane.showMessageDialog(jifshow3, "��ǰ���������","Error", 1);
                            }
                            else{
                                JOptionPane.showMessageDialog(jifshow3, "����ʧ��","Error", 1);
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
            String[] name = {"����","�����","�Ƶ��","��������","��ص�","�����","����ʱ��","�ʱ��","����鿴�����"};
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
    @Override
    public void actionPerformed(ActionEvent e) {
        jplDisplay.removeAll();        //�����
        allActivities();
        jplDisplay.setVisible(true);
    }
}
