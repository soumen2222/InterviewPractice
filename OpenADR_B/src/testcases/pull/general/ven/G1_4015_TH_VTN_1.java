package testcases.pull.general.ven;

import com.qualitylogic.openadr.core.action.IDistributeEventAction;
import com.qualitylogic.openadr.core.action.IResponseCreateReportTypeAckAction;
import com.qualitylogic.openadr.core.action.impl.DefaultDistributeEvent_001Action;
import com.qualitylogic.openadr.core.action.impl.DefaultResponseCreateReportRequest_001Action;
import com.qualitylogic.openadr.core.base.VtnPullTestCase;
import com.qualitylogic.openadr.core.bean.ServiceType;
import com.qualitylogic.openadr.core.util.ConformanceRuleValidator;

public class G1_4015_TH_VTN_1 extends VtnPullTestCase {

	public static void main(String[] args) {
		execute(new G1_4015_TH_VTN_1());
	}

	@Override
	public void test() throws Exception {
		checkActiveRegistration();

		IDistributeEventAction distributeEvent = new DefaultDistributeEvent_001Action();
		queueResponse(distributeEvent); 
		addResponse();
		
		IResponseCreateReportTypeAckAction createReport = new DefaultResponseCreateReportRequest_001Action(ServiceType.VTN);
		createReport.setlastEvent(true);
		queueResponse(createReport);
		addResponse();
		
		addUpdatedReportResponse().setlastEvent(true);
		
		listenForRequests();

		waitForCompletion();
		
		checkDistributeEvent(distributeEvent);
		checkCreateReport(createReport);
	}
}
