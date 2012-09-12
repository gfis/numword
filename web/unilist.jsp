<%--
    Shows a list of 256 Unicode blocks 
    Copyright (C) 2011 Dr. Georg Fischer <punctum@punctum.com>
    @(#) $Id: unilist.jsp 819 2011-11-01 14:06:06Z gfis $
	2011-11-01: copied from unicode.jsp
--%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%--
 * Copyright 2011 Dr. Georg Fischer 
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
<%@page import="java.lang.Character.UnicodeBlock"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Unicode List</title>
    <link rel="stylesheet" type="text/css" href="stylesheet.css">
</head>
<%
    String CVSID = "@(#) $Id: unilist.jsp 819 2011-11-01 14:06:06Z gfis $";
%>
<body>
	<span class="large">Unicode Blocks</span>
    <table>
        <tr>
            <th>Start</th>
            <th>Description</th>
        </tr>
    <%
 		int code = 0;
		while (code < 0x10000) {
    %>
        <tr>
            <td><strong>&nbsp;
    <%
   				out.write("<a href=\"servlet?view=uniblock&digits=" 
   						+ Integer.toHexString(code >> 8)
   						+ "\">" 
   						+ Integer.toHexString(0x10000 + code).substring(1, 3) 
   						+ "xx</a>");
    %>
                &nbsp;</strong>
            </td>
            <td>
    			<%
    				String name = "(undefined)";
    				try {
    					name = Character.UnicodeBlock.of(code).toString();
    				} catch (Exception exc) {
    				}
    				out.write(name);
    			%>
            </td>
        </tr>
    <%  
	        code += 0x100; // next block
        } // while code
    %>
    </table>
    Back to <strong><a href="index.jsp">numword</a></strong> input form;
</body>
</html>
