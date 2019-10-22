package com.honeywell.reflection;

import java.util.List;

public class TestReflection {
	
private int integervalue;
private String stringvalue;
private List<String> stringvalues;
public List<String> getStringvalues() {
	return stringvalues;
}

public void setStringvalues(List<String> stringvalues) {
	this.stringvalues = stringvalues;
}
private Boolean booleanvalue;
public TestReflection()
{
	
}

public TestReflection(String value)
{
	this.stringvalue = value;
}
public int getIntegervalue() {
	return integervalue;
}
public void setIntegervalue(int integervalue) {
	this.integervalue = integervalue;
}
public String getStringvalue() {
	return stringvalue;
}
public void setStringvalue(String stringvalue) {
	this.stringvalue = stringvalue;
}
public Boolean getBooleanvalue() {
	return booleanvalue;
}
public void setBooleanvalue(Boolean booleanvalue) {
	this.booleanvalue = booleanvalue;
}



}
