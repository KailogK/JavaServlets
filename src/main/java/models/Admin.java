package models;

public class Admin {
	private String password, email;

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

	public Admin(String password, String email) {
		super();
		this.password = password;
		this.email = email;
	}

}
