package Listener.mn_listener;

import connect_database.leader_db;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class addActivityListener implements ActionListener {
    String mno = "";
    JPanel jplDisplay;
    JInternalFrame jifShow;//��ӳ�����崰��
    JPanel displayUp,displayMid,displayDown;

    leader_db ldb;
    public addActivityListener(JPanel jpl,String mano)
    {
        this.jplDisplay=jpl;
        this.mno=mano;
    }
    public void actionPerformed(ActionEvent e)
    {
        jplDisplay.removeAll();
        jifShow=new JInternalFrame("����־Ը����",true,true,true);
        jifShow.setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        //�������
        displayUp=new JPanel(new GridBagLayout());
        String[] acType={"","У��","Ժ��"};
        final JComboBox productBox=new JComboBox(acType);		//���������˵�
        productBox.setName("�����");
        //���ñ�ǩ(������ʾ)
        JLabel jAlev=new JLabel("�����");
        JLabel jName=new JLabel("�����");
        JLabel jType=new JLabel("����");
        JLabel jAfre=new JLabel("�Ƶ��");
        JLabel jDura=new JLabel("�ʱ��");
        JLabel jPlace=new JLabel("��ص�");
        JLabel jPnum=new JLabel("�ɲμ�����");
        JLabel jBtime1=new JLabel("�ʱ��");
        JLabel jBtime2=new JLabel("����ʱ��");

        //�����ı������,����ʾ����Ҫ���ý������
        final JTextField pName=new JTextField(12);
        final JTextField pType=new JTextField(12);
        final JTextField pAfre=new JTextField(12);
        final JTextField pDura=new JTextField(12);
        final JTextField pPlace=new JTextField(12);
        final JTextField pPnum=new JTextField(12);
        //����ʱ�������ѡ���
        String[] year = new String[10];
        for(int y=2020,i=0;y<2030;y++,i++){
            year[i] = String.valueOf(y);
        }
        final JComboBox yearBox=new JComboBox(year);		//���������˵�
        final JComboBox yearBox2=new JComboBox(year);
        yearBox.setName("��");
        String[] month = new String[12];
        for(int i=1;i<=12;i++){
            month[i-1] = String.valueOf(i);
        }
        final JComboBox monthBox=new JComboBox(month);		//���������˵�
        final JComboBox monthBox2=new JComboBox(month);
        monthBox.setName("��");
        String[] day = new String[31];
        for(int i=1;i<=31;i++){
            day[i-1] = String.valueOf(i);
        }
        final JComboBox dayBox=new JComboBox(day);		//���������˵�
        final JComboBox dayBox2=new JComboBox(day);
        dayBox.setName("��");

        //��������Ĳ���
        GridBagConstraints gridBagCon01 = new GridBagConstraints();

        gridBagCon01.gridx = 5;
        gridBagCon01.gridy = 0;
        gridBagCon01.insets = new Insets(5,5,5,5);
        displayUp.add(jAlev, gridBagCon01);
        gridBagCon01.gridx = 6;
        gridBagCon01.fill = GridBagConstraints.BOTH;
        displayUp.add(productBox, gridBagCon01);

        gridBagCon01.gridx = 5;
        gridBagCon01.gridy = 1;
        displayUp.add(jName, gridBagCon01);
        gridBagCon01.gridx = 6;
        displayUp.add(pName, gridBagCon01);
        gridBagCon01.gridx = 5;
        gridBagCon01.gridy = 2;
        displayUp.add(jType, gridBagCon01);
        gridBagCon01.gridx = 6;
        displayUp.add(pType, gridBagCon01);
        gridBagCon01.gridx = 5;
        gridBagCon01.gridy = 3;
        displayUp.add(jAfre, gridBagCon01);
        gridBagCon01.gridx = 6;
        displayUp.add(pAfre, gridBagCon01);
        gridBagCon01.gridx = 5;
        gridBagCon01.gridy = 4;
        displayUp.add(jDura, gridBagCon01);
        gridBagCon01.gridx = 6;
        displayUp.add(pDura, gridBagCon01);
        gridBagCon01.gridx = 5;
        gridBagCon01.gridy = 5;
        displayUp.add(jPlace, gridBagCon01);
        gridBagCon01.gridx = 6;
        displayUp.add(pPlace, gridBagCon01);
        gridBagCon01.gridx = 5;
        gridBagCon01.gridy = 6;
        displayUp.add(jPnum, gridBagCon01);
        gridBagCon01.gridx = 6;
        displayUp.add(pPnum, gridBagCon01);

        gridBagCon01.gridx = 5;
        gridBagCon01.gridy = 7;
        displayUp.add(jBtime1, gridBagCon01);
        gridBagCon01.gridx = 6;
        displayUp.add(yearBox, gridBagCon01);
        gridBagCon01.gridx = 7;
        displayUp.add(monthBox, gridBagCon01);
        gridBagCon01.gridx = 8;
        displayUp.add(dayBox, gridBagCon01);


        gridBagCon01.gridx = 5;
        gridBagCon01.gridy = 8;
        displayUp.add(jBtime2, gridBagCon01);
        gridBagCon01.gridx = 6;
        displayUp.add(yearBox2, gridBagCon01);
        gridBagCon01.gridx = 7;
        displayUp.add(monthBox2, gridBagCon01);
        gridBagCon01.gridx = 8;
        displayUp.add(dayBox2, gridBagCon01);

        displayUp.setBackground(Color.PINK);

        //�м����
        displayMid=new JPanel(new GridBagLayout());

        //�ײ����
        JButton jbStore=new JButton("����");			//���ð�ť
        JButton jbReset=new JButton("���");
        JButton jbQuit=new JButton("�˳�");
        displayDown=new JPanel(new FlowLayout());
        displayDown.add(jbStore);
        displayDown.add(jbReset);
        displayDown.add(jbQuit);
        displayDown.setBackground(Color.BLACK);

        jifShow.add(displayUp,BorderLayout.NORTH);
        jifShow.add(displayDown,BorderLayout.SOUTH);
        jifShow.setVisible(true);
        jplDisplay.add(jifShow);
        //ΪjbQuit��ť��Ӽ�����
        jbQuit.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                jifShow.dispose();
            }
        });
        //ΪjbReset��ť��Ӽ�����
        jbReset.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                productBox.setSelectedIndex(0);
                yearBox.setSelectedIndex(0);
                yearBox2.setSelectedIndex(0);
                monthBox.setSelectedIndex(0);
                monthBox2.setSelectedIndex(0);
                dayBox.setSelectedIndex(0);
                dayBox2.setSelectedIndex(0);
                pName.setText(null);
                pType.setText(null);
                pAfre.setText(null);
                pDura.setText(null);
                pPlace.setText(null);
                pPnum.setText(null);

            }
        });
        //ΪjbStore��ť��Ӽ�����
        jbStore.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                //1.��ֵ��Ӧ
                String tableName=productBox.getSelectedItem().toString();
                if(tableName.length()==0)//��Ʒ������ʾ��1������Ϣ����
                {
                    JOptionPane.showMessageDialog(jifShow, "��ѡ������","��ʾ", 1);
                    return;
                }
                String volunteerName=pName.getText();
                Pattern pattern=Pattern.compile("^[\\040]*");//���С�\040�ǿո�İ˽���ת���ַ���
                if(pattern.matcher(volunteerName).matches()==true)//��Ʒ������ʾ��
                {
                    JOptionPane.showMessageDialog(jifShow, "������־Ը����","��ʾ", 1);
                    return;
                }
                String volunteerType=pType.getText();
                pattern=Pattern.compile("^[\\040]*");
                if(pattern.matcher(volunteerType).matches()==true)//��Ʒ������ʾ��
                {
                    JOptionPane.showMessageDialog(jifShow, "���������","��ʾ", 1);
                    return;
                }
                String volunteerPlace=pPlace.getText();
                pattern=Pattern.compile("^[\\040]*");
                if(pattern.matcher(volunteerPlace).matches()==true)//��Ʒ������ʾ��
                {
                    JOptionPane.showMessageDialog(jifShow, "�������ص�","��ʾ", 1);
                    return;
                }
                String peopleNum=pPnum.getText();
                pattern=Pattern.compile("^[\\040]*");
                if(pattern.matcher(peopleNum).matches()==true)//��Ʒ������ʾ��
                {
                    JOptionPane.showMessageDialog(jifShow, "������ɲμ�����","��ʾ", 1);
                    return;
                }
                String beginTime = yearBox.getSelectedItem().toString()+"-"+monthBox.getSelectedItem().toString()+"-"+dayBox.getSelectedItem().toString();
                String regTime = yearBox2.getSelectedItem().toString()+"-"+monthBox2.getSelectedItem().toString()+"-"+dayBox2.getSelectedItem().toString();
                //2.���ݿ��������
                //�õ��µı�ź���������
                String Vno="";
                String Vdepart="";
                try {
                    ldb = new leader_db();
                    ldb.searchActivityNo();
                    ldb.rs.next();
                    int tVno = Integer.parseInt(ldb.rs.getString(1));
                    //�µı��Ϊ���һ�б��+1
                    Vno = String.valueOf(tVno+1);
                    ldb.searchMydepart(mno);
                    ldb.rs.next();
                    Vdepart = ldb.rs.getString(1);
                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                //�ȴ���arraylist����һ�е�����
                ArrayList<String> rowdata = new ArrayList<String>();
                rowdata.add(Vno);
                rowdata.add(volunteerName);
                rowdata.add(Vdepart);
                rowdata.add(mno);
                rowdata.add(volunteerType);
                rowdata.add(productBox.getSelectedItem().toString());
                rowdata.add(pAfre.getText());
                rowdata.add(pDura.getText());
                rowdata.add(volunteerPlace);
                rowdata.add(peopleNum);
                rowdata.add(beginTime);
                rowdata.add(regTime);
                //��arraylist�е�����д�����ݿ���
                if(ldb.insertActivity(rowdata)==0)
                    JOptionPane.showMessageDialog(jifShow, "���ʧ��","Error", 1);
                else
                    JOptionPane.showMessageDialog(jifShow, "��ӳɹ�","Successful", 1);
            }
        });
    }
}
