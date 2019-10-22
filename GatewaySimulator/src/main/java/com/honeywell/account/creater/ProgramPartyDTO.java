package com.honeywell.account.creater;

import java.io.Serializable;
import java.util.List;

import com.honeywell.dras.configure.party.PartyRole;
import com.honeywell.dras.scapi.messages.party.ScParty;
import com.honeywell.dras.scapi.messages.program.ScProgram;

public class ProgramPartyDTO implements Serializable{
	
	private static final long serialVersionUID = -7727667022923620720L;
	private ScParty party;
	private boolean checkBoxSelect;
	private List<ScProgram> programs;
	
	public ProgramPartyDTO(){
		this(new ScParty());
	}
	
	public ProgramPartyDTO(ScParty party){
		this.party=party;
	}
	
	public ScParty getParty() {
		return party;
	}

	public String gePartyId() {
		return party.getEntityId();
	}

	public void setPartyId(String entityId) {
		party.setEntityId(entityId);
	}

	public String getUUID() {
		return party.getUUID();
	}

	public void setUUID(String UUID) {
		party.setUUID(UUID);
	}
	
	public String getAccountNumber() {
		return party.getAccountNumber();
	}
	
	public String getEntityId() {
		return party.getEntityId();
	}

	public void setEntityId(String entityId) {
		party.setEntityId(entityId);
	}

	public void setAccountNumber(String accountNumber) {
		party.setAccountNumber(accountNumber);
	}

	public void setAggregator(boolean aggregator) {
		party.setAggregator(aggregator);
	}

	public void setAuthUsername(String authUsername) {
		party.setAuthUsername(authUsername);
	}

	public void setAuthPassword(String authPassword) {
		party.setAuthPassword(authPassword);
	}

	public String getName() {
		return party.getName();
	}

	public void setName(String name) {
		party.setName(name);
	}

	public String getFirstName() {
		return party.getFirstName();
	}
	
	public void setFirstName(String firstName) {
		party.setFirstName(firstName);
	}

	public String getLastName() {
		return party.getLastName();
	}

	public void setLastName(String lastName) {
		party.setLastName(lastName);
	}

	public String getMiddleName() {
		return party.getMiddleName();
	}

	public void setMiddleName(String middleName) {
		party.setMiddleName(middleName);
	}

	public String getSalutation() {
		return party.getSalutation();
	}
	
	public void setSalutation(String salutation) {
		party.setSalutation(salutation);
	}

	public String getTitle() {
		return party.getTitle();
	}

	public void setTitle(String title) {
		party.setTitle(title);
	}

	public String getTimeZone() {
		return party.getTimeZone();
	}

	public void setTimeZone(String timeZone) {
		party.setTimeZone(timeZone);
	}

	public boolean isMedicalEntity() {
		return party.isMedicalEntity();
	}

	public void setMedicalEntity(boolean medicalEntity) {
		party.setMedicalEntity(medicalEntity);
	}

	public boolean isAggregator() {
		return party.isAggregator();
	}

	public String getStreetAddress() {
		return party.getStreetAddress();
	}

	public void setStreetAddress(String streetAddress) {
		party.setStreetAddress(streetAddress);
	}

	public String getUtilityName() {
		return party.getUtilityName();
	}

	public void setUtilityName(String utilityName) {
		party.setUtilityName(utilityName);
	}

	public String getLatitude() {
		return party.getLatitude();
	}

	public void setLatitude(String latitude) {
		party.setLatitude(latitude);
	}

	public String getLongitude() {
		return party.getLongitude();
	}

	public void setLongitude(String longitude) {
		party.setLongitude(longitude);
	}

	public String getCity() {
		return party.getCity();
	}

	public void setCity(String city) {
		party.setCity(city);
	}

	public String getState() {
		return party.getState();
	}

	public void setState(String state) {
		party.setState(state);
	}

	public String getPostalCode() {
		return party.getPostalCode();
	}

	public void setPostalCode(String postalCode) {
		party.setPostalCode(postalCode);
	}

	public String getCounty() {
		return party.getCounty();
	}

	public void setCounty(String county) {
		party.setCounty(county);
	}

	public String getPhoneNumber() {
		return party.getPhoneNumber();
	}

	public void setPhoneNumber(String phoneNumber) {
		party.setPhoneNumber(phoneNumber);
	}

	public String getFeeder() {
		return party.getFeeder();
	}

	public void setFeeder(String feeder) {
		party.setFeeder(feeder);
	}

	public String getSubstation() {
		return party.getSubstation();
	}

	public void setSubstation(String substation) {
		party.setSubstation(substation);
	}

	public String getMapNumber() {
		return party.getMapNumber();
	}

	public void setMapNumber(String mapNumber) {
		party.setMapNumber(mapNumber);
	}

	public String getPremiseId() {
		return party.getPremiseId();
	}

	public void setPremiseId(String premiseId) {
		party.setPremiseId(premiseId);
	}

	public String getServiceId() {
		return party.getServiceId();
	}

	public void setServiceId(String serviceId) {
		party.setServiceId(serviceId);
	}

	public String getTransformer() {
		return party.getTransformer();
	}

	public void setTransformer(String transformer) {
		party.setTransformer(transformer);
	}

	public String getGroup1() {
		return party.getGroup1();
	}

	public void setGroup1(String group1) {
		party.setGroup1(group1);
	}

	public String getGroup2() {
		return party.getGroup2();
	}

	public void setGroup2(String group2) {
		party.setGroup2(group2);
	}

	public PartyRole getPartyRole() {
		return party.getPartyRole();
	}

	public void setPartyRole(PartyRole partyRole) {
		party.setPartyRole(partyRole);
	}

	public String getAuthUsername() {
		return party.getAuthUsername();
	}

	public String getAuthPassword() {
		return party.getAuthPassword();
	}

	public boolean isCheckBoxSelect() {
		return checkBoxSelect;
	}

	public void setCheckBoxSelect(boolean checkBoxSelect) {
		this.checkBoxSelect = checkBoxSelect;
	}

	public List<ScProgram>  getPrograms() {
		return programs;
	}

	public void setPrograms(List<ScProgram> programs) {
		this.programs = programs;
	}
	
}
