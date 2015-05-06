package com.github.borione.crud;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.github.borione.connection.ConnectionTest;
import com.github.borione.connection.Sendable;
import com.github.borione.util.Consts;

public class Deck implements Sendable {
	
	public static ConnectionTest SERVER_DEFAULT = new ConnectionTest(Consts.DB_ADDRESS, Consts.DB_NAME, Consts.DB_USER, Consts.DB_PASSWORD);

	private int id;
	private String player;
	private String name;
	private Timestamp creationDate;

	public Deck(int id, String player, String name, Timestamp creationDate) {
		setId(id);
		setPlayer(player);
		setName(name);
		setCreationDate(creationDate);
	}

	public static Deck factory(int id) {
		Deck deck = null;
		try {
			SERVER_DEFAULT.openConnection();
			Statement stat = SERVER_DEFAULT.getConnection().createStatement();
			ResultSet rs = stat.executeQuery("SELECT * FROM decks WHERE id = " + id + ";");
			if(rs.next()) {
				String player = rs.getString("player");
				String name = rs.getString("name");
				Timestamp creationDate = rs.getTimestamp("creationDate");

				deck = new Deck(id, player, name, creationDate);
			}
			rs.close();
			stat.close();
			SERVER_DEFAULT.closeConnection();
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

	public Timestamp getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}

	public List<Card> retriveCards() {
		List<Card> cards = new ArrayList<Card>();

		try {		
			SERVER_DEFAULT.openConnection();
			Statement stat = SERVER_DEFAULT.getConnection().createStatement();
			ResultSet rs = stat.executeQuery("SELECT DISTINCT card FROM collection_deck WHERE deck = " + id + " AND player = '" + player + "';");
			while(rs.next()) {
				int card = rs.getInt("card");
				cards.add(Card.factory(card));
			}
			rs.close();
			stat.close();
			SERVER_DEFAULT.closeConnection();
			
			return cards;
		} catch (SQLException e) {
			throw new RuntimeException("An error occurred while fetching data from db.");
		}
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
					// TODO: omit check on nanos field
					getCreationDate().equals(deck.getCreationDate())) {
				equals = true;
			}
		}
		
		return equals;
	}

	@Override
	public String formatData() {
		StringBuilder sb = new StringBuilder();
		
		sb.append(getClass().getName() + Consts.SEPARATOR);
		sb.append(getId() + Consts.SEPARATOR);
		sb.append(getPlayer() + Consts.SEPARATOR);
		sb.append(getName() + Consts.SEPARATOR);
		sb.append(getCreationDate().toString() + Consts.SEPARATOR);
		
		return sb.toString();
	}

}
