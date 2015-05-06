package com.github.borione.crud;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.github.borione.connection.ConnectionTest;
import com.github.borione.util.Consts;

public class Esper {
	
	public static ConnectionTest SERVER_DEFAULT = new ConnectionTest(Consts.DB_ADDRESS, Consts.DB_NAME, Consts.DB_USER, Consts.DB_PASSWORD);
	
	private int id;
	private String name;
	
	public Esper(int id, String name) {
		setId(id);
		setName(name);
	}
	
	public static Esper factory(int id) {
		Esper esper = null;
		try {
			SERVER_DEFAULT.openConnection();
			Statement stat = SERVER_DEFAULT.getConnection().createStatement();
			ResultSet rs = stat.executeQuery("SELECT * FROM espers WHERE id = " + id + ";");
			if(rs.next()) {
				String name = rs.getString("name");
				
				esper = new Esper(id, name);
			}
			rs.close();
			stat.close();
			SERVER_DEFAULT.closeConnection();
			return esper;
		} catch (SQLException e) {
			throw new IllegalArgumentException("No esper was found with id = " + id);
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
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Id: " + getId() +
				"\nName: " + getName());
		return sb.toString();
	}
	
	@Override
	public boolean equals(Object o) {
		boolean equals = false;
		
		if (o instanceof Esper) {
			Esper esper = (Esper) o;
			
			if(getId() == esper.getId() &&
					getName().equals(esper.getName())) {
				equals = true;
			}
		}
		
		return equals;
	}

}
