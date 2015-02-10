package com.github.borione.network;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class ConnectionTest implements Cloneable {
	
	public static final ConnectionTest DEFAULT = new ConnectionTest("jdbc:mysql://localhost:3306", "popup", "root", "masterkey");
	
	private String address;
	private String db;
	private String user;
	private String password;
	private Connection conn;
	private boolean open;
	
	public ConnectionTest(String address, String db, String user, String password) {
		setAddress(address);
		setDb(db);
		setUser(user);
		setPassword(password);
		conn = null;
		open = false;
		openConnection();		
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDb() {
		return db;
	}

	public void setDb(String db) {
		this.db = db;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public boolean isOpen() {
		return open;
	}
	
	public void openConnection() {
		try {
			conn = DriverManager.getConnection(getAddress() + "/" + getDb(), getUser(), getPassword());
			open = true;
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Si è verificato un errore durante la connessione al server.", "ERRORE", JOptionPane.ERROR_MESSAGE, null);
		}
	}

	public void closeConnection() {
		try {
			conn.close();
			open = false;
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Si è verificato un errore durante la disconnessione al server.", "ERRORE", JOptionPane.ERROR_MESSAGE, null);
		}
	}

	public Connection getConnection() {
		return conn;
	}
	
	public ConnectionTest clone() {
		ConnectionTest ct = new ConnectionTest(getAddress(), getDb(), getUser(), getPassword());
		if(!ct.isOpen()) {
			ct.closeConnection();
		}
		return ct;
	}
	
	public static void main(String[] args) {
		ConnectionTest cn = new ConnectionTest("jdbc:mysql://localhost:3306", "popup", "root", "masterkey");
		System.out.println(cn);
		cn.closeConnection();
	}
}
