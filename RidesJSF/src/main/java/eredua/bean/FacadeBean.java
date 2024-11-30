package eredua.bean;

import javax.faces.bean.SessionScoped;

import businessLogic.BLFacade;
import businessLogic.BLFacadeImplementation;
import dataAccess.DataAccess;

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
