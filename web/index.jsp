<%--
    @(#) $Id: index.jsp 820 2011-11-07 21:59:07Z gfis $
    2011-10-17: spellClock
    2009-11-24: spellGreeting
	2009-04-24: simple 4 columns layout
    2009-04-06: old names were 'NumberSpeller' etc.
    2007-04-24: <input name="digits" maxsize="123" size="101"
    2007-02-08: use SpellerFactory
    2006-01-02: result in 2nd row of table
    2005-08-22: Georg Fischer
--%>
<%@page import="org.teherba.numword.BaseSpeller"%>
<%@page import="org.teherba.numword.NumwordCommand"%>
<%@page import="org.teherba.numword.SpellerFactory"%>
<%@page import="java.util.Iterator"%>
<% response.setContentType("text/html; charset=UTF-8"); %>
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
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>Number Words</title>
    <link rel="stylesheet" type="text/css" href="stylesheet.css">
</head>
<%
    String CVSID = "@(#) $Id: index.jsp 820 2011-11-07 21:59:07Z gfis $";
    String[] optFunction = new String []
            /*  0 */ { "c"
            /*  1 */ , "C"
            /*  2 */ , "m"
            /*  3 */ , "m3"
            /*  4 */ , "w"
            /*  5 */ , "w2"
            /*  6 */ , "s"
            /*  7 */ , "g"
            /*  8 */ , "h0"
            /*  9 */ , "h1"
            /* 10 */ , "h2"
            /* 11 */ , "h3"
			/* 12 */ , "d"
			/* 13 */ , "p"
            } ;
    String[] enFunction = new String []
            /*  0 */ { "Digits as Word"
            /*  1 */ , "Word as Digits"
            /*  2 */ , "Month"
            /*  3 */ , "Month's Abbreviation"
            /*  4 */ , "Weekday"
            /*  5 */ , "Weekday's Abbreviation"
            /*  6 */ , "Season"
            /*  7 */ , "Greeting"
            /*  8 */ , "Time of Day - offical"
            /*  9 */ , "Time - variant 1"
            /* 10 */ , "Time - variant 2"
            /* 11 */ , "Time - variant 3"
            /* 12 */ , "Cardinal Direction"
            /* 13 */ , "Planet"
            } ;
    SpellerFactory factory;
    Object obj = session.getAttribute("factory");
    if (obj == null) {
        factory = new SpellerFactory();
        session.setAttribute("factory"  , factory );
    } else {
        factory = (SpellerFactory) obj;
    }
    // factory = new SpellerFactory();
    Object
    field = session.getAttribute("function");
    String function = (field != null) ? (String) field : "c";
    field = session.getAttribute("language");
    String language = (field != null && field != "") ? (String) field : "de";
    field = session.getAttribute("digits");
    String digits   = (field != null) ? (String) field : "1947";
%>
<body>
    <!--
    function <%=function%>, language <%=language%>, digits="<%=digits%>"
    -->
    <h2>Number Words</h2>
    <form action="servlet" method="post">
        <input type = "hidden" name="view" value="index" />
        <table cellpadding="8"> 
            <tr>
                <td width="100">Language<br />
                    <select name="language" size="<%= factory.getCount() %>"> <%-- 16 --%>
                    <%
                        Iterator/*<1.5*/<BaseSpeller>/*1.5>*/ iter = factory.getIterator();
                        while (iter.hasNext()) {
                            BaseSpeller speller = (BaseSpeller) iter.next();
                            String iso = speller.getFirstIso639();
                            out.write("<option value=\""
                                    + iso + "\""
                                    + (iso.equals(language) ? " selected" : "")
                                    + ">"
                                    + speller.getDescription() + "</option>\n");
                        } // while iter
                    %>
                    </select>
                </td>

                <td width="100">Spell<br />
                    <select name="function" size="<%= optFunction.length %>">
                    <%
                        int ind = 0;
                        while (ind < optFunction.length) {
                            out.write("<option value=\""
                                    + optFunction[ind] + "\""
                                    + (optFunction[ind].equals(function) ? " selected" : "")
                                    + ">"
                                    + enFunction[ind] + "</option>\n");
                            ind ++;
                        } // while ind
                    %>
                    </select>
                    <br />&nbsp;
                    <br /><a href="docs/documentation.html">Documentation</a>,
                          <a href="docs/api/index.html">API</a>
                    <br /><a href="docs/developer.html">Developer Hints, </a>
                          <a href="docs/bugs.html">Bugs</a>
                    <br /><a href="servlet?view=uniblock&digits=01">Unicode Tables</a>
				    <br /><a href="metaInf.jsp?view=manifest">Manifest</a>, 
				          <a href="metaInf.jsp?view=license" >License</a>
          			<br /><a href="metaInf.jsp?view=notice"  >References</a>
                    <p  />If the <em>Digits/Word</em> field is left empty, all months, weekdays etc. are shown,
                    and for "Digits as Words" a list of representative test numbers is spelled.
                </td>

                <td width="100" valign="top">Digits / Word<br />
                    <input name="digits" size="80" value="<%= digits %>" />
                    <br />&nbsp;
                    <br /><input type="submit" value="Submit" />
					<p />
    <%
		// this is the result output code
	    NumwordCommand command = new NumwordCommand(); 
        String commandLine = "-l " + language + " -" + function + " " + digits;
	    command.setMode(command.MODE_HTML_EM);
        if (digits.equals("") && "ch".indexOf(function.substring(0,1)) >= 0) {
		    command.setMode(command.MODE_HTML);
        }
        out.write(command.process(commandLine));
	/*
        if (factory.getSpeller(language).getDirection().equals("rtl")) {
            // swap around tab
            int pos = result.indexOf("\t");
            out.write(result.substring(pos + 1)); // 2nd element - word
            out.write("\t");
            out.write(result.substring(0, pos)); // 1st element - digits
            //    result =  "<bdo dir=\"ltr\">"
            //            + result.replace("\t", "</bdo>\t<bdo dir=\"ltr\">")
            //            + "</bdo>";
        }
        else {
            out.write(result);
        }
    */
    %>
                </td>

                <td width="*">
                    <%-- such that the input field remains visible --%>
                    &nbsp;
                </td>
            </tr>
        </table>
    </form>
    <p>
    <font size="-2">
    Questions, remarks to: <a href="mailto:punctum@punctum.com">Dr. Georg Fischer</a>
    </font>
    </p>

</body>
</html>
