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
import com.github.borione.util.ListUtils;

public class Player implements Sendable {

	private String user;
	private String password;
	private String name;
	private String mail;
	private Timestamp registration;
	private Timestamp lastLogin;
	private int avatar;

	/**
	 * Creates a new instance of the <code>players</code> table.
	 * @param user The username, used to login.
	 * @param password The MD5 of the password.
	 * @param name The name displayed to other players.
	 * @param mail The mail used to register.
	 * @param registration The timestamp of the registration.
	 * @param lastLogin The timestamp of the last login. <code>null</code> if never logged.
	 * @param avatar The id of the avatar used in the game. Default is <code>1</code>.
	 */
	public Player(String user, String password, String name, String mail, Timestamp registration, Timestamp lastLogin, int avatar) {
		setUser(user);
		setPassword(password);
		setName(name);
		setMail(mail);
		setRegistration(registration);
		setLastLogin(lastLogin);
		setAvatar(avatar);
	}

	/**
	 * Factors a <code>Player</code> given the user.<br>
	 * The player must exist on the database, or it would throw an exception.
	 * @param user The user of an existing player.
	 * @return A <code>Player</code> object with the characteristics of the pointed record of the db.
	 * @throws IllegalArgumentException If the player with the given user doesn't exist.
	 */
	public static Player factory(String user) throws IllegalArgumentException {
		Player player = null;
		try {
			ConnectionTest ct = ConnectionTest.DEFAULT.clone();
			Statement stat = ct.getConnection().createStatement();
			ResultSet rs = stat.executeQuery("SELECT * FROM players WHERE user = '" + user + "';");
			if(rs.next()) {
				String password = rs.getString("password");
				String name = rs.getString("name");
				String mail = rs.getString("mail");
				Timestamp registration = rs.getTimestamp("registration");
				Timestamp lastLogin = rs.getTimestamp("lastlogin");
				int avatar = rs.getInt("avatar");

				player = new Player(user, password, name, mail, registration, lastLogin, avatar);
			}
			rs.close();
			stat.close();
			ct.closeConnection();
			return player;
		} catch (SQLException e) {
			throw new IllegalArgumentException("No player was found with user = " + user);
		}
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public Timestamp getRegistration() {
		return registration;
	}

	public void setRegistration(Timestamp registration) {
		this.registration = registration;
	}

	public Timestamp getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Timestamp lastLogin) {
		this.lastLogin = lastLogin;
	}

	public int getAvatar() {
		return avatar;
	}

	public String getAvatarName() throws RuntimeException {
		return Avatar.factory(getAvatar()).getName();
	}

	public void setAvatar(int avatar) {
		this.avatar = avatar;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Username: " + getUser() +
				"\nPassword: " + getPassword() +
				"\nName: " + getName() +
				"\nE-Mail: " + getMail() +
				"\nRegistered " + getRegistration() +
				"\nLast seen: " + getLastLogin() +
				"\nAvatar: " + getAvatarName());
		return sb.toString();
	}

	@Override
	public boolean equals(Object o) {
		boolean equals = false;

		if (o instanceof Player) {
			Player player = (Player) o;
			try {
				if (getUser().equals(player.getUser()) &&
						getPassword().equals(player.getPassword()) &&
						getName().equals(player.getName()) &&
						getMail().equals(player.getMail()) &&
						getRegistration().equals(player.getRegistration()) &&
						getLastLogin().equals(player.getLastLogin()) &&
						getAvatar() == player.getAvatar()) {
					equals = true;
				}

			} catch(NullPointerException e) {
				if (getUser().equals(player.getUser()) &&
						getPassword().equals(player.getPassword()) &&
						getName().equals(player.getName()) &&
						getMail().equals(player.getMail()) &&
						getRegistration().equals(player.getRegistration()) &&
						getAvatar() == player.getAvatar() &&
						getLastLogin() == null &&
						player.getLastLogin() == null) {
					equals = true;
				}
			}
		}

		return equals;
	}

	public List<Card> retriveCollection() {
		List<Card> collection = new ArrayList<Card>();

		try {
			ConnectionTest ct = ConnectionTest.DEFAULT.clone();
			Statement stat = ct.getConnection().createStatement();
			ResultSet rs = stat.executeQuery("SELECT * FROM collections WHERE player = '" + getUser() + "';");

			while(rs.next()) {
				collection.add(Card.factory(rs.getInt("card")));
			}
		} catch(SQLException e) {
			throw new RuntimeException("An error occurred while fetching data from the db.");
		}

		return collection;
	}

	public List<Deck> retriveDecks() {
		List<Deck> decks = new ArrayList<Deck>();

		try {		
			ConnectionTest ct = ConnectionTest.DEFAULT.clone();
			Statement stat = ct.getConnection().createStatement();
			ResultSet rs = stat.executeQuery("SELECT id FROM decks WHERE player = '" + getUser() + "';");
			while(rs.next()) {
				int deck = rs.getInt("id");
				decks.add(Deck.factory(deck));
			}
			rs.close();
			stat.close();
			ct.closeConnection();
		} catch (SQLException e) {
			throw new RuntimeException("An error occurred while fetching data from db.");
		}

		return decks;
	}

	public List<Duel> retriveDuels() {
		List<Duel> duels = new ArrayList<Duel>();

		try {		
			ConnectionTest ct = ConnectionTest.DEFAULT.clone();
			Statement stat = ct.getConnection().createStatement();
			ResultSet rs = stat.executeQuery("SELECT DISTINCT id FROM duels WHERE player1 = '" + getUser() + "' OR player2 = '" + getUser() + "';");
			while(rs.next()) {
				int id = rs.getInt("id");
				duels.add(Duel.factory(id));
			}
			rs.close();
			stat.close();
			ct.closeConnection();
		} catch (SQLException e) {
			throw new RuntimeException("An error occurred while fetching data from db.");
		}

		return duels;
	}

	public List<Duel> retriveWins() {
		List<Duel> duels = retriveDuels();
		List<Duel> wins = new ArrayList<Duel>();

		for (Duel duel : duels) {
			if(duel.retriveWinner().getUser().equals(getUser())) {
				wins.add(duel);
			}
		}

		return wins;
	}

	public int countWins() {
		return retriveWins().size();
	}

	public List<Duel> retriveLosses() {
		List<Duel> duels = retriveDuels();
		List<Duel> losses = new ArrayList<Duel>();

		for (Duel duel : duels) {
			if(duel.retriveLoser().getUser().equals(getUser())) {
				losses.add(duel);
			}
		}

		return losses;
	}

	public int countLosses() {
		return retriveLosses().size();
	}

	public List<Duel> retriveTies() {
		List<Duel> duels = retriveDuels();
		List<Duel> ties = new ArrayList<Duel>();

		for (Duel duel : duels) {
			if(duel.getResult().equals(Result.TIE)) {
				ties.add(duel);
			}
		}

		return ties;
	}

	public int countTies() {
		return retriveTies().size();
	}

	@Override
	public String formatData() {
		StringBuilder sb = new StringBuilder();

		sb.append(getClass().getName() + Consts.SEPARATOR);
		sb.append(getUser() + Consts.SEPARATOR);
		sb.append(getPassword() + Consts.SEPARATOR);
		sb.append(getName() + Consts.SEPARATOR);
		sb.append(getMail() + Consts.SEPARATOR);
		sb.append(getRegistration().toString() + Consts.SEPARATOR);
		try {
			sb.append(getLastLogin().toString() + Consts.SEPARATOR);
		} catch(NullPointerException e) {
			sb.append("null" + Consts.SEPARATOR);
		}
		sb.append(getAvatar());

		return sb.toString();
	}

	public static void main(String[] args) {
		Player master = Player.factory("LeaX_XIV");
		System.out.println(master + "\n\n" + ListUtils.toString(master.retriveCollection(), "\n----------------------\n"));
		System.out.println(ListUtils.toString(master.retriveDecks(), "\n\n--------------------\n\n"));
		System.out.println("\n\n\n\n\n\n\n\n" + master.formatData());
	}
}
