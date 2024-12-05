package eredua;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import eredua.domeinua.User;

import java.io.Serializable;

@ManagedBean
@SessionScoped
public class LoggedUser implements Serializable {
    private User user;
    
    public LoggedUser() {
	}

    public LoggedUser(User user) {
		this.user = user;
	}

	public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isLoggedIn() {
        return user != null;
    }

}
