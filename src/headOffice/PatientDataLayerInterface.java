package headOffice;

import java.rmi.RemoteException;

public interface PatientDataLayerInterface {
	
	/**
	 * Adds a new Patient to the data base
	 * @param fname The Patient's first name
	 * @param lname The Patient's last name
	 * @param regNo The Patient's NHS registration number
	 * @param address The Patient's address
	 * @param cond The patient's medical condition
	 * @return Message indicating outcome of adding Patient
	 */
	public boolean addPatient(Patient patient);
	
	/**
	 * Updates an existing Patient record
	 * @param regNo The Patient's NHS Registration number
	 * @return The Patient's record
	 */

	
	/**
	 * 
	 * @param regNo The Patient's NHS Registration Number
	 * @param patient New Patient record
	 * @return True if update successful
	 */
	public Patient getPatient(String regNo);
	public boolean updatePatient(String regNo, Patient patient);

}


