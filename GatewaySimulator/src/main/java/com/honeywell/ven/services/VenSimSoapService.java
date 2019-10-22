package com.honeywell.ven.services;

import java.math.BigDecimal;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.Date;

import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebService;

import com.honeywell.payloads.PojoGenerator;
import com.honeywell.payloads.util.PayloadUtil;
import com.honeywell.pull.ven.VenPullReqResManager;
import com.honeywell.ven.api.common.PushProfile;
import com.honeywell.ven.api.common.Response;
import com.honeywell.ven.api.fault.VenException;
import com.honeywell.ven.api.opt.ServiceArea;
import com.honeywell.ven.api.opt.ServiceDeliveryPoint;
import com.honeywell.ven.api.opt.ServiceLocation;
import com.honeywell.ven.api.opt.TransportInterface;
import com.honeywell.ven.api.poll.Poll;
import com.honeywell.ven.api.poll.PollRequest;
import com.honeywell.ven.api.registration.CancelPartyRegistration;
import com.honeywell.ven.api.registration.CreatePartyRegistration;
import com.honeywell.ven.api.registration.CreatePartyRegistrationRequest;
import com.honeywell.ven.api.registration.CreatedPartyRegistration;
import com.honeywell.ven.api.registration.ProfileName;
import com.honeywell.ven.api.registration.TransportName;
import com.honeywell.ven.api.report.PowerReal;
import com.honeywell.ven.api.report.RegisterReport;
import com.honeywell.ven.api.report.RegisterReportRequest;
import com.honeywell.ven.api.report.RegisteredReport;
import com.honeywell.ven.api.report.RegisteredReportRequest;
import com.honeywell.ven.api.report.Report;
import com.honeywell.ven.api.report.ReportDataSource;
import com.honeywell.ven.api.report.ReportDescription;
import com.honeywell.ven.api.report.ReportInterval;
import com.honeywell.ven.api.report.UpdateReportRequest;
import com.honeywell.ven.api.report.UpdatedReport;
import com.honeywell.ven.entities.event.VenEvent;
import com.honeywell.ven.entities.registration.Registration;
import com.honeywell.ven.services.messages.RegisterGatewayResponse;
import com.honeywell.ven.services.messages.RegisterMultipleGatewayRequest;
import com.honeywell.ven.services.messages.RegisterMultipleGatewayResponse;
import com.honeywell.ven.services.messages.TotalVenDataResponse;
import com.honeywell.ven.services.messages.VenData;
import com.honeywell.account.creater.AccountCreaterClientQueue;
import com.honeywell.account.creater.AccountManager;
import com.honeywell.account.creater.DrasEndPoints;
import com.honeywell.account.creater.ProgramPartyDTO;
import com.honeywell.dras.scapi.messages.group.GroupServiceException;
import com.honeywell.dras.scapi.messages.program.ProgramServiceException;
import com.honeywell.dras.vtn.api.CommsStatusType;
import com.honeywell.openadr.core.signal.OadrPayload;
import com.honeywell.openadr.core.signal.OadrRegisterReportType;
import com.honeywell.pull.ven.VenManager;


@WebService(targetNamespace = "http://services.ven.honeywell.com/", endpointInterface = "com.honeywell.ven.services.VenSimService", portName = "VenSimSoapServicePort", serviceName = "VenSimService")

public class VenSimSoapService implements VenSimService {

	private static final String REQUEST_ID_PREFIX = "ReqID";
	private static final String Account_ID_PREFIX ="AccID";

	/**
	 * @param args
	 * @return 
	 * @throws VenException 
	 */
	@EJB
	private VenManager.L VenManager;
	
	@EJB
	private VenPullReqResManager.L venPullReqResManager;
	
	@EJB
	private AccountManager.L  accountManager;
	
	@EJB
	AccountCreaterClientQueue.L accountQueue;

	//register Party in DRAS (VTN) 
	//Sequence of operation are : 
	//1. Post OadrCreatePartyRegistrationType --OadrCreatedPartyRegistrationType Response Received(Save VenID and VTN ID)
	//2. Post OadrPollType -- OadrRegisterReportType Response Received. ( Save RequestID)
	//3. Post OadrRegisteredReportType --- OadrResponseType Response Received.
	//4. Post OadrRegisterReportType -- OadrRegisteredReportType Response Received.
	
   

	@WebMethod(operationName = "registerGateway")
	public RegisterGatewayResponse registerGateway(CreatePartyRegistrationRequest createPartyRegistrationRequest ) throws VenException{
		
		//Sending Create party Registration
		CreatedPartyRegistration createdPartyRegistration = null;
		String baseurl = createPartyRegistrationRequest.getPushProfile().getPushUrl();
		createdPartyRegistration = createParty(createPartyRegistrationRequest, baseurl);
	
		//Sending Poll
		
		RegisterReport registerReport =null;
		registerReport= sendpoll(createPartyRegistrationRequest, createdPartyRegistration, baseurl);
				
			
		//Sending OadrRegisteredReportType
		
		Response regreportresp1 = null;
		regreportresp1 = sendregisteredReport(createPartyRegistrationRequest, registerReport, baseurl);
			
				
		//Sending OadrRegisterReportType 
		//Load Data for METADATA_TELEMETRY_USAGE and METADATA_TELEMETRY_STATUS
		RegisteredReport registeredReportresponse = null;		
		registeredReportresponse = sendregisterreport(createPartyRegistrationRequest, regreportresp1, createdPartyRegistration, baseurl);		
				
		
		
		if (registeredReportresponse!=null){
			
		
		// Save the gateway registration in database- registration entity.
		Registration Registrationdetail = new Registration() ;
		Registrationdetail.setVenid(createdPartyRegistration.getVenId());
		Registrationdetail.setVtnid(createdPartyRegistration.getVtnId());
		Registrationdetail.setVenName(createPartyRegistrationRequest.getCreatePartyRegistration().getVenName());
		Registrationdetail.setVtnurl(baseurl);
		Registrationdetail.setVenComStatus((CommsStatusType.valueOf("OFFLINE")).toString());
		VenManager.createRegistration(Registrationdetail);
		
		
		DrasEndPoints.setDnsname(createPartyRegistrationRequest.getPushProfile().getServerUrl());		
		
		// create customer and resource and associate the Ven to the resource
		
		ProgramPartyDTO createPartyRequest = new ProgramPartyDTO();
		String accountNumber =Account_ID_PREFIX + UUID.randomUUID().toString();		
		createPartyRequest .setAccountNumber(accountNumber);
		createPartyRequest.setName(createPartyRegistrationRequest.getCreatePartyRegistration().getVenName());
		String authUsername = createPartyRegistrationRequest.getCreatePartyRegistration().getVenName() +"_log@gmail.com";
		createPartyRequest.setAuthUsername(authUsername);
		String utilityName = "SIM";
		createPartyRequest.setUtilityName(utilityName );
		
		accountQueue.sendMessage(createPartyRequest);
	/*	String accresp= null;
		try {
			accresp = accountManager.createCustomerInformation(createPartyRequest);
		} catch (GroupServiceException | ProgramServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		if (accresp!=null){
		RegisterGatewayResponse registerGatewayResponse = new RegisterGatewayResponse();
		registerGatewayResponse.setRegistrationdetail(Registrationdetail);
		return registerGatewayResponse;
		}
	return null;*/
		RegisterGatewayResponse registerGatewayResponse = new RegisterGatewayResponse();
		registerGatewayResponse.setRegistrationdetail(Registrationdetail);
		return registerGatewayResponse;
	}
	return null;
		
}	
	@WebMethod
	public UpdatedReport sendReportData( UpdateReportRequest updateReportRequest ,String baseurl) throws VenException {
		
		UpdatedReport updatedreportresponse = null;
		
		if (updateReportRequest!= null)
		{
			updateReportRequest.getPushProfile().setPushUrl(baseurl);
			try {
				updatedreportresponse = venPullReqResManager.updateReport(updateReportRequest);
			} catch (VenException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return updatedreportresponse;
		}
		return null;
	}
	

	
	
	private CreatedPartyRegistration createParty (CreatePartyRegistrationRequest createPartyRegistrationRequest , String baseurl) throws VenException{
		
		CreatedPartyRegistration createdPartyRegistration = null;
		
		if (createPartyRegistrationRequest!=null ){
		try {
			createPartyRegistrationRequest.getPushProfile().setPushUrl(baseurl);
			createdPartyRegistration = venPullReqResManager.sendCreatePartyRegistration(createPartyRegistrationRequest);
		} catch (VenException e) {
			throw new VenException("Error in Customer end point creation ",e);
		}
		}else{
			throw new VenException("Custmomer Request is Empty ");
		}
		return createdPartyRegistration;
		
	}
	
	 private RegisterReport sendpoll(CreatePartyRegistrationRequest createPartyRegistrationRequest , CreatedPartyRegistration createdPartyRegistration , String baseurl) throws VenException{
		 
		   PollRequest pollRequest = new PollRequest();
			Poll poll = new Poll();
			createPartyRegistrationRequest.getPushProfile().setPushUrl(baseurl);
			pollRequest.setPushProfile(createPartyRegistrationRequest.getPushProfile());
			poll.setVenId(createdPartyRegistration.getVenId());
			pollRequest.setPoll(poll );
			Object response = null;
			if (pollRequest!=null ){
			response  = venPullReqResManager.sendpoll(pollRequest);
			
			if (!PayloadUtil.isExpected(response, OadrRegisterReportType.class))
	   	{
			throw new VenException("Expected class has not been received");
		    }
			}
			
			
			//Need to Refactor
			PojoGenerator pojoGenerator = new PojoGenerator();
			RegisterReport registerReport = new RegisterReport();
			OadrPayload oadrResponsePayload = (OadrPayload)response;			
			registerReport = pojoGenerator.getRegisterReport((OadrRegisterReportType)oadrResponsePayload.getOadrSignedObject().getOadrRegisterReport());
			
			return registerReport;
	   }
	 
	 private Response sendregisteredReport(CreatePartyRegistrationRequest createPartyRegistrationRequest ,RegisterReport registerReport , String baseurl) throws VenException{
		  
		   RegisteredReportRequest registeredReportRequest = new RegisteredReportRequest();
			createPartyRegistrationRequest.getPushProfile().setPushUrl(baseurl);
			registeredReportRequest.setPushProfile(createPartyRegistrationRequest.getPushProfile());
			RegisteredReport registeredReport = new RegisteredReport();
			registeredReport.setVenId(registerReport.getVenId());
			
			Response eiresponse = new Response();
			eiresponse.setRequestId(registerReport.getRequestId());
			eiresponse.setResponseCode("200");
			eiresponse.setResponseDescription("OK");
			registeredReport.setResponse(eiresponse );
			registeredReportRequest.setRegisteredReport(registeredReport );		
			Response regreportresp1 = venPullReqResManager.sendregisteredReport(registeredReportRequest );
			return regreportresp1;
	   }
	 
	 private RegisteredReport sendregisterreport(CreatePartyRegistrationRequest createPartyRegistrationRequest , Response regreportresp1 , CreatedPartyRegistration createdPartyRegistration, String baseurl) throws VenException{
		   
		   if (regreportresp1!=null){
				
			   
				RegisterReportRequest registerReportRequest = new RegisterReportRequest();
				
				//Set Base URL
				createPartyRegistrationRequest.getPushProfile().setPushUrl(baseurl);
				registerReportRequest.setPushProfile(createPartyRegistrationRequest.getPushProfile());
				
				// Set Report Contents
				
				RegisterReport loadregisterReport = new RegisterReport();
				loadregisterReport.setVenId(createdPartyRegistration.getVenId());		
				String regId = REQUEST_ID_PREFIX + UUID.randomUUID().toString();
				loadregisterReport.setRequestId(regId);
				
				List<Report>  ReportList = new ArrayList<Report>();
				
				//// Load Metadata for METADATA_TELEMETRY_USAGE
					Report reportcontent1 = new Report();
					String rid = "MeterData:"+createPartyRegistrationRequest.getCreatePartyRegistration().getVenName();
					String resourceid = createPartyRegistrationRequest.getCreatePartyRegistration().getVenName();
					
					List<ReportInterval> intervalList = new ArrayList<ReportInterval>();			
					reportcontent1.setIntervalList(intervalList );
					reportcontent1.setReportRequestId("0");
					reportcontent1.setReportSpecifierId("RS_44444");
					reportcontent1.setReportName("METADATA_TELEMETRY_USAGE");
					reportcontent1.setCreatedDate(new Date());
					reportcontent1.setDuration(86400000L);
					List<ReportDescription> reportDescriptionList = new ArrayList<ReportDescription>();
					ReportDescription reportDescription = new ReportDescription();
					reportDescription.setrId(rid);
					reportDescription.setReportType("usage");
					ReportDataSource reportDataSource = new ReportDataSource();
					List<String> resourceIdList = new ArrayList<String>();
					List<String> aggregatedPnodeList = new ArrayList<String>();
					List<String> endDeviceAssetList = new ArrayList<String>();
					List<String> groupIdList = new ArrayList<String>();
					List<String> groupNameList = new ArrayList<String>();
					List<String> meterAssetList = new ArrayList<String>();
					List<String> partyIdList = new ArrayList<String>();
					List<String> pnodeList = new ArrayList<String>();
					List<ServiceArea> serviceAreaList = new ArrayList<ServiceArea>();
					List<ServiceDeliveryPoint> serviceDeliveryPointList = new ArrayList<ServiceDeliveryPoint>();
					List<ServiceLocation> serviceLocationList = new ArrayList<ServiceLocation>();
					List<TransportInterface> transportInterfaceList = new ArrayList<TransportInterface>();
					List<String> venIdList = new ArrayList<String>();
					reportDataSource.setAggregatedPnodeList(aggregatedPnodeList);
					reportDataSource.setEndDeviceAssetList(endDeviceAssetList);
					reportDataSource.setGroupIdList(groupIdList);
					reportDataSource.setGroupNameList(groupNameList);
					reportDataSource.setMeterAssetList(meterAssetList);
					reportDataSource.setPartyIdList(partyIdList);
					reportDataSource.setPnodeList(pnodeList);
					reportDataSource.setServiceAreaList(serviceAreaList);
					reportDataSource.setServiceDeliveryPointList(serviceDeliveryPointList);
					reportDataSource.setServiceLocationList(serviceLocationList);
					reportDataSource.setTransportInterfaceList(transportInterfaceList);
					reportDataSource.setVenIdList(venIdList);
					
					
					resourceIdList.add(resourceid);
					reportDataSource.setResourceIdList(resourceIdList );
					reportDescription.setReportDataSource(reportDataSource );
					reportDescription.setReadingType("Direct Read");
					reportDescription.setMarketContext("*");
					reportDescription.setSamplingMaxPeriod(300000L);
					reportDescription.setSamplingMinPeriod(300000L);
					PowerReal itemBase = new PowerReal() ;
					itemBase.setItemDescription("RealPower");
					itemBase.setItemUnits("w");
					itemBase.setSiScaleCode("k");
					itemBase.setAc(true);
					BigDecimal hertz = new BigDecimal("50.0");
					BigDecimal volts = new BigDecimal("250.0");
					itemBase.setHertz(hertz);
					itemBase.setVoltage(volts);
					
					
					reportDescription.setItemBase(itemBase );
					reportDescriptionList.add(reportDescription);
					reportcontent1.setReportDescriptionList(reportDescriptionList);
					
					
		   //// Load meta Data for METADATA_TELEMETRY_STATUS
					Report reportcontent2 = new Report();
					reportcontent2.setIntervalList(intervalList );
					reportcontent2.setReportRequestId("0");
					reportcontent2.setReportSpecifierId("RS_44445");
					reportcontent2.setReportName("METADATA_TELEMETRY_STATUS");
					reportcontent2.setCreatedDate(new Date());
					reportcontent2.setDuration(86400000L);
					List<ReportDescription> reportDescriptionList1 = new ArrayList<ReportDescription>();
					ReportDescription reportDescription1 = new ReportDescription();
					reportDescription1.setrId(rid);
					reportDescription1.setReportType("x-resourceStatus");
					ReportDataSource reportDataSource1 = new ReportDataSource();
					List<String> resourceIdList1 = new ArrayList<String>();
					resourceIdList1.add(resourceid);
					reportDataSource1.setResourceIdList(resourceIdList1);
					
					reportDataSource1.setAggregatedPnodeList(aggregatedPnodeList);
					reportDataSource1.setEndDeviceAssetList(endDeviceAssetList);
					reportDataSource1.setGroupIdList(groupIdList);
					reportDataSource1.setGroupNameList(groupNameList);
					reportDataSource1.setMeterAssetList(meterAssetList);
					reportDataSource1.setPartyIdList(partyIdList);
					reportDataSource1.setPnodeList(pnodeList);
					reportDataSource1.setServiceAreaList(serviceAreaList);
					reportDataSource1.setServiceDeliveryPointList(serviceDeliveryPointList);
					reportDataSource1.setServiceLocationList(serviceLocationList);
					reportDataSource1.setTransportInterfaceList(transportInterfaceList);
					reportDataSource1.setVenIdList(venIdList);
					
					reportDescription1.setReportDataSource(reportDataSource1 );
					reportDescription1.setReadingType("x-notApplicable");
					reportDescription1.setMarketContext("*");
					reportDescription1.setSamplingMaxPeriod(300000L);
					reportDescription1.setSamplingMinPeriod(300000L);
					reportDescriptionList1.add(reportDescription1);
					reportcontent2.setReportDescriptionList(reportDescriptionList1);			 
					ReportList.add(reportcontent1);
					ReportList.add(reportcontent2);						
					loadregisterReport.setReportList(ReportList);			
				    registerReportRequest.setRegisterReport(loadregisterReport);		
				    RegisteredReport regreportresp2 = venPullReqResManager.sendregisterReport(registerReportRequest);
				    return regreportresp2;
				}
		return null;
		   
	   }


	@Override
	@WebMethod(operationName = "cancelPartyRegistration")
	public void cancelPartyRegistration(
			CancelPartyRegistration cancelPartyRegistrationRequest)
			throws VenException {
		// TODO Auto-generated method stub
		
	}
	
	
	@Override
	@WebMethod
	public TotalVenDataResponse getAllVendata() throws VenException {
			
		TotalVenDataResponse totalVenDataResponse = new TotalVenDataResponse();
		totalVenDataResponse=VenManager.getallVendetails();
		return totalVenDataResponse;
		
	}
	@Override
	public RegisterMultipleGatewayResponse createMultipleVens(
			RegisterMultipleGatewayRequest registerMultipleGatewayRequest)
			throws VenException {
		// TODO Auto-generated method stub
		
		int numofvens = registerMultipleGatewayRequest.getNumofgateways();
		RegisterMultipleGatewayResponse registerMultipleGatewayResponse = new RegisterMultipleGatewayResponse();
		List<String> registeredGateway = new ArrayList<String>();
		String VenName = registerMultipleGatewayRequest.getCreatePartyRegistrationRequest().getCreatePartyRegistration().getVenName();
		String baseurl = registerMultipleGatewayRequest.getCreatePartyRegistrationRequest().getPushProfile().getPushUrl();
		while(numofvens>0){
			
			String ModifiedVenname = VenName + numofvens +System.currentTimeMillis();
			registerMultipleGatewayRequest.getCreatePartyRegistrationRequest().getCreatePartyRegistration().setVenName(ModifiedVenname);
			registerMultipleGatewayRequest.getCreatePartyRegistrationRequest().getPushProfile().setPushUrl(baseurl);
			RegisterGatewayResponse registerGatewayResponse = registerGateway(registerMultipleGatewayRequest.getCreatePartyRegistrationRequest());
			registeredGateway.add(registerGatewayResponse.getRegistrationdetail().getVenid());
			numofvens--;
		}
		
		registerMultipleGatewayResponse.setRegisteredGateway(registeredGateway);		
		return registerMultipleGatewayResponse;
	}
	
}
	
	


