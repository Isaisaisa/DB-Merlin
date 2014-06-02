package merlin.data.entities;

public class BirdwatcherImpl implements Birdwatcher {

	private final int id;
	private final String name;
	private final String firstname;
	private final String username;
	private final String password;
	private final String email;
	private final String role;
	
	
	private BirdwatcherImpl(int id, String name, String firstname, String username, String password, String email, String role) {
		super();
		this.id = id;
		this.name = name;
		this.firstname = firstname;
		this.username = username;
		this.password = password;
		this.email = email;
		this.role = role;
	}
	
	public static BirdwatcherImpl valueOf(int id, String name, String firstname, String username, String password, String email, String role){
		return new BirdwatcherImpl(id, name, firstname, username, password, email, role);
	}

	public int Id() {
		return id;
	}
	
	public String Name() {
		return name;
	}

	public String Firstname() {
		return firstname;
	}
	
	public String Username() {
		return username;
	}
	
	public String Password() {
		return password;
	}

	public String Email() {
		return email;
	}
	
	public String Role() {
		return role;
	}

}
