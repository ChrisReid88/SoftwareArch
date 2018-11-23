package headOffice;

import java.io.Serializable;

public class Patient implements Serializable {


	private String firstname; 
	private String lastname;
	private String regNumber;
	private String address;
	private String condition;

	public Patient(String fname, String lname, String regNo, String address, String cond) {
		this.firstname = fname;
		this.lastname = lname;
		this.regNumber = regNo;
		this.address = address;
		this.condition = cond;
	}
	
	
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getRegNumber() {
		return regNumber;
	}
	public void setRegNumber(String regNumber) {
		this.regNumber = regNumber;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}
	
}
