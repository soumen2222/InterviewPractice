package testcases.push.report.vtn;

import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;

import com.qualitylogic.openadr.core.base.VenPushTestCase;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.signal.OadrCreateReportType;
import com.qualitylogic.openadr.core.signal.helper.CreateReportEventHelper;
import com.qualitylogic.openadr.core.util.OadrUtil;

public class R0_8080_TH_VEN extends VenPushTestCase {

	public static void main(String[] args) {
		execute(new R0_8080_TH_VEN());
	}

	@Override
	public void test() throws Exception {
		checkActiveRegistration();

		addRegisteredReportResponse();
		addRegisteredReportResponse().setlastEvent(true);
		
		listenForRequests();

		sendCreateReport(CreateReportEventHelper.loadOadrCreateReport_Request_005());
	
		waitForCompletion();

		// waitForRegisterReport(createReport);

		checkRegisterReportRequest(2);
	}

	private void waitForRegisterReport(OadrCreateReportType createReport) {
		String reportBackDuration = createReport.getOadrReportRequest().get(0).getReportSpecifier().getReportBackDuration().getDuration();
		Duration duration = OadrUtil.createDuration(reportBackDuration);
		XMLGregorianCalendar calendar = OadrUtil.getCurrentTime();
		calendar.add(duration);
		long expiration = OadrUtil.getTimeMilliseconds(calendar) + (30 * 1000);
		
		System.out.println("Pausing for " + duration + " + 30 seconds...");
		while (System.currentTimeMillis() < expiration) {
			pause(1);
			
			if (TestSession.getOadrRegisterReportTypeReceivedList().size() > 1) {
				break;
			}
		}
		
		pause(2);
	}
}
