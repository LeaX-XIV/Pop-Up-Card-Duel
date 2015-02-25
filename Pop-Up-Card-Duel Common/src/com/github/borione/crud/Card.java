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

enum CardColor {
	RED,
	GREEN,
	BLUE,
	YELLOW,
	GRAY
}

public class Card implements Sendable {
	
	private int id;
	private int esper;
	private String attack;
	private int power;
	private Rarity rarity;
	private CardColor color;
	private int effect;
	
	public Card(int id, int esper, String attack, int power, Rarity rarity, CardColor color, int effect) {
		setId(id);
		setEsper(esper);
		setAttack(attack);
		setPower(power);
		setRarity(rarity);
		setColor(color);
		setEffect(effect);
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
				int power = rs.getInt("power");
				Rarity rarity = Rarity.valueOf(rs.getString("rarity"));
				CardColor color = CardColor.valueOf(rs.getString("color"));
				int effect = rs.getInt("effect");
				
				card = new Card(id, esper, attack, power, rarity, color, effect);
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

	public int getPower() {
		return power;
	}

	public void setPower(int power) {
		this.power = power;
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

	public int getEffect() {
		return effect;
	}
	
	public Effect retriveEffect() {
		return Effect.factory(getEffect());
	}

	public void setEffect(int effect) {
		this.effect = effect;
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
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		Effect effect = retriveEffect();
		sb.append("Id: " + getId() +
				"\nEsper: " + getEsperName() +
				"\nAttack: " + getAttack() +
				"\nPower: " + getPower() +
				"\nRarity: " + getRarity() +
				"\nColor: " + getColor());
		sb.append("\nEffect: " + effect.getName() +
				"\nCost: " + effect.getCpCost());
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
					getAttack().equals(card.getAttack()) &&
					getPower() == card.getPower() &&
					getRarity().equals(card.getRarity()) &&
					getColor().equals(card.getColor()) &&
					getEffect() == card.getEffect()) {
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
		System.out.println(Card.factory(1));
	}
}
