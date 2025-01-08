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
import java.util.ArrayList;
import java.util.regex.Pattern;

public class managerListener implements ActionListener {
    JPanel jplDisplay;
    JInternalFrame jifShow;//��ʾ־Ը�㳡��������
    JInternalFrame jifShow2;//��ʾ־Ը���Ϣ�����
    JPanel jback;//�������
    JPanel japply;//�������
    JPanel jadd;//�������
    JPanel jreset;

    final JComboBox vtBox=new JComboBox();		//���������˵�
    final DefaultTableModel JTableModel = new DefaultTableModel(100,12);	//������ģ��
    final JScrollPane jspDisplay=new JScrollPane();	//�������

    JLabel label1[];    //�̶��ı���label
    JTextField textField2[];     //�����ݿ��ȡ��Ϣ
    Font labelfont = new Font("����", 1, 24);
    Font labelfont2 = new Font("����",1,24);

    my_db md;
    public managerListener(JPanel jpl)
    {
        this.jplDisplay=jpl;
    }
    public void managerDetails(JInternalFrame showtemp,String vno) throws SQLException, ClassNotFoundException {
        Font fbutton=new Font("����", 1, 24);

        jplDisplay.removeAll();		//�����
        jback=new JPanel();
        japply=new JPanel();
        jadd=new JPanel();
        jreset=new JPanel();
        jback.setBackground(new Color(3, 209, 245));
        japply.setBackground(new Color(3, 209, 245));
        jadd.setBackground(new Color(3, 209, 245));
        jreset.setBackground(new Color(3, 209, 245));
        JButton back=new JButton("����");			//���÷��ذ�ť��������һ��
        JButton apply=new JButton("�޸�");           //���ñ�����ť������Զ���ӱ�����¼
        JButton add=new JButton("����");
        JButton reset=new JButton("���");
        back.setFont(fbutton);
        apply.setFont(fbutton);
        add.setFont(fbutton);
        reset.setFont(fbutton);
        jback.add(back);
        jback.setVisible(true);
        japply.add(apply);
        japply.setVisible(true);
        jadd.add(reset);
        jadd.setVisible(true);
        jreset.add(add);
        jreset.setVisible(true);

        //������ʾ��������
        UIManager.put("InternalFrame.titleFont", new java.awt.Font("����", 0, 24));
        jifShow2 = new JInternalFrame("��������ϸ��Ϣ", true, true, true);
        //����ʽ����
        jifShow2.setLayout(new GridLayout(3,2,10,10));
        String labelname[] = new String[]{"ѧ��","����","�绰","����"};
        String tablename[] = new String[]{"mno","mdepart","mph","Mpassword"};
        //����label
        label1=new JLabel[labelname.length];
        textField2=new JTextField[labelname.length];
        try {
            md.searchManager(vno);
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
        jifShow2.add(japply);
        jifShow2.add(jreset);
        jifShow2.add(jadd);
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
                if(md.alterManager(vno,alterList,tablename)==0)
                    JOptionPane.showMessageDialog(jifShow2, "�޸�ʧ��","Error", 1);
                else
                    JOptionPane.showMessageDialog(jifShow2, "�޸ĳɹ�","Successful", 1);

            }
        });
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Pattern pattern=Pattern.compile("^[\\040]*");//���С�\040�ǿո�İ˽���ת���ַ���
                ArrayList<String> rowdata = new ArrayList<String>();
                for(int i=0;i<textField2.length;i++){
                    String text = textField2[i].getText();
                    rowdata.add(text);
                    if(pattern.matcher(text).matches()==true)//��Ʒ������ʾ��
                    {
                        JOptionPane.showMessageDialog(jifShow2, "������"+labelname[i],"��ʾ", 1);
                        return;
                    }
                }
                if(md.insertManger(rowdata,tablename)==0)
                    JOptionPane.showMessageDialog(jifShow, "���ʧ��","Error", 1);
                else
                    JOptionPane.showMessageDialog(jifShow, "��ӳɹ�","Successful", 1);
            }
        });
        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(int i=0;i<textField2.length;i++){
                    textField2[i].setText(null);
                }
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

            md.searchAllManagers();
            String[] name = {"ѧ��","����","����鿴־Ը����ϸ"};
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
                value[2]="��";
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
                    if(c==2){
                        String vno = (String) myJTable.getValueAt(r,0);
                        try {
                            managerDetails(jifShow,vno);
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
