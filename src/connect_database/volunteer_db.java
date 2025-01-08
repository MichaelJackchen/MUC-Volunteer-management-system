package connect_database;

import java.sql.*;

/*
 * volunteer_db��װ־Ը����ײ����ݿ�����,��ʵ��volunteer�����ݿ⽻��ʱ�Ĺ���
 */
public class volunteer_db {
    Connection con;			//�����ض����ݿ������ʵ��
    public Statement stmt;			//���������ݿⷢ��SQL����statement����
    public ResultSet rs;			//��������������ܲ�ѯ������صĶ���
    public static int maxId;		//��ŵ�ǰ�����ŵ����ֵ
    public static int maxno;		//��ŵ�ǰ�����ŵ����ֵ
    public volunteer_db() throws ClassNotFoundException, SQLException {
        con=null;
        stmt=null;
        rs=null;
        maxId=0;
        maxno=0;
        String url = "jdbc:mysql://127.0.0.1:3306/volunteer?&useSSL=false&serverTimezone=UTC";
        String user ="volunteers";//�滻�������ѵ����ݿ��û���
        String password = "000000";//�����滻�������ѵ����ݿ��û�����

        Class.forName("com.mysql.cj.jdbc.Driver");
        con = DriverManager.getConnection(url, user, password);
        con.setAutoCommit(false);
        stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);

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
            //�Ȳ�ѧԺ
            rs = stmt.executeQuery("select vxy from "+"volunteers where Vno='"+vno+"'");	//Executes the given SQL statement
            rs.next();
            String xyname = rs.getString(1);
            //�ٲ�
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
    //����ѧ�źͻ����ڻ��¼���в�ѯ�Ƿ�μ�
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
    //ͳ����־Ըʱ��
    public void searchVolunteerTime(String vno){
        try{
            rs = stmt.executeQuery("select sum(Adt) from activities where Aset = '3' and sno='"+vno+"'");
        }catch (Exception e){
            e.printStackTrace();
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
    //ִ�л��¼��Ĵ洢����
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