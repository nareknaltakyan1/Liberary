package users;

public class User {
	private String login;
	private int id;
	
	public User() {
		
	}
	
	public User(int id, String login) {
		this.id=id;
		this.login=login;
	}

	public String getLogin() {
		return login;
	}

	public int getId() {
		return id;
	}
	
}
