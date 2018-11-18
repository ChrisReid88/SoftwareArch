package headOffice;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import db.DatabaseImpl;


public class PatientDataLayer implements PatientDataLayerInterface {
	
	public PatientDataLayer(){
		
	}

	public boolean addPatient(Patient patient) {
		try {
			// Get the registry from the server (null = local host)
			Registry registry = LocateRegistry.getRegistry(null);

			// Look up the remote object
			DatabaseImpl stub = (DatabaseImpl) registry.lookup("Database");
			stub.addPatient(patient);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}	
	}

	public boolean updatePatient(String regNo, Patient patient) {
		try {
			// Get the registry from the server (null = local host)
			Registry registry = LocateRegistry.getRegistry(null);

			// Look up the remote object
			DatabaseImpl stub = (DatabaseImpl) registry.lookup("Database");
			stub.updatePatient(regNo, patient);
			return true;
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
