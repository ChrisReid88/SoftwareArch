package regionalOffice;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class RegSystem {

	public static void main(String[] args) {
		RegDataLayer dataLayer = new RegDataLayer();
		RegAppLayer appLayer = new RegAppLayer(dataLayer);
		RegionalGUI gui = new RegionalGUI(appLayer);
		

	}
}
