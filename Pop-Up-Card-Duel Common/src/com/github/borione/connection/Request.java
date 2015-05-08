package com.github.borione.connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;

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

	public String send() throws IOException, SocketTimeoutException {
		BufferedReader input;
		OutputStreamWriter output;

		String command;
		String answer;
		Socket client = new Socket("127.0.0.1", Consts.SERVER_PORT);

		// Connection request
		input = new BufferedReader(new InputStreamReader(client.getInputStream()));
		output = new OutputStreamWriter(client.getOutputStream());

		command = getType().toString() + Consts.SEPARATOR + getObject().formatData();

		// Write
		output.write(command + "\n");
		output.flush();
		client.setSoTimeout(1000);

		// Read
		answer = input.readLine();
		
		if(answer.startsWith(Consts.ALL_OK)) {	// Good
			// XXX: Cose brutte
			return answer.replace("Â", "");
		} else {	// Something went wrong
			throw new IOException(answer);
		}

	}
}
