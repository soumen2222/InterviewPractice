package com.qualitylogic.openadr.core.bean;

import java.util.ArrayList;
import java.util.Collections;
import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;

import com.qualitylogic.openadr.core.signal.EventStatusEnumeratedType;
import com.qualitylogic.openadr.core.util.OadrUtil;

public class EventBean implements Comparable<EventBean> {
	String eventID;

	// 1- active 2- near or far, 3 for complete or cancelled
	EventStatusEnumeratedType eventStatus;
	long priority;
	XMLGregorianCalendar startDate;

	public static void main(String args[]) {

		EventBean requestEventBeana = new EventBean();
		requestEventBeana.setEventStatus(EventStatusEnumeratedType.CANCELLED);
		requestEventBeana.setPriority(1);

		EventBean requestEventBean0 = new EventBean();
		requestEventBean0.setEventStatus(EventStatusEnumeratedType.COMPLETED);
		requestEventBean0.setPriority(1);

		EventBean requestEventBeann = new EventBean();
		requestEventBeann.setEventStatus(EventStatusEnumeratedType.NEAR);
		requestEventBeann.setPriority(1);
		XMLGregorianCalendar startDate = OadrUtil.getCurrentTime();

		requestEventBeann.setStartDate(startDate);

		EventBean requestEventBean1 = new EventBean();
		requestEventBean1.setEventStatus(EventStatusEnumeratedType.FAR);
		requestEventBean1.setPriority(1);
		XMLGregorianCalendar startDate1 = (XMLGregorianCalendar) startDate
				.clone();

		requestEventBean1.setStartDate(startDate1);

		EventBean requestEventBean2 = new EventBean();
		requestEventBean2.setEventStatus(EventStatusEnumeratedType.ACTIVE);
		requestEventBean2.setPriority(3);
		XMLGregorianCalendar startDatex = (XMLGregorianCalendar) startDate
				.clone();
		requestEventBean2.setStartDate(startDatex);

		EventBean requestEventBean2b = new EventBean();
		requestEventBean2b.setEventStatus(EventStatusEnumeratedType.ACTIVE);
		requestEventBean2b.setPriority(3);
		XMLGregorianCalendar startDatey = (XMLGregorianCalendar) startDatex
				.clone();
		Duration durationyToAdd = OadrUtil.createDuration(5, 0);
		startDatey.add(durationyToAdd);
		requestEventBean2b.setStartDate(startDatey);

		EventBean requestEventBean3 = new EventBean();
		requestEventBean3.setEventStatus(EventStatusEnumeratedType.ACTIVE);
		requestEventBean3.setPriority(2);

		Duration durationToAdd = OadrUtil.createDuration(5, 0);
		startDate.add(durationToAdd);

		ArrayList<EventBean> requestEventBeanList = new ArrayList<EventBean>();
		requestEventBeanList.add(requestEventBeann);
		requestEventBeanList.add(requestEventBeana);
		requestEventBeanList.add(requestEventBean0);
		requestEventBeanList.add(requestEventBean1);
		requestEventBeanList.add(requestEventBean2);
		requestEventBeanList.add(requestEventBean3);
		requestEventBeanList.add(requestEventBean2b);
		Collections.sort(requestEventBeanList);

		for (EventBean eachRequestEventBean : requestEventBeanList) {
			System.out.println(eachRequestEventBean.getEventStatus() + " : "
					+ eachRequestEventBean.getPriority() + " "
					+ eachRequestEventBean.getStartDate());
		}
	}

	public String getEventID() {
		return eventID;
	}

	public void setEventID(String eventID) {
		this.eventID = eventID;
	}

	public int compareTo(EventBean requestEventBean) {

		if (this.getEventStatus().equals(EventStatusEnumeratedType.ACTIVE)
				&& requestEventBean.getEventStatus().equals(
						EventStatusEnumeratedType.ACTIVE)) {
			if (this.getPriority() == requestEventBean.getPriority()) {

				if (this.getStartDate()
						.compare(requestEventBean.getStartDate()) == 0) {
					return 0;
				} else if (this.getStartDate().compare(
						requestEventBean.getStartDate()) > 0) {
					return 1;
				} else {
					return -1;
				}

			} else if (this.getPriority() > requestEventBean.getPriority()) {
				return 1;
			} else {
				return -1;
			}

		}
		if (this.getEventStatus().equals(EventStatusEnumeratedType.ACTIVE)
				&& !requestEventBean.getEventStatus().equals(
						EventStatusEnumeratedType.ACTIVE)) {
			return -1;
		}
		if (!this.getEventStatus().equals(EventStatusEnumeratedType.ACTIVE)
				&& requestEventBean.getEventStatus().equals(
						EventStatusEnumeratedType.ACTIVE)) {
			return 1;
		}

		boolean isCurrentEventPending = (this.getEventStatus().equals(
				EventStatusEnumeratedType.NEAR) || this.getEventStatus()
				.equals(EventStatusEnumeratedType.FAR));
		boolean isPreviousEventPending = (requestEventBean.getEventStatus()
				.equals(EventStatusEnumeratedType.NEAR) || requestEventBean
				.getEventStatus().equals(EventStatusEnumeratedType.FAR));

		if (isCurrentEventPending && isPreviousEventPending) {
			if (this.getStartDate().compare(requestEventBean.getStartDate()) == 0) {
				return 0;
			} else if (this.getStartDate().compare(
					requestEventBean.getStartDate()) > 0) {
				return 1;
			} else {
				return -1;
			}
		}

		if (isCurrentEventPending && !isPreviousEventPending) {
			return -1;
		}
		if (!isCurrentEventPending && isPreviousEventPending) {
			return 1;
		}

		return 1;
	}

	public EventStatusEnumeratedType getEventStatus() {
		return eventStatus;
	}

	public void setEventStatus(EventStatusEnumeratedType eventStatus) {
		this.eventStatus = eventStatus;
	}

	public long getPriority() {

		if (getEventStatus().equals(EventStatusEnumeratedType.ACTIVE)
				&& priority == 0)
			return 9999;

		return priority;
	}

	public void setPriority(long priority) {
		this.priority = priority;
	}

	public XMLGregorianCalendar getStartDate() {
		return startDate;
	}

	public void setStartDate(XMLGregorianCalendar startDate) {
		this.startDate = startDate;
	}

}
