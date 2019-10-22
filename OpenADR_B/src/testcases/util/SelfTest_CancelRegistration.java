package testcases.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.common.UIUserPrompt;
import com.qualitylogic.openadr.core.util.PropertiesFileReader;

public class SelfTest_CancelRegistration {

	public static void main(String args[]){
		
		String []dbFiles=new String[]{new PropertiesFileReader().getXMLDBFile_VEN(),new PropertiesFileReader().getXMLDBFile_VTN()};
		UIUserPrompt uiUserPrompt = new UIUserPrompt();
		uiUserPrompt.Prompt("This utility will reset the device and report registration to an unregistered state in the local test harness vendb.xml and vtndb.xml file.");
		
		if (!TestSession.isUserClickedContinuePlay()) {
					return;
		}
		
		for(String eachFile:dbFiles){

			String line="\n";
			try {
				BufferedWriter out = new BufferedWriter(new FileWriter(eachFile));
				out.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>");
				out.write(line);
				out.write("<db>");
				out.write(line);
				out.write("<VEN>");
				out.write(line);
				out.write("<ID/>");
				out.write(line);
				out.write("<REGISTRATION_ID/>");
				out.write(line);
				out.write("<TRANSPORT_ADDRESS/>");
				out.write(line);
				out.write("</VEN>");
				out.write(line);
				out.write("<VTN_REGISTER_REPORT_RECEIVED/>");
				out.write(line);
				out.write("<VEN_REGISTER_REPORT_RECEIVED/>");
				out.write(line);
				out.write("</db>");
				out.close();
			} catch (IOException e){ 
				e.printStackTrace();
			}
		}
		
		System.out.println("\nCancelSelfTestRegistration done.");
	}
}
