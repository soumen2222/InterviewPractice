package com.honeywell.payloads;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.xml.bind.JAXBElement;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.XMLGregorianCalendar;

import com.honeywell.openadr.core.signal.AggregatedPnodeType;
import com.honeywell.openadr.core.signal.BaseUnitType;
import com.honeywell.openadr.core.signal.CurrencyItemDescriptionType;
import com.honeywell.openadr.core.signal.CurrencyType;
import com.honeywell.openadr.core.signal.CurrentValueType;
import com.honeywell.openadr.core.signal.EiActivePeriodType;
import com.honeywell.openadr.core.signal.EiCreatedEvent;
import com.honeywell.openadr.core.signal.EiEventSignalType;
import com.honeywell.openadr.core.signal.EiEventSignalsType;
import com.honeywell.openadr.core.signal.EiEventType;
import com.honeywell.openadr.core.signal.EiResponseType;
import com.honeywell.openadr.core.signal.EiTargetType;
import com.honeywell.openadr.core.signal.EndDeviceAssetType;
import com.honeywell.openadr.core.signal.EnergyApparentType;
import com.honeywell.openadr.core.signal.EnergyReactiveType;
import com.honeywell.openadr.core.signal.EnergyRealType;
import com.honeywell.openadr.core.signal.EventDescriptorType;
import com.honeywell.openadr.core.signal.EventDescriptorType.EiMarketContext;
import com.honeywell.openadr.core.signal.EventResponses;
import com.honeywell.openadr.core.signal.EventStatusEnumeratedType;
import com.honeywell.openadr.core.signal.FeedType;
import com.honeywell.openadr.core.signal.FrequencyType;
import com.honeywell.openadr.core.signal.ISO3AlphaCurrencyCodeContentType;
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
import com.honeywell.openadr.core.signal.OadrGBItemBase;
import com.honeywell.openadr.core.signal.OadrPayload;
import com.honeywell.openadr.core.signal.OadrPayloadResourceStatusType;
import com.honeywell.openadr.core.signal.OadrPendingReportsType;
import com.honeywell.openadr.core.signal.OadrPollType;
import com.honeywell.openadr.core.signal.OadrProfiles;
import com.honeywell.openadr.core.signal.OadrProfiles.OadrProfile;
import com.honeywell.openadr.core.signal.OadrRegisterReportType;
import com.honeywell.openadr.core.signal.OadrRegisteredReportType;
import com.honeywell.openadr.core.signal.OadrReportDescriptionType;
import com.honeywell.openadr.core.signal.OadrReportPayloadType;
import com.honeywell.openadr.core.signal.OadrReportRequestType;
import com.honeywell.openadr.core.signal.OadrReportType;
import com.honeywell.openadr.core.signal.OadrRequestReregistrationType;
import com.honeywell.openadr.core.signal.OadrResponseType;
import com.honeywell.openadr.core.signal.OadrSamplingRateType;
import com.honeywell.openadr.core.signal.OadrSignedObject;
import com.honeywell.openadr.core.signal.OadrTransportType;
import com.honeywell.openadr.core.signal.OadrTransports;
import com.honeywell.openadr.core.signal.OadrTransports.OadrTransport;
import com.honeywell.openadr.core.signal.OadrUpdateReportType;
import com.honeywell.openadr.core.signal.OadrUpdatedReportType;
import com.honeywell.openadr.core.signal.OptTypeType;
import com.honeywell.openadr.core.signal.PayloadBaseType;
import com.honeywell.openadr.core.signal.PayloadFloatType;
import com.honeywell.openadr.core.signal.PnodeType;
import com.honeywell.openadr.core.signal.PowerApparentType;
import com.honeywell.openadr.core.signal.PowerReactiveType;
import com.honeywell.openadr.core.signal.PowerRealType;
import com.honeywell.openadr.core.signal.PulseCountType;
import com.honeywell.openadr.core.signal.QualifiedEventIDType;
import com.honeywell.openadr.core.signal.ReportPayloadType;
import com.honeywell.openadr.core.signal.ReportSpecifierType;
import com.honeywell.openadr.core.signal.ResponseRequiredType;
import com.honeywell.openadr.core.signal.ServiceAreaType;
import com.honeywell.openadr.core.signal.ServiceDeliveryPointType;
import com.honeywell.openadr.core.signal.ServiceLocationType;
import com.honeywell.openadr.core.signal.SignalPayloadType;
import com.honeywell.openadr.core.signal.SignalTypeEnumeratedType;
import com.honeywell.openadr.core.signal.SpecifierPayloadType;
import com.honeywell.openadr.core.signal.StreamPayloadBaseType;
import com.honeywell.openadr.core.signal.TemperatureType;
import com.honeywell.openadr.core.signal.TemperatureUnitType;
import com.honeywell.openadr.core.signal.ThermType;
import com.honeywell.openadr.core.signal.TransportInterfaceType;
import com.honeywell.openadr.core.signal.VoltageType;
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
import com.honeywell.ven.api.event.DistributeEvent;
import com.honeywell.ven.api.OptType;
import com.honeywell.ven.api.common.WsCalanderInterval;
import com.honeywell.ven.api.event.CreatedEvent;
import com.honeywell.ven.api.event.Event;
import com.honeywell.ven.api.event.EventInterval;
import com.honeywell.ven.api.event.EventResponse;
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
import com.honeywell.ven.api.poll.PollRequest;
import com.honeywell.ven.api.registration.CancelPartyRegistration;
import com.honeywell.ven.api.registration.CanceledPartyRegistration;
import com.honeywell.ven.api.registration.CreatePartyRegistration;
import com.honeywell.ven.api.registration.CreatedPartyRegistration;
import com.honeywell.ven.api.registration.Profile;
import com.honeywell.ven.api.registration.ReRegistration;
import com.honeywell.ven.api.registration.TransportName;
import com.honeywell.ven.api.report.BaseUnit;
import com.honeywell.ven.api.report.CancelReport;
import com.honeywell.ven.api.report.CanceledReport;
import com.honeywell.ven.api.report.CreateReport;
import com.honeywell.ven.api.report.CreatedReport;
import com.honeywell.ven.api.report.Currency;
import com.honeywell.ven.api.report.EnergyApparent;
import com.honeywell.ven.api.report.EnergyReactive;
import com.honeywell.ven.api.report.EnergyReal;
import com.honeywell.ven.api.report.Frequency;
import com.honeywell.ven.api.report.GBItemBase;
import com.honeywell.ven.api.report.ItemBase;
import com.honeywell.ven.api.report.PayloadBase;
import com.honeywell.ven.api.report.ReportDataPayload;
import com.honeywell.ven.api.report.ResourceStatusPayload;
import com.honeywell.ven.api.report.SignalPayload;
import com.honeywell.ven.api.report.PendingReports;
import com.honeywell.ven.api.report.PowerApparent;
import com.honeywell.ven.api.report.PowerReactive;
import com.honeywell.ven.api.report.PowerReal;
import com.honeywell.ven.api.report.PulseCount;
import com.honeywell.ven.api.report.RegisterReport;
import com.honeywell.ven.api.report.RegisteredReport;
import com.honeywell.ven.api.report.Report;
import com.honeywell.ven.api.report.ReportDescription;
import com.honeywell.ven.api.report.ReportInterval;
import com.honeywell.ven.api.report.ReportRequest;
import com.honeywell.ven.api.report.ReportSpecifier;
import com.honeywell.ven.api.report.SpecifierPayload;
import com.honeywell.ven.api.report.Temperature;
import com.honeywell.ven.api.report.Therm;
import com.honeywell.ven.api.report.UpdateReport;
import com.honeywell.ven.api.report.UpdatedReport;
import com.honeywell.ven.api.report.Voltage;
import com.honeywell.payloads.util.DurationUtil;
import com.honeywell.payloads.util.FactoryHelper;
import com.honeywell.payloads.util.XMLGregorianCalendarConverter;
import org.jboss.logging.Logger;

/**
 * Converts API types to OpenADR types
 * 
 * @author Joe
 * 
 */
public class PayloadGenerator {
	
	Logger log = Logger.getLogger(PayloadGenerator.class);
	
	public static final String VTN_ID_PROPERTY = "com.honeywell.dras.vtn.VtnId";
	public static final String REQUEST_ID_PREFIX = "SimVen_REQ:";
	
	///////////////////////////////////////
	//Common Response 
	///////////////////////////////////////
	
	
	public OadrResponseType getOadrResponse(com.honeywell.ven.api.common.Response response){
		OadrResponseType oadrResponseType = FactoryHelper.getOadrObjectFactory().createOadrResponseType();
		oadrResponseType.setEiResponse(getEiResponseType(response));
		oadrResponseType.setVenID(response.getVenId());
		oadrResponseType.setSchemaVersion(response.getSchemaVersion());
		return oadrResponseType;
	}
	
	public EiResponseType getEiResponseType(com.honeywell.ven.api.common.Response response){
		EiResponseType eiResponseType = FactoryHelper.getOadrObjectFactory().createEiResponseType();
		eiResponseType.setRequestID(response.getRequestId());
		eiResponseType.setResponseCode(response.getResponseCode());
		eiResponseType.setResponseDescription(response.getResponseDescription());
		
		return eiResponseType;
	}
	
	///////////////////////////////////////
	//Report Service payloads
	///////////////////////////////////////
	
	
	public OadrRegisterReportType getOadrRegisterReportType(RegisterReport registerReport) throws DatatypeConfigurationException{
		OadrRegisterReportType oadrRegisterReportType = FactoryHelper.getOadrObjectFactory().createOadrRegisterReportType();
		oadrRegisterReportType.setRequestID(registerReport.getRequestId());
		oadrRegisterReportType.setSchemaVersion(registerReport.getSchemaVersion());
		oadrRegisterReportType.setVenID(registerReport.getVenId());
		oadrRegisterReportType.getOadrReport().addAll(getOadrReportType(registerReport.getReportList()));
		
		return oadrRegisterReportType;
	}
	
	public OadrRegisteredReportType getOadrRegisteredReportType(RegisteredReport registeredReport) throws DatatypeConfigurationException{
		OadrRegisteredReportType oadrRegisteredReportType = FactoryHelper.getOadrObjectFactory().createOadrRegisteredReportType();
		oadrRegisteredReportType.setSchemaVersion(registeredReport.getSchemaVersion());
		oadrRegisteredReportType.setVenID(registeredReport.getVenId());
		oadrRegisteredReportType.setEiResponse(getEiResponseType(registeredReport.getResponse()));
		oadrRegisteredReportType.getOadrReportRequest().addAll(getOadrReportRequestTypes(registeredReport.getReportRequestList()));
		
		return oadrRegisteredReportType;
	}
	
	public OadrCreateReportType getOadrCreateReportType(CreateReport createReport) throws DatatypeConfigurationException{
		OadrCreateReportType oadrCreateReportType = FactoryHelper.getOadrObjectFactory().createOadrCreateReportType();
		oadrCreateReportType.setRequestID(createReport.getRequestId());
		oadrCreateReportType.setSchemaVersion(createReport.getSchemaVersion());
		oadrCreateReportType.setVenID(createReport.getVenId());
		oadrCreateReportType.getOadrReportRequest().addAll(getOadrReportRequestTypes(createReport.getReportRequestList()));
		
		return oadrCreateReportType;
	}
	
	public OadrCreatedReportType getOadrCreatedReportType(CreatedReport createdReport) throws DatatypeConfigurationException{
		OadrCreatedReportType oadrCreatedReportType = FactoryHelper.getOadrObjectFactory().createOadrCreatedReportType();
		oadrCreatedReportType.setEiResponse(getEiResponseType(createdReport.getResponse()));
		oadrCreatedReportType.setSchemaVersion(createdReport.getSchemaVersion());
		oadrCreatedReportType.setVenID(createdReport.getVenId());
		oadrCreatedReportType.setOadrPendingReports(getOadrPendingReportsType(createdReport.getPendingReports()));
		
		return oadrCreatedReportType;
	}
	
	public OadrUpdateReportType getOadrUpdateReportType(UpdateReport updateReport) throws DatatypeConfigurationException{
		OadrUpdateReportType oadrUpdateReportType = FactoryHelper.getOadrObjectFactory().createOadrUpdateReportType();
		oadrUpdateReportType.setRequestID(updateReport.getRequestId());
		oadrUpdateReportType.setSchemaVersion(updateReport.getSchemaVersion());
		oadrUpdateReportType.setVenID(updateReport.getVenId());
		oadrUpdateReportType.getOadrReport().addAll(getOadrReportType(updateReport.getReportList()));
		
		return oadrUpdateReportType;
	}
	
	public OadrUpdatedReportType getOadrUpdatedReportType(UpdatedReport updatedReport){
		OadrUpdatedReportType oadrUpdatedReportType = FactoryHelper.getOadrObjectFactory().createOadrUpdatedReportType();
		oadrUpdatedReportType.setSchemaVersion(updatedReport.getSchemaVersion());
		oadrUpdatedReportType.setVenID(updatedReport.getVenId());
		oadrUpdatedReportType.setEiResponse(getEiResponseType(updatedReport.getResponse()));
		oadrUpdatedReportType.setOadrCancelReport(getOadrCancelReportType(updatedReport.getCancelReport()));
		
		return oadrUpdatedReportType;
	}
	
	public OadrCancelReportType getOadrCancelReportType(CancelReport cancelReport){
		if(null != cancelReport){
			OadrCancelReportType oadrCancelReportType = FactoryHelper.getOadrObjectFactory().createOadrCancelReportType();
			//TODO report to follow not available inside the cancelreport object
			oadrCancelReportType.setReportToFollow(cancelReport.isReportToFollow());
			oadrCancelReportType.setRequestID(cancelReport.getRequestId());
			oadrCancelReportType.setSchemaVersion(cancelReport.getSchemaVersion());
			oadrCancelReportType.setVenID(cancelReport.getVenId());
			
			//TODO cancel report doesn't allow multiple requestIds
			oadrCancelReportType.getReportRequestID().addAll(cancelReport.getReportRequestId());
			
			return oadrCancelReportType;
		}else{
			return null;
		}
	}
	

	public OadrCanceledReportType getOadrCanceledReportType(CanceledReport canceledReport){
		OadrCanceledReportType oadrCanceledReportType = FactoryHelper.getOadrObjectFactory().createOadrCanceledReportType();
		oadrCanceledReportType.setSchemaVersion(canceledReport.getSchemaVersion());
		oadrCanceledReportType.setVenID(canceledReport.getVenId());
		oadrCanceledReportType.setEiResponse(getEiResponseType(canceledReport.getResponse()));
		oadrCanceledReportType.setOadrPendingReports(getOadrPendingReportsType(canceledReport.getPendingReports()));
				
		return oadrCanceledReportType;
	}
	
	///////////////////////////////////////
	//Report Service utility payloads
	///////////////////////////////////////

	public OadrPendingReportsType getOadrPendingReportsType(PendingReports pendingReports){
		OadrPendingReportsType oadrPendingReportsType = FactoryHelper.getOadrObjectFactory().createOadrPendingReportsType();
		if(null != pendingReports){
			if(null != pendingReports.getReportRequestIdList()){
				oadrPendingReportsType.getReportRequestID().addAll(pendingReports.getReportRequestIdList());
			}
		}
		return oadrPendingReportsType;
	}

	public List<OadrReportType> getOadrReportType(List<Report> reports) throws DatatypeConfigurationException{
		List<OadrReportType> oadrReportTypes = new ArrayList<OadrReportType>();
		
		if(null != reports){
			for(Report report: reports){
				OadrReportType oadrReportType = FactoryHelper.getOadrObjectFactory().createOadrReportType();
				oadrReportType.setCreatedDateTime(XMLGregorianCalendarConverter.asXMLGregorianCalendar(report.getCreatedDate()));
				oadrReportType.setDtstart(getDtstart(report.getStart()));
				oadrReportType.setDuration(getDurationPropType(report.getDuration()));
				oadrReportType.setEiReportID(report.getReportId());
				oadrReportType.setIntervals(getReportIntervals(report.getIntervalList()));
				oadrReportType.setReportName(report.getReportName());
				oadrReportType.setReportRequestID(report.getReportRequestId());
				oadrReportType.setReportSpecifierID(report.getReportSpecifierId());
				oadrReportType.getOadrReportDescription().addAll(getOadrReportDescriptionTypes(report.getReportDescriptionList()));
				oadrReportTypes.add(oadrReportType);
			}
		}
		return oadrReportTypes;
	}
	
	public Intervals getReportIntervals(List<ReportInterval> reportIntervals) throws DatatypeConfigurationException{
		Intervals intervals = FactoryHelper.getOadrObjectFactory().createIntervals();
		for(ReportInterval reportInterval: reportIntervals){
			IntervalType intervalType = FactoryHelper.getOadrObjectFactory().createIntervalType();
			intervalType.setDtstart(getDtstart(reportInterval.getStart()));
			intervalType.setDuration(getDurationPropType(reportInterval.getDuration()));
			Uid uid= FactoryHelper.getCalObjectFactory().createUid();
			uid.setText(reportInterval.getuId());
			intervalType.setUid(uid);		   
			   			
			for(PayloadBase payloads: reportInterval.getPayloadList()){
				OadrReportPayloadType oadrReportPayloadType = FactoryHelper.getOadrObjectFactory().createOadrReportPayloadType();
				oadrReportPayloadType.setOadrDataQuality(payloads.getDataQuality());
				oadrReportPayloadType.setRID(payloads.getrId());
				/*PayloadFloatType base = FactoryHelper.getOadrObjectFactory().createPayloadFloatType();
				base.setValue(payloads.getValue());*/
				oadrReportPayloadType.setPayloadBase(getPayloadBase(payloads));						
				intervalType.getStreamPayloadBase().add(FactoryHelper.getOadrObjectFactory().createOadrReportPayload(oadrReportPayloadType));
						
				}
				
			
			intervals.getInterval().add(intervalType);
		}
		return intervals;
	}
	
	public List<OadrReportDescriptionType> getOadrReportDescriptionTypes(List<ReportDescription> reportDescriptions){
		List<OadrReportDescriptionType> oadrReportDescriptionTypes = new ArrayList<OadrReportDescriptionType>();
		for(ReportDescription reportDescription : reportDescriptions){
			OadrReportDescriptionType oadrReportDescriptionType = FactoryHelper.getOadrObjectFactory().createOadrReportDescriptionType();
			oadrReportDescriptionType.setMarketContext(reportDescription.getMarketContext());
			OadrSamplingRateType oadrSamplingRateType = new OadrSamplingRateType();
			oadrSamplingRateType.setOadrMaxPeriod(getDurationPropType(reportDescription.getSamplingMaxPeriod()).getDuration());
			oadrSamplingRateType.setOadrMinPeriod(getDurationPropType(reportDescription.getSamplingMinPeriod()).getDuration());
			//TODO No field equivalent on dras side
			oadrSamplingRateType.setOadrOnChange(false);
			oadrReportDescriptionType.setOadrSamplingRate(oadrSamplingRateType);
			oadrReportDescriptionType.setReadingType(reportDescription.getReadingType());
			oadrReportDescriptionType.setReportDataSource(getEiTargetType(reportDescription.getReportDataSource()));
			JAXBElement<ItemBaseType> oadrItemBase = FactoryHelper.
					getOadrObjectFactory().createItemBase(getItemBase(reportDescription.getItemBase()));
			if(null != oadrItemBase && null != oadrItemBase.getValue()){
				oadrReportDescriptionType.setItemBase(oadrItemBase);
			}				
			//oadrReportDescriptionType.setReportSubject(getEiTargetType(reportDescription.getReportSubject()));
			oadrReportDescriptionType.setReportType(reportDescription.getReportType());
			oadrReportDescriptionType.setRID(reportDescription.getrId());
			
			oadrReportDescriptionTypes.add(oadrReportDescriptionType);
		}
		return oadrReportDescriptionTypes;
	}
	
	public List<OadrReportRequestType> getOadrReportRequestTypes(List<ReportRequest> reportRequestTypes) throws DatatypeConfigurationException{
		List<OadrReportRequestType> oadrReportRequestTypes = new ArrayList<OadrReportRequestType>();
		if(null != reportRequestTypes){
			for(ReportRequest reportRequest: reportRequestTypes){
				OadrReportRequestType oadrReportRequestType = FactoryHelper.getOadrObjectFactory().createOadrReportRequestType();
				oadrReportRequestType.setReportRequestID(reportRequest.getReportRequestId());
				ReportSpecifier reportSpecifier = reportRequest.getReportSpecifier();
				if(null != reportSpecifier){
					oadrReportRequestType.setReportSpecifier(getReportSpecifierType(reportSpecifier));
				}else{
					oadrReportRequestType.setReportSpecifier(null);
				}
				
				oadrReportRequestTypes.add(oadrReportRequestType);
			}
		}
		return oadrReportRequestTypes;
	}
	
	private ReportSpecifierType getReportSpecifierType(ReportSpecifier reportSpecifier) throws DatatypeConfigurationException{
		ReportSpecifierType oadrReportSpecifierType = FactoryHelper.getOadrObjectFactory().createReportSpecifierType();
		if(null != reportSpecifier){
			oadrReportSpecifierType.setGranularity(getDurationPropType(reportSpecifier.getGranularity()));
			oadrReportSpecifierType.setReportBackDuration(getDurationPropType(reportSpecifier.getReportBackDuration()));
			oadrReportSpecifierType.setReportInterval(getWsCalendarIntervalType(reportSpecifier.getReportInterval())); 
			oadrReportSpecifierType.setReportSpecifierID(reportSpecifier.getReportSpecifierId());
			oadrReportSpecifierType.getSpecifierPayload().addAll(
					getSpecifierPayloadType(reportSpecifier.getSpecifierPayloadList()));
		}
		return oadrReportSpecifierType;
	}
	
	
	private List<SpecifierPayloadType> getSpecifierPayloadType(List<SpecifierPayload> specifierPayloads){
		List<SpecifierPayloadType> oadrSpecifierPayloadTypes = new ArrayList<SpecifierPayloadType>();
		for(SpecifierPayload specifierPayload:  specifierPayloads){
			SpecifierPayloadType oadrSpecifierPayloadType = FactoryHelper.getOadrObjectFactory().createSpecifierPayloadType();
			JAXBElement<ItemBaseType> oadrItemBase = FactoryHelper.
					getOadrObjectFactory().createItemBase(getItemBase(specifierPayload.getItemBase()));
			if(null != oadrItemBase && null != oadrItemBase.getValue()){
				oadrSpecifierPayloadType.setItemBase(oadrItemBase);
			}
			oadrSpecifierPayloadType.setReadingType(specifierPayload.getReadingType());
			oadrSpecifierPayloadType.setRID(specifierPayload.getrId());				
			
			oadrSpecifierPayloadTypes.add(oadrSpecifierPayloadType);
		}
		return oadrSpecifierPayloadTypes;
	}
	
private JAXBElement<? extends PayloadBaseType> getPayloadBase (PayloadBase payloads){
		
		if(payloads instanceof ReportDataPayload){
			PayloadFloatType data = FactoryHelper.getOadrObjectFactory().createPayloadFloatType();
			data.setValue(payloads.getValue());
			return FactoryHelper.getOadrObjectFactory().createPayloadFloat(data);
			
		}else if(payloads instanceof ResourceStatusPayload){
			OadrPayloadResourceStatusType status = FactoryHelper.getOadrObjectFactory().createOadrPayloadResourceStatusType();
			status.setOadrManualOverride(payloads.getResourceStatus().getManualOverride());
			status.setOadrOnline(payloads.getResourceStatus().getOnline());	
			return FactoryHelper.getOadrObjectFactory().createOadrPayloadResourceStatus(status);
		}
		return null;
	}
	
	
	private ItemBaseType getItemBase(ItemBase itemBase){
		
		if(itemBase instanceof BaseUnit){
			BaseUnitType oadrBaseUnitType = FactoryHelper.getOadrObjectFactory().createBaseUnitType();
			oadrBaseUnitType.setItemDescription(itemBase.getItemDescription());
			oadrBaseUnitType.setItemUnits(itemBase.getItemUnits());
			oadrBaseUnitType.setSiScaleCode(itemBase.getSiScaleCode());
			return oadrBaseUnitType;
		}else if(itemBase instanceof Currency){
			CurrencyType oadrCurrencyType = FactoryHelper.getOadrObjectFactory().createCurrencyType();
			oadrCurrencyType.setItemDescription(CurrencyItemDescriptionType.fromValue(itemBase.getItemDescription()));
			oadrCurrencyType.setItemUnits(ISO3AlphaCurrencyCodeContentType.fromValue(itemBase.getItemUnits()));
			oadrCurrencyType.setSiScaleCode(itemBase.getSiScaleCode());
			return oadrCurrencyType;
		}else if(itemBase instanceof EnergyApparent){
			EnergyApparentType oadrEnergyApparentType = FactoryHelper.getOadrObjectFactory().createEnergyApparentType();
			oadrEnergyApparentType.setItemDescription(itemBase.getItemDescription());
			oadrEnergyApparentType.setItemUnits(itemBase.getItemUnits());
			oadrEnergyApparentType.setSiScaleCode(itemBase.getSiScaleCode());
			return oadrEnergyApparentType;
		}else if (itemBase instanceof EnergyReactive){
			EnergyReactiveType oadrEnergyReactiveType = FactoryHelper.getOadrObjectFactory().createEnergyReactiveType();
			oadrEnergyReactiveType.setItemDescription(itemBase.getItemDescription());
			oadrEnergyReactiveType.setItemUnits(itemBase.getItemUnits());
			oadrEnergyReactiveType.setSiScaleCode(itemBase.getSiScaleCode());
			return oadrEnergyReactiveType;
		}else if(itemBase instanceof EnergyReal){
			EnergyRealType oadrEnergyRealType = FactoryHelper.getOadrObjectFactory().createEnergyRealType();
			oadrEnergyRealType.setItemDescription(itemBase.getItemDescription());
			oadrEnergyRealType.setItemUnits(itemBase.getItemUnits());
			oadrEnergyRealType.setSiScaleCode(itemBase.getSiScaleCode());
			return oadrEnergyRealType;
		}else if(itemBase instanceof Frequency){
			FrequencyType oadrFrequencyType = FactoryHelper.getOadrObjectFactory().createFrequencyType();
			oadrFrequencyType.setItemDescription(itemBase.getItemDescription());
			oadrFrequencyType.setItemUnits(itemBase.getItemUnits());
			oadrFrequencyType.setSiScaleCode(itemBase.getSiScaleCode());
			return oadrFrequencyType;
		}else if(itemBase instanceof GBItemBase){
			GBItemBase gbItemBase = (GBItemBase) itemBase;
			com.honeywell.ven.api.report.FeedType feedType = gbItemBase.getFeed();
			OadrGBItemBase oadrGBItemBase = FactoryHelper.getOadrObjectFactory().createOadrGBItemBase();
			FeedType oadrFeedType = FactoryHelper.getOadrObjectFactory().createFeedType();
			if(null != feedType){
				oadrFeedType.setBase(feedType.getBase());
				oadrFeedType.setLang(feedType.getLang());
				oadrGBItemBase.setFeed(oadrFeedType);
			}
			return oadrGBItemBase; 
		}else if(itemBase instanceof PowerApparent){
			PowerApparentType oadrPowerApparentType = FactoryHelper.getOadrObjectFactory().createPowerApparentType();
			oadrPowerApparentType.setItemDescription(itemBase.getItemDescription());
			oadrPowerApparentType.setItemUnits(itemBase.getItemUnits());
			oadrPowerApparentType.setSiScaleCode(itemBase.getSiScaleCode());
			return oadrPowerApparentType;
		}else if (itemBase instanceof PowerReactive){
			PowerReactiveType oadrPowerReactiveType = FactoryHelper.getOadrObjectFactory().createPowerReactiveType();
			oadrPowerReactiveType.setItemDescription(itemBase.getItemDescription());
			oadrPowerReactiveType.setItemUnits(itemBase.getItemUnits());
			oadrPowerReactiveType.setSiScaleCode(itemBase.getSiScaleCode());
			return oadrPowerReactiveType;
		}else if(itemBase instanceof PowerReal){
			PowerRealType oadrPowerRealType = FactoryHelper.getOadrObjectFactory().createPowerRealType();
			oadrPowerRealType.setItemDescription(itemBase.getItemDescription());
			oadrPowerRealType.setItemUnits(itemBase.getItemUnits());
			oadrPowerRealType.setSiScaleCode(itemBase.getSiScaleCode());
			return oadrPowerRealType;
		}else if(itemBase instanceof PulseCount){
			PulseCount pulseCount = (PulseCount) itemBase;
			PulseCountType oadrPulseCountType = FactoryHelper.getOadrObjectFactory().createPulseCountType();
			oadrPulseCountType.setItemDescription(pulseCount.getItemDescription());
			oadrPulseCountType.setItemUnits(pulseCount.getItemUnits());
			float value = Float.parseFloat(pulseCount.getSiScaleCode());
			oadrPulseCountType.setPulseFactor(value);
			return oadrPulseCountType;
		}else if(itemBase instanceof Temperature){
			TemperatureType oadrTemperatureType = FactoryHelper.getOadrObjectFactory().createTemperatureType();
			oadrTemperatureType.setItemDescription(itemBase.getItemDescription());
			Temperature temperature = (Temperature) itemBase;
			oadrTemperatureType.setItemUnits(TemperatureUnitType.fromValue(temperature.getItemUnits()));
			oadrTemperatureType.setSiScaleCode(itemBase.getSiScaleCode());
			return oadrTemperatureType;
		}else if(itemBase instanceof Therm){
			ThermType oadrThermType = FactoryHelper.getOadrObjectFactory().createThermType();
			oadrThermType.setItemDescription(itemBase.getItemDescription());
			oadrThermType.setItemUnits(itemBase.getItemUnits());
			oadrThermType.setSiScaleCode(itemBase.getSiScaleCode());
			return oadrThermType;
		}else if(itemBase instanceof Voltage){
			VoltageType oadrVoltageType = FactoryHelper.getOadrObjectFactory().createVoltageType();
			oadrVoltageType.setItemDescription(itemBase.getItemDescription());
			oadrVoltageType.setItemUnits(itemBase.getItemUnits());
			oadrVoltageType.setSiScaleCode(itemBase.getSiScaleCode());
			return oadrVoltageType;
		}
		
		return null;
	}
	
	public WsCalendarIntervalType getWsCalendarIntervalType(WsCalanderInterval wsCalanderInterval) throws DatatypeConfigurationException{
		WsCalendarIntervalType oadrWsCalendarIntervalType = FactoryHelper.getCalObjectFactory().createWsCalendarIntervalType();
		
		if(null != wsCalanderInterval){
			Properties oadrProperties = FactoryHelper.getCalObjectFactory().createProperties();
			oadrProperties.setDtstart(getDtstart(wsCalanderInterval.getStart()));
			oadrProperties.setDuration(getDurationPropType(wsCalanderInterval.getDuration()));
			if(null != wsCalanderInterval.getToleranceStartAfter()){
				oadrProperties.setTolerance(getTolerance(wsCalanderInterval.getToleranceStartAfter()));
			}
			if(null != wsCalanderInterval.getNotification()){
				oadrProperties.setXEiNotification(getDurationPropType(wsCalanderInterval.getNotification()));
			}
			if(null != wsCalanderInterval.getRampUp()){
				oadrProperties.setXEiRampUp(getDurationPropType(wsCalanderInterval.getRampUp()));
			}
			if(null != wsCalanderInterval.getRecovery()){
				oadrProperties.setXEiRecovery(getDurationPropType(wsCalanderInterval.getRecovery()));
			}
			oadrWsCalendarIntervalType.setProperties(oadrProperties);
		}
		
		return oadrWsCalendarIntervalType;
	}
	
	public EiTargetType getEiTargetType(DeviceClass deviceClass){
		EiTargetType eiTargetType = FactoryHelper.getOadrObjectFactory().createEiTargetType();
		eiTargetType.getAggregatedPnode().addAll(getOadrAggregatedPnodeTypes(deviceClass.getAggregatedPnodeList()));
		eiTargetType.getEndDeviceAsset().addAll(getOadrEndDeviceAssetTypes(deviceClass.getEndDeviceAssetList()));
		eiTargetType.getGroupID().addAll(deviceClass.getGroupIdList());
		eiTargetType.getGroupName().addAll(deviceClass.getGroupNameList());
		eiTargetType.getMeterAsset().addAll(getOadrMeterAssetTypes(deviceClass.getMeterAssetList()));
		eiTargetType.getPartyID().addAll(deviceClass.getPartyIdList());
		eiTargetType.getPnode().addAll(getOadrPnodeTypes(deviceClass.getPnodeList()));
		eiTargetType.getResourceID().addAll(deviceClass.getResourceIdList());
		eiTargetType.getServiceArea().addAll(getOadrServiceAreaTypes(deviceClass.getServiceAreaList()));
		eiTargetType.getServiceDeliveryPoint().addAll(getOadrServiceDeliveryPointTypes(deviceClass.getServiceDeliveryPointList()));
		eiTargetType.getServiceLocation().addAll(getOadrServiceLocationTypes(deviceClass.getServiceLocationList()));
		eiTargetType.getTransportInterface().addAll(getOadrTransportInterfaceTypes(deviceClass.getTransportInterfaceList())); 
		eiTargetType.getVenID().addAll(deviceClass.getVenIdList());
		return eiTargetType;
	}
	
	
	public List<AggregatedPnodeType> getOadrAggregatedPnodeTypes(List<String> lst){
		List<AggregatedPnodeType> lstAggregatedPnodeTypes = new ArrayList<AggregatedPnodeType>();
		if (lst == null) {
			return lstAggregatedPnodeTypes;
		}
		for(String type: lst){
			AggregatedPnodeType aggregatedPnodeType = FactoryHelper.getOadrObjectFactory().createAggregatedPnodeType();
			aggregatedPnodeType.setNode(type);
			lstAggregatedPnodeTypes.add(aggregatedPnodeType);
		}
		return lstAggregatedPnodeTypes;
	}
	
	public List<EndDeviceAssetType> getOadrEndDeviceAssetTypes(List<String> lst){
		List<EndDeviceAssetType> lstEndDeviceAssetTypes = new ArrayList<EndDeviceAssetType>();
		if (lst == null) {
			return lstEndDeviceAssetTypes;
		}
		for(String type: lst){
			EndDeviceAssetType endDeviceAssetType = FactoryHelper.getOadrObjectFactory().createEndDeviceAssetType();
			endDeviceAssetType.setMrid(type);
			lstEndDeviceAssetTypes.add(endDeviceAssetType);
		}
		return lstEndDeviceAssetTypes;
	}
	
	public List<MeterAssetType> getOadrMeterAssetTypes(List<String> lst){
		List<MeterAssetType> lstMeterAssetTypes = new ArrayList<MeterAssetType>();
		if (lst == null) {
			return lstMeterAssetTypes;
		}
		for(String type: lst){
			MeterAssetType meterAssetType = FactoryHelper.getOadrObjectFactory().createMeterAssetType();
			meterAssetType.setMrid(type);
			lstMeterAssetTypes.add(meterAssetType);
		}
		return lstMeterAssetTypes;
	}
	
	public List<PnodeType> getOadrPnodeTypes(List<String> lst){
		List<PnodeType> lstPnodeTypes = new ArrayList<PnodeType>();
		if (lst == null) {
			return lstPnodeTypes;
		}
		for(String type: lst){
			PnodeType pnodeType = FactoryHelper.getOadrObjectFactory().createPnodeType();
			pnodeType.setNode(type);
			lstPnodeTypes.add(pnodeType);
		}
		return lstPnodeTypes;
	}
	
	//TODO Not Done
	public List<ServiceAreaType> getOadrServiceAreaTypes(List<ServiceArea> lst){
		List<ServiceAreaType> oadrServiceAreaTypes = new ArrayList<ServiceAreaType>();
		//ServiceArea Type in Oadr side has 1:1 relation with feature collection
		//But on the dras side we have 1:n relation with the feature collection
		if (lst == null) {
			return null;
		}
		
		log.error("ServiceAreaTypes not mapped in PayloadGenerator file !!!!");
		
		return oadrServiceAreaTypes;
	}
	//TODO Not Done
	public List<ServiceDeliveryPointType> getOadrServiceDeliveryPointTypes(List<ServiceDeliveryPoint> lst){
		if (lst == null) {
			return null;
		}
		List<ServiceDeliveryPointType> oadrServiceDeliveryPointTypes = new ArrayList<ServiceDeliveryPointType>();
		for(ServiceDeliveryPoint serviceDeliveryPoint: lst){
			//TODO the serviceDeliveryPoint type has only one Node in Oadr. But on dras side it is a list
			log.error("ServiceDeliveryPointType not mapped in PayloadGenerator file !!!!");
			List<String> nodeList = serviceDeliveryPoint.getNodeList();
			for(String node: nodeList){
				ServiceDeliveryPointType oadrServiceDeliveryPointType = 
						FactoryHelper.getOadrObjectFactory().createServiceDeliveryPointType();
				oadrServiceDeliveryPointType.setNode(node);
				oadrServiceDeliveryPointTypes.add(oadrServiceDeliveryPointType);
			}
		}
		return oadrServiceDeliveryPointTypes;
	}
	//TODO Not Done
	public List<ServiceLocationType> getOadrServiceLocationTypes(List<ServiceLocation> lst){
		if (lst == null) {
			return null;
		}
		List<ServiceLocationType> oadrServiceLocationTypes = new ArrayList<ServiceLocationType>();
		for(ServiceLocation serviceLocation:lst){
			//TODO ServiceLocation has 1:n with feature collection
			//On oadr it 1:1 with the feature collection
			log.error("ServiceLocationType not mapped in PayloadGenerator file !!!!");
			
			ServiceLocationType oadrServiceLocationType = FactoryHelper.getOadrObjectFactory().createServiceLocationType();
			oadrServiceLocationType.setFeatureCollection(null);
			oadrServiceLocationTypes.add(oadrServiceLocationType);
		}
		return oadrServiceLocationTypes;
	}
	
	public List<TransportInterfaceType> getOadrTransportInterfaceTypes(List<TransportInterface> lst){
		if (lst == null) {
			return null;
		}
		List<TransportInterfaceType> oadrTransportInterfaceTypes = new ArrayList<TransportInterfaceType>();
		for(TransportInterface transportInterface: lst){
			TransportInterfaceType oadrTransportInterfaceType = FactoryHelper.getOadrObjectFactory().createTransportInterfaceType();
			oadrTransportInterfaceType.setPointOfDelivery(transportInterface.getPointOfDelivery());
			oadrTransportInterfaceType.setPointOfReceipt(transportInterface.getPointOfReceipt());
			
			oadrTransportInterfaceTypes.add(oadrTransportInterfaceType);
		}
		return oadrTransportInterfaceTypes;
	}
	
	private Dtstart getDtstart(Date start) {
		Dtstart dtstart = FactoryHelper.getCalObjectFactory().createDtstart();
		XMLGregorianCalendar cal = null;
		try {
			cal = XMLGregorianCalendarConverter.getCal(start);
		} catch (DatatypeConfigurationException e) {
			log.error("Exception in converting date to XmlGregorianCalendar !!"  +e);
		}
		dtstart.setDateTime(cal);
		
		return dtstart;
	}
	
	private XMLGregorianCalendar getZuluGregorianCal(Date start) {
		XMLGregorianCalendar cal = null;
		try {
			cal = XMLGregorianCalendarConverter.getCal(start);
		} catch (DatatypeConfigurationException e) {
			log.error("Exception in converting date to XmlGregorianCalendar !!"  +e);
		}
		
		return cal;
	}
	
	private DurationPropType getDurationPropType(long duration) {
		DurationPropType dpt = FactoryHelper.getCalObjectFactory().createDurationPropType();
		dpt.setDuration(DurationUtil.convert(duration));
		return dpt;
	}
	
	private Tolerance getTolerance(long toleranceStartAfter){
		Tolerance tol = FactoryHelper.getCalObjectFactory()
				.createPropertiesTolerance();
		Tolerate tolerate = FactoryHelper.getCalObjectFactory()
				.createPropertiesToleranceTolerate();
		//tolerate.setStartafter(getDurationString(event.getStartAfter() * 1000));
		tolerate.setStartafter(DurationUtil.convert(toleranceStartAfter * 1000));
		tol.setTolerate(tolerate);
	return tol;
	
	}
/*
	public OadrCanceledOptType getOadrCanceledOptType(CanceledOpt rr) {
		// TODO Auto-generated method stub
		return null;
	}

	public OadrCreatedOptType getOadrCreatedOptType(CreatedOpt rr) {
		// TODO Auto-generated method stub
		return null;
	}*/

	public OadrRequestReregistrationType getOadrRequestReregistrationType(
			ReRegistration rr) {
		OadrRequestReregistrationType oadrRequestReregistrationType = new OadrRequestReregistrationType();
		oadrRequestReregistrationType.setSchemaVersion(rr.getSchemaVersion());
		oadrRequestReregistrationType.setVenID(rr.getVenId());
		return oadrRequestReregistrationType;
	}

	// ///////////////////////////////////////////////////
	// Registration Services
	// ///////////////////////////////////////////////////
	
	public OadrCreatePartyRegistrationType getOadrCreatePartyRegistrationType (
			CreatePartyRegistration createPartyRegistration) throws DatatypeConfigurationException{
		OadrCreatePartyRegistrationType res = FactoryHelper.getOadrObjectFactory().createOadrCreatePartyRegistrationType();

		if (createPartyRegistration != null) {
			
			res.setRegistrationID(createPartyRegistration.getRegistrationId());
			res.setSchemaVersion(createPartyRegistration.getSchemaVersion());
			res.setVenID(createPartyRegistration.getVenId());
			res.setOadrProfileName("2.0b"); //Hardcoded to profile b
			res.setOadrReportOnly(createPartyRegistration.getReportOnly());
			res.setOadrXmlSignature(createPartyRegistration.getXmlSignature());
			res.setOadrVenName(createPartyRegistration.getVenName());
			res.setOadrHttpPullModel(createPartyRegistration.getHttpPullMode());
			
			String regId = REQUEST_ID_PREFIX + UUID.randomUUID().toString();
			//Dynamic ID
			res.setRequestID(regId);
			
			// Only Simple Http it is not configurable
			res.setOadrTransportName(OadrTransportType.SIMPLE_HTTP);
			
			}
		return res;
		}
	
	
	
	

	public OadrCreatedPartyRegistrationType getOadrCreatedPartyRegistrationType(
			CreatedPartyRegistration createPartyRegistration) {
		OadrCreatedPartyRegistrationType res = FactoryHelper.getOadrObjectFactory().createOadrCreatedPartyRegistrationType();

		if (createPartyRegistration != null) {
			res.setEiResponse(this.getEiResponseType(createPartyRegistration.getResponse()));
			res.setRegistrationID(createPartyRegistration.getRegistrationId());
			res.setSchemaVersion(createPartyRegistration.getSchemaVersion());
			res.setVenID(createPartyRegistration.getVenId());
			res.setVtnID(createPartyRegistration.getVtnId());
			res.setOadrRequestedOadrPollFreq(getDurationPropType(createPartyRegistration.getPollFrequency()));
			
			if (createPartyRegistration.getProfiles() != null) {
				OadrProfiles oProfs = FactoryHelper.getOadrObjectFactory().createOadrProfiles();
				for (Profile prof : createPartyRegistration.getProfiles()) {
					OadrProfile oProf = FactoryHelper.getOadrObjectFactory().createOadrProfilesOadrProfile();
					oProf.setOadrProfileName(prof.getProfileName().value());

					OadrTransports oTrans = FactoryHelper.getOadrObjectFactory().createOadrTransports();
					for (TransportName trans : prof.getTransports()) {
						OadrTransport oTran = FactoryHelper.getOadrObjectFactory().createOadrTransportsOadrTransport();
						oTran.setOadrTransportName(OadrTransportType.fromValue(trans.value()));
						oTrans.getOadrTransport().add(oTran);
					}
					oProf.setOadrTransports(oTrans);
					oProfs.getOadrProfile().add(oProf);
				}
				
				res.setOadrProfiles(oProfs);
			}
		}

		return res;
	}
	
	public OadrCancelPartyRegistrationType getOadrCancelPartyRegistrationType(
			CancelPartyRegistration cancelPartyRegistration) {
		OadrCancelPartyRegistrationType res = FactoryHelper.getOadrObjectFactory().createOadrCancelPartyRegistrationType();

		if (cancelPartyRegistration != null) {
			res.setRegistrationID(cancelPartyRegistration.getRegistrationId());
			res.setRequestID(cancelPartyRegistration.getRequestId());
			res.setSchemaVersion(cancelPartyRegistration.getSchemaVersion());
			res.setVenID(cancelPartyRegistration.getVenId());
		}

		return res;
	}

	public OadrCanceledPartyRegistrationType getOadrCanceledPartyRegistrationType(
			CanceledPartyRegistration canceledPartyRegistration) {
		OadrCanceledPartyRegistrationType res = FactoryHelper.getOadrObjectFactory().createOadrCanceledPartyRegistrationType();

		if (canceledPartyRegistration != null) {
			res.setEiResponse(this.getEiResponseType(canceledPartyRegistration.getResponse()));
			res.setRegistrationID(canceledPartyRegistration.getRegistrationId());
			res.setSchemaVersion(canceledPartyRegistration.getSchemaVersion());
			res.setVenID(canceledPartyRegistration.getVenId());
		}

		return res;
	}

	// ///////////////////////////////////////////////////
	// END of Party Registration Service methods
	// ///////////////////////////////////////////////////

	// ///////////////////////////////////////////////////
	// Event Services
	// ///////////////////////////////////////////////////

	public OadrDistributeEventType getOadrDistributeEvent(DistributeEvent de) {
		OadrDistributeEventType res = FactoryHelper.getOadrObjectFactory().createOadrDistributeEventType();
		
		String vtnId = System.getProperty(VTN_ID_PROPERTY);
		if (vtnId == null || vtnId.isEmpty()) {
			log.error("DRAS VTN ID is not set");
		}
		
		if (de != null && vtnId != null) {
			res.setRequestID(de.getRequestId());
			res.setSchemaVersion(de.getSchemaVersion());
			res.setVtnID(vtnId);
			res.setEiResponse(this.getEiResponseType(de.getResponse()));
			
			if (de.getEventList() != null) {
				for (Event event : de.getEventList()) {
					
					OadrEvent oEvent = new OadrEvent();
					oEvent.setOadrResponseRequired(ResponseRequiredType.fromValue(event.getResponseRequired().value()));
					EiEventType eie = FactoryHelper.getOadrObjectFactory().createEiEventType();
					
					eie.setEventDescriptor(getEventDescriptorType(event));
					
					eie.setEiActivePeriod(getEiActivePeriod(event));
					
					eie.setEiEventSignals(getEiEventSignals(event.getSignals()));
					
					eie.setEiTarget(getEiTargetType(event));
					
					oEvent.setEiEvent(eie);
					
					res.getOadrEvent().add(oEvent);
				}
			}
		}
		
		return res;
	}
	
	public OadrCreatedEventType getOadrCreatedEventType(CreatedEvent createdEvent) throws DatatypeConfigurationException{
		OadrCreatedEventType oadrCreatedEventType = FactoryHelper.getOadrObjectFactory().createOadrCreatedEventType();
		oadrCreatedEventType.setSchemaVersion(createdEvent.getSchemaVersion());
		EiCreatedEvent eiCreatedEvent = FactoryHelper.getOadrObjectFactory().createEiCreatedEvent();
		eiCreatedEvent.setVenID(createdEvent.getVenId());
		eiCreatedEvent.setEiResponse(getEiResponseType(createdEvent.getResponse()));
		eiCreatedEvent.setEventResponses(getEventResponses(createdEvent.getEventResponseList()));
		oadrCreatedEventType.setEiCreatedEvent(eiCreatedEvent);
		return oadrCreatedEventType;
	}
	
	private EventResponses getEventResponses(List<EventResponse> eventResponseList){		
		EventResponses eventResponses = FactoryHelper.getOadrObjectFactory().createEventResponses();
		List<EventResponses.EventResponse> eventResponseTypes = new ArrayList<EventResponses.EventResponse>();
		if(null != eventResponseList){
		for (EventResponse eventResponse : eventResponseList) {			
			EventResponses.EventResponse eventResponseType =new EventResponses.EventResponse();
			eventResponseType.setRequestID(eventResponse.getRequestID());
			eventResponseType.setResponseCode(eventResponse.getResponseCode());
			eventResponseType.setResponseDescription(eventResponse.getDescription());
			eventResponseType.setOptType(getOadrOptType(eventResponse.getOptType()));
			eventResponseType.setQualifiedEventID(getQualifiedEventIDType(eventResponse.getEventID(),0L));
			eventResponseTypes.add(eventResponseType);
			}
		
		eventResponses.getEventResponse().addAll(eventResponseTypes);
	}
		return eventResponses;
		
	}
	
	private EventDescriptorType getEventDescriptorType(Event event) {
		EventDescriptorType eventDescriptor = FactoryHelper.getOadrObjectFactory().createEventDescriptorType();
		eventDescriptor.setCreatedDateTime(getZuluGregorianCal(event.getCreatedTime()));
		EiMarketContext mc = FactoryHelper.getOadrObjectFactory().createEventDescriptorTypeEiMarketContext();
		mc.setMarketContext(event.getMarketContext());
		eventDescriptor.setEiMarketContext(mc);
		eventDescriptor.setEventID(event.getEventID());
		eventDescriptor.setEventStatus(EventStatusEnumeratedType.fromValue(event.getEventStatus().value()));
		if (event.getModificationDate() != null) {
			eventDescriptor.setModificationDateTime(getZuluGregorianCal(event.getModificationDate()));
		}
		eventDescriptor.setModificationNumber(event.getModificationNumber());
		eventDescriptor.setModificationReason(event.getModificationReason());
		eventDescriptor.setPriority(event.getPriority());
		eventDescriptor.setTestEvent(event.getTest().toString());
		eventDescriptor.setVtnComment(event.getVtnComment());
		
		return eventDescriptor;
	}
	
	private EiTargetType getEiTargetType(Event event) {
		EiTargetType eiTargetType = FactoryHelper.getOadrObjectFactory().createEiTargetType();
		if (event.getTarget() != null) {
			if (event.getTarget().getAggregatedPnodeList() != null)
				eiTargetType.getAggregatedPnode().addAll(getOadrAggregatedPnodeTypes(event.getTarget().getAggregatedPnodeList()));
			
			if (event.getTarget().getEndDeviceAssetList() != null)
				eiTargetType.getEndDeviceAsset().addAll(getOadrEndDeviceAssetTypes(event.getTarget().getEndDeviceAssetList()));
			
			if (event.getTarget().getGroupIdList() != null)
				eiTargetType.getGroupID().addAll(event.getTarget().getGroupIdList());
			
			if (event.getTarget().getGroupNameList() != null)
				eiTargetType.getGroupName().addAll(event.getTarget().getGroupNameList());
			
			if (event.getTarget().getMeterAssetList() != null)
				eiTargetType.getMeterAsset().addAll(getOadrMeterAssetTypes(event.getTarget().getMeterAssetList()));
			
			if (event.getTarget().getPartyIdList() != null)
				eiTargetType.getPartyID().addAll(event.getTarget().getPartyIdList());
			
			if (event.getTarget().getPnodeList() != null)
				eiTargetType.getPnode().addAll(getOadrPnodeTypes(event.getTarget().getPnodeList()));
			
			if (event.getTarget().getResourceIdList() != null)
				eiTargetType.getResourceID().addAll(event.getTarget().getResourceIdList());
			
			if (event.getTarget().getServiceAreaList() != null)
				eiTargetType.getServiceArea().addAll(getOadrServiceAreaTypes(event.getTarget().getServiceAreaList()));
			
			if (event.getTarget().getServiceDeliveryPointList() != null)
				eiTargetType.getServiceDeliveryPoint().addAll(getOadrServiceDeliveryPointTypes(event.getTarget().getServiceDeliveryPointList()));
			
			if (event.getTarget().getServiceLocationList() != null)
				eiTargetType.getServiceLocation().addAll(getOadrServiceLocationTypes(event.getTarget().getServiceLocationList()));
			
			if (event.getTarget().getTransportInterfaceList() != null)
				eiTargetType.getTransportInterface().addAll(getOadrTransportInterfaceTypes(event.getTarget().getTransportInterfaceList())); 
			
			if (event.getTarget().getVenIdList() != null)
				eiTargetType.getVenID().addAll(event.getTarget().getVenIdList());
		}
		
		return eiTargetType;
	}
	
	
	private EiTargetType getEiTargetTypes(DeviceClass deviceClass) {
		EiTargetType eiTargetType = FactoryHelper.getOadrObjectFactory().createEiTargetType();
			if (deviceClass.getAggregatedPnodeList() != null)
				eiTargetType.getAggregatedPnode().addAll(getOadrAggregatedPnodeTypes(deviceClass.getAggregatedPnodeList()));
			
			if (deviceClass.getEndDeviceAssetList() != null)
				eiTargetType.getEndDeviceAsset().addAll(getOadrEndDeviceAssetTypes(deviceClass.getEndDeviceAssetList()));
			
			if (deviceClass.getGroupIdList() != null)
				eiTargetType.getGroupID().addAll(deviceClass.getGroupIdList());
			
			if (deviceClass.getGroupNameList() != null)
				eiTargetType.getGroupName().addAll(deviceClass.getGroupNameList());
			
			if (deviceClass.getMeterAssetList() != null)
				eiTargetType.getMeterAsset().addAll(getOadrMeterAssetTypes(deviceClass.getMeterAssetList()));
			
			if (deviceClass.getPartyIdList() != null)
				eiTargetType.getPartyID().addAll(deviceClass.getPartyIdList());
			
			if (deviceClass.getPnodeList() != null)
				eiTargetType.getPnode().addAll(getOadrPnodeTypes(deviceClass.getPnodeList()));
			
			if (deviceClass.getResourceIdList() != null)
				eiTargetType.getResourceID().addAll(deviceClass.getResourceIdList());
			
			if (deviceClass.getServiceAreaList() != null)
				eiTargetType.getServiceArea().addAll(getOadrServiceAreaTypes(deviceClass.getServiceAreaList()));
			
			if (deviceClass.getServiceDeliveryPointList() != null)
				eiTargetType.getServiceDeliveryPoint().addAll(getOadrServiceDeliveryPointTypes(deviceClass.getServiceDeliveryPointList()));
			
			if (deviceClass.getServiceLocationList() != null)
				eiTargetType.getServiceLocation().addAll(getOadrServiceLocationTypes(deviceClass.getServiceLocationList()));
			
			if (deviceClass.getTransportInterfaceList() != null)
				eiTargetType.getTransportInterface().addAll(getOadrTransportInterfaceTypes(deviceClass.getTransportInterfaceList())); 
			
			if (deviceClass.getVenIdList() != null)
				eiTargetType.getVenID().addAll(deviceClass.getVenIdList());
		
		return eiTargetType;
	}
	
	private EiEventSignalsType getEiEventSignals(List<Signal> signals) {
		EiEventSignalsType eiEventSignals = FactoryHelper.getOadrObjectFactory().createEiEventSignalsType();
		for (Signal signal : signals) {
			EiEventSignalType eiEventSignal = getEiEventSignal(signal);
			eiEventSignals.getEiEventSignal().add(eiEventSignal);
		}
		return eiEventSignals;
	}
	
	private EiEventSignalType getEiEventSignal(Signal signal) {
		EiEventSignalType eiEventSignal = FactoryHelper.getOadrObjectFactory().createEiEventSignalType();
		Float cv = signal.getCurrentValue();
		if(cv != null){
			CurrentValueType currentValue = getCurrentValue(cv);
			eiEventSignal.setCurrentValue(currentValue);
		}
		eiEventSignal.setSignalID(signal.getId());
		eiEventSignal.setSignalName(signal.getName());
		eiEventSignal.setSignalType(SignalTypeEnumeratedType.fromValue(signal.getType().value()));
		
		// see page 31 of the OpenADR 2.0b Profile Spec
		//TODO: this needs a separate method to handle all the types
		if ("ELECTRICITY_PRICE".equals(signal.getName()) && SignalTypeEnumeratedType.PRICE.equals(eiEventSignal.getSignalType())) {
			CurrencyType ct = FactoryHelper.getOadrObjectFactory().createCurrencyType();
			ct.setItemDescription(CurrencyItemDescriptionType.CURRENCY_PER_K_WH);
			ct.setItemUnits(ISO3AlphaCurrencyCodeContentType.USD);
			ct.setSiScaleCode("none");
			
			eiEventSignal.setItemBase(FactoryHelper.getOadrObjectFactory().createCurrencyPerKWh(ct));
		}
		else if ("ELECTRICITY_PRICE".equals(signal.getName()) && SignalTypeEnumeratedType.PRICE_RELATIVE.equals(eiEventSignal.getSignalType())) {
			CurrencyType ct = FactoryHelper.getOadrObjectFactory().createCurrencyType();
			ct.setItemDescription(CurrencyItemDescriptionType.CURRENCY_PER_K_WH);
			ct.setItemUnits(ISO3AlphaCurrencyCodeContentType.USD);
			ct.setSiScaleCode("none");
			
			eiEventSignal.setItemBase(FactoryHelper.getOadrObjectFactory().createCurrencyPerKWh(ct));
		}
		else if ("ENERGY_PRICE".equals(signal.getName()) && SignalTypeEnumeratedType.PRICE.equals(eiEventSignal.getSignalType())) {
			CurrencyType ct = FactoryHelper.getOadrObjectFactory().createCurrencyType();
			ct.setItemDescription(CurrencyItemDescriptionType.CURRENCY_PER_K_WH);
			ct.setItemUnits(ISO3AlphaCurrencyCodeContentType.USD);
			ct.setSiScaleCode("none");
			
			eiEventSignal.setItemBase(FactoryHelper.getOadrObjectFactory().createCurrencyPerKWh(ct));
		}
		else if ("ENERGY_PRICE".equals(signal.getName()) && SignalTypeEnumeratedType.PRICE_RELATIVE.equals(eiEventSignal.getSignalType())) {
			CurrencyType ct = FactoryHelper.getOadrObjectFactory().createCurrencyType();
			ct.setItemDescription(CurrencyItemDescriptionType.CURRENCY_PER_K_WH);
			ct.setItemUnits(ISO3AlphaCurrencyCodeContentType.USD);
			ct.setSiScaleCode("none");
			
			eiEventSignal.setItemBase(FactoryHelper.getOadrObjectFactory().createCurrencyPerKWh(ct));
		}
		else if ("DEMAND_CHARGE".equals(signal.getName()) && SignalTypeEnumeratedType.PRICE.equals(eiEventSignal.getSignalType())) {
			CurrencyType ct = FactoryHelper.getOadrObjectFactory().createCurrencyType();
			ct.setItemDescription(CurrencyItemDescriptionType.CURRENCY_PER_KW);
			ct.setItemUnits(ISO3AlphaCurrencyCodeContentType.USD);
			ct.setSiScaleCode("none");
			
			eiEventSignal.setItemBase(FactoryHelper.getOadrObjectFactory().createCurrencyPerKWh(ct));
		}
		else if ("DEMAND_CHARGE".equals(signal.getName()) && SignalTypeEnumeratedType.PRICE_RELATIVE.equals(eiEventSignal.getSignalType())) {
			CurrencyType ct = FactoryHelper.getOadrObjectFactory().createCurrencyType();
			ct.setItemDescription(CurrencyItemDescriptionType.CURRENCY_PER_KW);
			ct.setItemUnits(ISO3AlphaCurrencyCodeContentType.USD);
			ct.setSiScaleCode("none");
			
			eiEventSignal.setItemBase(FactoryHelper.getOadrObjectFactory().createCurrencyPerKWh(ct));
		}
		
		Intervals intervals = getIntervals(signal.getIntervals());
		eiEventSignal.setIntervals(intervals);
		if(signal.getTarget() != null) {
			EiTargetType eiTargetType = getEiTargetTypes(signal.getTarget());
			eiEventSignal.setEiTarget(eiTargetType);
		}		
		return eiEventSignal;
	}
	
//	private JAXBElement<ItemBaseType> getItemBase(String signalName, SignalTypeEnumeratedType signalType) {
//		JAXBElement<ItemBaseType> oadrItemBase = null;
//		
//		if ("ELECTRICITY_PRICE".equals(signal.getName()) && SignalTypeEnumeratedType.PRICE.equals(eiEventSignal.getSignalType())) {
//			CurrencyType ct = FactoryHelper.getOadrObjectFactory().createCurrencyType();
//			ct.setItemDescription(CurrencyItemDescriptionType.CURRENCY_PER_K_WH);
//			ct.setItemUnits(ISO3AlphaCurrencyCodeContentType.USD);
//			ct.setSiScaleCode("none");
//			
//			oadrItemBase = FactoryHelper.getOadrObjectFactory().createCurrencyPerKWh(ct);
//		}
//		
//		return oadrItemBase;
//	}
	
	private CurrentValueType getCurrentValue(float value) {
		CurrentValueType currentValue = FactoryHelper.getOadrObjectFactory().createCurrentValueType();
		PayloadFloatType payloadFloatType = getPayloadFloat(value);
		currentValue.setPayloadFloat(payloadFloatType);
		return currentValue;
	}

	private PayloadFloatType getPayloadFloat(float value) {
		PayloadFloatType payloadFloatType = FactoryHelper.getOadrObjectFactory().createPayloadFloatType();
		payloadFloatType.setValue(value);
		return payloadFloatType;
	}

	private Intervals getIntervals(List<EventInterval> intervals) {
		Intervals result = FactoryHelper.getOadrObjectFactory().createIntervals();
		for (EventInterval interval : intervals) {
			IntervalType i = getInterval(interval);
			result.getInterval().add(i);
		}
		return result;
	}

	private IntervalType getInterval(EventInterval interval) {
		IntervalType i = FactoryHelper.getOadrObjectFactory().createIntervalType();
//		i.setDtstart(this.getDtstart(interval.getStart()));
		i.setDuration(this.getDurationPropType(interval.getDuration().longValue()));
		Uid uid = getUid(interval.getOffsetId());
		i.setUid(uid);

		SignalPayloadType payload = FactoryHelper.getOadrObjectFactory().createSignalPayloadType();
		PayloadFloatType base = FactoryHelper.getOadrObjectFactory().createPayloadFloatType();
		base.setValue(interval.getValue());
		payload.setPayloadBase(FactoryHelper.getOadrObjectFactory().createPayloadFloat(base));
				
		i.getStreamPayloadBase().add(FactoryHelper.getOadrObjectFactory().createSignalPayload(payload));
		
		return i;
	}
	
	private Uid getUid(String id) {
		Uid uid = FactoryHelper.getCalObjectFactory().createUid();
		uid.setText(id);
		return uid;
	}
	
	private EiActivePeriodType getEiActivePeriod(Event event)  {
		EiActivePeriodType eiActivePeriod = FactoryHelper.getOadrObjectFactory().createEiActivePeriodType();
		Properties p = FactoryHelper.getCalObjectFactory().createProperties();
		Dtstart dtstart = getDtstart(event.getStartTime());
		p.setDtstart(dtstart);
		DurationPropType dpt = getDurationPropType(event.getDuration().longValue());
		p.setDuration(dpt);
		DurationPropType not = getDurationPropType(event.getNotification().longValue());
		p.setXEiNotification(not);

		DurationPropType ramp = getDurationPropType(event.getRampUp().longValue());
		p.setXEiRampUp(ramp);

		//DurationPropType recovery = getDurationPropType(event.getRecovery());
		//p.setXEiRecovery(recovery);

		Tolerance tolerance = getTolerance(event.getToleranceStartAfter());
		p.setTolerance(tolerance );
		
		eiActivePeriod.setProperties(p);
		
		return eiActivePeriod;
	}
	
	protected String getDurationString(long d) {
		return "PT" + (d / 60000) + "M";
	}

	// ///////////////////////////////////////////////////
	// END of Event Services
	// ///////////////////////////////////////////////////
	
	// ///////////////////////////////////////////////////
	// OPT Service
	// ///////////////////////////////////////////////////
	
	public OadrCreateOptType getOadrCreateOptType(CreateOpt createOpt){
		OadrCreateOptType createOptType = FactoryHelper.getOadrObjectFactory().createOadrCreateOptType();
		createOptType.setRequestID(createOpt.getRequestId());
		//createOptType.setCreatedDateTime(createOpt.get)
		createOptType.setEiTarget(getEiTargetType(createOpt.getDeviceClass()));
		createOptType.setMarketContext(createOpt.getMarketContext());
		createOptType.setOadrDeviceClass(getEiTargetType(createOpt.getDeviceClass()));
		createOptType.setOptID(createOpt.getOptId());
		createOptType.setOptReason(createOpt.getOptReason());
		createOptType.setOptType(getOadrOptType(createOpt.getOptType()));
		createOptType.setQualifiedEventID(getQualifiedEventIDType(createOpt.getEventId(),0L)); // Modification number is not available , need to check with Sunil
		createOptType.setRequestID(createOpt.getRequestId());
		createOptType.setSchemaVersion(createOpt.getSchemaVersion());
		createOptType.setVavailability(getVavailabilityType(createOpt.getAvailibilityList()));
		createOptType.setVenID(createOpt.getVenId());
		return createOptType;
	}
	
	public OadrCreatedOptType getOadrCreatedOptType(CreatedOpt createdOpt){
		OadrCreatedOptType oadrCreatedOptType = FactoryHelper.getOadrObjectFactory().createOadrCreatedOptType();
		oadrCreatedOptType.setEiResponse(getEiResponseType(createdOpt.getResponse()));
		oadrCreatedOptType.setOptID(createdOpt.getOptId());
		oadrCreatedOptType.setSchemaVersion(createdOpt.getSchemaVersion());
		return oadrCreatedOptType;
	}
	public OadrCancelOptType getOadrCancelOptType(CancelOpt cancelOpt){
		OadrCancelOptType oadrCancelOptType = FactoryHelper.getOadrObjectFactory().createOadrCancelOptType();
		oadrCancelOptType.setOptID(cancelOpt.getOptId());         
		oadrCancelOptType.setRequestID(cancelOpt.getRequestId());
		oadrCancelOptType.setSchemaVersion(cancelOpt.getSchemaVersion());
		oadrCancelOptType.setVenID(cancelOpt.getVenId());
		return oadrCancelOptType;
	}
	public OadrCanceledOptType getOadrCanceledOptType(CanceledOpt canceledOpt) {
		OadrCanceledOptType oadrCanceledOptType = FactoryHelper.getOadrObjectFactory().createOadrCanceledOptType();
		oadrCanceledOptType.setEiResponse(getEiResponseType(canceledOpt.getResponse()));   
		oadrCanceledOptType.setOptID(canceledOpt.getOptId());
		oadrCanceledOptType.setSchemaVersion(canceledOpt.getSchemaVersion());
		return oadrCanceledOptType;
	}
	private VavailabilityType getVavailabilityType(List<Availibility> availibilityList){
		VavailabilityType vavailabilityType = FactoryHelper.getCalObjectFactory().createVavailabilityType();
		ArrayOfVavailabilityContainedComponents arrayOfVavailability = FactoryHelper.getCalObjectFactory().createArrayOfVavailabilityContainedComponents();
		for(Availibility availibility : availibilityList){
			arrayOfVavailability.getAvailable().add(getAvailableType(availibility));
		}
		vavailabilityType.setComponents(arrayOfVavailability);
		return vavailabilityType;
	}
	private AvailableType getAvailableType(Availibility availibility){
		AvailableType availableType = FactoryHelper.getCalObjectFactory().createAvailableType();
		Properties properties = FactoryHelper.getCalObjectFactory().createProperties();
		properties.setDtstart(getDtstart(availibility.getStartTime()));
		properties.setDuration(getDurationPropType(availibility.getDuration()));
		availableType.setProperties(properties);
		return availableType;
	}
	private OptTypeType getOadrOptType(OptType optType){
		String string = optType.value().toString();
		OptTypeType optTypeType = OptTypeType.fromValue(string);
		return optTypeType;
	}
    private QualifiedEventIDType getQualifiedEventIDType(String eventId , Long modificationNumber){
    	QualifiedEventIDType qualifiedEventIDType = FactoryHelper.getOadrObjectFactory().createQualifiedEventIDType();
    	qualifiedEventIDType.setEventID(eventId);
    	qualifiedEventIDType.setModificationNumber(modificationNumber);
    	
    	return qualifiedEventIDType;
    	
    }
    
    
    ///////////////////////////////////////////////////
   	// Poll Service
   	// ///////////////////////////////////////////////////
	public OadrPollType getoadrPollType(
			Poll pollRequest) throws DatatypeConfigurationException  {
		OadrPollType res = FactoryHelper.getOadrObjectFactory().createOadrPollType();

		if (pollRequest != null) {
		    res.setVenID(pollRequest.getVenId());
		}

		return res;
	}
    
    
    
    
    
    ///////////////////////////////////////////////////
   	// End of Poll Service
   	// ///////////////////////////////////////////////////
    
    
    
    ///////////////////////////////////////////////////
 	// Push Service
 	// ///////////////////////////////////////////////////
    public OadrPayload getOadrPayload(OadrRegisterReportType oadrRegisterReportType){
    	OadrPayload oadrPayload = new OadrPayload();
    	OadrSignedObject oadrSignedObject = new  OadrSignedObject();
    	oadrSignedObject.setOadrRegisterReport(oadrRegisterReportType);
		oadrPayload.setOadrSignedObject(oadrSignedObject);
		return oadrPayload;
    }
    
    public OadrPayload getOadrPayload(OadrCreatedReportType oadrCreatedReportType){
    	OadrPayload oadrPayload = new OadrPayload();
    	OadrSignedObject oadrSignedObject = new  OadrSignedObject();
    	oadrSignedObject.setOadrCreatedReport(oadrCreatedReportType);
		oadrPayload.setOadrSignedObject(oadrSignedObject);
		return oadrPayload;
    }
    
    public OadrPayload getOadrPayload(OadrRegisteredReportType oadrRegisteredReportType){
    	OadrPayload oadrPayload = new OadrPayload();
    	OadrSignedObject oadrSignedObject = new  OadrSignedObject();
    	oadrSignedObject.setOadrRegisteredReport(oadrRegisteredReportType);
		oadrPayload.setOadrSignedObject(oadrSignedObject);
		return oadrPayload;
    }
    public OadrPayload getOadrPayload(OadrCreateReportType oadrCreateReportType){
    	OadrPayload oadrPayload = new OadrPayload();
    	OadrSignedObject oadrSignedObject = new  OadrSignedObject();
    	oadrSignedObject.setOadrCreateReport(oadrCreateReportType);
		oadrPayload.setOadrSignedObject(oadrSignedObject);
		return oadrPayload;
    }
    public OadrPayload getOadrPayload(OadrUpdateReportType oadrUpdateReportType){
    	OadrPayload oadrPayload = new OadrPayload();
    	OadrSignedObject oadrSignedObject = new  OadrSignedObject();
    	oadrSignedObject.setOadrUpdateReport(oadrUpdateReportType);
		oadrPayload.setOadrSignedObject(oadrSignedObject);
		return oadrPayload;
    }
    public OadrPayload getOadrPayload(OadrCancelReportType oadrCancelReportType){
    	OadrPayload oadrPayload = new OadrPayload();
    	OadrSignedObject oadrSignedObject = new  OadrSignedObject();
    	oadrSignedObject.setOadrCancelReport(oadrCancelReportType);
		oadrPayload.setOadrSignedObject(oadrSignedObject);
		return oadrPayload;
    }
    public OadrPayload getOadrPayload(OadrDistributeEventType oadrDistributeEventType){
    	OadrPayload oadrPayload = new OadrPayload();
    	OadrSignedObject oadrSignedObject = new  OadrSignedObject();
    	oadrSignedObject.setOadrDistributeEvent(oadrDistributeEventType);
		oadrPayload.setOadrSignedObject(oadrSignedObject);
		return oadrPayload;
    }
    
    public OadrPayload getOadrPayload(OadrCreatedEventType oadrCreatedEventType){
    	OadrPayload oadrPayload = new OadrPayload();
    	OadrSignedObject oadrSignedObject = new  OadrSignedObject();
    	oadrSignedObject.setOadrCreatedEvent(oadrCreatedEventType);
		oadrPayload.setOadrSignedObject(oadrSignedObject);
		return oadrPayload;
    }

	public OadrPayload getOadrPayload(
			OadrCancelPartyRegistrationType oadrCancelPartyRegistrationType) {
		OadrPayload oadrPayload = new OadrPayload();
    	OadrSignedObject oadrSignedObject = new  OadrSignedObject();
    	oadrSignedObject.setOadrCancelPartyRegistration(oadrCancelPartyRegistrationType);
		oadrPayload.setOadrSignedObject(oadrSignedObject);
		return oadrPayload;
	}

	public OadrPayload getOadrPayload(
			OadrRequestReregistrationType oadrRequestReregistrationType) {
		OadrPayload oadrPayload = new OadrPayload();
    	OadrSignedObject oadrSignedObject = new  OadrSignedObject();
    	oadrSignedObject.setOadrRequestReregistration(oadrRequestReregistrationType);
		oadrPayload.setOadrSignedObject(oadrSignedObject);
		return oadrPayload;
	}
	
	public OadrPayload getOadrPayload(
			OadrCreatePartyRegistrationType OadrCreatePartyRegistrationType) {
		OadrPayload oadrPayload = new OadrPayload();
    	OadrSignedObject oadrSignedObject = new  OadrSignedObject();
    	oadrSignedObject.setOadrCreatePartyRegistration(OadrCreatePartyRegistrationType);
		oadrPayload.setOadrSignedObject(oadrSignedObject);
		return oadrPayload;
	}
	
	public OadrPayload getOadrPayload(
			OadrPollType oadrPollType) {
		OadrPayload oadrPayload = new OadrPayload();
    	OadrSignedObject oadrSignedObject = new  OadrSignedObject();
    	oadrSignedObject.setOadrPoll(oadrPollType);
		oadrPayload.setOadrSignedObject(oadrSignedObject);
		return oadrPayload;
	}
    
}
