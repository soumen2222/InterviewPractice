package com.soumen.generic.inheritence.Example2;

import java.io.Serializable;

public class PayloadBaseType implements Serializable
{

    private final static long serialVersionUID = 1L;
    protected float value;

    /**
     * Gets the value of the value property.
     * 
     */
    public float getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     * 
     */
    public void setValue(float value) {
        this.value = value;
    }

}
