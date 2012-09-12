<%--
    Shows a range of 256 Unicode characters
    Copyright (C) 2005 Dr. Georg Fischer <punctum@punctum.com>
    @(#) $Id: uniblock.jsp 819 2011-11-01 14:06:06Z gfis $
	2011-11-01: revised 8x32 layout, block names; -> G&ouml;rlitz
	2009-12-04: name changed: numservlet -> servlet
    2006-12-13: links to some special planes on the right
    2006-01-02: copied from index.jsp
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
<%@page import="java.lang.Character.UnicodeBlock"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Unicode Block</title>
    <link rel="stylesheet" type="text/css" href="stylesheet.css">
</head>
<%
    String CVSID = "@(#) $Id: uniblock.jsp 819 2011-11-01 14:06:06Z gfis $";
    Object 
    field = session.getAttribute("digits");
    String digits = (field != null) ? (String) field : "01";
	int highByte = 0;
    try {
        highByte = Integer.parseInt(digits, 16);
    } catch (Exception exc) {
    }
    int pageNo = highByte * 256;           
%>
<body>
    <form action="servlet" method="post">
	    <span class="large">Unicode Block U+<%= Integer.toHexString(0x100 + highByte).substring(1).toUpperCase() %>xx</span>
        &#xa0; &#xa0;
        <input type = "hidden" name="view" value="uniblock" />
        Upper byte: <input name="digits" maxsize="2" size="2" value="<%= digits %>" />
        &#xa0; &#xa0;
        <input type="submit" value="Lookup" />
        &#xa0; &#xa0;
        <%
		    out.write("<a href=\"servlet?view=uniblock&digits=" + Integer.toHexString(highByte - 1) + "\">&lt;</a>&#xa0; &#xa0;");
		    out.write("<a href=\"servlet?view=uniblock&digits=" + Integer.toHexString(highByte + 1) + "\">&gt;</a>&#10;");
        %>
        &#xa0; &#xa0;
		<span class="large">
			<%
    			String name = "(undefined)";
    			try {
    				name = Character.UnicodeBlock.of(pageNo).toString();
    			} catch (Exception exc) {
    			}
    			out.write(name);
			%>
		</span>
	</form>
	
    <table>
        <tr>
            <th>
                &nbsp;
            </th>
    <%  
    	int maxCol = 32;
    	int maxRow =  8;
        for (int column = 0; column < maxCol; column ++) {
        %>
            <th>
            <%
                out.write (Integer.toHexString(0x100 + column).substring(1));
            %>
            </th>
        <%  
        }
    %>
        </tr>
    <%
 		int code = 0;
        for (int row = 0; row < maxRow; row ++) {
    %>
        <tr>
            <td><strong>&nbsp;
                <%= Integer.toHexString(0x100 + code).substring(1) %>
                &nbsp;</strong>
            </td>
            <%  for (int column = 0; column < maxCol; column ++) {
            %>
                <td>
                <%
                	code ++;
                    int unicode = pageNo + code;
                    // out.write(Integer.toString(unicode));
                    String unistr = Integer.toHexString(0x10000 + unicode).substring(1);
                    out.write("<span class=\"code\">");
                    out.write("<a href=\"servlet?view=unicode&digits=" + unistr + "\">" + unistr + "</a></span>");
                    out.write("<br /><span class=\"glyph\">&#x" + unistr + ";</span>");
                %>
                </td>
            <%  }
        }
    %>
    </table>
    Back to <strong><a href="index.jsp">numword</a></strong> input form;
    &#xa0; &#xa0;
    <a href="servlet?view=unilist">List</a> of all Unicode blocks &lt; 0x10000
</body>
</html>
