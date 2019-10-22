package com.qualitylogic.openadr.core.action;

import java.util.ArrayList;

import com.qualitylogic.openadr.core.common.IPrompt;
import com.qualitylogic.openadr.core.signal.OadrCreateOptType;

public interface ICreatedEventAction {

	public abstract boolean isPreConditionsMet();

	public abstract boolean isEventCompleted();

	public void setEventCompleted(boolean isEventCompleted);

	public String getCreateEvent();

	public void setLastCreateEvent(boolean isLastResponseResult);

	public boolean isLastCreateEvent();

	public void setPromptForResponseReceipt(IPrompt prompt);

	public IPrompt getPromptForResponseReceipt();
	
	public void addCreateOptEventAction(ICreateOptEventAction createOptEventAction);
	
	public ArrayList<ICreateOptEventAction> getCreateOptEventList();
	
}