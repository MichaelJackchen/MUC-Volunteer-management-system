package connect_database;

import java.sql.*;
import java.util.ArrayList;

/*
 * leader_db封装负责人与底层数据库连接
 */
public class leader_db {
    Connection con;			//声明特定数据库的连接实例
    public Statement stmt;			//声明向数据库发送SQL语句的statement对象
    public ResultSet rs;			//声明结果集，接受查询结果返回的对象
    public leader_db() throws ClassNotFoundException, SQLException {
        con = null;
        stmt = null;
        rs = null;
        String url = "jdbc:mysql://127.0.0.1:3306/volunteer?&useSSL=false&serverTimezone=UTC";
        String user = "managers";//替换成你自已的数据库用户名
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
    //查询负责的志愿活动
    public void searchResponsibleActivities(String vamno)
    {
        try{
            rs = stmt.executeQuery("select * from "+"volunteer_activity where Vamno='"+vamno+"'");	//Executes the given SQL statement
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void searchResponsibleActivity(String vano,String mno)
    {
        try{
            rs = stmt.executeQuery("select * from "+"volunteer_activity where Vamno='"+mno+"'"+"and Vano='"+vano+"'");	//Executes the given SQL statement
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    //查询志愿活动表中的最后一行数据得到活动编号
    public void searchActivityNo(){
        try{
            rs = stmt.executeQuery("select * from volunteer_activity order by Vano desc limit 1");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    //查询当前志愿者的所属部门
    public void searchMydepart(String mno){
        try{
            rs = stmt.executeQuery("select mdepart from manager where mno='"+mno+"'");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    //向志愿活动表插入新的活动
    public int insertActivity(ArrayList row){
        stmt=null;
        rs=null;
        try{
            String sql = "INSERT INTO volunteer_activity(vano,vaname,vasec,vamno,vatype,valev,vafre,vadura,vapl,vasnum,vatime,vregtime)";
            sql+="value(";
            for(int i=0;i<row.size();i++)
            {
                if(i==7 || i==9){
                    sql += row.get(i);
                }
                else {
                    sql += "'" + row.get(i) + "'";
                }
                if(i!=row.size()-1)
                    sql+=",";

            }
            sql+=")";
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
    public void searchAppliedVl(String acno){
        try{
            rs = stmt.executeQuery("select * from activities where Ano='"+acno+"'");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    //查询与该志愿有关的志愿者信息和活动状态
    public void searchVolunteers(String vno){
        try{
            rs = stmt.executeQuery("select * from volunteers where Vno='"+vno+"'");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    //查询活动状态
    public void searchActivityStatus(String vno,String vano){
        try{
            rs = stmt.executeQuery("select Aset from activities where sno='"+vno+"'"+" and Ano='"+vano+"'");
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
    //更改志愿活动状态
    public void alterActivityStatus(String vno,String vano,int aset){
        stmt=null;
        rs=null;
        try{
            stmt=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            String sql = "update activities set Aset="+aset+" where sno='"+vno+"'" +" and Ano='"+vano+"'";
            stmt.executeUpdate(sql);
            con.commit();
        }
        catch(SQLException e){
            System.err.println("executeUpdate:"+e.getMessage());
        }
    }
    //修改志愿活动信息
    public int alterActivity(String vano,String alterlist[]){
        stmt=null;
        rs=null;
        try{
            String sql = "update volunteer_activity set Vaname = '"
                    +alterlist[0]+"',"+"Vatype = '"+alterlist[1]+"',"
                    +"Vafre = '"+alterlist[2]+"',"
                    +"Vadura = '"+alterlist[3]+"',"
                    +"Vapl = '"+alterlist[4]+"',"
                    +"Vasnum = '"+alterlist[5]+"',"
                    +"Vatime = '"+alterlist[6]+"',"
                    +"Vregtime = '"+alterlist[7]+"'"
                    +"where (Vano='"+vano+"')";
//            System.out.println(sql);
            stmt=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            int p=stmt.executeUpdate(sql);
            con.commit();
            return p;
        }
        catch(SQLException e){
            System.err.println("executeUpdate:"+e.getMessage());
            return 0;
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
    public void executeUpdate(String sql){
        stmt=null;
        rs=null;
        try{
            stmt=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            stmt.executeUpdate(sql);
            con.commit();
        }
        catch(SQLException e){
            System.err.println("executeUpdate:"+e.getMessage());
        }
    }
}
