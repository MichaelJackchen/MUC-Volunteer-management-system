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
    JInternalFrame jifShow;//添加宠物面板窗口
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
        jifShow=new JInternalFrame("发布志愿活动面板",true,true,true);
        jifShow.setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        //顶层面板
        displayUp=new JPanel(new GridBagLayout());
        String[] acType={"","校级","院级"};
        final JComboBox productBox=new JComboBox(acType);		//设置下拉菜单
        productBox.setName("活动级别");
        //设置标签(文字显示)
        JLabel jAlev=new JLabel("活动级别");
        JLabel jName=new JLabel("活动名称");
        JLabel jType=new JLabel("活动类别");
        JLabel jAfre=new JLabel("活动频率");
        JLabel jDura=new JLabel("活动时长");
        JLabel jPlace=new JLabel("活动地点");
        JLabel jPnum=new JLabel("可参加人数");
        JLabel jBtime1=new JLabel("活动时间");
        JLabel jBtime2=new JLabel("报名时间");

        //设置文本输入框,带提示输入要设置焦点监听
        final JTextField pName=new JTextField(12);
        final JTextField pType=new JTextField(12);
        final JTextField pAfre=new JTextField(12);
        final JTextField pDura=new JTextField(12);
        final JTextField pPlace=new JTextField(12);
        final JTextField pPnum=new JTextField(12);
        //设置时间的下拉选择框
        String[] year = new String[10];
        for(int y=2020,i=0;y<2030;y++,i++){
            year[i] = String.valueOf(y);
        }
        final JComboBox yearBox=new JComboBox(year);		//设置下拉菜单
        final JComboBox yearBox2=new JComboBox(year);
        yearBox.setName("年");
        String[] month = new String[12];
        for(int i=1;i<=12;i++){
            month[i-1] = String.valueOf(i);
        }
        final JComboBox monthBox=new JComboBox(month);		//设置下拉菜单
        final JComboBox monthBox2=new JComboBox(month);
        monthBox.setName("月");
        String[] day = new String[31];
        for(int i=1;i<=31;i++){
            day[i-1] = String.valueOf(i);
        }
        final JComboBox dayBox=new JComboBox(day);		//设置下拉菜单
        final JComboBox dayBox2=new JComboBox(day);
        dayBox.setName("日");

        //设置组件的布局
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

        //中间面板
        displayMid=new JPanel(new GridBagLayout());

        //底层面板
        JButton jbStore=new JButton("保存");			//设置按钮
        JButton jbReset=new JButton("清除");
        JButton jbQuit=new JButton("退出");
        displayDown=new JPanel(new FlowLayout());
        displayDown.add(jbStore);
        displayDown.add(jbReset);
        displayDown.add(jbQuit);
        displayDown.setBackground(Color.BLACK);

        jifShow.add(displayUp,BorderLayout.NORTH);
        jifShow.add(displayDown,BorderLayout.SOUTH);
        jifShow.setVisible(true);
        jplDisplay.add(jifShow);
        //为jbQuit按钮添加监听器
        jbQuit.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                jifShow.dispose();
            }
        });
        //为jbReset按钮添加监听器
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
        //为jbStore按钮添加监听器
        jbStore.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                //1.空值响应
                String tableName=productBox.getSelectedItem().toString();
                if(tableName.length()==0)//产品类型提示框，1代表消息类型
                {
                    JOptionPane.showMessageDialog(jifShow, "请选择活动级别","提示", 1);
                    return;
                }
                String volunteerName=pName.getText();
                Pattern pattern=Pattern.compile("^[\\040]*");//其中‘\040是空格的八进制转义字符’
                if(pattern.matcher(volunteerName).matches()==true)//产品名称提示框
                {
                    JOptionPane.showMessageDialog(jifShow, "请输入志愿名称","提示", 1);
                    return;
                }
                String volunteerType=pType.getText();
                pattern=Pattern.compile("^[\\040]*");
                if(pattern.matcher(volunteerType).matches()==true)//产品描述提示框
                {
                    JOptionPane.showMessageDialog(jifShow, "请输入活动类别","提示", 1);
                    return;
                }
                String volunteerPlace=pPlace.getText();
                pattern=Pattern.compile("^[\\040]*");
                if(pattern.matcher(volunteerPlace).matches()==true)//产品描述提示框
                {
                    JOptionPane.showMessageDialog(jifShow, "请输入活动地点","提示", 1);
                    return;
                }
                String peopleNum=pPnum.getText();
                pattern=Pattern.compile("^[\\040]*");
                if(pattern.matcher(peopleNum).matches()==true)//产品描述提示框
                {
                    JOptionPane.showMessageDialog(jifShow, "请输入可参加人数","提示", 1);
                    return;
                }
                String beginTime = yearBox.getSelectedItem().toString()+"-"+monthBox.getSelectedItem().toString()+"-"+dayBox.getSelectedItem().toString();
                String regTime = yearBox2.getSelectedItem().toString()+"-"+monthBox2.getSelectedItem().toString()+"-"+dayBox2.getSelectedItem().toString();
                //2.数据库添加数据
                //得到新的编号和所属部门
                String Vno="";
                String Vdepart="";
                try {
                    ldb = new leader_db();
                    ldb.searchActivityNo();
                    ldb.rs.next();
                    int tVno = Integer.parseInt(ldb.rs.getString(1));
                    //新的编号为最后一行编号+1
                    Vno = String.valueOf(tVno+1);
                    ldb.searchMydepart(mno);
                    ldb.rs.next();
                    Vdepart = ldb.rs.getString(1);
                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                //先创建arraylist保存一行的数据
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
                //将arraylist中的数据写入数据库中
                if(ldb.insertActivity(rowdata)==0)
                    JOptionPane.showMessageDialog(jifShow, "添加失败","Error", 1);
                else
                    JOptionPane.showMessageDialog(jifShow, "添加成功","Successful", 1);
            }
        });
    }
}
