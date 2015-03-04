package com.github.borione.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.sql.Timestamp;
import java.util.Date;

import com.github.borione.connection.Sendable;
import com.github.borione.connection.TypeRequest;
import com.github.borione.crud.Player;
import com.github.borione.crud.management.PlayerManager;
import com.github.borione.util.Consts;

public class ServerThread extends Thread {

	private Socket connection;
	private InputStream input;
	private OutputStreamWriter output;
	private TcpServer parent;

	public ServerThread(Socket connection, TcpServer parent) throws IOException {
		this.connection = connection;
		input = this.connection.getInputStream();
		output = new OutputStreamWriter(this.connection.getOutputStream());
		this.parent = parent;
	}

	@Override
	public void run() {
		int n;
		int i = 0;
		String result;
		String character;
		byte[] buffer = new byte[1024];
		StringBuffer command = new StringBuffer();

		try {
			while((n = input.read(buffer)) != -1) {
				if(n > 0) {
					// Searching termination char
					for(i = 0; i < n; i++) {
						if(buffer[i] == '\r' || buffer[i] == '\n') {
							// Command set. Execute
							System.out.println(connection.getInetAddress() + ":" + connection.getPort() + " requested: " + command.toString());
							result = serve(command.toString());
							System.out.println("Sending response to " + connection.getInetAddress() + ":" + connection.getPort());
							output.write(result + "\r\n");
							output.flush();
							// Clear command
							command = new StringBuffer();
						} else {
							character = new String(buffer, i, 1, "ISO-8859-1");
							command.append(character);
						}
					}
				}
			}
		} catch(IOException e) {
			// Do nothing
		}

	}

	private String serve(String command) {
		if(command.startsWith(TypeRequest.REGISTER.toString())) {
			Player p = (Player) Sendable.reconstruct(command.substring(command.indexOf(Consts.SEPARATOR) + 1));
			PlayerManager pm = new PlayerManager();

			boolean result = pm.addPlayer(p);

			if(result) {
				return Consts.ALL_OK + Consts.SEPARATOR + Player.factory(p.getUser()).formatData();
			} else {
				return Consts.ERROR + Consts.SEPARATOR + command;
			}
		} else if(command.startsWith(TypeRequest.LOGIN.toString())) {
			Player p = (Player) Sendable.reconstruct(command.substring(command.indexOf(Consts.SEPARATOR) + 1));
			p.setLastLogin(new Timestamp(new Date().getTime()));
			PlayerManager pm = new PlayerManager();
			
			boolean result = pm.updatePlayer(p);

			if(result) {
				parent.login(p.getUser());
				System.out.println(new Date().toString() + ": " + p.getName() + " logged in.");
				System.out.println("Online players: " + parent.getLogged().toString());
				return Consts.ALL_OK + Consts.SEPARATOR + Player.factory(p.getUser()).formatData();
			} else {
				return Consts.ERROR + Consts.SEPARATOR + command;
			}
		}

		return command;
	}
}