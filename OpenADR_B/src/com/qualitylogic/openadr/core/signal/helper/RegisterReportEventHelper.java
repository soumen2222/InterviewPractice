package com.qualitylogic.openadr.core.signal.helper;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.qualitylogic.openadr.core.signal.EiTargetType;
import com.qualitylogic.openadr.core.signal.OadrPayload;
import com.qualitylogic.openadr.core.signal.OadrRegisterReportType;
import com.qualitylogic.openadr.core.signal.OadrReportDescriptionType;
import com.qualitylogic.openadr.core.signal.OadrReportType;
import com.qualitylogic.openadr.core.signal.OadrSignedObject;
import com.qualitylogic.openadr.core.signal.xcal.DurationPropType;
import com.qualitylogic.openadr.core.util.OadrUtil;
import com.qualitylogic.openadr.core.util.PropertiesFileReader;

public class RegisterReportEventHelper {

	@SuppressWarnings("unchecked")
	public static OadrRegisterReportType createOadrRegisterReportFromString(String data) {
		OadrRegisterReportType oadrRegisterReportType = null;
		if (data == null || data.length() < 1)
			return null;

		try {

			JAXBContext testcontext = JAXBContext
					.newInstance("com.qualitylogic.openadr.core.signal");
			InputStream is = new ByteArrayInputStream(data.getBytes("UTF-8"));
			Unmarshaller unmarshall = testcontext.createUnmarshaller();

			//oadrRegisterReportType = (OadrRegisterReportType)((JAXBElement<Object>)unmarshall.unmarshal(is)).getValue();
			OadrSignedObject oadrSignedObject = ((OadrPayload)unmarshall.unmarshal(is)).getOadrSignedObject();
			oadrRegisterReportType = oadrSignedObject.getOadrRegisterReport();

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return oadrRegisterReportType;
	}

	public static OadrRegisterReportType loadOadrRegisterReportType(String fileName)
			throws JAXBException, FileNotFoundException,
			UnsupportedEncodingException {

		OadrRegisterReportType oadrRegisterReportType = ((OadrRegisterReportType) new SchemaHelper()
				.loadTestDataXMLFile(fileName).getOadrRegisterReport());

		return oadrRegisterReportType;
	}

	public static OadrRegisterReportType loadOadrRegisterTelemetryStatusReportType()
			throws JAXBException, FileNotFoundException,
			UnsupportedEncodingException {

		/*OadrRegisterReportType oadrRegisterReportType = ((OadrRegisterReportType) new SchemaHelper()
				.loadTestDataXMLFile("oadrRegisiterReport_History_Usage.xml").getOadrRegisterReport());
*/
		OadrRegisterReportType oadrRegisterReportType = ((OadrRegisterReportType) new SchemaHelper()
		.loadTestDataXMLFile("oadrRegisiterReport_Telemetry_Status.xml").getOadrRegisterReport());

		PropertiesFileReader propertiesFileReader = new PropertiesFileReader();

		oadrRegisterReportType.setRequestID(OadrUtil.createUniqueRegisterReportReqID());
		oadrRegisterReportType.setVenID(propertiesFileReader.getVenID());
		oadrRegisterReportType.getOadrReport().get(0).setReportSpecifierID(OadrUtil.createUniqueReportSpecifierID());
		oadrRegisterReportType.getOadrReport().get(0).setReportName("METADATA_TELEMETRY_STATUS");
		oadrRegisterReportType.getOadrReport().get(0).getOadrReportDescription().get(0).getOadrSamplingRate().setOadrMinPeriod("PT5M");
		oadrRegisterReportType.getOadrReport().get(0).getOadrReportDescription().get(0).getOadrSamplingRate().setOadrMaxPeriod("PT5M");
		
		DurationPropType duration = new DurationPropType();
		duration.setDuration("PT30M");

		oadrRegisterReportType.getOadrReport().get(0).setDuration(duration);
		oadrRegisterReportType.getOadrReport().get(0).getOadrReportDescription().get(0).setRID(OadrUtil.createUniqueRID());
		
		oadrRegisterReportType.getOadrReport().get(0).setCreatedDateTime(OadrUtil.getCurrentTime());
		oadrRegisterReportType.getOadrReport().get(0).getOadrReportDescription().get(0).setMarketContext(propertiesFileReader.get("DR_MarketContext_1_Name"));
		return oadrRegisterReportType;
	}
	

	public static OadrRegisterReportType loadMetadata_001()
			throws JAXBException, FileNotFoundException,
			UnsupportedEncodingException {

		OadrRegisterReportType oadrRegisterReportType = ((OadrRegisterReportType) new SchemaHelper()
				.loadTestDataXMLFile("Metadata_001.xml").getOadrRegisterReport());

		String resourceInProperties = new PropertiesFileReader().getOneResourceID();
		
		
		PropertiesFileReader propertiesFileReader = new PropertiesFileReader();

		oadrRegisterReportType.setRequestID(OadrUtil.createUniqueRegisterReportReqID());
		oadrRegisterReportType.setVenID(propertiesFileReader.getVenID());

		 List<OadrReportType>  OadrReportList = oadrRegisterReportType.getOadrReport();

		 int i=0;
		 for(OadrReportType eachOadrReportType:OadrReportList){
		
						
			 	List<OadrReportDescriptionType>  oadrReportDescriptionList = eachOadrReportType.getOadrReportDescription();

			 	eachOadrReportType.setReportSpecifierID(OadrUtil.createUniqueReportSpecifierID()+"_"+i++);

			 	eachOadrReportType.setCreatedDateTime(OadrUtil.getCurrentTime());
			 	int j=0;
			 	for(OadrReportDescriptionType oadrReportDescriptionType:oadrReportDescriptionList){
			 		EiTargetType tagetType =  oadrReportDescriptionType.getReportDataSource();
			 		tagetType.getResourceID().clear();
			 		tagetType.getResourceID().add(resourceInProperties);
			 		String uniqueRID = OadrUtil.createUniqueRID()+"_"+j++;			 		
			 		oadrReportDescriptionType.setRID(uniqueRID);					
					oadrReportDescriptionType.setMarketContext(propertiesFileReader.get("DR_MarketContext_1_Name"));			 		
			 	}
				
		 }
		
		return oadrRegisterReportType;
	}
	
	public static OadrRegisterReportType loadMetadata_003()
			throws JAXBException, FileNotFoundException,
			UnsupportedEncodingException {

		OadrRegisterReportType oadrRegisterReportType = ((OadrRegisterReportType) new SchemaHelper()
				.loadTestDataXMLFile("Metadata_003.xml").getOadrRegisterReport());

		
		
		PropertiesFileReader propertiesFileReader = new PropertiesFileReader();

		oadrRegisterReportType.setRequestID(OadrUtil.createUniqueRegisterReportReqID());
		oadrRegisterReportType.setVenID(propertiesFileReader.getVenID());

		return oadrRegisterReportType;
	}

	public static OadrRegisterReportType loadOadrRegisterHistoryReportType()
			throws JAXBException, FileNotFoundException,
			UnsupportedEncodingException {

		OadrRegisterReportType oadrRegisterReportType = ((OadrRegisterReportType) new SchemaHelper()
				.loadTestDataXMLFile("oadrRegisiterReport_History_Usage.xml").getOadrRegisterReport());

	
		PropertiesFileReader propertiesFileReader = new PropertiesFileReader();

		oadrRegisterReportType.setRequestID(OadrUtil.createUniqueRegisterReportReqID());
		oadrRegisterReportType.setVenID(propertiesFileReader.getVenID());

		 List<OadrReportType>  OadrReportList = oadrRegisterReportType.getOadrReport();
		
		 int i=0;
		 for(OadrReportType eachOadrReportType:OadrReportList){
		
			 	eachOadrReportType.setReportSpecifierID(OadrUtil.createUniqueReportSpecifierID()+"_"+i++);
			 	eachOadrReportType.setReportName("METADATA_HISTORY_USAGE");
				eachOadrReportType.setCreatedDateTime(OadrUtil.getCurrentTime());
			 	
				DurationPropType duration = new DurationPropType();
				duration.setDuration("PT30M");

				eachOadrReportType.setDuration(duration);
				
			 	List<OadrReportDescriptionType>  oadrReportDescriptionList = eachOadrReportType.getOadrReportDescription();
			 	int j=0;
			 	for(OadrReportDescriptionType oadrReportDescriptionType:oadrReportDescriptionList){
			 		oadrReportDescriptionType.getOadrSamplingRate().setOadrMinPeriod("PT5M");
			 		oadrReportDescriptionType.getOadrSamplingRate().setOadrMaxPeriod("PT5M");
			 		String uniqueRID = OadrUtil.createUniqueRID()+"_"+j++;			 		
			 		oadrReportDescriptionType.setRID(uniqueRID);					
					oadrReportDescriptionType.setMarketContext(propertiesFileReader.get("DR_MarketContext_1_Name"));			 		
			 	}
				
		 }
		
		return oadrRegisterReportType;
	}
	
	public static void main(String []args) throws FileNotFoundException, UnsupportedEncodingException, JAXBException{
		OadrRegisterReportType oadrRegisterReportType = loadOadrRegisterHistoryReportType();
		System.out.println("Done");
	}
	
}