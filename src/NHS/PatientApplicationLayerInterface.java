package NHS;

public interface PatientApplicationLayerInterface {

	/**
	 * Adds a new Patient to the underlying Data Layer
	 * 
	 * @param fname The Patient's first name
	 * @param lname The Patient's last name
	 * @param regNo The Patient's NHS registration number
	 * @param address The Patient's address
	 * @param cond The patient's medical condition
	 * @return Message indicating outcome of adding Patient
	 */
	public String addPatient(String fname, String lname, String regNo, String address, String cond);
	
	/**
	 * Updates an existing Patient record
	 * 
	 * @param fname The Patient's first name
	 * @param lname The Patient's last name
	 * @param regNo The Patient's NHS registration number
	 * @param address The Patient's address
	 * @param cond The patient's medical condition
	 * @return Message indicating outcome of adding Patient
	 */
	public String updatePatient(String fname, String lname, String regNo, String address, String cond);
	
	/**
	 * Gets a Student record
	 * 
	 * @param regNo The NHS registration number of the Patient to get
	 * @return Message indicating the outcome of getting the Patient
	 */
	public String getPatient(String regNo);

}
