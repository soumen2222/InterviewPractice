package com.qualitylogic.openadr.core.base;

import java.util.ArrayList;

import com.qualitylogic.openadr.core.action.ICreatedEventAction;
import com.qualitylogic.openadr.core.action.ICreateOptEventAction;
import com.qualitylogic.openadr.core.common.IPrompt;
import com.qualitylogic.openadr.core.signal.OadrCreatedEventType;

public abstract class Base_CreatedEventAction implements ICreatedEventAction {

	private boolean isEventCompleted;
	protected OadrCreatedEventType oadrCreatedEvent = null;
	boolean isLastCreatedEvent;
	IPrompt prompt;
	ArrayList<ICreateOptEventAction> oadrCreateOptList=new ArrayList<ICreateOptEventAction>();

	public boolean isPreConditionsMet() {
		return true;
	}

	public boolean isEventCompleted() {
		return isEventCompleted;
	}

	public void setEventCompleted(boolean isEventCompleted) {
		this.isEventCompleted = isEventCompleted;
	}

	public void setLastCreateEvent(boolean isLastCreatedEvent) {
		this.isLastCreatedEvent = isLastCreatedEvent;

	}

	public boolean isLastCreateEvent() {
		return isLastCreatedEvent;
	}

	public void setPromptForResponseReceipt(IPrompt prompt) {
		this.prompt = prompt;
	}

	public IPrompt getPromptForResponseReceipt() {
		return prompt;
	}
	
	@Override
	public void addCreateOptEventAction(
			ICreateOptEventAction createOptEventAction) {
		oadrCreateOptList.add(createOptEventAction);
		
	}

	@Override
	public ArrayList<ICreateOptEventAction> getCreateOptEventList() {
		return oadrCreateOptList;
	}


}