package regionalOffice;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import db.DatabaseImpl;
import headOffice.Patient;

public class RegDataLayer implements RegDataLayerInterface {

	public RegDataLayer() {

	}


	public boolean addCall(Callout callout) {
		try {
			// Get the registry from the server (null = local host)
			Registry registry = LocateRegistry.getRegistry(null);

			// Look up the remote object
			DatabaseImpl stub = (DatabaseImpl) registry.lookup("Database");
			boolean res = stub.addCall(callout);
			return res;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public Patient getPatient(String regNo) {
		Patient student = null;
		try {
			// Get the registry from the server (null = local host)
			Registry registry = LocateRegistry.getRegistry(null);

			// Look up the remote object
			DatabaseImpl stub = (DatabaseImpl) registry.lookup("Database");
			student = stub.getPatient(regNo);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return student;
	}

}
