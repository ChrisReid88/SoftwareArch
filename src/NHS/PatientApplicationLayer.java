package NHS;

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

	@Override
	public String updatePatient(String fname, String lname, String regNo, String address, String cond) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPatient(String regNo) {
		// TODO Auto-generated method stub
		return null;
	}

	
}

