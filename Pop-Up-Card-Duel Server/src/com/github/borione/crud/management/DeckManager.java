package com.github.borione.crud.management;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.github.borione.crud.Deck;
import com.github.borione.crud.Player;
import com.github.borione.crud.Possession;
import com.github.borione.connection.ConnectionTest;

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

		if(deck != null) {
			try {
				Deck.factory(deck.getId());	// Check deck existence
				stat = conn.getConnection().createStatement();

				String command = "UPDATE decks SET "
						+ "name = '" + deck.getName() + "' "
						+ "WHERE id = " + deck.getId() + ";";

				if(stat.executeUpdate(command) == 0) {
					// Error
					return false;
				}
		} catch(SQLException | IllegalArgumentException e) {
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
				} catch(SQLException | NullPointerException e) {
					// Do nothing
				}
			}

			return true;
		}

		return false;
	}

	public boolean addPossessionToDeck(int card, String player, int deck) {
		Statement stat = null;

		try {
			stat = conn.getConnection().createStatement();

			// Checking if records exist
			Possession.factory(player, card);
			Deck.factory(deck);

			String command = "INSERT INTO collection_deck "
					+ "(player, card, deck) "
					+ "VALUE ('" + player + "', " + card + ", " + deck + ");";
			
			if(stat.executeUpdate(command) == 0) {
				// Error
				return false;
			}
		} catch(SQLException | IllegalArgumentException e) {
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

	public boolean addPossessionToDeck(Possession p, Deck d) {
		if(p != null && d != null) {
			return addPossessionToDeck(p.getCard(), p.getPlayer(), d.getId());
		}
		return false;
	}
	
	public boolean addPossessionsToDeck(List<Possession> l, Deck d) {
		if(l != null && d != null) {
			StringBuilder sbCommand = new StringBuilder();
			// Checking if record exist
			Deck.factory(d.getId());
			sbCommand.append("INSERT INTO collection_deck (player, card, deck) VALUES ");
			for (Possession p : l) {
				// Checking if record exist
				Possession.factory(p.getPlayer(), p.getCard());
				sbCommand.append("('" + p.getPlayer() + "', " + p.getCard() + ", " + d.getId() + "), ");
			}
			sbCommand.delete(sbCommand.length() - 2, sbCommand.length());
			sbCommand.append(";");
			
			Statement stat = null;
			
			try {
				stat = conn.getConnection().createStatement();
				
				String command = sbCommand.toString();
				
				if(stat.executeUpdate(command) == 0) {
					// Error
					return false;
				}
			} catch(SQLException | IllegalArgumentException e) {
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
		dm.addPossessionToDeck(Possession.factory("LeaX_XIV", 2), Deck.factory(2));
	}
}
