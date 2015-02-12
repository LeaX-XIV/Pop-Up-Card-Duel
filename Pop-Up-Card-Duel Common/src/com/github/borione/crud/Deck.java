package com.github.borione.crud;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.github.borione.network.ConnectionTest;

public class Deck {

	private int id;
	private String player;
	private String name;
	private Date creationDate;

	public Deck(int id, String player, String name, Date creationDate) {
		setId(id);
		setPlayer(player);
		setName(name);
		setCreationDate(creationDate);
	}

	public static Deck factory(int id) {
		Deck deck = null;
		try {
			ConnectionTest ct = ConnectionTest.DEFAULT.clone();
			Statement stat = ct.getConnection().createStatement();
			ResultSet rs = stat.executeQuery("SELECT * FROM decks WHERE id = " + id + ";");
			if(rs.next()) {
				String player = rs.getString("player");
				String name = rs.getString("name");
				Date creationDate = rs.getDate("creationDate");

				deck = new Deck(id, player, name, creationDate);
			}
			rs.close();
			stat.close();
			ct.closeConnection();
			return deck;
		} catch (SQLException e) {
			throw new IllegalArgumentException("No deck was found with id = " + id);
		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getPlayer() {
		return player;
	}
	
	public Player retrivePlayer() {
		return Player.factory(getPlayer());
	}
	
	public void setPlayer(String player) {
		this.player = player;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public List<Card> retriveCards() {
		List<Card> cards = new ArrayList<Card>();

		try {		
			ConnectionTest ct = ConnectionTest.DEFAULT.clone();
			Statement stat = ct.getConnection().createStatement();
			ResultSet rs = stat.executeQuery("SELECT DISTINCT card FROM collection_deck WHERE deck = " + id + " AND player = '" + player + "';");
			while(rs.next()) {
				int card = rs.getInt("card");
				cards.add(Card.factory(card));
			}
			rs.close();
			stat.close();
			ct.closeConnection();
		} catch (SQLException e) {
			throw new RuntimeException("An error occurred while fetching data from db.");
		}
		
		return cards;
	}
	
	public int retriveNumberCards() {
		return retriveCards().size();
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		Player owner = retrivePlayer();
		List<Card> cards = retriveCards();
		sb.append("Id: " + getId() +
				"\nName: " + getName() +
				"\nCreation Date: " + getCreationDate());
		sb.append("\nOwner: " + owner.getName());
		for (Card card : cards) {
			sb.append("\n\nEsper: " + card.getEsperName() +
					"\nAttack: " + card.getAttack());
		}
		return sb.toString();		
	}
	
	@Override
	public boolean equals(Object o) {
		boolean equals = false;
		
		if (o instanceof Deck) {
			Deck deck = (Deck) o;
			if(getId() == deck.getId() &&
					getPlayer().equals(deck.getPlayer()) &&
					getName().equals(deck.getName()) &&
					getCreationDate().equals(deck.getCreationDate())) {
				equals = true;
			}
		}
		
		return equals;
	}

}
