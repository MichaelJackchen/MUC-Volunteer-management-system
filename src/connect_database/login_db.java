package connect_database;

import java.sql.*;

public class login_db {
    Connection con;			//�����ض����ݿ������ʵ��
    public Statement stmt;			//���������ݿⷢ��SQL����statement����
    public ResultSet rs;			//��������������ܲ�ѯ������صĶ���
    public login_db() throws ClassNotFoundException, SQLException {
        con = null;
        stmt = null;
        rs = null;
        String url = "jdbc:mysql://127.0.0.1:3306/volunteer?&useSSL=false&serverTimezone=UTC";
        String user = "login";//�滻�������ѵ����ݿ��û���
        String password = "000000";//�����滻�������ѵ����ݿ��û�����

        Class.forName("com.mysql.cj.jdbc.Driver");
        con = DriverManager.getConnection(url, user, password);
        con.setAutoCommit(false);
        stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
    }
    protected void finalize()throws Throwable		//��������
    {
        if(rs!=null)
            rs.close();
        if(stmt!=null)
            stmt.close();
        if(con!=null)
            con.close();
    }
    public void execute(String sql){
        try{
            rs = stmt.executeQuery(sql);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void closeStmt(){
        try{
            stmt.close();
        }
        catch(SQLException e){
            System.err.println("closeStmt:"+e.getMessage());
        }
    }
    public void closeConn(){
        try{
            con.close();
        }
        catch(SQLException ex){
            System.err.println("aq.closeConn:"+ex.getMessage());
        }
    }
}
