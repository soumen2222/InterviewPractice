@echo off
set TRUSTSTORE=truststore.jks
set PASSWORD=password

rem Import root CA certificate to truststore
rem
keytool -import -keystore %TRUSTSTORE% -storepass %PASSWORD% -alias RSA_RCA0001  -file TEST_OpenADR_RSA_RCA0001_Cert.crt

rem Import intermediate CA certificate for VTN server certificates to truststore
rem
keytool -import -keystore %TRUSTSTORE% -storepass %PASSWORD% -alias RSA_SPCA0001 -file TEST_OpenADR_RSA_SPCA0001_Cert.crt

rem Import intermediate CA certificate for VEN device certificates to truststore
rem
keytool -import -keystore %TRUSTSTORE% -storepass %PASSWORD% -alias RSA_MCA0001  -file TEST_OpenADR_RSA_MCA0001_Cert.crt

rem List truststore
rem
keytool -list -v -storepass %PASSWORD% -Keystore %TRUSTSTORE%

pause
