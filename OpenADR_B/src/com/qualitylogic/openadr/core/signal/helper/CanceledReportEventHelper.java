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

import com.qualitylogic.openadr.core.base.ErrorConst;
import com.qualitylogic.openadr.core.bean.CreatedEventBean;
import com.qualitylogic.openadr.core.signal.EiCreatedEvent;
import com.qualitylogic.openadr.core.signal.EiResponseType;
import com.qualitylogic.openadr.core.signal.EiTargetType;
import com.qualitylogic.openadr.core.signal.OadrCancelReportType;
import com.qualitylogic.openadr.core.signal.OadrCanceledReportType;
import com.qualitylogic.openadr.core.signal.OadrCreateOptType;
import com.qualitylogic.openadr.core.signal.OadrCreateReportType;
import com.qualitylogic.openadr.core.signal.OadrCreatedOptType;
import com.qualitylogic.openadr.core.signal.OadrCreatedPartyRegistrationType;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType;
import com.qualitylogic.openadr.core.signal.OadrPayload;
import com.qualitylogic.openadr.core.signal.OadrRegisterReportType;
import com.qualitylogic.openadr.core.signal.OadrRegisteredReportType;
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

public class CanceledReportEventHelper {

	@SuppressWarnings("unchecked")
	public static OadrCanceledReportType loadCanceledReportTypeFromString(String data) {
		OadrCanceledReportType oadrCanceledReportType = null;
		if (data == null || data.length() < 1)
			return null;

		try {

			JAXBContext testcontext = JAXBContext
					.newInstance("com.qualitylogic.openadr.core.signal");
			InputStream is = new ByteArrayInputStream(data.getBytes("UTF-8"));
			Unmarshaller unmarshall = testcontext.createUnmarshaller();

			//oadrCanceledReportType = (OadrCanceledReportType)((JAXBElement<Object>)unmarshall.unmarshal(is)).getValue();
			OadrSignedObject oadrSignedObject = ((OadrPayload)unmarshall.unmarshal(is)).getOadrSignedObject();
			oadrCanceledReportType = oadrSignedObject.getOadrCanceledReport();

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return oadrCanceledReportType;
	}

	public static OadrCanceledReportType loadOadrCanceledReportType(String fileName)
			throws JAXBException, FileNotFoundException,
			UnsupportedEncodingException {

		OadrCanceledReportType oadrCanceledReportType = ((OadrCanceledReportType) new SchemaHelper()
				.loadTestDataXMLFile(fileName).getOadrCanceledReport());
		oadrCanceledReportType.setVenID(new PropertiesFileReader().getVenID());
		oadrCanceledReportType.getOadrPendingReports().getReportRequestID().clear();
		return oadrCanceledReportType;
	}

	public static OadrCanceledReportType loadOadrCanceledReportType()
			throws JAXBException, FileNotFoundException,
			UnsupportedEncodingException {

		String fileName = "oadrCanceledReport.xml";
		
		OadrCanceledReportType oadrCanceledReportType = ((OadrCanceledReportType) new SchemaHelper()
				.loadTestDataXMLFile(fileName).getOadrCanceledReport());
		oadrCanceledReportType.setVenID(new PropertiesFileReader().getVenID());

		return oadrCanceledReportType;
	}
	
	public static OadrCanceledReportType loadOadrCanceledReportType(OadrCancelReportType oadrCancelReportType)
			throws JAXBException, FileNotFoundException,
			UnsupportedEncodingException {

		String reqID = oadrCancelReportType.getRequestID();
		
		OadrCanceledReportType  oadrCanceledReportType  = loadOadrCanceledReportType();
		EiResponseType eiResponse = oadrCanceledReportType.getEiResponse();
		eiResponse.setResponseCode(ErrorConst.OK_200);
		eiResponse.setResponseDescription("OK");
		eiResponse.setRequestID(reqID);
		return oadrCanceledReportType;
	}	
}