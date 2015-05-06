package com.github.borione.crud;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.github.borione.connection.ConnectionTest;
import com.github.borione.util.Consts;

public class Action {
	
	public static ConnectionTest SERVER_DEFAULT = new ConnectionTest(Consts.DB_ADDRESS, Consts.DB_NAME, Consts.DB_USER, Consts.DB_PASSWORD);
	
	private int id;
	private Position position;
	private Type type;
	
	public Action(int id, Position position, Type type) {
		setId(id);
		setPosition(position);
		setType(type);
	}
	
	public static Action factory(int id) {
		Action action = null;
		try {
			SERVER_DEFAULT.openConnection();
			Statement stat = SERVER_DEFAULT.getConnection().createStatement();
			ResultSet rs = stat.executeQuery("SELECT * FROM actions WHERE id = " + id + ";");
			if(rs.next()) {
				Position position = Position.valueOf(rs.getString("position"));
				Type type = Type.valueOf(rs.getString("type"));
				
				action = new Action(id, position, type);
			}
			rs.close();
			stat.close();
			SERVER_DEFAULT.closeConnection();
			return action;
		} catch (SQLException e) {
			throw new IllegalArgumentException("No action was found with id = " + id);
		}
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Id: " + getId() +
				"\nPosition: " + getPosition() +
				"\nType: " + getType());
		return sb.toString();
	}
	
	@Override
	public boolean equals(Object o) {
		boolean equals = false;
		
		if (o instanceof Action) {
			Action action = (Action) o;
			if(getId() == action.getId() &&
					getPosition().equals(action.getPosition()) &&
					getType().equals(action.getType())) {
				equals = true;
			}
		}
		
		return equals;
	}
	
	public static void main(String[] args) {
		for (int i = 1; i <= 8; i++) {
			System.out.println(Action.factory(i));			
		}
	}
}
