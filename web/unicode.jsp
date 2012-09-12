<%--
    Shows a single Unicode character with its description
    Copyright (C) 2005 Dr. Georg Fischer <punctum@punctum.com>
    @(#) $Id: unicode.jsp 819 2011-11-01 14:06:06Z gfis $
	2011-11-01: superceeded, was uniblock.jsp before 
	Example for the output of 'unicode': 
	
gfis@nunki:~/work/webapps-super/numword$ unicode -x 25b6 -C off 
U+25B6 BLACK RIGHT-POINTING TRIANGLE
UTF-8: e2 96 b6  UTF-16BE: 25b6  Decimal: &#9654;

Category: So (Symbol, Other)
Bidi: ON (Other Neutrals)

--%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%--
 * Copyright 2006 Dr. Georg Fischer <punctum at punctum dot kom>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
--%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Unicode</title>
    <link rel="stylesheet" type="text/css" href="stylesheet.css">
</head>
<%
    String CVSID = "@(#) $Id: unicode.jsp 819 2011-11-01 14:06:06Z gfis $";
    Object 
    field = session.getAttribute("text");
    String[] text = new String[] {""};
    if (field != null) {
    	text = (String[]) field;
    } 
%>
<body>
	<%
	int nline = text.length;
	int iline = 0;
	if (iline < nline && text[iline] != null) {
	%>
		<h2><%=    text[iline] %></h2>
	<%
	} 
	iline ++;
	if (iline < nline && text[iline] != null) {
	%>
		<br /><span><%=  text[iline].replaceAll("&", "&amp") %></span>
	<%
	} 
	iline ++;
	if (iline < nline && text[iline] != null) {
	%>
		<br /><span style="font-size:240pt">&#x<%= Integer.toHexString(0x10000 + text[iline].charAt(0)).substring(1) %>;</span>
	<%
	} 
	iline ++;
	if (iline < nline && text[iline] != null) {
	%>
		<br /><span><%=  text[iline] %></span>
	<%
	} 
	iline ++;
	if (iline < nline && text[iline] != null) {
	%>
		<br /><span><%=  text[iline] %></span>
	<%
	}
    %>
</body>
</html>
