package regionalOffice;

import java.io.Serializable;

public class Callout implements Serializable {
	

	private static final long serialVersionUID = 1L;
		private String firstName;
		private String lastname;
		private String time;
		private String location;
		private String reason;
		private String action;
		private String regNumber;
		

		public Callout(String firstname, String lastname, String time, String location, String reason, String action, String regNumber) {
			this.firstName = firstname;
			this.lastname = lastname;
			this.time = time;
			this.location = location;
			this.reason = reason;
			this.action = action;
			this.regNumber = regNumber;
		}
		
		public String getFirstName() {
			return firstName;
		}
		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}
		public String getLastname() {
			return lastname;
		}
		public void setLastname(String lastname) {
			this.lastname = lastname;
		}
		public String getTime() {
			return time;
		}
		public void setTime(String time) {
			this.time = time;
		}
		public String getLocation() {
			return location;
		}
		public void setLocation(String location) {
			this.location = location;
		}
		public String getReason() {
			return reason;
		}
		public void setReason(String reason) {
			this.reason = reason;
		}
		public String getAction() {
			return action;
		}
		public void setAction(String action) {
			this.action = action;
		}
		public String getRegNumber() {
			return regNumber;
		}
		public void setRegNumber(String regNumber) {
			this.regNumber = regNumber;
		}

		
}
		
