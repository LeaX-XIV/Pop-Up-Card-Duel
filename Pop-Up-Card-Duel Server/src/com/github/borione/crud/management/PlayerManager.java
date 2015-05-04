package com.github.borione.crud.management;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.github.borione.crud.Player;
import com.github.borione.connection.ConnectionTest;
import com.github.borione.util.StringUtils;

public class PlayerManager {

	private ConnectionTest conn;

	public PlayerManager() {
		conn = ConnectionTest.DEFAULT.clone();
	}

	public boolean addPlayer(Player player) {
		Statement stat = null;
		String registration;
		String lastLogin;

		if(player != null) {
			try {
				stat = conn.getConnection().createStatement();

				registration = (player.getRegistration() == null ? "NULL" : "'" + player.getRegistration().toString() + "'");
				lastLogin = (player.getLastLogin() == null ? "NULL" : "'" + player.getLastLogin().toString() + "'");

				String command = "INSERT INTO players "
						+ "(user, password, name, mail, registration, lastlogin, avatar) "
						+ "VALUE ('" + player.getUser() + "', "
						+ "'" + player.getPassword() + "', "
						+ "'" + player.getName() + "', "
						+ "'" + player.getMail() + "', "
						+ registration + ", "
						+ lastLogin + ", "
						+ "'" + player.getAvatar() + "');";

				if(stat.executeUpdate(command) == 0) {
					// Error
					return false;
				}
			} catch(SQLException e) {
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

	public boolean updatePlayer(Player player) {
		Statement stat = null;
		ResultSet rs = null;
		String lastLogin;

		if(player != null) {
			try { // Check if the player exists
				stat = conn.getConnection().createStatement();
				String query = "SELECT COUNT(*) AS number FROM players "
						+ "WHERE user = '" + player.getUser() + "';";
				rs = stat.executeQuery(query);
				rs.next();
				if(rs.getInt(1) == 0) {
					// The player doesn't exist
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
				lastLogin = (player.getLastLogin() == null ? "NULL" : "'" + player.getLastLogin().toString() + "'");

				String command = "UPDATE players SET "
						+ "password = '" + player.getPassword() + "', "
						+ "mail = '" + player.getMail() + "', "
						+ "lastlogin = " + lastLogin + ", "
						+ "avatar = '" + player.getAvatar() + "' "
						+ "WHERE user = '" + player.getUser() + "';";

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

	public List<Player> listPlayers() {
		List<Player> list = null;

		Statement stat = null;
		ResultSet rs = null;

		String user;

		try {
			stat = conn.getConnection().createStatement();
			String query = "SELECT user FROM players;";
			rs = stat.executeQuery(query);

			list = new ArrayList<Player>();

			while(rs.next()) {
				user = rs.getString("user");
				list.add(Player.factory(user));
			}

		} catch(SQLException | IllegalArgumentException e) {
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

	public boolean deletePlayer(Player player) {
		Statement stat = null;

		if(player != null) {
			try {
				stat = conn.getConnection().createStatement();
				String command = "DELETE FROM players "
						+ "WHERE user = '" + player.getUser() + "';";

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
		Player test = new Player("LeaX", StringUtils.toMD5("12345"), "LeaX_XIV", "test@test.com", new Timestamp(2222000L), null, 1);
		PlayerManager pm = new PlayerManager();
		if(pm.addPlayer(test)) {
			test.setLastLogin(new Timestamp(1111111000L));
			if(pm.updatePlayer(test)) {
				if(pm.listPlayers().contains(test)) {
					if(pm.deletePlayer(test)) {
						System.out.println("It works! :)");
					}
				}
			}
		}
	}
}
