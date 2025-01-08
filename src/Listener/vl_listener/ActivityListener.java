package Listener.vl_listener;

import connect_database.volunteer_db;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

//־Ը�button����
public class ActivityListener implements ActionListener {
    String vno =new String();
    JPanel jplDisplay;
    JInternalFrame jifShow;//��ʾ־Ը�㳡��������
    JInternalFrame jifShow2;//��ʾ־Ը���Ϣ�����
    JPanel jplButton;//ѡ��־Ը�������
    JPanel jback;//�������
    JPanel japply;//�������

    JButton jbQuit=new JButton("�˳�");
    JButton jbSure=new JButton("ȷ��");			//���ð�ť
    final JComboBox vtBox=new JComboBox();		//���������˵�
    final DefaultTableModel JTableModel = new DefaultTableModel(100,12);	//������ģ��
    final JScrollPane jspDisplay=new JScrollPane();	//�������

    JLabel label1[];    //�̶��ı���label
    JLabel label2[];    //�����ݿ��ȡ��Ϣ��label
    Font labelfont = new Font("����", 1, 24);
    Font labelfont2 = new Font("����",1,24);
    public ActivityListener(JPanel jpl,String vno)
    {
        this.vno=vno;
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
        JButton apply=new JButton("����");           //���ñ�����ť������Զ���ӱ�����¼
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
        //����label
        label1=new JLabel[labelname.length];
        label2=new JLabel[labelname.length];
        volunteer_db vt = new volunteer_db();
        try {

            vt.searchVolunteerActivity(vano);
            vt.rs.next();
            for(int i=0;i<label1.length;i++) {
                label1[i] = new JLabel(labelname[i]);
                label2[i] = new JLabel(vt.rs.getString(i+1));
                label1[i].setFont(labelfont);
                label2[i].setFont(labelfont2);
//            label2[i].setForeground(Color.CYAN);
                jifShow2.add(label1[i]);
                jifShow2.add(label2[i]);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        int knum = Integer.parseInt(label2[9].getText());
//        JLabel vname = new JLabel("NAME:");
//        JLabel vlph = new JLabel("PHONE:");
//        JTextField vntxt = new JTextField();
//        jifShow.add(vname);
//        jifShow.add(vntxt);
//        jifShow.add(vlph);
        jifShow2.setBackground(new Color(3, 209, 245));
        jifShow2.add(jback);
        //���񲼾ֲ���
        jifShow2.add(new JLabel());
        jifShow2.add(new JLabel());

        jifShow2.add(japply);
        jifShow2.setVisible(true);
        jplDisplay.add(jifShow2);
//        jplDisplay.remove(jifShow);
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
                JFrame perform = new JFrame("��ʾ");
                perform.setSize(400, 200);
                perform.setLocationRelativeTo(null);
                JLabel ok = new JLabel("�����ɹ���");
                JLabel no1 = new JLabel("����ʧ��!��������μӸû��");
                JLabel no2 = new JLabel("����ʧ��!�û����ʱ���ѹ���");
                JLabel no3 = new JLabel("����ʧ��!�û��������������");
                ok.setFont(labelfont);
                no1.setFont(labelfont);
                no2.setFont(labelfont);
                no3.setFont(labelfont);
                String aedate = label2[10].getText();       //�ʱ��
                String asdate = label2[11].getText();
                //��������
                Date nowdate = new Date();
                SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
                String snowdate = ft.format(nowdate);
                boolean timeright = false;
                try {
                    Date d = ft.parse(snowdate);
                    Date d1 = ft.parse(asdate);
                    Date d2 = ft.parse(aedate);

                    if(d1.before(d) && d.before(d2))
                        timeright = true;
                    else
                        timeright = false;
                } catch (ParseException ex) {
                    ex.printStackTrace();
                }
                //��ѯ��������
                int num=0;
                try{
                    vt.searchVolunteersNum(vano);
                    vt.rs.next();
                    num = vt.rs.getInt(1);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

//                System.out.println(asdate+aedate+snowdate);
                //���ݿ����

                try {
                    vt.searchVolunteerTranscibe(vno,vano);

                    int vaStatus = 0;
                    if(vt.rs.next()) {
                        vaStatus = vt.rs.getInt(1);
                    }

                    //��������,�μ�,���Ѿ���¼�μӽ���
                    if(vaStatus==1 || vaStatus==2 ||vaStatus==3){
                        perform.add(no1);
                    }
                    //�жϵ�ǰʱ���Ƿ��ڱ���ʱ��-�ʱ��֮��
                    else if(!timeright)
                    {
                        perform.add(no2);
                    }
                    //�жϱ�������
                    else if(num>=knum)
                    {
                        perform.add(no3);
                    }
                    //����¼������������¼
                    else
                    {
                        vt.executeUpdate("call Activities_insert('"+vano+"','"+vno+"',1)");
                        perform.add(ok);
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

//              perform.add(ok);
                perform.setVisible(true);
            }
        });
    }
    public void allActivities(){
        Font fbutton=new Font("����", 1, 24);

        jplDisplay.removeAll();
        //����ѡ��־Ը��������
        jplButton=new JPanel();
        jplButton.setBackground(new Color(250,250,240));
        String[] vtName={"ȫУ�","��Ժ�"};
        vtBox.setFont(fbutton);                    //�����˵���������
        vtBox.setModel(new DefaultComboBoxModel(vtName));      //ˢ�������˵�������

        jbSure.setFont(fbutton);

        jbQuit.setFont(fbutton);

        JLabel advise=new JLabel("��ѡ��");
        advise.setFont(new Font("�����п�", 1, 32));
        advise.setForeground(Color.BLACK);
        jplButton.setLayout(new GridLayout(12,1));		//����־Ը������Ĳ��֣������Ҳ�
        //���������ϼ���ѡ�������˵���ȷ�����˳�
        for(int i=0;i<12;i++)
        {
            switch(i)
            {
                case 0:jplButton.add(advise,BorderLayout.CENTER);break;
                case 1:jplButton.add( vtBox,BorderLayout.CENTER);break;
                case 10:jplButton.add(jbSure,BorderLayout.CENTER);break;
                case 11:jplButton.add(jbQuit,BorderLayout.CENTER);break;
                default:jplButton.add(new JLabel());
            }
        }
        jplButton.setVisible(true);
        String[] infoname = {"����","�����","��������","������","�����",
                "�����","�Ƶ��","�ʱ��","��ص�","�ɲμ�����",
                "�ʱ��","����ʱ��"};
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

        jspDisplay.add(myFirstTable);		//��ӵ���ʾ�����
        jspDisplay.setViewportView(myFirstTable);
        jifShow.add(jplButton,BorderLayout.EAST);
        jifShow.add(jspDisplay,BorderLayout.CENTER);
        jifShow.setVisible(true);
        jplDisplay.add(jifShow);
        //��table��������¼�������������������ϸ��Ϣ
        myFirstTable.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e){
                int r= myFirstTable.getSelectedRow();
                int c= myFirstTable.getSelectedColumn();
                String row = (String) myFirstTable.getValueAt(r,c);
                if(row==null)
                    return;
                if(c==8){
                    String vano = (String) myFirstTable.getValueAt(r,0);
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
    }
    public void actionPerformed(ActionEvent e)
    {
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
                    volunteer_db vdb = new volunteer_db();
                    if(vtType.equals("ȫУ�")){
                        vdb.searchVolunteerType("У��");
                    }
                    else {
                        vdb.searchMyxyActivities(vno);

                    }
                    ResultSetMetaData rsmd=vdb.rs.getMetaData();	//�������������
                    String[] name = {"����","�����","�Ƶ��","��������","��ص�","�����","����ʱ��","�ʱ��","����鿴���ϸ"};
                    JTableModel.setRowCount(0);	//���ģ�����������������
                    JTableModel.setColumnCount(0);
                    //���ñ�ͷ������
                    UIManager.put("TableHeader.font", new java.awt.Font("����", 0,18));
                    for(int i=0;i<name.length;i++)	//�������
                    {
                        JTableModel.addColumn(name[i]);
                    }
                    vdb.rs.beforeFirst();
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
                    vdb.rs.close();
                    JTableModel.setRowCount(JTableModel.getRowCount()+10);
                    JTableModel.setColumnCount(JTableModel.getColumnCount()+10);
                    myJTable=new JTable(JTableModel);	//�ñ��ģ���ʼ�����myJTable
//                    myJTable.setEnabled(true);		//���ñ���ܱ��༭
                    myJTable.setRowHeight(54);		//�����и�
                    myJTable.setRowMargin(100);
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
