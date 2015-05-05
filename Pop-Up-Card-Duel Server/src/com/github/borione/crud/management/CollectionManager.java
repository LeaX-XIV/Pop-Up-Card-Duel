package com.github.borione.crud.management;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.github.borione.crud.Card;
import com.github.borione.crud.Player;
import com.github.borione.connection.ConnectionTest;
import com.github.borione.util.Consts;

public class CollectionManager {

	public static final ConnectionTest DEFAULT = new ConnectionTest(Consts.DB_ADDRESS, Consts.DB_NAME, Consts.DB_USER, Consts.DB_PASSWORD);

	public CollectionManager() {
	}

	public boolean addCardToCollection(Player player, Card card) {
		if(player != null && card != null) {
			return addCardToCollection(player.getUser(), card.getId());
		}
		
		return false;
	}

	public boolean addCardToCollection(String player, int card) {
		Statement stat = null;

		try {
			// With this, I'm sure the values are right
			Player p = Player.factory(player);
			Card c = Card.factory(card);

			stat = DEFAULT.getConnection().createStatement();
			String command = "INSERT INTO collections (player, card)"
					+ "VALUE ('" + p.getUser() + "', " + c.getId() + ");";

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
			} catch (SQLException | NullPointerException e) {
				// Do nothing
			}
		}

		return true;
	}

	public List<Card> listCollection(Player player) {
		if(player != null) {
			return player.retriveCollection();
		}

		return null;
	}

	public List<Card> listCollection(String player) {
		return listCollection(Player.factory(player));
	}

	public boolean deleteCardFromCollection(Player player, Card card) {
		if(player != null && card != null) {
			return deleteCardFromCollection(player.getUser(), card.getId());
		}

		return false;
	}

	public boolean deleteCardFromCollection(String player, int card) {
		Statement stat = null;

		try {
			// With this, I'm sure the values are right
			Player p = Player.factory(player);
			Card c = Card.factory(card);

			stat = DEFAULT.getConnection().createStatement();
			String command = "DELETE FROM collections "
					+ "WHERE player = '" + p.getUser() + "' AND "
					+ "card = " + c.getId() + ";";

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
			} catch (SQLException | NullPointerException e) {
				// Do nothing
			}
		}

		return true;
	}

	public static void main(String[] args) {
		Player p = Player.factory("LeaX_XIV");
		Card c = Card.factory(1);
		CollectionManager cm = new CollectionManager();

		if(cm.addCardToCollection(p, c)) {
			if(cm.deleteCardFromCollection(p, c)) {
				System.out.println("It works! :)");
			}
		}
	}
}
