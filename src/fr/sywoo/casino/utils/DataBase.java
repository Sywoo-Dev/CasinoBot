package fr.sywoo.casino.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DataBase {

	private Connection connection;

	public void connect(String host, String database, int port, String user, String password){
		if(!isConnected()){
			try { 
				try {
					Class.forName("com.mysql.jdbc.Driver");
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database + "?useUnicode=true&characterEncoding=UTF-8", user, password);
				
			} catch (SQLException e) { e.printStackTrace(); }
		}
	}

	public void disconnect(){
		if(isConnected()){
			try { connection.close();
			} catch (SQLException e) { e.printStackTrace(); }
		}
	}

	public boolean isConnected(){
		try {
			if((connection == null) || (connection.isClosed())) return false;
			return true;
		} catch (SQLException e) { e.printStackTrace(); } return false;
	}

	public Connection getConnection(){
		return connection;
	}
	public Connection getBungeeConnection(){
		return connection;
	}
}
