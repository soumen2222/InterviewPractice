package com.qualitylogic.openadr.core.ven;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;

import org.restlet.engine.Helper;

import com.qualitylogic.openadr.core.action.ICreatedEventAction;
import com.qualitylogic.openadr.core.action.ICreateOptEventAction;
import com.qualitylogic.openadr.core.bean.VTNServiceType;
import com.qualitylogic.openadr.core.common.IPrompt;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.signal.OadrCreateOptType;
import com.qualitylogic.openadr.core.signal.OadrCreatedEventType;
import com.qualitylogic.openadr.core.signal.OadrCreatedOptType;
import com.qualitylogic.openadr.core.signal.OadrPayload;
import com.qualitylogic.openadr.core.signal.OadrResponseType;
import com.qualitylogic.openadr.core.signal.OadrSignedObject;
import com.qualitylogic.openadr.core.signal.helper.CreatedEventHelper;
import com.qualitylogic.openadr.core.signal.helper.CreatedOptEventHelper;
import com.qualitylogic.openadr.core.signal.helper.SchemaHelper;
import com.qualitylogic.openadr.core.util.OadrUtil;
import com.qualitylogic.openadr.core.util.OadrValidationEventHandler;
import com.qualitylogic.openadr.core.util.Trace;

public class CreatedEventHandlerThread implements Runnable {
	ICreatedEventAction createdEventAction;

	public CreatedEventHandlerThread(ICreatedEventAction createdEventAction) {
		this.createdEventAction = createdEventAction;
	}

	public void run() {
		try {
			Trace trace = TestSession.getTraceObj();
			createdEventAction.setEventCompleted(true);
			Thread.sleep(2000);

			String oadrResponse = VenToVtnClient.post(createdEventAction
					.getCreateEvent());

			JAXBContext testcontext = JAXBContext
					.newInstance("com.qualitylogic.openadr.core.signal");
			InputStream is = new ByteArrayInputStream(
					oadrResponse.getBytes("UTF-8"));
			Unmarshaller unmarshall = testcontext.createUnmarshaller();
			//new SchemaHelper();
			unmarshall.setSchema(SchemaHelper.getSchema());

			if (trace != null) {
				unmarshall.setEventHandler(new OadrValidationEventHandler());
			}
			//Object test = unmarshall.unmarshal(is);
			@SuppressWarnings("unchecked")
			//Object test = ((JAXBElement<Object>)unmarshall.unmarshal(is)).getValue();

			OadrSignedObject oadrSignedObject = ((OadrPayload)unmarshall.unmarshal(is)).getOadrSignedObject();
			Object test = new SchemaHelper().getPayloadFromOadrSignedObject(oadrSignedObject);
				
			OadrResponseType oadrResponseObj = (OadrResponseType) test;
			TestSession.addOadrResponseList(oadrResponseObj);
			
			 ArrayList<ICreateOptEventAction>  createOptEventActionList = createdEventAction.getCreateOptEventList();
			
			
			 for(ICreateOptEventAction eachCreateOptEventAction:createOptEventActionList){
				 if(!eachCreateOptEventAction.isPreConditionsMet()){
					 System.out.println("Preconditions are not met. Skipping sending createOpt");
				 }
				 
				 Thread.sleep(2000);
				 OadrCreateOptType oadrCreateOptType  = eachCreateOptEventAction.getOadrCreateOpt();
				 String oadrCreateOptTypeStr = SchemaHelper.getOadrCreateOptAsString(oadrCreateOptType);
				 String oadrCreatedOptResponse = VenToVtnClient.post(oadrCreateOptTypeStr,VTNServiceType.EiOpt);
				 OadrCreatedOptType oadrCreatedOptEventType  = CreatedOptEventHelper.createCreatedOptTypeEventFromString(oadrCreatedOptResponse);
				 
				 if (eachCreateOptEventAction.islastEvent()) {
						TestSession.setTestCaseDone(true);
				 }
			}
			 
			Thread.sleep(2000);
			IPrompt prompt = createdEventAction.getPromptForResponseReceipt();
			if (prompt != null) {
				prompt.Prompt(prompt.getPromptMessage());
			}
	
			if (createdEventAction.isLastCreateEvent()) {
				TestSession.setTestCaseDone(true);
			}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
