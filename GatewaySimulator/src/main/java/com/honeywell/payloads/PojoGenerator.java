package com.honeywell.payloads;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.JAXBElement;
import javax.xml.datatype.XMLGregorianCalendar;

import org.jboss.logging.Logger;



import com.honeywell.openadr.core.signal.AggregatedPnodeType;
import com.honeywell.openadr.core.signal.EiEventSignalType;
import com.honeywell.openadr.core.signal.EiResponseType;
import com.honeywell.openadr.core.signal.EiTargetType;
import com.honeywell.openadr.core.signal.EndDeviceAssetType;
import com.honeywell.openadr.core.signal.FeedType;
import com.honeywell.openadr.core.signal.IntervalType;
import com.honeywell.openadr.core.signal.Intervals;
import com.honeywell.openadr.core.signal.ItemBaseType;
import com.honeywell.openadr.core.signal.MeterAssetType;
import com.honeywell.openadr.core.signal.OadrCancelOptType;
import com.honeywell.openadr.core.signal.OadrCancelPartyRegistrationType;
import com.honeywell.openadr.core.signal.OadrCancelReportType;
import com.honeywell.openadr.core.signal.OadrCanceledOptType;
import com.honeywell.openadr.core.signal.OadrCanceledPartyRegistrationType;
import com.honeywell.openadr.core.signal.OadrCanceledReportType;
import com.honeywell.openadr.core.signal.OadrCreateOptType;
import com.honeywell.openadr.core.signal.OadrCreatePartyRegistrationType;
import com.honeywell.openadr.core.signal.OadrCreateReportType;
import com.honeywell.openadr.core.signal.OadrCreatedEventType;
import com.honeywell.openadr.core.signal.OadrCreatedOptType;
import com.honeywell.openadr.core.signal.OadrCreatedPartyRegistrationType;
import com.honeywell.openadr.core.signal.OadrCreatedReportType;
import com.honeywell.openadr.core.signal.OadrDistributeEventType;
import com.honeywell.openadr.core.signal.OadrDistributeEventType.OadrEvent;
import com.honeywell.openadr.core.signal.OadrGBStreamPayloadBase;
import com.honeywell.openadr.core.signal.OadrLoadControlStateType;
import com.honeywell.openadr.core.signal.OadrLoadControlStateTypeType;
import com.honeywell.openadr.core.signal.OadrPayloadResourceStatusType;
import com.honeywell.openadr.core.signal.OadrPendingReportsType;
import com.honeywell.openadr.core.signal.OadrPollType;
import com.honeywell.openadr.core.signal.OadrProfiles.OadrProfile;
import com.honeywell.openadr.core.signal.OadrQueryRegistrationType;
import com.honeywell.openadr.core.signal.OadrRegisterReportType;
import com.honeywell.openadr.core.signal.OadrRegisteredReportType;
import com.honeywell.openadr.core.signal.OadrReportDescriptionType;
import com.honeywell.openadr.core.signal.OadrReportRequestType;
import com.honeywell.openadr.core.signal.OadrReportType;
import com.honeywell.openadr.core.signal.OadrRequestEventType;
import com.honeywell.openadr.core.signal.OadrResponseType;
import com.honeywell.openadr.core.signal.OadrSamplingRateType;
import com.honeywell.openadr.core.signal.OadrTransports;
import com.honeywell.openadr.core.signal.OadrTransports.OadrTransport;
import com.honeywell.openadr.core.signal.OadrUpdateReportType;
import com.honeywell.openadr.core.signal.OadrUpdatedReportType;
import com.honeywell.openadr.core.signal.PayloadBaseType;
import com.honeywell.openadr.core.signal.PayloadFloatType;
import com.honeywell.openadr.core.signal.PnodeType;
import com.honeywell.openadr.core.signal.QualifiedEventIDType;
import com.honeywell.openadr.core.signal.ReportPayloadType;
import com.honeywell.openadr.core.signal.ReportSpecifierType;
import com.honeywell.openadr.core.signal.ServiceAreaType;
import com.honeywell.openadr.core.signal.ServiceDeliveryPointType;
import com.honeywell.openadr.core.signal.ServiceLocationType;
import com.honeywell.openadr.core.signal.SignalPayloadType;
import com.honeywell.openadr.core.signal.SpecifierPayloadType;
import com.honeywell.openadr.core.signal.StreamPayloadBaseType;
import com.honeywell.openadr.core.signal.TransportInterfaceType;
import com.honeywell.openadr.core.signal.xcal.ArrayOfVavailabilityContainedComponents;
import com.honeywell.openadr.core.signal.xcal.AvailableType;
import com.honeywell.openadr.core.signal.xcal.Dtstart;
import com.honeywell.openadr.core.signal.xcal.DurationPropType;
import com.honeywell.openadr.core.signal.xcal.Properties;
import com.honeywell.openadr.core.signal.xcal.Properties.Tolerance;
import com.honeywell.openadr.core.signal.xcal.Properties.Tolerance.Tolerate;
import com.honeywell.openadr.core.signal.xcal.Uid;
import com.honeywell.openadr.core.signal.xcal.VavailabilityType;
import com.honeywell.openadr.core.signal.xcal.WsCalendarIntervalType;
import com.honeywell.payloads.itembasegenerator.ItemBaseGenerator;
import com.honeywell.payloads.util.DurationUtil;
import com.honeywell.payloads.util.XMLGregorianCalendarConverter;
import com.honeywell.ven.api.EventStatus;
import com.honeywell.ven.api.OptType;
import com.honeywell.ven.api.common.EiTarget;
import com.honeywell.ven.api.common.Response;
import com.honeywell.ven.api.common.Target;
import com.honeywell.ven.api.common.WsCalanderInterval;
import com.honeywell.ven.api.event.CreatedEvent;
import com.honeywell.ven.api.event.DistributeEvent;
import com.honeywell.ven.api.event.Event;
import com.honeywell.ven.api.event.RequestEvent;
import com.honeywell.ven.api.event.Signal;
import com.honeywell.ven.api.opt.Availibility;
import com.honeywell.ven.api.opt.CancelOpt;
import com.honeywell.ven.api.opt.CanceledOpt;
import com.honeywell.ven.api.opt.CreateOpt;
import com.honeywell.ven.api.opt.CreatedOpt;
import com.honeywell.ven.api.opt.DeviceClass;
import com.honeywell.ven.api.opt.ServiceArea;
import com.honeywell.ven.api.opt.ServiceDeliveryPoint;
import com.honeywell.ven.api.opt.ServiceLocation;
import com.honeywell.ven.api.opt.TransportInterface;
import com.honeywell.ven.api.poll.Poll;
import com.honeywell.ven.api.registration.CancelPartyRegistration;
import com.honeywell.ven.api.registration.CanceledPartyRegistration;
import com.honeywell.ven.api.registration.CreatePartyRegistration;
import com.honeywell.ven.api.registration.CreatedPartyRegistration;
import com.honeywell.ven.api.registration.Profile;
import com.honeywell.ven.api.registration.ProfileName;
import com.honeywell.ven.api.registration.QueryRegistration;
import com.honeywell.ven.api.registration.TransportName;
import com.honeywell.ven.api.report.CancelReport;
import com.honeywell.ven.api.report.CanceledReport;
import com.honeywell.ven.api.report.Capacity;
import com.honeywell.ven.api.report.CreateReport;
import com.honeywell.ven.api.report.CreatedReport;
import com.honeywell.ven.api.report.ItemBase;
import com.honeywell.ven.api.report.LevelOffset;
import com.honeywell.ven.api.report.PayloadBase;
import com.honeywell.ven.api.report.PendingReports;
import com.honeywell.ven.api.report.PercentOffset;
import com.honeywell.ven.api.report.RegisterReport;
import com.honeywell.ven.api.report.RegisteredReport;
import com.honeywell.ven.api.report.Report;
import com.honeywell.ven.api.report.ReportDataSource;
import com.honeywell.ven.api.report.ReportDescription;
import com.honeywell.ven.api.report.ReportInterval;
import com.honeywell.ven.api.report.ReportRequest;
import com.honeywell.ven.api.report.ReportSpecifier;
import com.honeywell.ven.api.report.ReportSubject;
import com.honeywell.ven.api.report.ResourceStatus;
import com.honeywell.ven.api.report.SetPoint;
import com.honeywell.ven.api.report.SpecifierPayload;
import com.honeywell.ven.api.report.UpdateReport;
import com.honeywell.ven.api.report.UpdatedReport;
/**
 * Converts OpenADR types to API types
 * 
 * @author Joe
 * 
 */
public class PojoGenerator {
	
	public PojoGenerator(String certCommonName) {
		super();
		this.certCommonName = certCommonName;
	}

	public PojoGenerator() {
		// TODO Auto-generated constructor stub
	}

	Logger log = Logger.getLogger(PojoGenerator.class);
	
	private String certCommonName;
	
	///////////////////////////////////////
	//Response payloads
	///////////////////////////////////////

	public Response getResponse(EiResponseType eiResponseType){
		Response response = new Response();
		response.setRequestId(eiResponseType.getRequestID());
		response.setResponseCode(eiResponseType.getResponseCode());
		response.setResponseDescription(eiResponseType.getResponseDescription());
		response.setCertCommonName(this.certCommonName);
		return response;
	}
		
	
	public Response getResponse(OadrResponseType oadrResponseType){
		Response response = new Response();
		if(null != oadrResponseType){
			if(null != oadrResponseType.getEiResponse()){
				response.setRequestId(oadrResponseType.getEiResponse().getRequestID());
				response.setResponseCode(oadrResponseType.getEiResponse().getResponseCode());
				response.setResponseDescription(oadrResponseType.getEiResponse().getResponseDescription());
			}
			response.setVenId(oadrResponseType.getVenID());
			response.setSchemaVersion(oadrResponseType.getSchemaVersion());
			response.setCertCommonName(this.certCommonName);
		}
		return response;
	}

	
	///////////////////////////////////////
	//Report Service payloads
	///////////////////////////////////////
	
	public RegisterReport getRegisterReport(OadrRegisterReportType oadrRegisterReportType){
		RegisterReport registerReport = new RegisterReport();
		
		if(null != oadrRegisterReportType){
			registerReport.setRequestId(oadrRegisterReportType.getRequestID());
			registerReport.setSchemaVersion(oadrRegisterReportType.getSchemaVersion());
			registerReport.setVenId(oadrRegisterReportType.getVenID());
			registerReport.setReportList(getReports(oadrRegisterReportType.getOadrReport(),oadrRegisterReportType.getVenID()));
			registerReport.setCertCommonName(certCommonName);
		}
		return registerReport;
	}
	
	public RegisteredReport getRegisteredReport(OadrRegisteredReportType oadrRegisteredReportType){
		RegisteredReport registeredReport = new RegisteredReport();
		if(null != oadrRegisteredReportType){
			registeredReport.setReportRequestList(getReportRequests(oadrRegisteredReportType.getOadrReportRequest()));
			/* RequestId need not be filled for the response object
			 * if(null != oadrRegisteredReportType.getEiResponse()){
				registeredReport.setRequestId(oadrRegisteredReportType.getEiResponse().getRequestID());
			}*/
			registeredReport.setResponse(getResponse(oadrRegisteredReportType.getEiResponse()));
			registeredReport.setSchemaVersion(oadrRegisteredReportType.getSchemaVersion());
			registeredReport.setVenId(oadrRegisteredReportType.getVenID());
			if(null != oadrRegisteredReportType || null !=  oadrRegisteredReportType.getEiResponse()){
				registeredReport.setRequestId(oadrRegisteredReportType.getEiResponse().getRequestID());
			}
			registeredReport.setCertCommonName(certCommonName);
		}
		return registeredReport;
	}
	
	public CreateReport getCreateReport(OadrCreateReportType oadrCreateReportType){
		CreateReport createReport = new CreateReport();
		if(null != oadrCreateReportType){
			
			//TODO fingerPrint and CertCommonName are not available on the Oadr side
			createReport.setReportRequestList(getReportRequests(oadrCreateReportType.getOadrReportRequest()));
			createReport.setRequestId(oadrCreateReportType.getRequestID());
			createReport.setSchemaVersion(oadrCreateReportType.getSchemaVersion());
			createReport.setVenId(oadrCreateReportType.getVenID());
			createReport.setCertCommonName(certCommonName);
		}
		return createReport;
	}
	
	public CreatedReport getCreatedReport(OadrCreatedReportType oadrCreatedReportType){
		CreatedReport createdReport = new CreatedReport();
		if(null != oadrCreatedReportType){
			createdReport.setPendingReports(getPendingReports(oadrCreatedReportType.getOadrPendingReports()));
			/* RequestId need not be filled for the response object
			 * if(null != oadrCreatedReportType.getEiResponse()){
				createdReport.setRequestId(oadrCreatedReportType.getEiResponse().getRequestID());
			}*/
			createdReport.setResponse(getResponse(oadrCreatedReportType.getEiResponse()));
			createdReport.setSchemaVersion(oadrCreatedReportType.getSchemaVersion());
			createdReport.setVenId(oadrCreatedReportType.getVenID());
			if(null != oadrCreatedReportType.getEiResponse()){
				createdReport.setRequestId(oadrCreatedReportType.getEiResponse().getRequestID());
			}
			createdReport.setCertCommonName(certCommonName);
		}
		return createdReport;
	}
	
	public UpdateReport getUpdateReport(OadrUpdateReportType oadrUpdateReportType){
		UpdateReport updateReport = new UpdateReport();
		if(null != oadrUpdateReportType){
			updateReport.setReportList(getReports(oadrUpdateReportType.getOadrReport()));
			updateReport.setRequestId(oadrUpdateReportType.getRequestID());
			updateReport.setSchemaVersion(oadrUpdateReportType.getSchemaVersion());
			updateReport.setVenId(oadrUpdateReportType.getVenID());
			updateReport.setCertCommonName(certCommonName);
		}
		return updateReport;
	}
	
	public UpdatedReport getUpdatedReport(OadrUpdatedReportType oadrUpdatedReportType){
		UpdatedReport updatedReport = new UpdatedReport();
		if(null != oadrUpdatedReportType){
			updatedReport.setCancelReport(getCancelReport(oadrUpdatedReportType.getOadrCancelReport()));
			/* RequestId need not be filled for the response object
			 * if(null != oadrUpdatedReportType.getEiResponse()){
				updatedReport.setRequestId(oadrUpdatedReportType.getEiResponse().getRequestID());
			}*/
			updatedReport.setResponse(getResponse(oadrUpdatedReportType.getEiResponse()));
			updatedReport.setSchemaVersion(oadrUpdatedReportType.getSchemaVersion());
			updatedReport.setVenId(oadrUpdatedReportType.getVenID());
			updatedReport.setCertCommonName(certCommonName);
		}
		return updatedReport;
	}
	
	public CancelReport getCancelReport(OadrCancelReportType oadrCancelReportType){
		CancelReport cancelReport = new CancelReport();
		if(null != oadrCancelReportType){
			cancelReport.setReportRequestId(oadrCancelReportType.getReportRequestID());
			cancelReport.setRequestId(oadrCancelReportType.getRequestID());
			cancelReport.setSchemaVersion(oadrCancelReportType.getSchemaVersion());
			cancelReport.setVenId(oadrCancelReportType.getVenID());
			cancelReport.setReportToFollow(oadrCancelReportType.isReportToFollow());
			cancelReport.setCertCommonName(certCommonName);
		}
		return cancelReport;
	}
	
	public CanceledReport getCanceledReport(OadrCanceledReportType oadrCanceledReportType){
		CanceledReport canceledReport = new CanceledReport();
		if(null != oadrCanceledReportType){
			canceledReport.setPendingReports(getPendingReports(oadrCanceledReportType.getOadrPendingReports()));
			 //RequestId need not be filled for the response object
			if(null != oadrCanceledReportType.getEiResponse()){
				canceledReport.setRequestId(oadrCanceledReportType.getEiResponse().getRequestID());
			}
			canceledReport.setResponse(getResponse(oadrCanceledReportType.getEiResponse()));
			canceledReport.setSchemaVersion(oadrCanceledReportType.getSchemaVersion());
			canceledReport.setVenId(oadrCanceledReportType.getVenID());
			canceledReport.setCertCommonName(certCommonName);
		}
		return canceledReport;
	}

	
	private PendingReports getPendingReports(OadrPendingReportsType oadrPendingReportsType){
		PendingReports pendingReports = new PendingReports();
		pendingReports.setReportRequestIdList(oadrPendingReportsType.getReportRequestID());
		return pendingReports;
	}
	
	
	private List<ReportRequest> getReportRequests(List<OadrReportRequestType> oadrReportRequestTypes){
		List<ReportRequest> reportRequests = new ArrayList<ReportRequest>();
		if(null != oadrReportRequestTypes){
			for(OadrReportRequestType oadrReportRequestType : oadrReportRequestTypes){
				ReportRequest reportRequest = new ReportRequest();
				reportRequest.setReportRequestId(oadrReportRequestType.getReportRequestID());
				reportRequest.setReportSpecifier(getReportSpecifier(oadrReportRequestType.getReportSpecifier()));
				reportRequests.add(reportRequest);
			}
		}
		return reportRequests;
	}
	
	private ReportSpecifier getReportSpecifier(ReportSpecifierType reportSpecifierType){
		ReportSpecifier reportSpecifier = new ReportSpecifier();
		if(null != reportSpecifierType){
			reportSpecifier.setGranularity(getDuration(reportSpecifierType.getGranularity()));
			reportSpecifier.setReportBackDuration(getDuration(reportSpecifierType.getReportBackDuration()));
			reportSpecifier.setReportInterval(getCalanderInterval(reportSpecifierType.getReportInterval()));
			reportSpecifier.setReportSpecifierId(reportSpecifierType.getReportSpecifierID());
			reportSpecifier.setSpecifierPayloadList(getSpecifierPayloads(reportSpecifierType.getSpecifierPayload()));
		}
		return reportSpecifier;
	}
	
	private List<Report> getReports(List<OadrReportType> oadrReportTypes){
		return getReports(oadrReportTypes, null);
	}
	
	private List<Report> getReports(List<OadrReportType> oadrReportTypes,String venId){
		List<Report> reports = new ArrayList<Report>();
		if(null != oadrReportTypes){
			for(OadrReportType oadrReportType: oadrReportTypes){
				Report report = new Report();
				report.setCreatedDate(XMLGregorianCalendarConverter.asDate(oadrReportType.getCreatedDateTime()));
				if(null != oadrReportType.getDuration()){
					report.setDuration(getDuration(oadrReportType.getDuration()));
				}
				report.setIntervalList(getReportIntervals(oadrReportType.getIntervals()));
				report.setReportDescriptionList(getReportDescriptions(oadrReportType.getOadrReportDescription(),oadrReportType.getEiReportID(),venId));
				report.setReportId(oadrReportType.getEiReportID());
				report.setReportName(oadrReportType.getReportName());
				report.setReportRequestId(oadrReportType.getReportRequestID());
				report.setReportSpecifierId(oadrReportType.getReportSpecifierID());
				report.setStart(getDate(oadrReportType.getDtstart()));
				reports.add(report);
			}
		}
		return reports;
	}
	
	private List<SpecifierPayload> getSpecifierPayloads(List<SpecifierPayloadType> oadrSpecifierPayloads){
		List<SpecifierPayload> specifierPayloads = new ArrayList<SpecifierPayload>();
		for(SpecifierPayloadType oadrSpecifierPayloadType: oadrSpecifierPayloads){
			SpecifierPayload specifierPayload = new SpecifierPayload();
			specifierPayload.setItemBase(getItemBase(oadrSpecifierPayloadType.getItemBase()));
			specifierPayload.setReadingType(oadrSpecifierPayloadType.getReadingType());
			specifierPayload.setrId(oadrSpecifierPayloadType.getRID());
			specifierPayloads.add(specifierPayload);
		}
		return specifierPayloads;
	}
	
	private ItemBase getItemBase(JAXBElement<? extends ItemBaseType> jaxBitemBase){
		ItemBase itemBase = null;
		if(null != jaxBitemBase){
			ItemBaseType oadrItemBase = jaxBitemBase.getValue();
			ItemBaseGenerator itemBaseGenerator = new ItemBaseGenerator();
			try {
				itemBase = itemBaseGenerator.getItemBase(oadrItemBase, oadrItemBase.getClass().getSimpleName());
			} catch (ClassNotFoundException | InstantiationException
					| IllegalAccessException e) {
				log.error("Exception in getting ItemBase object !!!! ");
			}
		}
		return itemBase;
	}
	
	private List<ReportInterval> getReportIntervals(Intervals  oadrInterval){
		List<ReportInterval> reportIntervals = new ArrayList<ReportInterval>();
		if(null != oadrInterval){
			List<IntervalType> oadrIntervals = oadrInterval.getInterval();
			for(IntervalType intervalType: oadrIntervals){
				ReportInterval reportInterval = new ReportInterval();
				if(null != intervalType.getDuration()){
					reportInterval.setDuration(getDuration(intervalType.getDuration()));
				}
				reportInterval.setPayloadList(getPayloads(intervalType));
				reportInterval.setStart(getDate(intervalType.getDtstart()));
				Uid uid = intervalType.getUid();
				if(null != uid){
					reportInterval.setuId(intervalType.getUid().getText());
				}else{
					reportInterval.setuId(null);
				}
				reportIntervals.add(reportInterval);
			}
		}
		return reportIntervals;
	}

	private List<PayloadBase> getPayloads(IntervalType intervalType){
		List<JAXBElement<? extends StreamPayloadBaseType>> streamPayloadBase = intervalType.getStreamPayloadBase();
		List<PayloadBase> payloads = new ArrayList<PayloadBase>();
		for(JAXBElement<? extends StreamPayloadBaseType> jaxbStreamPayloadBaseType: streamPayloadBase){
			StreamPayloadBaseType streamPayloadBaseType = jaxbStreamPayloadBaseType.getValue();
			if(null != streamPayloadBaseType){
				PayloadBase payload = new PayloadBase();
				if(streamPayloadBaseType instanceof SignalPayloadType){}else if(streamPayloadBaseType instanceof OadrGBStreamPayloadBase){
					payload = getPayloadFromSignalPayloadType(streamPayloadBaseType);
				}else if(streamPayloadBaseType instanceof ReportPayloadType){
					payload = getPayloadFromReportPayloadType(streamPayloadBaseType);
				}else if(streamPayloadBaseType instanceof OadrGBStreamPayloadBase){
					payload = getPayloadFromOadrGBStreamPayloadBase(streamPayloadBaseType);
				}
				payloads.add(payload);
			}else{
				log.error("streamPayloadBaseType is null !!!");
			}
		}
		return payloads;
	}
	
	private PayloadBase getPayloadFromSignalPayloadType(StreamPayloadBaseType streamPayloadBaseType){
		PayloadBase payload = null;
		SignalPayloadType oadrSignalPayloadType = (SignalPayloadType)streamPayloadBaseType;
		payload = getPayLoadBase(oadrSignalPayloadType.getPayloadBase());
		return payload;
	}
	
	private PayloadBase getPayloadFromReportPayloadType(StreamPayloadBaseType streamPayloadBaseType){
		PayloadBase payload = null;
		ReportPayloadType reportPayloadType = (ReportPayloadType)streamPayloadBaseType;
		//This getPayloadBase object call to be made before setting accuracy,confidence and rId
		payload = getPayLoadBase(reportPayloadType.getPayloadBase());
		payload.setAccuracy(reportPayloadType.getAccuracy());
		payload.setConfidence(reportPayloadType.getConfidence());
		payload.setrId(reportPayloadType.getRID());
		return payload;
	}
	
	//TODO Not Done: fields for mapping the GBStreamPayloadBase are not available on the dras Side
	private PayloadBase getPayloadFromOadrGBStreamPayloadBase(StreamPayloadBaseType streamPayloadBaseType){
		PayloadBase payload = new PayloadBase();
		OadrGBStreamPayloadBase oadrGBStreamPayloadBase = (OadrGBStreamPayloadBase)streamPayloadBaseType;
		FeedType oadrFeedType = oadrGBStreamPayloadBase.getFeed();
		
		FeedType feedType = new FeedType();
		feedType.setBase(oadrFeedType.getBase());
		feedType.setLang(oadrFeedType.getLang());
		
		log.error("OadrGBStreamPayloadBase is not mapped properly !!!");
		
		return payload;
	}
	
	private PayloadBase getPayLoadBase(JAXBElement<? extends PayloadBaseType> oadrPayloadBase){
		PayloadBase payload = new PayloadBase();
		if(null != oadrPayloadBase){
			PayloadBaseType oadrPayloadBaseType = oadrPayloadBase.getValue();
			if(oadrPayloadBaseType instanceof PayloadFloatType){
				float value = ((PayloadFloatType) oadrPayloadBaseType).getValue();
				payload.setValue(value);
			}else if(oadrPayloadBaseType instanceof OadrPayloadResourceStatusType){
				payload.setResourceStatus(getResourceStatus((OadrPayloadResourceStatusType) oadrPayloadBaseType));	
			}
		}else{
			log.error("oadrPayloadBase is null !!!");
		}
		return payload;
	}
	
	private ResourceStatus getResourceStatus(OadrPayloadResourceStatusType oadrPayloadResourceStatusType){
		ResourceStatus resourceStatus = new  ResourceStatus();
		if(null != oadrPayloadResourceStatusType){
			resourceStatus.setManualOverride(oadrPayloadResourceStatusType.isOadrManualOverride());
			resourceStatus.setOnline(oadrPayloadResourceStatusType.isOadrOnline());
			OadrLoadControlStateType oadrLoadControlState = oadrPayloadResourceStatusType.getOadrLoadControlState();
			if(null != oadrLoadControlState){
				resourceStatus.setCapacity(getCapacity(oadrLoadControlState.getOadrCapacity()));
				resourceStatus.setLevelOffset(getLevelOffset(oadrLoadControlState.getOadrLevelOffset()));
				resourceStatus.setPercentOffset(getPercentOffset(oadrLoadControlState.getOadrPercentOffset()));
				resourceStatus.setSetPoint(getSetPoint(oadrLoadControlState.getOadrSetPoint()));
			}
		}
		return resourceStatus;
	}
	
	private Capacity getCapacity(OadrLoadControlStateTypeType oadrCapacity){
		Capacity capacity = null;
		if(null != oadrCapacity){
			capacity = new Capacity();
			capacity.setCurrent(oadrCapacity.getOadrCurrent());
			capacity.setMax(oadrCapacity.getOadrMax());
			capacity.setMin(oadrCapacity.getOadrMin());
			capacity.setNormal(oadrCapacity.getOadrNormal());
		}
		return capacity;
	}
	
	private LevelOffset getLevelOffset(OadrLoadControlStateTypeType oadrLevelOffset){
		LevelOffset levelOffset = null;
		if(null != oadrLevelOffset){
			levelOffset = new LevelOffset();
			levelOffset.setCurrent(oadrLevelOffset.getOadrCurrent());
			levelOffset.setMax(oadrLevelOffset.getOadrMax());
			levelOffset.setMin(oadrLevelOffset.getOadrMin());
			levelOffset.setNormal(oadrLevelOffset.getOadrNormal());
		}
		return levelOffset;
	}
	
	private PercentOffset getPercentOffset(OadrLoadControlStateTypeType oadrPercentOffset){
		PercentOffset percentOffset = null;
		if(null != oadrPercentOffset){
			percentOffset = new PercentOffset();
			percentOffset.setCurrent(oadrPercentOffset.getOadrCurrent());
			percentOffset.setMax(oadrPercentOffset.getOadrMax());
			percentOffset.setMin(oadrPercentOffset.getOadrMin());
			percentOffset.setNormal(oadrPercentOffset.getOadrNormal());
		}
		return percentOffset;
	}
	
	private SetPoint getSetPoint(OadrLoadControlStateTypeType oadrSetPoint){
		SetPoint setPoint = null;
		if(null != oadrSetPoint){
			setPoint = new SetPoint();
			setPoint.setCurrent(oadrSetPoint.getOadrCurrent());
			setPoint.setMax(oadrSetPoint.getOadrMax());
			setPoint.setMin(oadrSetPoint.getOadrMin());
			setPoint.setNormal(oadrSetPoint.getOadrNormal());
		}
		return setPoint;
	}

	private List<ReportDescription> getReportDescriptions(List<OadrReportDescriptionType> oadrReportDescriptionTypes, String reportId,String venId){
		List<ReportDescription> reportDescriptions = new ArrayList<ReportDescription>();
		if(null != oadrReportDescriptionTypes){
			for(OadrReportDescriptionType oadrReportDescriptionType: oadrReportDescriptionTypes){
				ReportDescription reportDescription = new ReportDescription();
				reportDescription.setItemBase(getItemBase(oadrReportDescriptionType.getItemBase()));
				reportDescription.setMarketContext(oadrReportDescriptionType.getMarketContext());
				reportDescription.setReadingType(oadrReportDescriptionType.getReadingType());
				reportDescription.setReportDataSource(getReportDataSource(oadrReportDescriptionType.getReportDataSource(),venId));
				//TODO there is no reportId field on the OpenADR side for OadrReportDescriptionType.
				//So we map the reportId from the top level OadrReport class
				reportDescription.setReportId(reportId);
				reportDescription.setReportSubject(getReportSubject(oadrReportDescriptionType.getReportSubject()));
				reportDescription.setReportType(oadrReportDescriptionType.getReportType());
				reportDescription.setrId(oadrReportDescriptionType.getRID());
				reportDescription.setSamplingMaxPeriod(getSamplingMaxPeriod(oadrReportDescriptionType.getOadrSamplingRate()));
				reportDescription.setSamplingMinPeriod(getSamplingMinPeriod(oadrReportDescriptionType.getOadrSamplingRate()));
				reportDescriptions.add(reportDescription);
			}
		}
		return reportDescriptions;
	}
	private Long getSamplingMaxPeriod(OadrSamplingRateType oadrSamplingRateType){
		String oadrMaxPeriod = "PT10M";
		if(null !=oadrSamplingRateType)
			oadrMaxPeriod = oadrSamplingRateType.getOadrMaxPeriod();
		return DurationUtil.parse(oadrMaxPeriod);
	}
	
	private Long getSamplingMinPeriod(OadrSamplingRateType oadrSamplingRateType){
		String oadrMinPeriod = "PT1M";
		if(null !=oadrSamplingRateType)
			oadrMinPeriod = oadrSamplingRateType.getOadrMinPeriod();
		return DurationUtil.parse(oadrMinPeriod);
	}
	
	//TODO not done: ServiceArea / ServiceDeliveryPointList / ServiceLocationList are not mapped
	private ReportDataSource getReportDataSource(EiTargetType oadrEiTargetType,String venId){
		ReportDataSource reportDataSource = new ReportDataSource();
		if(null != oadrEiTargetType){
			reportDataSource.setAggregatedPnodeList(getAggregatedPnodeList(oadrEiTargetType.getAggregatedPnode()));
			reportDataSource.setEndDeviceAssetList(getEndDeviceAssetTypes(oadrEiTargetType.getEndDeviceAsset()));
			reportDataSource.setGroupIdList(oadrEiTargetType.getGroupID());
			reportDataSource.setGroupNameList(oadrEiTargetType.getGroupName());
			reportDataSource.setMeterAssetList(getMeterAssetTypes(oadrEiTargetType.getMeterAsset()));
			reportDataSource.setPartyIdList(oadrEiTargetType.getPartyID());
			reportDataSource.setPnodeList(getPnodeTypes(oadrEiTargetType.getPnode()));
			reportDataSource.setResourceIdList(oadrEiTargetType.getResourceID());
			reportDataSource.setServiceAreaList(getServiceAreas(oadrEiTargetType.getServiceArea()));
			reportDataSource.setServiceDeliveryPointList(getServiceDeliveryPoints(oadrEiTargetType.getServiceDeliveryPoint()));
			reportDataSource.setServiceLocationList(getServiceLocations(oadrEiTargetType.getServiceLocation()));
			reportDataSource.setTransportInterfaceList(getTransportInterfaces(oadrEiTargetType.getTransportInterface()));
			reportDataSource.setVenIdList(oadrEiTargetType.getVenID());
		}else{
			//When the oadrEiTargetType is null, it means that the particular report belongs entirely to a ven
			//so we store the ven id itself as the resourceid in the report datasource table
			if(null != venId){
				List<String> artificialResourceList = new ArrayList<String>();
				artificialResourceList.add(venId);
				reportDataSource.setResourceIdList(artificialResourceList);
			}
		}
		return reportDataSource;
	}
	
	//TODO not done: ServiceArea / ServiceDeliveryPointList / ServiceLocationList are not mapped
	private ReportSubject getReportSubject(EiTargetType oadrEiTargetType){
		ReportSubject reportSubject = new ReportSubject();
		if(null != oadrEiTargetType){
			reportSubject.setAggregatedPnodeList(getAggregatedPnodeList(oadrEiTargetType.getAggregatedPnode()));
			reportSubject.setEndDeviceAssetList(getEndDeviceAssetTypes(oadrEiTargetType.getEndDeviceAsset()));
			reportSubject.setGroupIdList(oadrEiTargetType.getGroupID());
			reportSubject.setGroupNameList(oadrEiTargetType.getGroupName());
			reportSubject.setMeterAssetList(getMeterAssetTypes(oadrEiTargetType.getMeterAsset()));
			reportSubject.setPartyIdList(oadrEiTargetType.getPartyID());
			reportSubject.setPnodeList(getPnodeTypes(oadrEiTargetType.getPnode()));
			reportSubject.setResourceIdList(oadrEiTargetType.getResourceID());
			reportSubject.setServiceAreaList(getServiceAreas(oadrEiTargetType.getServiceArea()));
			reportSubject.setServiceDeliveryPointList(getServiceDeliveryPoints(oadrEiTargetType.getServiceDeliveryPoint()));
			reportSubject.setServiceLocationList(getServiceLocations(oadrEiTargetType.getServiceLocation()));
			reportSubject.setTransportInterfaceList(getTransportInterfaces(oadrEiTargetType.getTransportInterface()));
			reportSubject.setVenIdList(oadrEiTargetType.getVenID());
		}
		return reportSubject;
	}
	
	private List<String> getAggregatedPnodeList(List<AggregatedPnodeType> oadrAggregatedPnodeTypes){
		List<String> aggregatedPnodeList = new ArrayList<String>();
		for(AggregatedPnodeType oadrAggregatedPnodeType: oadrAggregatedPnodeTypes){
			aggregatedPnodeList.add(oadrAggregatedPnodeType.getNode());
		}
		return aggregatedPnodeList;
	}
	
	private List<String> getEndDeviceAssetTypes(List<EndDeviceAssetType> oadrEndDeviceAssetTypes){
		List<String> endDeviceAssetTypes = new ArrayList<String>();
		for(EndDeviceAssetType oadrEndDeviceAssetType: oadrEndDeviceAssetTypes){
			endDeviceAssetTypes.add(oadrEndDeviceAssetType.getMrid());
		}
		return endDeviceAssetTypes;
	}
	
	private List<String> getMeterAssetTypes(List<MeterAssetType> oadrMeterAssetTypes){
		List<String> meterAssetTypes = new ArrayList<String>();
		for(MeterAssetType oadrMeterAssetType: oadrMeterAssetTypes){
			meterAssetTypes.add(oadrMeterAssetType.getMrid());
		}
		return meterAssetTypes;
	}
	
	private List<String> getPnodeTypes(List<PnodeType> oadrPnodeTypes){
		List<String> pnodeTypes = new ArrayList<String>();
		for(PnodeType oadrPnodeType : oadrPnodeTypes){
			pnodeTypes.add(oadrPnodeType.getNode());
		}
		return pnodeTypes;
	}
	
	private List<TransportInterface> getTransportInterfaces(List<TransportInterfaceType> oadrTransportInterfaceTypes){
		List<TransportInterface> transportInterfaces = new ArrayList<TransportInterface>();
		for(TransportInterfaceType oadrTransportInterfaceType: oadrTransportInterfaceTypes){
			TransportInterface transportInterface = new TransportInterface();
			transportInterface.setPointOfDelivery(oadrTransportInterfaceType.getPointOfDelivery());
			transportInterface.setPointOfReceipt(oadrTransportInterfaceType.getPointOfReceipt());
			transportInterfaces.add(transportInterface);
		}
		return transportInterfaces;
	}
	
	//TODO not done
	private List<ServiceArea> getServiceAreas(List<ServiceAreaType> oadrServiceAreaTypes){
		return null;
	}
	//TODO not done
	private List<ServiceDeliveryPoint> getServiceDeliveryPoints(List<ServiceDeliveryPointType> oadrServiceDeliveryPointTypes){
		return null;
	}
	//TODO not done
	private List<ServiceLocation> getServiceLocations(List<ServiceLocationType> oadrServiceLocationTypes){
		return null;
	}
	
	/////////////////////////////////////////////////////
	//END of ReportService methods
	/////////////////////////////////////////////////////
	
	/////////////////////////////////////////////////////
	// Registration Services
	/////////////////////////////////////////////////////

	public QueryRegistration getQueryRegistration(OadrQueryRegistrationType queryRegistration) {
		QueryRegistration res = new QueryRegistration();

		if (queryRegistration != null) {
			res.setRequestId(queryRegistration.getRequestID());
			res.setSchemaVersion(queryRegistration.getSchemaVersion());
			res.setCertCommonName(certCommonName);
		}

		return res;
	}

	public CreatePartyRegistration getCreatePartyRegistration(OadrCreatePartyRegistrationType createPartyRegistration) {
		CreatePartyRegistration res = new CreatePartyRegistration();

		if (createPartyRegistration != null) {
			res.setRegistrationId(createPartyRegistration.getRegistrationID());
			res.setHttpPullMode(createPartyRegistration.isOadrHttpPullModel());
			res.setProfileName(ProfileName.fromValue(createPartyRegistration.getOadrProfileName()));
			res.setReportOnly(createPartyRegistration.isOadrReportOnly());
			res.setRequestId(createPartyRegistration.getRequestID());
			res.setSchemaVersion(createPartyRegistration.getSchemaVersion());
			res.setTransportAddress(createPartyRegistration.getOadrTransportAddress());

			List<TransportName> transports = new ArrayList<TransportName>();
			transports.add(TransportName.fromValue(createPartyRegistration.getOadrTransportName().value()));
			res.setTransports(transports);

			res.setVenId(createPartyRegistration.getVenID());
			res.setVenName(createPartyRegistration.getOadrVenName());
			res.setXmlSignature(createPartyRegistration.isOadrXmlSignature());
			res.setCertCommonName(certCommonName);
		}

		return res;
	}

	public CreatedPartyRegistration getCreatedPartyRegistration(OadrCreatedPartyRegistrationType createPartyRegistration) {
		CreatedPartyRegistration res = new CreatedPartyRegistration();

		if (createPartyRegistration != null) {
			if (createPartyRegistration.getEiResponse() != null) {
				res.setResponse(this.getResponse(createPartyRegistration.getEiResponse()));
			}

			if (createPartyRegistration.getOadrRequestedOadrPollFreq() != null
					&& createPartyRegistration.getOadrRequestedOadrPollFreq().getDuration() != null) {
				res.setPollFrequency(getDuration(createPartyRegistration.getOadrRequestedOadrPollFreq()));
			}

			if (createPartyRegistration.getOadrProfiles() != null && createPartyRegistration.getOadrProfiles().getOadrProfile() != null) {
				List<Profile> profiles = new ArrayList<Profile>();

				 for (OadrProfile oProfile : createPartyRegistration.getOadrProfiles().getOadrProfile()) {
					 Profile prof = new Profile();
					 prof.setProfileName(ProfileName.fromValue(oProfile.getOadrProfileName()));
					 List<TransportName> transports = new ArrayList<TransportName>();
					
					 for (OadrTransport otrans : oProfile.getOadrTransports().getOadrTransport()) {
						 
						 transports.add( TransportName.fromValue(otrans.getOadrTransportName().value()));
						 
						}
					prof.setTransports(transports);
					 
					profiles.add(prof);
				 }

				res.setProfiles(profiles);
			}

			res.setRegistrationId(createPartyRegistration.getRegistrationID());
			res.setSchemaVersion(createPartyRegistration.getSchemaVersion());
			res.setVenId(createPartyRegistration.getVenID());
			res.setVtnId(createPartyRegistration.getVtnID());
			res.setCertCommonName(certCommonName);
		}

		return res;
	}

	public CancelPartyRegistration getCancelPartyRegistration(OadrCancelPartyRegistrationType cancelPartyRegistration) {
		CancelPartyRegistration res = new CancelPartyRegistration();

		if (cancelPartyRegistration != null) {
			res.setRegistrationId(cancelPartyRegistration.getRegistrationID());
			res.setRequestId(cancelPartyRegistration.getRequestID());
			res.setSchemaVersion(cancelPartyRegistration.getSchemaVersion());
			res.setVenId(cancelPartyRegistration.getVenID());
			res.setCertCommonName(certCommonName);
		}

		return res;
	}

	public CanceledPartyRegistration getCanceledPartyRegistration(OadrCanceledPartyRegistrationType canceledPartyRegistration) {
		CanceledPartyRegistration res = new CanceledPartyRegistration();

		if (canceledPartyRegistration != null) {
			res.setRegistrationId(canceledPartyRegistration.getRegistrationID());
			res.setResponse(this.getResponse(canceledPartyRegistration.getEiResponse()));
			res.setSchemaVersion(canceledPartyRegistration.getSchemaVersion());
			res.setVenId(canceledPartyRegistration.getVenID());
			res.setCertCommonName(certCommonName);
		}

		return res;
	}

	/////////////////////////////////////////////////////
	//END of Party Registration Service methods
	/////////////////////////////////////////////////////
	
	/////////////////////////////////////////////////////
	// Event Services
	/////////////////////////////////////////////////////
	
	public RequestEvent getRequestEvent(OadrRequestEventType requestEvent) {
		RequestEvent res = new RequestEvent();

		if (requestEvent != null && requestEvent.getEiRequestEvent() != null) {
			res.setReplyLimit(requestEvent.getEiRequestEvent().getReplyLimit());
			res.setRequestId(requestEvent.getEiRequestEvent().getRequestID());
			res.setSchemaVersion(requestEvent.getSchemaVersion());
			res.setVenId(requestEvent.getEiRequestEvent().getVenID());
			res.setCertCommonName(certCommonName);
		}

		return res;
	}
	
	public CreatedEvent getCreatedEvent(OadrCreatedEventType createdEvent) {
		CreatedEvent res = new CreatedEvent();

		if (createdEvent != null && createdEvent.getEiCreatedEvent() != null) {
			res.setSchemaVersion(createdEvent.getSchemaVersion());
			res.setResponse(this.getResponse(createdEvent.getEiCreatedEvent().getEiResponse()));
			res.setVenId(createdEvent.getEiCreatedEvent().getVenID());
			
			if (createdEvent.getEiCreatedEvent().getEventResponses() != null 
					&& createdEvent.getEiCreatedEvent().getEventResponses().getEventResponse() != null) {
				for (com.honeywell.openadr.core.signal.EventResponses.EventResponse er : createdEvent.getEiCreatedEvent().getEventResponses().getEventResponse()) {
					com.honeywell.ven.api.event.EventResponse eResp = new com.honeywell.ven.api.event.EventResponse();
					
					eResp.setDescription(er.getResponseDescription());
					eResp.setEventID(er.getQualifiedEventID().getEventID());
					eResp.setModificationNumber(er.getQualifiedEventID().getModificationNumber());
					eResp.setOptType(OptType.fromValue(er.getOptType().value()));
					eResp.setRequestID(er.getRequestID());
					eResp.setResponseCode(er.getResponseCode());
					
					res.getEventResponseList().add(eResp);
					res.setCertCommonName(certCommonName);
				}
			}
		}

		return res;
	}
	
	
	public DistributeEvent getDistributeEvent(OadrDistributeEventType distributeEvent) {
		DistributeEvent res = new DistributeEvent();

		if (distributeEvent != null ) {
			res.setSchemaVersion(distributeEvent.getSchemaVersion());
			res.setResponse(this.getResponse(distributeEvent.getEiResponse()));
			res.setVtnId(distributeEvent.getVtnID());
			res.setRequestId(distributeEvent.getRequestID());
					
		
			if (distributeEvent.getOadrEvent() != null)
			{
				List<Event> eventList = new ArrayList<Event>();
			for (OadrEvent oadrEvent : distributeEvent.getOadrEvent()) {
				Event event = new Event();
				event.setEventID(oadrEvent.getEiEvent().getEventDescriptor().getEventID());
				event.setEventStatus(EventStatus.fromValue(oadrEvent.getEiEvent().getEventDescriptor().getEventStatus().value()));
				
				List<Signal> signals = new ArrayList<Signal>();
				for (EiEventSignalType eiEventSignalType : oadrEvent.getEiEvent().getEiEventSignals().getEiEventSignal()) {
					Signal signal = new Signal();
					signal.setCurrentValue(eiEventSignalType.getCurrentValue().getPayloadFloat().getValue());
					signals.add(signal);
				}
				event.setSignals(signals);	
			   	event.setTarget(getEiTarget(oadrEvent.getEiEvent().getEiTarget()));					
				eventList.add(event);
								
		}
			res.setEventList(eventList);
			res.setCertCommonName(certCommonName);
		}
			
	}
		return res;
		
}
	
	/////////////////////////////////////////////////////
	// END of Event Services
	/////////////////////////////////////////////////////
	
	///////////////////////////////////////
	//Poll Service payloads
	///////////////////////////////////////
		
	public Poll getPoll(OadrPollType oadrPollType){
		Poll poll = new Poll();
		poll.setSchemaVersion(oadrPollType.getSchemaVersion());
		poll.setVenId(oadrPollType.getVenID());
		poll.setCertCommonName(certCommonName);
		return poll;
	}
	
	/////////////////////////////////////////////////////
	// END of Poll service
	/////////////////////////////////////////////////////
	
	///////////////////////////////////////
	// Opt service
	///////////////////////////////////////
	
	public CreateOpt  getCreateOpt(OadrCreateOptType oadrCreateOptType){
		CreateOpt createOpt = new CreateOpt();	
		if(null == oadrCreateOptType){
			return createOpt;
		}
		createOpt.setAvailibilityList(getAvailibilityList(oadrCreateOptType.getVavailability()));
		//createOpt.setCertCommonName(oadrCreateOptType.get)
		createOpt.setDeviceClass(getDeviceClass(oadrCreateOptType.getEiTarget()));
		QualifiedEventIDType qualifiedEventIDType = oadrCreateOptType.getQualifiedEventID();
		if(null != qualifiedEventIDType){
			createOpt.setEventId(qualifiedEventIDType.getEventID());
		}
		//createOpt.setFingerprint(oadrCreateOptType.ge)
		createOpt.setMarketContext(oadrCreateOptType.getMarketContext());
		createOpt.setOptId(oadrCreateOptType.getOptID());
		createOpt.setOptReason(oadrCreateOptType.getOptReason());
		createOpt.setOptType(OptType.fromValue(oadrCreateOptType.getOptType().value()));
		createOpt.setRequestId(oadrCreateOptType.getRequestID());
		createOpt.setSchemaVersion(oadrCreateOptType.getSchemaVersion());
		createOpt.setTarget(getTarget(oadrCreateOptType.getEiTarget()));
		createOpt.setVenId(oadrCreateOptType.getVenID());
		createOpt.setCertCommonName(certCommonName);
		return createOpt;
	}
	
	public CreatedOpt  getCreatedOpt(OadrCreatedOptType oadrCreatedOptType){
		CreatedOpt createdOpt = new CreatedOpt();   
		if(null == oadrCreatedOptType){
			return createdOpt;
		}
		//createdOpt.setCertCommonName()
		//createdOpt.setFingerprint()
		createdOpt.setOptId(oadrCreatedOptType.getOptID());
		createdOpt.setRequestId(oadrCreatedOptType.getEiResponse().getRequestID());
		createdOpt.setResponse(getResponse(oadrCreatedOptType.getEiResponse()));
		createdOpt.setSchemaVersion(oadrCreatedOptType.getSchemaVersion());
		//createdOpt.setVenId();
		createdOpt.setCertCommonName(certCommonName);
		return createdOpt;
	}
	public CancelOpt  getCancelOpt(OadrCancelOptType oadrCancelOptType){
		CancelOpt cancelOpt = new CancelOpt();   
		if(null == oadrCancelOptType){
			return cancelOpt;
		}
		//cancelOpt.setCertCommonName();
		//cancelOpt.setFingerprint()
		cancelOpt.setOptId(oadrCancelOptType.getOptID());
		cancelOpt.setRequestId(oadrCancelOptType.getRequestID());
		cancelOpt.setSchemaVersion(oadrCancelOptType.getSchemaVersion());
		cancelOpt.setVenId(oadrCancelOptType.getVenID());
		cancelOpt.setCertCommonName(certCommonName);
		return cancelOpt;
	}
	public CanceledOpt  getCanceledOpt(OadrCanceledOptType oadrCanceledOptType) {
		CanceledOpt canceledOpt = new CanceledOpt();   
		if(null == oadrCanceledOptType){
			return canceledOpt;
		}
		//canceledOpt.setCertCommonName()
		//canceledOpt.getFingerprint(oadrCancelOptType.getEiResponse())
		canceledOpt.setOptId(oadrCanceledOptType.getOptID());
		canceledOpt.setRequestId(oadrCanceledOptType.getEiResponse().getRequestID());
		canceledOpt.setResponse(getResponse(oadrCanceledOptType.getEiResponse()));
		canceledOpt.setSchemaVersion(oadrCanceledOptType.getSchemaVersion());
		//canceledOpt.setVenId(oadrCanceledOptType.getEiResponse())
		canceledOpt.setCertCommonName(certCommonName);
		return canceledOpt;
	}
	private Target getTarget(EiTargetType eiTargetType){
		Target target = new Target();  
		target.setGroupIdList(eiTargetType.getGroupID());
		target.setPartyIdList(eiTargetType.getPartyID());
		target.setResourceIdList(eiTargetType.getResourceID());
		target.setVenIdList(eiTargetType.getVenID());
		return target;
	}
	private DeviceClass getDeviceClass(EiTargetType eiTargetType){
		DeviceClass deviceClass = new DeviceClass();  
		if(null == eiTargetType){
			return deviceClass;
		}
		deviceClass.setAggregatedPnodeList(getAggregatedPnodeList(eiTargetType.getAggregatedPnode()));
		deviceClass.setEndDeviceAssetList(getEndDeviceAssetTypes(eiTargetType.getEndDeviceAsset()));
		deviceClass.setGroupIdList(eiTargetType.getGroupID());
		deviceClass.setGroupNameList(eiTargetType.getGroupName());
		deviceClass.setPartyIdList(eiTargetType.getPartyID());
		deviceClass.setMeterAssetList(getMeterAssetTypes(eiTargetType.getMeterAsset()));
		deviceClass.setPnodeList(getPnodeTypes(eiTargetType.getPnode()));
		deviceClass.setResourceIdList(eiTargetType.getResourceID());
		deviceClass.setServiceAreaList(getServiceAreas(eiTargetType.getServiceArea()));
		deviceClass.setServiceDeliveryPointList(getServiceDeliveryPoints(eiTargetType.getServiceDeliveryPoint()));
		deviceClass.setServiceLocationList(getServiceLocations(eiTargetType.getServiceLocation()));
		deviceClass.setTransportInterfaceList(getTransportInterfaces(eiTargetType.getTransportInterface()));
		deviceClass.setVenIdList(eiTargetType.getVenID());
		return deviceClass;
	}
	
	private EiTarget getEiTarget (EiTargetType eiTargetType){
		EiTarget eiTarget = new EiTarget();  
		if(null == eiTargetType){
			return eiTarget;
		}
		eiTarget.setAggregatedPnodeList(getAggregatedPnodeList(eiTargetType.getAggregatedPnode()));
		eiTarget.setEndDeviceAssetList(getEndDeviceAssetTypes(eiTargetType.getEndDeviceAsset()));
		eiTarget.setGroupIdList(eiTargetType.getGroupID());
		eiTarget.setGroupNameList(eiTargetType.getGroupName());
		eiTarget.setPartyIdList(eiTargetType.getPartyID());
		eiTarget.setMeterAssetList(getMeterAssetTypes(eiTargetType.getMeterAsset()));
		eiTarget.setPnodeList(getPnodeTypes(eiTargetType.getPnode()));
		eiTarget.setResourceIdList(eiTargetType.getResourceID());
		eiTarget.setServiceAreaList(getServiceAreas(eiTargetType.getServiceArea()));
		eiTarget.setServiceDeliveryPointList(getServiceDeliveryPoints(eiTargetType.getServiceDeliveryPoint()));
		eiTarget.setServiceLocationList(getServiceLocations(eiTargetType.getServiceLocation()));
		eiTarget.setTransportInterfaceList(getTransportInterfaces(eiTargetType.getTransportInterface()));
		eiTarget.setVenIdList(eiTargetType.getVenID());
		return eiTarget;
	}
	private List<Availibility> getAvailibilityList(VavailabilityType vavailabilityType){
		List<Availibility> availibilityList = new ArrayList<Availibility>();
		ArrayOfVavailabilityContainedComponents arrayOfVavailability = vavailabilityType.getComponents();
		if(null == arrayOfVavailability){
			return availibilityList;
		}
		List<AvailableType> availableTypeList =  arrayOfVavailability.getAvailable();
		if(null == availableTypeList){
			return availibilityList;
		}
		for(AvailableType availableType : availableTypeList){
			availibilityList.add(getAvailibility(availableType));
		}
		return availibilityList;
	}
	
	private Availibility getAvailibility(AvailableType availableType){
		Availibility availibility = new Availibility();
		Properties properties = availableType.getProperties();
		if(null == properties){
			return availibility;
		}
		availibility.setDuration(getDuration(properties.getDuration()));
		availibility.setStartTime(getDate(properties.getDtstart()));
		return availibility;
	}
	
	///////////////////////////////////////
	// Opt service end
	///////////////////////////////////////
	
	/////////////////////////////////////////////////////
	// Utility
	/////////////////////////////////////////////////////
	
	private WsCalanderInterval getCalanderInterval(WsCalendarIntervalType oadrWsCalendarIntervalType){
		WsCalanderInterval wsCalanderInterval = new WsCalanderInterval();
		if(null != oadrWsCalendarIntervalType){
			Properties oadrProperties = oadrWsCalendarIntervalType.getProperties();
			if(null != oadrProperties.getDuration()){
				wsCalanderInterval.setDuration(getDuration(oadrProperties.getDuration()));
			}
			if(null != oadrProperties.getXEiNotification()){
			
				wsCalanderInterval.setNotification(getDuration(oadrProperties.getXEiNotification()));
			}
			if(null != oadrProperties.getXEiRampUp()){
				wsCalanderInterval.setRampUp(getDuration(oadrProperties.getXEiRampUp()));
			}
			if(null != oadrProperties.getXEiRecovery()){
				wsCalanderInterval.setRecovery(getDuration(oadrProperties.getXEiRecovery()));
			}
			if(null != oadrProperties.getDtstart())
			{
				wsCalanderInterval.setStart(getDate(oadrProperties.getDtstart()));
			}
			if(null != oadrProperties.getTolerance()){
				wsCalanderInterval.setToleranceStartAfter( getTolerance(oadrProperties.getTolerance()));
			}
		}
		return wsCalanderInterval;
	}
	
	private long getDuration(DurationPropType durationPropType){
		String duration = durationPropType.getDuration();	
		return DurationUtil.parse(duration);
	}
	
	private Date getDate(Dtstart dtstart){
		if(null != dtstart){
			XMLGregorianCalendar xgc = dtstart.getDateTime();
			return XMLGregorianCalendarConverter.asDate(xgc);
		}
		return null;
	}
	
	private Long getTolerance(Tolerance oadrTolerance){
		Tolerate tolerate = oadrTolerance.getTolerate();
		String startafter = tolerate.getStartafter();
		return DurationUtil.parse(startafter);
	}

	
	
}
