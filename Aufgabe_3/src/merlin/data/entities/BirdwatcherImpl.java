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
	
	public static Birdwatcher valueOf(int id, String name, String firstname, String username, String password, String email, String role){
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
