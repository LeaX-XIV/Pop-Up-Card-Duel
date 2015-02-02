package com.github.borione.crud;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import com.github.borione.network.ConnectionTest;

public class Player {

	private String user;
	private String password;
	private String name;
	private String mail;
	private Timestamp registration;
	private Timestamp lastLogin;
	private int avatar;

	/**
	 * Creates a new instance of the <code>players</code> table.
	 * @param user The username, used to login.
	 * @param password The MD5 of the password.
	 * @param name The name displayed to other players.
	 * @param mail The mail used to register.
	 * @param registration The timestamp of the registration.
	 * @param lastLogin The timestamp of the last login. <code>null</code> if never logged.
	 * @param avatar The id of the avatar used in the game. Default is <code>1</code>.
	 */
	public Player(String user, String password, String name, String mail, Timestamp registration, Timestamp lastLogin, int avatar) {
		setUser(user);
		setPassword(password);
		setName(name);
		setMail(mail);
		setRegistration(registration);
		setLastLogin(lastLogin);
		setAvatar(avatar);
	}

	public static Player factory(String username) {
		Player player = null;
		try {
			ConnectionTest ct = ConnectionTest.DEFAULT.clone();
			Statement stat = ct.getConnection().createStatement();
			ResultSet rs = stat.executeQuery("SELECT * FROM players WHERE user = '" + username + "';");
			if(rs.next()) {
				String user = rs.getString("user");
				String password = rs.getString("password");
				String name = rs.getString("name");
				String mail = rs.getString("mail");
				Timestamp registration = rs.getTimestamp("registration");
				Timestamp lastLogin = rs.getTimestamp("lastlogin");
				int avatar = rs.getInt("avatar");
				
				player = new Player(user, password, name, mail, registration, lastLogin, avatar);
			}
			rs.close();
			stat.close();
			ct.closeConnection();
			return player;
		} catch (SQLException e) {
			throw new IllegalArgumentException("No player was found with user = " + username);
		}
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
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getMail() {
		return mail;
	}
	
	public void setMail(String mail) {
		this.mail = mail;
	}
	
	public Timestamp getRegistration() {
		return registration;
	}
	
	public void setRegistration(Timestamp registration) {
		this.registration = registration;
	}
	
	public Timestamp getLastLogin() {
		return lastLogin;
	}
	
	public void setLastLogin(Timestamp lastLogin) {
		this.lastLogin = lastLogin;
	}

	public int getAvatar() {
		return avatar;
	}
	
	public String getAvatarName() throws RuntimeException {
		String query = "SELECT DISTINCT * FROM avatars WHERE id = " + getAvatar() + ";";
		try {
			ConnectionTest ct = ConnectionTest.DEFAULT.clone();
			Statement stat = ct.getConnection().createStatement();
			ResultSet rs = stat.executeQuery(query);
			rs.next();
			String name = rs.getString("name");
			rs.close();
			stat.close();
			ct.closeConnection();
			return name;
		} catch (SQLException e) {
			throw new RuntimeException("An error occurred during the run of the query\n" + query);
		}
	}

	public void setAvatar(int avatar) {
		this.avatar = avatar;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Username: " + getUser() +
				"\nPassword: " + getPassword() +
				"\nName: " + getName() +
				"\nE-Mail: " + getMail() +
				"\nRegistered " + getRegistration() +
				"\nLast seen: " + getLastLogin() +
				"\nAvatar: " + getAvatarName());
		return sb.toString();
	}

	public static void main(String[] args) {
		System.out.println(Player.factory("LeaX_XIV"));
		System.out.println("\n-----------------------------\n");
		System.out.println(Player.factory("CapraTheBest"));
	}
}
