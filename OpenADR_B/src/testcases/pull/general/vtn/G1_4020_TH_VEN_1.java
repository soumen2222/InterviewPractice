package testcases.pull.general.vtn;

import testcases.push.general.vtn.G0_9020_TH_VEN_1;

import com.qualitylogic.openadr.core.base.VenPullTestCase;

public class G1_4020_TH_VEN_1 extends VenPullTestCase {

	public static void main(String[] args) {
		execute(new G1_4020_TH_VEN_1());
	}

	@Override
	public void test() throws Exception {
		new G0_9020_TH_VEN_1().test();
	}
}
