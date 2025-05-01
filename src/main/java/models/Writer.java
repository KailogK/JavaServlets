package models;

public class Writer {
	private String password, email, name, writerId;

	public String getPassword() {
		return password;
	}

	public Writer(String password, String email, String name, String writerId) {
		super();
		this.password = password;
		this.email = email;
		this.name = name;
		this.writerId = writerId;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getWriterId() {
		return writerId;
	}

	public void setWriterId(String writerId) {
		this.writerId = writerId;
	}
}
