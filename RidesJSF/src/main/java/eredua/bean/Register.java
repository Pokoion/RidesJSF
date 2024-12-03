package eredua.bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import businessLogicRides24.BLFacade;
import eredua.domeinua.User;
import exceptions.UserAlreadyExistsException;

@ManagedBean(name = "Register")
@SessionScoped
public class Register {

	private BLFacade facade = FacadeBean.getBusinessLogic();
	private String email = "";
	private String name = "";
	private String pass = "";
	private String pass2 = "";
	private String selectedType = User.TYPES.TRAVELER;
	private List<String> types;
	
	public Register() {
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
	
	public String getPass2() {
		return pass2;
	}

	public void setPass2(String pass2) {
		this.pass2 = pass2;
	}
	
	public List<String> getTypes() {
		List<String> types = new ArrayList<>();
		types.add(User.TYPES.TRAVELER);
		types.add(User.TYPES.DRIVER);
		types.add(User.TYPES.ADMIN);
		return types;
	}

	public void setTypes(List<String> types) {
		this.types = types;
	}

	public String getSelectedType() {
		return selectedType;
	}

	public void setSelectedType(String selectedType) {
		this.selectedType = selectedType;
	}

	public boolean validateInput() {
		String mailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
		
		if(this.email.isEmpty() || !this.email.matches(mailRegex)) {
			FacesContext.getCurrentInstance().addMessage(null, 
				    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: ", "Please write a valid email."));
			return false;
		}
		
		if(this.name.isEmpty()) {
			FacesContext.getCurrentInstance().addMessage(null, 
				    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: ", "Please write a valid name."));
			return false;
		}
		
		if(this.pass.isEmpty()) {
			FacesContext.getCurrentInstance().addMessage(null, 
				    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: ", "Please write a valid password."));
			return false;
		}
		
		if(!this.pass.equals(this.pass2)) {
			FacesContext.getCurrentInstance().addMessage(null, 
				    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: ", "Please repeat the password correctly."));
			return false;
		}
		
		return true;
	}
	
    public void register() {
    	if(!validateInput()) return;
    	try {
    		
    		facade.register(email, name, pass, selectedType);
			
		} catch (UserAlreadyExistsException | RuntimeException e) {
			FacesContext.getCurrentInstance().addMessage(null, 
				    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: ", e.getMessage()));
			e.printStackTrace();
			return;			
		}
    	
		FacesContext.getCurrentInstance().addMessage(null, 
			    new FacesMessage(FacesMessage.SEVERITY_INFO, "Info: ", "You have been registered correctly!"));
    }

}
