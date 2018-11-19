package regionalOffice;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import db.DatabaseImpl;

public class RegDataLayer implements RegDataLayerInterface {

	public RegDataLayer() {

	}


	public boolean addCall(String fname) {
		try {
			// Get the registry from the server (null = local host)
			Registry registry = LocateRegistry.getRegistry(null);

			// Look up the remote object
			DatabaseImpl stub = (DatabaseImpl) registry.lookup("Database");
			stub.addCall(fname);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
