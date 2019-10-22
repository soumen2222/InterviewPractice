package com.qualitylogic.openadr.core.util;

import java.util.Map;

import org.restlet.Request;
import org.restlet.Response;
import org.restlet.Uniform;

public class RequestSentCallback implements Uniform {

	public void handle(Request request, Response response) {

		Map<String, Object> map = response.getAttributes();
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			System.out.println(entry.getKey() + " : " + entry.getValue()
					+ " \n");
		}
		System.out.println("");
	}

}
