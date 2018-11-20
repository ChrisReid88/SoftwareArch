package regionalOffice;

import headOffice.Patient;

public interface RegDataLayerInterface  {
	
	public boolean addCall(String name);
	
	/**
	 * 
	 * @param regNo The Patient's NHS Registration Number
	 * @param patient New Patient record
	 * @return True if update successful
	 */
	public Patient getPatient(String regNo);
}
