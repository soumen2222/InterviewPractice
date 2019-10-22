package com.honeywell.ven.services;

import javax.ejb.Local;
import javax.ejb.Remote;


public interface VenSimServicehelper {
	@Remote
    public interface R extends VenSimServicehelper {}
    @Local
    public interface L extends VenSimServicehelper {}
    
    public VenSimService getVenSimService();
    
}
