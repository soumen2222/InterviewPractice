package com.qualitylogic.openadr.core.util;

import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

import com.qualitylogic.openadr.core.common.TestSession;

public class ResourceFileReader {

	static Properties props = new Properties();
	ResourceBundle res = null;

	public ResourceFileReader() {
		res = ResourceBundle.getBundle("com.qualitylogic.openadr.core.resources.openadrresource", new Locale("en", "US"));
	}

	public String get(String key) {
		try {
			Object value = res.getObject(key);
			if (value != null)
				return (String) value;
			return "";
		} catch (Exception e) {
			return "** Cannot find the key " + key
					+ " in resource string file **";
		}

	}

	public String Clear_Log_Files() {
		return get("Clear_Log_Files");
	}
	
	public String View_Log_Files() {
		return get("View_Log_Files");
	}
	
	public String UIUserPromptButtonLabel() {
		return get("UIUserPromptButtonLabel");
	}

	public String LogMessage_VerifiedActiveEvent() {
		return get("LogMessage_VerifiedActiveEvent");
	}

	public String LogMessage_UnableTOVerifyActiveEvent() {
		return get("LogMessage_UnableTOVerifyActiveEvent");
	}

	public String Prereq_NoEvents_Message() {
		return get("Prereq_NoEvents_Message");
	}

	public String Push_VTN_EndOFTestFlush() {
		return get("Push_VTN_EndOFTestFlush");
	}

	public String TestCase_0070_UIUserPromptText() {
		return get("TestCase_0070_UIUserPromptText");
	}

	public String TestCase_0130_UIUserPromptText() {
		return get("TestCase_0130_UIUserPromptText");
	}

	public String TestCase_0200_UIUserPromptText() {
		return get("TestCase_0200_UIUserPromptText");
	}

	public String TestCase_0210_UIUserPromptText() {
		return get("TestCase_0210_UIUserPromptText");
	}

	public String TestCase_0210_UIUserPromptDualActionQuestion() {
		return get("TestCase_0210_UIUserPromptDualActionQuestion");
	}

	public String TestCase_1210_UIUserPromptDualActionQuestion() {
		return get("TestCase_1210_UIUserPromptDualActionQuestion");
	}

	public String TestCase_0220_UIUserPromptText() {
		return get("TestCase_0220_UIUserPromptText");
	}

	public String TestCase_0220_UIUserPromptDualActionQuestion() {
		return get("TestCase_0220_UIUserPromptDualActionQuestion");
	}

	public String TestCase_0230_UIUserPromptText() {
		return get("TestCase_0230_UIUserPromptText");
	}

	public String TestCase_0230_UIUserPromptDualActionQuestion() {
		return get("TestCase_0230_UIUserPromptDualActionQuestion");
	}

	public String TestCase_0240_UIUserPromptText() {
		return get("TestCase_0240_UIUserPromptText");
	}

	public String TestCase_0240_UIUserPromptDualActionQuestion() {
		return get("TestCase_0240_UIUserPromptDualActionQuestion");
	}

	public String TestCase_0250_UIUserPromptText() {
		return get("TestCase_0250_UIUserPromptText");
	}

	public String TestCase_0250_UIUserPromptDualActionQuestion() {
		return get("TestCase_0250_UIUserPromptDualActionQuestion");
	}

	public String TestCase_0260_UIUserPromptText() {
		return get("TestCase_0260_UIUserPromptText");
	}

	public String TestCase_0260_UIUserPromptDualActionQuestion() {
		return get("TestCase_0260_UIUserPromptDualActionQuestion");
	}

	public String TestCase_0262_UIUserPromptText() {
		return get("TestCase_0262_UIUserPromptText");
	}

	public String TestCase_0262_UIUserPromptDualActionQuestion() {
		return get("TestCase_0262_UIUserPromptDualActionQuestion");
	}

	public String TestCase_0267_UIUserPromptText() {
		return get("TestCase_0267_UIUserPromptText");
	}

	public String TestCase_0267_SecondUIUserPromptText() {
		return get("TestCase_0267_SecondUIUserPromptText");
	}

	public String TestCase_0267_UIUserPromptDualActionQuestion() {
		return get("TestCase_0267_UIUserPromptDualActionQuestion");
	}

	public String TestCase_0268_UIUserPromptText() {
		return get("TestCase_0268_UIUserPromptText");
	}

	public String TestCase_0268_SecondUIUserPromptText() {
		return get("TestCase_0268_SecondUIUserPromptText");
	}

	public String TestCase_0268_UIUserPromptDualActionQuestion() {
		return get("TestCase_0268_UIUserPromptDualActionQuestion");
	}

	public String TestCase_0270_UIUserPromptText() {
		return get("TestCase_0270_UIUserPromptText");
	}

	public String TestCase_0270_UIUserPromptDualActionQuestion() {
		return get("TestCase_0270_UIUserPromptDualActionQuestion");
	}

	public String TestCase_0280_UIUserPromptText() {
		return get("TestCase_0280_UIUserPromptText");
	}

	public String TestCase_0280_UIUserPromptDualActionQuestion() {
		return get("TestCase_0280_UIUserPromptDualActionQuestion");
	}

	public String TestCase_0290_UIUserPromptText() {
		return get("TestCase_0290_UIUserPromptText");
	}

	public String TestCase_0290_Second_UIUserPromptText() {
		return get("TestCase_0290_Second_UIUserPromptText");
	}

	public String TestCase_0295_UIUserPromptText() {
		return get("TestCase_0295_UIUserPromptText");
	}

	public String TestCase_0297_UIUserPromptText() {
		return get("TestCase_0297_UIUserPromptText");
	}

	public String TestCase_0350_UIUserPromptText() {
		return get("TestCase_0350_UIUserPromptText");
	}

	public String TestCase_0370_UIUserPromptText() {
		return get("TestCase_0370_UIUserPromptText");
	}

	public String TestCase_0370_UIUserPromptDualActionQuestion() {
		return get("TestCase_0370_UIUserPromptDualActionQuestion");
	}

	public String TestCase_0392_UIUserPromptText() {
		return get("TestCase_0392_UIUserPromptText");
	}

	public String TestCase_0392_UIUserPromptText2() {
		return get("TestCase_0392_UIUserPromptText2");
	}

	public String TestCase_0420_UIUserPromptText() {
		return get("TestCase_0420_UIUserPromptText");
	}

	public String TestCase_0430_UIUserPromptText() {
		return get("TestCase_0430_UIUserPromptText");
	}

	public String TestCase_0432_UIUserPromptText() {
		return get("TestCase_0432_UIUserPromptText");
	}

	public String TestCase_0435_UIUserPromptText() {
		return get("TestCase_0435_UIUserPromptText");
	}

	public String TestCase_0440_Push_UIUserPromptText() {
		return get("TestCase_0440_UIUserPromptText");
	}

	public String TestCase_0440_Pull_UIUserPromptText() {
		return get("TestCase_0440_UIUserPromptText");
	}

	public String TestCase_0450_UIUserPromptText() {
		return get("TestCase_0450_UIUserPromptText");
	}

	public String TestCase_0452_UIUserPromptText() {
		return get("TestCase_0452_UIUserPromptText");
	}

	public String TestCase_0454_UIUserPromptText() {
		return get("TestCase_0454_UIUserPromptText");
	}

	public String TestCase_0464_UIUserPromptText() {
		return get("TestCase_0464_UIUserPromptText");
	}

	public String TestCase_0464_SecondUIUserPromptText() {
		return get("TestCase_0464_SecondUIUserPromptText");
	}

	public String TestCase_0466_UIUserPromptText() {
		return get("TestCase_0466_UIUserPromptText");
	}

	public String TestCase_0466_SecondUIUserPromptText() {
		return get("TestCase_0466_SecondUIUserPromptText");
	}

	public String TestCase_0468_UIUserPromptText() {
		return get("TestCase_0468_UIUserPromptText");
	}

	public String TestCase_0470_UIUserPromptText() {
		return get("TestCase_0470_UIUserPromptText");
	}

	public String TestCase_0470_SecondUIUserPromptText() {
		return get("TestCase_0470_SecondUIUserPromptText");
	}

	public String TestCase_0474_UIUserPromptText() {
		return get("TestCase_0474_UIUserPromptText");
	}

	public String TestCase_0474_Second_UIUserPromptText() {
		return get("TestCase_0474_Second_UIUserPromptText");
	}

	public String TestCase_0476_UIUserPromptText() {
		return get("TestCase_0476_UIUserPromptText");
	}

	public String TestCase_0476_SecondUIUserPromptText() {
		return get("TestCase_0476_SecondUIUserPromptText");
	}

	public String TestCase_0476_Push_ThirdUIUserPromptText() {
		return get("TestCase_0476_Push_ThirdUIUserPromptText");
	}

	public String TestCase_0478_UIUserPromptText() {
		return get("TestCase_0478_UIUserPromptText");
	}

	public String TestCase_0478_SecondUIUserPromptText() {
		return get("TestCase_0478_SecondUIUserPromptText");
	}

	public String TestCase_0478_Push_ThirdUIUserPromptText() {
		return get("TestCase_0478_Push_ThirdUIUserPromptText");
	}

	public String TestCase_0480_UIUserPromptText() {
		return get("TestCase_0480_UIUserPromptText");
	}

	public String TestCase_0480_Second_UIUserPromptText() {
		return get("TestCase_0480_Second_UIUserPromptText");
	}

	public String TestCase_0484_UIUserPromptText() {
		return get("TestCase_0484_UIUserPromptText");
	}

	public String TestCase_0490_UIUserPromptText() {
		return get("TestCase_0490_UIUserPromptText");
	}

	public String TestCase_0490_SecondUIUserPrompt() {
		return get("TestCase_0490_SecondUIUserPrompt");
	}

	public String TestCase_0492_UIUserPromptText() {
		return get("TestCase_0492_UIUserPromptText");
	}

	public String TestCase_0492_Second_UIUserPromptText() {
		return get("TestCase_0492_Second_UIUserPromptText");
	}

	public String TestCase_0494_UIUserPromptText() {
		return get("TestCase_0494_UIUserPromptText");
	}

	public String TestCase_0494_SecondUIUserPrompt() {
		return get("TestCase_0494_SecondUIUserPrompt");
	}

	public String TestCase_0496_UIUserPromptText() {
		return get("TestCase_0496_UIUserPromptText");
	}

	public String TestCase_0496_Second_UIUserPromptText() {
		return get("TestCase_0496_Second_UIUserPromptText");
	}

	public String TestCase_0498_UIUserPromptText() {
		return get("TestCase_0498_UIUserPromptText");
	}

	public String TestCase_0498_Second_UIUserPromptText() {
		return get("TestCase_0498_Second_UIUserPromptText");
	}

	public String TestCase_0500_UIUserPromptText() {
		return get("TestCase_0500_UIUserPromptText");
	}

	public String TestCase_0510_UIUserPromptText() {
		return get("TestCase_0510_UIUserPromptText");
	}

	public String TestCase_0520_UIUserPromptText() {
		return get("TestCase_0520_UIUserPromptText");
	}

	public String TestCase_0520_UIUserPromptText_End() {
		return get("TestCase_0520_UIUserPromptText_End");
	}

	public String TestCase_0525_UIUserPromptTex() {
		return get("TestCase_0525_UIUserPromptText");
	}

	public String TestCase_0525__Second_UIUserPromptText() {
		return get("TestCase_0525__Second_UIUserPromptText");
	}

	public String TestCase_0527_Pull_UIUserPromptText() {
		return get("TestCase_0527_Pull_UIUserPromptText");
	}
	
	public String TestCase_0527_Push_UIUserPromptText() {
		return get("TestCase_0527_Push_UIUserPromptText");
	}

	public String TestCase_0530_UIUserPromptText() {
		return get("TestCase_0530_UIUserPromptText");
	}

	public String TestCase_0530_UIUserPromptDualActionQuestion() {
		return get("TestCase_0530_UIUserPromptDualActionQuestion");
	}

	public String TestCase_0655_UIUserPromptText() {
		return get("TestCase_0655_UIUserPromptText");
	}

	public String TestCase_0657_UIUserPromptText() {
		return get("TestCase_0657_UIUserPromptText");
	}

	public String TestCase_0657_Second_UIUserPromptText() {
		return get("TestCase_0657_Second_UIUserPromptText");
	}

	public String TestCase_0657_Third_UIUserPromptText() {
		return get("TestCase_0657_Third_UIUserPromptText");
	}

	public String TestCase_0660_UIUserPromptText() {
		return get("TestCase_0660_UIUserPromptText");
	}

	public String TestCase_0660_Second_UIUserPromptText() {
		return get("TestCase_0660_Second_UIUserPromptText");
	}

	public String TestCase_0670_UIUserPromptText() {
		return get("TestCase_0670_UIUserPromptText");
	}

	public String TestCase_0680_UIUserPromptText() {
		return get("TestCase_0680_UIUserPromptText");
	}

	public String TestCase_0680_Second_Pull_UIUserPromptText() {
		return get("TestCase_0680_Second_Pull_UIUserPromptText");
	}

	public String TestCase_0680_Second_Push_UIUserPromptText() {
		return get("TestCase_0680_Second_Push_UIUserPromptText");
	}

	public String TestCase_0685_UIUserPromptText() {
		return get("TestCase_0685_UIUserPromptText");
	}

	public String TestCase_E2_0685_UIUserPromptText() {
		return get("TestCase_E2_0685_UIUserPromptText");
	}

	public String TestCase_0710_UIUserPromptText() {
		return get("TestCase_0710_UIUserPromptText");
	}

	public String TestCase_0720_UIUserPromptText() {
		return get("TestCase_0720_UIUserPromptText");
	}

	public String TestCase_0730_UIUserPromptText() {
		return get("TestCase_0730_UIUserPromptText");
	}

	public String TestCase_0740_UIUserPromptText() {
		return get("TestCase_0740_UIUserPromptText");
	}

	public String TestCase_0750_UIUserPromptText() {
		return get("TestCase_0750_UIUserPromptText");
	}

	public String TestCase_0780_UIUserPromptText() {
		return get("TestCase_0780_UIUserPromptText");
	}

	public String TestCase_1200_UIUserPromptDualActionQuestion() {
		return get("TestCase_1200_UIUserPromptDualActionQuestion");
	}
	
	public String TestCase_RandomizedCancellationPrompt() {
		return get("TestCase_RandomizedCancellationPrompt");
	}
	
	public String TestCase_RandomizedCancellationCompletionPrompt(){
		return get("TestCase_RandomizedCancellationCompletionPrompt");		
	}

	public String TestCase_RandomizedCancellationFailWarningPrompt(){
		return get("TestCase_RandomizedCancellationFailWarningPrompt");		
	}
		
	public String TestCase_STLS1012ECC_UIUserPromptText(){
		return get("TestCase_STLS1012ECC_UIUserPromptText");		
	}
	
	public String TestCase_STLS1012RSA_UIUserPromptText(){
		return get("TestCase_STLS1012RSA_UIUserPromptText");		
	}
	
	public String TestCase_STLS12ECC_UIUserPromptText(){
		return get("TestCase_STLS12ECC_UIUserPromptText");		
	}
	
	public String TestCase_SConfirmTLS10ClientCip_UIUserPromptText(){
		return get("TestCase_SConfirmTLS10ClientCip_UIUserPromptText");		
	}
	
	public String TestCase_STLS12RSA_UIUserPromptText(){
		return get("TestCase_STLS12RSA_UIUserPromptText");		
	}
	
	public String TestCase_STLS12_UIUserPromptText(){
		return get("TestCase_STLS12_UIUserPromptText");		
	}
	
	public String LogMessage_VerifiedSecurity() {
		return get("LogMessage_VerifiedSecurity");
	}

	public String LogMessage_UnableTOVerifySecurity() {
		return get("LogMessage_UnableTOVerifySecurity");
	}
	
	public String TestCase_STLS12_UIUserPromptDualActionQuestion() {
		return get("TestCase_STLS12_UIUserPromptDualActionQuestion");
	}
	
	public String prompt_001() {
		return get("Prompt_001");
	}
	public String prompt_002() {
		return get("Prompt_002");
	}
	public String prompt_003() {
		return get("Prompt_003");
	}
	public String prompt_004() {
		return get("Prompt_004");
	}
	public String prompt_005() {
		return get("Prompt_005");
	}
	public String prompt_006() {
		return get("Prompt_006");
	}
	public String prompt_007(){
		return get("Prompt_007");
	}
	public String prompt_008(){
		return get("Prompt_008");
	}
	public String prompt_009(){
		return get("Prompt_009");
	}
	public String prompt_010(){
		return get("Prompt_010");
	}
	public String prompt_011(){
		return get("Prompt_011");
	}
	public String prompt_012(){
		return get("Prompt_012");
	}
	public String prompt_013(){
		return get("Prompt_013");
	}
	public String prompt_014(){
		return get("Prompt_014");
	}
	public String prompt_015(){
		return get("Prompt_015");
	}
	public String prompt_016(){
		return get("Prompt_016");
	}
	public String prompt_017(){
		return get("Prompt_017");
	}
	
	public String prompt_018(){
		return get("Prompt_018");
	}
	
	public String prompt_019(){
		return get("Prompt_019");
	}
	
	public String prompt_020(){
		return get("Prompt_020");
	}
	
	public String prompt_021(){
		return get("Prompt_021");
	}
	
	public String prompt_022(){
		return get("Prompt_022");
	}
	
	public String prompt_023(){
		return get("Prompt_023");
	}
	
	public String prompt_024(){
		return get("Prompt_024");
	}
	
	public String prompt_025(){
		return get("Prompt_025");
	}
	
	public String prompt_026(){
		return get("Prompt_026");
	}
	
	public String prompt_027(){
		return get("Prompt_027");
	}
	
	public String prompt_028(){
		return get("Prompt_028");
	}
	
	public String prompt_029(){
		return get("Prompt_029");
	}
	
	public String prompt_030(){
		return get("Prompt_030");
	}

	public String prompt_032(){
		return get("Prompt_032");
	}

	public String prompt_033() { return get("Prompt_033"); }
	public String prompt_034() { return get("Prompt_034"); }
	public String prompt_035() { return get("Prompt_035"); }
	public String prompt_036() { return get("Prompt_036"); }
	public String prompt_037() { return get("Prompt_037"); }
	public String prompt_038() { return get("Prompt_038"); }
	public String prompt_039() { return get("Prompt_039"); }
	public String prompt_040() { return get("Prompt_040"); }
	public String prompt_041() { return get("Prompt_041"); }
	public String prompt_042() { return get("Prompt_042"); }
	public String prompt_043() { return get("Prompt_043"); }
	public String prompt_044() { return get("Prompt_044"); }
	public String prompt_045() { return get("Prompt_045"); }
	public String prompt_046() { return get("Prompt_046"); }
	public String prompt_047() { return get("Prompt_047"); }
	public String prompt_048() { return get("Prompt_048"); }
	public String prompt_049() { return get("Prompt_049"); }
	public String prompt_050() { return get("Prompt_050"); }
	public String prompt_051() { return get("Prompt_051"); }
	public String prompt_052() { return get("Prompt_052"); }
	public String prompt_053() { return get("Prompt_053"); }
	public String prompt_054() { return get("Prompt_054"); }
	public String prompt_055() { return get("Prompt_055"); }
	public String prompt_056() { return get("Prompt_056"); }
	public String prompt_057() { return get("Prompt_057"); }
	public String prompt_058() { return get("Prompt_058"); }
	public String prompt_059() { return get("Prompt_059"); }
	public String prompt_060() { return get("Prompt_060"); }
	public String prompt_061() { return get("Prompt_061"); }
	public String prompt_062() { return get("Prompt_062"); }
	public String prompt_063() { return get("Prompt_063"); }
	public String prompt_064() { return get("Prompt_064"); }
	public String prompt_065() { return get("Prompt_065"); }
	public String prompt_066() { return get("Prompt_066"); }
	public String prompt_067() { return get("Prompt_067"); }
	public String prompt_068() { return get("Prompt_068"); }
	public String prompt_069() { return get("Prompt_069"); }
	public String prompt_070() { return get("Prompt_070"); }
	
	// Get test case description string from resources
	public String TestCase_Intent_Description() {
		
		Trace trace = TestSession.getTraceObj();
		if(trace==null)return "";
		
		String testCaseName = trace.getTestCaseName();
		if (testCaseName.startsWith("A_")) {
			testCaseName = testCaseName.substring(2);
		}
		return get(testCaseName.substring(0, 7));
		
	}

	// Get test case name
		public String TestCase_Name() {
			
			Trace trace = TestSession.getTraceObj();
			if(trace==null)return "";
			
			return "    ("+ trace.getTestCaseName() + ")";
			
		}
}