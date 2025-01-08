package Listener.vl_listener;

import connect_database.volunteer_db;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.regex.Pattern;

//个人中心button监听
public class PcListener implements ActionListener {
    String vno = new String();
    JPanel jplDisplay;
    JInternalFrame jifShow;//显示结果的面板
    JLabel label1[];    //固定文本的label
    JLabel label2[];    //从数据库获取信息的label
    JTextField mima;
    Font labelfont = new Font("宋体", 1, 24);
    Font labelfont2 = new Font("楷体",1,24);

    volunteer_db vt;
    public PcListener(JPanel jpl, String vno){
        this.vno = vno;
        this.jplDisplay = jpl;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        jplDisplay.removeAll();		//先清空
        UIManager.put("InternalFrame.titleFont", new java.awt.Font("宋体", 0, 24));
        jifShow = new JInternalFrame("个人信息", true, true, true);
        //网格式布局
        jifShow.setLayout(new GridLayout(7,4,10,10));
        String labelname[] = new String[]{"学号","姓名","性别","民族","联系方式","身份证号","年级",
                "专业","政治面貌","邮箱","总志愿时长","学院"};
        //创建label
        label1=new JLabel[12];
        label2=new JLabel[12];
        mima = new JTextField();
        JLabel labelm = new JLabel("密码");
        labelm.setFont(labelfont);
        JButton xg = new JButton("修改密码");
        xg.setFont(labelfont2);

        //志愿者与数据库连接
        try {
            vt = new volunteer_db();
            vt.searchMy(vno);
            ResultSetMetaData rsmd=vt.rs.getMetaData();	//创建结果集对象
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
            //统计总志愿时长
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
                Pattern pattern=Pattern.compile("^[\\040]*");//其中‘\040是空格的八进制转义字符’
                if(pattern.matcher(m).matches()==true)//产品名称提示框
                {
                    JOptionPane.showMessageDialog(jifShow, "请输入密码","提示", 1);
                    return;
                }
                if(vt.executeUpdate("update volunteers set Vpassword='"+m+"'where Vno='"+vno+"'")==0)
                    JOptionPane.showMessageDialog(jifShow, "修改失败","Error", 1);
                else
                    JOptionPane.showMessageDialog(jifShow, "修改成功","Successful", 1);
            }
        });
//        test.display();
    }
}
