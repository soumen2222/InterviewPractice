package com.qualitylogic.openadr.core.signal.helper;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.w3c.dom.Node;

import com.qualitylogic.openadr.core.bean.CreatedEventBean;
import com.qualitylogic.openadr.core.bean.ServiceType;
import com.qualitylogic.openadr.core.signal.EiCreatedEvent;
import com.qualitylogic.openadr.core.signal.EiResponseType;
import com.qualitylogic.openadr.core.signal.EiTargetType;
import com.qualitylogic.openadr.core.signal.OadrCancelReportType;
import com.qualitylogic.openadr.core.signal.OadrCreateOptType;
import com.qualitylogic.openadr.core.signal.OadrCreateReportType;
import com.qualitylogic.openadr.core.signal.OadrCreatedOptType;
import com.qualitylogic.openadr.core.signal.OadrCreatedPartyRegistrationType;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType;
import com.qualitylogic.openadr.core.signal.OadrPayload;
import com.qualitylogic.openadr.core.signal.OadrRegisterReportType;
import com.qualitylogic.openadr.core.signal.OadrRegisteredReportType;
import com.qualitylogic.openadr.core.signal.OadrReportRequestType;
import com.qualitylogic.openadr.core.signal.OadrResponseType;
import com.qualitylogic.openadr.core.signal.OadrSignedObject;
import com.qualitylogic.openadr.core.signal.OadrUpdateReportType;
import com.qualitylogic.openadr.core.signal.OadrUpdatedReportType;
import com.qualitylogic.openadr.core.signal.OptTypeType;
import com.qualitylogic.openadr.core.signal.QualifiedEventIDType;
import com.qualitylogic.openadr.core.signal.EventResponses.EventResponse;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType.OadrEvent;
import com.qualitylogic.openadr.core.util.OadrUtil;
import com.qualitylogic.openadr.core.util.PropertiesFileReader;
import com.qualitylogic.openadr.core.util.XMLDBUtil;

public class CancelReportEventHelper {

	public static OadrCancelReportType createOadrCancelReportTypeFromString(String data) {
		OadrCancelReportType oadrCancelReportType = null;
		if (data == null || data.length() < 1)
			return null;

		try {

			JAXBContext testcontext = JAXBContext
					.newInstance("com.qualitylogic.openadr.core.signal");
			InputStream is = new ByteArrayInputStream(data.getBytes("UTF-8"));
			Unmarshaller unmarshall = testcontext.createUnmarshaller();

			//oadrCancelReportType = (OadrCancelReportType)((JAXBElement<Object>)unmarshall.unmarshal(is)).getValue();
			OadrSignedObject oadrSignedObject = ((OadrPayload)unmarshall.unmarshal(is)).getOadrSignedObject();
			oadrCancelReportType = oadrSignedObject.getOadrCancelReport();

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return oadrCancelReportType;
	}

	public static OadrCancelReportType loadOadrCancelReportType(String fileName)
			throws JAXBException, FileNotFoundException,
			UnsupportedEncodingException {

		OadrCancelReportType oadrCancelReportType = ((OadrCancelReportType) new SchemaHelper()
				.loadTestDataXMLFile(fileName).getOadrCancelReport());

		return oadrCancelReportType;
	}

	public static OadrCancelReportType loadOadrCancelReportType(OadrCreateReportType createReport)
			throws JAXBException, FileNotFoundException,
			UnsupportedEncodingException {
		String fileName ="oadrCancelReport.xml";
		PropertiesFileReader properties = new PropertiesFileReader();

		OadrCancelReportType oadrCancelReportType = ((OadrCancelReportType) new SchemaHelper()
				.loadTestDataXMLFile(fileName).getOadrCancelReport());

		oadrCancelReportType.setRequestID(OadrUtil.createUniqueCancelReportRequestID());
		oadrCancelReportType.getReportRequestID().clear();
		
		oadrCancelReportType.setVenID(properties.getVenID());

		List<OadrReportRequestType>  oadrReportRequestList = createReport.getOadrReportRequest();
		
		for(OadrReportRequestType eachReportRequest:oadrReportRequestList){
			oadrCancelReportType.getReportRequestID().add(eachReportRequest.getReportRequestID());
		}
		
		
		return oadrCancelReportType;
	}

}