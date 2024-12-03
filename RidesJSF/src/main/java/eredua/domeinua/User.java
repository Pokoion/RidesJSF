package eredua.domeinua;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class User {
	
	@Id
	private String email;
	private String name;
	private String pass;

	public User() {

	}
	
	public User(String email, String name, String pass) {
		this.email = email;
		this.name = name;
		this.pass = pass;
	}
	
    public static class TYPES {
        public static final String DRIVER = "Driver";
        public static final String ADMIN = "Admin";
        public static final String TRAVELER = "Traveler";
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

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	@Override
	public int hashCode() {
		return Objects.hash(email, name, pass);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(email, other.email) && Objects.equals(name, other.name)
				&& Objects.equals(pass, other.pass);
	}

	@Override
	public String toString() {
		return "User [email=" + email + ", name=" + name + "]";
	}
	
}
