package com.github.borione.connection;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

import com.github.borione.util.Consts;

public class Request {

	private TypeRequest type;
	private Sendable object;

	public Request(TypeRequest type, Sendable object) {
		setType(type);
		setObject(object);
	}

	public TypeRequest getType() {
		return type;
	}

	public void setType(TypeRequest type) {
		this.type = type;
	}

	public Sendable getObject() {
		return object;
	}

	public void setObject(Sendable object) {
		this.object = object;
	}

	public String send() throws IOException {
		InputStream input;
		OutputStreamWriter output;

		String command;
		String character;
		int i;
		int n;
		byte[] buffer = new byte[1024];
		StringBuffer answer = new StringBuffer();
		Socket client = new Socket();
		InetSocketAddress server = new InetSocketAddress(Consts.SERVER, Consts.SERVER_PORT);

		// Connection request
		client.connect(server, 1000);
		input = client.getInputStream();
		output = new OutputStreamWriter(client.getOutputStream());

		command = getType().toString() + Consts.SEPARATOR + getObject().formatData();

		// Write
		output.write(command + "\r\n");
		output.flush();
		client.setSoTimeout(1000);

		// Read
		while((n = input.read(buffer)) != -1) {
			if (n > 0) {
				for(i = 0; i < n; i++) {
					if(buffer[i] == '\r' || buffer[i] == '\n') {
						// Received: closing connection
						input.close();
						output.close();
						client.shutdownInput();
						client.shutdownOutput();
						client.close();
						// Check error in answer
						if(answer.toString().startsWith(Consts.ALL_OK)) {	// Good
							return answer.toString();
						} else {	// Something went wrong
							throw new IOException(answer.toString());
						}
					} else {	// Reading answer char by char
						character = new String(buffer, i, 1, "ISO-8859-1");
						answer.append(character);
					}
				}
			}
		}
		// Error
		input.close();
		output.close();
		client.shutdownInput();
		client.shutdownOutput();
		client.close();

		throw new IOException();
	}
}
