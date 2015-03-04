package com.github.borione.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.github.borione.crud.Player;
import com.github.borione.util.Consts;

public class TcpServer extends Thread {

	private ServerSocket server;
	private List<String> logged;

	public TcpServer (int port) throws IOException {
		logged = new ArrayList<String>();
		server = new ServerSocket(port);
		server.setSoTimeout(1000);	// 1s
	}

	public TcpServer() throws IOException {
		this(Consts.SERVER_PORT);
	}

	@Override
	public void run() {
		Socket connection = null;

		System.out.println("Server listening on port " + server.getLocalPort());

		while(!Thread.interrupted()) {
			try {
				// Wait for connection request (max wait 1 second)
				connection = server.accept();
				System.out.println(new Date().toString() + ": Request from " + connection.getInetAddress().toString() + ":" + connection.getPort());

				Thread thread = new ServerThread(connection, this);
				thread.start();

			} catch(SocketTimeoutException e) {
				// Do nothing
			} catch(IOException e) {
				// Do nothing
			}
		}

		// Close listening socket
		try {
			server.close();
		} catch(IOException e) {
			// Do nothing
		}
	}
	
	public boolean login(String str) {
		return logged.add(str);
	}
	
	public boolean login(Player p) {
		return logged.add(p.getUser());
	}
	
	public void logout(String str) {
		logged.remove(str);
	}
	
	public void logout(Player p) {
		logged.remove(p.getUser());
	}
	
	public List<String> getLogged() {
		return logged;
	}

	public static void main(String[] args) {
		try {
			TcpServer server = new TcpServer();
			server.start();
			int c = System.in.read();
			server.interrupt();
			server.join();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
