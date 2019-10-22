package com.honeywell.account.creater;


public class PartyDTO {
	
	private String operatorID;
	private String displayName;
	private String lastName;
	private String password;
	private String confirmPassword;
	private String notes;
	private String entityID;
	
	public String getOperatorID() {
		return operatorID;
	}
	public void setOperatorID(String operatorID) {
		this.operatorID = operatorID;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}


	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	@Override
	public boolean equals(Object object)
	{
		 if (!(object instanceof PartyDTO)) {
			              return false;
		 }
		 PartyDTO other = (PartyDTO)object;
		 if (this.operatorID != other.operatorID && (this.operatorID == null ||
			!this.operatorID.equals(other.operatorID))) 
			return false;
			return true;

	}
	public String getEntityID() {
		return entityID;
	}
	public void setEntityID(String entityID) {
		this.entityID = entityID;
	}
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
}
