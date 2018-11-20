package db;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import headOffice.Patient;

public class Database implements DatabaseImpl {

	public boolean addPatient(Patient patient) {
		try {

			Class.forName("com.mysql.cj.jdbc.Driver");
			// Establish a connection to the database
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/patients?user=Java&password=Java");

			Statement statement2 = conn.createStatement();
			// Query to get all records from the database
			String query = "SELECT *  FROM patientrecords WHERE regNumber = '" + patient.getRegNumber() + "'";
			// Results from executing the query
			ResultSet results = statement2.executeQuery(query);

			if (!results.next()) {
				// Create a new SQL statement
				statement2.close();
				Statement statement = conn.createStatement();

				// Build the INSERT statement
				String update = "INSERT INTO patientrecords (Firstname, Lastname, RegNumber, Address, MedCondition) VALUES ('"
						+ patient.getFirstname() + "', '" + patient.getLastname() + "', '" + patient.getRegNumber()
						+ "', '" + patient.getAddress() + "', '" + patient.getCondition() + "')";
				// Execute the statement

				statement.executeUpdate(update);
				// Release resources held by the statement
				statement.close();
				// Release resources held by the connection. This also ensures that the INSERT
				// completes
				conn.close();
				System.out.println("Update successful");
				return true;
			} else {
				System.out.println("Matric taken!");
				return false;
			}
		} catch (ClassNotFoundException cnf) {
			System.err.println("Could not load driver");
			System.err.println(cnf.getMessage());

		} catch (SQLException sqe) {
			System.err.println("Error performing SQL Update");
			System.err.println(sqe.getMessage());
		}

		return false;
	}

	public Patient getPatient(String regNo) {

		Patient patient = null;
		try {

			String firstname = "";
			String address = "";
			String lastname = "";
			String cond = "";
			String regNumber = "";

			// Load the driver
			Class.forName("com.mysql.cj.jdbc.Driver");

			// Establish a connection to the database
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/patients?user=Java&password=Java");
			Statement statement = conn.createStatement();
			// Query to get all records from the database
			String query = "SELECT * FROM  patientrecords WHERE RegNumber = '" + regNo + "'";
			// Return results
			ResultSet results = statement.executeQuery(query);

			// While results aren't empty retrieve each field from the record
			while (results.next()) {
				firstname = results.getString("Firstname");
				lastname = results.getString("Lastname");
				regNumber = results.getString("RegNumber");
				address = results.getString("Address");
				cond = results.getString("MedCondition");
			}

			// Store in the instantiated patient object
			patient = new Patient(firstname, lastname, regNumber, address, cond);

			// Close statement and connection
			statement.close();
			conn.close();

		} catch (ClassNotFoundException cnf) {
			System.err.println("Could not load driver");
			System.err.println(cnf.getMessage());
		} catch (SQLException sqe) {
			System.out.println("Error performing SQL Query");
			System.out.println(sqe.getMessage());
		}
		return patient;

	}

	public boolean updatePatient(String regNo, Patient patient) {
		try {
			// Load the driver
			Class.forName("com.mysql.cj.jdbc.Driver");
			// Establish a connection to the database
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/patients?user=Java&password=Java");

			// Create statement object. We need to traverse results so allow scrolling
			Statement statement = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			// Create query string
			String query = "SELECT * FROM  patientrecords WHERE RegNumber = '" + regNo + "'";
			// Get results from query
			ResultSet results = statement.executeQuery(query);
			// Check if we have results
			if (results.next()) {
				// We will update the first hit (there should be only one)
				results.first();

				// Update the relevant columns in the DB
				results.updateString("Firstname", patient.getFirstname());
				results.updateString("Lastname", patient.getLastname());
				results.updateString("RegNumber", patient.getRegNumber());
				results.updateString("Address", patient.getAddress());
				results.updateString("MedCondition", patient.getCondition());
				// Update the row in the DB
				results.updateRow();
				System.out.println("Record updated");
			} else {
				// No matching records. Display message
				System.out.println("Record does not exist");
				return false;
			}
			// Free statement resources
			statement.close();
			// Free connection resources and commit updates
			conn.close();
		} catch (ClassNotFoundException cnf) {
			System.err.println("Could not load driver");
			System.err.println(cnf.getMessage());
		} catch (SQLException sqe) {
			System.err.println("Error in SQL Update");
			System.err.println(sqe.getMessage());
		}
		return true;

	}

	public boolean addCall(String name) {
		try {

			Class.forName("com.mysql.cj.jdbc.Driver");
			// Establish a connection to the database
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/patients?user=Java&password=Java");

			Statement statement2 = conn.createStatement();
			// Query to get all records from the database
			String query = "INSERT INTO callrecords (Name) VALUES ('" + name + "')";
			// Results from executing the query
			statement2.executeUpdate(query);

			statement2.close();
			conn.close();
			System.out.println("Update successful");

		} catch (ClassNotFoundException cnf) {
			System.err.println("Could not load driver");
			System.err.println(cnf.getMessage());
		} catch (SQLException sqe) {
			System.err.println("Error in SQL Update");
			System.err.println(sqe.getMessage());
		}
		return true;
	}

}