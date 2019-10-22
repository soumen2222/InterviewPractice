<?xml version="1.0"?>
<!DOCTYPE xsl:stylesheet [ <!ENTITY nbsp '&#xA0;'>]>



<xsl:stylesheet version="1.0"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
 <xsl:output method="html" indent="yes"/>
<xsl:template match="/">



  <html>
  <head>
  <link rel="stylesheet" type="text/css" href="logcss.css" 
                title="Style"/>

  </head>
  <body>
    <h2>Test Case Result</h2>
    <table border="0">
      <tr bgcolor="#CCCCCC" >

        <th>Test Case Name</th>
        <th>Start Time</th>
	<th>End Time</th>
        <th>Result</th>
        <th>Log</th>
       
      </tr>
      <xsl:for-each select="log/trace">
        <tr class="odd">

	  <td nowrap="nowrap"><xsl:value-of select="@testCaseName"/></td>        
	  <td nowrap="nowrap"><xsl:value-of select="@testStartTime"/></td>        
          <td nowrap="nowrap"><xsl:value-of select="@testEndTime"/></td>
          <td nowrap="nowrap"><xsl:value-of select="@result"/></td>
          <td nowrap="nowrap">   <a href="{@logFileName}" target="_new">View Log <xsl:value-of select="@logFileName"/></a><xsl:text disable-output-escaping="yes">&nbsp;</xsl:text></td>
        </tr>
      </xsl:for-each>
    </table>
  </body>
  </html>
</xsl:template>

</xsl:stylesheet>