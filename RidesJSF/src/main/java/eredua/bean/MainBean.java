package eredua.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import eredua.LoggedUser;

@ManagedBean(name = "main")
@SessionScoped
public class MainBean {

    @ManagedProperty(value = "#{loggedUser}")
    private LoggedUser loggedUser;
    
	public MainBean() {
		// TODO Auto-generated constructor stub
	}
	
	public LoggedUser getLoggedUser() {
		return loggedUser;
	}

	public void setLoggedUser(LoggedUser loggedUser) {
		this.loggedUser = loggedUser;
	}
	
	public String logout() {
	    FacesContext facesContext = FacesContext.getCurrentInstance();
	    HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
	    HttpSession session = request.getSession(false);
	    if (session != null) {
	        session.invalidate();
	    }
		return "Close";
	}
}
