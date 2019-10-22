package com.qualitylogic.openadr.core.common;

public interface IPrompt {
	public void Prompt(String question, int size);
	public void Prompt(String question);
	
	public String getPromptMessage();
}
