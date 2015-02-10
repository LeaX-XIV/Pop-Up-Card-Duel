package com.github.borione.crud;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.github.borione.network.ConnectionTest;
import com.github.borione.util.ListUtils;

public class Effect {

	private int id;
	private String name;
	private String description;

	public Effect(int id, String name, String description) {
		setId(id);
		setName(name);
		setDescription(description);
	}

	public static Effect factory(int id) {
		Effect effect = null;
		try {
			ConnectionTest ct = ConnectionTest.DEFAULT.clone();
			Statement stat = ct.getConnection().createStatement();
			ResultSet rs = stat.executeQuery("SELECT * FROM effects WHERE id = " + id + ";");
			if(rs.next()) {
				String name = rs.getString("name");
				String description = rs.getString("description");

				effect = new Effect(id, name, description);
			}
			rs.close();
			stat.close();
			ct.closeConnection();
			return effect;
		} catch (SQLException e) {
			throw new IllegalArgumentException("No effect was found with id = " + id);
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

	public List<CpCost> retriveCpCost() {
		List<CpCost> results = new ArrayList<CpCost>();
		for (Color cp : Color.values()) {
			try {
				results.add(CpCost.factory(getId(), cp));
			}catch(IllegalArgumentException e) {
				// Do nothing
			}
		}
		return ListUtils.deleteNull(results);
	}
	
	public String getCpCost() {
		StringBuilder sb = new StringBuilder();
		List<CpCost> cpCosts = retriveCpCost();
		cpCosts.remove(null);
		if(cpCosts.isEmpty()) {
			sb.append("null");
		}
		else {
			for (CpCost cpCost : cpCosts) {
				sb.append(cpCost.getCost() + " " + cpCost.getCp() + ", ");
			}
			sb.delete(sb.length() - 2, sb.length());
		}
		
		return sb.toString();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Id: " + getId() +
				"\nName: " + getName() +
				"\nDescription: " + getDescription() +
				"\nCost: " + getCpCost());
		return sb.toString();
	}
	
	@Override
	public boolean equals(Object o) {
		boolean equals = false;
		
		if (o instanceof Effect) {
			Effect effect = (Effect) o;
			
			if(getId() == effect.getId() &&
					getName().equals(effect.getName()) &&
					getDescription().equals(effect.getDescription())) {
				equals = true;
			}			
		}
		
		return equals;
	}
	
	public static void main(String[] args) {
		System.out.println(Effect.factory(1));
	}
}
