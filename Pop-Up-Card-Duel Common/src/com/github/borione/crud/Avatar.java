package com.github.borione.crud;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.github.borione.connection.ConnectionTest;
import com.github.borione.util.Consts;

public class Avatar {
	
	public static ConnectionTest SERVER_DEFAULT = new ConnectionTest(Consts.DB_ADDRESS, Consts.DB_NAME, Consts.DB_USER, Consts.DB_PASSWORD);
	
	private int id;
	private String name;
	private String description;
	private String path;
	
	/**
	 * Creates a new instance of the <code>avatars</code> table.
	 * @param id The id.
	 * @param name The name of the avatar.
	 * @param description A short description of the avatar.
	 * @param path The path where the avatar picture is.
	 */
	public Avatar(int id, String name, String description, String path) {
		setId(id);
		setName(name);
		setDescription(description);
		setPath(path);
	}
	
	/**
	 * Factors a <code>Avatar</code> given the id.<br>
	 * The avatar must exist on the database, or it would throw an exception.
	 * @param id The id of an existing avatar.
	 * @return A <code>Avatar</code> object with the characteristics of the pointed record of the db.
	 * @throws IllegalArgumentException If the avatar with the given id doesn't exist.
	 */
	public static Avatar factory(int id) throws IllegalArgumentException {
		Avatar avatar = null;
		try {
			SERVER_DEFAULT.openConnection();
			Statement stat = SERVER_DEFAULT.getConnection().createStatement();
			ResultSet rs = stat.executeQuery("SELECT * FROM avatars WHERE id = " + id + ";");
			if(rs.next()) {
				String name = rs.getString("name");
				String description = rs.getString("description");
				String path = rs.getString("path");
				
				avatar = new Avatar(id, name, description, path);
			}
			rs.close();
			stat.close();
			SERVER_DEFAULT.closeConnection();
			return avatar;
		} catch (SQLException e) {
			throw new IllegalArgumentException("No avatar was found with id = " + id);
		}
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getPath() {
		return path;
	}
	
	public void setPath(String path) {
		this.path = path;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Id: " + getId() +
				"\nName: " + getName() +
				"\nDescription: " + getDescription() +
				"\nPath: " + getPath());
		return sb.toString();
	}
	
	@Override
	public boolean equals(Object o) {
		boolean equals = false;
		
		if (o instanceof Avatar) {
			Avatar avatar = (Avatar) o;
			if(getId() == avatar.getId() &&
					getName().equals(avatar.getName()) &&
					getDescription().equals(avatar.getDescription()) &&
					getPath().equals(avatar.getPath())) {
				equals = true;
			}
		}
		
		return equals;
	}
	
	public static void main(String[] args) {
		Avatar def = Avatar.factory(1);
		System.out.print(def);
	}
}
