package headOffice;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import db.DatabaseImpl;


public class PatientDataLayer implements PatientDataLayerInterface {
	
	public PatientDataLayer(){
		
	}
	//Call add patient method on the stub
	public boolean addPatient(Patient patient) {
		try {
			// Get the registry from the server (null = local host)
			Registry registry = LocateRegistry.getRegistry(null);

			// Look up the remote object
			DatabaseImpl stub = (DatabaseImpl) registry.lookup("Database");
			boolean res = stub.addPatient(patient);
			
			return res;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}	
	}
	//call the update method on the stub
	public boolean updatePatient(String regNo, Patient patient) {
		try {
			// Get the registry from the server (null = local host)
			Registry registry = LocateRegistry.getRegistry(null);

			// Look up the remote object
			DatabaseImpl stub = (DatabaseImpl) registry.lookup("Database");
			boolean res= stub.updatePatient(regNo, patient);
			return res;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}	
	}
	// get a patient from the db by calling this method to the stub
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
