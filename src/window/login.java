package window;
import connect_database.login_db;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

import javax.swing.*;

/**
 * ־Ը����ϵͳ��¼����
 */
public class login extends JFrame{
    private static login_db ld;
    // ���� JFrame ʵ��
    JFrame frame = new JFrame("���־Ը�߹���ϵͳ");
    JPanel panel = new JPanel();
    JTextField userText = new JTextField(20);
    JPasswordField passwordText = new JPasswordField(20);
    JButton loginButton = new JButton("login");
    final JComboBox vtBox = new JComboBox();        //���������˵�
    Icon icon;
    public login() {
        frame.setSize(800, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   
        // ������
        frame.add(panel);

        placeComponents(panel);
        //������ʾ����Ļ����
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    private void placeComponents(JPanel panel) {


        panel.setLayout(new FlowLayout(FlowLayout.CENTER,20,10));
        Font fbutton = new Font("����", 1, 20);
        String[] vtName = {"־Ը��", "������", "����Ա"};
        icon=new ImageIcon("D:\\ѧѧѧ\\����\\���ݿ�ԭ��γ����\\19011469��ӳ����ݿ�ԭ��γ����\\code\\vicon.jpg");
        JLabel I = new JLabel();
        I.setIcon(icon);
        I.setBounds(10,10,200,50);
        panel.add(I,new Integer(Integer.MIN_VALUE));
        vtBox.setFont(fbutton);                    //�����˵���������
        vtBox.setModel(new DefaultComboBoxModel(vtName));      //ˢ�������˵�������
        panel.add(vtBox);
        JLabel userLabel = new JLabel("User:");
        userLabel.setBounds(10,20,80,25);
        panel.add(userLabel);

        /* 
         * �����ı��������û�����
         */
        userText.setBounds(100,20,165,25);
        panel.add(userText);
        // ����������ı���
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(10,50,80,25);
        panel.add(passwordLabel);

        /* 
         *�����������������ı���
         * �����������Ϣ���Ե�Ŵ��棬���ڰ�������İ�ȫ��
         */
        passwordText.setBounds(100,50,165,25);
        panel.add(passwordText);

        // ������¼��ť
        loginButton.setBounds(10, 80, 80, 25);
        loginButton.setFont(fbutton);
        panel.add(loginButton);


    }


    public String searchPassword(String account) throws SQLException
    {
        String tableName = new String("");
        String userStatus = vtBox.getSelectedItem().toString();
        if(userStatus.equals("־Ը��")){
            tableName = "volunteers";
        }
        else if(userStatus.equals("������")){
            tableName = "manager";
        }
        else
            tableName = "login";
        try{
            String up = null;
            if(tableName.equals("volunteers"))
            {
                ld.execute("select Vpassword from "+tableName+" where Vno ='"  + account + "'");	///Executes the given SQL statement
                while(ld.rs.next()) {
                    up = ld.rs.getString("Vpassword");
                }
            }
            else if(tableName.equals("manager"))
            {
                ld.execute("select Mpassword from "+tableName+" where Mno ='"  + account + "'");	///Executes the given SQL statement
                while(ld.rs.next()) {
                    up = ld.rs.getString("Mpassword");
                }
            }
            else
            {
                ld.execute("select upassword from "+tableName+" where uaccount ='"  + account + "'");	///Executes the given SQL statement
                while(ld.rs.next()) {
                    up = ld.rs.getString("upassword");
                }
            }

            return up;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }


    public static void main(String args[]) throws SQLException, ClassNotFoundException {
    	//��¼���������˻�������
        login lg = new login();
        //�������ݿ������ݿ��������˻�����ȶԣ�������ݽ��벻ͬ����
        ld = new login_db();
        lg.loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Ϊbutton�����������������ݿ���˻�������֤�û�
                String vtType=lg.vtBox.getSelectedItem().toString();
                String usertxt = lg.userText.getText().trim();      //־Ը�ߡ������˵�ѧ�ţ�����Ա���˺�
                char pw[] = lg.passwordText.getPassword();
                try {
                    String userpassword = lg.searchPassword(usertxt);
                    if(userpassword == null){
                        JOptionPane.showMessageDialog(lg.panel, "�û������������","��ʾ", 1);
                        return;
                    }
                    else {
                        String ptemp = "";
                        for(int i=0;i<pw.length;i++){
                            ptemp += pw[i];
                        }
                        if(userpassword.equals(ptemp) && vtType.equals("־Ը��")){
                            lg.frame.setVisible(false);
                            new volunteer(usertxt,lg.frame);
                        }
                        else if(userpassword.equals(ptemp) && vtType.equals("������")){
                            lg.frame.setVisible(false);
                            new manager(usertxt,lg.frame);
                        }
                        else if(userpassword.equals(ptemp) && vtType.equals("����Ա")){
                            lg.frame.setVisible(false);
                            new admin(lg.frame);
                        }
                        else {
                            JOptionPane.showMessageDialog(lg.panel, "�û������������","��ʾ", 1);
                            return;
                        }
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }


            }
        });
    }
}
