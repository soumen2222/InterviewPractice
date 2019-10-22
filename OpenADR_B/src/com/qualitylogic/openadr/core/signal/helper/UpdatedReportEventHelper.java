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

import com.qualitylogic.openadr.core.action.IResponseCreateReportTypeAckAction;
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

public class UpdatedReportEventHelper {

	@SuppressWarnings("unchecked")
	public static OadrUpdatedReportType loadReportFromString(String data) {
		OadrUpdatedReportType oadrUpdatedReportType = null;
		if (data == null || data.length() < 1)
			return null;

		try {

			JAXBContext testcontext = JAXBContext
					.newInstance("com.qualitylogic.openadr.core.signal");
			InputStream is = new ByteArrayInputStream(data.getBytes("UTF-8"));
			Unmarshaller unmarshall = testcontext.createUnmarshaller();

			//oadrUpdatedReportType = (OadrUpdatedReportType)((JAXBElement<Object>)unmarshall.unmarshal(is)).getValue();
			OadrSignedObject oadrSignedObject = ((OadrPayload)unmarshall.unmarshal(is)).getOadrSignedObject();
			oadrUpdatedReportType = oadrSignedObject.getOadrUpdatedReport();

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return oadrUpdatedReportType;
	}

	public static OadrUpdatedReportType loadOadrUpdatedReportType(String fileName)
			throws JAXBException, FileNotFoundException,
			UnsupportedEncodingException {

		OadrUpdatedReportType oadrUpdatedReportType = ((OadrUpdatedReportType) new SchemaHelper()
				.loadTestDataXMLFile(fileName).getOadrUpdatedReport());
		oadrUpdatedReportType.setOadrCancelReport(null);
		oadrUpdatedReportType.setVenID(new PropertiesFileReader().getVenID());

		
		return oadrUpdatedReportType;
	}

	public static OadrUpdatedReportType loadOadrUpdatedReportType()
			throws JAXBException, FileNotFoundException,
			UnsupportedEncodingException {
		OadrUpdatedReportType  oadrUpdatedReportType = loadOadrUpdatedReportType("oadrUpdatedReport.xml");
		oadrUpdatedReportType.setVenID(new PropertiesFileReader().getVenID());

		return oadrUpdatedReportType;
	}

	public static OadrUpdatedReportType loadOadrUpdatedReportType(OadrUpdateReportType updateReportType)
			throws JAXBException, FileNotFoundException,
			UnsupportedEncodingException {
		String requestID = updateReportType.getRequestID();
		OadrUpdatedReportType  oadrUpdatedReportType = loadOadrUpdatedReportType();
		oadrUpdatedReportType.getEiResponse().setRequestID(requestID);
		return oadrUpdatedReportType;
	}

	public static OadrUpdatedReportType loadOadrUpdatedReportWithCancelReport(ServiceType serviceType,IResponseCreateReportTypeAckAction responseCreateReportTypeAckAction)
			throws JAXBException, FileNotFoundException,
			UnsupportedEncodingException {
		OadrUpdatedReportType  oadrUpdatedReportType = loadOadrUpdatedReportType();
		
		OadrCancelReportType  oadrCancelReportType  = CancelReportEventHelper.loadOadrCancelReportType(responseCreateReportTypeAckAction.getOadrCreateReportResponse());
		oadrUpdatedReportType.setOadrCancelReport(oadrCancelReportType);
		return oadrUpdatedReportType;
	}

	public static OadrUpdatedReportType loadOadrUpdatedReportWithCancelReport(ServiceType serviceType,OadrCreateReportType oadrCreateReportType)
			throws JAXBException, FileNotFoundException,
			UnsupportedEncodingException {
		OadrUpdatedReportType  oadrUpdatedReportType = loadOadrUpdatedReportType();
		oadrUpdatedReportType.getEiResponse().setRequestID(OadrUtil.createUniqueCancelReportRequestID());
		
		OadrCancelReportType  oadrCancelReportType  = CancelReportEventHelper.loadOadrCancelReportType(oadrCreateReportType);
		oadrUpdatedReportType.setOadrCancelReport(oadrCancelReportType);
		return oadrUpdatedReportType;
	}
}