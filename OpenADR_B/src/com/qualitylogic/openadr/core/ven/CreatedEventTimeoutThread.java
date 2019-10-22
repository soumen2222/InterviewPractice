package com.qualitylogic.openadr.core.ven;

import java.util.Date;

import com.qualitylogic.openadr.core.action.ICreatedEventResult;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.util.PropertiesFileReader;
import com.qualitylogic.openadr.core.util.Trace;

public class CreatedEventTimeoutThread implements Runnable {
	ICreatedEventResult createdEventTimeoutAction;
	PropertiesFileReader propertiesFileReader = new PropertiesFileReader();
	long createdEventAsynchTimeout = Long.valueOf(propertiesFileReader
			.get("createdEventAsynchTimeout"));
	long firstCreatedEventReceiptTimeOutThreshold = System.currentTimeMillis()
			+ createdEventAsynchTimeout;
	Trace trace;

	public CreatedEventTimeoutThread(
			ICreatedEventResult createdEventTimeoutAction) {
		this.createdEventTimeoutAction = createdEventTimeoutAction;
		propertiesFileReader = new PropertiesFileReader();
		createdEventAsynchTimeout = Long.valueOf(propertiesFileReader
				.get("createdEventAsynchTimeout"));
		firstCreatedEventReceiptTimeOutThreshold = System.currentTimeMillis()
				+ createdEventAsynchTimeout;
		trace = TestSession.getTraceObj();

	}

	public void run() {
		try {

			if (createdEventTimeoutAction.isExpectedCreatedEventReceived()) {
				return;
			}

			System.out.println("Created EventTimeout start :" + new Date());

			while (true) {

				if (createdEventTimeoutAction.isExpectedCreatedEventReceived()) {
					return;
				}

				if (System.currentTimeMillis() > firstCreatedEventReceiptTimeOutThreshold) {
					if (trace != null) {
						trace.getLogFileContentTrace()
								.append("\nThe expected CreatedEvent was not received within the expected time\n");
					}

					TestSession.setCreatedEventNotReceivedTillTimeout(true);
					System.out
							.println("Created EventTimeout occured - breaking out of loop.. : "
									+ new Date());
					break;
				} else {
					Thread.sleep(500);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
