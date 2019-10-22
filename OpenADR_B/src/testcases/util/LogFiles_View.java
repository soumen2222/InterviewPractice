package testcases.util;

import java.awt.Desktop;
import java.io.File;

import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.common.UIUserPrompt;
import com.qualitylogic.openadr.core.util.PropertiesFileReader;
import com.qualitylogic.openadr.core.util.ResourceFileReader;

public class LogFiles_View {

	static public void main(String[] args) {
		ResourceFileReader resourceFileReader = new ResourceFileReader();
		UIUserPrompt uiUserPrompt = new UIUserPrompt();
		uiUserPrompt.Prompt(resourceFileReader.View_Log_Files());
		
		if (TestSession.isUserClickedContinuePlay()) {
		
			try {
				Desktop desktop = null;
				PropertiesFileReader propertiesReader = new PropertiesFileReader();
			
				String logPath = propertiesReader.getLogPath();
	
				File logFile = new File(logPath + propertiesReader.getlogFileName());
	
				if (Desktop.isDesktopSupported()) {
					desktop = Desktop.getDesktop();
					System.out.println("Opening " + logFile + ".....");
					desktop.open(logFile);
				} else {
					System.out.println("Unable to launch your default browser");
				}
			} catch (Exception e) {
				System.out.println("No log file is available to open");
			}
			System.out.println("\nView Log Files Complete");	

		}	
	}
}