package com.qualitylogic.openadr.core.util;

import org.eclipse.jetty.util.log.Logger;

public class NoLog implements Logger {
	private static boolean debug = System.getProperty("DEBUG", null) != null;
	private String name;

	static {
	}

	public NoLog() {
		this(null);
	}

	public NoLog(String name) {
		this.name = name == null ? "" : name;
	}

	public boolean isDebugEnabled() {
		return debug;
	}

	public void setDebugEnabled(boolean enabled) {
		debug = enabled;
	}

	public void info(String msg, Object arg0, Object arg1) {
	}

	public void debug(String msg, Throwable th) {
	}

	public void debug(String msg, Object arg0, Object arg1) {
	}

	public void warn(String msg, Object arg0, Object arg1) {
	}

	public void warn(String msg, Throwable th) {
	}

	public Logger getLogger(String name) {
		if ((name == null && this.name == null)
				|| (name != null && name.equals(this.name)))
			return this;
		return new NoLog(name);
	}

	@Override
	public String toString() {
		return "NOLOG" + name;
	}

	public void debug(String arg0) {
	}

	public String getName() {
		return null;
	}

	public void info(String arg0) {
	}

	public void warn(String arg0) {
	}

	public void debug(Throwable arg0) {
		// TODO Auto-generated method stub
		
	}

	public void debug(String arg0, Object... arg1) {
		// TODO Auto-generated method stub
		
	}

	public void ignore(Throwable arg0) {
		// TODO Auto-generated method stub
		
	}

	public void info(Throwable arg0) {
		// TODO Auto-generated method stub
		
	}

	public void info(String arg0, Object... arg1) {
		// TODO Auto-generated method stub
		
	}

	public void info(String arg0, Throwable arg1) {
		// TODO Auto-generated method stub
		
	}

	public void warn(Throwable arg0) {
		// TODO Auto-generated method stub
		
	}

	public void warn(String arg0, Object... arg1) {
		// TODO Auto-generated method stub
		
	}

}
