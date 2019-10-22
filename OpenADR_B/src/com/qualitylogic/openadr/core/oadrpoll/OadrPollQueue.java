package com.qualitylogic.openadr.core.oadrpoll;

import java.util.Vector;

import com.qualitylogic.openadr.core.action.DistributeEventActionList;
import com.qualitylogic.openadr.core.action.IDistributeEventAction;
import com.qualitylogic.openadr.core.action.IRequestReregistrationAction;
import com.qualitylogic.openadr.core.action.IResponseCancelPartyRegistrationAckAction;
import com.qualitylogic.openadr.core.action.IResponseCancelReportTypeAckAction;
import com.qualitylogic.openadr.core.action.IResponseCanceledPartyRegistrationAckAction;
import com.qualitylogic.openadr.core.action.IResponseCreateReportTypeAckAction;
import com.qualitylogic.openadr.core.action.IResponseEventAction;
import com.qualitylogic.openadr.core.action.IResponseRegisterReportTypeAckAction;
import com.qualitylogic.openadr.core.action.IResponseRegisteredReportTypeAckAction;
import com.qualitylogic.openadr.core.action.IResponseUpdateReportTypeAckAction;
import com.qualitylogic.openadr.core.action.RequestReregistrationEventActionList;
import com.qualitylogic.openadr.core.action.ResponseCancelPartyRegistrationAckActionList;
import com.qualitylogic.openadr.core.action.ResponseCancelReportTypeAckActionList;
import com.qualitylogic.openadr.core.action.ResponseCanceledPartyRegistrationAckActionList;
import com.qualitylogic.openadr.core.action.ResponseCreateReportTypeAckActionList;
import com.qualitylogic.openadr.core.action.ResponseEventActionList;
import com.qualitylogic.openadr.core.action.ResponseRegisterReportTypeAckActionList;
import com.qualitylogic.openadr.core.action.ResponseRegisteredReportTypeAckActionList;
import com.qualitylogic.openadr.core.action.ResponseUpdateReportTypeAckActionList;
import com.qualitylogic.openadr.core.action.ResponseUpdatedReportTypeAckActionList;

public class OadrPollQueue{
	
	private static Vector<Object> oadrQueue=new Vector<Object>();

	public static void addToQueue(Object item){
		oadrQueue.add(item);
		
			if(item instanceof 
					IResponseCancelPartyRegistrationAckAction){
				ResponseCancelPartyRegistrationAckActionList.addResponseCancelPartyRegistrationAckAction((IResponseCancelPartyRegistrationAckAction)item);
			}else if(item instanceof 
					IResponseRegisteredReportTypeAckAction){
				ResponseRegisteredReportTypeAckActionList.addResponseRegisteredReportAckAction((IResponseRegisteredReportTypeAckAction)item);
			}else if(item instanceof 
					IResponseCanceledPartyRegistrationAckAction){
				ResponseCanceledPartyRegistrationAckActionList.addResponseCanceledPartyRegistrationAckAction((IResponseCanceledPartyRegistrationAckAction)item);
			}else if(item instanceof 
					IRequestReregistrationAction){
				RequestReregistrationEventActionList.addRequestReregistrationAction((IRequestReregistrationAction)item);
			}else if(item instanceof 
					IDistributeEventAction){
				DistributeEventActionList.addDistributeEventAction((IDistributeEventAction)item);
			}else if(item instanceof IResponseRegisterReportTypeAckAction){
				ResponseRegisterReportTypeAckActionList.addResponseRegisterReportAckAction((IResponseRegisterReportTypeAckAction)item);				
			}else if(item instanceof IResponseCreateReportTypeAckAction){
				ResponseCreateReportTypeAckActionList.addCreateRegisterReportAckAction((IResponseCreateReportTypeAckAction)item);				
			}else if(item instanceof IResponseUpdateReportTypeAckAction){
				ResponseUpdateReportTypeAckActionList.addUpdateRegisterReportAckAction((IResponseUpdateReportTypeAckAction)item);
			}else if(item instanceof IResponseCancelReportTypeAckAction){
				ResponseCancelReportTypeAckActionList.addCancelRegisterReportAckAction((IResponseCancelReportTypeAckAction)item);
			}else if(item instanceof IResponseEventAction){
				ResponseEventActionList.addResponseEventAction((IResponseEventAction)item);
			}else{
				System.out.println("Wrong type added to the Queue");
				System.exit(-1);
			}

			
		}

	

	public static Object getNext(){
	
		for(Object item:oadrQueue){
				if(item instanceof 
						IResponseCancelPartyRegistrationAckAction){
					IResponseCancelPartyRegistrationAckAction responseCancelPartyRegistrationAckAction = (IResponseCancelPartyRegistrationAckAction)item;
					if(!responseCancelPartyRegistrationAckAction.isEventCompleted()){
						return responseCancelPartyRegistrationAckAction;
					}
				}else if(item instanceof 
						IResponseRegisteredReportTypeAckAction){
					IResponseRegisteredReportTypeAckAction responseRegisteredReportTypeAckAction = (IResponseRegisteredReportTypeAckAction)item;
					if(!responseRegisteredReportTypeAckAction.isEventCompleted()){
						return responseRegisteredReportTypeAckAction;
					}
					
				}else if(item instanceof 
						IResponseCanceledPartyRegistrationAckAction){
					IResponseCanceledPartyRegistrationAckAction responseCanceledPartyRegistrationAckAction = (IResponseCanceledPartyRegistrationAckAction)item;
					if(!responseCanceledPartyRegistrationAckAction.isEventCompleted()){
						return responseCanceledPartyRegistrationAckAction;
					}
				
				}else if(item instanceof 
						IRequestReregistrationAction){
					IRequestReregistrationAction requestReregistrationAction = (IRequestReregistrationAction)item;
					if(!requestReregistrationAction.isEventCompleted()){
						return requestReregistrationAction;
					}
				}else if(item instanceof 
						IDistributeEventAction){
					IDistributeEventAction distributeEventAction = (IDistributeEventAction)item;
					if(!distributeEventAction.isEventCompleted()){
						return distributeEventAction;
					}
				}else if(item instanceof IResponseRegisterReportTypeAckAction){
					IResponseRegisterReportTypeAckAction responseRegisterReportTypeAckAction = (IResponseRegisterReportTypeAckAction)item;
						if(!responseRegisterReportTypeAckAction.isEventCompleted()){
							return responseRegisterReportTypeAckAction;
						}
						
				}else if(item instanceof IResponseCreateReportTypeAckAction){
					IResponseCreateReportTypeAckAction responseCreateReportTypeAckAction = (IResponseCreateReportTypeAckAction)item;
					if(!responseCreateReportTypeAckAction.isEventCompleted()){
						return responseCreateReportTypeAckAction;
					}
					
				}else if(item instanceof IResponseUpdateReportTypeAckAction){
					IResponseUpdateReportTypeAckAction responseUpdateReportTypeAckAction = (IResponseUpdateReportTypeAckAction)item;
					if(!responseUpdateReportTypeAckAction.isEventCompleted()){
						return responseUpdateReportTypeAckAction;
					}

				}else if(item instanceof IResponseCancelReportTypeAckAction){
					IResponseCancelReportTypeAckAction responseCancelReportTypeAckAction = (IResponseCancelReportTypeAckAction)item;
					if(!responseCancelReportTypeAckAction.isEventCompleted()){
						return responseCancelReportTypeAckAction;
					}

				}else if(item instanceof IResponseEventAction){
					IResponseEventAction responseEventAction = (IResponseEventAction)item;
					if(!responseEventAction.isEventCompleted()){
						return responseEventAction;
					}

				}
				
		}
		return null;
	}
	

	public static Vector<Object> getOadrQueue(){
		return oadrQueue;
	}
	
	public static void main(String args[]){
		
		addToQueue("1");
		addToQueue("2");
		addToQueue("3");
		
		System.out.println(getNext());
		System.out.println(getNext());
		System.out.println(getNext());
		System.out.println(getNext());
		System.out.println(getNext());
	}
	
}
