package eredua.bean;

import javax.faces.bean.SessionScoped;

import businessLogicRides24.BLFacade;
import businessLogicRides24.BLFacadeImplementation;
import nagusia.DataAccess;

@SessionScoped
public class FacadeBean {

	private static FacadeBean singleton = new FacadeBean();
	private static BLFacade facadeInterface;

	private FacadeBean() {
		try {
			facadeInterface = new BLFacadeImplementation(new DataAccess());
		} catch (Exception e) {
			System.out.println("FacadeBean: negozioaren logika sortzean errorea: " + e.getMessage());
		}
	}

	public static BLFacade getBusinessLogic() {
		return facadeInterface;
	}

}
