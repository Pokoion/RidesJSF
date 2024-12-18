package eredua.bean;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import businessLogicRides24.BLFacade;
import eredua.LoggedUser;
import eredua.domeinua.User;

@ManagedBean(name = "Login")
@SessionScoped
public class Login {

	private BLFacade facade = FacadeBean.getBusinessLogic();
	private String email = "";
	private String pass = "";
	
    @ManagedProperty(value = "#{loggedUser}")
    private LoggedUser loggedUser;
    
	public Login() {
	}
	
	public LoggedUser getLoggedUser() {
		return loggedUser;
	}

	public void setLoggedUser(LoggedUser loggedUser) {
		this.loggedUser = loggedUser;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}
	public boolean validateInput() {
		if(this.email.isEmpty()) {
			FacesContext.getCurrentInstance().addMessage(null, 
				    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: ", "Please write a valid email."));
			return false;
		}
		if(this.pass.isEmpty()) {
			FacesContext.getCurrentInstance().addMessage(null, 
				    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: ", "Please write a valid password."));
			return false;
		}
		return true;
	}
	
    public String login() {
    	if(!validateInput()) return "";
    	User u = facade.login(email, pass);
    	if(u == null) {
			FacesContext.getCurrentInstance().addMessage(null, 
				    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: ", "User not found."));
			return "";
    	}
    	loggedUser.setUser(u);
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
        session.setAttribute("loggedUser", loggedUser);
        
        return u.goMain(); 
    }
}
