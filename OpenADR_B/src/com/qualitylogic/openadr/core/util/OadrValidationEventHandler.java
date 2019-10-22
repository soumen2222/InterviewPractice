
package com.qualitylogic.openadr.core.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventHandler;

import com.qualitylogic.openadr.core.base.Debug;
import com.qualitylogic.openadr.core.bean.ServiceType;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.signal.helper.SchemaHelper;

public class OadrValidationEventHandler implements ValidationEventHandler {

	public boolean handleEvent(ValidationEvent event) {
		TestSession.setValidationErrorFound(true);
		System.out.print("\nSchema validation failed \n");
		Trace trace = TestSession.getTraceObj();
		if (trace != null) {
			trace.getLogFileContentTrace().append(
					"\nSchema Validation Failed\n");
			trace.getLogFileContentTrace().append(
					"Schema Error :  " + event.getMessage());
			trace.getLogFileContentTrace().append(
					"\n-------------------------------\n");
		}
		return true;
	}

	public synchronized static void checkForSchemaValidationError(String data,
			ServiceType service, Direction direction) throws Exception {
		if (data == null || data.length() < 1)
			return;

		try {

			JAXBContext testcontext = JAXBContext
					.newInstance("com.qualitylogic.openadr.core.signal");
			InputStream is = new ByteArrayInputStream(data.getBytes("UTF-8"));
			Unmarshaller unmarshall = testcontext.createUnmarshaller();
			unmarshall.setEventHandler(new OadrValidationEventHandler());
			//new SchemaHelper();
			unmarshall.setSchema(SchemaHelper.getSchema());
			unmarshall.unmarshal(is);

			Trace trace = TestSession.getTraceObj();

			if (!TestSession.isValidationErrorFound()) {
				if (trace != null) {
					trace.getLogFileContentTrace().append(
							"\nSchema Validation has Passed\n");
				}
				boolean conformanceRuleErrorFound = ConformanceRuleValidator
						.isValid(data, service,direction);
				if (!conformanceRuleErrorFound) {
					TestSession.setValidationErrorFound(true);
					
					if (Debug.printLogWhenConformanceRuleErrorFound) {
						System.out.println(trace.getLogFileContentTrace());
					}
					
					trace.getLogFileContentTrace().append(
							"Conformance Rule Validation Failed\n");
					trace.getLogFileContentTrace().append(
							"Stopping Conformance Rule Validation...\n");
				} else {

					PropertiesFileReader properties = new PropertiesFileReader();
					String conformanceValidation = properties
							.get("CONFORMANCE_VALIDATION");
					if (!(ConformanceRuleValidator
							.isDisableAllConformanceCheck() || (conformanceValidation != null && conformanceValidation
							.equals("DISABLE")))) {
						trace.getLogFileContentTrace().append(
								"Conformance Rule Validation Passed\n");
					}

				}

				trace.getLogFileContentTrace().append(
						"------------------------------------------\n");

			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;

		}
	}

}
