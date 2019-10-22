@echo off
set PASSWORD=password
set IMPORT_KEY=com.qualitylogic.openadr.core.util.ImportKey
set CLASSPATH=..\..\..\..\..\bin\

rem Import certificate and private key to its own keystore using ImportKey.java
rem
java -cp %CLASSPATH% %IMPORT_KEY% VTN_vtn_privkey.der          VTN_vtn_cert.crt          vtn          %PASSWORD% keystore_vtn_vtn.jks
java -cp %CLASSPATH% %IMPORT_KEY% VEN_111111111111_privkey.der VEN_111111111111_cert.crt 111111111111 %PASSWORD% keystore_ven_111111111111.jks
java -cp %CLASSPATH% %IMPORT_KEY% VEN_222222222222_privkey.der VEN_222222222222_cert.crt 222222222222 %PASSWORD% keystore_ven_222222222222.jks

rem List keystores
rem
keytool -list -v -storepass %PASSWORD% -keystore keystore_vtn_vtn.jks
keytool -list -v -storepass %PASSWORD% -keystore keystore_ven_111111111111.jks
keytool -list -v -storepass %PASSWORD% -keystore keystore_ven_222222222222.jks

pause
