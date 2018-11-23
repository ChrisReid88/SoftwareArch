package regionalOffice;

public class RegSystem {

	public static void main(String[] args) {
		//Start all three layers on the Regional office
		RegDataLayer dataLayer = new RegDataLayer();
		RegAppLayer appLayer = new RegAppLayer(dataLayer);
		RegionalGUI gui = new RegionalGUI(appLayer);
		

	}
}
