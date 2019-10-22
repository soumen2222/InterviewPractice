package com.qualitylogic.openadr.core.vtn;

import java.util.Date;

import com.qualitylogic.openadr.core.common.UIUserPrompt;
import com.qualitylogic.openadr.core.signal.helper.LogHelper;
import com.qualitylogic.openadr.core.util.ResourceFileReader;

public class RandomizedCancellationTimeoutThread implements Runnable {

	private static boolean timerCompleted=false;
	
	public boolean isTimerCompleted() {
		return timerCompleted;
	}

	public void setTimerCompleted(boolean isCompleted) {
		timerCompleted = isCompleted;
	}

	public void pauseIfRandomizedCancellationNotComplete(){
		
		if(!isTimerCompleted()){
			String startMsg="Delaying test case exit until maximum randomized cancellation window is complete.";
			String endMsg="Randomized cancellation window is complete.";
			LogHelper.addTrace(startMsg);
			System.out.println(startMsg);
			UIUserPrompt prompt = new UIUserPrompt();
			prompt.Prompt(startMsg,
					prompt.SMALL);
			
			while(true){
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {}
				if(isTimerCompleted()){
					break;
				}
			}
			
			LogHelper.addTrace(endMsg);
			System.out.println(endMsg);	
			UIUserPrompt prompt1 = new UIUserPrompt();
			prompt1.Prompt(endMsg,
					prompt.SMALL);
		}

	}
	
	static long testCaseTimeout = 0;
	
	public void start2andHalfMinTimer(){
		
		testCaseTimeout = System.currentTimeMillis() + 150000;
		
		Thread timer=new Thread(new RandomizedCancellationTimeoutThread());
		timer.start();
	}
	
	public void start3MinTimerWithPause(){
		
		String startMsg="Delaying test case exit until randomized cancellation is complete.";
		String endMsg="Randomized cancellation is complete.";
		LogHelper.addTrace(startMsg);
		System.out.println(startMsg);

		testCaseTimeout = System.currentTimeMillis() + 180000;
		
		Thread timer=new Thread(new RandomizedCancellationTimeoutThread());
		timer.start();
		
		UIUserPrompt prompt = new UIUserPrompt();
		prompt.Prompt(new ResourceFileReader().TestCase_RandomizedCancellationPrompt(),
				prompt.MEDIUM);
		
		while(true){
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {}
			if(isTimerCompleted()){
				break;
			}
		}
		
		UIUserPrompt prompt2 = new UIUserPrompt();
		prompt2.Prompt(new ResourceFileReader().TestCase_RandomizedCancellationCompletionPrompt(),
				prompt2.SMALL);
		
		LogHelper.addTrace(endMsg);
		System.out.println(endMsg);	
		
	}
	public RandomizedCancellationTimeoutThread() {
		
	}
	
	
	public void run() {

		String threadStart="Begin : Randomized Cancellation Timeout : "+new Date();

		System.out.println(threadStart);
		
		LogHelper.addTrace(threadStart);
		
		while (System.currentTimeMillis() < testCaseTimeout) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {}
		}
		timerCompleted=true;

		String threadEnd="End : Randomized Cancellation Timeout : "+new Date();
		
		System.out.println(threadEnd);
		LogHelper.addTrace(threadEnd);
		
	}

}
