package com.github.borione.connection;

enum TypeRequest {
	INSERT,
	UPDATE,
	DELETE
}

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
	
	
}
