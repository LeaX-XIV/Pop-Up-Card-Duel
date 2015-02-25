package com.github.borione.server;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

import com.github.borione.util.Consts;
import com.sun.jmx.snmp.Timestamp;

public class TcpServer extends Thread {

	private ServerSocket server;

	public TcpServer (int port) throws IOException {
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

				// Create output stream with ISO-8859-1 coding
				OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "ISO-8859-1");

				/* TODO: DO THINGS
				 * 
				 * 
				 * 
				 * 
				 */

				// Send
				out.write("Stupid ZER0s");
				out.flush();
				// Close stream
				out.close();

			} catch(IOException e) {
				// Do nothing
			} finally {
				if(connection != null) {
					try {
						connection.shutdownOutput();
						connection.close();
					} catch(IOException e) {
						// Do nothing
					}
				}
			}
		}

		// Close listening socket
		try {
			server.close();
		} catch(IOException e) {
			// Do nothing
		}
	}
	
	public static void main(String[] args) {
		try {
			TcpServer server = new TcpServer();
			server.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
