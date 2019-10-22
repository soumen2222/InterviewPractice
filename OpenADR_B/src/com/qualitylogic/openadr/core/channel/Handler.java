package com.qualitylogic.openadr.core.channel;

/**
 * Interface for handling messages received by channel Listeners.
 */
public interface Handler {
	String handle(final String message);
}
