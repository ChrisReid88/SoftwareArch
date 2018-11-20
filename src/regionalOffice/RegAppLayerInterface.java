package regionalOffice;

public interface RegAppLayerInterface {
	

	public String addCall(String name);

	/**
	 * Gets a Student record
	 * 
	 * @param regNo The NHS registration number of the Patient to get
	 * @return Message indicating the outcome of getting the Patient
	 */
	public String getPatient(String regNo);
}
