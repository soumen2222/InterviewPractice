package com.qualitylogic.openadr.core.common;

import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

import com.qualitylogic.openadr.core.util.PropertiesFileReader;
import com.qualitylogic.openadr.core.util.ResourceFileReader;
import com.qualitylogic.openadr.core.util.Trace;

public class UIUserPrompt implements IPrompt {
	static public int SMALL = 0;
	static public int MEDIUM = 1;
	static public int LARGE = 2;
	private boolean isPauseDisabled = false;
	
	public boolean isPauseDisabled() {
		return isPauseDisabled;
	}

	public void setPauseDisabled(boolean isPauseDisabled) {
		this.isPauseDisabled = isPauseDisabled;
	}

	JFrame myJframe = new JFrame("");
	String promptMessage = "";

	public String getPromptMessage() {
		return promptMessage;
	}

	int size = -1;

	public UIUserPrompt() {

	}

	public UIUserPrompt(String promptMessage) {
		this(promptMessage, autoDetermineSize(promptMessage));
	}

	private static int autoDetermineSize(String promptMessage) {
		int newlineCount = promptMessage.length() - promptMessage.replaceAll("\n", "").length();
		
		int size = UIUserPrompt.SMALL;
		if (promptMessage.length() > (570 - (newlineCount * 60))) {
			size = UIUserPrompt.LARGE;
		} else if (promptMessage.length() > (165 - newlineCount * 45)) {
			size = UIUserPrompt.MEDIUM;
		}
		return size;
	}

	public UIUserPrompt(String promptMessage, int size) {
		this.promptMessage = promptMessage;
		this.size = size;
	}

	// Default prompt
	public void Prompt(String message) {
		PropertiesFileReader properties = new PropertiesFileReader(); 
		if (!properties.isTestPrompt()) {
			ResourceFileReader resources = new ResourceFileReader();
			if (message.equals(resources.TestCase_0657_Third_UIUserPromptText())) {
				try {
					Thread.sleep(2 * 60 * 1000);
				} catch (InterruptedException e) {
					// ignore
				}
			}
			TestSession.setUserClickedContinuePlay(true);
			return;
		}
		
		if (this.size != -1) {
			Prompt(message, this.size);
		} else {
			Prompt(message, autoDetermineSize(message));
		}
	}

	public void Prompt(String message, int size) {
		PropertiesFileReader properties = new PropertiesFileReader(); 
		if (!properties.isTestPrompt()) {
			TestSession.setUserClickedContinuePlay(true);
			return;
		}
		
		TestSession.setUserClickedContinuePlay(false);
		TestSession.setUserClickedToCompleteUIAction(false);
		ResourceFileReader resourceFileReader = new ResourceFileReader();
		myJframe.setAlwaysOnTop(true);
		myJframe.setVisible(true);
		myJframe.setSize(400, 300);
		myJframe.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		myJframe.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {

				myJframe.setAlwaysOnTop(false);
				int confirmed = JOptionPane
						.showConfirmDialog(
								null,
								"Are you sure you want to exit and cancel the TestCase execution?",
								"User Confirmation", JOptionPane.YES_NO_OPTION);

				if (confirmed == JOptionPane.YES_OPTION) {
					exit(myJframe);
				} else {
					myJframe.setAlwaysOnTop(true);
				}
			}
		});

		JPanel panel = new JPanel();


		String buttonName = resourceFileReader.UIUserPromptButtonLabel();

		String buttonName1 = "Cancel";

		panel.setLayout(null);
		JButton continueExecution = new JButton(buttonName);
		JButton continueExecution1 = new JButton(buttonName1);

		if (size == SMALL) {
			continueExecution.setBounds(60, 100, 170, 25);
			continueExecution1.setBounds(240, 100, 80, 25);
		} else if (size == MEDIUM) {
			continueExecution.setBounds(110, 200, 170, 25);
			continueExecution1.setBounds(290, 200, 80, 25);
		} else {
			continueExecution.setBounds(160, 300, 170, 25);
			continueExecution1.setBounds(340, 300, 80, 25);
		}

		continueExecution
				.addActionListener(new UIUserPrompt().new ButtonListener(
						myJframe));
		continueExecution1
				.addActionListener(new UIUserPrompt().new ButtonListener1(
						myJframe));

		JTextArea _resultArea = new JTextArea();
		_resultArea.setEditable(false);
		_resultArea.setText(message + " Please click '" + buttonName
				+ "' to continue" + resourceFileReader.TestCase_Name());
		if (size == SMALL) {
			_resultArea.setBounds(0, 0, 385, 100);
		} else if (size == MEDIUM) {
			_resultArea.setBounds(0, 0, 485, 200);
		} else {
			_resultArea.setBounds(0, 0, 585, 300);
		}

		_resultArea.setLineWrap(true);
		_resultArea.setWrapStyleWord(true);
		_resultArea.setMargin(new Insets(6, 6, 6, 6));
		Font font = new Font("Verdana", Font.PLAIN, 12);
		_resultArea.setFont(font);

		panel.add(_resultArea);
		panel.add(continueExecution);
		panel.add(continueExecution1);

		myJframe.add(panel);

		myJframe.setTitle(resourceFileReader.TestCase_Intent_Description());
		if (size == SMALL) {
			myJframe.setSize(400, 165);
		} else if (size == MEDIUM) {
			myJframe.setSize(500, 265);
		} else {
			myJframe.setSize(600, 365);
		}

		myJframe.setLocationRelativeTo(null);
		Trace trace = TestSession.getTraceObj();
		String waitingForUserAction = "\nWaiting for user action :"
				+ new Date() + "\n";
		if (trace != null) {
			trace.getLogFileContentTrace().append(waitingForUserAction);
		}
	
		if(!isPauseDisabled()){
			System.out.print(waitingForUserAction);
			pauseForResponseOnSingleOptionUIPrompt();
		}
	}

	class ButtonListener implements ActionListener {
		JFrame jframe;

		public ButtonListener(JFrame jframe) {
			this.jframe = jframe;
		}

		public void actionPerformed(ActionEvent e) {
			TestSession.setUserClickedContinuePlay(true);
			TestSession.setUserClickedToCompleteUIAction(true);
			String actionReceived = "User action received. Playing Test Case again. :"
					+ new Date() + "\n";
			Trace trace = TestSession.getTraceObj();
			if (trace != null) {
				trace.getLogFileContentTrace().append(actionReceived);
			}

			System.out.print(actionReceived);
			jframe.dispose();

		}

	}

	class ButtonListener1 implements ActionListener {
		JFrame jframe;

		public ButtonListener1(JFrame jframe) {
			this.jframe = jframe;
		}

		public void actionPerformed(ActionEvent e) {
			jframe.setAlwaysOnTop(false);
			int confirmed = JOptionPane
					.showConfirmDialog(
							null,
							"Are you sure you want to exit and cancel the TestCase execution?",
							"User Confirmation", JOptionPane.YES_NO_OPTION);

			if (confirmed == JOptionPane.YES_OPTION) {
				exit(jframe);
			} else {
				jframe.setAlwaysOnTop(true);
			}

		}

	}

	static void exit(JFrame j) {

		TestSession.setUserClickedContinuePlay(false);
		TestSession.setUserClickedToCompleteUIAction(true);

		String actionReceived = "User action received. Cancelling Test Case execution.\n";
		Trace trace = TestSession.getTraceObj();
		if (trace != null) {
			trace.getLogFileContentTrace().append(
					"\nReceived user action :" + new Date() + "\n");
			trace.getLogFileContentTrace().append(actionReceived);
		}
		System.out.print(actionReceived);

		j.dispose();
	}

	private static void pauseForResponseOnSingleOptionUIPrompt() {
		while (!TestSession.isUserClickedToCompleteUIAction()) {
			System.out.print("");

			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
			}

		}
	}
}