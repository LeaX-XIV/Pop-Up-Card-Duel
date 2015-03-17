package com.github.borione.crud;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.github.borione.connection.ConnectionTest;
import com.github.borione.connection.Sendable;
import com.github.borione.util.Consts;
import com.github.borione.util.ListUtils;

enum Rarity {
	D,
	C,
	B,
	A,
	S,
	SS,
	SSS,
	SSSS,
	SSSSS
}

public class Card implements Sendable {

	private int id;
	private int esper;
	private String attack;
	private Rarity rarity;
	private CardColor color;

	public Card(int id, int esper, String attack, Rarity rarity, CardColor color) {
		setId(id);
		setEsper(esper);
		setAttack(attack);
		setRarity(rarity);
		setColor(color);
	}

	public Card(String arg) {
		super();
	}

	public static Card factory(int id) {
		Card card = null;
		try {
			ConnectionTest ct = ConnectionTest.DEFAULT.clone();
			Statement stat = ct.getConnection().createStatement();
			ResultSet rs = stat.executeQuery("SELECT * FROM cards WHERE id = " + id + ";");
			if(rs.next()) {
				int esper = rs.getInt("esper");
				String attack = rs.getString("attack");
				Rarity rarity = Rarity.valueOf(rs.getString("rarity"));
				CardColor color = CardColor.valueOf(rs.getString("color"));

				card = new Card(id, esper, attack, rarity, color);
			}
			rs.close();
			stat.close();
			ct.closeConnection();
			return card;
		} catch (SQLException e) {
			throw new IllegalArgumentException("No card was found with id = " + id);
		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getEsper() {
		return esper;
	}

	public String getEsperName() {
		return Esper.factory(getEsper()).getName();
	}

	public void setEsper(int esper) {
		this.esper = esper;
	}

	public String getAttack() {
		return attack;
	}

	public void setAttack(String attack) {
		this.attack = attack;
	}

	public Rarity getRarity() {
		return rarity;
	}

	public void setRarity(Rarity rarity) {
		this.rarity = rarity;
	}

	public CardColor getColor() {
		return color;
	}

	public void setColor(CardColor color) {
		this.color = color;
	}

	public List<CpCost> retriveCpCost() {
		List<CpCost> costs = new ArrayList<CpCost>();

		try {
			ConnectionTest ct = ConnectionTest.DEFAULT.clone();
			Statement stat = ct.getConnection().createStatement();
			ResultSet rs = stat.executeQuery("SELECT * FROM cpcosts WHERE card = '" + getId() + "';");

			while(rs.next()) {
				costs.add(CpCost.factory(rs.getInt("card"), Color.valueOf(rs.getString("cp"))));
			}
		} catch(SQLException e) {
			throw new RuntimeException("An error occurred while fetching data from the db.");
		}

		return costs;
	}

	public List<Action> retriveActions() {
		List<Action> actions = new ArrayList<Action>();

		try {
			ConnectionTest ct = ConnectionTest.DEFAULT.clone();
			Statement stat = ct.getConnection().createStatement();
			ResultSet rs = stat.executeQuery("SELECT * FROM card_action WHERE card = '" + getId() + "';");

			while(rs.next()) {
				actions.add(Action.factory(rs.getInt("action")));
			}
		} catch(SQLException e) {
			throw new RuntimeException("An error occurred while fetching data from the db.");
		}

		return actions;
	}
	
	public Effect retrievePrimaryEffect() {
		Effect effect = null;

		try {
			ConnectionTest ct = ConnectionTest.DEFAULT.clone();
			Statement stat = ct.getConnection().createStatement();
			ResultSet rs = stat.executeQuery("SELECT effect FROM effect_card WHERE card = '" + getId() + "' AND importance = 'PRIMARY';");

			rs.next();
				effect = Effect.factory(rs.getInt("effect"));
		} catch(SQLException e) {
			throw new RuntimeException("An error occurred while fetching data from the db.");
		}

		return effect;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Id: " + getId() +
				"\nEsper: " + getEsperName() +
				"\nAttack: " + getAttack() +
				"\nRarity: " + getRarity() +
				"\nColor: " + getColor() +
				"\nCpCost: " + ListUtils.toString(retriveCpCost(),  "\n------\n"));
		sb.append("\n\n" + ListUtils.toString(retriveActions(), "\n\n"));

		return sb.toString();
	}

	@Override
	public boolean equals(Object o) {
		boolean equals = false;

		if (o instanceof Card) {
			Card card = (Card) o;
			if(getId() == card.getId() &&
					getEsper() == card.getEsper() &&
					getAttack().equals(card.getAttack())&&
					getRarity().equals(card.getRarity()) &&
					getColor().equals(card.getColor())) {
				equals = true;
			}
		}

		return equals;
	}

	@Override
	public String formatData() {
		StringBuilder sb = new StringBuilder();

		sb.append("Card" + Consts.SEPARATOR);
		// Cards won't be changed, so only ID is required to the server
		sb.append(getId());

		return sb.toString();
	}

	public static void main(String[] args) {
		System.out.println(Card.factory(6));
	}
}
