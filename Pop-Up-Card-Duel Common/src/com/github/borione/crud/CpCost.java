package com.github.borione.crud;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.github.borione.connection.ConnectionTest;


public class CpCost {
	
	private int card;
	private Color cp;
	private int cost;
	
	public CpCost(int card, Color cp, int cost) {
		setCard(card);
		setCp(cp);
		setCost(cost);
	}
	
	public static CpCost factory(int card, Color cp) {
		CpCost cpCost = null;
		try {
			ConnectionTest ct = ConnectionTest.LOCAL_DEFAULT.clone();
			Statement stat = ct.getConnection().createStatement();
			ResultSet rs = stat.executeQuery("SELECT * FROM cpcosts WHERE card = " + card + " AND cp = '" + cp + "';");
			if(rs.next()) {
				int cost = rs.getInt("cost");
				
				cpCost = new CpCost(card, cp, cost);
			}
			rs.close();
			stat.close();
			ct.closeConnection();
			return cpCost;
		} catch (SQLException e) {
			throw new IllegalArgumentException("No cpCost was found from card " + card + " being color " + cp);
		}
	}

	public int getCard() {
		return card;
	}

	public void setCard(int card) {
		this.card = card;
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
		sb.append("Card: " + getCard() +
				"\nCP: " + getCp() +
				"\nCost: " + getCost());
		return sb.toString();
	}
	
	@Override
	public boolean equals(Object o) {
		boolean equals = false;
		
		if (o instanceof CpCost) {
			CpCost cpCost = (CpCost) o;
			if(getCard() == cpCost.getCard() &&
					getCp().equals(cpCost.getCp()) &&
					getCost() == cpCost.getCost()) {
				equals = true;
			}
		}
		
		return equals;
	}

}
