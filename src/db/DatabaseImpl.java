package db;

import java.rmi.Remote;
import java.rmi.RemoteException;

import headOffice.Patient;


public interface DatabaseImpl extends Remote {
	
	/**
	 * Adds a new Patient to the data store
	 * 
	 * @param Patient Patient to add to the data store
	 * @return True if Patient added, false otherwise
	 */
	public boolean addPatient(Patient patient) throws RemoteException;
	
	/**
	 * Returns a Patient record from the data store
	 * 
	 * @param regNo The Patient's NHS registration number
	 * @return The Patient record if it exists, null otherwise
	 */
	public Patient getPatient(String regNo) throws RemoteException;
	
	/**
	 * Updates a Patient record in data store
	 * 
	 * @param regNo The Patient's NHS registration number
	 * @param patient The Patient's NHS registration number
	 * @return True if Patient updated, false otherwise 
	 */
	public boolean updatePatient(String regNo, Patient patient) throws RemoteException;
	
}
