package window;
import connect_database.login_db;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

import javax.swing.*;

/**
 * 志愿管理系统登录界面
 */
public class login extends JFrame{
    private static login_db ld;
    // 创建 JFrame 实例
    JFrame frame = new JFrame("民大志愿者管理系统");
    JPanel panel = new JPanel();
    JTextField userText = new JTextField(20);
    JPasswordField passwordText = new JPasswordField(20);
    JButton loginButton = new JButton("login");
    final JComboBox vtBox = new JComboBox();        //设置下拉菜单
    Icon icon;
    public login() {
        frame.setSize(800, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   
        // 添加面板
        frame.add(panel);

        placeComponents(panel);
        //窗口显示在屏幕中央
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    private void placeComponents(JPanel panel) {


        panel.setLayout(new FlowLayout(FlowLayout.CENTER,20,10));
        Font fbutton = new Font("宋体", 1, 20);
        String[] vtName = {"志愿者", "负责人", "管理员"};
        icon=new ImageIcon("vicon.jpg");
        JLabel I = new JLabel();
        I.setIcon(icon);
        I.setBounds(10,10,200,50);
        panel.add(I,new Integer(Integer.MIN_VALUE));
        vtBox.setFont(fbutton);                    //下拉菜单设置字体
        vtBox.setModel(new DefaultComboBoxModel(vtName));      //刷新下拉菜单的数据
        panel.add(vtBox);
        JLabel userLabel = new JLabel("User:");
        userLabel.setBounds(10,20,80,25);
        panel.add(userLabel);

        /* 
         * 创建文本域用于用户输入
         */
        userText.setBounds(100,20,165,25);
        panel.add(userText);
        // 输入密码的文本域
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(10,50,80,25);
        panel.add(passwordLabel);

        /* 
         *这个类似用于输入的文本域
         * 但是输入的信息会以点号代替，用于包含密码的安全性
         */
        passwordText.setBounds(100,50,165,25);
        panel.add(passwordText);

        // 创建登录按钮
        loginButton.setBounds(10, 80, 80, 25);
        loginButton.setFont(fbutton);
        panel.add(loginButton);


    }


    public String searchPassword(String account) throws SQLException
    {
        String tableName = new String("");
        String userStatus = vtBox.getSelectedItem().toString();
        if(userStatus.equals("志愿者")){
            tableName = "volunteers";
        }
        else if(userStatus.equals("负责人")){
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
    	//登录界面输入账户和密码
        login lg = new login();
        //连接数据库与数据库内已有账户密码比对，根据身份进入不同界面
        ld = new login_db();
        lg.loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //为button创建监听，根据数据库的账户密码验证用户
                String vtType=lg.vtBox.getSelectedItem().toString();
                String usertxt = lg.userText.getText().trim();      //志愿者、负责人的学号，管理员的账号
                char pw[] = lg.passwordText.getPassword();
                try {
                    String userpassword = lg.searchPassword(usertxt);
                    if(userpassword == null){
                        JOptionPane.showMessageDialog(lg.panel, "用户名或密码错误","提示", 1);
                        return;
                    }
                    else {
                        String ptemp = "";
                        for(int i=0;i<pw.length;i++){
                            ptemp += pw[i];
                        }
                        if(userpassword.equals(ptemp) && vtType.equals("志愿者")){
                            lg.frame.setVisible(false);
                            new volunteer(usertxt,lg.frame);
                        }
                        else if(userpassword.equals(ptemp) && vtType.equals("负责人")){
                            lg.frame.setVisible(false);
                            new manager(usertxt,lg.frame);
                        }
                        else if(userpassword.equals(ptemp) && vtType.equals("管理员")){
                            lg.frame.setVisible(false);
                            new admin(lg.frame);
                        }
                        else {
                            JOptionPane.showMessageDialog(lg.panel, "用户名或密码错误","提示", 1);
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
