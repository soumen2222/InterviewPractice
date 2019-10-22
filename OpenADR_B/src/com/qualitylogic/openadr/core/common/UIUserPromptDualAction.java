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

public class UIUserPromptDualAction implements IPrompt {
	public int SMALL = 0;
	public int MEDIUM = 1;
	public int LARGE = 2;

	JFrame myJframe = new JFrame("");
	String promptMessage = "";

	public String getPromptMessage() {
		return promptMessage;
	}

	public UIUserPromptDualAction() {

	}

	public UIUserPromptDualAction(String promptMessage) {
		this.promptMessage = promptMessage;
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
	
	// Default prompt.
	public void Prompt(String question) {
		Prompt(question, autoDetermineSize(question));
	}

	public void Prompt(String question, int size) {
		PropertiesFileReader properties = new PropertiesFileReader(); 
		if (!properties.isTestPrompt()) {
			ResourceFileReader resources = new ResourceFileReader();
			if (question.equals(resources.TestCase_0280_UIUserPromptDualActionQuestion())) {
				try {
					Thread.sleep(30 * 1000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
			
			TestSession.setUiDualActionYesOptionClicked(true);
			TestSession.setUserClickedToCompleteUIDualAction(true);
			TestSession.setUserClickedContinuePlay(true);
			return;
		}
		
		TestSession.setUserClickedToCompleteUIDualAction(false);
		TestSession.setUserClickedContinuePlay(false);

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
					TestSession.setUserClickedToCancelUIDualAction(true);
					exit(myJframe);
				} else {
					myJframe.setAlwaysOnTop(true);
				}
			}
		});

		JPanel panel = new JPanel();

		panel.setLayout(null);
		JButton yesButton = new JButton("Yes");

		if (size == SMALL) {
			yesButton.setBounds(120, 100, 80, 25);
		} else if (size == MEDIUM) {
			yesButton.setBounds(170, 200, 80, 25);
		} else {
			yesButton.setBounds(220, 300, 80, 25);
		}

		yesButton
				.addActionListener(new UIUserPromptDualAction().new ButtonListener(
						myJframe));

		JButton noButton = new JButton("No");

		if (size == SMALL) {
			noButton.setBounds(205, 100, 80, 25);
		} else if (size == MEDIUM) {
			noButton.setBounds(255, 200, 80, 25);
		} else {
			noButton.setBounds(305, 300, 80, 25);
		}

		noButton.addActionListener(new UIUserPromptDualAction().new ButtonListener(
				myJframe));
		ResourceFileReader resourceFileReader = new ResourceFileReader();
		JTextArea _resultArea = new JTextArea();
		_resultArea.setEditable(false);
		_resultArea.setText(question  + resourceFileReader.TestCase_Name());
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
		panel.add(yesButton);
		panel.add(noButton);
		myJframe.add(panel);

		myJframe.setTitle("OpenADR TestHarness");
		if (size == SMALL) {
			myJframe.setSize(400, 165);
		} else if (size == MEDIUM) {
			myJframe.setSize(500, 265);
		} else {
			myJframe.setSize(600, 365);
		}
		myJframe.setLocationRelativeTo(null);
		Trace trace = TestSession.getTraceObj();
		String userActionWaitingText = "\nWaiting for user action : "
				+ new Date() + "\n";
		if (trace != null) {
			trace.getLogFileContentTrace().append(userActionWaitingText);
		}
		System.out.print(userActionWaitingText);

		pauseForUserActionOnDualActionDialog();

	}

	class ButtonListener implements ActionListener {
		JFrame jframe;

		ButtonListener(JFrame myJframe) {
			this.jframe = myJframe;
		}

		public void actionPerformed(ActionEvent e) {
			String actionReceived = "User action received. Playing Test Case again. :"
					+ new Date() + "\n";
			Trace trace = TestSession.getTraceObj();
			if (trace != null) {
				trace.getLogFileContentTrace().append(actionReceived);
			}
			System.out.print(actionReceived);
			JButton o = (JButton) e.getSource();
			String label = o.getText();

			if (label.equals("Yes")) {
				TestSession.setUiDualActionYesOptionClicked(true);
				System.out.print("\nYes Clicked\n");

			} else if (label.equals("No")) {
				TestSession.setUiDualActionYesOptionClicked(false);
				System.out.print("\nNo Clicked\n");
			}
			TestSession.setUserClickedToCompleteUIDualAction(true);
			TestSession.setUserClickedContinuePlay(true);
			jframe.dispose();
		}
	}

	static void exit(JFrame j) {
		String actionReceivedTime = "Received user action :" + new Date()
				+ "\n";
		String actionReceived = "\nUser action received. Cancelling Test Case execution.\n";

		Trace trace = TestSession.getTraceObj();
		if (trace != null) {
			trace.getLogFileContentTrace().append(actionReceivedTime);
			trace.getLogFileContentTrace().append(actionReceived);
		}
		System.out.print(actionReceivedTime);
		System.out.print(actionReceived);

		TestSession.setUserClickedToCompleteUIDualAction(true);
		TestSession.setUserCancelledCompleteUIDualAction(true);
		j.dispose();
	}

	public static void pauseForUserActionOnDualActionDialog() {
		while (!TestSession.isUserClickedToCompleteUIDualAction()) {
			System.out.print("");

			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
			}

		}
	}
}
