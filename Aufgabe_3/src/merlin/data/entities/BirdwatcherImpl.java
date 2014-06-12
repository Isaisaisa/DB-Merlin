package merlin.data.entities;

public class BirdwatcherImpl implements Birdwatcher {

	private final String id;
	private final String name;
	private final String firstname;
	private final String username;
	private final String password;
	private final String email;
	private final String role;
	public final static String BWROLE = "R03";
	
	private BirdwatcherImpl(String id, String name, String firstname, String username, String password, String email, String role) {
		super();
		this.id = id;
		this.name = name;
		this.firstname = firstname;
		this.username = username;
		this.password = password;
		this.email = email;
		this.role = role;
	}
	
	public static Birdwatcher valueOf(String id, String name, String firstname, String username, String password, String email, String role){
		return new BirdwatcherImpl(id, name, firstname, username, password, email, role);
	}

	
	public String id() {
		return id;
	}

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

	public String role() {
		return role;
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
		result = prime * result + ((role == null) ? 0 : role.hashCode());
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
		if (role == null) {
			if (other.role != null)
				return false;
		} else if (!role.equals(other.role))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	
	

}

