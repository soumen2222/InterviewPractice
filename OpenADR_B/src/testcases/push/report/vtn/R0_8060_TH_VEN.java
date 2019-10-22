package testcases.push.report.vtn;

import com.qualitylogic.openadr.core.base.VenPushTestCase;
import com.qualitylogic.openadr.core.bean.ServiceType;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.signal.OadrCreateReportType;
import com.qualitylogic.openadr.core.signal.OadrRegisterReportType;
import com.qualitylogic.openadr.core.signal.OadrRegisteredReportType;
import com.qualitylogic.openadr.core.signal.OadrUpdateReportType;
import com.qualitylogic.openadr.core.signal.helper.RegisterReportEventHelper;
import com.qualitylogic.openadr.core.signal.helper.RegisteredReportEventHelper;
import com.qualitylogic.openadr.core.signal.helper.UpdateReportEventHelper;

public class R0_8060_TH_VEN extends VenPushTestCase {

	public static void main(String[] args) {
		execute(new R0_8060_TH_VEN());
	}
	
	@Override
	public void test() throws Exception {
		checkActiveRegistration();

		prompt(resources.prompt_024());

		OadrRegisterReportType registerReport = RegisterReportEventHelper.loadMetadata_001();
		OadrRegisteredReportType registeredReport = sendRegisterReport(registerReport);
		
		OadrCreateReportType createReport = RegisteredReportEventHelper.createCreateReport(registeredReport);
		createReport.setRequestID(registerReport.getRequestID());
		TestSession.getOadrCreateReportTypeReceivedList().add(createReport);
		sendCreatedReport();

		OadrUpdateReportType updateReport = UpdateReportEventHelper.loadOadrUpdateReport_Update001(ServiceType.VEN,true,createReport);
		sendUpdateReport(updateReport);
	}

}