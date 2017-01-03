package application;

import java.sql.*;
import com.microsoft.sqlserver.jdbc.*;

public class SQL {
	private Connection connection = null;
	public void connect() throws SQLException {
		Statement st = null;
		String connectionStr = "jdbc:sqlserver://MATEUSZPC;datebaseName=Transakcje;integratedSecurity=true;";
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			connection = DriverManager.getConnection(connectionStr);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void select(String table,String ...strings) throws SQLException{
		connection.setAutoCommit(false);
		Statement st = null;
		try{
			System.out.println("EEEEEEE"+connection+":  "+connection.isReadOnly());
			st = connection.createStatement();
			System.out.println(st+"   <<<<<");
			ResultSet rs = st.executeQuery("select * from Transakcje.dbo."+table);
			System.out.println("SSSSSSS: "+rs);
			while(rs.next()){
				for(String string : strings){
					System.out.println(rs.getString(string));
				}
			}
		}catch( SQLException e){
			
		}
	}
	public void insert (String table,int howMany, String ...strings) throws SQLException{
		int i=0;
		connection.setAutoCommit(false);
		Statement st = null;
		try{
			System.out.println("EEEEEEE"+connection+":  "+connection.isReadOnly());
			st = connection.createStatement();
			System.out.println(st+"   <<<<<");
			String command ="insert into Transakcje.dbo."+table +" values (";
			for(String string : strings){
				i++;
				command+=string;
				if(i!=howMany)command+=", ";
				else command+=")";
			}
			System.out.println(command);
			st.executeUpdate(command);
		}catch( SQLException e){
			
		}
	}
	public void execDodawanieRachunku (int one, String ...data) throws SQLException{
		connection.setAutoCommit(false);
		PreparedStatement ps = null;
		try{
			System.out.println("EEEEEEE"+connection+":  "+connection.isReadOnly());
			String command ="EXEC Transakcje.dbo.dodawanieRachunku ?,?,?,?,?,?,?,?,?,?,?";
			System.out.println(command);
			String params[]= new String[10];
			int i=0;
			for(String string : data){
				params[i]=string;
				i++;
			}
			ps = connection.prepareStatement(command);
			ps.setEscapeProcessing(true);
			ps.setQueryTimeout(10);
			for(i=1; i<=11;i++){
				if(i==10) {
					System.out.println(i+" "+one);ps.setInt(10, one);
				}
				else{
					if(i==11){
						System.out.println(i+" "+params[9]);ps.setString(i, params[9]);
					}
					else{
						System.out.println(i+" "+params[i-1]);
						ps.setString(i, params[i-1]);
					}
				}
			}
			ResultSet rs = ps.executeQuery();
			System.out.println(command);
		}catch( SQLException e){
			
		}
	}
}
