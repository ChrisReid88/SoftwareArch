package regionalOffice;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import headOffice.Patient;

public class RegAppLayer implements RegAppLayerInterface {
	
	private RegDataLayer dataLayer;

	public RegAppLayer (RegDataLayer dataLayer) {
		this.dataLayer = dataLayer;
		
	}
	
	//Create a new callout object and send to data layer
	public String addCall(String firstname, String lastname, String time, String location, String reason, String action,String regNumber) {
		Callout callout = new Callout(firstname, lastname, time, location, reason , action, regNumber);
		System.out.println("Callout:" +  callout);
		boolean success =  dataLayer.addCall(callout);
		if (success) {
			return "ADDED";
		}else {
			return "FAILED";
		}
	}
	
	public String getPatient(String regNo) {
		Patient patient = dataLayer.getPatient(regNo);
		if (patient != null)
		{
			// Return textual representation of the patient record
			return patient.getRegNumber() + "\n" + 
					patient.getFirstname() + "\n" + 
					patient.getLastname() + "\n" + 
					patient.getAddress() + "\n" + 
					patient.getCondition() ;
		}
		else
		{
			// Return fail message
			return "patient " + regNo + " does not exist";
		}
	}
	
	//Server to check data (Unused)
	public String checkData() {
		String result = null;
		
		try {
			int serverPort = 7896;
			ServerSocket listenSocket = new ServerSocket(serverPort);
			while (true) {
				Socket clientSocket = listenSocket.accept();
				Connection c = new Connection(clientSocket);
				String data = c.getData();
				Patient patient = dataLayer.getPatient(data);
				
				result = "RECEIVED FROM HEAD OFFICE!" + "\nNHS Registration No.:    " + patient.getFirstname();
					
			}
		} catch (IOException e) {
			System.out.println("Listen: " + e.getMessage());
		}
		return result;
	}

}
