package com.github.borione.crud;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.github.borione.connection.ConnectionTest;
import com.github.borione.util.Consts;

public class Possession {
	
	public static final ConnectionTest SERVER_DEFAULT = new ConnectionTest(Consts.DB_ADDRESS, Consts.DB_NAME, Consts.DB_USER, Consts.DB_PASSWORD);
	
	private String player;
	private int card;
	
	public Possession(String player, int card) {
		setPlayer(player);
		setCard(card);
	}
	
	public static Possession factory(String player, int card) throws IllegalArgumentException {
		Possession possession = null;
		try {
			SERVER_DEFAULT.openConnection();
			Statement stat = SERVER_DEFAULT.getConnection().createStatement();
			ResultSet rs = stat.executeQuery("SELECT * FROM collections "
					+ "WHERE player = '" + player + "' AND "
					+ "card = " + card + ";");
			if(rs.next()) {	// player owns the card
				possession = new Possession(player, card);
			}
			
			rs.close();
			stat.close();
			SERVER_DEFAULT.closeConnection();
		} catch(SQLException e) {
			throw new IllegalArgumentException("Player " + player + " doesn't own card + " + card + ".");
		}
		
		return possession;
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

	public int getCard() {
		return card;
	}
	
	public Card retriveCard() {
		return Card.factory(getCard());
	}

	public void setCard(int card) {
		this.card = card;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(retrivePlayer().toString() + "\n\nowns\n\n" + retriveCard().toString());
		
		return sb.toString();
	}
	
	@Override
	public boolean equals(Object o) {
		boolean equals = false;
		
		if (o instanceof Possession) {
			Possession possession = (Possession) o;
			if(getPlayer().equals(possession.getPlayer()) &&
					getCard() == possession.getCard()) {
				equals = true;
			}
		}
		
		return equals;
	}

}
