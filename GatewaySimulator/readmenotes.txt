a) Need to certificate details and meter update frequency (a server can be VTN or VEN)

        <property name="com.honeywell.dras.vtn.openadr.2b.ssl.required" value="true"/>
        <property name="com.honeywell.dras.soap.isCertCommonNameValidationEnabled" value="true"/>
		<property name="com.honeywell.baseline.meterInterval" value="15"/>
		<property name="com.honeywell.dras.vtn.push.client.keyStoreFileName" value="C:/Project/DRAS8/OpenADRGatewayCerts/certs/SHA1/Keystore_Ven.jks"/>
        <property name="com.honeywell.dras.vtn.push.client.keyStorePassword" value="password"/>
        <property name="com.honeywell.dras.vtn.push.client.trustStoreFileName" value="C:/Project/DRAS8/OpenADRGatewayCerts/certs/SHA1/truststore.jks"/>
        <property name="com.honeywell.dras.vtn.push.client.trustStorePassword" value="password"/>




b) Need to add a new data source in Standalone.
<xa-datasource jndi-name="java:jboss/datasources/venSimulatorDS" pool-name="venSimulatorDS">
                    <xa-datasource-property name="ServerName">
                        localhost
                    </xa-datasource-property>
                    <xa-datasource-property name="PortNumber">
                        5432
                    </xa-datasource-property>
                    <xa-datasource-property name="DatabaseName">
                        venSimulator
                    </xa-datasource-property>
                    <xa-datasource-property name="User">
                        postgres
                    </xa-datasource-property>
                    <xa-datasource-property name="Password">
                        Password1
                    </xa-datasource-property>
                    <xa-datasource-class>org.postgresql.xa.PGXADataSource</xa-datasource-class>
                    <driver>postgresql-9.2-1003.jdbc4.jar</driver>
                    <xa-pool>
                        <min-pool-size>5</min-pool-size>
                        <max-pool-size>30</max-pool-size>
                        <prefill>true</prefill>
                        <use-strict-min>false</use-strict-min>
                        <flush-strategy>FailingConnectionOnly</flush-strategy>
                    </xa-pool>
                </xa-datasource>
				
c) Need to add queue for customer -resource creation , Device polling and meter update 

                    <jms-queue name="DevicePollQueue">
                        <entry name="queue/devicePollQueue"/>
                        <entry name="java:jboss/exported/jms/queue/devicePollQueue"/>
                    </jms-queue>
					<jms-queue name="DeviceMeterDataQueue">
                        <entry name="queue/deviceMeterDataQueue"/>
                        <entry name="java:jboss/exported/jms/queue/deviceMeterDataQueue"/>
                    </jms-queue>
                    <jms-queue name="AccountCreationQueue">
                        <entry name="queue/accountCreationQueue"/>
                        <entry name="java:jboss/exported/jms/queue/accountCreationQueue"/>
                    </jms-queue>
					
d) URL to access the Simulator after war is deployed

https://demo1.openadr.com/GatewaySimulator

Add DRAS URL , Ven name and vtn details such as 

VTN URL - https://demo1.openadr.com/bvtn
DRAS URL - https://demo1.openadr.com
Ven Name: GatewaySim