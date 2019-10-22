package com.qualitylogic.openadr.core.vtn;

import java.util.Vector;

import com.qualitylogic.openadr.core.action.DistributeEventActionList;
import com.qualitylogic.openadr.core.action.IDistributeEventAction;
import com.qualitylogic.openadr.core.action.IRequestReregistrationAction;
import com.qualitylogic.openadr.core.action.IResponseCancelPartyRegistrationAckAction;
import com.qualitylogic.openadr.core.action.IResponseCanceledPartyRegistrationAckAction;
import com.qualitylogic.openadr.core.action.IResponseRegisteredReportTypeAckAction;
import com.qualitylogic.openadr.core.action.RequestReregistrationEventActionList;
import com.qualitylogic.openadr.core.action.ResponseCancelPartyRegistrationAckActionList;
import com.qualitylogic.openadr.core.action.ResponseCanceledPartyRegistrationAckActionList;
import com.qualitylogic.openadr.core.action.ResponseRegisteredReportTypeAckActionList;
import com.qualitylogic.openadr.core.oadrpoll.OadrPollQueue;

public class VTNToPollServiceThread implements Runnable{

	static boolean isExit;
	
	public static boolean isExit() {
		return isExit;
	}

	public static void setExit(boolean exit) {
		isExit = exit;
	}

	@Override
	public void run() {
		while(true){
			if(isExit){
				return;
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}

			
	////////////////////////////////////////
						
/*			
			IResponseCancelPartyRegistrationAckAction cancelPartyRegistrationAckAction = ResponseCancelPartyRegistrationAckActionList.getNextResponseCancelPartyRegistrationActionAction();
			
				boolean isPreconditionsMet = false;
				if (cancelPartyRegistrationAckAction != null) {
					isPreconditionsMet = cancelPartyRegistrationAckAction
							.isPreConditionsMet();
				}

				//OadrDistributeEventType oadrDistributeEvent = null;

				if (isPreconditionsMet) {
					
					//OadrPollServerResource.getQueue().offer(cancelPartyRegistrationAckAction);
					OadrPollQueue.addToQueue(cancelPartyRegistrationAckAction);

					
					//oadrDistributeEvent = distributeEventAction
						//	.getDistributeEvent();
				}else if(cancelPartyRegistrationAckAction!=null){
					cancelPartyRegistrationAckAction.resetToInitialState();
				}*/
			////////////////////////////////////////
	////////////////////////////////////////
			
	/*		IResponseRegisteredReportTypeAckAction responseRegisteredReportTypeAckAction = ResponseRegisteredReportTypeAckActionList.getNextResponseRegisteredReportAckAction();

				isPreconditionsMet = false;
				if (responseRegisteredReportTypeAckAction != null) {
					isPreconditionsMet = responseRegisteredReportTypeAckAction
							.isPreConditionsMet();
				}

				//OadrDistributeEventType oadrDistributeEvent = null;

				if (isPreconditionsMet) {
					
					//OadrPollServerResource.getQueue().offer(responseRegisteredReportTypeAckAction);
					OadrPollQueue.addToQueue(responseRegisteredReportTypeAckAction);

					//oadrDistributeEvent = distributeEventAction
						//	.getDistributeEvent();
				}else if(responseRegisteredReportTypeAckAction!=null){
					responseRegisteredReportTypeAckAction.resetToInitialState();
				}*/

			////////////////////////////////////////

				////////////////////////////////////////
				
/*				IResponseCanceledPartyRegistrationAckAction canceledPartyRegistrationAckAction = ResponseCanceledPartyRegistrationAckActionList.getNextResponseCanceledPartyRegistrationActionAction();

					isPreconditionsMet = false;
					if (canceledPartyRegistrationAckAction != null) {
						isPreconditionsMet = canceledPartyRegistrationAckAction
								.isPreConditionsMet();
					}

					//OadrDistributeEventType oadrDistributeEvent = null;

					if (isPreconditionsMet) {
						
						//OadrPollServerResource.getQueue().offer(canceledPartyRegistrationAckAction);
						OadrPollQueue.addToQueue(canceledPartyRegistrationAckAction);

						//oadrDistributeEvent = distributeEventAction
							//	.getDistributeEvent();
					}else if(canceledPartyRegistrationAckAction!=null){
						canceledPartyRegistrationAckAction.resetToInitialState();
					}
*/
				////////////////////////////////////////
					
			////////////////////////////////////////
			
		/*	IRequestReregistrationAction requestReregistrationAction = RequestReregistrationEventActionList.getNextRequestReregistrationAction();

				 isPreconditionsMet = false;
				if (requestReregistrationAction != null) {
					isPreconditionsMet = requestReregistrationAction
							.isPreConditionsMet();
				}

				//OadrDistributeEventType oadrDistributeEvent = null;

				if (isPreconditionsMet) {
					
					//OadrPollServerResource.getQueue().offer(requestReregistrationAction);
					OadrPollQueue.addToQueue(requestReregistrationAction);

					//oadrDistributeEvent = distributeEventAction
						//	.getDistributeEvent();
				}else if(requestReregistrationAction!=null){
					requestReregistrationAction.resetToInitialState();
				}*/

			////////////////////////////////////////
			//FROM_INTERNAL_VTN
/*			IDistributeEventAction distributeEventAction = DistributeEventActionList
					.getNextDistributeEventAction();

				isPreconditionsMet = false;
				if (distributeEventAction != null) {
					isPreconditionsMet = distributeEventAction
							.isPreConditionsMet(null);
				}

				//OadrDistributeEventType oadrDistributeEvent = null;

				if (isPreconditionsMet) {
					
					//OadrPollServerResource.getQueue().offer(distributeEventAction);
					OadrPollQueue.addToQueue(distributeEventAction);

					//oadrDistributeEvent = distributeEventAction
						//	.getDistributeEvent();
				}else if(distributeEventAction!=null){
					distributeEventAction.resetToInitialState();
				}
*/				
			
						
				//if(oadrDistributeEvent!=null){
					//OadrPollServerResource.getQueue().offer(oadrDistributeEvent);
					
					/*
					JAXBContext context = null;

					context = JAXBContext.newInstance(OadrDistributeEventType.class);
					String distributeEvent = SchemaHelper.asString(context,
									oadrDistributeEvent);
					ConcurrentLinkedQueue<Object> queue = OadrPollServerResource.getQueue();
					
					VenToVtnClient.post(distributeEvent,true);
					*/
				//}
							
		}		
	}
	
	
	
	

}
