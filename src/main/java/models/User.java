package models;

import java.util.List;

public class User {
	private String password, email, username;
	private List<Book> orders;
	
	public User(String password, String email, String username, List<Book> orders) {
		super();
		this.password = password;
		this.email = email;
		this.username = username;
		this.orders = orders;
	}
	
	public User(String password, String email, String username) {
		super();
		this.password = password;
		this.email = email;
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public List<Book> getOrders() {
		return orders;
	}
	public void setOrders(List<Book> orders) {
		this.orders = orders;
	}
}
