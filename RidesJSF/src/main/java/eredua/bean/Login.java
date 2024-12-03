package eredua.bean;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import businessLogicRides24.BLFacade;
import eredua.domeinua.User;

@ManagedBean(name = "Login")
@SessionScoped
public class Login {

	private BLFacade facade = FacadeBean.getBusinessLogic();
	private String email = "";
	private String pass = "";
	
	public Login() {
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
        return "Ok"; 
    }
}
