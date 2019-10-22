package com.qualitylogic.openadr.core.signal.helper;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.qualitylogic.openadr.core.bean.ServiceType;
import com.qualitylogic.openadr.core.signal.OadrCancelOptType;
import com.qualitylogic.openadr.core.signal.OadrSignedObject;
import com.qualitylogic.openadr.core.util.OadrUtil;
import com.qualitylogic.openadr.core.util.PropertiesFileReader;
import com.qualitylogic.openadr.core.signal.OadrPayload;

public class CancelOptEventHelper {

	public static OadrCancelOptType createCreatedOptTypeEventFromString(String data) {
		OadrCancelOptType oadrCancelOptType = null;
		if (data == null || data.length() < 1)
			return null;

		try {

			JAXBContext testcontext = JAXBContext
					.newInstance("com.qualitylogic.openadr.core.signal");
			InputStream is = new ByteArrayInputStream(data.getBytes("UTF-8"));
			Unmarshaller unmarshall = testcontext.createUnmarshaller();
			//OadrCreatedEventType = (OadrCreatedEventType) unmarshall.unmarshal(is);

			//oadrCancelOptType = (OadrCancelOptType)((JAXBElement<Object>)unmarshall.unmarshal(is)).getValue();
			
			OadrSignedObject oadrSignedObject = ((OadrPayload)unmarshall.unmarshal(is)).getOadrSignedObject();
			oadrCancelOptType = oadrSignedObject.getOadrCancelOpt();

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return oadrCancelOptType;
	}

	public static OadrCancelOptType loadOadrCancelOptEvent(String fileName)
			throws JAXBException, FileNotFoundException,
			UnsupportedEncodingException {

		OadrCancelOptType oadrCancelOptType = ((OadrCancelOptType) new SchemaHelper()
				.loadTestDataXMLFile(fileName).getOadrCancelOpt());

		return oadrCancelOptType;
	}

	public static OadrCancelOptType createOadrCancelOptEvent(String optID)
			throws JAXBException, FileNotFoundException,
			UnsupportedEncodingException {

		OadrCancelOptType oadrCancelOptType = ((OadrCancelOptType) new SchemaHelper()
				.loadTestDataXMLFile("oadrCancelOpt.xml").getOadrCancelOpt());

		oadrCancelOptType.setOptID(optID);
		oadrCancelOptType.setRequestID(OadrUtil.createUniqueOadrCancelOptReqID());
		oadrCancelOptType.setVenID(new PropertiesFileReader().getVenID());
		
		return oadrCancelOptType;
	}
/*
public static boolean isExpectedOadrCancelOptReceived(ArrayList<String> expectedOptIDList){
	// Find the createdOptReceived for the canceledOpt to get the optID
	
	List<OadrCancelOptType> receivedOadrCancelOptTypeList = TestSession.getCancelOptTypeReceivedList();
	
	//ArrayList<OadrCreateOptType>  oadrCreateOptTypeExpectedList=(ArrayList<OadrCreateOptType>)eachDistributeEventAction.getExpectedOadrCreateOptListPreConditions();

		
		if(expectedOadrCancelOptTypeList==null ||receivedOadrCancelOptTypeList==null ||expectedOadrCancelOptTypeList.size()<1 ||receivedOadrCancelOptTypeList.size()<1){
			return false;
		}
		for(OadrCancelOptType oadrCancelOptType:expectedOadrCancelOptTypeList){
			
			//String expMarketContext=oadrCancelOptType.getMarketContext();
			String expReqId=oadrCancelOptType.getRequestID();
			//String expPEventID=oadrCancelOptType.getQualifiedEventID().getEventID();
			//OptTypeType expOptType=oadrCancelOptType.getOptType();
			//List<String> expResourceList=oadrCancelOptType.getEiTarget().getResourceID();
			int numberOfResourceMatched=-1;
			
				for(OadrCancelOptType eachReceivedOadrCancelOpt:receivedOadrCancelOptTypeList){

					eachReceivedOadrCancelOpt.get
					String recMarketContext="";
					//ArrayList<String> recResourceList=(ArrayList<String>)eachReceivedOadrCancelOpt.getEiTarget().getResourceID();
					
					//if(expResourceList.size()!=recResourceList.size()) continue;
					
					//if(expMarketContext.endsWith(recMarketContext) && expReqId.equals(recReqId) && expPEventID.equals(recPEventID) && expOptType.equals(recOptType)){
					//if(expReqId.equals(recReqId) && expPEventID.equals(recPEventID) && expOptType.equals(recOptType)){
					if(expPEventID.equals(recPEventID) && expOptType.equals(recOptType)){
					for(String eachExpRes:expResourceList){
								if(OadrUtil.isIDFoundInList(eachExpRes, recResourceList)){
									if(numberOfResourceMatched==-1){
										numberOfResourceMatched=0;
									}
									numberOfResourceMatched++;
								}
							}
						}else{
							continue;
						}
				}
				
				if(expResourceList.size()!=numberOfResourceMatched){
					return false;
				}
		}
		
		return true;
	}
	
	*/
}