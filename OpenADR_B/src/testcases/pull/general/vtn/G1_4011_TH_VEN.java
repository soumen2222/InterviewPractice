package testcases.pull.general.vtn;

import com.qualitylogic.openadr.core.base.VenPullTestCase;

public class G1_4011_TH_VEN extends VenPullTestCase {

	public static void main(String[] args) {
		execute(new G1_4011_TH_VEN());
	}

	@Override
	public void test() throws Exception {
		pollResponse();
	}
}
