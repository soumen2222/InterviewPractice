package com.qualitylogic.openadr.core.util;

import java.io.UnsupportedEncodingException;

import javax.xml.bind.JAXBException;

import org.restlet.engine.header.Header;
import org.restlet.util.Series;

import com.qualitylogic.openadr.core.bean.ModeType;
import com.qualitylogic.openadr.core.bean.ServiceType;
import com.qualitylogic.openadr.core.channel.util.StringUtil;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.exception.ValidationException;
import com.qualitylogic.openadr.core.signal.OadrCancelOptType;
import com.qualitylogic.openadr.core.signal.OadrCancelPartyRegistrationType;
import com.qualitylogic.openadr.core.signal.OadrCancelReportType;
import com.qualitylogic.openadr.core.signal.OadrCanceledOptType;
import com.qualitylogic.openadr.core.signal.OadrCanceledPartyRegistrationType;
import com.qualitylogic.openadr.core.signal.OadrCanceledReportType;
import com.qualitylogic.openadr.core.signal.OadrCreateOptType;
import com.qualitylogic.openadr.core.signal.OadrCreatePartyRegistrationType;
import com.qualitylogic.openadr.core.signal.OadrCreateReportType;
import com.qualitylogic.openadr.core.signal.OadrCreatedEventType;
import com.qualitylogic.openadr.core.signal.OadrCreatedOptType;
import com.qualitylogic.openadr.core.signal.OadrCreatedPartyRegistrationType;
import com.qualitylogic.openadr.core.signal.OadrCreatedReportType;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType;
import com.qualitylogic.openadr.core.signal.OadrPollType;
import com.qualitylogic.openadr.core.signal.OadrQueryRegistrationType;
import com.qualitylogic.openadr.core.signal.OadrRegisterReportType;
import com.qualitylogic.openadr.core.signal.OadrRegisteredReportType;
import com.qualitylogic.openadr.core.signal.OadrRequestEventType;
import com.qualitylogic.openadr.core.signal.OadrRequestReregistrationType;
import com.qualitylogic.openadr.core.signal.OadrResponseType;
import com.qualitylogic.openadr.core.signal.OadrUpdateReportType;
import com.qualitylogic.openadr.core.signal.OadrUpdatedReportType;
import com.qualitylogic.openadr.core.signal.helper.CancelOptConformanceValidationHelper;
import com.qualitylogic.openadr.core.signal.helper.CancelOptEventHelper;
import com.qualitylogic.openadr.core.signal.helper.CancelPartyRegistrationConfValHelper;
import com.qualitylogic.openadr.core.signal.helper.CancelPartyRegistrationHelper;
import com.qualitylogic.openadr.core.signal.helper.CancelReportConformanceValidationHelper;
import com.qualitylogic.openadr.core.signal.helper.CancelReportEventHelper;
import com.qualitylogic.openadr.core.signal.helper.CanceledOptConformanceValidationHelper;
import com.qualitylogic.openadr.core.signal.helper.CanceledOptEventHelper;
import com.qualitylogic.openadr.core.signal.helper.CanceledPartyConformanceValidationHelper;
import com.qualitylogic.openadr.core.signal.helper.CanceledPartyRegistrationHelper;
import com.qualitylogic.openadr.core.signal.helper.CanceledReportConformanceValidationHelper;
import com.qualitylogic.openadr.core.signal.helper.CanceledReportEventHelper;
import com.qualitylogic.openadr.core.signal.helper.CreateOptConformanceValidationHelper;
import com.qualitylogic.openadr.core.signal.helper.CreateOptEventHelper;
import com.qualitylogic.openadr.core.signal.helper.CreatePartyRegistrationConforValHelper;
import com.qualitylogic.openadr.core.signal.helper.CreatePartyRegistrationReqSignalHelper;
import com.qualitylogic.openadr.core.signal.helper.CreateReportConformanceValidationHelper;
import com.qualitylogic.openadr.core.signal.helper.CreateReportEventHelper;
import com.qualitylogic.openadr.core.signal.helper.CreatedEventConformanceValidationHelper;
import com.qualitylogic.openadr.core.signal.helper.CreatedEventHelper;
import com.qualitylogic.openadr.core.signal.helper.CreatedOptConformanceValidationHelper;
import com.qualitylogic.openadr.core.signal.helper.CreatedOptEventHelper;
import com.qualitylogic.openadr.core.signal.helper.CreatedPartyRegistrationConforValHelper;
import com.qualitylogic.openadr.core.signal.helper.CreatedPartyRegistrationSignalHelper;
import com.qualitylogic.openadr.core.signal.helper.CreatedReportConformanceValidationHelper;
import com.qualitylogic.openadr.core.signal.helper.CreatedReportEventHelper;
import com.qualitylogic.openadr.core.signal.helper.DistributeEventConformanceValidationHelper;
import com.qualitylogic.openadr.core.signal.helper.DistributeEventSignalHelper;
import com.qualitylogic.openadr.core.signal.helper.LogHelper;
import com.qualitylogic.openadr.core.signal.helper.PollConforValHelper;
import com.qualitylogic.openadr.core.signal.helper.PollEventSignalHelper;
import com.qualitylogic.openadr.core.signal.helper.QueryRegistrationSignalHelper;
import com.qualitylogic.openadr.core.signal.helper.RegisterReportConformanceValidationHelper;
import com.qualitylogic.openadr.core.signal.helper.RegisterReportEventHelper;
import com.qualitylogic.openadr.core.signal.helper.RegisteredReportConformanceValidationHelper;
import com.qualitylogic.openadr.core.signal.helper.RegisteredReportEventHelper;
import com.qualitylogic.openadr.core.signal.helper.RequestEventConformanceValidationHelper;
import com.qualitylogic.openadr.core.signal.helper.RequestEventSignalHelper;
import com.qualitylogic.openadr.core.signal.helper.RequestReRegistrationConforValHelper;
import com.qualitylogic.openadr.core.signal.helper.RequestReregistrationSignalHelper;
import com.qualitylogic.openadr.core.signal.helper.ResponseEventConformanceValidationHelper;
import com.qualitylogic.openadr.core.signal.helper.ResponseHelper;
import com.qualitylogic.openadr.core.signal.helper.SchemaHelper;
import com.qualitylogic.openadr.core.signal.helper.UpdateReportConformanceValidationHelper;
import com.qualitylogic.openadr.core.signal.helper.UpdateReportEventHelper;
import com.qualitylogic.openadr.core.signal.helper.UpdatedReportConformanceValidationHelper;
import com.qualitylogic.openadr.core.signal.helper.UpdatedReportEventHelper;

public class ConformanceRuleValidator {
	private static boolean disableAllConformanceCheck;
	static PropertiesFileReader properties = new PropertiesFileReader();

	public static boolean isDisableAllConformanceCheck() {
		return disableAllConformanceCheck;
	}

	private static boolean disableDistributeEvent_RequestIDPresent_Check;
	
	static String  p_disableDistributeEvent_RequestIDPresent_Check = properties.get("disableDistributeEvent_RequestIDPresent_Check");
	
	static{
		if(p_disableDistributeEvent_RequestIDPresent_Check!=null && p_disableDistributeEvent_RequestIDPresent_Check.equalsIgnoreCase("true")){
			disableDistributeEvent_RequestIDPresent_Check=true;
		}
	}
	
	
	private static boolean disableSchemaVersionValid_Check;
	static String  p_disableSchemaVersionValid_Check = properties.get("disableSchemaVersionValid_Check");
	
	static{
		if(p_disableSchemaVersionValid_Check!=null && p_disableSchemaVersionValid_Check.equalsIgnoreCase("true")){
			disableSchemaVersionValid_Check=true;
		}
	}
	
	private static boolean 	disableRegisterReport_VenReportNameValid_Check;
	static String  p_disableRegisterReport_VenReportNameValid_Check= properties.get("disableRegisterReport_VenReportNameValid_Check");
	static{
		if(p_disableRegisterReport_VenReportNameValid_Check!=null && p_disableRegisterReport_VenReportNameValid_Check.equalsIgnoreCase("true")){
			disableRegisterReport_VenReportNameValid_Check=true;
		}
	}
	
	
	private static boolean 	disableRegisterReport_RequestIDMatchPreviousCreateReport_Check;
	static String  p_disableRegisterReport_RequestIDMatchPreviousCreateReport_Check= properties.get("disableRegisterReport_RequestIDMatchPreviousCreateReport_Check");
	static{
		if(p_disableRegisterReport_RequestIDMatchPreviousCreateReport_Check!=null && p_disableRegisterReport_RequestIDMatchPreviousCreateReport_Check.equalsIgnoreCase("true")){
			disableRegisterReport_RequestIDMatchPreviousCreateReport_Check=true;
		}
	}
	
	private static boolean disableDistributeEvent_OnePayloadFloatPresentInEachInterval_Check;
	static String  p_disableDistributeEvent_OnePayloadFloatPresentInEachInterval_Check = properties.get("disableDistributeEvent_OnePayloadFloatPresentInEachInterval_Check");
	
	static{
		if(p_disableDistributeEvent_OnePayloadFloatPresentInEachInterval_Check!=null && p_disableDistributeEvent_OnePayloadFloatPresentInEachInterval_Check.equalsIgnoreCase("true")){
			disableDistributeEvent_OnePayloadFloatPresentInEachInterval_Check=true;
		}
	}
	
	
	private static boolean disable_UpdateReportLoadControlStateConsistentForAllIntervals_Check;
	static String  p_disable_UpdateReportLoadControlStateConsistentForAllIntervals_Check = properties.get("disable_UpdateReportLoadControlStateConsistentForAllIntervals_Check");
	
	static{
		if(p_disable_UpdateReportLoadControlStateConsistentForAllIntervals_Check!=null && p_disable_UpdateReportLoadControlStateConsistentForAllIntervals_Check.equalsIgnoreCase("true")){
			disable_UpdateReportLoadControlStateConsistentForAllIntervals_Check=true;
		}
	}
	
	
	private static boolean disable_UpdateReportTelemetryUsageReportIntervalDuration_Check;
	static String  p_disable_UpdateReportTelemetryUsageReportIntervalDuration_Check = properties.get("disable_UpdateReportTelemetryUsageReportIntervalDuration_Check");
	
	static{
		if(p_disable_UpdateReportTelemetryUsageReportIntervalDuration_Check!=null && p_disable_UpdateReportTelemetryUsageReportIntervalDuration_Check.equalsIgnoreCase("true")){
			disable_UpdateReportTelemetryUsageReportIntervalDuration_Check=true;
		}
	}
	
	
	private static boolean disableCreateOpt_isOptIDPresent_Check;
	static String p_disableCreateOpt_isOptIDPresent_Check = properties.get("disableCreateOpt_isOptIDPresent_Check");
	static{
		if(p_disableCreateOpt_isOptIDPresent_Check!=null && p_disableCreateOpt_isOptIDPresent_Check.equalsIgnoreCase("true")){
			disableCreateOpt_isOptIDPresent_Check=true;
		}
	}
	
	
	private static boolean disableCancelOpt_isOptIDValid_Check;
	static String p_disableCancelOpt_isOptIDValid_Check = properties.get("disableCancelOpt_isOptIDValid_Check");
	static{
		if(p_disableCancelOpt_isOptIDValid_Check!=null && p_disableCancelOpt_isOptIDValid_Check.equalsIgnoreCase("true")){
			disableCancelOpt_isOptIDValid_Check=true;
		}
	}
	
	
	private static boolean disableHTTPHeader_Valid_Check;
	static String p_disableHTTPHeader_Valid_Check = properties.get("disableHTTPHeader_Valid_Check");
	static{
		if(p_disableHTTPHeader_Valid_Check!=null && p_disableHTTPHeader_Valid_Check.equalsIgnoreCase("true")){
			disableHTTPHeader_Valid_Check=true;
		}
	}
	
	
	private static boolean disable_ActiveRegistration_Check;
	static String p_disable_ActiveRegistration_Check = properties.get("disable_ActiveRegistration_Check");
	static{
		if(p_disable_ActiveRegistration_Check!=null && p_disable_ActiveRegistration_Check.equalsIgnoreCase("true")){
			disable_ActiveRegistration_Check=true;
		}
	}
	
	private static boolean disable_MaketContext_Check;
	static String  p_disable_MaketContext_Check = properties.get("disable_MaketContext_Check");
	static{
		if(p_disable_MaketContext_Check!=null && p_disable_MaketContext_Check.equalsIgnoreCase("true")){
			disable_MaketContext_Check=true;
		}
	}
	/*private static boolean disableDistributeEvent_SignalNameSimple_Check;
	static String  p_disableDistributeEvent_SignalNameSimple_Check = properties.get("disableDistributeEvent_SignalNameSimple_Check");
	static{
		if(p_disableDistributeEvent_SignalNameSimple_Check!=null && p_disableDistributeEvent_SignalNameSimple_Check.equalsIgnoreCase("true")){
			disableDistributeEvent_SignalNameSimple_Check=true;
		}
	}*/
	
	private static boolean disable_UIDValid_Check;
	static String  p_disable_UIDValid_Check = properties.get("disable_UIDValid_Check");
	static{
		if(p_disable_UIDValid_Check!=null && p_disable_UIDValid_Check.equalsIgnoreCase("true")){
			disable_UIDValid_Check=true;
		}
	}
	
	
	private static boolean disableUpdatedReport_CancelReportRequestIDMatch_Check;
	static String  p_disableUpdatedReport_CancelReportRequestIDMatch_Check = properties.get("disableUpdatedReport_CancelReportRequestIDMatch_Check");
	static{
		if(p_disableUpdatedReport_CancelReportRequestIDMatch_Check!=null && p_disableUpdatedReport_CancelReportRequestIDMatch_Check.equalsIgnoreCase("true")){
			disableUpdatedReport_CancelReportRequestIDMatch_Check=true;
		}
	}
	
	
	private static boolean disable_UpdateReportDtstartValid_Check;
	static String  p_disable_UpdateReportDtstartValid_Check = properties.get("disable_UpdateReportDtstartValid_Check");
	static{
		if(p_disable_UpdateReportDtstartValid_Check!=null && p_disable_UpdateReportDtstartValid_Check.equalsIgnoreCase("true")){
			disable_UpdateReportDtstartValid_Check=true;
		}
	}
	
	private static boolean disable_UpdateReportDurationValid_Check;
	static String  p_disable_UpdateReportDurationValid_Check = properties.get("disable_UpdateReportDurationValid_Check");
	static{
		if(p_disable_UpdateReportDurationValid_Check!=null && p_disable_UpdateReportDurationValid_Check.equalsIgnoreCase("true")){
			disable_UpdateReportDurationValid_Check=true;
		}
	}
	
	private static boolean disableDistributeEvent_MultipleVENIDFound_Check;
	static String  p_disableDistributeEvent_MultipleVENIDFound_Check = properties.get("disableDistributeEvent_MultipleVENIDFound_Check");
	static{
		if(p_disableDistributeEvent_MultipleVENIDFound_Check!=null && p_disableDistributeEvent_MultipleVENIDFound_Check.equalsIgnoreCase("true")){
			disableDistributeEvent_MultipleVENIDFound_Check=true;
		}
	}
	
	private static boolean disable_VtnIDValid_Check;
	static String  p_disableVtnIDValid_Check = properties.get("disable_VtnIDValid_Check");
	static{
		if(p_disableVtnIDValid_Check!=null && p_disableVtnIDValid_Check.equalsIgnoreCase("true")){
			disable_VtnIDValid_Check=true;
		}
	}
	
	private static boolean disableDistributeEvent_ResponseCodeSuccess_Check;
	static String  p_disableDistributeEvent_ResponseCodeSuccess_Check = properties.get("disableDistributeEvent_ResponseCodeSuccess_Check");
	static{
		if(p_disableDistributeEvent_ResponseCodeSuccess_Check!=null && p_disableDistributeEvent_ResponseCodeSuccess_Check.equalsIgnoreCase("true")){
			disableDistributeEvent_ResponseCodeSuccess_Check=true;
		}
	}
	


	private static boolean disableCreateReport_isRIDValid_Check;
	static String  p_disableCreateReport_isRIDValid_Check = properties.get("disableCreateReport_isRIDValid_Check");
	static{
		if(p_disableCreateReport_isRIDValid_Check!=null && p_disableCreateReport_isRIDValid_Check.equalsIgnoreCase("true")){
			disableCreateReport_isRIDValid_Check=true;
		}
	}
	
	
	private static boolean disableHistoryUsageGreenbuttonGranularityReportBackDurationZero_Check;
	static String  p_disableHistoryUsageGreenbuttonGranularityReportBackDurationZero_Check = properties.get("disableHistoryUsageGreenbuttonGranularityReportBackDurationZero_Check");
	static{
		if(p_disableHistoryUsageGreenbuttonGranularityReportBackDurationZero_Check!=null && p_disableHistoryUsageGreenbuttonGranularityReportBackDurationZero_Check.equalsIgnoreCase("true")){
			disableHistoryUsageGreenbuttonGranularityReportBackDurationZero_Check=true;
		}
	}
	
	
	private static boolean disableCreateReport_reportRequestIDUnique_Check;
	static String  p_disableCreateReport_reportRequestIDUnique_Check = properties.get("disableCreateReport_reportRequestIDUnique_Check");
	static{
		if(p_disableCreateReport_reportRequestIDUnique_Check!=null && p_disableCreateReport_reportRequestIDUnique_Check.equalsIgnoreCase("true")){
			disableCreateReport_reportRequestIDUnique_Check=true;
		}
	}
	
	
	private static boolean disableCreateReport_PeriodicTelemetryReportContainsRequiredElements_Check;
	static String  p_disableCreateReport_PeriodicTelemetryReportContainsRequiredElements_Check = properties.get("disableCreateReport_PeriodicTelemetryReportContainsRequiredElements_Check");
	static{
		if(p_disableCreateReport_PeriodicTelemetryReportContainsRequiredElements_Check!=null && p_disableCreateReport_PeriodicTelemetryReportContainsRequiredElements_Check.equalsIgnoreCase("true")){
			disableCreateReport_PeriodicTelemetryReportContainsRequiredElements_Check=true;
		}
	}
	
	
	private static boolean disableCreateReport_PeriodicReportReportDurationNotLessThanMinimumSamplingPeriod_Check;
	static String  p_disableCreateReport_PeriodicReportReportDurationNotLessThanMinimumSamplingPeriod_Check = properties.get("disableCreateReport_PeriodicReportReportDurationNotLessThanMinimumSamplingPeriod_Check");
	static{
		if(p_disableCreateReport_PeriodicReportReportDurationNotLessThanMinimumSamplingPeriod_Check!=null && p_disableCreateReport_PeriodicReportReportDurationNotLessThanMinimumSamplingPeriod_Check.equalsIgnoreCase("true")){
			disableCreateReport_PeriodicReportReportDurationNotLessThanMinimumSamplingPeriod_Check=true;
		}
	}
	
	
	private static boolean disableCreateReport_GranularityValid_Check;
	static String  p_disableCreateReport_GranularityValid_Check = properties.get("disableCreateReport_GranularityValid_Check");
	static{
		if(p_disableCreateReport_GranularityValid_Check!=null && p_disableCreateReport_GranularityValid_Check.equalsIgnoreCase("true")){
			disableCreateReport_GranularityValid_Check=true;
		}
	}
	
	private static boolean disableRegister_Create_reportRequestIDZero_Check;
	static String  p_disableRegister_Create_reportRequestIDZero_Check = properties.get("disableRegister_Create_reportRequestIDZero_Check");
	static{
		if(p_disableRegister_Create_reportRequestIDZero_Check!=null && p_disableRegister_Create_reportRequestIDZero_Check.equalsIgnoreCase("true")){
			disableRegister_Create_reportRequestIDZero_Check=true;
		}
	}
	
	
	private static boolean disableRegisterReport_ReportNameReportTypeItemBaseReadingTypeCombination_Check;
	static String  p_disableRegisterReport_ReportNameReportTypeItemBaseReadingTypeCombination_Check = properties.get("disableRegisterReport_ReportNameReportTypeItemBaseReadingTypeCombination_Check");
	static{
		if(p_disableRegisterReport_ReportNameReportTypeItemBaseReadingTypeCombination_Check!=null && p_disableRegisterReport_ReportNameReportTypeItemBaseReadingTypeCombination_Check.equalsIgnoreCase("true")){
			disableRegisterReport_ReportNameReportTypeItemBaseReadingTypeCombination_Check=true;
		}
	}
	
	
	private static boolean disableRegisterReport_IntervalsDtstartEiReportIDNotPresent_Check;
	static String  p_disableRegisterReport_IntervalsDtstartEiReportIDNotPresent_Check = properties.get("disableRegisterReport_IntervalsDtstartEiReportIDNotPresent_Check");
	static{
		if(p_disableRegisterReport_IntervalsDtstartEiReportIDNotPresent_Check!=null && p_disableRegisterReport_IntervalsDtstartEiReportIDNotPresent_Check.equalsIgnoreCase("true")){
			disableRegisterReport_IntervalsDtstartEiReportIDNotPresent_Check=true;
		}
	}
	
	private static boolean disableUpdateReport_isRIDValid_Check;
	static String  p_disableUpdateReport_isRIDValid_Check = properties.get("disableUpdateReport_isRIDValid_Check");
	static{
		if(p_disableUpdateReport_isRIDValid_Check!=null && p_disableUpdateReport_isRIDValid_Check.equalsIgnoreCase("true")){
			disableUpdateReport_isRIDValid_Check=true;
		}
	}
	
	
	private static boolean disableUpdateReport_RequiredElementsAndReportName_Check;
	static String  p_disableUpdateReport_RequiredElementsAndReportName_Check = properties.get("disableUpdateReport_RequiredElementsAndReportName_Check");
	static{
		if(p_disableUpdateReport_RequiredElementsAndReportName_Check!=null && p_disableUpdateReport_RequiredElementsAndReportName_Check.equalsIgnoreCase("true")){
			disableUpdateReport_RequiredElementsAndReportName_Check=true;
		}
	}
	
	
	
	
	private static boolean disableCanceledOpt_ResponseCodeSuccess_Check;
	static String  p_disableCanceledOpt_ResponseCodeSuccess_Check = properties.get("disableCanceledOpt_ResponseCodeSuccess_Check");
	static{
		if(p_disableCanceledOpt_ResponseCodeSuccess_Check!=null && p_disableCanceledOpt_ResponseCodeSuccess_Check.equalsIgnoreCase("true")){
			disableCanceledOpt_ResponseCodeSuccess_Check=true;
		}
	}
	

	private static boolean disableCanceledPartyRegistration_ResponseCodeSuccess_Check;
	static String  p_disableCanceledPartyRegistration_ResponseCodeSuccess_Check = properties.get("disableCanceledPartyRegistration_ResponseCodeSuccess_Check");
	static{
		if(p_disableCanceledPartyRegistration_ResponseCodeSuccess_Check!=null && p_disableCanceledPartyRegistration_ResponseCodeSuccess_Check.equalsIgnoreCase("true")){
			disableCanceledPartyRegistration_ResponseCodeSuccess_Check=true;
		}
	}
	
	
	private static boolean disableCanceledReport_ResponseCodeSuccess_Check;
	static String  p_disableCanceledReport_ResponseCodeSuccess_Check = properties.get("disableCanceledReport_ResponseCodeSuccess_Check");
	static{
		if(p_disableCanceledReport_ResponseCodeSuccess_Check!=null && p_disableCanceledReport_ResponseCodeSuccess_Check.equalsIgnoreCase("true")){
			disableCanceledReport_ResponseCodeSuccess_Check=true;
		}
	}

	
	private static boolean disableCreatedOpt_ResponseCodeSuccess_Check;
	static String  p_disableCreatedOpt_ResponseCodeSuccess_Check = properties.get("disableCreatedOpt_ResponseCodeSuccess_Check");
	static{
		if(p_disableCreatedOpt_ResponseCodeSuccess_Check!=null && p_disableCreatedOpt_ResponseCodeSuccess_Check.equalsIgnoreCase("true")){
			disableCreatedOpt_ResponseCodeSuccess_Check=true;
		}
	}
	
	
	private static boolean disableCreatedPartyRegistration_ResponseCodeSuccess_Check;
	static String  p_disableCreatedPartyRegistration_ResponseCodeSuccess_Check = properties.get("disableCreatedPartyRegistration_ResponseCodeSuccess_Check");
	static{
		if(p_disableCreatedPartyRegistration_ResponseCodeSuccess_Check!=null && p_disableCreatedPartyRegistration_ResponseCodeSuccess_Check.equalsIgnoreCase("true")){
			disableCreatedPartyRegistration_ResponseCodeSuccess_Check=true;
		}
	}
	
	private static boolean disableCreatedReport_ResponseCodeSuccess_Check;
	static String  p_disableCreatedReport_ResponseCodeSuccess_Check = properties.get("disableCreatedReport_ResponseCodeSuccess_Check");
	static{
		if(p_disableCreatedReport_ResponseCodeSuccess_Check!=null && p_disableCreatedReport_ResponseCodeSuccess_Check.equalsIgnoreCase("true")){
			disableCreatedReport_ResponseCodeSuccess_Check=true;
		}
	}

	
	private static boolean disableRegisteredReport_ResponseCodeSuccess_Check;
	static String  p_disableRegisteredReport_ResponseCodeSuccess_Check = properties.get("disableRegisteredReport_ResponseCodeSuccess_Check");
	static{
		if(p_disableRegisteredReport_ResponseCodeSuccess_Check!=null && p_disableRegisteredReport_ResponseCodeSuccess_Check.equalsIgnoreCase("true")){
			disableRegisteredReport_ResponseCodeSuccess_Check=true;
		}
	}

	
	private static boolean disableUpdatedReport_ResponseCodeSuccess_Check;
	static String  p_disableUpdatedReport_ResponseCodeSuccess_Check = properties.get("disableUpdatedReport_ResponseCodeSuccess_Check");
	static{
		if(p_disableUpdatedReport_ResponseCodeSuccess_Check!=null && p_disableUpdatedReport_ResponseCodeSuccess_Check.equalsIgnoreCase("true")){
			disableUpdatedReport_ResponseCodeSuccess_Check=true;
		}
	}
	
	private static boolean disableDistributeEvent_EventIDsUnique_ValidCheck;
	static String  p_disableDistributeEvent_EventIDsUnique_ValidCheck = properties.get("disableDistributeEvent_EventIDsUnique_ValidCheck");
	static{
		if(p_disableDistributeEvent_EventIDsUnique_ValidCheck!=null && p_disableDistributeEvent_EventIDsUnique_ValidCheck.equalsIgnoreCase("true")){
			disableDistributeEvent_EventIDsUnique_ValidCheck=true;
		}
	}
	
	
	private static boolean disableRegisterReport_DurationValid_Check;
	static String  p_disableRegisterReport_DurationValid_Check = properties.get("disableRegisterReport_DurationValid_Check");
	static{
		if(p_disableRegisterReport_DurationValid_Check!=null && p_disableRegisterReport_DurationValid_Check.equalsIgnoreCase("true")){
			disableRegisterReport_DurationValid_Check=true;
		}
	}
	
	
	private static boolean disableRegisterReport_ReportSpecifierIDAndRIDUnique_Check;
	static String  p_disableRegisterReport_ReportSpecifierIDAndRIDUnique_Check = properties.get("disableRegisterReport_ReportSpecifierIDAndRIDUnique_Check");
	static{
		if(p_disableRegisterReport_ReportSpecifierIDAndRIDUnique_Check!=null && p_disableRegisterReport_ReportSpecifierIDAndRIDUnique_Check.equalsIgnoreCase("true")){
			disableRegisterReport_ReportSpecifierIDAndRIDUnique_Check=true;
		}
	}
	
	
	
	private static boolean disableRegisterReport_SamplingRateFoundInTelemertyMetadata_Check;
	static String  p_disableRegisterReport_SamplingRateFoundInTelemertyMetadata_Check = properties.get("disableRegisterReport_SamplingRateFoundInTelemertyMetadata_Check");
	static{
		if(p_disableRegisterReport_SamplingRateFoundInTelemertyMetadata_Check!=null && p_disableRegisterReport_SamplingRateFoundInTelemertyMetadata_Check.equalsIgnoreCase("true")){
			disableRegisterReport_SamplingRateFoundInTelemertyMetadata_Check=true;
		}
	}
	
	
	private static boolean disableDistributeEvent_EventStatusValid_ValidCheck;
	static String  p_disableDistributeEvent_EventStatusValid_ValidCheck = properties.get("disableDistributeEvent_EventStatusValid_ValidCheck");
	static{
		if(p_disableDistributeEvent_EventStatusValid_ValidCheck!=null && p_disableDistributeEvent_EventStatusValid_ValidCheck.equalsIgnoreCase("true")){
			disableDistributeEvent_EventStatusValid_ValidCheck=true;
		}
	}
	
	private static boolean disable_CreatedDateTimeWithinExpectedWindow_Check;
	static String  p_disable_CreatedDateTimeWithinExpectedWindow_Check = properties.get("disable_CreatedDateTimeWithinExpectedWindow_Check");
	static{
		if(p_disable_CreatedDateTimeWithinExpectedWindow_Check!=null && p_disable_CreatedDateTimeWithinExpectedWindow_Check.equalsIgnoreCase("true")){
			disable_CreatedDateTimeWithinExpectedWindow_Check=true;
		}
	}
	private static boolean disable_AtLeastOneEiTargetMatch_ValidCheck;
	static String  p_disable_AtLeastOneEiTargetMatch_ValidCheck = properties.get("disable_AtLeastOneEiTargetMatch_ValidCheck");
	static{
		if(p_disable_AtLeastOneEiTargetMatch_ValidCheck!=null && p_disable_AtLeastOneEiTargetMatch_ValidCheck.equalsIgnoreCase("true")){
			disable_AtLeastOneEiTargetMatch_ValidCheck=true;
		}
	}
	private static boolean disableDistributeEvent_EventOrderValid_Check;
	static String  p_disableDistributeEvent_EventOrderValid_Check = properties.get("disableDistributeEvent_EventOrderValid_Check");
	static{
		if(p_disableDistributeEvent_EventOrderValid_Check!=null && p_disableDistributeEvent_EventOrderValid_Check.equalsIgnoreCase("true")){
			disableDistributeEvent_EventOrderValid_Check=true;
		}
	}
	private static boolean disableDistributeEvent_ActiveCurrentValueMatchCurrentPayload_Check;
	static String  p_disableDistributeEvent_ActiveCurrentValueMatchCurrentPayload_Check = properties.get("disableDistributeEvent_ActiveCurrentValueMatchCurrentPayload_Check");
	static{
		if(p_disableDistributeEvent_ActiveCurrentValueMatchCurrentPayload_Check!=null && p_disableDistributeEvent_ActiveCurrentValueMatchCurrentPayload_Check.equalsIgnoreCase("true")){
			disableDistributeEvent_ActiveCurrentValueMatchCurrentPayload_Check=true;
		}
	}
	private static boolean disableDistributeEvent_NonActiveCurrentValueValid_Check;
	static String  p_disableDistributeEvent_NonActiveCurrentValueValid_Check = properties.get("disableDistributeEvent_NonActiveCurrentValueValid_Check");
	static{
		if(p_disableDistributeEvent_NonActiveCurrentValueValid_Check!=null && p_disableDistributeEvent_NonActiveCurrentValueValid_Check.equalsIgnoreCase("true")){
			disableDistributeEvent_NonActiveCurrentValueValid_Check=true;
		}
	}
	private static boolean disableDistributeEvent_PayloadFloatLimitValid_Check;
	static String  p_disableDistributeEvent_PayloadFloatLimitValid_Check = properties.get("disableDistributeEvent_PayloadFloatLimitValid_Check");
	static{
		if(p_disableDistributeEvent_PayloadFloatLimitValid_Check!=null && p_disableDistributeEvent_PayloadFloatLimitValid_Check.equalsIgnoreCase("true")){
			disableDistributeEvent_PayloadFloatLimitValid_Check=true;
		}
	}
	private static boolean disableDistributeEvent_ActivePeriodDurationValid_Check;
	static String  p_disableDistributeEvent_ActivePeriodDurationValid_Check = properties.get("disableDistributeEvent_ActivePeriodDurationValid_Check");
	static{
		if(p_disableDistributeEvent_ActivePeriodDurationValid_Check!=null && p_disableDistributeEvent_ActivePeriodDurationValid_Check.equalsIgnoreCase("true")){
			disableDistributeEvent_ActivePeriodDurationValid_Check=true;
		}
	}
	
	
	private static boolean disableDistributeEvent_EventBaselineDurationValid_Check;
	static String  p_disableDistributeEvent_EventBaselineDurationValid_Check = properties.get("disableDistributeEvent_EventBaselineDurationValid_Check");
	static{
		if(p_disableDistributeEvent_EventBaselineDurationValid_Check!=null && p_disableDistributeEvent_EventBaselineDurationValid_Check.equalsIgnoreCase("true")){
			disableDistributeEvent_EventBaselineDurationValid_Check=true;
		}
	}
	
	
	private static boolean disableDistributeEvent_EINotificationSubElementOfActivePeriod_Check;
	static String  p_disableDistributeEvent_EINotificationSubElementOfActivePeriod_Check = properties.get("disableDistributeEvent_EINotificationSubElementOfActivePeriod_Check");
	static{
		if(p_disableDistributeEvent_EINotificationSubElementOfActivePeriod_Check!=null && p_disableDistributeEvent_EINotificationSubElementOfActivePeriod_Check.equalsIgnoreCase("true")){
			disableDistributeEvent_EINotificationSubElementOfActivePeriod_Check=true;
		}
	}
	
	
	private static boolean disableDeviceClass_Check;
	static String  p_disableDeviceClass_Check = properties.get("disableDeviceClass_Check");
	static{
		if(p_disableDeviceClass_Check!=null && p_disableDeviceClass_Check.equalsIgnoreCase("true")){
			disableDeviceClass_Check=true;
		}
	}
	
	
	private static boolean disableCreateOpt_VavailabilityExcludedProperties_Check;
	static String  p_disableCreateOpt_VavailabilityExcludedProperties_Check = properties.get("disableCreateOpt_VavailabilityExcludedProperties_Check");
	static{
		if(p_disableCreateOpt_VavailabilityExcludedProperties_Check!=null && p_disableCreateOpt_VavailabilityExcludedProperties_Check.equalsIgnoreCase("true")){
			disableCreateOpt_VavailabilityExcludedProperties_Check=true;
		}
	}
	
	
	private static boolean disableCreateOpt_VavailabilityMarketContextNotPresentIfEventIDPresent_Check;
	static String  p_disableCreateOpt_VavailabilityMarketContextNotPresentIfEventIDPresent_Check = properties.get("disableCreateOpt_VavailabilityMarketContextNotPresentIfEventIDPresent_Check");
	static{
		if(p_disableCreateOpt_VavailabilityMarketContextNotPresentIfEventIDPresent_Check!=null && p_disableCreateOpt_VavailabilityMarketContextNotPresentIfEventIDPresent_Check.equalsIgnoreCase("true")){
			disableCreateOpt_VavailabilityMarketContextNotPresentIfEventIDPresent_Check=true;
		}
	}
	
	private static boolean disableDistributeEvent_EIResponsePresent_Check;
	static String  p_disableDistributeEvent_EIResponsePresent_Check = properties.get("disableDistributeEvent_EIResponsePresent_Check");
	static{
		if(p_disableDistributeEvent_EIResponsePresent_Check!=null && p_disableDistributeEvent_EIResponsePresent_Check.equalsIgnoreCase("true")){
			disableDistributeEvent_EIResponsePresent_Check=true;
		}
	}
	/*
	private static boolean disableDistributeEvent_MultipleEventSignalpPresent_Check;
	static String  p_disableDistributeEvent_MultipleEventSignalpPresent_Check = properties.get("disableDistributeEvent_MultipleEventSignalpPresent_Check");
	static{
		if(p_disableDistributeEvent_MultipleEventSignalpPresent_Check!=null && p_disableDistributeEvent_MultipleEventSignalpPresent_Check.equalsIgnoreCase("true")){
			disableDistributeEvent_MultipleEventSignalpPresent_Check=true;
		}
	}*/
	private static boolean disableDistributeEvent_EventNonOverlappingPresent_Check;
	static String  p_disableDistributeEvent_EventNonOverlappingPresent_Check = properties.get("disableDistributeEvent_EventNonOverlappingPresent_Check");
	static{
		if(p_disableDistributeEvent_EventNonOverlappingPresent_Check!=null && p_disableDistributeEvent_EventNonOverlappingPresent_Check.equalsIgnoreCase("true")){
			disableDistributeEvent_EventNonOverlappingPresent_Check=true;
		}
	}
	private static boolean disable_VenIDValid_Check;
	static String  p_disable_VenIDValid_Check = properties.get("disable_VenIDValid_Check");
	static{
		if(p_disable_VenIDValid_Check!=null && p_disable_VenIDValid_Check.equalsIgnoreCase("true")){
			disable_VenIDValid_Check=true;
		}
	}
	
	
	private static boolean disableCreateReportReportSpecifierNotPresent_Check;
	static String  p_disableCreateReportReportSpecifierNotPresent_Check = properties.get("disableCreateReportReportSpecifierNotPresent_Check");
	static{
		if(p_disableCreateReportReportSpecifierNotPresent_Check!=null && p_disableCreateReportReportSpecifierNotPresent_Check.equalsIgnoreCase("true")){
			disableCreateReportReportSpecifierNotPresent_Check=true;
		}
	}
	
	
	private static boolean disableCreateReport_DoesNotContainExcludedReportIntervalProperties_Check;
	static String  p_disableCreateReport_DoesNotContainExcludedReportIntervalProperties_Check = properties.get("disableCreateReport_DoesNotContainExcludedReportIntervalProperties_Check");
	static{
		if(p_disableCreateReport_DoesNotContainExcludedReportIntervalProperties_Check!=null && p_disableCreateReport_DoesNotContainExcludedReportIntervalProperties_Check.equalsIgnoreCase("true")){
			disableCreateReport_DoesNotContainExcludedReportIntervalProperties_Check=true;
		}
	}
	
	
	private static boolean disableCreateReport_ReadingTypeValid_Check;
	static String  p_disableCreateReport_ReadingTypeValid_Check = properties.get("disableCreateReport_ReadingTypeValid_Check");
	static{
		if(p_disableCreateReport_ReadingTypeValid_Check!=null && p_disableCreateReport_ReadingTypeValid_Check.equalsIgnoreCase("true")){
			disableCreateReport_ReadingTypeValid_Check=true;
		}
	}
	
	
	private static boolean disableDistributeEvent_SignalTypeAndUnitValidForSignalName_Check;
	static String  p_disableDistributeEvent_SignalTypeAndUnitValidForSignalName_Check = properties.get("disableDistributeEvent_SignalTypeAndUnitValidForSignalName_Check");
	static{
		if(p_disableDistributeEvent_SignalTypeAndUnitValidForSignalName_Check!=null && p_disableDistributeEvent_SignalTypeAndUnitValidForSignalName_Check.equalsIgnoreCase("true")){
			disableDistributeEvent_SignalTypeAndUnitValidForSignalName_Check=true;
		}
	}
	
	private static boolean disableOadrCreatedPartyRegistration_PULLPollFreq_Check;
	static String  p_disableOadrCreatedPartyRegistration_PULLPollFreq_Check = properties.get("disableOadrCreatedPartyRegistration_PULLPollFreq_Check");
	static{
		if(p_disableOadrCreatedPartyRegistration_PULLPollFreq_Check!=null && p_disableOadrCreatedPartyRegistration_PULLPollFreq_Check.equalsIgnoreCase("true")){
			disableOadrCreatedPartyRegistration_PULLPollFreq_Check=true;
		}
	}
	
	
	private static boolean disableCreatedEvent_ResponseCodeSuccess_Check;
	static String  p_disableCreatedEvent_ResponseCodeSuccess_Check = properties.get("disableCreatedEvent_ResponseCodeSuccess_Check");
	static{
		if(p_disableCreatedEvent_ResponseCodeSuccess_Check!=null && p_disableCreatedEvent_ResponseCodeSuccess_Check.equalsIgnoreCase("true")){
			disableCreatedEvent_ResponseCodeSuccess_Check=true;
		}
	}
	
	
	private static boolean disableRequestIDValid_Check;
	static String  p_disableRequestIDValid_Check = properties.get("disableRequestIDValid_Check");
	static{
		if(p_disableRequestIDValid_Check!=null && p_disableRequestIDValid_Check.equalsIgnoreCase("true")){
			disableRequestIDValid_Check=true;
		}
	}
	
	private static boolean disable_EventIDValid_Check;
	static String  p_disable_EventIDValid_Check = properties.get("disable_EventIDValid_Check");
	static{
		if(p_disable_EventIDValid_Check!=null && p_disable_EventIDValid_Check.equalsIgnoreCase("true")){
			disable_EventIDValid_Check=true;
		}
	}
	
	private static boolean disableCreatedEvent_EIResponsesValid_Check;
	static String  p_disableCreatedEvent_EIResponsesValid_Check = properties.get("disableCreatedEvent_EIResponsesValid_Check");
	static{
		if(p_disableCreatedEvent_EIResponsesValid_Check!=null && p_disableCreatedEvent_EIResponsesValid_Check.equalsIgnoreCase("true")){
			disableCreatedEvent_EIResponsesValid_Check=true;
		}
	}
	
	private static boolean disableResponseEvent_ResponseCodeSuccess_Check;
	static String  p_disableResponseEvent_ResponseCodeSuccess_Check = properties.get("disableResponseEvent_ResponseCodeSuccess_Check");
	static{
		if(p_disableResponseEvent_ResponseCodeSuccess_Check!=null && p_disableResponseEvent_ResponseCodeSuccess_Check.equalsIgnoreCase("true")){
			disableResponseEvent_ResponseCodeSuccess_Check=true;
		}
	}
	
	
	private static boolean disablePoll_PUSH_Check;
	static String  p_disablePoll_PUSH_Check = properties.get("disablePoll_PUSH_Check");
	static{
		if(p_disablePoll_PUSH_Check!=null && p_disablePoll_PUSH_Check.equalsIgnoreCase("true")){
			disablePoll_PUSH_Check=true;
		}
	}
	
	
	private static boolean disableCancelPartyRegistration_IsRegistrationValid_Check;
	static String  p_disableCancelPartyRegistration_IsRegistrationValid_Check = properties.get("disableCancelPartyRegistration_IsRegistrationValid_Check");
	static{
		if(p_disableCancelPartyRegistration_IsRegistrationValid_Check!=null && p_disableCancelPartyRegistration_IsRegistrationValid_Check.equalsIgnoreCase("true")){
			disableCancelPartyRegistration_IsRegistrationValid_Check=true;
		}
	}
	
	private static boolean disableRequestEvent_isAllPrevCreatedEvntsRecvd_Check;
	static String  p_disableRequestEvent_isAllPrevCreatedEvntsRecvd_Check = properties.get("disableRequestEvent_isAllPrevCreatedEvntsRecvd_Check");
	static{
		if(p_disableRequestEvent_isAllPrevCreatedEvntsRecvd_Check!=null && p_disableRequestEvent_isAllPrevCreatedEvntsRecvd_Check.equalsIgnoreCase("true")){
			disableRequestEvent_isAllPrevCreatedEvntsRecvd_Check=true;
		}
	}
	private static boolean disableDistributeEvent_AllPreviousEvntPresent_Check;
	static String  p_disableDistributeEvent_AllPreviousEvntPresent_Check = properties.get("disableDistributeEvent_AllPreviousEvntPresent_Check");
	static{
		if(p_disableDistributeEvent_AllPreviousEvntPresent_Check!=null && p_disableDistributeEvent_AllPreviousEvntPresent_Check.equalsIgnoreCase("true")){
			disableDistributeEvent_AllPreviousEvntPresent_Check=true;
		}
	}

	
	private static boolean disableDistributeEvent_DTStartNotInEventSignalsOrEventBaselines_Check;
	static String  p_disableDistributeEvent_DTStartNotInEventSignalsOrEventBaselines_Check = properties.get("disableDistributeEvent_DTStartNotInEventSignalsOrEventBaselines_Check");
	static{
		if(p_disableDistributeEvent_DTStartNotInEventSignalsOrEventBaselines_Check!=null && p_disableDistributeEvent_DTStartNotInEventSignalsOrEventBaselines_Check.equalsIgnoreCase("true")){
			disableDistributeEvent_DTStartNotInEventSignalsOrEventBaselines_Check=true;
		}
	}

	
	private static boolean disableDistributeEvent_SignalIDUnique_Check;
	static String  p_disableDistributeEvent_SignalIDUnique_Check = properties.get("disableDistributeEvent_SignalIDUnique_Check");
	static{
		if(p_disableDistributeEvent_SignalIDUnique_Check!=null && p_disableDistributeEvent_SignalIDUnique_Check.equalsIgnoreCase("true")){
			disableDistributeEvent_SignalIDUnique_Check=true;
		}
	}
	
	private static boolean disableOadrCreatePartyRegistration_MatchDBVENAndRegID_Check;
	static String  p_disableOadrCreatePartyRegistration_MatchDBVENAndRegID_Check = properties.get("disableOadrCreatePartyRegistration_MatchDBVENAndRegID_Check");
	static{
		if(p_disableOadrCreatePartyRegistration_MatchDBVENAndRegID_Check!=null && p_disableOadrCreatePartyRegistration_MatchDBVENAndRegID_Check.equalsIgnoreCase("true")){
			disableOadrCreatePartyRegistration_MatchDBVENAndRegID_Check=true;
		}
	}
	
	private static boolean disableOadrCreatePartyRegistration_PUSHTransportAddress_Check;
	static String  p_disableOadrCreatePartyRegistration_PUSHTransportAddress_Check = properties.get("disableOadrCreatePartyRegistration_PUSHTransportAddress_Check");
	static{
		if(p_disableOadrCreatePartyRegistration_PUSHTransportAddress_Check!=null && p_disableOadrCreatePartyRegistration_PUSHTransportAddress_Check.equalsIgnoreCase("true")){
			disableOadrCreatePartyRegistration_PUSHTransportAddress_Check=true;
		}
	}
	
	private static boolean disableCreatePartyRegistration_HttpPullModelTrueFalse_Check;
	static String  p_disableCreatePartyRegistration_HttpPullModelTrueFalse_Check = properties.get("disableCreatePartyRegistration_HttpPullModelTrueFalse_Check");
	static{
		if(p_disableCreatePartyRegistration_HttpPullModelTrueFalse_Check!=null && p_disableCreatePartyRegistration_HttpPullModelTrueFalse_Check.equalsIgnoreCase("true")){
			disableCreatePartyRegistration_HttpPullModelTrueFalse_Check=true;
		}
	}
	
	public static boolean isDisableOadrCreatePartyRegistration_isRegIDAndVENIDMatch_Check(){
		return disableOadrCreatePartyRegistration_MatchDBVENAndRegID_Check;
	}
	
	
	public static boolean isDisableOadrCreatePartyRegistration_PUSHTransportAddress_Check(){
		return disableOadrCreatePartyRegistration_PUSHTransportAddress_Check;
	}
	public static boolean isDisableCancelPartyRegistration_IsRegistrationValid_Check(){
		return disableCancelPartyRegistration_IsRegistrationValid_Check;
	}
	
	public static void setDisableOadrCreatePartyRegistration_isRegIDAndVENIDMatch_Check(boolean flag){
		disableOadrCreatePartyRegistration_MatchDBVENAndRegID_Check=flag;
	}
	
	public static void setDisableDistributeEvent_SignalIDUnique_Check(boolean flag){
		disableDistributeEvent_SignalIDUnique_Check=flag;
	}
	
	public static void setDisableDistributeEvent_DTStartNotInEventSignalsOrEventBaselines_Check(boolean flag){
		disableDistributeEvent_DTStartNotInEventSignalsOrEventBaselines_Check=flag;
	}
	
	public static void setDisableOadrCreatePartyRegistration_PUSHTransportAddress_Check(boolean flag){
		disableOadrCreatePartyRegistration_PUSHTransportAddress_Check=flag;
	}
	
	public static void setDisableCreatePartyRegistration_HttpPullModelTrueFalse_Check(boolean flag){
		disableCreatePartyRegistration_HttpPullModelTrueFalse_Check=flag;
	}
	
	
	public static void setDisableCancelPartyRegistration_IsRegistrationValid_Check(boolean flag){
		disableCancelPartyRegistration_IsRegistrationValid_Check=flag;
	}
			
	public static boolean isDisableDistributeEvent_AllPreviousEvntPresent_Check() {
		return disableDistributeEvent_AllPreviousEvntPresent_Check;
	}

	public static void setDisableDistributeEvent_AllPreviousEvntPresent_Check(
			boolean disableDistributeEvent_AllPreviousEvntPresent_Check) {
		ConformanceRuleValidator.disableDistributeEvent_AllPreviousEvntPresent_Check = disableDistributeEvent_AllPreviousEvntPresent_Check;
	}

	public static boolean isDisableRequestEvent_isAllPrevCreatedEvntsRecvd_Check() {
		return disableRequestEvent_isAllPrevCreatedEvntsRecvd_Check;
	}

	public static void setDisableRequestEvent_isAllPrevCreatedEvntsRecvd_Check(
			boolean disableRequestEvent_isAllPrevCreatedEvntsRecvd_Check) {
		ConformanceRuleValidator.disableRequestEvent_isAllPrevCreatedEvntsRecvd_Check = disableRequestEvent_isAllPrevCreatedEvntsRecvd_Check;
	}

	public static boolean isDisableDistributeEvent_EventNonOverlappingPresent_Check() {
		return disableDistributeEvent_EventNonOverlappingPresent_Check;
	}

	public static void setDisableDistributeEvent_EventNonOverlappingPresent_Check(
			boolean disableDistributeEvent_EventNonOverlappingPresent_Check) {
		ConformanceRuleValidator.disableDistributeEvent_EventNonOverlappingPresent_Check = disableDistributeEvent_EventNonOverlappingPresent_Check;
	}

	public static void setDisableAllConformanceCheck(
			boolean disableAllConformanceCheck) {
		ConformanceRuleValidator.disableAllConformanceCheck = disableAllConformanceCheck;
	}

	public static boolean isDisableDistributeEvent_MultipleVENIDFound_Check() {
		return disableDistributeEvent_MultipleVENIDFound_Check;
	}

	public static void setDisableDistributeEvent_EIResponsePresent_Check(
			boolean disableDistributeEvent_EIResponsePresent_Check) {
		ConformanceRuleValidator.disableDistributeEvent_EIResponsePresent_Check = disableDistributeEvent_EIResponsePresent_Check;
	}

	
	public static void setDisableCreateOpt_VavailabilityMarketContextNotPresentIfEventIDPresent_Check(
			boolean disableCreateOpt_VavailabilityMarketContextNotPresentIfEventIDPresent_Check) {
		ConformanceRuleValidator.disableCreateOpt_VavailabilityMarketContextNotPresentIfEventIDPresent_Check = disableCreateOpt_VavailabilityMarketContextNotPresentIfEventIDPresent_Check;
	}
	
	public static void setDisableDistributeEvent_EventBaselineDurationValid_Check(
			boolean disableDistributeEvent_EventBaselineDurationValid_Check) {
		ConformanceRuleValidator.disableDistributeEvent_EventBaselineDurationValid_Check = disableDistributeEvent_EventBaselineDurationValid_Check;
	}
	
	public static void setDisableDistributeEvent_EINotificationSubElementOfActivePeriod_Check(
			boolean disableDistributeEvent_EINotificationSubElementOfActivePeriod_Check) {
		ConformanceRuleValidator.disableDistributeEvent_EINotificationSubElementOfActivePeriod_Check = disableDistributeEvent_EINotificationSubElementOfActivePeriod_Check;
	}
	
	
	public static void setDisableDeviceClass_Check(
			boolean disableDeviceClass_Check) {
		ConformanceRuleValidator.disableDeviceClass_Check = disableDeviceClass_Check;
	}
	
	
	public static void setDisableCreateOpt_VavailabilityExcludedProperties_Check(
			boolean disableCreateOpt_VavailabilityExcludedProperties_Check) {
		ConformanceRuleValidator.disableCreateOpt_VavailabilityExcludedProperties_Check = disableCreateOpt_VavailabilityExcludedProperties_Check;
	}
	
	public static void setDisableDistributeEvent_MultipleVENIDFound_Check(
			boolean disableDistributeEvent_MultipleVENIDFound_Check) {
		ConformanceRuleValidator.disableDistributeEvent_MultipleVENIDFound_Check = disableDistributeEvent_MultipleVENIDFound_Check;
	}

	public static void setDisableDistributeEvent_RequestIDPresent_Check(
			boolean disableDistributeEvent_RequestIDPresent_Check) {
		ConformanceRuleValidator.disableDistributeEvent_RequestIDPresent_Check = disableDistributeEvent_RequestIDPresent_Check;
	}
	
	
	public static void setDisableSchemaVersionValid_Check(
			boolean disableSchemaVersionValid_Check) {
		ConformanceRuleValidator.disableSchemaVersionValid_Check = disableSchemaVersionValid_Check;
	}
	
	
	public static void setDisableDistributeEvent_OnePayloadFloatPresentInEachInterval_Check(
			boolean disableDistributeEvent_OnePayloadFloatPresentInEachInterval_Check) {
		ConformanceRuleValidator.disableDistributeEvent_OnePayloadFloatPresentInEachInterval_Check = disableDistributeEvent_OnePayloadFloatPresentInEachInterval_Check;
	}
	
	
	public static void setDisableRegisterReport_RequestIDMatchPreviousCreateReport_Check(
			boolean disableRegisterReport_RequestIDMatchPreviousCreateReport_Check) {
		ConformanceRuleValidator.disableRegisterReport_RequestIDMatchPreviousCreateReport_Check = disableRegisterReport_RequestIDMatchPreviousCreateReport_Check;
	}
	
	public static void setDisableRegisterReport_VenReportNameValid_Check(
			boolean disableRegisterReport_VenReportNameValid_Check) {
		ConformanceRuleValidator.disableRegisterReport_VenReportNameValid_Check = disableRegisterReport_VenReportNameValid_Check;
	}
	
	
	public static void setDisable_UpdateReportLoadControlStateConsistentForAllIntervals_Check(
			boolean disable_UpdateReportLoadControlStateConsistentForAllIntervals_Check) {
		ConformanceRuleValidator.disable_UpdateReportLoadControlStateConsistentForAllIntervals_Check = disable_UpdateReportLoadControlStateConsistentForAllIntervals_Check;
	}
	
	
	public static void setDisableCreateOpt_isOptIDPresent_Check(
			boolean disableCreateOpt_isOptIDPresent_Check) {
		ConformanceRuleValidator.disableCreateOpt_isOptIDPresent_Check = disableCreateOpt_isOptIDPresent_Check;
	}

	public static void setDisableCancelOpt_isOptIDValid_Check(
			boolean disableCancelOpt_isOptIDValid_Check) {
		ConformanceRuleValidator.disableCancelOpt_isOptIDValid_Check = disableCancelOpt_isOptIDValid_Check;
	}
	
	
	public static void setDisableHTTPHeader_Valid_Check(
			boolean disableHTTPHeader_Valid_Check) {
		ConformanceRuleValidator.disableHTTPHeader_Valid_Check = disableHTTPHeader_Valid_Check;
	}
	
	
	public static boolean isDisableHTTPHeader_Valid_Check(){
		return disableHTTPHeader_Valid_Check;
	}
	
	
	public static void setDisable_MaketContext_Check(
			boolean disable_MaketContext_Check) {
		ConformanceRuleValidator.disable_MaketContext_Check = disable_MaketContext_Check;
	}

	/*public static void setDisableDistributeEvent_SignalNameSimple_Check(
			boolean disableDistributeEvent_SignalNameSimple_Check) {
		ConformanceRuleValidator.disableDistributeEvent_SignalNameSimple_Check = disableDistributeEvent_SignalNameSimple_Check;
	}*/

	public static void setDisableUIDValid_Check(
			boolean disableDistributeEvent_UIDValid_Check) {
		ConformanceRuleValidator.disable_UIDValid_Check = disableDistributeEvent_UIDValid_Check;
	}

	
	public static void setDisable_UpdateReportDtstartValid_Check(
			boolean disable_UpdateReportDtstartValid_Check) {
		ConformanceRuleValidator.disable_UpdateReportDtstartValid_Check = disable_UpdateReportDtstartValid_Check;
	}
	
	
	public static void setDisable_UpdateReportDurationValid_Check(
			boolean disable_UpdateReportDurationValid_Check) {
		ConformanceRuleValidator.disable_UpdateReportDurationValid_Check = disable_UpdateReportDurationValid_Check;
	}
	
	
	public static void setDisableUpdatedReport_CancelReportRequestIDMatch_Check(
			boolean disableUpdatedReport_CancelReportRequestIDMatch_Check) {
		ConformanceRuleValidator.disableUpdatedReport_CancelReportRequestIDMatch_Check = disableUpdatedReport_CancelReportRequestIDMatch_Check;
	}
	
	public static void setDisable_VtnIDValid_Check(
			boolean disable_VtnIDValid_Check) {
		ConformanceRuleValidator.disable_VtnIDValid_Check = disable_VtnIDValid_Check;
	}

	public static void setDisableDistributeEvent_ResponseCodeSuccess_Check(
			boolean disableDistributeEvent_ResponseCodeSuccess_Check) {
		ConformanceRuleValidator.disableDistributeEvent_ResponseCodeSuccess_Check = disableDistributeEvent_ResponseCodeSuccess_Check;
	}
	//////////////////////////////////
	public static void setDisableCanceledOpt_ResponseCodeSuccess_Check(
			boolean disableCanceledOpt_ResponseCodeSuccess_Check) {
		ConformanceRuleValidator.disableCanceledOpt_ResponseCodeSuccess_Check = disableCanceledOpt_ResponseCodeSuccess_Check;
	}

	public static void setDisableCreateReport_isRIDValid_Check_Check(
			boolean 	disableCreateReport_isRIDValid_Check) {
		ConformanceRuleValidator.	disableCreateReport_isRIDValid_Check = 	disableCreateReport_isRIDValid_Check;
	}
	
	
	public static void setDisableCreateReport_reportRequestIDNonZero_Check(
			boolean disableCreateReport_reportRequestIDNonZero_Check) {
		ConformanceRuleValidator.disableRegister_Create_reportRequestIDZero_Check = disableCreateReport_reportRequestIDNonZero_Check;
	}
	
	
	public static void setDisableHistoryUsageGreenbuttonGranularityReportBackDurationZero_Check(
			boolean disableHistoryUsageGreenbuttonGranularityReportBackDurationZero_Check) {
		ConformanceRuleValidator.disableHistoryUsageGreenbuttonGranularityReportBackDurationZero_Check = disableHistoryUsageGreenbuttonGranularityReportBackDurationZero_Check;
	}
	
	
	public static void setDisableCreateReport_reportRequestIDUnique_Check(
			boolean disableCreateReport_reportRequestIDUnique_Check) {
		ConformanceRuleValidator.disableCreateReport_reportRequestIDUnique_Check = disableCreateReport_reportRequestIDUnique_Check;
	}
	
	public static void setDisableUpdateReport_isRIDValid_Check(
			boolean 	disableUpdateReport_isRIDValid_Check) {
		ConformanceRuleValidator.disableUpdateReport_isRIDValid_Check= disableUpdateReport_isRIDValid_Check;
	}
	
	
	public static void setDisableRegisterReport_IntervalsDtstartEiReportIDNotPresent_Check(
			boolean disableRegisterReport_IntervalsDtstartEiReportIDNotPresent_Check) {
		ConformanceRuleValidator.disableRegisterReport_IntervalsDtstartEiReportIDNotPresent_Check= disableRegisterReport_IntervalsDtstartEiReportIDNotPresent_Check;
	}
	
	
	public static void setDisableRegisterReport_ReportNameReportTypeItemBaseReadingTypeCombination_Check(
			boolean disableRegisterReport_ReportNameReportTypeItemBaseReadingTypeCombination_Check) {
		ConformanceRuleValidator.disableRegisterReport_ReportNameReportTypeItemBaseReadingTypeCombination_Check= disableRegisterReport_ReportNameReportTypeItemBaseReadingTypeCombination_Check;
	}
	
	
	public static void setDisableCreateReport_PeriodicTelemetryReportContainsRequiredElements_Check(
			boolean disableCreateReport_PeriodicTelemetryReportContainsRequiredElements_Check) {
		ConformanceRuleValidator.disableCreateReport_PeriodicTelemetryReportContainsRequiredElements_Check= disableCreateReport_PeriodicTelemetryReportContainsRequiredElements_Check;
	}
	
	public static void setDisableCreateReport_PeriodicReportReportDurationNotLessThanMinimumSamplingPeriod_Check(
			boolean disableCreateReport_PeriodicReportReportDurationNotLessThanMinimumSamplingPeriod_Check) {
		ConformanceRuleValidator.disableCreateReport_PeriodicReportReportDurationNotLessThanMinimumSamplingPeriod_Check= disableCreateReport_PeriodicReportReportDurationNotLessThanMinimumSamplingPeriod_Check;
	}
	
	public static void setDisableUpdateReport_RequiredElementsAndReportName_Check(
			boolean 	disableUpdateReport_RequiredElementsAndReportName_Check) {
		ConformanceRuleValidator.disableUpdateReport_RequiredElementsAndReportName_Check= disableUpdateReport_RequiredElementsAndReportName_Check;
	}
	
	public static void setDisableCanceledPartyRegistration_ResponseCodeSuccess_Check(
			boolean disableCanceledPartyRegistration_ResponseCodeSuccess_Check) {
		ConformanceRuleValidator.disableCanceledPartyRegistration_ResponseCodeSuccess_Check = disableCanceledPartyRegistration_ResponseCodeSuccess_Check;
	}
	
	public static void setDisableCanceledReport_ResponseCodeSuccess_Check(
			boolean disableCanceledReport_ResponseCodeSuccess_Check) {
		ConformanceRuleValidator.disableCanceledReport_ResponseCodeSuccess_Check = disableCanceledReport_ResponseCodeSuccess_Check;
	}
	
	public static void setDisableCreatedPartyRegistration_ResponseCodeSuccess_Check(
			boolean disableCreatedPartyRegistration_ResponseCodeSuccess_Check) {
		ConformanceRuleValidator.disableCreatedPartyRegistration_ResponseCodeSuccess_Check = disableCreatedPartyRegistration_ResponseCodeSuccess_Check;
	}
	
	public static void setDisableCreatedReport_ResponseCodeSuccess_Check(
			boolean disableCreatedReport_ResponseCodeSuccess_Check) {
		ConformanceRuleValidator.disableCreatedReport_ResponseCodeSuccess_Check = disableCreatedReport_ResponseCodeSuccess_Check;
	}
	
	public static void setDisableUpdatedReport_ResponseCodeSuccess_Check(
			boolean disableUpdatedReport_ResponseCodeSuccess_Check) {
		ConformanceRuleValidator.disableUpdatedReport_ResponseCodeSuccess_Check = disableUpdatedReport_ResponseCodeSuccess_Check;
	}
	
	public static void setDisableRegisteredReport_ResponseCodeSuccess_Check(
			boolean disableRegisteredReport_ResponseCodeSuccess_Check) {
		ConformanceRuleValidator.disableRegisteredReport_ResponseCodeSuccess_Check = disableRegisteredReport_ResponseCodeSuccess_Check;
	}
	
	public static void setDisableCreatedOpt_ResponseCodeSuccess_Check(
			boolean disableCreatedOpt_ResponseCodeSuccess_Check) {
		ConformanceRuleValidator.disableCreatedOpt_ResponseCodeSuccess_Check = disableCreatedOpt_ResponseCodeSuccess_Check;
	}

	public static void setDisableDistributeEvent_EventIDsUnique_ValidCheck(
			boolean disableDistributeEvent_EventIDsUnique_ValidCheck) {
		ConformanceRuleValidator.disableDistributeEvent_EventIDsUnique_ValidCheck = disableDistributeEvent_EventIDsUnique_ValidCheck;
	}

	
	public static void setDisableRegisterReport_DurationValid_Check(
			boolean disableRegisterReport_DurationValid_Check) {
		ConformanceRuleValidator.disableRegisterReport_DurationValid_Check = disableRegisterReport_DurationValid_Check;
	}
	
	public static void setDisableDistributeEvent_EventStatusValid_ValidCheck(
			boolean disableDistributeEvent_EventStatusValid_ValidCheck) {
		ConformanceRuleValidator.disableDistributeEvent_EventStatusValid_ValidCheck = disableDistributeEvent_EventStatusValid_ValidCheck;
	}

	
	public static void setDisableRegisterReport_SamplingRateFoundInTelemertyMetadata_Check(
			boolean disableRegisterReport_SamplingRateFoundInTelemertyMetadata_Check) {
		ConformanceRuleValidator.disableRegisterReport_SamplingRateFoundInTelemertyMetadata_Check = disableRegisterReport_SamplingRateFoundInTelemertyMetadata_Check;
	}
	
	public static void setDisableRegisterReport_ReportSpecifierIDUnique_Checkk(
			boolean disableRegisterReport_ReportSpecifierIDUnique_Check) {
		ConformanceRuleValidator.disableRegisterReport_ReportSpecifierIDAndRIDUnique_Check = disableRegisterReport_ReportSpecifierIDUnique_Check;
	}

	
	public static void setDisable_AtLeastOneEiTargetMatch_ValidCheck(
			boolean disable_AtLeastOneEiTargetMatch_ValidCheck) {
		ConformanceRuleValidator.disable_AtLeastOneEiTargetMatch_ValidCheck = disable_AtLeastOneEiTargetMatch_ValidCheck;
	}

	public static void setDisableDistributeEvent_EventOrderValid_Check(
			boolean disableDistributeEvent_EventOrderValid_Check) {
		ConformanceRuleValidator.disableDistributeEvent_EventOrderValid_Check = disableDistributeEvent_EventOrderValid_Check;
	}

	public static void setDisableDistributeEvent_ActiveCurrentValueMatchCurrentPayload_Check(
			boolean disableDistributeEvent_ActiveCurrentValueMatchCurrentPayload_Check) {
		ConformanceRuleValidator.disableDistributeEvent_ActiveCurrentValueMatchCurrentPayload_Check = disableDistributeEvent_ActiveCurrentValueMatchCurrentPayload_Check;
	}

	public static void setDisableDistributeEvent_NonActiveCurrentValueValid_Check(
			boolean disableDistributeEvent_NonActiveCurrentValueValid_Check) {
		ConformanceRuleValidator.disableDistributeEvent_NonActiveCurrentValueValid_Check = disableDistributeEvent_NonActiveCurrentValueValid_Check;
	}

	public static void setDisableDistributeEvent_PayloadFloatLimitValid_Check(
			boolean disableDistributeEvent_PayloadFloatLimitValid_Check) {
		ConformanceRuleValidator.disableDistributeEvent_PayloadFloatLimitValid_Check = disableDistributeEvent_PayloadFloatLimitValid_Check;
	}

	public static void setDisableDistributeEvent_ActivePeriodDurationValid_Check(
			boolean disableDistributeEvent_ActivePeriodDurationValid_Check) {
		ConformanceRuleValidator.disableDistributeEvent_ActivePeriodDurationValid_Check = disableDistributeEvent_ActivePeriodDurationValid_Check;
	}

	public static void setDisableCreatedEvent_RequestIDPresent_Check(
			boolean disableCreatedEvent_RequestIDPresent_Check) {
	}

	public static void setDisable_VenIDValid_Check(
			boolean disable_VenIDValid_Check) {
		ConformanceRuleValidator.disable_VenIDValid_Check = disable_VenIDValid_Check;
	}

	public static void setDisableCreatedEvent_ResponseCodeSuccess_Check(
			boolean disableCreatedEvent_ResponseCodeSuccess_Check) {
		ConformanceRuleValidator.disableCreatedEvent_ResponseCodeSuccess_Check = disableCreatedEvent_ResponseCodeSuccess_Check;
	}

	public static void setDisableOadrCreatedPartyRegistration_PULLPollFreq_Check(
			boolean disableOadrCreatedPartyRegistration_PULLPollFreq_Check) {
		ConformanceRuleValidator.disableOadrCreatedPartyRegistration_PULLPollFreq_Check = disableOadrCreatedPartyRegistration_PULLPollFreq_Check;
	}
	
	
	public static void setDisableDistributeEvent_SignalTypeAndUnitValidForSignalName(
			boolean disableDistributeEvent_SignalTypeAndUnitValidForSignalName_Check) {
		ConformanceRuleValidator.disableDistributeEvent_SignalTypeAndUnitValidForSignalName_Check = disableDistributeEvent_SignalTypeAndUnitValidForSignalName_Check;
	}
	
	public static void setDisableCreateReportReportSpecifierNotPresent_Check(
			boolean disableCreateReportReportSpecifierNotPresent_Check) {
		ConformanceRuleValidator.disableCreateReportReportSpecifierNotPresent_Check = disableCreateReportReportSpecifierNotPresent_Check;
	}
	
	public static void setDisableCreateReport_DoesNotContainExcludedReportIntervalProperties_Check(boolean disableCreateReport_DoesNotContainExcludedReportIntervalProperties_Check) {
		ConformanceRuleValidator.disableCreateReport_DoesNotContainExcludedReportIntervalProperties_Check = disableCreateReport_DoesNotContainExcludedReportIntervalProperties_Check;
	}
	
	
	public static void setDisableCreateReport_ReadingTypeValid_Check(boolean disableCreateReport_ReadingTypeValid_Check) {
		ConformanceRuleValidator.disableCreateReport_ReadingTypeValid_Check = disableCreateReport_ReadingTypeValid_Check;
	}
	
	public static void setDisableRequestIDValid_Check(
			boolean disableRequestIDValid_Check) {
		ConformanceRuleValidator.disableRequestIDValid_Check = disableRequestIDValid_Check;
	}

	public static boolean getDisableRequestIDValid_Check(){
		return ConformanceRuleValidator.disableRequestIDValid_Check;
	}
	
	public static void setDisable_EventIDValid_Check(
			boolean disable_EventIDValid_Check) {
		ConformanceRuleValidator.disable_EventIDValid_Check = disable_EventIDValid_Check;
	}

	public static void setDisableCreatedEvent_EIResponsesValid_Check(
			boolean disableCreatedEvent_EIResponsesValid_Check) {
		ConformanceRuleValidator.disableCreatedEvent_EIResponsesValid_Check = disableCreatedEvent_EIResponsesValid_Check;
	}


	public static void setDisableResponseEvent_ResponseCodeSuccess_Check(
			boolean disableResponseEvent_ResponseCodeSuccess_Check) {
		ConformanceRuleValidator.disableResponseEvent_ResponseCodeSuccess_Check = disableResponseEvent_ResponseCodeSuccess_Check;
	}

	public static void setDisablePoll_PUSH_Check(
			boolean disablePoll_PUSH_Check) {
		ConformanceRuleValidator.disablePoll_PUSH_Check = disablePoll_PUSH_Check;
	}
	
	public static void setDisable_ActiveRegistration_Check(
			boolean disable_ActiveRegistration_Check) {
		ConformanceRuleValidator.disable_ActiveRegistration_Check = disable_ActiveRegistration_Check;
	}

	public static boolean isValid(String data, ServiceType service,Direction direction) {
		Trace trace = TestSession.getTraceObj();
		
		
		if (disableAllConformanceCheck == true) {
			trace.getLogFileContentTrace()
					.append("All Conformance check is disabled; No Conformance check is performed\n");
			return true;
		}

		if (data == null || data.length() < 1)
			return false;

		try {
			if (data.contains("schemaLocation")) {

				logFailure(null,"Messages sent between VENs and VTNs shall *not* include a schemaLocation attribute");

				return false;
			}

			@SuppressWarnings("rawtypes")
			Class objectType = SchemaHelper.getObjectType(data);  
			if(!objectType.equals(OadrRequestReregistrationType.class) &&!objectType.equals(OadrResponseType.class) &&!objectType.equals(OadrPollType.class) &&!objectType.equals(OadrCreatePartyRegistrationType.class) && !objectType.equals(OadrCreatedPartyRegistrationType.class) && !objectType.equals(OadrQueryRegistrationType.class) && !objectType.equals(OadrCanceledPartyRegistrationType.class) && !objectType.equals(OadrCancelPartyRegistrationType.class)){
				
				if(!disable_ActiveRegistration_Check){
					
					String registerationID = new XMLDBUtil().getRegistrationID();
					if(registerationID==null || registerationID.trim().equals("")){
	
						System.out.print("\n*** Registration is not active, stopping test case ***\n\n");

						logFailure("disable_ActiveRegistration_Check","*************Registration is not active*************");

						return false;
					}
				}else{
					skippingConformanceRut("disable_ActiveRegistration_Check","Check if Registration is active");
				}
			}
				
			if (objectType.equals(OadrDistributeEventType.class)) {
				trace.getLogFileContentTrace()
						.append("Performing OadrDistributeEvent Conformance Rule validation\n");

				if (trace != null) {
					OadrDistributeEventType oadrDistributeEvent = DistributeEventSignalHelper
							.createOadrDistributeEventFromString(data);

					if (!disableSchemaVersionValid_Check) {
						
						if (!OadrUtil.isSchemaVersionValid(oadrDistributeEvent.getSchemaVersion())) {
							logFailure("disableSchemaVersionValid_Check","SchemaVersion is not valid");
							
							return false;
						}
					} else {
						skippingConformanceRut("disableSchemaVersionValid_Check","Check to see if SchemaVersion is valid");
					}

					
					if (!disableDistributeEvent_RequestIDPresent_Check) {
						boolean isRequestIDPresent = DistributeEventConformanceValidationHelper
								.isRequestIDPresent(oadrDistributeEvent);

						if (!isRequestIDPresent) {
							logFailure("disableDistributeEvent_RequestIDPresent_Check","RequestID is not present");
							
							return false;
						}
					} else {
						skippingConformanceRut("disableDistributeEvent_RequestIDPresent_Check","RequestID Present Check");
					
					}

					if (!disable_MaketContext_Check) {
						boolean isMarketContextValid = DistributeEventConformanceValidationHelper
								.isMarketContextValid(oadrDistributeEvent);

						if (!isMarketContextValid) {
							logFailure("disable_MaketContext_Check","MarketContext is not Valid");
							
							return false;
						}
					} else {
						skippingConformanceRut("disable_MaketContext_Check","MarketContext valid check");
					}

					if (!disable_UIDValid_Check) {
						boolean isUIDValid = DistributeEventConformanceValidationHelper
								.isUIDValid(oadrDistributeEvent);

						if (!isUIDValid) {
							logFailure("disable_UIDValid_Check","Within a single eiEventSignal, UID must be expressed as an interval number with a base of 0 and an increment of 1 for each subsequent interval");
							return false;
						}
					} else {
						skippingConformanceRut("disable_UIDValid_Check","UID valid check");
					}

					if (!disableDistributeEvent_MultipleVENIDFound_Check) {
						boolean isMultipleVENIDFound = DistributeEventConformanceValidationHelper
								.isMultipleVENIDFound(oadrDistributeEvent);

						if (!isMultipleVENIDFound) {
							logFailure("disableDistributeEvent_MultipleVENIDFound_Check","VTN must not include more than one venID in the oadrDistributeEvent eitarget");
							return false;
						}
					} else {
						skippingConformanceRut("disableDistributeEvent_MultipleVENIDFound_Check","VTN must not include more than one venID in the oadrDistributeEvent eitarget");
					}

					if (!disable_VtnIDValid_Check) {
						boolean isVtnIDValid = DistributeEventConformanceValidationHelper
								.isVtnIDValid(oadrDistributeEvent);

						if (!isVtnIDValid) {
							logFailure("disable_VtnIDValid_Check","Unknown VTN ID has been received");
							return false;
						}
					} else {
						skippingConformanceRut("disable_VtnIDValid_Check","VTN ID valid check");
					}

					if (!disableDistributeEvent_ResponseCodeSuccess_Check) {
						boolean isResponseCodeSuccess = DistributeEventConformanceValidationHelper
								.isResponseCodeSuccess(oadrDistributeEvent);

						if (!isResponseCodeSuccess) {
							logFailure("disableDistributeEvent_ResponseCodeSuccess_Check","Error Response Code found");
							return false;
						}
					} else {
						skippingConformanceRut("disableDistributeEvent_ResponseCodeSuccess_Check","Success Response Code check");
					}

					if (!disableDistributeEvent_EventIDsUnique_ValidCheck) {
						boolean isEventIDsUnique = DistributeEventConformanceValidationHelper
								.isEventIDsUnique(oadrDistributeEvent);
						if (!isEventIDsUnique) {
							logFailure("disableDistributeEvent_EventIDsUnique_ValidCheck","Event Id is not unique");
							return false;
						}
					} else {
						skippingConformanceRut("disableDistributeEvent_EventIDsUnique_ValidCheck","Event Id unique check");
					}

					if (!disable_CreatedDateTimeWithinExpectedWindow_Check) {
						String createdDateTime = DistributeEventConformanceValidationHelper
								.isCreatedDateTimeWithinFiveMin(oadrDistributeEvent);
						if (createdDateTime.length() > 1) {
							logFailure("disable_CreatedDateTimeWithinExpectedWindow_Check","CreatedDateTime in oadrDisributeEVent payloads is not within +- 5 minute of the CurrentTime "+ createdDateTime);
							return false;
						}
					} else {
						skippingConformanceRut("disable_CreatedDateTimeWithinExpectedWindow_Check","Check to see if CreatedDateTime to be within +- 5 minute of the CurrentTime");
					}

					if (!disableDistributeEvent_EventStatusValid_ValidCheck) {
						boolean isEventStatusValid = DistributeEventConformanceValidationHelper
								.isEventStatusValid(oadrDistributeEvent);
						if (!isEventStatusValid) {
							logFailure("disableDistributeEvent_EventStatusValid_ValidCheck","EventStatus received is not valid");
							return false;
						}
					} else {
						skippingConformanceRut("disableDistributeEvent_EventStatusValid_ValidCheck","EventStatus should be valid");
					}
					if (!disable_AtLeastOneEiTargetMatch_ValidCheck) {
						boolean isAtLeastOneEiTargetMatch = DistributeEventConformanceValidationHelper
								.isAtLeastOneEiTargetMatch(oadrDistributeEvent);
						if (!isAtLeastOneEiTargetMatch) {
							logFailure("disable_AtLeastOneEiTargetMatch_ValidCheck","None of eiTarget sub elements match");
							return false;
						}
					} else {
						skippingConformanceRut("disable_AtLeastOneEiTargetMatch_ValidCheck","At least one eiTarget sub elements should match");
					}

					if (TestSession.getMode().equals(ModeType.PULL)) {
						if (!disableDistributeEvent_EIResponsePresent_Check) {
							boolean isEIResponsePresent = DistributeEventConformanceValidationHelper
									.isEIResponsePresent(oadrDistributeEvent);
							if (!isEIResponsePresent) {
								logFailure("disableDistributeEvent_EIResponsePresent_Check","EIResponse element is not present");
								return false;
							}
						} else {
							skippingConformanceRut("disableDistributeEvent_EIResponsePresent_Check","EIResponse element present check");
							}
					}
					if (!disableDistributeEvent_EventOrderValid_Check) {
						boolean isEventOrderValid = DistributeEventConformanceValidationHelper
								.isEventOrderValid(oadrDistributeEvent);
						if (!isEventOrderValid) {
							logFailure("disableDistributeEvent_EventOrderValid_Check","EventOrder does not match the expected order");
							return false;
						}
					} else {
						skippingConformanceRut("disableDistributeEvent_EventOrderValid_Check","EventOrder valid check");
					}

					if (!disableDistributeEvent_ActiveCurrentValueMatchCurrentPayload_Check) {
						boolean isActiveCurrentValueMatchCurrentPayload = DistributeEventConformanceValidationHelper
								.isActiveCurrentValueMatchCurrentPayload(oadrDistributeEvent);
						if (!isActiveCurrentValueMatchCurrentPayload) {
							logFailure("disableDistributeEvent_ActiveCurrentValueMatchCurrentPayload_Check","Active Event's CurrentValue did not Match CurrentSignalPayload");
							return false;
						}
					} else {
						skippingConformanceRut("disableDistributeEvent_ActiveCurrentValueMatchCurrentPayload_Check","Active Event's CurrentValue match CurrentSignalPayload check");
					}

					if (!disableDistributeEvent_NonActiveCurrentValueValid_Check) {
						boolean isNonActiveCurrentValueValid = DistributeEventConformanceValidationHelper
								.isNonActiveCurrentValueValid(oadrDistributeEvent);
						if (!isNonActiveCurrentValueValid) {
							logFailure("disableDistributeEvent_NonActiveCurrentValueValid_Check","The currentValue must be set to 0 (normal) when the event is not active");
							return false;
						}
					} else {
						skippingConformanceRut("disableDistributeEvent_NonActiveCurrentValueValid_Check","Check to see if currentValue is 0 (normal) when the event is not active");
					}

					if (!disableDistributeEvent_PayloadFloatLimitValid_Check) {
						boolean isPayloadFloatLimitValid = DistributeEventConformanceValidationHelper
								.isPayloadFloatLimitValid(oadrDistributeEvent);
						if (!isPayloadFloatLimitValid) {
							logFailure("disableDistributeEvent_PayloadFloatLimitValid_Check","For a eiEventSignal with a signalName of 'simple', the values used for signalPayload must be limited to 0=normal; 1=moderate; 2=high; 3=special");
							return false;
						}
					} else {
						skippingConformanceRut("disableDistributeEvent_PayloadFloatLimitValid_Check","Check for a eiEventSignal with a signalName of 'simple', the values used for signalPayload must be limited to 0=normal; 1=moderate; 2=high; 3=special");

					}

					if (!disableDistributeEvent_ActivePeriodDurationValid_Check) {
						boolean isActivePeriodDurationValid = DistributeEventConformanceValidationHelper
								.isActivePeriodDurationValid(oadrDistributeEvent);
						if (!isActivePeriodDurationValid) {
							logFailure("disableDistributeEvent_ActivePeriodDurationValid_Check","Signal interval durations for a given event must add up to eiEvent eiActivePeriod duration");
							return false;
						}
					} else {
						skippingConformanceRut("disableDistributeEvent_ActivePeriodDurationValid_Check","Check to see if signal interval durations for a given event adds up to eiEvent eiActivePeriod duration");
					}

					if (!disableDistributeEvent_EventNonOverlappingPresent_Check) {
						boolean isEventNonOverlapping = DistributeEventConformanceValidationHelper
								.isEventNonOverlapping(oadrDistributeEvent);

						if (!isEventNonOverlapping) {
							logFailure("disableDistributeEvent_EventNonOverlappingPresent_Check","There are overlapping events in the same market context.");
							return false;
						}
					} else {
						skippingConformanceRut("disableDistributeEvent_EventNonOverlappingPresent_Check","Check to see if there are overlapping events in the same market context.");

					}

					if (!disableDistributeEvent_AllPreviousEvntPresent_Check) {
						boolean isAllPreviousEventsReceived = DistributeEventConformanceValidationHelper
								.isAllPreviousEventsReceived(
										oadrDistributeEvent, service);

						if (!isAllPreviousEventsReceived) {
							logFailure("disableDistributeEvent_AllPreviousEvntPresent_Check","OadrDistributeEvent MUST contain all existing events which have the eventStatus element set to either FAR, NEAR, or ACTIVE. Events with an eventStatus of cancelled MUST be included in the payload upon change to the modificationNumber and MAY be included in subsequent payloads");
							return false;
						}
					} else {
						skippingConformanceRut("disableDistributeEvent_AllPreviousEvntPresent_Check","Check to see if OadrDistributeEvent contains all existing events which have the eventStatus element set to either FAR, NEAR, or ACTIVE. Events with an eventStatus of cancelled MUST be included in the payload upon change to the modificationNumber and MAY be included in subsequent payloads");
					}
					
					
					if (!disableDistributeEvent_DTStartNotInEventSignalsOrEventBaselines_Check) {
						boolean isDTStartNotInEventSignalsOrEventBaselines = DistributeEventConformanceValidationHelper
								.isDTStartNotInEventSignalsOrEventBaselines(
										oadrDistributeEvent);

						if (!isDTStartNotInEventSignalsOrEventBaselines) {
							logFailure("disableDistributeEvent_DTStartNotInEventSignalsOrEventBaselines_Check","Confirm dtstart is not present in intervals for eiEventSignals or eiEventBaselines");
							return false;
						}
					} else {
						skippingConformanceRut("disableDistributeEvent_DTStartNotInEventSignalsOrEventBaselines_Check","Check to confirm if dtstart is not present in intervals for eiEventSignals or eiEventBaselines");
					}
					
					
					if (!disableDistributeEvent_SignalIDUnique_Check) {
						boolean isSignalIDUnique = DistributeEventConformanceValidationHelper
								.isSignalIDUnique(
										oadrDistributeEvent);

						if (!isSignalIDUnique) {
							logFailure("disableDistributeEvent_SignalIDUnique_Check","Confirm that signalID is unique within the scope of the event if the event has multiple signals");
							return false;
						}
					} else {
						skippingConformanceRut("disableDistributeEvent_SignalIDUnique_Check","Check to confirm that signalID is unique within the scope of the event if the event has multiple signals");
					}
										

					if (!disable_VenIDValid_Check) {
						boolean isVenIDValid = DistributeEventConformanceValidationHelper
								.isVenIDValid(oadrDistributeEvent,direction);

						if (!isVenIDValid) {
							logFailure("disable_VenIDValid_Check","Unknown VEN ID has been received");
							return false;
						}
					} else {
						skippingConformanceRut("disable_VenIDValid_Check","Unknown VEN ID check");
						
					}
					
					
					if (!disableDistributeEvent_SignalTypeAndUnitValidForSignalName_Check) {
						boolean isSignalTypeAndUnitValidForSignalName = DistributeEventConformanceValidationHelper
								.isSignalTypeAndUnitValidForSignalName(oadrDistributeEvent);

						if (!isSignalTypeAndUnitValidForSignalName) {
							logFailure("disableDistributeEvent_SignalTypeAndUnitValidForSignalName_Check","Confirm that the signalType and units are correct for a given signalName");
							return false;
						}
					} else {
						skippingConformanceRut("disableDistributeEvent_SignalTypeAndUnitValidForSignalName_Check","Confirm that the signalType and units are correct for a given signalName");
					}
					
					
					if (!disableDistributeEvent_OnePayloadFloatPresentInEachInterval_Check) {
						boolean isSignalTypeAndUnitValidForSignalName = DistributeEventConformanceValidationHelper
								.isOnePayloadFloatPresentInEachInterval(oadrDistributeEvent);

						if (!isSignalTypeAndUnitValidForSignalName) {
							logFailure("disableOnePayloadFloatPresentInEachInterval_Check","Only one payloadFloat element should be present in each signal interval");
							return false;
						}
					} else {
						skippingConformanceRut("disableOnePayloadFloatPresentInEachInterval_Check","Check to see if only one payloadFloat element is be present in each signal interval");

					}

					if (!disableDistributeEvent_EventBaselineDurationValid_Check) {
						boolean isBaselineDurationValid = DistributeEventConformanceValidationHelper
								.isBaselineDurationValid(oadrDistributeEvent);

						if (!isBaselineDurationValid) {
							logFailure("disableDistributeEvent_EventBaselineDurationValid_Check","If a baseline is included in the event, make sure that the sum of interval durations is equal to ei:EventSignal:eiEventBaseline:duration");
							return false;
						}
					} else {
						skippingConformanceRut("disableDistributeEvent_EventBaselineDurationValid_Check","Check to see if a baseline is included in the event then the sum of interval durations is equal to ei:EventSignal:eiEventBaseline:duration.");
					}
					
					if (!disableDistributeEvent_EINotificationSubElementOfActivePeriod_Check) {
						boolean isEINotificationSubElementOfActivePeriod = DistributeEventConformanceValidationHelper
								.isEINotificationSubElementOfActivePeriod(oadrDistributeEvent);

						if (!isEINotificationSubElementOfActivePeriod) {
							logFailure("disableDistributeEvent_EINotificationSubElementOfActivePeriod_Check","eiNotification should be a sub element of activePeriod");
							return false;
						}
					} else {
						skippingConformanceRut("disableDistributeEvent_EINotificationSubElementOfActivePeriod_Check","Check to see if eiNotification is a sub element of activePeriod");
					}
					
					
					if (!disableDeviceClass_Check) {
						boolean isDeviceClassValuesPresent = DistributeEventConformanceValidationHelper
								.isDeviceClassValuesPresent(oadrDistributeEvent);

						if (!isDeviceClassValuesPresent) {
							logFailure("disableDeviceClass_Check","Confirm that eiEventSignal:eiTarget:endDeviceAsset:mrid is a valid Device Class");
							return false;
						}
					} else {
						skippingConformanceRut("disableDeviceClass_Check","Check to confirm if eiEventSignal:eiTarget:endDeviceAsset:mrid is a valid Device Class");
					}
					
				}
			} else if (objectType.equals(OadrCreatedEventType.class)) {
				if (trace != null) {
					trace.getLogFileContentTrace()
							.append("Performing OadrCreatedEventType Conformance Rule validation\n");
					OadrCreatedEventType oadrCreatedEvent = CreatedEventHelper
							.createCreatedEventFromString(data);

					if (!disableSchemaVersionValid_Check) {
						
						if (!OadrUtil.isSchemaVersionValid(oadrCreatedEvent.getSchemaVersion())) {
							logFailure("disableSchemaVersionValid_Check","SchemaVersion is not valid");
							
							return false;
						}
					} else {
						skippingConformanceRut("disableSchemaVersionValid_Check","Check to see if SchemaVersion is valid");
					}
					
					if (!disable_VenIDValid_Check) {
						boolean isVenIDValid = CreatedEventConformanceValidationHelper
								.isVenIDValid(oadrCreatedEvent);

						if (!isVenIDValid) {
							logFailure("disable_VenIDValid_Check","Unknown VEN ID has been received");
							return false;
						}
					} else {
						skippingConformanceRut("disable_VenIDValid_Check","VEN ID valid check");

					}
					if (!disableCreatedEvent_ResponseCodeSuccess_Check){

						boolean isEventResponseCodeSuccess = CreatedEventConformanceValidationHelper
								.isEventResponseCodeSuccess(oadrCreatedEvent);

						if (!isEventResponseCodeSuccess) {
							logFailure("disableCreatedEvent_ResponseCodeSuccess_Check","Received failure response code");
							return false;
						}
					} else{
						skippingConformanceRut("disableCreatedEvent_ResponseCodeSuccess_Check","Success response code check");
					}
					
					if (service.equals(ServiceType.VTN)
							|| service.equals(ServiceType.VTN_PULL_MODE)) {

						if (!disableRequestIDValid_Check) {

							boolean isRequestIDValid = CreatedEventConformanceValidationHelper
									.isRequestIDValid(oadrCreatedEvent);

							if (!isRequestIDValid) {
								logFailure("disableRequestIDValid_Check","The request ID received is not valid");
								return false;
							}
						} else {
							skippingConformanceRut("disableRequestIDValid_Check","The request ID valid check");						
							
						}

						if (!disable_EventIDValid_Check) {

							boolean isEventIDValid = CreatedEventConformanceValidationHelper
									.isEventIDValid(oadrCreatedEvent);

							if (!isEventIDValid) {
								logFailure("disable_EventIDValid_Check","The event ID received is unknown or not pending");
								return false;
							}
						} else {
							skippingConformanceRut("disable_EventIDValid_Check","The event ID valid check");
						}
					}

					if (!disableCreatedEvent_EIResponsesValid_Check) {

						boolean isEIResponsesValid = CreatedEventConformanceValidationHelper
								.isEIResponsesValid(oadrCreatedEvent);

						if (!isEIResponsesValid) {
							logFailure("disableCreatedEvent_EIResponsesValid_Check","Failure Response Code check");
							return false;
						}
					} else {
						skippingConformanceRut("disableCreatedEvent_EIResponsesValid_Check","Success Response Code check");
					}

				}
			} else if (objectType.equals(OadrRequestEventType.class)) {
				if (trace != null) {
					trace.getLogFileContentTrace()
							.append("Performing OadrRequestEventType Conformance Rule validation\n");
					OadrRequestEventType oadrRequestEvent = RequestEventSignalHelper
							.createOadrRequestEventFromString(data);

					if (!disable_VenIDValid_Check) {
						boolean isVenIDValid = RequestEventConformanceValidationHelper
								.isVenIDValid(oadrRequestEvent);

						if (!isVenIDValid) {
							logFailure("disable_VenIDValid_Check","Unknown VEN ID has been received");
							return false;
						}
					} else {
						skippingConformanceRut("disable_VenIDValid_Check","Unknown VEN ID received check");
					}

					if (!disableRequestEvent_isAllPrevCreatedEvntsRecvd_Check) {
						boolean isAllPreviousCreatedEventsReceived = RequestEventConformanceValidationHelper
								.isAllPreviousCreatedEventsReceived();

						if (!isAllPreviousCreatedEventsReceived) {
							logFailure("disableRequestEvent_isAllPrevCreatedEvntsRecvd_Check","In the pull model the expectation is that the VEN should respond to the oadrDisributeEvent before the next polling cycle. If the responseRequired is set to ALWAYS, the VEN MUST respond with a createdEvent message; if the responseRequired is set to NEVER, the VEN MUST NOT respond with a createdEvent message");
							return false;
						}
					} else {
						skippingConformanceRut("disableRequestEvent_isAllPrevCreatedEvntsRecvd_Check","In the pull model the expectation is that the VEN should respond to the oadrDisributeEvent before the next polling cycle. If the responseRequired is set to ALWAYS, the VEN MUST respond with a createdEvent message; if the responseRequired is set to NEVER, the VEN MUST NOT respond with a createdEvent message");
					}
					
					if (!disableSchemaVersionValid_Check) {
						
						if (!OadrUtil.isSchemaVersionValid(oadrRequestEvent.getSchemaVersion())) {
							logFailure("disableSchemaVersionValid_Check","SchemaVersion is not valid");
							
							return false;
						}
					} else {
						skippingConformanceRut("disableSchemaVersionValid_Check","Check to see if SchemaVersion is valid");
					}
					

				}
			}else if (objectType.equals(OadrResponseType.class)) {
				if (trace != null) {
					trace.getLogFileContentTrace()
							.append("Performing OadrResponse Conformance Rule validation\n");
					OadrResponseType oadrResponse = ResponseHelper
							.createOadrResponseFromString(data);

					if (!disableResponseEvent_ResponseCodeSuccess_Check) {
						boolean isResponseCodeSuccess = ResponseEventConformanceValidationHelper
								.isResponseCodeSuccess(oadrResponse);

						if (!isResponseCodeSuccess) {
							logFailure("disableResponseEvent_ResponseCodeSuccess_Check","Error Response Code found");
							return false;
						}

					} else {
						skippingConformanceRut("disableResponseEvent_ResponseCodeSuccess_Check","Success Response Code check");
					}
					
					if (!disableSchemaVersionValid_Check) {
						
						if (!OadrUtil.isSchemaVersionValid(oadrResponse.getSchemaVersion())) {
							logFailure("disableSchemaVersionValid_Check","SchemaVersion is not valid");
							
							return false;
						}
					} else {
						skippingConformanceRut("disableSchemaVersionValid_Check","Check to see if SchemaVersion is valid");
					}
		
					if (!disable_VenIDValid_Check) {
						boolean isVenIDValid = ResponseEventConformanceValidationHelper
								.isVenIDValid(oadrResponse,direction);

						if (!isVenIDValid) {
							logFailure("disable_VenIDValid_Check","Unknown VEN ID has been received");
							return false;
						}
					} else {
						skippingConformanceRut("disable_VenIDValid_Check","VEN ID valid check");
					}
				}
			}else if (objectType.equals(OadrPollType.class)) {
				if (trace != null) {
					trace.getLogFileContentTrace()
							.append("Performing OadrPollType Conformance Rule validation\n");

					if (!disablePoll_PUSH_Check) {

						if(!OadrUtil.isCreatePartyRegistration_PullModel()){
							logFailure("disablePoll_PUSH_Check","Received an oadrPoll in PUSH mode");
							return false;
						}
						
					}else{
						skippingConformanceRut("disablePoll_PUSH_Check","OadrPoll in PUSH mode check");
					}
					
				
				
					OadrPollType oadrPollType = PollEventSignalHelper.createOadrPollTypeFromString(data);
					
					if (!disableSchemaVersionValid_Check) {
						
						if (!OadrUtil.isSchemaVersionValid(oadrPollType.getSchemaVersion())) {
							logFailure("disableSchemaVersionValid_Check","SchemaVersion is not valid");
							
							return false;
						}
					} else {
						skippingConformanceRut("disableSchemaVersionValid_Check","Check to see if SchemaVersion is valid");
					}
			
					if (!disable_VenIDValid_Check) {
						boolean isVenIDValid = PollConforValHelper
								.isVenIDValid(oadrPollType,direction);

						if (!isVenIDValid) {
							logFailure("disable_VenIDValid_Check","Unknown VEN ID has been received");
							return false;
						}
					} else {
						skippingConformanceRut("disable_VenIDValid_Check","VEN ID valid check");
					}

					
				}
			}else if (objectType.equals(OadrCreateOptType.class)) {
				if (trace != null) {
					trace.getLogFileContentTrace()
							.append("Performing OadrCreateOpt Conformance Rule validation\n");
									}
				if (trace != null) {
					
					OadrCreateOptType oadrCreateOptType = CreateOptEventHelper.createCreateOptTypeEventFromString(data);
						if (!disable_MaketContext_Check) {
							boolean isMarketContextValid = CreateOptConformanceValidationHelper.isMarketContextValid(oadrCreateOptType);
							if (!isMarketContextValid) {
								logFailure("disable_MaketContext_Check","MarketContext is not valid");
								return false;
							}
						}else{
							skippingConformanceRut("disable_MaketContext_Check","MarketContext valid check");
						}
						
						
						if (!disableSchemaVersionValid_Check) {
							
							if (!OadrUtil.isSchemaVersionValid(oadrCreateOptType.getSchemaVersion())) {
								logFailure("disableSchemaVersionValid_Check","SchemaVersion is not valid");
								
								return false;
							}
						} else {
							skippingConformanceRut("disableSchemaVersionValid_Check","Check to see if SchemaVersion is valid");
						}
						
						
						if (!disable_AtLeastOneEiTargetMatch_ValidCheck) {
							boolean isAtLeastOneEiTargetMatch_ValidCheck = CreateOptConformanceValidationHelper.isAtLeastOneEiTargetMatch(oadrCreateOptType);
							if (!isAtLeastOneEiTargetMatch_ValidCheck) {
								logFailure("disable_AtLeastOneEiTargetMatch_ValidCheck","None of eiTarget sub elements match");
								return false;
							}
						}else{
							skippingConformanceRut("disable_AtLeastOneEiTargetMatch_ValidCheck","Atleast one eiTarget sub elements match check");
						}
						
						if (!disable_EventIDValid_Check) {

							boolean isEventIDValid = CreateOptConformanceValidationHelper
									.isEventIDValid(oadrCreateOptType);

							if (!isEventIDValid) {
								logFailure("disable_EventIDValid_Check","The event ID received is unknown or not pending");
								return false;
							}
						} else {
							skippingConformanceRut("disable_EventIDValid_Check","Event ID valid check");
						}
						
						if (!disableCreateOpt_isOptIDPresent_Check) {

							boolean isOptIDPresent = CreateOptConformanceValidationHelper
									.isOptIDPresent(oadrCreateOptType);

							if (!isOptIDPresent) {
								logFailure("disableCreateOpt_isOptIDPresent_Check","The optID is not present in payload");
								return false;
							}
						} else {
							skippingConformanceRut("disableCreateOpt_isOptIDPresent_Check","optID present check");
						}
						
						
						if (!disable_VenIDValid_Check) {
							boolean isVenIDValid = CreateOptConformanceValidationHelper
									.isVenIDValid(oadrCreateOptType,direction);

							if (!isVenIDValid) {
								logFailure("disable_VenIDValid_Check","Unknown VEN ID has been received");
								return false;
							}
						} else {
							skippingConformanceRut("disable_VenIDValid_Check","VEN ID valid check");

						}
						
						
						if (!disableDeviceClass_Check) {
							boolean isDeviceClassValuesPresent = CreateOptConformanceValidationHelper
									.isDeviceClassValuesPresent(oadrCreateOptType);

							if (!isDeviceClassValuesPresent) {
								logFailure("disableDeviceClass_Check","Confirm that oadrCreateOpt:oadrDeviceClass has valid Device Class");
								return false;
							}
						} else {
							skippingConformanceRut("disableDeviceClass_Check","Check to confirm if oadrCreateOpt:oadrDeviceClass has valid Device Class");
						}				
						
						if (!disableCreateOpt_VavailabilityExcludedProperties_Check) {
							boolean isVavailabilityExcludedPropertiesNotPresent = CreateOptConformanceValidationHelper
									.isVavailabilityExcludedPropertiesNotPresent(oadrCreateOptType);

							if (!isVavailabilityExcludedPropertiesNotPresent) {
								logFailure("disableDeviceClass_Check","VavailabilityType should not include tolerance, eiRampUp, eiRecovery, eiNotification");
								return false;
							}
						} else {
							skippingConformanceRut("disableDeviceClass_Check","Check to see that VavailabilityType does not include tolerance, eiRampUp, eiRecovery, and eiNotification");
						}
						
						
				
						if (!disableCreateOpt_VavailabilityMarketContextNotPresentIfEventIDPresent_Check) {
							boolean isVavailabilityMarketContextNotPresentIfEventIDPresent = CreateOptConformanceValidationHelper
									.isVavailabilityMarketContextNotPresentIfEventIDPresent(oadrCreateOptType);

							if (!isVavailabilityMarketContextNotPresentIfEventIDPresent) {
								logFailure("disableCreateOpt_VavailabilityMarketContextNotPresentIfEventIDPresent_Check","Confirm that when  eventID is specified in oadrCreateOpt, the VEN does NOT include a vavailabilityType or marketContext in the payload. When eventID is not specified in oadrCreateOpt, confirm that the ven includes vavailability.");
								return false;
							}
						} else {
							skippingConformanceRut("disableCreateOpt_VavailabilityMarketContextNotPresentIfEventIDPresent_Check","Check to confirm that when  eventID is specified in oadrCreateOpt, the VEN does NOT include a vavailabilityType or marketContext in the payload. When eventID is not specified in oadrCreateOpt, confirm that the ven includes vavailability.");

						}
												
						if (!disable_CreatedDateTimeWithinExpectedWindow_Check) {
							String createdDateTime = CreateOptConformanceValidationHelper
									.isCreatedDateTimeWithinOneMin(oadrCreateOptType);
							if (createdDateTime.length() > 1) {
								logFailure("disable_CreatedDateTimeWithinExpectedWindow_Check","CreatedDateTime in OadrCreateOpt payloads is not within +- 1 minute of the CurrentTime "+ createdDateTime);
								return false;
							}
						} else {
							skippingConformanceRut("disable_CreatedDateTimeWithinExpectedWindow_Check","Check to see if CreatedDateTime in OadrCreateOpt is within +- 1 minute of the CurrentTime");
						}

						
				
				}
				
		
			} else if (objectType.equals(OadrCreatedOptType.class)) {
				if (trace != null) {
					trace.getLogFileContentTrace()
							.append("Performing OadrCreatedOpt Conformance Rule validation\n");
					
					
					OadrCreatedOptType oadrCreatedOptType = CreatedOptEventHelper.createCreatedOptTypeEventFromString(data);
					
					if (!disableCreatedOpt_ResponseCodeSuccess_Check) {
						
						boolean isResponseCodeSuccess = CreatedOptConformanceValidationHelper
								.isResponseCodeSuccess(oadrCreatedOptType);

						if (!isResponseCodeSuccess) {
							logFailure("disableCreatedOpt_ResponseCodeSuccess_Check","Error Response Code found");
							return false;
						}
						
						
					} else {
						skippingConformanceRut("disableCreatedOpt_ResponseCodeSuccess_Check","Success Response Code check");

					}
					
					if (!disableSchemaVersionValid_Check) {
						
						if (!OadrUtil.isSchemaVersionValid(oadrCreatedOptType.getSchemaVersion())) {
							logFailure("disableSchemaVersionValid_Check","SchemaVersion is not valid");
							
							return false;
						}
					} else {
						skippingConformanceRut("disableSchemaVersionValid_Check","Check to see if SchemaVersion is valid");
					}
				}
			} else if (objectType.equals(OadrCancelOptType.class)) {
				if (trace != null) {
					trace.getLogFileContentTrace()
							.append("Performing OadrCancelOpt Conformance Rule validation\n");
				
					OadrCancelOptType oadrCancelOptType = CancelOptEventHelper.createCreatedOptTypeEventFromString(data);
			
					if (!disableSchemaVersionValid_Check) {
						
						if (!OadrUtil.isSchemaVersionValid(oadrCancelOptType.getSchemaVersion())) {
							logFailure("disableSchemaVersionValid_Check","SchemaVersion is not valid");
							
							return false;
						}
					} else {
						skippingConformanceRut("disableSchemaVersionValid_Check","Check to see if SchemaVersion is valid");
					}

					if (!disableCancelOpt_isOptIDValid_Check) {
						
						boolean isOptIDValid = CancelOptConformanceValidationHelper
								.isOptIDValid(oadrCancelOptType);

						if (!isOptIDValid) {
							logFailure("disableCancelOpt_isOptIDValid_Check","Not able to map the OptID received in OadrCancelOpt to OptID in OadrCreateOpt");
							return false;
						}
						
						
					} else {
						skippingConformanceRut("disableCancelOpt_isOptIDValid_Check","OptID valid check");
					}
				
					
					if (!disable_VenIDValid_Check) {
						boolean isVenIDValid = CancelOptConformanceValidationHelper
								.isVenIDValid(oadrCancelOptType,direction);

						if (!isVenIDValid) {
							logFailure("disable_VenIDValid_Check","Unknown VEN ID has been received");
							return false;
						}
					} else {
						skippingConformanceRut("disable_VenIDValid_Check","VEN ID valid check");
					}
					
				}
			} else if (objectType.equals(OadrCanceledOptType.class)) {
				
				if (trace != null) {
					
					OadrCanceledOptType oadrCanceledOptType = CanceledOptEventHelper.createCanceledOptTypeEventFromString(data);
					
					trace.getLogFileContentTrace()
							.append("Performing OadrCanceledOpt Conformance Rule validation\n");
									
					if (!disableCanceledOpt_ResponseCodeSuccess_Check) {
						
						boolean isResponseCodeSuccess = CanceledOptConformanceValidationHelper
								.isResponseCodeSuccess(oadrCanceledOptType);

						if (!isResponseCodeSuccess) {
							logFailure("disableCanceledOpt_ResponseCodeSuccess_Check","Error Response Code found");
							return false;
						}
						
						
					} else {
						skippingConformanceRut("disableCanceledOpt_ResponseCodeSuccess_Check","Success Response Code check");
					}
					
					if (!disableSchemaVersionValid_Check) {
						
						if (!OadrUtil.isSchemaVersionValid(oadrCanceledOptType.getSchemaVersion())) {
							logFailure("disableSchemaVersionValid_Check","SchemaVersion is not valid");
							
							return false;
						}
					} else {
						skippingConformanceRut("disableSchemaVersionValid_Check","Check to see if SchemaVersion is valid");
					}

										
				}
			}else if (objectType.equals(OadrCreatePartyRegistrationType.class)) {
				if (trace != null) {
					OadrCreatePartyRegistrationType oadrCreatePartyRegistrationType = CreatePartyRegistrationReqSignalHelper.createCreatePartyRegistrationRequestFromString(data);
					trace.getLogFileContentTrace()
							.append("Performing OadrCreatePartyRegistration Conformance Rule validation\n");
					
					if (!disableOadrCreatePartyRegistration_MatchDBVENAndRegID_Check) {
						boolean isVENIDAndRegIDMatchDB = CreatePartyRegistrationConforValHelper
								.isRegIDAndVENIDMatch(data);

						if (!isVENIDAndRegIDMatchDB) {
							logFailure("disableOadrCreatePartyRegistration_MatchDBVENAndRegID_Check","RegistrationID and VenID received in OadrCreatePartyRegistration did not match the Registration details in TestHarness Database");
							return false;
						}
					} else {
						skippingConformanceRut("disableOadrCreatePartyRegistration_MatchDBVENAndRegID_Check","Valid RegistrationID and VenID check");
					}
					
					
					if (!disableSchemaVersionValid_Check) {
						
						if (!OadrUtil.isSchemaVersionValid(oadrCreatePartyRegistrationType.getSchemaVersion())) {
							logFailure("disableSchemaVersionValid_Check","SchemaVersion is not valid");
							
							return false;
						}
					} else {
						skippingConformanceRut("disableSchemaVersionValid_Check","Check to see if SchemaVersion is valid");
					}
					
					if (!disableOadrCreatePartyRegistration_PUSHTransportAddress_Check) {
						boolean isTransportAddressPresent = CreatePartyRegistrationConforValHelper
								.isTransportAddressPresent(data);

						if (!isTransportAddressPresent) {
							logFailure("disableOadrCreatePartyRegistration_PUSHTransportAddress_Check","TransportAddress was expected in PUSH Model for OadrCreatePartyRegistration");
							return false;
						}
					} else {
						skippingConformanceRut("disableOadrCreatePartyRegistration_PUSHTransportAddress_Check","TransportAddress present check in PUSH Model");
					}

					if (!disableCreatePartyRegistration_HttpPullModelTrueFalse_Check) {
						boolean isDisableCreatePartyRegistration_HttpPullModelTrueFalse_Check = CreatePartyRegistrationConforValHelper
								.isCreatePartyRegistration_HttpPullModelTrueFalse_Check(data);

						if (!isDisableCreatePartyRegistration_HttpPullModelTrueFalse_Check) {
							logFailure("disableCreatePartyRegistration_HttpPullModelTrueFalse_Check","If the oadrTransportName is simpleHttp, then oadrHttpPullModel MUST be set to true or false.\n If oadrHttpPullModel is set to false, indicating a http push exchange model, the oadrTransportAddress MUST be included");
							return false;
						}
					} else {
						skippingConformanceRut("disableCreatePartyRegistration_HttpPullModelTrueFalse_Check","If the oadrTransportName is simpleHttp, then oadrHttpPullModel MUST be set to true or false.\n If oadrHttpPullModel is set to false, indicating a http push exchange model, the oadrTransportAddress MUST be included");
					}

				}
			}else if (objectType.equals(OadrCreatedPartyRegistrationType.class)) {
				
				if (trace != null) {
					trace.getLogFileContentTrace()
							.append("Performing OadrCreatedPartyRegistration Conformance Rule validation\n");
					OadrCreatedPartyRegistrationType oadrCreatedPartyRegistrationType = CreatedPartyRegistrationSignalHelper.createCreatedPartyRegistrationTypeFromString(data);

					if (!disable_VtnIDValid_Check) {
						boolean isVtnIDValid = CreatedPartyRegistrationConforValHelper
								.isVtnIDValid(oadrCreatedPartyRegistrationType);

						if (!isVtnIDValid) {
							logFailure("disable_VtnIDValid_Check","Unknown VTN ID has been received");
							
							return false;
						}
					} else {
						skippingConformanceRut("disable_VtnIDValid_Check","Unknown VTN ID valid check");
					}
					
					
					if (!disableSchemaVersionValid_Check) {
						
						if (!OadrUtil.isSchemaVersionValid(oadrCreatedPartyRegistrationType.getSchemaVersion())) {
							logFailure("disableSchemaVersionValid_Check","SchemaVersion is not valid");
							
							return false;
						}
					} else {
						skippingConformanceRut("disableSchemaVersionValid_Check","Check to see if SchemaVersion is valid");
					}
	
					if (!disableCreatedPartyRegistration_ResponseCodeSuccess_Check) {
						
						boolean isResponseCodeSuccess = CreatedPartyRegistrationConforValHelper
								.isResponseCodeSuccess(oadrCreatedPartyRegistrationType);

						if (!isResponseCodeSuccess) {
							logFailure("disableCreatedPartyRegistration_ResponseCodeSuccess_Check","Error Response Code found");
							
							return false;
						}
						
						
					} else {
						skippingConformanceRut("disableCreatedPartyRegistration_ResponseCodeSuccess_Check","Success Response Code check");
					}

					
					if (!disable_VenIDValid_Check) {
						boolean isVenIDValid = CreatedPartyRegistrationConforValHelper
								.isVenIDValid(oadrCreatedPartyRegistrationType);

						if (!isVenIDValid) {
							logFailure("disable_VenIDValid_Check","Unknown VEN ID has been received");
							return false;
						}
					} else {
						skippingConformanceRut("disable_VenIDValid_Check","VEN ID valid check");					}
					

					if (!disableOadrCreatedPartyRegistration_PULLPollFreq_Check){
						boolean isPollFreqSpecifiedForPULL = CreatedPartyRegistrationConforValHelper
								.isPollFreqSpecifiedForPULL(data);

						if (!isPollFreqSpecifiedForPULL) {
							logFailure("disableOadrCreatedPartyRegistration_PULLPollFreq_Check","If the VEN has registered with an http Pull model, then the oadrRequestedOadrPollFreqFreq MUST be included in the payload");
							return false;
						}
					} else {
						skippingConformanceRut("disableOadrCreatedPartyRegistration_PULLPollFreq_Check","Check if the VEN that has registered with an http Pull model has oadrRequestedOadrPollFreq in the payload");
					}
				

				}
			}else if (objectType.equals(OadrCancelPartyRegistrationType.class)) {
				if (trace != null) {
					trace.getLogFileContentTrace()
							.append("Performing OadrCancelPartyRegistration Conformance Rule validation\n");
					OadrCancelPartyRegistrationType cancelPartyRegistrationType = CancelPartyRegistrationHelper
							.createOadrCancelPartyRegistrationTypeFromString(data);

					if (!disableCancelPartyRegistration_IsRegistrationValid_Check) {
						boolean isRegistrationValid = CancelPartyRegistrationConfValHelper
								.isRegistrationValid(cancelPartyRegistrationType,direction);

						if (!isRegistrationValid) {
							logFailure("disableCancelPartyRegistration_IsRegistrationValid_Check","Active Registration to Cancel in not found");
							return false;
						}

					} else {
						skippingConformanceRut("disableCancelPartyRegistration_IsRegistrationValid_Check","Active Registration to Cancel found check");
					}
					
					if(!disableSchemaVersionValid_Check) {
						
						if (!OadrUtil.isSchemaVersionValid(cancelPartyRegistrationType.getSchemaVersion())) {
							logFailure("disableSchemaVersionValid_Check","SchemaVersion is not valid");
							
							return false;
						}
					} else {
						skippingConformanceRut("disableSchemaVersionValid_Check","Check to see if SchemaVersion is valid");
					}
	
					if (!disable_VenIDValid_Check) {
						boolean isVenIDValid = CancelPartyRegistrationConfValHelper
								.isVenIDValid(cancelPartyRegistrationType,direction);

						if (!isVenIDValid) {
							logFailure("disable_VenIDValid_Check","Unknown VEN ID has been received");
							return false;
						}
					} else {
						skippingConformanceRut("disable_VenIDValid_Check","VEN ID valid check");
					}
					
					
					
									}
			}else if (objectType.equals(OadrCanceledPartyRegistrationType.class)) {
				if (trace != null) {
					
					OadrCanceledPartyRegistrationType oadrCanceledPartyRegistrationType = CanceledPartyRegistrationHelper.createOadrCanceledPartyRegistrationTypeFromString(data);
					
					trace.getLogFileContentTrace()
							.append("Performing OadrCanceledPartyRegistration Conformance Rule validation\n");
									
					if (!disableCanceledPartyRegistration_ResponseCodeSuccess_Check) {
						
						boolean isResponseCodeSuccess = CanceledPartyConformanceValidationHelper
								.isResponseCodeSuccess(oadrCanceledPartyRegistrationType);

						if (!isResponseCodeSuccess) {
							logFailure("disableCanceledPartyRegistration_ResponseCodeSuccess_Check","Error Response Code found");
							
							return false;
						}
						
						
					} else {
						skippingConformanceRut("disableCanceledPartyRegistration_ResponseCodeSuccess_Check","Success Response Code check");
						}

					if(!disableSchemaVersionValid_Check) {
						
						if (!OadrUtil.isSchemaVersionValid(oadrCanceledPartyRegistrationType.getSchemaVersion())) {
							logFailure("disableSchemaVersionValid_Check","SchemaVersion is not valid");
							
							return false;
						}
					} else {
						skippingConformanceRut("disableSchemaVersionValid_Check","Check to see if SchemaVersion is valid");
					}
	
		
					if (!disable_VenIDValid_Check && (TestSession.getMode() == ModeType.PUSH)) {
						boolean isVenIDValid = CanceledPartyConformanceValidationHelper
								.isVenIDValid(oadrCanceledPartyRegistrationType,direction);

						if (!isVenIDValid) {
							logFailure("disable_VenIDValid_Check","Unknown VEN ID has been received");
							return false;
						}
					} else {
						skippingConformanceRut("disable_VenIDValid_Check","VEN ID valid check");

					}
					
					if (!disableRequestIDValid_Check) {

						boolean isRequestIDValid = CanceledPartyConformanceValidationHelper
								.isRequestIDsMatch(oadrCanceledPartyRegistrationType,direction);

						if (!isRequestIDValid) {
							logFailure("disableRequestIDValid_Check","The request ID received is not valid");
							return false;
						}
					} else {
						skippingConformanceRut("disableRequestIDValid_Check","The request ID valid check");						
						
					}
					
				}
			}else if (objectType.equals(OadrRegisteredReportType.class)) {
				if (trace != null) {
					trace.getLogFileContentTrace()
							.append("Performing OadrRegisteredReport Conformance Rule validation\n");
					
					OadrRegisteredReportType oadrRegisteredReportType = RegisteredReportEventHelper.createOadrRegisteredReportFromString(data);
					if (!disableRegisteredReport_ResponseCodeSuccess_Check) {
						
						boolean isResponseCodeSuccess = RegisteredReportConformanceValidationHelper
								.isResponseCodeSuccess(oadrRegisteredReportType);

						if (!isResponseCodeSuccess) {
							logFailure("disableRegisteredReport_ResponseCodeSuccess_Check","Error Response Code found");
							return false;
						}
						
					} else {
						skippingConformanceRut("disableRegisteredReport_ResponseCodeSuccess_Check","Success Response Code check");
					}
					
				if(!disableSchemaVersionValid_Check) {
						
						if (!OadrUtil.isSchemaVersionValid(oadrRegisteredReportType.getSchemaVersion())) {
							logFailure("disableSchemaVersionValid_Check","SchemaVersion is not valid");
							
							return false;
						}
					} else {
						skippingConformanceRut("disableSchemaVersionValid_Check","Check to see if SchemaVersion is valid");
					}
				
					if (!disable_VenIDValid_Check) {
						boolean isVenIDValid = RegisteredReportConformanceValidationHelper
								.isVenIDValid(oadrRegisteredReportType,direction);

						if (!isVenIDValid) {
							logFailure("disable_VenIDValid_Check","Unknown VEN ID has been received");
							return false;
						}
					} else {
						skippingConformanceRut("disable_VenIDValid_Check","VEN ID valid check");
					}
					
					if (!disableRequestIDValid_Check) {

						boolean isRequestIDValid = RegisteredReportConformanceValidationHelper
								.isRequestIDsMatch(oadrRegisteredReportType,direction);

						if (!isRequestIDValid) {
							logFailure("disableRequestIDValid_Check","The request ID received is not valid");
							return false;
						}
					} else {
						skippingConformanceRut("disableRequestIDValid_Check","The request ID valid check");						
						
					}

				}
			}else if (objectType.equals(OadrRegisterReportType.class)) {
				if (trace != null) {
		
					trace.getLogFileContentTrace()
					.append("Performing OadrRegisterReport Conformance Rule validation\n");
					
					OadrRegisterReportType oadrRegisterReportType = RegisterReportEventHelper.createOadrRegisterReportFromString(data);
						if (!disable_MaketContext_Check) {
							boolean isMarketContextValid = RegisterReportConformanceValidationHelper.isMarketContextValid(oadrRegisterReportType);
							if (!isMarketContextValid) {
								logFailure("disable_MaketContext_Check","MarketContext is not Valid");
								return false;
							}
						}else{
							skippingConformanceRut("disable_MaketContext_Check","MarketContext valid check");
						}
						
						
						
						if (!disableRegisterReport_RequestIDMatchPreviousCreateReport_Check) {
							boolean isReportRequestIDMatchPreviousCreateReport = RegisterReportConformanceValidationHelper.isReportRequestIDMatchPreviousCreateReport(oadrRegisterReportType,direction);
							if (!isReportRequestIDMatchPreviousCreateReport) {
								logFailure("disableRegisterReport_RequestIDMatchPreviousCreateReport_Check","The OadrRegisterReport.reportRequestID element must match the oadrCreateReport reportRequestID if the oadrRqgisterReport is being delivered as the result of a report request");
								return false;
							}
						}else{
							skippingConformanceRut("disableRegisterReport_RequestIDMatchPreviousCreateReport_Check","Check to see if the OadrRegisterReport.reportRequestID element match the oadrCreateReport reportRequestID if the oadrRqgisterReport is delivered as the result of a report request");
						}
						
						
							
						if (!disableRegisterReport_VenReportNameValid_Check){
							boolean isVenReportNameValid = RegisterReportConformanceValidationHelper.isVenReportNameValid(oadrRegisterReportType,direction);
							if (!isVenReportNameValid) {
								logFailure("disableRegisterReport_VenReportNameValid_Check","OadrRegisterReport coming from the VEN must contain TELEMETERY_STATUS and TELEMETERY_USAGE metadata reports");
								return false;
							}
						}else{
							skippingConformanceRut("disableRegisterReport_VenReportNameValid_Check","Check to see if OadrRegisterReport coming from the VEN contains either TELEMETERY_STATUS or TELEMETERY_USAGE metadata report");
						}
						
						
						if(!disableSchemaVersionValid_Check) {
								
								if (!OadrUtil.isSchemaVersionValid(oadrRegisterReportType.getSchemaVersion())) {
									logFailure("disableSchemaVersionValid_Check","SchemaVersion is not valid");
									
									return false;
								}
							} else {
								skippingConformanceRut("disableSchemaVersionValid_Check","Check to see if SchemaVersion is valid");
							}
						
						
						if (!disable_AtLeastOneEiTargetMatch_ValidCheck) {
							boolean isAtLeastOneEiTargetMatch_ValidCheck = RegisterReportConformanceValidationHelper.isAtLeastOneEiTargetMatch(oadrRegisterReportType);
							if (!isAtLeastOneEiTargetMatch_ValidCheck) {
								logFailure("disable_AtLeastOneEiTargetMatch_ValidCheck","None of eiTarget sub elements match");
								return false;
							}
						}else{
							skippingConformanceRut("disable_AtLeastOneEiTargetMatch_ValidCheck","At least one EiTarget match check");
						}
						
						if (!disableRegisterReport_DurationValid_Check) {
							boolean isMarketContextValid = RegisterReportConformanceValidationHelper.isDurationValid(oadrRegisterReportType);
							if (!isMarketContextValid) {
								logFailure("disableRegisterReport_DurationValid_Check","Duration is not Valid");
								return false;
							}
						}else{
							skippingConformanceRut("disableRegisterReport_DurationValid_Check","Duration valid check");
						}
						
						
						
						if (!disableRegisterReport_ReportSpecifierIDAndRIDUnique_Check) {
							boolean isReportSpecifierIDUnique = RegisterReportConformanceValidationHelper.isReportSpecifierIDAndRIDUnique(oadrRegisterReportType);
							if (!isReportSpecifierIDUnique) {
								logFailure("disableRegisterReport_ReportSpecifierIDAndRIDUnique_Check","ReportSpecifierID is not unique or duplicate rID found within a specific reportSpecifiedID");
								return false;
							}
						}else{
							skippingConformanceRut("disableRegisterReport_ReportSpecifierIDAndRIDUnique_Check","ReportSpecifierID and rID unique check");
						}
						
						if (!disableRegisterReport_SamplingRateFoundInTelemertyMetadata_Check) {
							boolean isSamplingRateFoundInTelemertyMetadata = RegisterReportConformanceValidationHelper.isSamplingRateFoundInTelemertyMetadata(oadrRegisterReportType);
							if (!isSamplingRateFoundInTelemertyMetadata) {
								logFailure("disableRegisterReport_SamplingRateFoundInTelemertyMetadata_Check","reportDescription:oadrSamplingRate element is not present in the telemerty metadata reports");
								return false;
							}
						}else{
							skippingConformanceRut("disableRegisterReport_SamplingRateFoundInTelemertyMetadata_Check","reportDescription:oadrSamplingRate element present in the telemerty metadata reports check");
						}
												
						if (!disable_VenIDValid_Check) {
							boolean isVenIDValid = RegisterReportConformanceValidationHelper
									.isVenIDValid(oadrRegisterReportType,direction);

							if (!isVenIDValid) {
								logFailure("disable_VenIDValid_Check","Unknown VEN ID has been received");
								return false;
							}
						} else {
							skippingConformanceRut("disable_VenIDValid_Check","VEN ID valid check");
						}
						
						
						if (!disableRegister_Create_reportRequestIDZero_Check) {
							
							boolean isReportRequestIDZeroValid = RegisterReportConformanceValidationHelper.isReportRequestIDZeroValid(oadrRegisterReportType);

							if (!isReportRequestIDZeroValid) {
								logFailure("disableRegister_Create_reportRequestIDZero_Check","OadrRegisterReport:reportRequestID should be zero");
								return false;
							}
							
							
						} else {
							skippingConformanceRut("disableRegister_Create_reportRequestIDZero_Check","OadrRegisterReport:reportRequestID to be zero check");
						}
						
						
						if (!disableRegisterReport_ReportNameReportTypeItemBaseReadingTypeCombination_Check) {
							boolean isReportNameReportTypeItemBaseReadingTypeCombinationValid = RegisterReportConformanceValidationHelper.isReportNameReportTypeItemBaseReadingTypeCombinationValid(oadrRegisterReportType);
							if (!isReportNameReportTypeItemBaseReadingTypeCombinationValid) {
								logFailure("disableRegisterReport_ReportNameReportTypeItemBaseReadingTypeCombination_Check","RegisterReport ReportName, ReportType, ItemBase, and ReadingType combination check");
								return false;
							}
						}else{
							skippingConformanceRut("disableRegisterReport_ReportNameReportTypeItemBaseReadingTypeCombination_Check","RegisterReport ReportName, ReportType, ItemBase, and ReadingType combination check");
						}
						
						
						if (!disableRegisterReport_IntervalsDtstartEiReportIDNotPresent_Check){
							boolean isIntervalsDtstartEiReportIDNotPresent = RegisterReportConformanceValidationHelper
									.isIntervalsDtstartEiReportIDNotPresent(oadrRegisterReportType);
							
							if (!isIntervalsDtstartEiReportIDNotPresent) {
								logFailure("disableRegisterReport_IntervalsDtstartEiReportIDNotPresent_Check","Confirm that a metadata report does not include the intervals, dtsatar, and eiREportID elements");
								return false;
							}
						} else {
							skippingConformanceRut("disableRegisterReport_IntervalsDtstartEiReportIDNotPresent_Check","Check to confirm that a metadata report does not contain intervals, dtsatar, and eiREportID elements");
						}
						
						
						if (!disable_CreatedDateTimeWithinExpectedWindow_Check) {
							String createdDateTime = RegisterReportConformanceValidationHelper
									.isCreatedDateTimeWithinOneMin(oadrRegisterReportType);
							if (createdDateTime.length() > 1) {
								logFailure("disable_CreatedDateTimeWithinExpectedWindow_Check","CreatedDateTime in OadrRegisterReport payloads is not within +- 1 minute of the CurrentTime "+ createdDateTime);
								return false;
							}
						} else {
							skippingConformanceRut("disable_CreatedDateTimeWithinExpectedWindow_Check","Check to see if OadrRegisterReport in oadrRegisterReport payload is within +- 1 minute of the CurrentTime");
						}

						
						if (!disableDeviceClass_Check) {
							boolean isDeviceClassValuesPresent = RegisterReportConformanceValidationHelper
									.isDeviceClassValuesPresent(oadrRegisterReportType);

							if (!isDeviceClassValuesPresent) {
								logFailure("disableDeviceClass_Check","Confirm that reportSubject.endDeviceAsset has mrid with valid enumerated values or if no endDeviceAsset reportSubject sub-elements are present that oadrDeviceClass is not in payload");
								return false;
							}
						} else {
							skippingConformanceRut("disableDeviceClass_Check","Check to confirm if oadrReportDescription:reportSubject has valid Device Class");
						}
						
						
						
				}
				
			}else if (objectType.equals(OadrCreatedReportType.class)) {
				if (trace != null) {
					

					OadrCreatedReportType oadrCreatedReportType = CreatedReportEventHelper.createOadrCreatedReportFromString(data);
					
					trace.getLogFileContentTrace()
							.append("Performing OadrCreatedReport Conformance Rule validation\n");
									
					if (!disableCreatedReport_ResponseCodeSuccess_Check) {
						
						boolean isResponseCodeSuccess = CreatedReportConformanceValidationHelper
								.isResponseCodeSuccess(oadrCreatedReportType);

						if (!isResponseCodeSuccess) {
							logFailure("disableCreatedReport_ResponseCodeSuccess_Check","Error Response Code found");
							return false;
						}
						
						
					} else {
						skippingConformanceRut("disableCreatedReport_ResponseCodeSuccess_Check","Success Response Code check");
					}
					
					if (!disable_VenIDValid_Check) {
						boolean isVenIDValid = CreatedReportConformanceValidationHelper
								.isVenIDValid(oadrCreatedReportType,direction);

						if (!isVenIDValid) {
							logFailure("disable_VenIDValid_Check","Unknown VEN ID has been received");
							return false;
						}
					} else {
						skippingConformanceRut("disable_VenIDValid_Check","VEN ID valid check");
					}
					

					if(!disableSchemaVersionValid_Check) {
							
							if (!OadrUtil.isSchemaVersionValid(oadrCreatedReportType.getSchemaVersion())) {
								logFailure("disableSchemaVersionValid_Check","SchemaVersion is not valid");
								
								return false;
							}
						} else {
							skippingConformanceRut("disableSchemaVersionValid_Check","Check to see if SchemaVersion is valid");
						}
					

					if (!disableRequestIDValid_Check) {

						boolean isRequestIDValid = CreatedReportConformanceValidationHelper
								.isRequestIDsMatch(oadrCreatedReportType,direction);

						if (!isRequestIDValid) {
							logFailure("disableRequestIDValid_Check","The request ID received is not valid");
							return false;
						}
					} else {
						skippingConformanceRut("disableRequestIDValid_Check","The request ID valid check");						
						
					}


				}
			}else if (objectType.equals(OadrCreateReportType.class)) {
				if (trace != null) {
					trace.getLogFileContentTrace()
							.append("Performing OadrCreateReport Conformance Rule validation\n");
					OadrCreateReportType oadrCreateReportType = CreateReportEventHelper.createOadrCreateReportFromString(data);
					if (!disableCreateReport_isRIDValid_Check) {
						
						boolean isRIDValid_Check = CreateReportConformanceValidationHelper.isRIDValid(oadrCreateReportType,direction);

						if (!isRIDValid_Check) {
							logFailure("disableCreateReport_isRIDValid_Check","RID value did not match that was received during Report Registration");
							return false;
						}
						
						
					} else {
						skippingConformanceRut("disableCreateReport_isRIDValid_Check","RID valid check");
					}

					if(!disableSchemaVersionValid_Check) {
						
						if (!OadrUtil.isSchemaVersionValid(oadrCreateReportType.getSchemaVersion())) {
							logFailure("disableSchemaVersionValid_Check","SchemaVersion is not valid");
							
							return false;
						}
					} else {
						skippingConformanceRut("disableSchemaVersionValid_Check","Check to see if SchemaVersion is valid");
					}
				

					
					if (!disableHistoryUsageGreenbuttonGranularityReportBackDurationZero_Check){
						boolean isHistoryUsageGreenbuttonGranularityReportBackDurationZero = CreateReportConformanceValidationHelper
								.isHistoryUsagHistoryUsageGreenbuttonGranularityReportBackDurationZero(oadrCreateReportType,direction);

						if (!isHistoryUsageGreenbuttonGranularityReportBackDurationZero) {
							logFailure("disableHistoryUsageGreenbuttonGranularityReportBackDurationZero_Check","HISTORY_USAGE or HISTORY_GREENBUTTON report request's granularity and reportBackDuration should be Zero");
							return false;
						}
					} else {
						skippingConformanceRut("disableHistoryUsageGreenbuttonGranularityReportBackDurationZero_Check","Check to see if HISTORY_USAGE or HISTORY_GREENBUTTON report request's granularity and reportBackDuration are Zero");
					}
					
					
					if (!disableCreateReport_reportRequestIDUnique_Check) {
						
						boolean isReportRequestIDUnique_Check = CreateReportConformanceValidationHelper.isReportRequestIDUnique_Check(oadrCreateReportType, direction);

						if (!isReportRequestIDUnique_Check) {
							logFailure("disableCreateReport_reportRequestIDUnique_Check","The ReportRequestID is not Unique");
							return false;
						}
						
						
					} else {
						skippingConformanceRut("disableCreateReport_reportRequestIDUnique_Check","ReportRequestID Unique check");					}
					
					
					if (!disableCreateReport_PeriodicTelemetryReportContainsRequiredElements_Check) {
						
						boolean isPeriodicTelemetryReportContainRequiredElements = CreateReportConformanceValidationHelper.isPeriodicTelemetryReportContainRequiredElements(oadrCreateReportType, direction);

						if (!isPeriodicTelemetryReportContainRequiredElements) {
							logFailure("disableCreateReport_PeriodicTelemetryReportContainsRequiredElements_Check","Periodic telemetry reports should contain an reportSpecifier interval dtstart and duration element");
							return false;
						}
						
						
					} else {
						skippingConformanceRut("disableCreateReport_PeriodicTelemetryReportContainsRequiredElements_Check","Check for Periodic telemetry reports to contain an reportSpecifier interval dtstart and duration element");
					}
					
		/*			
					if (!disableCreateReport_PeriodicReportReportDurationNotLessThanMinimumSamplingPeriod_Check) {
						
						boolean isPeriodicReportReportDurationNotLessThanMinimumSamplingPeriod = CreateReportConformanceValidationHelper.isPeriodicReportReportDurationNotLessThanMinimumSamplingPeriod(oadrCreateReportType, direction);

						if (!isPeriodicReportReportDurationNotLessThanMinimumSamplingPeriod) {
							logFailure("disableCreateReport_PeriodicReportReportDurationNotLessThanMinimumSamplingPeriod_Check","Confirm that in a periodic report request the Duration is not less than the minimum sampling period if sampling rate is specified in the metadata report");
							return false;
						}
						
					} else {
						skippingConformanceRut("disableCreateReport_PeriodicReportReportDurationNotLessThanMinimumSamplingPeriod_Check","Check to confirm that in a periodic report request the Duration is not less than the minimum sampling period if sampling rate is specified in the metadata report");
					}
					*/
					
					
					if (!disableCreateReport_GranularityValid_Check) {
						
						boolean isGranularityValid = CreateReportConformanceValidationHelper.isGranularityValid(oadrCreateReportType, direction);

						if (!isGranularityValid) {
							logFailure("disableCreateReport_GranularityValid_Check","Confirm that if the OadrCreateReport granularity is greater than zero, then it is not less than than the metadata report's oadrSamplingRate:oadrMinPeriod");
							return false;
						}
						
					} else {
						skippingConformanceRut("disableCreateReport_GranularityValid_Check","Check to confirm that if the OadrCreateReport granularity is greater than zero, then it is not less than than the metadata report's oadrSamplingRate:oadrMinPeriod");
					}
					
					
									
					if (!disableRegister_Create_reportRequestIDZero_Check) {
						
						boolean isReportRequestIDZeroValid = CreateReportConformanceValidationHelper.isReportRequestIDZeroValid(oadrCreateReportType);

						if (!isReportRequestIDZeroValid) {
							logFailure("disableRegister_Create_reportRequestIDZero_Check","OadrCreateReport:reportRequestID cannot be empty or zero");
							return false;
						}
						
						
					} else {
						skippingConformanceRut("disableRegister_Create_reportRequestIDZero_Check","Check to see if OadrCreateReport:reportRequestID is empty or zero");
					}

					
					if (!disable_VenIDValid_Check) {
						boolean isVenIDValid = CreateReportConformanceValidationHelper
								.isVenIDValid(oadrCreateReportType,direction);

						if (!isVenIDValid) {
							logFailure("disable_VenIDValid_Check","Unknown VEN ID has been received");
							return false;
						}
					} else {
						skippingConformanceRut("disable_VenIDValid_Check","VEN ID valid check");
					}
					
					
					if (!disableCreateReportReportSpecifierNotPresent_Check) {
						boolean isReportRequestIDZeroValid = CreateReportConformanceValidationHelper.isCreateReportReportSpecifierNotPresentValid(oadrCreateReportType,direction);

						if (!isReportRequestIDZeroValid) {
							logFailure("disableCreateReportReportSpecifierNotPresent_Check","For HISTORY_USAGE, TELEMETRY_USAGE and TELEMETRY_STATUS reports oadrCreateReport.oadrReportRequest.reportSpecifier.specifierPayload.itembase should not be present");
							return false;
						}
						
						
					} else {
						skippingConformanceRut("disableCreateReportReportSpecifierNotPresent_Check","Check to see if HISTORY_USAGE, TELEMETRY_USAGE and TELEMETRY_STATUS reports oadrCreateReport.oadrReportRequest.reportSpecifier.specifierPayload.itembase is not present");
					}
					
					
					if (!disableCreateReport_DoesNotContainExcludedReportIntervalProperties_Check) {
						boolean isCreateReportDoesNotContainExcludedReportIntervalPropertiesAndReadingType = CreateReportConformanceValidationHelper.isCreateReportDoesNotContainExcludedReportIntervalProperties(oadrCreateReportType,direction);

						if (!isCreateReportDoesNotContainExcludedReportIntervalPropertiesAndReadingType) {
							logFailure("disableCreateReport_DoesNotContainExcludedReportIntervalPropertiesAndReadingType_Check","Report request payload must not include reportSpecifier:reportInterval:properties:tolerance, reportSpecifier:reportInterval:properties:eiNotification, reportSpecifier:reportInterval:properties:eiReampUp, reportSpecifier:reportInterval:properties:eiRecovery, and reportSpecifier:specifierPayload:readingType");
							return false;
						}
						
						
					} else {
						skippingConformanceRut("disableCreateReport_DoesNotContainExcludedReportIntervalPropertiesAndReadingType_Check","Check to see if HISTORY_USAGE, TELEMETRY_USAGE and TELEMETRY_STATUS reports oadrCreateReport.oadrReportRequest.reportSpecifier.specifierPayload.itembase is not present");
					}
					
					
					if (!disableCreateReport_ReadingTypeValid_Check) {
						boolean isReadingTypeValid = CreateReportConformanceValidationHelper.isReadingTypeValid(oadrCreateReportType);

						if (!isReadingTypeValid) {
							logFailure("disableCreateReport_ReadingTypeValid_Check","Confirm that oadrCreateReport reportSpecifier:specifierPayload:readingType values is set to x-notApplicable");
							return false;
						}
						
						
					} else {
						skippingConformanceRut("disableCreateReport_ReadingTypeValid_Check","Check to confirm if oadrCreateReport reportSpecifier:specifierPayload:readingType values is set to x-notApplicable");
					}
										
					
									}
			}else if (objectType.equals(OadrUpdateReportType.class)) {

				if (trace != null) {
					trace.getLogFileContentTrace()
							.append("Performing OadrUpdateReport Conformance Rule validation\n");
					OadrUpdateReportType oadrUpdateReportType = UpdateReportEventHelper.createReportFromString(data);
					if (!disableUpdateReport_isRIDValid_Check) {
						boolean isRIDValid_Check = UpdateReportConformanceValidationHelper.isRIDValid_Check(oadrUpdateReportType);

						if (!isRIDValid_Check) {
							logFailure("disableUpdateReport_isRIDValid_Check","RID value did not match that was received in OadrUpdateReport");
							return false;
						}
						
						
					} else {
						skippingConformanceRut("disableUpdateReport_isRIDValid_Check","RID valid check");
					}
					
					if(!disableSchemaVersionValid_Check) {
						
						if (!OadrUtil.isSchemaVersionValid(oadrUpdateReportType.getSchemaVersion())) {
							logFailure("disableSchemaVersionValid_Check","SchemaVersion is not valid");
							
							return false;
						}
					} else {
						skippingConformanceRut("disableSchemaVersionValid_Check","Check to see if SchemaVersion is valid");
					}
				

					
					if (!disableUpdateReport_RequiredElementsAndReportName_Check) {
						boolean isRequiredElementsAndReportNameValid = UpdateReportConformanceValidationHelper.isRequiredElementsAndReportNameValid(oadrUpdateReportType,direction);

						
						if (!isRequiredElementsAndReportNameValid) {
							logFailure("disableUpdateReport_RequiredElementsAndReportName_Check","Non-metadata reports (oadrUpdateReport)should contain oadrReport:dtStart, intervals, and reportName, do NOT contain oadrReportDescription and reportName is included with the name of the well know report excluding the METADATA_ prefix");
							return false;
						}
						
						
					} else {
						skippingConformanceRut("disableUpdateReport_RequiredElementsAndReportName_Check","Check for Non-metadata reports (oadrUpdateReport)to contain oadrReport:dtStart, intervals, and reportName, do NOT contain oadrReportDescription and reportName is included with the name of the well know report excluding the METADATA_ prefix");
					}
					
					if (!disable_UpdateReportDtstartValid_Check) {
						boolean isReportDtstartValid = UpdateReportConformanceValidationHelper
								.isReportDtstartValid(oadrUpdateReportType);

						if (!isReportDtstartValid) {
							logFailure("disable_UpdateReportDtstartValid_Check","If both oadrReport:dtStart and interval dtstart are present then the oadrReport:dtstart element must match this dtstart value in the first interval. (non-metadata report).  If one interval contains dtstart, all intervals must contain dtstart  (non-metadata report)");
							return false;
						}
					} else {
						skippingConformanceRut("disable_UpdateReportDtstartValid_Check","Check to see if both oadrReport:dtStart and interval dtstart are present then the oadrReport:dtstart element match this dtstart value in the first interval. (non-metadata report).  If one interval contains dtstart, all intervals must contain dtstart  (non-metadata report)");
						
					}

					
					if (!disable_UpdateReportDurationValid_Check) {
						boolean isReportDurationValid = UpdateReportConformanceValidationHelper
								.isReportDurationValid(oadrUpdateReportType);

						if (!isReportDurationValid) {
							logFailure("disable_UpdateReportDurationValid_Check","If oadrReport:duration is included in the payload, it must match the time from the start of the first interval until the end of the last interval. (non-metadata report)  If one interval had a duration value, all intervals must have a duration. (non-metadata report).  If intervals do not contain dtstart elements and there is more than one interval, duration must be specified in oadrReport:intervals:interval:duration");
							return false;
						}
					} else {
						skippingConformanceRut("disable_UpdateReportDurationValid_Check","Check to see if oadrReport:duration is included in the payload, it must match the time from the start of the first interval until the end of the last interval. (non-metadata report)  If one interval had a duration value, all intervals must have a duration. (non-metadata report).  If intervals do not contain dtstart elements and there is more than one interval, duration must be specified in oadrReport:intervals:interval:duration");
					}

					
					if (!disable_UIDValid_Check) {
						boolean isUIDValid = UpdateReportConformanceValidationHelper
								.isUIDValid(oadrUpdateReportType);

						if (!isUIDValid) {
							logFailure("disable_UIDValid_Check","UID must be expressed as an interval number with a base of 0 and an increment of 1 for each subsequent interval");
							return false;
						}
					} else {
						skippingConformanceRut("disable_UIDValid_Check","Check to see if UID is expressed as an interval number with a base of 0 and an increment of 1 for each subsequent interval");
					}
					
					if (!disable_VenIDValid_Check) {
						boolean isVenIDValid = UpdateReportConformanceValidationHelper
								.isVenIDValid(oadrUpdateReportType,direction);

						if (!isVenIDValid) {
							logFailure("disable_VenIDValid_Check","Unknown VEN ID has been received");
							return false;
						}
					} else {
						skippingConformanceRut("disable_VenIDValid_Check","Is VEN ID Valid check");
					}
					
			/*		if (!disableOnePayloadFloatPresentInEachInterval_Check) {
						boolean isSignalTypeAndUnitValidForSignalName = UpdateReportConformanceValidationHelper
								.isOnePayloadFloatPresentInEachInterval(oadrUpdateReportType);

						if (!isSignalTypeAndUnitValidForSignalName) {
							logFailure("disableOnePayloadFloatPresentInEachInterval_Check","Only one payloadFloat element should be present in each signal interval");
							return false;
						}
					} else {
						skippingConformanceRut("disableOnePayloadFloatPresentInEachInterval_Check","Check to see if only one payloadFloat element is be present in each signal interval");

					}*/
					
			/*		if (!disableDeviceClass_Check) {
						boolean isDeviceClassValuesPresent = UpdateReportConformanceValidationHelper
								.isDeviceClassValuesPresent(oadrUpdateReportType);

						if (!isDeviceClassValuesPresent) {
							logFailure("disableDeviceClass_Check","Confirm that reportSubject.endDeviceAsset has mrid with valid enumerated values or if no endDeviceAsset reportSubject sub-elements are present that oadrDeviceClass is not in payload");
							return false;
						}
					} else {
						skippingConformanceRut("disableDeviceClass_Check","Check to confirm if oadrReportDescription:reportSubject has valid Device Class");
					}*/
										
					if (!disable_UpdateReportLoadControlStateConsistentForAllIntervals_Check) {
						boolean isLoadControlStateConsistentForAllIntervals = UpdateReportConformanceValidationHelper
								.isLoadControlStateConsistentForAllIntervals(oadrUpdateReportType,direction);

						if (!isLoadControlStateConsistentForAllIntervals) {
							logFailure("disable_UpdateReportLoadControlStateConsistentForAllIntervals_Check","For telemetry reports, if a oadrLoadControlState sub-element is reported in one interval, it should be reported in all intervals for the given report");
							return false;
						}
					} else {
						skippingConformanceRut("disable_UpdateReportLoadControlStateConsistentForAllIntervals_Check","Check to confirm in telemetry reports, if a oadrLoadControlState sub-element is reported in one interval, it should be reported in all intervals for the given report");
					}
					
					

					if (!disable_UpdateReportTelemetryUsageReportIntervalDuration_Check) {
						boolean isTelemetryUsageReportIntervalDurationPresent = UpdateReportConformanceValidationHelper
								.isTelemetryUsageReportIntervalDurationPresent(oadrUpdateReportType,direction);
 						
						if (!isTelemetryUsageReportIntervalDurationPresent) {
							logFailure("disable_UpdateReportTelemetryUsageReportIntervalDuration_Check","In OadrUpdateReports for TELEMETERY_USAGE, where the OadrCreateReport has a reportBackDuration of 0 AND that OadrRegisterReport's min and max sampling rate are equal, the OadrUpdateReport intervals.interval.duration element must be present in all intervals");
							return false;
						}
					} else {
						skippingConformanceRut("disable_UpdateReportTelemetryUsageReportIntervalDuration_Check","In OadrUpdateReports for TELEMETERY_USAGE, where the OadrCreateReport has a reportBackDuration of 0 AND that OadrRegisterReport's min and max sampling rate are equal, the OadrUpdateReport intervals.interval.duration element must be present in all intervals");
					}
					
					
				}
				
			}else if (objectType.equals(OadrUpdatedReportType.class)) {
				if (trace != null) {
					trace.getLogFileContentTrace()
							.append("Performing OadrUpdatedReport Conformance Rule validation\n");
									
					OadrUpdatedReportType oadrUpdatedReportType = UpdatedReportEventHelper.loadReportFromString(data);
					if (!disableUpdatedReport_ResponseCodeSuccess_Check) {
						
						boolean isResponseCodeSuccess = UpdatedReportConformanceValidationHelper
								.isResponseCodeSuccess(oadrUpdatedReportType);

						if (!isResponseCodeSuccess) {
							logFailure("disableUpdatedReport_ResponseCodeSuccess_Check","Error Response Code found");
							return false;
						}
						
						
					} else {
						skippingConformanceRut("disableUpdatedReport_ResponseCodeSuccess_Check","Success Response Code check");
					}

				
					if(!disableSchemaVersionValid_Check) {
						
						if (!OadrUtil.isSchemaVersionValid(oadrUpdatedReportType.getSchemaVersion())) {
							logFailure("disableSchemaVersionValid_Check","SchemaVersion is not valid");
							
							return false;
						}
					} else {
						skippingConformanceRut("disableSchemaVersionValid_Check","Check to see if SchemaVersion is valid");
					}
				
					
					if (!disableUpdatedReport_CancelReportRequestIDMatch_Check) {
						
						boolean isCancelReportRequestIDMatch = UpdatedReportConformanceValidationHelper
								.isCancelReportRequestIDMatch(oadrUpdatedReportType,direction);

						if (!isCancelReportRequestIDMatch) {
							logFailure("disableUpdatedReport_CancelReportRequestIDMatch_Check","Piggy back cancelation must be for one or more if the a reportRequestIDs that was contained  in the delivered report (oadrUpdateReport)");
							return false;
						}
						
						
					} else {
						skippingConformanceRut("disableUpdatedReport_CancelReportRequestIDMatch_Check","Check to see if Piggy back cancelation is for one or more reportRequestIDs that was contained  in the delivered report (oadrUpdateReport)");
					}

					if (!disable_VenIDValid_Check) {
						boolean isVenIDValid = UpdatedReportConformanceValidationHelper
								.isVenIDValid(oadrUpdatedReportType,direction);

						if (!isVenIDValid){
							logFailure("disable_VenIDValid_Check","Unknown VEN ID has been received");
							return false;
						}
					} else {
						skippingConformanceRut("disable_VenIDValid_Check","VEN ID valid check");
					}
					
					if (!disableRequestIDValid_Check) {

						boolean isRequestIDValid = UpdatedReportConformanceValidationHelper
								.isRequestIDsMatch(oadrUpdatedReportType,direction);

						if (!isRequestIDValid) {
							logFailure("disableRequestIDValid_Check","The request ID received is not valid");
							return false;
						}
					} else {
						skippingConformanceRut("disableRequestIDValid_Check","The request ID valid check");						
						
					}
				}
			}else if (objectType.equals(OadrCancelReportType.class)) {
				if (trace != null) {
					trace.getLogFileContentTrace()
							.append("Performing OadrCancelReport Conformance Rule validation\n");
					
					OadrCancelReportType oadrCanceleReportType = CancelReportEventHelper.createOadrCancelReportTypeFromString(data);
					
					if (!disable_VenIDValid_Check) {
						boolean isVenIDValid = CancelReportConformanceValidationHelper
								.isVenIDValid(oadrCanceleReportType,direction);

						if (!isVenIDValid) {
							logFailure("disable_VenIDValid_Check","Unknown VEN ID has been received");
							return false;
						}
					} else {
						skippingConformanceRut("disable_VenIDValid_Check","VEN ID valid check");
					}
					
					
					if(!disableSchemaVersionValid_Check) {
						
						if (!OadrUtil.isSchemaVersionValid(oadrCanceleReportType.getSchemaVersion())) {
							logFailure("disableSchemaVersionValid_Check","SchemaVersion is not valid");
							
							return false;
						}
					} else {
						skippingConformanceRut("disableSchemaVersionValid_Check","Check to see if SchemaVersion is valid");
					}
				
				}
				
			}else if (objectType.equals(OadrCanceledReportType.class)) {
				if (trace != null) {
					trace.getLogFileContentTrace()
							.append("Performing OadrCanceledReport Conformance Rule validation\n");
								
					OadrCanceledReportType oadrCanceledReportType = CanceledReportEventHelper.loadCanceledReportTypeFromString(data);
									
					if (!disableCanceledReport_ResponseCodeSuccess_Check) {
						
						boolean isResponseCodeSuccess = CanceledReportConformanceValidationHelper
								.isResponseCodeSuccess(oadrCanceledReportType);

						if (!isResponseCodeSuccess) {
							logFailure("disableCanceledReport_ResponseCodeSuccess_Check","Error Response Code found");
							return false;
						}
					} else {
						skippingConformanceRut("disableCanceledReport_ResponseCodeSuccess_Check","Success Response Code check");
					}
					
				
					if(!disableSchemaVersionValid_Check) {
						
						if (!OadrUtil.isSchemaVersionValid(oadrCanceledReportType.getSchemaVersion())) {
							logFailure("disableSchemaVersionValid_Check","SchemaVersion is not valid");
							
							return false;
						}
					} else {
						skippingConformanceRut("disableSchemaVersionValid_Check","Check to see if SchemaVersion is valid");
					}

					if (!disable_VenIDValid_Check) {
						boolean isVenIDValid = CanceledReportConformanceValidationHelper
								.isVenIDValid(oadrCanceledReportType,direction);

						if (!isVenIDValid) {
							logFailure("disable_VenIDValid_Check","Unknown VEN ID has been received");
							return false;
						}
					} else {
						skippingConformanceRut("disable_VenIDValid_Check","Unknown VEN ID check");
					}
					
					
					if (!disableRequestIDValid_Check) {

						boolean isRequestIDValid = CanceledReportConformanceValidationHelper
								.isRequestIDsMatch(oadrCanceledReportType,direction);

						if (!isRequestIDValid) {
							logFailure("disableRequestIDValid_Check","The request ID received is not valid");
							return false;
						}
					} else {
						skippingConformanceRut("disableRequestIDValid_Check","The request ID valid check");						
						
					}
					
				}
			}else if (objectType.equals(OadrRequestReregistrationType.class)) {
				if (trace != null) {
					trace.getLogFileContentTrace()
							.append("Performing OadrRequestReregistration Conformance Rule validation\n");
										
					OadrRequestReregistrationType OadrRequestReregistrationType = RequestReregistrationSignalHelper.createOadrRequestReregistrationTypeFromString(data);
									
					if (!disable_VenIDValid_Check) {
						boolean isVenIDValid = RequestReRegistrationConforValHelper
								.isVenIDValid(OadrRequestReregistrationType,direction);

						if (!isVenIDValid) {
							logFailure("disable_VenIDValid_Check","Unknown VEN ID has been received");
							return false;
						}
					} else {
						skippingConformanceRut("disable_VenIDValid_Check","Unknown VEN ID check");
					}
					
					
					if(!disableSchemaVersionValid_Check) {
						
						if (!OadrUtil.isSchemaVersionValid(OadrRequestReregistrationType.getSchemaVersion())) {
							logFailure("disableSchemaVersionValid_Check","SchemaVersion is not valid");
							
							return false;
						}
					} else {
						skippingConformanceRut("disableSchemaVersionValid_Check","Check to see if SchemaVersion is valid");
					}
					
									}
			}else if (objectType.equals(OadrQueryRegistrationType.class)) {
				if (trace != null) {
					trace.getLogFileContentTrace()
							.append("Performing OadrQueryRegistration Conformance Rule validation\n");
					
					OadrQueryRegistrationType oadrQueryRegistrationType = QueryRegistrationSignalHelper.createOadrQueryRegistrationTypeRequestFromString(data);
					
					if(!disableSchemaVersionValid_Check) {
						
						if (!OadrUtil.isSchemaVersionValid(oadrQueryRegistrationType.getSchemaVersion())) {
							logFailure("disableSchemaVersionValid_Check","SchemaVersion is not valid");
							
							return false;
						}
					} else {
						skippingConformanceRut("disableSchemaVersionValid_Check","Check to see if SchemaVersion is valid");
					}
			/*		
					
					if (!disable_VenIDValid_Check) {
						boolean isVenIDValid = QueryRegistrationConforValHelper
								.isVenIDValid(oadrQueryRegistrationType);

						if (!isVenIDValid) {
							trace.getLogFileContentTrace()
									.append("Conformance check failed : Unknown VEN ID has been received\n");
							return false;
						}
					} else {
						trace.getLogFileContentTrace()
								.append("Skipping Conformance Rule 'CreatedEvent:VenIDValid'. This Conformance Rule Check has been disabled.\n");
					}*/

					
									}
			}else {
				if (trace != null) {
					trace.getLogFileContentTrace().append(
							"\nUnexpected type " + objectType + " received\n");
				}
				return false;
			} 

			return true;

		} catch (Exception e) {
			OadrUtil.exceptionHandler(e,
					"Exception while performing Conformance Rule Validation");
			e.printStackTrace();
			return false;
		}

	}
	
	public static boolean isDisable_CreatedDateTimeWithinExpectedWindow_Check() {
		return disable_CreatedDateTimeWithinExpectedWindow_Check;
	}

	public static void setDisable_CreatedDateTimeWithinExpectedWindow_Check(
			boolean disable_CreatedDateTimeWithinExpectedWindow_Check) {
		ConformanceRuleValidator.disable_CreatedDateTimeWithinExpectedWindow_Check = disable_CreatedDateTimeWithinExpectedWindow_Check;
	}

	public static void setDisableCreateReport_GranularityValid_Check(
			boolean disableCreateReport_GranularityValid_Check) {
		ConformanceRuleValidator.disableCreateReport_GranularityValid_Check = disableCreateReport_GranularityValid_Check;
	}
	
	
	public static void validateRequestID(String request,String response) throws UnsupportedEncodingException, JAXBException{
		
		if(request==null || response==null) return;
		
		String reqObjectType = SchemaHelper.getEventTypeName(request);

		
		Class objectType = null;
		if (!StringUtil.isBlank(response)) {
			objectType = SchemaHelper.getObjectType(response);	
		}
		
		if ((objectType != null && objectType.equals(OadrCreatedOptType.class)) && (reqObjectType!=null && reqObjectType.equals("OadrCreateOptType"))) {
			OadrCreateOptType oadrCreateOptType = CreateOptEventHelper.createCreateOptTypeEventFromString(request);
			OadrCreatedOptType oadrCreatedOptType = CreatedOptEventHelper.createCreatedOptTypeEventFromString(response);
			CreatedOptConformanceValidationHelper.validateRequestIDsMatch(oadrCreateOptType, oadrCreatedOptType);
		}
		
		if ((objectType != null && objectType.equals(OadrRegisteredReportType.class)) && (reqObjectType!=null && reqObjectType.equals("OadrRegisterReportType"))) {
			OadrRegisterReportType oadrRegisterReport = RegisterReportEventHelper.createOadrRegisterReportFromString(request);
			OadrRegisteredReportType oadrRegisteredReport = RegisteredReportEventHelper.createOadrRegisteredReportFromString(response);
			RegisteredReportConformanceValidationHelper.validateRequestIDsMatch(oadrRegisterReport, oadrRegisteredReport);
		}
		
		if ((objectType != null && objectType.equals(OadrCanceledReportType.class)) && (reqObjectType!=null && reqObjectType.equals("OadrCancelReportType"))) {
			OadrCancelReportType oadrCancelReport = CancelReportEventHelper.createOadrCancelReportTypeFromString(request);
			OadrCanceledReportType oadrCanceledReport = CanceledReportEventHelper.loadCanceledReportTypeFromString(response);
			CanceledReportConformanceValidationHelper.validateRequestIDsMatch(oadrCancelReport, oadrCanceledReport);
		}
		
		if ((objectType != null && objectType.equals(OadrUpdatedReportType.class)) && (reqObjectType!=null && reqObjectType.equals("OadrUpdateReportType"))) {
			OadrUpdateReportType oadrUpdateReportType = UpdateReportEventHelper.createReportFromString(request);
			OadrUpdatedReportType oadrUpdatedReportType = UpdatedReportEventHelper.loadReportFromString(response);
			UpdatedReportConformanceValidationHelper.validateRequestIDsMatch(oadrUpdateReportType, oadrUpdatedReportType);
		}
		
		if ((objectType != null && objectType.equals(OadrCreatedReportType.class)) && (reqObjectType!=null && reqObjectType.equals("OadrCreateReportType"))) {
			OadrCreateReportType oadrCreateReportType = CreateReportEventHelper.createOadrCreateReportFromString(request);
			OadrCreatedReportType oadrCreatedReportType = CreatedReportEventHelper.createOadrCreatedReportFromString(response);
			CreatedReportConformanceValidationHelper.validateRequestIDsMatch(oadrCreateReportType, oadrCreatedReportType);
		}

		
	/*	if (reqObjectType!=null && reqObjectType.equals("OadrPollType")) {
			OadrCreateReportType oadrCreateReportType = CreateReportEventHelper.createOadrCreateReportFromString(request);
			OadrCreatedReportType oadrCreatedReportType = CreatedReportEventHelper.createOadrCreatedReportFromString(response);
			CreatedReportConformanceValidationHelper.validateRequestIDsMatch(oadrCreateReportType, oadrCreatedReportType);
		}*/


	}
	


	public static void validateResponseHeaders(Series<Header> headers) {
		
		if(disableHTTPHeader_Valid_Check){
			//LogHelper.addTrace("Skipping Conformance Rule 'HTTPHeader:HTTPHeader_Valid'. This Conformance Rule Check has been disabled.");
			return;
		}
		
		String contentLength = OadrUtil.getHeaderContentLength(headers);
		String contentType = OadrUtil.getHeaderContentType(headers);
		
		if(contentLength==null){
			LogHelper.addTrace("Conformance check failed : Unable to find Content-Length in header.");
			throw new ValidationException("Unable to find Content-Length in header");			
		}
		
		if(contentType==null){
			LogHelper.addTrace("Conformance check failed : Unable to find Content-Type in header.");
			throw new ValidationException("Unable to find Content-Type in header");			
		}
		
		try {
			Integer.parseInt(contentLength);
		} catch (NumberFormatException e) {
			
			TestSession.setValidationErrorFound(true);
			LogHelper.addTrace("Conformance check failed : HTTP Header ContentLength "+contentType+"is not valid.");
			
			throw new ValidationException("Unexpected contentLength=" + contentLength);
		}
		
		boolean isApplicationXMLFound = isApplicationXMLFound(contentType);
		boolean isCharsetUTF8Found = isCharsetUTF8Found(contentType);
		
		if(contentType == null || !isApplicationXMLFound || !isCharsetUTF8Found){
			TestSession.setValidationErrorFound(true);
			
			if(!isApplicationXMLFound){
				LogHelper.addTrace("Conformance check failed : Content-Type is not found to be application/xml. Content-Type received is "+contentType);
				throw new ValidationException("Unexpected contentType=" + contentType);
			}
				
			if(!isCharsetUTF8Found){
				LogHelper.addTrace("Conformance check failed : Charset received in Content-Type is "+contentType+". Expected Charset is UTF-8");
				throw new ValidationException("Unexpected Charset received in Content-Type =" + contentType);				
			}
		}
	}

	private static boolean isApplicationXMLFound(String contentType){
		String [] contentArr = contentType.split(";");
	
		for(String eachContent : contentArr){
			if(eachContent.equals("application/xml")){
				return true;
			}
			
			
			
		}
		return false;
	}
	
	private static boolean isCharsetUTF8Found(String contentType){
		String [] contentArr = contentType.split(";");
	
		for(String eachContent : contentArr){
		
				
				if(eachContent.contains("charset")){
					String []charset = eachContent.split("=");
					if(charset.length>1 && !charset[1].trim().equalsIgnoreCase("UTF-8")){
						return false;
					}
					
			}
			
			
		}
		return true;
	}
	public static void validateRequestHeaders(Series<Header> headers) {
		if(disableHTTPHeader_Valid_Check){
			return;
		}
		
		String host = OadrUtil.getHeaderHost(headers);
		
		if (OadrUtil.isEmpty(host)) {
			TestSession.setValidationErrorFound(true);
			LogHelper.addTrace("Conformance check failed : HTTP Header Host not found.");
			
			throw new ValidationException("Unexpected empty Host header");
		}

		validateResponseHeaders(headers);
	}
	
	public static void logFailure(String propertyName,String description){
		String propertyLog = "";
		if(propertyName!=null){
			propertyLog = "Property - "+removeDisabledTextFromPropertyName(propertyName)+"  :: ";
		}
		
		String log = "Conformance check failed ::  "+propertyLog+" Description - "+description+".";

		
		LogHelper.addTrace(log);
		TestSession.setValidationErrorFound(true);
	}
	
	public static void skippingConformanceRut(String propertyName,String description){
		String log = "Skipping disabled Conformance Rule ::  Property - "+removeDisabledTextFromPropertyName(propertyName)+" :: Description - "+description+".";
		LogHelper.addTrace(log);
	}
	
	private static String removeDisabledTextFromPropertyName(String value){
		if(value==null) return "";
		value = value.replace("disable_", "");
		value = value.replace("disable", "");
		return value;
	}
	
}
