@echo off
set IP=127.0.0.1

rem Convert certificate to PEM
rem
openssl x509 -in VTN_%IP%_cert.crt -inform der -outform pem -out VTN_%IP%_cert.pem

rem Convert DER encoded private key to PEM
rem
openssl pkcs8 -in VTN_%IP%_privkey.der -inform DER -out VTN_%IP%_privkey.pem -outform PEM -nocrypt

echo Please go to /import-certificate.jsp page in Openfire and import the signed certificate by copy-pasting the contents of VTN_%IP%_privkey.pem and VTN_%IP%_cert.pem.
echo Press enter after you have completed configuring Openfire.
pause

del VTN_%IP%_privkey.pem
del VTN_%IP%_cert.pem
