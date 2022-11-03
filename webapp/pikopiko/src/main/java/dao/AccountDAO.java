package dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.Account;
import model.Recorder;
import model.User;


public class AccountDAO {
	private final String JDBC_URL = "jdbc:mysql://localhost:3306/pikopiko";
	private final String DB_USER = "root";
	private final String DB_PASS = "root";
	String msg;
	public Account findByLogin(User user) {
		Account account = null;

		try {
		      Class.forName("com.mysql.cj.jdbc.Driver");
		      msg = "ドライバのロードに成功しました";
		    }catch (ClassNotFoundException e){
		      msg = "ドライバのロードに失敗しました";
		    }catch (Exception e){
		      msg = "ドライバのロードに失敗しました";
		    }

		try (Connection conn = DriverManager.getConnection(JDBC_URL,DB_USER,DB_PASS)){
			//SQLの命令
			String sql = "SELECT USERID,NAME FROM ACCOUNT WHERE PASS=? AND NAME=?" ;
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, user.getPass());
			pStmt.setString(2, user.getName());
			//SQLの命令実行			
			ResultSet rs = pStmt.executeQuery();
			//実行結果取得
			if(rs.next()) {
				String userName = rs.getString("NAME");
				int userId = rs.getInt("USERID");
				account = new Account(userName,userId);
			}
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
		return account;
	}
	public Account createUser(User user) {
		Account account = null;

		try {
		      Class.forName("com.mysql.cj.jdbc.Driver");
		      msg = "ドライバのロードに成功しました";
		    }catch (ClassNotFoundException e){
		      msg = "ドライバのロードに失敗しました";
		    }catch (Exception e){
		      msg = "ドライバのロードに失敗しました";
		    }

		try (Connection conn = DriverManager.getConnection(JDBC_URL,DB_USER,DB_PASS)){
			//SQLの命令
			String sqlInsert = "INSERT INTO ACCOUNT(NAME,PASS) VALUES(?,?)";
			String sqlSelect = "SELECT USERID,NAME,PASS FROM ACCOUNT WHERE NAME=? AND PASS=?";
			PreparedStatement pStmtInsert = conn.prepareStatement(sqlInsert);
			PreparedStatement pStmtSelect = conn.prepareStatement(sqlSelect);
			pStmtInsert.setString(1, user.getName());
			pStmtInsert.setString(2, user.getPass());
			pStmtSelect.setString(1, user.getName());
			pStmtSelect.setString(2, user.getPass());
			//SQLの命令実行			
			int result = pStmtInsert.executeUpdate();

			//実行結果取得
			if(result!=1) {
				return account;
			}
			ResultSet rs = pStmtSelect.executeQuery();
			if(rs.next()) {
				String userName = rs.getString("NAME");
				int userId = rs.getInt("USERID");
				account = new Account(userName,userId);
			}

		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
		return account;
	}
	
	public Recorder createRecord(Account account,ArrayList<Integer> orderKeys,ArrayList<Integer> orderTimes,String title,int mode) {
		Recorder recorder = null;
		try {
		      Class.forName("com.mysql.cj.jdbc.Driver");
		      msg = "ドライバのロードに成功しました";
		    }catch (ClassNotFoundException e){
		      msg = "ドライバのロードに失敗しました";
		    }catch (Exception e){
		      msg = "ドライバのロードに失敗しました";
		    }

		try (Connection conn = DriverManager.getConnection(JDBC_URL,DB_USER,DB_PASS)){
			//SQLの命令
			String sqlSelect1 = "SELECT * FROM RECORDER WHERE USERID=?";
			String sqlInsert = "INSERT INTO RECORDER(USERID,TITLE,MODE,MUSICKEY,MUSICTIME) VALUES(?,?,?,?,?)";
			String sqlSelect2 = "SELECT TITLE,MODE,MUSICKEY,MUSICTIME FROM RECORDER WHERE USERID=?";
			String sqlSelect3 = "SELECT NAME FROM ACCOUNT WHERE USERID=?";
			PreparedStatement pStmtSelect1 = conn.prepareStatement(sqlSelect1);			
			PreparedStatement pStmtInsert = conn.prepareStatement(sqlInsert);
			PreparedStatement pStmtSelect2 = conn.prepareStatement(sqlSelect2);			
			PreparedStatement pStmtSelect3 = conn.prepareStatement(sqlSelect3);			
			pStmtSelect1.setInt(1, account.getUserId());
			pStmtInsert.setInt(1, account.getUserId());
			pStmtInsert.setString(2, title);
			pStmtInsert.setInt(3, mode);
			pStmtInsert.setString(4, orderKeys.toString());
			pStmtInsert.setString(5, orderTimes.toString());
			pStmtSelect2.setInt(1, account.getUserId());
			pStmtSelect3.setInt(1, account.getUserId());
			String userName = null;
			String musicTitle = null;
			int musicMode = 0;
			String musicKeys = null;
			String musicTimes = null;
			//SQLの命令実行						
			ResultSet rs1 = pStmtSelect1.executeQuery();
			if(rs1.next()) {
			}

			int result = pStmtInsert.executeUpdate();
			//実行結果取得
			if(result!=1) {
			}
			
			ResultSet rs2 = pStmtSelect2.executeQuery();
			if(rs2.next()) {
				musicTitle = rs2.getString("TITLE");
				musicMode = rs2.getInt("MODE");
				musicKeys = rs2.getString("MUSICKEY");
				musicTimes = rs2.getString("MUSICTIME");
				//recorder = new Recorder(musicTitle,userName,musicMode, musicKeys, musicTimes);
				
			}
			ResultSet rs3 = pStmtSelect3.executeQuery();
			if(rs3.next()) {
				userName = rs3.getString("NAME");				
				
			}
			recorder = new Recorder(musicTitle,userName,musicMode, musicKeys, musicTimes);


		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
		return recorder;
	}
	
	public int getRecordCount(Account account) {
		int musicCount = 0;
		try {
		      Class.forName("com.mysql.cj.jdbc.Driver");
		      msg = "ドライバのロードに成功しました";
		    }catch (ClassNotFoundException e){
		      msg = "ドライバのロードに失敗しました";
		    }catch (Exception e){
		      msg = "ドライバのロードに失敗しました";
		    }
		try (Connection conn = DriverManager.getConnection(JDBC_URL,DB_USER,DB_PASS)){
			//SQLの命令
			String sqlSelect = "SELECT COUNT(MUSICID) as CNT FROM RECORDER WHERE USERID=?";
			PreparedStatement pStmtSelect = conn.prepareStatement(sqlSelect);			
			pStmtSelect.setInt(1, account.getUserId());
			ResultSet rs = pStmtSelect.executeQuery();
			if(rs.next()) {
				musicCount = rs.getInt("CNT");
			}

		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			return 0;
		}
		return musicCount;		
	}
	public ArrayList<String> getRecordTitle(Account account) {
		String musicTitle = null;
		ArrayList<String> titleList = new ArrayList<String>();
		try {
		      Class.forName("com.mysql.cj.jdbc.Driver");
		      msg = "ドライバのロードに成功しました";
		    }catch (ClassNotFoundException e){
		      msg = "ドライバのロードに失敗しました";
		    }catch (Exception e){
		      msg = "ドライバのロードに失敗しました";
		    }
		try (Connection conn = DriverManager.getConnection(JDBC_URL,DB_USER,DB_PASS)){
			//SQLの命令
			String sqlSelect = "SELECT IFNULL(TITLE,'タイトルがありません') as title FROM RECORDER WHERE USERID=?";
			PreparedStatement pStmtSelect = conn.prepareStatement(sqlSelect);			
			pStmtSelect.setInt(1, account.getUserId());
			ResultSet rs = pStmtSelect.executeQuery();
			while(rs.next()) {
				musicTitle = rs.getString("title");
				titleList.add(musicTitle);
			}
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
		return titleList;

		
	}
}
