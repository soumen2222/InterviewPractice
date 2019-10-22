package testcases.pull.general.vtn;

import com.qualitylogic.openadr.core.base.VenPullTestCase;
import com.qualitylogic.openadr.core.bean.ServiceType;
import com.qualitylogic.openadr.core.signal.OadrCreateReportType;
import com.qualitylogic.openadr.core.signal.OptTypeType;
import com.qualitylogic.openadr.core.signal.helper.UpdateReportEventHelper;

public class G1_4015_TH_VEN extends VenPullTestCase {

	public static void main(String[] args) {
		execute(new G1_4015_TH_VEN());
	}

	@Override
	public void test() throws Exception {
		checkActiveRegistration();
		
		prompt(resources.prompt_059());
		
		String distributeEvent = pollDistributeEventString();

		OadrCreateReportType createReport = pollCreateReport();
		
		pollResponse();
		
		sendCreatedEvent(distributeEvent, OptTypeType.OPT_IN);
		
		sendCreatedReport();
		
		sendUpdateReport(UpdateReportEventHelper.loadOadrUpdateReport_Update001(ServiceType.VEN, true, createReport));
	}
}
