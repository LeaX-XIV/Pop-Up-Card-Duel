package com.github.borione.crud.management;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.github.borione.crud.Card;
import com.github.borione.crud.Deck;
import com.github.borione.crud.Player;
import com.github.borione.network.ConnectionTest;

public class DeckManager {

	private ConnectionTest conn;

	public DeckManager() {
		conn = ConnectionTest.DEFAULT.clone();
	}

	public boolean addDeck(Deck deck) {
		Statement stat = null;
		String creation;

		if(deck != null) {
			try {
				stat = conn.getConnection().createStatement();

				creation = deck.getCreationDate().toString();
				// With this I'm sure the player exists
				Player.factory(deck.getPlayer());

				String command = "INSERT INTO decks "
						+ "(id, player, name, creationDate) "
						+ "VALUES (" + deck.getId() + ", "
						+ "'" + deck.getPlayer() + "', "
						+ "'" + deck.getName() + "', "
						+ "'" + creation + "');";

				if(stat.executeUpdate(command) == 0) {
					// Error
					return false;
				}
			} catch(SQLException | IllegalArgumentException e) {
				return false;
			} finally {
				try {
					stat.close();
				} catch (SQLException | NullPointerException e) {
					// Do nothing
				}
			}

			return true;
		}

		return false;
	}

	public boolean updateDeck(Deck deck) { // ONLY THE NAME CAN BE CHANGED
		Statement stat = null;
		ResultSet rs = null;

		if(deck != null) {
			try { // Check if deck exists
				stat = conn.getConnection().createStatement();
				String query = "SELECT COUNT(*) AS number FROM decks "
						+ "WHERE id = " + deck.getId() + ";";
				rs = stat.executeQuery(query);
				rs.next();
				if(rs.getInt(1) == 0) {
					// The deck doesn't exist
					return false;
				}
			} catch(SQLException e) {
				// Error
				return false;
			} finally {
				try {
					rs.close();
					stat.close();
				} catch(SQLException | NullPointerException e) {
					// Do nothing			
				}
			}

			try {
				stat = conn.getConnection().createStatement();

				String command = "UPDATE decks SET "
						+ "name = '" + deck.getName() + "' "
						+ "WHERE id = " + deck.getId() + ";";

				if(stat.executeUpdate(command) == 0) {
					// Error
					return false;
				}
			} catch(SQLException e) {
				// Error
				return false;
			} finally {
				try {
					stat.close();
				} catch(SQLException | NullPointerException e) {
					// Do nothing
				}
			}

			return true;
		}

		return false;
	}

	public List<Deck> listDecks() {
		List<Deck> list = null;

		Statement stat = null;
		ResultSet rs = null;

		int id;

		try {
			stat = conn.getConnection().createStatement();
			String query = "SELECT id FROM decks;";
			rs = stat.executeQuery(query);

			list = new ArrayList<Deck>();

			while(rs.next()) {
				id = rs.getInt("id");
				list.add(Deck.factory(id));
			}
		} catch(SQLException e) {
			// Error
			return null;
		} finally {
			try {
				rs.close();
				stat.close();
			} catch(SQLException | NullPointerException e) {
				// Do nothing
			}
		}
		
		return list;
	}
	
	public boolean deleteDeck(Deck deck) {
		Statement stat = null;
		
		if(deck != null) {
			try {
				stat = conn.getConnection().createStatement();
				String command = "DELETE FROM decks "
						+ "WHERE id = " + deck.getId() + ";";
				
				if(stat.executeUpdate(command) == 0) {
					// Error
					return false;
				}
			} catch(SQLException e) {
				// Error
				return false;
			} finally {
				try {
					stat.close();
				} catch(SQLException e) {
					// Do nothing
				}
			}
			
			return true;
		}
		
		return false;
	}
	
	public static void main(String[] args) {
		Deck test = new Deck(5, "LeaX_XIV", "I love jinrui", new Timestamp(new Date().getTime()));
		DeckManager dm = new DeckManager();
		if(dm.addDeck(test)) {
			test.setName("I love ningen");
			if(dm.updateDeck(test)) {
//				if(dm.listDecks().contains(test)) {
					if(dm.deleteDeck(test)) {
						System.out.println("It works! :)");
					}
//				}
			}
		}
	}
}