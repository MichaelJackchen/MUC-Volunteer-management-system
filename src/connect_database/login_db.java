package connect_database;

import java.sql.*;

public class login_db {
    Connection con;			//声明特定数据库的连接实例
    public Statement stmt;			//声明向数据库发送SQL语句的statement对象
    public ResultSet rs;			//声明结果集，接受查询结果返回的对象
    public login_db() throws ClassNotFoundException, SQLException {
        con = null;
        stmt = null;
        rs = null;
        String url = "jdbc:mysql://127.0.0.1:3306/volunteer?&useSSL=false&serverTimezone=UTC";
        String user = "login";//替换成你自已的数据库用户名
        String password = "000000";//这里替换成你自已的数据库用户密码

        Class.forName("com.mysql.cj.jdbc.Driver");
        con = DriverManager.getConnection(url, user, password);
        con.setAutoCommit(false);
        stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
    }
    protected void finalize()throws Throwable		//析构函数
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
