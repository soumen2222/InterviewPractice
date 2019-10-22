package testcases.pull.event.vtn;

import com.qualitylogic.openadr.core.base.VenPullTestCase;
import com.qualitylogic.openadr.core.bean.VTNServiceType;
import com.qualitylogic.openadr.core.exception.FailedException;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType;
import com.qualitylogic.openadr.core.signal.OadrPollType;
import com.qualitylogic.openadr.core.signal.OadrResponseType;
import com.qualitylogic.openadr.core.signal.helper.PollEventSignalHelper;
import com.qualitylogic.openadr.core.signal.helper.SchemaHelper;
import com.qualitylogic.openadr.core.ven.VenToVtnClient;

public class E1_1010_TH_VEN extends VenPullTestCase {

	public static void main(String[] args) {
		execute(new E1_1010_TH_VEN());
	}

	@Override
	public void test() throws Exception {
		prompt(resources.prompt_009());
		
		OadrPollType oadrPollType = PollEventSignalHelper
				.loadOadrPollType();
		String stroadrPollType = SchemaHelper.getOadrPollTypeAsString(oadrPollType);
		String data = VenToVtnClient
				.post(stroadrPollType,VTNServiceType.OadrPoll);

		Class dataType = SchemaHelper.getObjectType(data);
		if(!dataType.equals(OadrDistributeEventType.class) && !dataType.equals(OadrResponseType.class)){
			throw new FailedException("The expected OadrDistributeEvent or OadrResponse has not been received");
		}

	}
}
