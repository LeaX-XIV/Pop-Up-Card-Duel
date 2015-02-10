package com.github.borione.crud;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.github.borione.network.ConnectionTest;

enum Color {
	RED,
	GREEN,
	BLUE,
	YELLOW
};

public class CpCost {
	
	private int effect;
	private Color cp;
	private int cost;
	
	public CpCost(int effect, Color cp, int cost) {
		setEffect(effect);
		setCp(cp);
		setCost(cost);
	}
	
	public static CpCost factory(int effect, Color cp) {
		CpCost cpCost = null;
		try {
			ConnectionTest ct = ConnectionTest.DEFAULT.clone();
			Statement stat = ct.getConnection().createStatement();
			ResultSet rs = stat.executeQuery("SELECT * FROM cpcosts WHERE effect = " + effect + " AND cp = '" + cp + "';");
			if(rs.next()) {
				int cost = rs.getInt("cost");
				
				cpCost = new CpCost(effect, cp, cost);
			}
			rs.close();
			stat.close();
			ct.closeConnection();
			return cpCost;
		} catch (SQLException e) {
			throw new IllegalArgumentException("No cpCost was found from effect " + effect + " being color " + cp);
		}
	}

	public int getEffect() {
		return effect;
	}
	
	public String getEffectName() {
		return Effect.factory(getEffect()).getName();
	}

	public void setEffect(int effect) {
		this.effect = effect;
	}

	public Color getCp() {
		return cp;
	}

	public void setCp(Color cp) {
		this.cp = cp;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Effect: " + getEffectName() +
				"\nCP: " + getCp() +
				"\nCost: " + getCost());
		return sb.toString();
	}

}
