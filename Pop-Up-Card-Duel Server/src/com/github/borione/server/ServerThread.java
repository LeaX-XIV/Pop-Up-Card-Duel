package com.github.borione.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class ServerThread extends Thread {

	private Socket connection;
	private InputStream input;
	private OutputStreamWriter output;

	public ServerThread(Socket connection) throws IOException {
		this.connection = connection;
		input = this.connection.getInputStream();
		output = new OutputStreamWriter(this.connection.getOutputStream());
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
							result = serve(command.toString());
							output.write(result + "\r\n");
							output.flush();
							// Clear command
							command = new StringBuffer();
						}
					}
				} else {
					character = new String(buffer, i, 1, "ISO-8859-1");
					command.append(character);
				}
			}
		} catch(IOException e) {
			// Do nothing
		}

	}

	private String serve(String string) {
		// TODO Auto-generated method stub
		return string;
	}
}
