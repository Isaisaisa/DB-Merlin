package merlin.data.entities;

public class BirdwatcherImpl implements Birdwatcher {

	private final String name;
	private final String firstname;
	private final String email;
	
	public BirdwatcherImpl(String name, String firstname, String email) {
		super();
		this.name = name;
		this.firstname = firstname;
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public String getFirstname() {
		return firstname;
	}

	public String getEmail() {
		return email;
	}
	
}
