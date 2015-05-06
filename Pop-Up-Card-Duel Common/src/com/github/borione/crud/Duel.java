package com.github.borione.crud;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import com.github.borione.connection.ConnectionTest;
import com.github.borione.util.Consts;

public class Duel {
	
	public static ConnectionTest SERVER_DEFAULT = new ConnectionTest(Consts.DB_ADDRESS, Consts.DB_NAME, Consts.DB_USER, Consts.DB_PASSWORD);
	
	private int id;
	private String player1;
	private String player2;
	private Timestamp date;
	private Result result;
	
	public Duel(int id, String player1, String player2, Timestamp date, Result result) {
		setId(id);
		setPlayer1(player1);
		setPlayer2(player2);
		setDate(date);
		setResult(result);
	}
	
	public static Duel factory(int id) {
		Duel duel = null;
		try {
			SERVER_DEFAULT.openConnection();
			Statement stat = SERVER_DEFAULT.getConnection().createStatement();
			ResultSet rs = stat.executeQuery("SELECT * FROM duels WHERE id = " + id + ";");
			if(rs.next()) {
				String player1 = rs.getString("player1");
				String player2 = rs.getString("player2");
				Timestamp date = rs.getTimestamp("date");
				Result result = Result.valueOf(rs.getString("result"));
				
				duel = new Duel(id, player1, player2, date, result);
			}
			rs.close();
			stat.close();
			SERVER_DEFAULT.closeConnection();
			return duel;
		} catch (SQLException e) {
			throw new IllegalArgumentException("No duel was found with id = " + id + ".");
		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPlayer1() {
		return player1;
	}
	
	public Player retrivePlayer1() {
		return Player.factory(player1);
	}

	public void setPlayer1(String player1) {
		this.player1 = player1;
	}

	public String getPlayer2() {
		return player2;
	}
	
	public Player retrivePlayer2() {
		return Player.factory(player2);
	}

	public void setPlayer2(String player2) {
		this.player2 = player2;
	}

	public Timestamp getDate() {
		return date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

	public Result getResult() {
		return result;
	}
	
	public Player retriveWinner() {
		Player winner = null;
		if(getResult() != Result.TIE && getResult() != Result.ERROR) {
			if(getResult() == Result.WIN1) {
				winner = Player.factory(getPlayer1());
			}
			else {
				winner = Player.factory(getPlayer2());
			}
		}
		
		return winner;
	}
	
	public Player retriveLoser() {
		Player loser = null;
		if(getResult() != Result.TIE && getResult() != Result.ERROR) {
			if(getResult() == Result.WIN1) {
				loser = Player.factory(getPlayer2());
			}
			else {
				loser = Player.factory(getPlayer1());
			}
		}
		
		return loser;
	}

	public void setResult(Result result) {
		this.result = result;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Id: " + getId() +
				"\nPlayer1: " + retrivePlayer1().getName() +
				"\nPlayer2: " + retrivePlayer2().getName() +
				"\nDate: " + getDate() +
				"\nResult: " + getResult());
		return sb.toString();
	}
	
	@Override
	public boolean equals(Object o) {
		boolean equals = false;
		
		if (o instanceof Duel) {
			Duel duel = (Duel) o;
			
			if(getId() == duel.getId() &&
					getPlayer1().equals(duel.getPlayer1()) &&
					getPlayer2().equals(duel.getPlayer2()) &&
					getDate().equals(duel.getDate()) &&
					getResult().equals(duel.getResult())) {
				equals = true;
			}		
		}
		
		return equals;
	}

}
