package connect_database;

import java.sql.*;
import java.util.ArrayList;

/*
 * my_db��װϵͳ����Ա��ײ����ݿ�����
 */
public class my_db {
	Connection con;			//�����ض����ݿ������ʵ��
	public Statement stmt;			//���������ݿⷢ��SQL����statement����
	public ResultSet rs;			//��������������ܲ�ѯ������صĶ���
	public my_db() throws ClassNotFoundException, SQLException {
		con = null;
		stmt = null;
		rs = null;
		String url = "jdbc:mysql://127.0.0.1:3306/volunteer?&useSSL=false&serverTimezone=UTC";
		String user = "admin";//�滻�������ѵ����ݿ��û���
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
	public void searchVolunteerActivity(String vano)
	{
		try{
			rs = stmt.executeQuery("select * from "+"volunteer_activity where Vano='"+vano+"'");	//Executes the given SQL statement
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	public void searchAllActivities()
	{
		try{
			rs = stmt.executeQuery("select * from volunteer_activity");	//Executes the given SQL statement
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	public void searchAllVolunteers()
	{
		try{
			rs = stmt.executeQuery("select * from volunteers");	//Executes the given SQL statement
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	public void searchAllManagers()
	{
		try{
			rs = stmt.executeQuery("select * from manager");	//Executes the given SQL statement
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	public void searchAllRecords()
	{
		try{
			rs = stmt.executeQuery("select * from activities");	//Executes the given SQL statement
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	public void searchVolunteer(String vno)
	{
		try{
			rs = stmt.executeQuery("select * from "+"volunteers where Vno='"+vno+"'");	//Executes the given SQL statement
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	public void searchManager(String vno)
	{
		try{
			rs = stmt.executeQuery("select * from "+"manager where mno='"+vno+"'");	//Executes the given SQL statement
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	public int alterActivity(String vano,String alterlist[],String name[]){
		stmt=null;
		rs=null;
		try{
			String sql ="update volunteer_activity set ";
			for(int i=0;i<alterlist.length-1;i++){
				sql += name[i]+"='"+alterlist[i]+"',";
			}
			sql += name[alterlist.length-1]+"='"+alterlist[alterlist.length-1]+"'";
			sql += "where (Vano='"+vano+"')";
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
	public int alterVolunteer(String vno,String alterlist[],String name[]){
		stmt=null;
		rs=null;
		try{
			String sql ="update volunteers set ";
			for(int i=0;i<alterlist.length-1;i++){
				sql += name[i]+"='"+alterlist[i]+"',";
			}
			sql += name[alterlist.length-1]+"='"+alterlist[alterlist.length-1]+"'";
			sql += "where (Vno='"+vno+"')";
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
	public int alterManager(String vno,String alterlist[],String name[]){
		stmt=null;
		rs=null;
		try{
			String sql ="update manager set ";
			for(int i=0;i<alterlist.length-1;i++){
				sql += name[i]+"='"+alterlist[i]+"',";
			}
			sql += name[alterlist.length-1]+"='"+alterlist[alterlist.length-1]+"'";
			sql += "where (mno='"+vno+"')";
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
	public void deleteActivityRecord(String ano,String sno){
		stmt=null;
		rs=null;
		try{
			String sql="delete from Activities where sno='"+sno+"' "+"and ano='"+ano+"'";
			stmt=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			stmt.executeUpdate(sql);
			con.commit();
		}
		catch(SQLException e){
			System.err.println("executeUpdate:"+e.getMessage());
		}
	}
	public int insertVolunteer(ArrayList row,String rowname[]){
		stmt=null;
		rs=null;
		try{
			String sql = "INSERT INTO Volunteers(";
			for(int i=0;i<rowname.length-1;i++){
				sql += rowname[i]+",";
			}
			sql+=rowname[rowname.length-1]+") value(";
			for(int i=0;i<row.size();i++)
			{

				sql += "'" + row.get(i) + "'";
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
	public int insertManger(ArrayList row,String rowname[]){
		stmt=null;
		rs=null;
		try{
			String sql = "INSERT INTO manager(";
			for(int i=0;i<rowname.length-1;i++){
				sql += rowname[i]+",";
			}
			sql+=rowname[rowname.length-1]+") value(";
			for(int i=0;i<row.size();i++)
			{

				sql += "'" + row.get(i) + "'";
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
