package testcases.push.report.vtn;

import com.qualitylogic.openadr.core.action.IResponseCanceledReportTypeAckAction;
import com.qualitylogic.openadr.core.base.ErrorConst;
import com.qualitylogic.openadr.core.base.VenPushTestCase;
import com.qualitylogic.openadr.core.bean.ServiceType;
import com.qualitylogic.openadr.core.exception.NotApplicableException;
import com.qualitylogic.openadr.core.signal.OadrCreateReportType;
import com.qualitylogic.openadr.core.signal.OadrRegisterReportType;
import com.qualitylogic.openadr.core.signal.OadrUpdateReportType;
import com.qualitylogic.openadr.core.signal.helper.RegisterReportEventHelper;
import com.qualitylogic.openadr.core.signal.helper.UpdateReportEventHelper;
import com.qualitylogic.openadr.core.util.ConformanceRuleValidator;

public class R0_8170_TH_VEN_1 extends VenPushTestCase {

	public static void main(String[] args) {
		execute(new R0_8170_TH_VEN_1());
	}

	@Override
	public void test() throws Exception {
		checkActiveRegistration();

		ConformanceRuleValidator.setDisableCanceledReport_ResponseCodeSuccess_Check(true);
		
		boolean yesClicked = promptYes(resources.prompt_036());
		if (yesClicked) {
			throw new NotApplicableException("Placeholder test case for self test, skipping execution for real device.");
		} else {
			addCreatedReportResponse();
			
			listenForRequests();
			
			OadrCreateReportType createReport1 = waitForCreateReport(1);
			String reportRequestID1 = createReport1.getOadrReportRequest().get(0).getReportRequestID();

			addCreatedReportResponse().addReportRequestID(reportRequestID1);

			OadrUpdateReportType updateReport_001 = UpdateReportEventHelper.loadOadrUpdateReport_Update001(ServiceType.VEN, true, createReport1);
			sendUpdateReport(updateReport_001);

			OadrCreateReportType createReport2 = waitForCreateReport(2);
			checkCreateReport_x170(createReport2);
			String reportRequestID2 = createReport2.getOadrReportRequest().get(0).getReportRequestID();

			IResponseCanceledReportTypeAckAction canceledReport = addCanceledReportResponse(ErrorConst.INVALID_ID_452, "ERROR");
			canceledReport.addReportRequestID(reportRequestID2);
			canceledReport.setlastEvent(true);
			
			OadrRegisterReportType registerReport = RegisterReportEventHelper.loadMetadata_001();
			registerReport.setReportRequestID(reportRequestID2);
			sendRegisterReport(registerReport);
			
			waitForCompletion();
		}
	}
}
