package de.happycarl.drivelog.server.models;

public class Greeting {
	String name;

	public Greeting() {
	};

	public Greeting(String name) {
		this.name = name;
	}

	public String getName() {
		return "Hello " + name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
