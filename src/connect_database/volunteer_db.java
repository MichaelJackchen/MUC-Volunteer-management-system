package connect_database;

import java.sql.*;

/*
 * volunteer_db封装志愿者与底层数据库连接,并实现volunteer与数据库交互时的功能
 */
public class volunteer_db {
    Connection con;			//声明特定数据库的连接实例
    public Statement stmt;			//声明向数据库发送SQL语句的statement对象
    public ResultSet rs;			//声明结果集，接受查询结果返回的对象
    public static int maxId;		//存放当前宠物编号的最大值
    public static int maxno;		//存放当前就诊编号的最大值
    public volunteer_db() throws ClassNotFoundException, SQLException {
        con=null;
        stmt=null;
        rs=null;
        maxId=0;
        maxno=0;
        String url = "jdbc:mysql://127.0.0.1:3306/volunteer?&useSSL=false&serverTimezone=UTC";
        String user ="volunteers";//替换成你自已的数据库用户名
        String password = "000000";//这里替换成你自已的数据库用户密码

        Class.forName("com.mysql.cj.jdbc.Driver");
        con = DriverManager.getConnection(url, user, password);
        con.setAutoCommit(false);
        stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);

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
    public void searchVolunteerType(String name)
    {
        try{
            rs = stmt.executeQuery("select * from "+"volunteer_activity where Valev='"+name+"'");	//Executes the given SQL statement
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void searchMyxyActivities(String vno)
    {
        try{
            //先查学院
            rs = stmt.executeQuery("select vxy from "+"volunteers where Vno='"+vno+"'");	//Executes the given SQL statement
            rs.next();
            String xyname = rs.getString(1);
            //再查活动
            rs = stmt.executeQuery("select * from "+"volunteer_activity where Vasec='"+xyname+"'");	//Executes the given SQL statement

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void searchVolunteerNo(String vno)
    {
        try{
            rs = stmt.executeQuery("select * from "+"volunteer_activity where Vano='"+vno+"'");	//Executes the given SQL statement
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void searchActivitiesStatus(int as,String vno)
    {
        try{
            rs = stmt.executeQuery("select ano from "+"activities where sno = '"+vno+"' and "+"Aset= "+as);	//Executes the given SQL statement
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void searchMy(String vno)
    {
        try{
            rs = stmt.executeQuery("select * from "+"volunteers where Vno='"+vno+"'");	//Executes the given SQL statement
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void searchVolunteerActivity(String vano)
    {
        try{
            rs = stmt.executeQuery("select * from "+"volunteer_activity where Vano='"+vano+"'");	//Executes the given SQL statement
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    //根据学号和活动编号在活动记录表中查询是否参加
    public void searchVolunteerTranscibe(String sno,String vano)
    {
        try{
            rs = stmt.executeQuery("select Aset from "+"activities where sno='"+sno+"'"+"and Ano='"+vano+"'");	//Executes the given SQL statement
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void searchVolunteersNum(String acno){
        try{
            rs = stmt.executeQuery("select count(*) from activities where Ano ='"+acno+"'");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    //统计总志愿时长
    public void searchVolunteerTime(String vno){
        try{
            rs = stmt.executeQuery("select sum(Adt) from activities where Aset = '3' and sno='"+vno+"'");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * 执行sql语句，返回结果集rs
     */
    public ResultSet executeQuery(String sql){
        stmt = null;
        rs=null;
        try{
            stmt=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            rs=stmt.executeQuery(sql);
        }
        catch(SQLException e){
            System.err.println("executeQuery:"+e.getMessage());
        }
        return rs;
    }

    /**
     * 执行sql语句
     */
    public int executeUpdate(String sql){
        stmt=null;
        rs=null;
        try{
            stmt=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            int p = stmt.executeUpdate(sql);
            con.commit();
            return p;
        }
        catch(SQLException e){
            System.err.println("executeUpdate:"+e.getMessage());
            return 0;
        }
    }
    //执行活动记录表的存储过程
    public void executeProcedure(String vano,String vno){
        stmt=null;
        rs=null;
        try{

            CallableStatement cs = con.prepareCall("call Activities_insert('"+vano+"','"+vno+"',1)");
            cs.execute();

        }
        catch(SQLException e){
            System.err.println("executeUpdate:"+e.getMessage());
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