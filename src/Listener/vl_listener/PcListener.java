package Listener.vl_listener;

import connect_database.volunteer_db;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.regex.Pattern;

//��������button����
public class PcListener implements ActionListener {
    String vno = new String();
    JPanel jplDisplay;
    JInternalFrame jifShow;//��ʾ��������
    JLabel label1[];    //�̶��ı���label
    JLabel label2[];    //�����ݿ��ȡ��Ϣ��label
    JTextField mima;
    Font labelfont = new Font("����", 1, 24);
    Font labelfont2 = new Font("����",1,24);

    volunteer_db vt;
    public PcListener(JPanel jpl, String vno){
        this.vno = vno;
        this.jplDisplay = jpl;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        jplDisplay.removeAll();		//�����
        UIManager.put("InternalFrame.titleFont", new java.awt.Font("����", 0, 24));
        jifShow = new JInternalFrame("������Ϣ", true, true, true);
        //����ʽ����
        jifShow.setLayout(new GridLayout(7,4,10,10));
        String labelname[] = new String[]{"ѧ��","����","�Ա�","����","��ϵ��ʽ","���֤��","�꼶",
                "רҵ","������ò","����","��־Ըʱ��","ѧԺ"};
        //����label
        label1=new JLabel[12];
        label2=new JLabel[12];
        mima = new JTextField();
        JLabel labelm = new JLabel("����");
        labelm.setFont(labelfont);
        JButton xg = new JButton("�޸�����");
        xg.setFont(labelfont2);

        //־Ը�������ݿ�����
        try {
            vt = new volunteer_db();
            vt.searchMy(vno);
            ResultSetMetaData rsmd=vt.rs.getMetaData();	//�������������
            vt.rs.beforeFirst();
            vt.rs.next();
            for(int i=0;i<label2.length;i++){
                label1[i] = new JLabel(labelname[i]);
                label2[i] = new JLabel(vt.rs.getString(i+1));
                label1[i].setFont(labelfont);
                label2[i].setFont(labelfont2);
//            label2[i].setForeground(Color.CYAN);
                jifShow.add(label1[i]);
                jifShow.add(label2[i]);
            }
            label2[11].setText(vt.rs.getString(13));
            mima.setText(vt.rs.getString(11));
            jifShow.add(labelm);
            jifShow.add(mima);
            jifShow.add(xg);
            //ͳ����־Ըʱ��
            vt.searchVolunteerTime(vno);
            vt.rs.next();
            if(vt.rs.getString(1) == null)
                label2[10].setText("0");
            else
                label2[10].setText(vt.rs.getString(1));

        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        jifShow.setBackground(new Color(236, 245, 3));
        jifShow.setVisible(true);

        jplDisplay.add(jifShow);
        xg.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String m = mima.getText();
                Pattern pattern=Pattern.compile("^[\\040]*");//���С�\040�ǿո�İ˽���ת���ַ���
                if(pattern.matcher(m).matches()==true)//��Ʒ������ʾ��
                {
                    JOptionPane.showMessageDialog(jifShow, "����������","��ʾ", 1);
                    return;
                }
                if(vt.executeUpdate("update volunteers set Vpassword='"+m+"'where Vno='"+vno+"'")==0)
                    JOptionPane.showMessageDialog(jifShow, "�޸�ʧ��","Error", 1);
                else
                    JOptionPane.showMessageDialog(jifShow, "�޸ĳɹ�","Successful", 1);
            }
        });
//        test.display();
    }
}
