package regionalOffice;

public interface RegAppLayerInterface {
	

	public String addCall(String name, String lastname, String time, String location, String reason, String action, String regNumber);


	public String getPatient(String regNo);

	public String checkData();

}
