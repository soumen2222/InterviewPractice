package testcases.util;

import java.io.File;
import java.io.IOException;

import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.common.UIUserPrompt;
import com.qualitylogic.openadr.core.util.PropertiesFileReader;
import com.qualitylogic.openadr.core.util.ResourceFileReader;

public class LogFiles_Clear {

	public static void main(String args[]) throws IOException {
		PropertiesFileReader propertiesReader = new PropertiesFileReader();

		ResourceFileReader resourceFileReader = new ResourceFileReader();
		UIUserPrompt uiUserPrompt = new UIUserPrompt();
		uiUserPrompt.Prompt(resourceFileReader.Clear_Log_Files());
		
		if (TestSession.isUserClickedContinuePlay()) {
		
			String logPath = propertiesReader.getLogPath();
	
			File directory = new File(logPath);
			File[] files = directory.listFiles();
			for (File file : files) {
				if (!file.delete())
	
				{
					System.out.println("Unable  to delete " + file);
	
				}
			}
			System.out.println("\nClear Log Files Complete");
		}
	
	}
}
