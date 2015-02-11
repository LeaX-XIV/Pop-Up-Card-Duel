package com.github.borione.crud;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.github.borione.network.ConnectionTest;

enum Position {
	UP,
	DOWN,
	LEFT,
	RIGHT
}

enum Type {
	ATTACK,
	DEFENSE
}

public class Action {
	
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
			ConnectionTest ct = ConnectionTest.DEFAULT.clone();
			Statement stat = ct.getConnection().createStatement();
			ResultSet rs = stat.executeQuery("SELECT * FROM actions WHERE id = " + id + ";");
			if(rs.next()) {
				Position position = Position.valueOf(rs.getString("position"));
				Type type = Type.valueOf(rs.getString("type"));
				
				action = new Action(id, position, type);
			}
			rs.close();
			stat.close();
			ct.closeConnection();
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
