package db;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class DBServer {
	public  static void main(String args[]) {
		try { 
			
			//Start the registry
			 Registry registry = LocateRegistry.createRegistry(1099); 
			
	        // Instantiate the concrete class 
	         Database db= new Database(); 
	    
	         // Export the stub using the interface specification
	         DatabaseImpl stub = (DatabaseImpl) UnicastRemoteObject.exportObject(db, 0);  
	         
	         //Bind the stub to the name "Database" in the registry 
	         registry.bind("Database", stub);  
	         
	         System.out.println("Patient server ready");
	         
	      } catch (Exception e) { 
	         System.err.println("Server exception: " + e.toString()); 
	         e.printStackTrace(); 
	      } 
	}
}