package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.scene.control.Label;
import javafx.scene.control.TableView;

public class SQL {
	private Connection connection = null;
	public void connect() throws SQLException {
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
	public void selectForSaldo(Label node)throws SQLException{
		connect();
		connection.setAutoCommit(false);
		Statement st = null;
		try{
			st = connection.createStatement();
			ResultSet rs = st.executeQuery(		"SELECT SUM(Kwota) FROM Transakcje.dbo.Transakcje");
			while(rs.next()){
				node.setText("SALDO: "+rs.getDouble(1));
			}
		}catch( SQLException e){
			System.out.println(e.getMessage());
		}
	}
	public void select(String table,String ...strings) throws SQLException{
		connect();
		connection.setAutoCommit(false);
		Statement st = null;
		try{
			st = connection.createStatement();
			System.out.println(st+"   <<<<<");
			ResultSet rs = st.executeQuery("select * from Transakcje.dbo."+table);
			while(rs.next()){
				for(String string : strings){
					System.out.println(rs.getString(string));
				}
			}
		}catch( SQLException e){
			
		}
	}
	public void insert (String table,int howMany, String ...strings) throws SQLException{
		connect();
		int i=0;
		connection.setAutoCommit(false);
		Statement st = null;
		try{
			st = connection.createStatement();
			String command ="insert into Transakcje.dbo."+table +" values (";
			for(String string : strings){
				i++;
				command+=string;
				if(i!=howMany)command+=", ";
				else command+=")";
			}
			st.executeUpdate(command);
		}catch( SQLException e){
			
		}
	}
	public boolean execUsuwanieRachunku ( int one, int two, String ...data) throws SQLException{
		boolean withouterror = true;
		try {
			connect();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			withouterror = false;
		}
		try {
			connection.setAutoCommit(false);
		} catch (SQLException e1) {
			withouterror = false;
		}
		PreparedStatement ps = null;
		try{
			String command ="EXEC Transakcje.dbo.usuwanieRachunku ?,?,?,?,?,?,?,?,?,?,?,?";
			String params[]= new String[10];
			int i=0;
			for(String string : data){
				params[i]=string;
				i++;
			}
			ps = connection.prepareStatement(command);
			ps.setEscapeProcessing(true);
			ps.setQueryTimeout(10);
			ps.setInt(1, one);
			for(i=2; i<=12;i++){
				if(i==11) {
					ps.setInt(11, two);
				}
				else{
					if(i==12){
						ps.setString(i, params[9]);
					}
					else{
						ps.setString(i, params[i-2]);
					}
				}
			}
			ps.executeUpdate();
			connection.commit();
		}catch( SQLException e){
			withouterror = false;
			System.out.println(e.getMessage());
		}finally{
			if( ps!= null){
				try {
					ps.close();
				} catch (SQLException e) {
					withouterror = false;
				}
			}
			if(connection != null){
				try {
					connection.close();
				} catch (SQLException e) {
					withouterror = false;
				}
			}
		}
		return withouterror;
	}
	public void execDodawanieRachunku (int one, String ...data) throws SQLException{
		connect();
		connection.setAutoCommit(false);
		PreparedStatement ps = null;
		try{
			String command ="EXEC Transakcje.dbo.dodawanieRachunku ?,?,?,?,?,?,?,?,?,?,?";
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
					ps.setInt(10, one);
				}
				else{
					if(i==11){
						ps.setString(i, params[9]);
					}
					else{
						ps.setString(i, params[i-1]);
					}
				}
			}
			ps.executeUpdate();
			connection.commit();
		}catch( SQLException e){
			System.out.println(e.getMessage());
		}finally{
			if( ps!= null){
				ps.close();
			}
			if(connection != null){
				connection.close();
			}
		}
	}
	public void selectForView(TableView<Data> table) throws SQLException{
		connect();
		String array[] = new String[13];
		connection.setAutoCommit(false);
		Statement st = null;
		try{
			st = connection.createStatement();
			System.out.println(st+"   <<<<<");
			String strings[] = new String[13];
			strings[0] ="A"; //"Transakcje.ID_Transakcji";
			strings[1] ="B";// "Transakcje.Data";
			strings[2] = "C";//"TypTransakcji.NazwaTypu";
			strings[3] = "D";//"RodzajTransakcji.Nazwa";
			strings[4] = "E";//"Artykul.NazwaArtykulu";
			strings[5] = "F";//"Podmiot.NazwaPodmiotu";
			strings[6] = "G";//"Lokalizacja.Ulica";
			strings[7] = "H";//"Lokalizacja.NrBudynku";
			strings[8] = "I";//"Lokalizacja.Miasto";
			strings[9] = "J";//"Lokalizacja.KodPocztowy";
			strings[10] = "K";//"Transakcje.Kwota";
			strings[11] = "L";//"Transakcje.Uwagi";
			strings[12] = "M";	
			ResultSet rs = st.executeQuery("SELECT Transakcje.dbo.Transakcje.ID_Transakcji as A , "
					+ "Transakcje.dbo.Transakcje.Data as B , "
					+ "Transakcje.dbo.TypTransakcji.NazwaTypu as C , "
					+ "Transakcje.dbo.RodzajTransakcji.Nazwa as D , Transakcje.dbo.Artykul.NazwaArtykulu as E , "
					+ "Transakcje.dbo.Podmiot.NazwaPodmiotu as F , "
					+ "Transakcje.dbo.Lokalizacja.Ulica as G , Transakcje.dbo.Lokalizacja.NrBudynku as H ,"
					+ " Lokalizacja.Miasto as I , Lokalizacja.KodPocztowy as J , "
					+ "Transakcje.Kwota as K , Transakcje.Uwagi as L , Budzet.Saldo as M FROM Transakcje.dbo.Budzet "
					+ "INNER JOIN Transakcje.dbo.Transakcje ON Budzet.Transakcja = Transakcje.ID_Transakcji "
					+ "INNER JOIN Transakcje.dbo.Podmiot ON Transakcje.ID_Podmiot = Podmiot.ID_Podmiotu "
					+ "INNER JOIN Transakcje.dbo.Artykul ON Transakcje.ID_Artykul = Artykul.ID_Artykulu "
					+ "INNER JOIN Transakcje.dbo.TypTransakcji ON Transakcje.ID_TypTransakcji = TypTransakcji.ID_Typ "
					+ "INNER JOIN Transakcje.dbo.RodzajTransakcji ON Transakcje.ID_RodzajTransakcji = RodzajTransakcji.ID_Rodzaj "
					+ "INNER JOIN Transakcje.dbo.Lokalizacja ON Podmiot.ID_Lokalizacja = Lokalizacja.ID_Lokalizacji"
					+ " ORDER BY B");
			while(rs.next()){
				int i =0;
				for(String string : strings){
					array[i]=rs.getString(string);
					i++;
				}
				table.getItems().add(new Data(array));
			}
		}catch( SQLException e){
			System.out.println(e.getMessage());
		}
	}
	public boolean execModyfikowanieRachunku (int one,int two, String ...data) {
		boolean withouterror = true;
		try {
			connect();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			withouterror = false;
		}
		try {
			connection.setAutoCommit(false);
		} catch (SQLException e1) {
			withouterror = false;
		}
		PreparedStatement ps = null;
		try{
			String command ="EXEC Transakcje.dbo.modyfikowanieRachunku ?,?,?,?,?,?,?,?,?,?,?,?";
			String params[]= new String[10];
			int i=0;
			for(String string : data){
				params[i]=string;
				i++;
			}
			ps = connection.prepareStatement(command);
			ps.setEscapeProcessing(true);
			ps.setQueryTimeout(10);
			ps.setInt(1, one);
			for(i=2; i<=12;i++){
				if(i==11) {
					ps.setInt(11, two);
				}
				else{
					if(i==12){
						ps.setString(i, params[9]);
					}
					else{
						ps.setString(i, params[i-2]);
					}
				}
			}
			ps.executeUpdate();
			connection.commit();
		}catch( SQLException e){
			withouterror = false;
			System.out.println(e.getMessage());
		}finally{
			if( ps!= null){
				try {
					ps.close();
				} catch (SQLException e) {
					withouterror = false;
				}
			}
			if(connection != null){
				try {
					connection.close();
				} catch (SQLException e) {
					withouterror = false;
				}
			}
		}
		return withouterror;
	}	
}
