package connect_database;

import java.sql.*;
import java.util.ArrayList;

/*
 * leader_db��װ��������ײ����ݿ�����
 */
public class leader_db {
    Connection con;			//�����ض����ݿ������ʵ��
    public Statement stmt;			//���������ݿⷢ��SQL����statement����
    public ResultSet rs;			//��������������ܲ�ѯ������صĶ���
    public leader_db() throws ClassNotFoundException, SQLException {
        con = null;
        stmt = null;
        rs = null;
        String url = "jdbc:mysql://127.0.0.1:3306/volunteer?&useSSL=false&serverTimezone=UTC";
        String user = "managers";//�滻�������ѵ����ݿ��û���
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
    //��ѯ�����־Ը�
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
    //��ѯ־Ը����е����һ�����ݵõ�����
    public void searchActivityNo(){
        try{
            rs = stmt.executeQuery("select * from volunteer_activity order by Vano desc limit 1");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    //��ѯ��ǰ־Ը�ߵ���������
    public void searchMydepart(String mno){
        try{
            rs = stmt.executeQuery("select mdepart from manager where mno='"+mno+"'");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    //��־Ը�������µĻ
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
    //��ѯ���־Ը�йص�־Ը����Ϣ�ͻ״̬
    public void searchVolunteers(String vno){
        try{
            rs = stmt.executeQuery("select * from volunteers where Vno='"+vno+"'");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    //��ѯ�״̬
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
    //����־Ը�״̬
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
    //�޸�־Ը���Ϣ
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
     * ִ��sql��䣬���ؽ����rs
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
     * ִ��sql���
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
