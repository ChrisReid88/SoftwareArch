package headOffice;

public class NHSRecordSystem 
{
	public static void main(String[] args)
	{
		PatientDataLayer dataLayer = new PatientDataLayer();
		PatientApplicationLayer appLayer = new PatientApplicationLayer(dataLayer);
		GUI gui = new GUI(appLayer);
	}
}
