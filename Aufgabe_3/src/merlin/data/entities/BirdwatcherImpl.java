package merlin.data.entities;

public class BirdwatcherImpl implements Birdwatcher {

	public final static String BWROLE = "R03";
	
	private final String name;
	private final String firstname;
	private final String username;
	private final String password;
	private final String email;
	
	
	private BirdwatcherImpl(String name, String firstname, String username, String password, String email) {
		super();
		this.name = name;
		this.firstname = firstname;
		this.username = username;
		this.password = password;
		this.email = email;
	}
	
	public static Birdwatcher valueOf(String name, String firstname, String username, String password, String email) {
		return new BirdwatcherImpl(name, firstname, username, password, email);
	}

	
//	public void id(String id) {
//		// SINGLE ASSIGNMENT
//		if (id != null) {
//			this.id = id;
//		} else {
//			System.out.println("Es wurde versucht, die ID des Birdwatcher-Objekts von " + this.id + " auf " + id + " zu setzen");
//		}
//	}
	
	public String name() {
		return name;
	}

	public String firstname() {
		return firstname;
	}
	
	public String username() {
		return username;
	}
	
	public String password() {
		return password;
	}

	public String email() {
		return email;
	}
	
	
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result
				+ ((firstname == null) ? 0 : firstname.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((password == null) ? 0 : password.hashCode());
		result = prime * result
				+ ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BirdwatcherImpl other = (BirdwatcherImpl) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (firstname == null) {
			if (other.firstname != null)
				return false;
		} else if (!firstname.equals(other.firstname))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	

	


}
