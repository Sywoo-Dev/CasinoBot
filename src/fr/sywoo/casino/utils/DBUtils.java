package fr.sywoo.casino.utils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import fr.sywoo.casino.Main;

public class DBUtils {
	
	public static int getInt(String table, String champs, String where, String where_value) {
		int value = 0;
		
		try {
			PreparedStatement statement = Main.db.getConnection().prepareStatement("SELECT * FROM " + table + " WHERE " + where + " = ?");
			statement.setString(1, where_value);
			statement.executeQuery();
			 
			ResultSet results = statement.getResultSet();
			
			while(results.next()) {
				value = results.getInt(champs);
			}
			
			results.close();
			statement.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return value;
	}
	
	public static long getLong(String table, String champs, String where, String where_value) {
		long value = 0;
		
		try {
			PreparedStatement statement = Main.db.getConnection().prepareStatement("SELECT * FROM " + table + " WHERE " + where + " = ?");
			statement.setString(1, where_value);
			statement.executeQuery();

			ResultSet results = statement.getResultSet();
			
			while(results.next()) {
				value = results.getLong(champs);
			}
			
			results.close();
			statement.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return value;
	}
	
	public static String getString(String table, String champs, String where, String where_value) {
		String value = null;
		
		try {
			PreparedStatement statement = Main.db.getConnection().prepareStatement("SELECT * FROM " + table + " WHERE " + where + " = ?");
			statement.setString(1, where_value);
			statement.executeQuery();

			ResultSet results = statement.getResultSet();
			
			while(results.next()) {
				value = results.getString(champs);
			}
			
			results.close();
			statement.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return value;
	}
	
	public static String getString(String table, String champs, String where, long where_value) {
		String value = null;
		
		try {
			PreparedStatement statement = Main.db.getConnection().prepareStatement("SELECT * FROM " + table + " WHERE " + where + " = ?");
			statement.setLong(1, where_value);
			statement.executeQuery();

			ResultSet results = statement.getResultSet();
			
			while(results.next()) {
				value = results.getString(champs);
			}
			
			results.close();
			statement.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return value;
	}
	
	public static double getDouble(String table, String champs, String where, String where_value) {
		double value = 0.0;
		
		try {
			PreparedStatement statement = Main.db.getConnection().prepareStatement("SELECT * FROM " + table + " WHERE " + where + " = ?");
			statement.setString(1, where_value);
			statement.executeQuery();

			ResultSet results = statement.getResultSet();
			
			while(results.next()) {
				value = results.getDouble(champs);
			}
			
			results.close();
			statement.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return value;
	}

	public static void updateInt(String table, String champs, String where, String where_value, int value) {
		
		try {
			PreparedStatement statement = Main.db.getConnection().prepareStatement("UPDATE " + table + " SET " + champs + " = ? WHERE " + where + " = ?");
			statement.setInt(1, value);
			statement.setString(2, where_value);
			statement.executeUpdate();
			statement.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void updateString(String table, String champs, String where, String where_value, String value) {
		
		try {
			PreparedStatement statement = Main.db.getConnection().prepareStatement("UPDATE " + table + " SET " + champs + " = ? WHERE " + where + " = ?");
			statement.setString(1, value);
			statement.setString(2, where_value);
			statement.executeUpdate();
			statement.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public static void updateDouble(String table, String champs, String where, String where_value, double value) {
		
		try {
			PreparedStatement statement = Main.db.getConnection().prepareStatement("UPDATE " + table + " SET " + champs + " = ? WHERE " + where + " = ?");
			statement.setDouble(1, value);
			statement.setString(2, where_value);
			statement.executeUpdate();
			statement.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public static void updateLong(String table, String champs, String where, String where_value, long value) {
		
		try {
			PreparedStatement statement = Main.db.getConnection().prepareStatement("UPDATE " + table + " SET " + champs + " = ? WHERE " + where + " = ?");
			statement.setLong(1, value);
			statement.setString(2, where_value);
			statement.executeUpdate();
			statement.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
