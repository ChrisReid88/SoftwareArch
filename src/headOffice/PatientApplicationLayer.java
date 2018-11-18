package headOffice;


public class PatientApplicationLayer implements PatientApplicationLayerInterface {
	
	//The underlying data layer this application layer sits on
	private PatientDataLayer dataLayer;
	
	/**
	 * 
	 * @param dataLayer The data layer this layer sits on
	 */
	public PatientApplicationLayer(PatientDataLayer dataLayer) {
		this.dataLayer = dataLayer;
	}
	
	
	public String addPatient(String fname, String lname, String regNo, String address, String cond) {
		Patient patient = new Patient(fname, lname, regNo, address, cond);
		boolean success = dataLayer.addPatient(patient);
		if (success) {
			return regNo + " added.";
		} else {
			return regNo + " failed to add.";
		}
	}

	
	public String getPatient(String regNo) {
		Patient patient = dataLayer.getPatient(regNo);
		if (patient != null)
		{
			// Return textual representation of the patient record
			return patient.getRegNumber() + "\n" + patient.getFirstname() + "\n" + patient.getLastname();
		}
		else
		{
			// Return fail message
			return "patient " + regNo + " does not exist";
		}
	}
	
	public String updatePatient(String fname, String lname, String regNo, String address, String cond) {
		// Create a new Patient record object
		Patient patient = new Patient(fname, lname, regNo, address, cond);
		// Try and update the Patient record with the data layer
		boolean success = dataLayer.updatePatient(regNo, patient);
		if (success)
		{
			return regNo  + " successfully updated";
		}
		else
		{
			return regNo  + " not updated";
		}
	}


	
}

