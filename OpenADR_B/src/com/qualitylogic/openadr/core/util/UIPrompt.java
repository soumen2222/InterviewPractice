package com.qualitylogic.openadr.core.util;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.*;

public class UIPrompt extends JFrame {

	private static final long serialVersionUID = 1L;

	public static void main(String args[]) {
		JFrame userPromptFrame = new JFrame("OpenADR TestHarness");
		userPromptFrame.setVisible(true);
		userPromptFrame.setSize(150, 150);
		userPromptFrame
				.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		userPromptFrame.setAlwaysOnTop(true);
		JPanel panel = new JPanel();
		userPromptFrame.add(panel);
		JButton button = new JButton("Click to continue");
		JLabel label = new JLabel(
				"Test Case execution has paused as it required manual intervention.");
		button.addActionListener(new UIPromptAction());
		userPromptFrame.add(label);
		panel.add(button);
	}

	static class UIPromptAction implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			System.out.println("Action Performeds");
		}

		public void windowClosed(WindowEvent e) {
			System.out.println("Action Performeds");
		}

	}
}
