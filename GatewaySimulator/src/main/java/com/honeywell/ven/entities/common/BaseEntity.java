package com.honeywell.ven.entities.common;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.annotations.GenericGenerator;

@MappedSuperclass
@XmlTransient
public class BaseEntity implements Serializable {

	private static final long serialVersionUID = 4935774976414909814L;

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String UUID;
	
	/** The creation time. */
	@Temporal( TemporalType.TIMESTAMP)
	protected Date creationTime = new Date();
	

   
   
    
    public String getUUID() {
		return UUID;
	}

	public void setUUID(String UUID) {
		this.UUID = setIfNotNull(UUID, this.UUID);
	}

	public Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}


	
	
	protected static <T> T setIfNotNull(T theirValue, T myValue) {
		if (theirValue != null) {
			return theirValue;
		} else {
			return myValue;
		}

	}

	@Override
	public boolean equals(Object obj) {
		boolean result = false;
		if (obj != null && obj instanceof BaseEntity) {
			// ensures the ids are the exact same
			String myId = this.getUUID();
			String theirId = ((BaseEntity) obj).getUUID();
			if (myId != null && theirId != null) {
				result = myId.equals(theirId);
			}
		}
		return result;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + " : " + getUUID();
	}

	@Override
	public int hashCode() {
		String id = getUUID();
		if (id == null) {
			return super.hashCode();
		} else {
			return id.hashCode();
		}
	}

}
