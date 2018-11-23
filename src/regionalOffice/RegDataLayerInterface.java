package regionalOffice;

import headOffice.Patient;

public interface RegDataLayerInterface  {
	
	public boolean addCall(Callout callout);
	
	public Patient getPatient(String regNo);
}
