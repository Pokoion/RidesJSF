package eredua.bean;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import businessLogicRides24.BLFacade;
import eredua.LoggedUser;
import eredua.domeinua.Alarm;

@ManagedBean(name = "main")
@SessionScoped
public class MainBean {

	private BLFacade facade = FacadeBean.getBusinessLogic();
	
    FacesContext facesContext = FacesContext.getCurrentInstance();
    HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
    private LoggedUser loggedUser = (LoggedUser) session.getAttribute("loggedUser");
    
    private List<Alarm> alarms;
    
	public MainBean() {
	}
	
	public LoggedUser getLoggedUser() {
		return loggedUser;
	}

	public void setLoggedUser(LoggedUser loggedUser) {
		this.loggedUser = loggedUser;
	}

	public List<Alarm> getAlarms() {
	    return facade.getRideMatchingAlarms(loggedUser.getUser().getEmail());
	}

	public void setAlarms(List<Alarm> alarms) {
		this.alarms = alarms;
	}
	
	public void deactiveAlarm(Alarm alarm) {
		if(facade.disableAlarm(loggedUser.getUser().getEmail(), alarm.getId())) {
			FacesContext.getCurrentInstance().addMessage(null, 
				    new FacesMessage(FacesMessage.SEVERITY_INFO, "Info: ", "Alarm deleted correctly!"));
		}else {
			FacesContext.getCurrentInstance().addMessage(null, 
				    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: ", "Alarm could not be deleted."));
		}
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
